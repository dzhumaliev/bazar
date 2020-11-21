package solutions.isky.gaurangarevolution.presentation.mvp.main;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetListPoster;
import solutions.isky.gaurangarevolution.data.event.GetSettings;
import solutions.isky.gaurangarevolution.data.event.GetUserInfo;
import solutions.isky.gaurangarevolution.data.event.UpdateFavorite;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

/**
 * Created by sergey on 21.03.18.
 */
@InjectViewState
public class MainPresenter extends BasePresenter<IMainView> {

    @Inject
    ServerMethod serverMethod;

    public MainPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getAppSettings(JsonObjBody.getSettings());
    }


    @StateStrategyType(SkipStrategy.class)
    public void getAdsList(JsonObject jsonObject, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<GetListPoster> observable = serverMethod.getListPosterSingle(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListPoster -> {
                    getViewState().hideProgres();
                    if (getListPoster.getResult() > 0) {
                        getViewState().listAds(getListPoster.getItems_list().getProduktList());
                    } else {
                        if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());

                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                jsonObject.remove("SESID");
                                getAdsList(jsonObject, true);
                                setSideMenu();
                            } else {
                                getViewState().sendError(text_error);
                            }
                            // getViewState().sendError(text_error);
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    } else if (throwable instanceof HttpException) {
                        getViewState().sendError(throwable.getMessage());
                    } else {
                        getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                    }

                });
        unsubscribeOnDestroy(subscription);
        Log.d("getAdsList", jsonObject.toString());
    }

    @StateStrategyType(SkipStrategy.class)
    public void update_fav_list(JsonObject jsonObject, boolean is_ad_delete) {
        getViewState().showProgres();
        final Single<UpdateFavorite> observable = serverMethod.update_fav(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateFavorite -> {
                    getViewState().hideProgres();
                    if (updateFavorite.getResult() > 0) {
                        getViewState().sendError((is_ad_delete) ? App.getInstance().getString(R.string.add_fav_ad) : App.getInstance().getString(R.string.delete_fav_ad));
                        AppUtils.update_user_fav(updateFavorite.getItems_fav());
                        setSideMenu();
                    } else {
                        if (updateFavorite.getMessage() != null && updateFavorite.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(updateFavorite.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(updateFavorite.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                getViewState().sendError(App.getInstance().getString(R.string.no_sesid));
                                getViewState().no_session(App.getInstance().getString(R.string.sign_in_to_submit_an_fav));
                                return;
                            }
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    } else if (throwable instanceof HttpException) {
                        getViewState().sendError(throwable.getMessage());
                    } else {
                        getViewState().sendError(App.getInstance().getString(R.string.error_retrieving_data));
                    }

                });
        unsubscribeOnDestroy(subscription);
        Log.d("update_fav_list", jsonObject.toString());

    }
    public void getAppSettings(JsonObject jsonObject) {
        final Single<GetSettings> observable = serverMethod.getSettings(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSettings ->  {
                    getViewState().hideProgres();
                    if (getSettings.getResult() > 0) {
                       AppUtils.setSettings(getSettings.getSettingsApp());
                       getViewState().get_setSettings();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getAppSettings", jsonObject.toString());

    }
    @StateStrategyType(SkipStrategy.class)
    public void setSideMenu() {
        if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN)) {
            getViewState().userLogin();
        } else {
            getViewState().userNotLogin();
        }
    }

    @StateStrategyType(SkipStrategy.class)
    public void user_logout(boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        JsonObject object = JsonObjBody.log_out(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID));
        final Single<CapObject> observable = serverMethod.user_logout(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {
                    getViewState().hideProgres();
                    if (capObject.getResult() > 0) {
                        AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                        AppUtils.clearUserData();
                        setSideMenu();
                        getViewState().userLogOut();

                    } else {
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                setSideMenu();
                                getViewState().userLogOut();
                                return;
                            }
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    } else if (throwable instanceof HttpException) {
                        getViewState().sendError(throwable.getMessage());
                    } else {
                        getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("user_logout", object.toString());
    }
    public void sendPushID() {
        JsonObject jsonObject = JsonObjBody.sendTokenFirebase(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH));
        Single<CapObject> setdFirebaseToken = serverMethod.sendTokenFirebase(jsonObject);
        Disposable subscription = setdFirebaseToken
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {
                }, throwable -> {
                    throwable.printStackTrace();
                });
        unsubscribeOnDestroy(subscription);
        Log.d("sendPushID", jsonObject.toString());
    }
    public void getUserInfo() {
        JsonObject object = JsonObjBody.getMyInfo(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID));
        final Single<GetUserInfo> observable = serverMethod.getInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo -> {
                    if (getUserInfo.getResult() > 0) {
                        AppUtils.setUserData(getUserInfo.getUser());
                        setSideMenu();
                    } else {
                        if (getUserInfo.getMessage() != null && getUserInfo.getMessage().size() > 0) {

                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getUserInfo.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                setSideMenu();
                            }
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getUserInfo", "getUserInfo");
    }
}
