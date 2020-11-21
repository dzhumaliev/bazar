package solutions.isky.gaurangarevolution.presentation.mvp.filters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetCategoryList;
import solutions.isky.gaurangarevolution.data.event.GetCountPosters;
import solutions.isky.gaurangarevolution.data.event.GetListPropsDyns;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;

@InjectViewState
public class FilterActivityPresenter extends BasePresenter<IfiltersView> {
    @Inject
    ServerMethod serverMethod;

    public FilterActivityPresenter() {
        App.getComponent().inject(this);
    }

    public void getPostersCount(JsonObject jsonObject) {

        getViewState().showProgres();
        final Single<GetCountPosters> observable = serverMethod.getCountPosters(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCountPosters ->  {
                    getViewState().hideProgres();
                    if (getCountPosters.getResult() > 0) {
                        getViewState().count_posters(getCountPosters.getItems_list().getCount_items());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getCountPoster",jsonObject.toString());
    }
    public void getDinParams(JsonObject jsonObject,boolean necessary_to_fill) {

        getViewState().showProgres();
        final Single<GetListPropsDyns> observable = serverMethod.getDinProps(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListPropsDyns ->   {
                    getViewState().hideProgres();
                    if(getListPropsDyns.getResult()>0){
                        getViewState().getDinProps(getListPropsDyns.getRows(),necessary_to_fill);
                    }else{

                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getDinParams",jsonObject.toString());
    }
}
