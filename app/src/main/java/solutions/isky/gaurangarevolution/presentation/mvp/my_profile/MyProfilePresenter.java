package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

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
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class MyProfilePresenter extends BasePresenter<IProfileView> {

    @Inject
    ServerMethod serverMethod;

    public MyProfilePresenter() {
        App.getComponent().inject(this);
    }

    public void user_logout(boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        JsonObject object = JsonObjBody.log_out(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID));
        final Single<CapObject> observable = serverMethod.user_logout(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {
                    getViewState().hideProgres();
                    if (capObject.getResult() > 0) {
                        AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                        AppUtils.clearUserData();
                        getViewState().userLogOut();

                    } else {
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                                getViewState().userLogOut();
                                return;
                            }
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    if (throwable instanceof NoNetworkException) {
                        getViewState().showNoNetworkLayout();
                    } else if (throwable instanceof HttpException) {
                        getViewState().sendError(throwable.getMessage());
                    } else {
                        getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                    }
                });
        unsubscribeOnDestroy(subscription);
        Log.d("user_logout", object.toString());
    }
}
