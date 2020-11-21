package solutions.isky.gaurangarevolution.presentation.mvp.main;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import javax.inject.Inject;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetShopsList;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;

@InjectViewState
public class ShopsPresenter extends BasePresenter<IShopView> {
    @Inject
    ServerMethod serverMethod;
    public ShopsPresenter() {
        App.getComponent().inject(this);
    }
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    public void getShops(JsonObject jsonObject, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<GetShopsList> observable = serverMethod.getShops(jsonObject);
        Disposable subscription = observable
            .subscribeOn(Schedulers.newThread())
            .retry(2)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getListPoster -> {
                getViewState().hideProgres();
                if (getListPoster.getResult() > 0) {
                    getViewState().listAds(getListPoster.getShopData());
                } else {
                    if (getListPoster.getMessage() != null && getListPoster.getMessage().size() > 0) {
                        String text_error = ErrorMessage.getError(getListPoster.getMessage().get(0).getMsg(), App.getInstance());
                        if(ErrorMessage.SHOPS_NOT_FOUND.equalsIgnoreCase(getListPoster.getMessage().get(0).getMsg())){
                            getViewState().listAds(new ArrayList<ShopData>());
                        }else{
                            getViewState().sendError(text_error);
                        }
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
}
