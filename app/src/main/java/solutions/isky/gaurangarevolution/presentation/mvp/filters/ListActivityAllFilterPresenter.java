package solutions.isky.gaurangarevolution.presentation.mvp.filters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

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
import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class ListActivityAllFilterPresenter extends BasePresenter<IListActivityFilterView> {
    private String mSort;
    private int mCategId;
    @NonNull
    private int mP_more = 0;
    @NonNull
    private int mP_less = 0;
    private String mTextSearch;
    private int mCityID;
    private int mIs_photo = 0;
    private String mCityname;
    private String mCategTitle;
    private int mOwner;
    private int PAGE_SIZE = 20;
    private int PAGE;
    JsonArray mProps = new JsonArray();
    List<AdItem> adInfos = new ArrayList<>();

    public JsonArray getmProps() {
        return mProps;
    }

    public void setmProps(JsonArray mProps) {
        this.mProps = mProps;
    }

    public int getPAGE_SIZE() {
        return PAGE_SIZE;
    }

    public void setPAGE_SIZE(int PAGE_SIZE) {
        this.PAGE_SIZE = PAGE_SIZE;
    }

    public int getPAGE() {
        return PAGE;
    }

    public void setPAGE(int PAGE) {
        Log.d("setPAGE", PAGE + "");
        this.PAGE = PAGE;
    }

    @NonNull
    public int getmP_more() {
        return mP_more;
    }

    public void setmP_more(@NonNull int mP_more) {
        this.mP_more = mP_more;
    }

    @NonNull
    public int getmP_less() {
        return mP_less;
    }

    public void setmP_less(@NonNull int mP_less) {
        this.mP_less = mP_less;
    }

    @Inject
    ServerMethod serverMethod;

    public String getmSort() {
        return mSort;
    }

    public int getmCategId() {
        return mCategId;
    }

    public String getmTextSearch() {
        return mTextSearch;
    }

    public int getmCityID() {
        return mCityID;
    }

    public int getmIs_photo() {
        return mIs_photo;
    }

    public String getmCityname() {
        return mCityname;
    }

    public String getmCategTitle() {
        return mCategTitle;
    }

    public int getmOwner() {
        return mOwner;
    }


    public void setmSort(String mSort) {
        this.mSort = mSort;
    }

    public void setmCategId(int mCategId) {
        this.mCategId = mCategId;
    }

    public void setmTextSearch(String mTextSearch) {
        this.mTextSearch = mTextSearch;
    }

    public void setmCityID(int mCityID) {
        this.mCityID = mCityID;
    }

    public void setmIs_photo(int mIs_photo) {
        this.mIs_photo = mIs_photo;
    }

    public void setmCityname(String mCityname) {
        this.mCityname = mCityname;
    }

    public void setmCategTitle(String mCategTitle) {
        this.mCategTitle = mCategTitle;
    }

    public void setmOwner(int mOwner) {
        this.mOwner = mOwner;
    }


    public ListActivityAllFilterPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        PAGE = 0;
        getAdsList(true);
    }

    private JsonObject getJson() {
        JsonObject params = JsonObjBody.getList(PAGE, PAGE_SIZE, mSort, mCategId, mTextSearch, mCityID, mOwner, mProps, mIs_photo, mP_less, mP_more, AppUtils.getLocale(App.getInstance()));
        return params;
    }
    public void getAdsList(boolean showProgress) {

        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<GetListPoster> observable = serverMethod.getListProdukt(getJson());
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListPoster -> {
                    getViewState().hideProgres();
                    if (getListPoster.getResult() > 0) {
                        if (PAGE == 0) {
                           // adInfos.clear();
                        }
                        if (getListPoster.getItems_list().getProduktList().size() < PAGE_SIZE) {
                            //getViewState().setLastPage(true);
                        } else {
                           // getViewState().setLastPage(false);
                        }
                        //adInfos.addAll(getListPoster.getItems_list().getProduktList());
                        getViewState().listAds(getListPoster.getItems_list().getProduktList());
                    } else {
                        if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                getAdsList(true);
                            } else {
                                getViewState().showError(text_error);
                            }
                        } else {
                            getViewState().showError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    } else if (throwable instanceof HttpException) {
                        getViewState().showError(throwable.getMessage());
                    } else {
                        getViewState().showError(App.getInstance().getString(R.string.unknown_error));
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getAdsList", getJson().toString());
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
                        getViewState().showError((is_ad_delete) ? App.getInstance().getString(R.string.add_fav_ad) : App.getInstance().getString(R.string.delete_fav_ad));
                        AppUtils.update_user_fav(updateFavorite.getItems_fav());

                    } else {
                        if (updateFavorite.getMessage() != null && updateFavorite.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(updateFavorite.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().showError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(updateFavorite.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                getViewState().showError(App.getInstance().getString(R.string.no_sesid));
                                getViewState().no_session(App.getInstance().getString(R.string.sign_in_to_submit_an_fav));
                                return;
                            }
                        } else {
                            getViewState().showError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    } else if (throwable instanceof HttpException) {
                        getViewState().showError(throwable.getMessage());
                    } else {
                        getViewState().showError(App.getInstance().getString(R.string.error_retrieving_data));
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("update_fav_list", jsonObject.toString());

    }
}
