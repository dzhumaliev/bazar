package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;
import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetMyPoster;
import solutions.isky.gaurangarevolution.data.models.AdItemMy;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;

@InjectViewState
public class MyAdPresenterActive extends BasePresenter<IMyAdsView> {
    public static String UNPUBLICATE = "unpublicate";
    public static String PUBLICATE = "publicate";
    public static String REFRESH = "refresh";
    public static String DELETE = "delete";

    JsonObject mParams = new JsonObject();

    @Inject
    ServerMethod serverMethod;

    public void setParams(JsonObject params) {
        this.mParams = params;
    }

    public MyAdPresenterActive() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (mParams != null){
            getMyAds(mParams, true);
        }
        // getMyAds(mParams, true);
    }


    public void getMyAds(JsonObject jsonObject, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<GetMyPoster> observable = serverMethod.getMyProdukt(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListPoster -> {
                    getViewState().hideProgres();
                    if (getListPoster.getResult() > 0) {
                        getViewState().listAds(getListPoster.getItems_list().getProduktList());
                        //getViewState().setLimits((getListPoster.getItems_list().getLimitsPayed() > 0) ? View.VISIBLE : View.GONE);
                        if (getListPoster.getItems_list().getMyCounters() != null) {
//                            getViewState().setTitlePage(getListPoster.getItems_list().getMyCounters().getActive(), getListPoster.getItems_list().getMyCounters().getNot_active(),
//                                    getListPoster.getItems_list().getMyCounters().getShop_active(), getListPoster.getItems_list().getMyCounters().getShop_not_active());
                        }
                    } else {
                        if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())) {
                                getViewState().no_session();
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getMyAds", jsonObject.toString());
    }
    public void item_freeup(JsonObject jsonObject, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<CapObject> observable = serverMethod.item_freeup(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListPoster -> {
                    getViewState().hideProgres();
                    if (getListPoster.getResult() > 0) {
                        getViewState().sendError(App.getInstance().getString(R.string.item_up_free));
                    } else {
                        if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())) {
                                getViewState().no_session();
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("item_freeup", jsonObject.toString());
    }
    public void item_change_status(JsonObject jsonObject, boolean showProgress, AdItemMy adItemMy, String stat) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<CapObject> observable = serverMethod.item_change_status(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListPoster -> {
                    getViewState().hideProgres();
                    if (getListPoster.getResult() > 0) {
                        if (stat.equalsIgnoreCase(DELETE)) {
                            getViewState().sendError(App.getInstance().getString(R.string.item_delete));
                        } else if (stat.equalsIgnoreCase(UNPUBLICATE)) {
                            getViewState().sendError(App.getInstance().getString(R.string.item_deactivate));
                        } else if (stat.equalsIgnoreCase(PUBLICATE)) {
                            getViewState().sendError(App.getInstance().getString(R.string.item_activate));
                        }else if (stat.equalsIgnoreCase(REFRESH)) {
                            getViewState().sendError(App.getInstance().getString(R.string.refresh_add));
                        }
                        getViewState().deactive_ad(adItemMy);


                    } else {
                        if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())) {
                                getViewState().no_session();
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("item_change_status", jsonObject.toString());
    }
    public void updater(JsonObject jsonObject, boolean showProgress, int is_active) {
        getMyAds(jsonObject, showProgress);
        getViewState().if_need_update(is_active);
    }
}
