package solutions.isky.gaurangarevolution.presentation.mvp.category;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetCategoryList;
import solutions.isky.gaurangarevolution.data.event.GetShopCategoryList;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class CategoryShopActivityPresenter extends BasePresenter<IShopCategoryView> {

    @Inject
    ServerMethod serverMethod;

    public CategoryShopActivityPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getCategoryList(JsonObjBody.getShopListCateg(AppUtils.getLocale(App.getInstance())));
    }

    public void getCategoryList(JsonObject jsonObject) {
        getViewState().showProgres();
        final Single<GetShopCategoryList> observable = serverMethod.getShopCategoryList(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getShopCategoryList -> {
                    getViewState().listCategory(getShopCategoryList.getItemShopCategoryLists());
                    getViewState().hideProgres();
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getCategoryList", jsonObject.toString());
    }
}
