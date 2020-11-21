package solutions.isky.gaurangarevolution.presentation.mvp.my_shop;

import android.net.Uri;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetCityInfo;
import solutions.isky.gaurangarevolution.data.event.GetUserInfo;
import solutions.isky.gaurangarevolution.data.event.SendImage;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.CreateShop;
import solutions.isky.gaurangarevolution.data.models.EditShop;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class MyShopPresenter extends BasePresenter<IMyShopView> {

    @Inject
    ServerMethod serverMethod;

    public MyShopPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getUserInfo();
    }

    public void getUserInfo() {
        getViewState().showProgres();
        JsonObject object = JsonObjBody.getMyInfo(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID));
        final Single<GetUserInfo> observable = serverMethod.getInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo -> {
                    getViewState().hideProgres();
                    if (getUserInfo.getResult() > 0) {
                        AppUtils.setUserData(getUserInfo.getUser());
                        if (getUserInfo.getUser().getShopData() != null) {
                            getViewState().setInitShop(getUserInfo.getUser().getShopData());
                            if (getUserInfo.getUser().getShopData().getRegion_id() > 0) {
                                getCityInfo(getUserInfo.getUser().getShopData().getRegion_id());
                            }
                        }
                    } else {
                        if (getUserInfo.getMessage() != null && getUserInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getUserInfo.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getUserInfo.getMessage().get(0).getMsg())) {
                                getViewState().no_session();
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    getViewState().hideProgres();
                    throwable.printStackTrace();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getUserInfo", "getUserInfo");
    }

    private void getCityInfo(int id) {

        JsonObject object = JsonObjBody.reg_info(id, AppUtils.getLocale(App.getInstance()));
        final Single<GetCityInfo> observable = serverMethod.cityInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCityInfo -> {
                    if (getCityInfo.getResult() > 0) {
                        getViewState().setCityInfo(getCityInfo.getRegionInfo());
                    } else {
                        if (getCityInfo.getMessage() != null && getCityInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getCityInfo.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {

                    throwable.printStackTrace();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getCityInfo", object.toString());
    }

    public void createShop(CreateShop shopData) {
        final Single<CapObject> observable = serverMethod.createShop(shopData);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCityInfo -> {
                    if (getCityInfo.getResult() > 0) {
                        getViewState().create_shop(shopData.getShop_open().getTitle());
                    } else {
                        if (getCityInfo.getMessage() != null && getCityInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getCityInfo.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {

                    throwable.printStackTrace();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("createShop", shopData.toString());
    }

    public void editShop(EditShop editShop) {
        final Single<CapObject> observable = serverMethod.createShop(editShop);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCityInfo -> {
                    if (getCityInfo.getResult() > 0) {
                        getViewState().edit_shop(editShop.getShop_edit().getTitle());
                    } else {
                        if (getCityInfo.getMessage() != null && getCityInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getCityInfo.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {

                    throwable.printStackTrace();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("createShop", editShop.toString());
    }
    public void resetAvatar() {
        getViewState().resetAvatar("");
    }

    public void sendImages(Uri uri) {

        File file = new File(uri.getPath());
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        String origName = file.getName();
        MultipartBody.Part file_body =
                MultipartBody.Part.createFormData("image_avatar", file.getName(), requestFile);
        getViewState().showProgresAvatar();
        final Single<SendImage> observable = serverMethod.sendIMG(file_body);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> {
                    if (image.getResult() > 0) {
                        getViewState().hideProgresavatar();
                        getViewState().getNewUriLogo(image.getImg_files().get(0).getNew_name());
                    } else {
                        if (image.getMessage() != null && image.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(image.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(image.getMessage().get(0).getMsg())) {
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
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("sendImafe", "sendImafe");

    }
}
