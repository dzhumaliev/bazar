package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;

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
import solutions.isky.gaurangarevolution.data.event.GetBindSocial;
import solutions.isky.gaurangarevolution.data.event.GetCityInfo;
import solutions.isky.gaurangarevolution.data.event.GetUserInfo;
import solutions.isky.gaurangarevolution.data.event.SendImage;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class EditProfilePresenter extends BasePresenter<IEditProfile> {
    User user;
    Uri uri_avatar;
    Bitmap bitmap;
    @Inject
    ServerMethod serverMethod;

    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getRegionId() > 0) {
            getCityInfo(user.getRegionId());
        }
        getViewState().setInitUser(user);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getUserInfo();
    }

    public EditProfilePresenter() {
        App.getComponent().inject(this);
    }

    private void getUserInfo() {
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
                        getViewState().setInitUser(getUserInfo.getUser());
                        user = getUserInfo.getUser();
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

        JsonObject object = JsonObjBody.reg_info(id,AppUtils.getLocale(App.getInstance()));
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
    public void resetAvatar() {
        if (user != null)
            user.setAvatar("");
        getViewState().resetAvatar("");
        sendAvatar("");
    }
    public void setPreview(CropImage.ActivityResult activityResult) {
        uri_avatar = activityResult.getUri();
        bitmap = activityResult.getBitmap();
        getViewState().setPreview(uri_avatar);
        sendImages(uri_avatar);
    }
    public void sendImages(Uri uri) {

        File file = new File(uri.getPath());
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        String origName = file.getName();
        MultipartBody.Part file_body =
                MultipartBody.Part.createFormData("image_avatar", file.getName(), requestFile);
        sendImage(file_body);

    }
    private void sendImage(MultipartBody.Part file_body) {
        getViewState().showProgresAvatar();
        final Single<SendImage> observable = serverMethod.sendIMG(file_body);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> {
                    if (image.getResult() > 0) {
                        getViewState().hideProgresavatar();
                        sendAvatar(image.getImg_files().get(0).getNew_name());
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
    private void sendAvatar(String img) {
        JsonObject jsonObject = JsonObjBody.avatar(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), img);
        final Single<CapObject> observable = serverMethod.sendavatar(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> {
                    if (image.getResult() > 0) {
                        getViewState().avatar_load();
                        updateUser();
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
        Log.d("sendAvatar", jsonObject.toString());
    }
    private void updateUser() {
        JsonObject object = JsonObjBody.getMyInfo(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID));
        final Single<GetUserInfo> observable = serverMethod.getInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo -> {

                    if (getUserInfo.getResult() > 0) {
                        AppUtils.setUserData(getUserInfo.getUser());
                        getViewState().setInitUser(getUserInfo.getUser());
                        user = getUserInfo.getUser();
                    }
                }, throwable -> {

                    throwable.printStackTrace();
                });
        unsubscribeOnDestroy(subscription);
        Log.d("updateUser", object.toString());
    }
    public void unlink_social(String provider_id) {
        getViewState().showProgres();
        JsonObject object = JsonObjBody.unlink_soc(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID),provider_id);
        final Single<GetUserInfo> observable = serverMethod.unlink_social(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo ->  {
                    getViewState().hideProgres();
                    if (getUserInfo.getResult() > 0) {
                        AppUtils.setUserData(getUserInfo.getUser());
                        getViewState().setInitUser(getUserInfo.getUser());
                        user = getUserInfo.getUser();

                    } else {
                        if (getUserInfo.getMessage() != null && getUserInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getUserInfo.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getUserInfo.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                getViewState().sendError(App.getInstance().getString(R.string.no_sesid));
                                return;
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
        Log.d("bind_fb", object.toString());
    }
    public void bind_social(String profile_id, String token,String provider_id) {
        getViewState().showProgres();
        JsonObject object = JsonObjBody.bind_soc(UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID), profile_id, token, UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID),provider_id);
        final Single<GetBindSocial> observable = serverMethod.bind_social(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {
                    getViewState().hideProgres();
                    if (capObject.getResult() > 0) {
                        getUserInfo();

                    } else {
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                getViewState().sendError(App.getInstance().getString(R.string.no_sesid));
                                return;
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
        Log.d("bind_fb", object.toString());
    }
    public void edit_user(String name, JsonArray phones, int region_id) {
        getViewState().showProgres();
        JsonObject object = JsonObjBody.editUser(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), name, phones, region_id);
        final Single<GetUserInfo> observable = serverMethod.editInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo -> {
                    getViewState().hideProgres();
                    if (getUserInfo.getResult() > 0) {
                        AppUtils.setUserData(getUserInfo.getUser());
                        getViewState().setInitUser(getUserInfo.getUser());
                        user = getUserInfo.getUser();
                        getViewState().sendError(App.getInstance().getString(R.string.user_data_save));
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
        Log.d("edit_user", object.toString());
    }
}
