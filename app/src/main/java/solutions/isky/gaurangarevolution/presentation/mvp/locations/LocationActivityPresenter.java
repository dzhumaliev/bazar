package solutions.isky.gaurangarevolution.presentation.mvp.locations;

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
import solutions.isky.gaurangarevolution.data.event.GetListLocation;
import solutions.isky.gaurangarevolution.data.models.ItemLocation;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;

@InjectViewState
public class LocationActivityPresenter extends BasePresenter<ILocationActivityView> {

    @Inject
    ServerMethod serverMethod;

    public LocationActivityPresenter() {
        App.getComponent().inject(this);
    }

    public void getListLocation(JsonObject jsonObject, String subtitle, boolean isImg) {

        getViewState().showProgres();
        final Single<GetListLocation> observable = serverMethod.getListLocation(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .doOnSuccess(getListLocation -> {
                    if(getListLocation.getResult()>0){
                        for (ItemLocation location:getListLocation.getRegion_list().getRows()){
                            location.setSubtitle(subtitle);
                            location.setIz_img(isImg);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getListLocation -> {
                    getViewState().hideProgres();
                    if (getListLocation.getResult() > 0) {
                        getViewState().getListLocation(getListLocation);
                    }else{
                        if (getListLocation.getMessage() != null && getListLocation.getMessage().size() > 0) {
                            String text_error= ErrorMessage.getError(getListLocation.getMessage().get(0).getMsg(),App.getInstance());
                            getViewState().show_error(text_error);
                        } else {
                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                },throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getListLocation",jsonObject.toString());
    }
}
