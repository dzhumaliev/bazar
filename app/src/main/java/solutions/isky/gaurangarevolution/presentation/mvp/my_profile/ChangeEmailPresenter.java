package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import android.text.TextUtils;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class ChangeEmailPresenter extends BasePresenter<IChangeEmailView> {

    @Inject
    ServerMethod serverMethod;

    public ChangeEmailPresenter() {
        App.getComponent().inject(this);
    }

    public void changeEmail(String email, String pass) {
        if (!isValidEmail(email)) {
            getViewState().showErrorNewEmail(App.getInstance().getString(R.string.error_invalid_email));
            return;
        }
        if (!isValidPass(pass)) {
            getViewState().showErrorOldPass(App.getInstance().getString(R.string.error_invalid_pass));
            return;
        }
        JsonObject jsonObject = JsonObjBody.change_email(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), pass, email,AppState.getInstance(App.getInstance()).getString(AppState.Key.LANG_APP));
        changeEmail(jsonObject, true);
    }

    private boolean isValidEmail(String email) {
        String sDomen ="^[^\\.]"+"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+";
        Pattern p = Pattern.compile(sDomen);
        Matcher m = p.matcher(email);
        return m.matches()&& !TextUtils.isEmpty(email);
    }

    private boolean isValidPass(String pass) {
        return pass.length() > 5;
    }

    public void changeEmail(JsonObject jsonObject, boolean showProgress) {
        if (showProgress) {
            getViewState().showProgres();
        }
        final Single<CapObject> observable = serverMethod.changeEmail(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {
                    getViewState().hideProgres();
                    if (capObject.getResult() > 0) {
                        getViewState().showError(App.getInstance().getString(R.string.change_email));
                        getViewState().email_change_ok();
                    } else {
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().showError(text_error);
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg()) || ErrorMessage.NO_PERMISSION.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                getViewState().no_session();
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }else if(ErrorMessage.CURRENT_PASSWORD_WRONG.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())){
                                getViewState().showErrorOldPass(text_error);
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

