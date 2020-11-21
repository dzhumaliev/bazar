package solutions.isky.gaurangarevolution.presentation.mvp.info_ad;

import android.content.Context;
import android.content.Intent;
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
import solutions.isky.gaurangarevolution.data.event.GetAdInfo;
import solutions.isky.gaurangarevolution.data.event.UpdateFavorite;
import solutions.isky.gaurangarevolution.data.models.AdInfoItem;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.ui.info_ad.AdMapPosition;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class InfoAdPresenter extends BasePresenter<IInfoAdView> {

    public static String UNPUBLICATE = "unpublicate";
    public static String PUBLICATE = "publicate";
    public static String REFRESH = "refresh";
    public static String DELETE = "delete";
    @Inject
    ServerMethod serverMethod;
    int mId=0;
    public InfoAdPresenter() {
        App.getComponent().inject(this);
    }
    public void setId(int id){
        mId=id;
    }
    public int getmId(){
        return mId;
    }
    @Override protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getFirstInfoPost();
    }
    public void getFirstInfoPost(){
        if(mId!=0){
            JsonObject infoPost = JsonObjBody.getAdInfo(mId, AppUtils.getLocale(App.getInstance()));
            getInfoPost(infoPost);
        }
    }

    public void getInfoPost(JsonObject jsonObject) {
        getViewState().hide_content();
        getViewState().showProgres();
        final Single<GetAdInfo> observable=serverMethod.getInfoAd(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAdInfo -> {
                    getViewState().hideProgres();
                    if(getAdInfo.getResult()>0){
                        getViewState().show_content();
                        getViewState().getInfoPost(getAdInfo);

                    }else{
                        if (getAdInfo.getMessage() != null && getAdInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getAdInfo.getMessage().get(0).getMsg(), App.getInstance());
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getAdInfo.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                if(mId!=0){
                                    JsonObject infoPost = JsonObjBody.getAdInfo(mId, AppUtils.getLocale(App.getInstance()));
                                    getInfoPost(infoPost);
                                }

                            }else{
                                getViewState().show_error(text_error);
                            }
                        } else {
                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                        }
                    }

                },throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().show_error(App.getInstance().getString(R.string.error_retrieving_data));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("InfoAdPresenter", jsonObject.toString());

    }
    public void update_fav_list(JsonObject jsonObject,boolean is_ad_delete) {
        getViewState().showProgres();
        final Single<UpdateFavorite> observable=serverMethod.update_fav(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateFavorite ->  {
                    getViewState().hideProgres();
                    if(updateFavorite.getResult()>0){
                        getViewState().update_favorite(is_ad_delete);
                        AppUtils.update_user_fav(updateFavorite.getItems_fav());
                    }else {
                        if (updateFavorite.getMessage() != null && updateFavorite.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(updateFavorite.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().show_error(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(updateFavorite.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                getViewState().show_error(App.getInstance().getString(R.string.no_sesid));
                                getViewState().no_session(App.getInstance().getString(R.string.sign_in_to_submit_an_fav));
                                return;
                            }
                        } else {
                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                },throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().show_error(App.getInstance().getString(R.string.error_retrieving_data));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("update_fav_list", jsonObject.toString());

    }
    public void sendClickPhone(JsonObject jsonObject) {

        final Single<CapObject> observable=serverMethod.sendClickPhone(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAdInfo -> {
                },throwable -> {
                    throwable.printStackTrace();
                });
        unsubscribeOnDestroy(subscription);
        Log.d("sendClickPhone", jsonObject.toString());

    }
    public void openMap(Context ctx, double lat, double lng, String  title){
        Intent intent=new Intent(ctx, AdMapPosition.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        intent.putExtra("title", title);
        getViewState().openMap(intent);
    }


    public void editPost(AdInfoItem adInfoItem){
        getViewState().edit_ad(adInfoItem);
    }

//    public void item_freeup(JsonObject jsonObject,AdInfoItem adInfoItem, boolean showProgress) {
//        if (showProgress) {
//            getViewState().showProgres();
//        }
//        final Observable<CapObject> observable = serverMethod.item_freeup(jsonObject);
//        Disposable subscription = observable
//                .subscribeOn(Schedulers.newThread())
//                .retry(2)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(getListPoster -> {
//                    getViewState().hideProgres();
//                    if (getListPoster.getResult() > 0) {
//                        getViewState().show_error(App.getInstance().getString(R.string.item_up_free));
//                        adInfoItem.setUp_free(0);
//                        getViewState().freeup(adInfoItem);
//                    } else {
//                        if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
//                            String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());
//                            getViewState().show_error(text_error);
//                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())) {
//                                getViewState().no_session(App.getInstance().getString(R.string.no_session));
//                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
//                                AppUtils.clearUserData();
//                            }
//                        } else {
//                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
//                        }
//                    }
//                }, throwable -> {
//                    throwable.printStackTrace();
//                    getViewState().hideProgres();
//                    getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
//                });
//        unsubscribeOnDestroy(subscription);
//        Log.d("item_freeup", jsonObject.toString());
//    }
//    public void item_change_status(JsonObject jsonObject, boolean showProgress, AdInfoItem adInfoItem, String stat) {
//        if (showProgress) {
//            getViewState().showProgres();
//        }
//        final Observable<CapObject> observable = serverMethod.item_change_status(jsonObject);
//        Disposable subscription = observable
//                .subscribeOn(Schedulers.newThread())
//                .retry(2)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(getListPoster -> {
//                    getViewState().hideProgres();
//                    if (getListPoster.getResult() > 0) {
//                        // getViewState().deactive_ad(adItemMy);
//                        if (stat.equalsIgnoreCase(DELETE)) {
//                            getViewState().show_error(App.getInstance().getString(R.string.item_delete));
//                            getViewState().delete();
//                        } else if (stat.equalsIgnoreCase(UNPUBLICATE)) {
//                            getViewState().show_error(App.getInstance().getString(R.string.item_deactivate));
//                            adInfoItem.setStatus("4");
//                            getViewState().unpublic(adInfoItem);
//
//                        } else if (stat.equalsIgnoreCase(PUBLICATE)) {
//                            getViewState().show_error(App.getInstance().getString(R.string.item_activate));
//                            adInfoItem.setStatus("3");
//                            getViewState().publicate(adInfoItem);
//                        }
//
//                    } else {
//                        if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
//                            String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());
//                            getViewState().show_error(text_error);
//                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())) {
//                                getViewState().no_session(App.getInstance().getString(R.string.no_session));
//                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
//                                AppUtils.clearUserData();
//                            }
//                        } else {
//                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
//                        }
//                    }
//                }, throwable -> {
//                    throwable.printStackTrace();
//                    getViewState().hideProgres();
//                    getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
//                });
//        unsubscribeOnDestroy(subscription);
//        Log.d("item_freeup", jsonObject.toString());
//    }

    public void sendMess(JsonObject jsonObject) {
        getViewState().showProgres();
        final Single<CapObject> observable = serverMethod.send_mess(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {
                    getViewState().hideProgres();
                    getViewState().enabledBtnSend();
                    if (capObject.getResult() > 0) {
                        getViewState().show_error(App.getInstance().getString(R.string.mes_send));
                        getViewState().message_send();
                    } else {
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());

                            if (ErrorMessage.MAIL_TIMELIMIT.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                getViewState().show_error(String.format(App.getInstance().getString(R.string.mail_time_limit),capObject.getMessage().get(0).getDtime()));
                            }else{
                                getViewState().show_error(text_error);
                            }
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                getViewState().no_session(App.getInstance().getString(R.string.no_session));
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }
                        } else {
                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                },throwable -> {
                    getViewState().enabledBtnSend();
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    } else if (throwable instanceof HttpException) {
                        getViewState().show_error(throwable.getMessage());
                    } else {
                        getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                    }

                });
        unsubscribeOnDestroy(subscription);
        Log.d("item_freeup", jsonObject.toString());
    }
    public void setComplain(int id_post,int id, String mess) {
        JsonObject jsonObject = JsonObjBody.setComplain(id_post, id, mess);
        getViewState().showProgres();
        final Single<CapObject> observable = serverMethod.send_complain(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {
                    getViewState().hideProgres();
                    if (capObject.getResult() > 0) {
                        getViewState().complain_confirm();
                    } else {
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                getViewState().no_session(App.getInstance().getString(R.string.no_session));
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }else{
                                getViewState().show_error(text_error);
                            }
                        } else {
                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                },throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    } else if (throwable instanceof HttpException) {
                        getViewState().show_error(throwable.getMessage());
                    } else {
                        getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("setComplain", jsonObject.toString());
    }
}
