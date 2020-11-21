package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import android.text.TextUtils;
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
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class ChangePasswordPresenter extends BasePresenter<IChangePassView> {
    @Inject
    ServerMethod serverMethod;

    public ChangePasswordPresenter() {
        App.getComponent().inject(this);
    }

    public void changePass(String new_pass, String pass) {
        if (!isValidPass(new_pass)) {
            getViewState().showErrorNewPass(App.getInstance().getString(R.string.error_emty_text));
            return;
        }
        if (!isValidPass(pass)) {
            getViewState().showErrorOldPass(App.getInstance().getString(R.string.error_emty_text));
            return;
        }
        JsonObject jsonObject= JsonObjBody.change_password(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID),pass,new_pass);
        changePassword(jsonObject,true);
    }


    private boolean isValidPass(String pass) {
        return !TextUtils.isEmpty(pass);
    }

    public void changePassword(JsonObject jsonObject, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<CapObject> observable = serverMethod.changePassword(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject ->  {
                    getViewState().hideProgres();
                    if (capObject.getResult() > 0) {

                        getViewState().pass_change_ok();
                    } else {
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())||ErrorMessage.NO_PERMISSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                getViewState().no_session();
                                AppUtils.clearUserData();
                            }else if(ErrorMessage.CURRENT_PASSWORD_WRONG.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())){
                                getViewState().showErrorOldPass(text_error);
                            }else if(ErrorMessage.USER_PASSWORD_LENGTH_MISSMATCH.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                getViewState().showErrorNewPass(String.format(App.getInstance().getString(R.string.error_invalid_pass),capObject.getMessage().get(0).getMin_length_pass()));
                            } else{
                                getViewState().showError(text_error);
                            }
                        } else {
                            getViewState().showError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().showError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("changePassword", jsonObject.toString());
    }
}
