package solutions.isky.gaurangarevolution.presentation.mvp.addad;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
import solutions.isky.gaurangarevolution.data.event.AddPosterSuccess;
import solutions.isky.gaurangarevolution.data.event.EditInfo;
import solutions.isky.gaurangarevolution.data.event.GetAdInfo;
import solutions.isky.gaurangarevolution.data.event.GetCityInfo;
import solutions.isky.gaurangarevolution.data.event.GetListPropsDyns;
import solutions.isky.gaurangarevolution.data.event.GetUserInfo;
import solutions.isky.gaurangarevolution.data.event.SendImage;
import solutions.isky.gaurangarevolution.data.models.PeriodItem;
import solutions.isky.gaurangarevolution.data.models.PriceEx;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.adapter.PeriodAdapter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.DinParamsAddAdLayout;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class AddAdPresenter extends BasePresenter<IAddAdView> {
    private PriceEx priceEx;
    @Inject
    ServerMethod serverMethod;
    private int curprice=-1;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getUserInfo();
    }
    public AddAdPresenter() {
        App.getComponent().inject(this);
    }

        private void getUserInfo() {
        JsonObject object = JsonObjBody.getMyInfo(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID));
        final Single<GetUserInfo> observable = serverMethod.getInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo -> {
                    if (getUserInfo.getResult() > 0) {
                        if (getUserInfo.getUser().getPhone_number_verified() == 0) {
                            getViewState().number_not_verified(getUserInfo.getUser());
                        } else {
                            AppUtils.setUserData(getUserInfo.getUser());
                        }

                    } else {
                        if (getUserInfo.getMessage() != null && getUserInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getUserInfo.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getUserInfo.getMessage().get(0).getMsg())) {
                                getViewState().no_session(App.getInstance().getString(R.string.sign_in_to_submit_an_ad));
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
        Log.d("getUserInfo", "getUserInfo");
    }
    public void getInfoPost(JsonObject jsonObject, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<GetAdInfo> observable = serverMethod.getInfoAd(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAdInfo -> {
                    getViewState().hideProgres();
                    getViewState().getInfoPost(getAdInfo);
                    curprice=getAdInfo.getAdInfoItem().getPriceCurr();
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().sendError(App.getInstance().getString(R.string.error_retrieving_data));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("InfoAdPresenter", jsonObject.toString());

    }

    public void sendImages(List<LocalMedia> mCheackPhotos) {

        int size = mCheackPhotos.size();
        for (int i = 0; i < size; i++) {
            if (!mCheackPhotos.get(i).getPath().contains("http")) {
                Uri uri = Uri.parse(mCheackPhotos.get(i).getPath());
                File file = new File(uri.getPath());
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part file_body =
                        MultipartBody.Part.createFormData("image" + i, file.getName(), requestFile);
                if (TextUtils.isEmpty(mCheackPhotos.get(i).getNew_path())) {
                    sendImage(file_body, i, mCheackPhotos, mCheackPhotos.get(i));
                }
            }
        }
    }

    private void sendImage(MultipartBody.Part file_body, int position, List<LocalMedia> mCheackPhotos, LocalMedia media) {

        final Single<SendImage> observable = serverMethod.sendIMG(file_body);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> {
                    if (image.getResult() > 0) {
                        if (mCheackPhotos.contains(media)) {
                            int i = mCheackPhotos.indexOf(media);
                            mCheackPhotos.remove(i);
                            media.setNew_path(image.getImg_files().get(0).getNew_name());
                            media.setIs_progress(false);
                            mCheackPhotos.add(i, media);
                        }
                        getViewState().image_upload(mCheackPhotos);
                    } else {
                        mCheackPhotos.remove(media);
                        getViewState().image_upload(mCheackPhotos);
                        if (image.getMessage() != null && image.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(image.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(image.getMessage().get(0).getMsg())) {
                                getViewState().no_session(App.getInstance().getString(R.string.no_session));
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

    public void getDinProps(JsonObject jsonObject, boolean showProgress) {
        getDinProps(jsonObject, showProgress, false);
    }

    public void getDinProps(JsonObject jsonObject, boolean showProgress, boolean is_edit) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<GetListPropsDyns> observable = serverMethod.getDinProps(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListPropsDyns -> {
                    getViewState().hideProgres();
                    if (getListPropsDyns.getResult() > 0) {

                        if (is_edit) {
                            getViewState().setCategParam(getListPropsDyns.getCategoryList(),curprice);
                        }
                        getViewState().getDinProps(getListPropsDyns.getRows(),getListPropsDyns.getCategoryList().getTpl_title_enabled());
                        getViewState().currency_list(getListPropsDyns.getCurrencyList());
                    } else {
                        if (getListPropsDyns.getMessage() != null && getListPropsDyns.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getListPropsDyns.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
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
        Log.d("getDinProps", jsonObject.toString());
    }

    public void getCityInfo(int id) {
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

    public void setPriceEx(PriceEx priceEx) {
        this.priceEx = priceEx;
    }

    public void setBlockPrice(int price_cur) {
        getViewState().setBlockPriceData(priceEx,price_cur);
    }

    public void setParams(int tpl_enabled,String title, String descr, String category, String location, String email, JsonArray phones, String name,
                          String adress, DinParamsAddAdLayout dinamic_params, int mCategId, int mCityId, JsonArray price_ex, int price_curr,
                          String price, JsonArray jsonArray, int id, double lat, double lng,int days) {
        setParamsShop(tpl_enabled,title, descr, category, location, email, phones, name,
                adress, dinamic_params, mCategId, mCityId, price_ex, price_curr,
                price, jsonArray, id, lat, lng, 0,days);
    }

    public void setParamsShop(int tpl_enabled,String title, String descr, String category, String location, String email, JsonArray phones, String name,
                              String adress, DinParamsAddAdLayout dinamic_params, int mCategId, int mCityId, JsonArray price_ex, int price_curr,
                              String price, JsonArray jsonArray, int id, double lat, double lng, int shop_id,int days) {
        getViewState().showProgres();
        boolean isError = false;
        if(tpl_enabled==0){
            if (!validTitle(title)) {
                getViewState().showErrorTitle(App.getInstance().getString(R.string.error_title));
                getViewState().hideProgres();
                isError = true;
            }
        }

        if (!validDescr(descr)) {
            getViewState().showErrorDescr(App.getInstance().getString(R.string.error_descr));
            getViewState().hideProgres();
            isError = true;
        }
        if (!validTextEmpty(category)) {
            getViewState().showErrorCateg(App.getInstance().getString(R.string.error_emty_text));
            getViewState().hideProgres();
        }
        if (!validTextEmpty(location)) {
            getViewState().showErrorLocation(App.getInstance().getString(R.string.error_emty_text));
            getViewState().hideProgres();
            isError = true;
        }
//        if (!validTextEmpty(email)) {
//            getViewState().showErrorEmail(App.getInstance().getString(R.string.error_emty_text));
//            getViewState().hideProgres();
//            isError = true;
//        }
//        if (!validTextEmpty(phone)) {
//            getViewState().showErrorPhone(App.getInstance().getString(R.string.error_emty_text));
//            getViewState().hideProgres();
//            isError = true;
//        }
//        if (phone.length()<5) {
//            getViewState().showErrorPhone(App.getInstance().getString(R.string.phone_number_wrong));
//            getViewState().hideProgres();
//            isError = true;
//        }
        if (adress != null) {
            if (!validTextEmpty(adress)) {
                getViewState().showErrorAdress(App.getInstance().getString(R.string.error_emty_text));
                getViewState().hideProgres();
                isError = true;
            }
        }
        if (!validTextEmpty(name)) {
            getViewState().showErrorName(App.getInstance().getString(R.string.error_emty_text));
            getViewState().hideProgres();
            isError = true;
        }
        isError = dinamic_params.collection_of_dynparams(isError);
        JsonObject dyn = dinamic_params.getDyn();
        if (isError) {
            getViewState().sendError(App.getInstance().getString(R.string.error_emty_fild));
            getViewState().hideProgres();
        } else {
            if (id > 0) {
                JsonObject object = JsonObjBody.editPoster(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), mCategId, title,
                    descr, mCityId, dyn, name, phones, price_ex, price_curr, price, jsonArray, id, lat, lng,adress);
                editPoster(object, false);
            } else {
                JsonObject object = JsonObjBody.addNewPoster(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), mCategId, title,
                    descr, mCityId, dyn, name, phones, price_ex, price_curr, price, jsonArray, lat, lng,adress, shop_id,days);
                addNewPoster(object, false);
            }

        }
        //проверка два
    }

    private boolean validTitle(String text) {
        if (text.length() > 4 && text.length() <= 70) {
            return true;
        }
        return false;
    }

    private boolean validDescr(String text) {
        if (text.length() > 11 && text.length() <= 1024) {
            return true;
        }
        return false;
    }

    private boolean validTextEmpty(String text) {
        if (text.length() > 0) {
            return true;
        }
        return false;
    }

    public void addNewPoster(JsonObject code, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<AddPosterSuccess> observable = serverMethod.addPoster(code);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addPosterOk -> {
                    getViewState().hideProgres();
                    if (addPosterOk.getResult() > 0) {
                        getViewState().add_successfully_submitted(addPosterOk.getAddInfoPost().getItem_link());
                    } else {
                        if (addPosterOk.getMessage() != null && addPosterOk.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(addPosterOk.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(addPosterOk.getMessage().get(0).getMsg())) {
                                getViewState().no_session(App.getInstance().getString(R.string.sign_in_to_submit_an_ad));
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
        Log.d("addNewPoster", code.toString());
    }

    public void editPoster(JsonObject code, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<EditInfo> observable = serverMethod.editPoster(code);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {
                    getViewState().hideProgres();
                    if (capObject.getResult() > 0) {
                        getViewState().add_successfully_edit();
                    } else {
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                getViewState().no_session(App.getInstance().getString(R.string.sign_in_to_submit_an_ad));
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
        Log.d("editPoster", code.toString());
    }

    public void initPeriod(){

            Type type = new TypeToken<HashMap<String, PeriodItem>>() {
            }.getType();
            HashMap<String, PeriodItem> periodMap = new Gson().fromJson(AppState.getInstance(App.getInstance()).getString(AppState.Key.DATA_PERIODS), type);

            if (periodMap != null) {
                List<PeriodItem>periodItems=new ArrayList<>(periodMap.values());
                Collections.sort(periodItems, new Comparator<PeriodItem>() {
                    @Override
                    public int compare(PeriodItem lhs, PeriodItem rhs) {

                        return lhs.getDays() < rhs.getDays() ? -1 : (lhs.getDays() > rhs.getDays()) ? 1 : 0;
                    }
                });
                getViewState().setPeriodPublicate(periodItems);
            } else {
                getViewState().setPeriodPublicate(new ArrayList<>());

            }

    }
}
