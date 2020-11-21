package solutions.isky.gaurangarevolution.presentation.mvp.adsuser;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
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
import solutions.isky.gaurangarevolution.data.event.UpdateFavorite;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;

@InjectViewState
public class AllAdsUserPresenter extends BasePresenter<IAllUserAds>{

    @Inject
    ServerMethod serverMethod;
    private int mUserId;
    JsonObject mParams = new JsonObject();
    public void setParams(JsonObject params) {
        this.mParams = params;
    }
    public AllAdsUserPresenter() {
        App.getComponent().inject(this);

    }
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (mParams != null)
            getAdsList(mParams, true);
    }
    public void getAdsList(JsonObject jsonObject, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<GetListPoster> observable = serverMethod.getListProdukt(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListPoster -> {
                    getViewState().hideProgres();
                    if (getListPoster.getResult() > 0) {
                        getViewState().setCountrAds(getListPoster.getItems_list().getCount_items(),getListPoster.getItems_list().getUser(),getListPoster.getItems_list().getShopData());
                        getViewState().listAds(getListPoster.getItems_list().getProduktList());
                    } else {
                        if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());

                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                jsonObject.remove("SESID");
                                getAdsList(jsonObject, true);

                            } else {
                                getViewState().sendError(text_error);
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
                        getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getAdsList", jsonObject.toString());
    }

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
}
