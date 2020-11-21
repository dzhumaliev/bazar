package solutions.isky.gaurangarevolution.presentation.mvp.category;

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
import solutions.isky.gaurangarevolution.data.event.GetCategoryList;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class CategoryActivityPresenter extends BasePresenter<ICategoryView> {

    @Inject
    ServerMethod serverMethod;

    public CategoryActivityPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getCategoryList(JsonObjBody.getListCateg(AppUtils.getLocale(App.getInstance())));
    }

    public void getCategoryList(JsonObject jsonObject) {
        getViewState().showProgres();
        final Single<GetCategoryList> observable = serverMethod.getCategoryList(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCategoryList -> {
                    getViewState().listCategory(getCategoryList.getCategList().getRows());
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
