package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

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
import solutions.isky.gaurangarevolution.data.event.GetMyPayments;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;

@InjectViewState
public class MyPaymentsPresenter extends BasePresenter<IMyPayments> {
    @Inject
    ServerMethod serverMethod;

    public MyPaymentsPresenter() {
        App.getComponent().inject(this);
    }

    public void get_billins(JsonObject jsonObject, boolean is_progress) {
        if(is_progress){
            getViewState().showProgres();
        }

        final Single<GetMyPayments> observable = serverMethod.getBillings(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMyPayments -> {
                    getViewState().hideProgres();
                    if (getMyPayments.getResult() > 0) {
                        getViewState().setHeader(getMyPayments.getUserBalance().getBalance_display(),getMyPayments.getUserBalance().getBalance_bonus());
                        getViewState().listPayments(getMyPayments.getUserBalance().getItemPayments());

                    } else {
                        if (getMyPayments.getMessage() != null && getMyPayments.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getMyPayments.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getMyPayments.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                getViewState().sendError(App.getInstance().getString(R.string.no_sesid));
                                getViewState().no_session();
                                return;
                            }
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().sendError(App.getInstance().getString(R.string.error_retrieving_data));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("get_billins", jsonObject.toString());

    }
}
