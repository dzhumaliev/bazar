package solutions.isky.gaurangarevolution.presentation.mvp.login;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.data.models.DetailsSocial;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class SocialConfirmPresenter extends BasePresenter<IConfirmSocial> {
    @Inject
    ServerMethod serverMethod;

    public SocialConfirmPresenter() {
        App.getComponent().inject(this);
    }


    public void confirm_email(String email,String phone, DetailsSocial detailsSocial,boolean is_phone_valid) {
        if(AppState.getInstance(App.getInstance()).getInteger(AppState.Key.USERS_REGISTER_MODE)!=3){
            if (!isValidEmail(email)) {
                getViewState().setErrorEmail(App.getInstance().getString(R.string.error_invalid_email));
                return;
            }
        }

        if (phone!=null&&!is_phone_valid) {
            getViewState().setErrorPhone(App.getInstance().getString(R.string.error_invalid_phone));
            return;
        }

        confirmRest(email,phone, detailsSocial);
    }
    public void confirm_pass(String email,String pass, DetailsSocial detailsSocial) {
        if (!isValidPass(pass)) {
            getViewState().setErrorPass(App.getInstance().getString(R.string.error_invalid_pass));
            return;
        }


        confirmPassRest(email,pass, detailsSocial);
    }
    private boolean isValidEmail(String email) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email));
    }
    private boolean isValidPhone(String phone) {
        //return PhoneNumberUtils.isGlobalPhoneNumber(phone);
        return (Patterns.PHONE.matcher(phone).matches() && !TextUtils.isEmpty(phone));
    }
    private boolean isValidPass(String pass) {
        return pass.length() >= 5;
    }

    private void confirmRest(String email,String phone, DetailsSocial detailsSocial) {
        getViewState().showProgres();
        JsonObject jsonObject = JsonObjBody.register(email,phone,null, detailsSocial.getProvider_id(),detailsSocial.getToken(),detailsSocial.getProfile_id(), AppUtils.getLocale(App.getInstance()));
        final Single<GetRegister> observable = serverMethod.login(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRegister -> {
                    getViewState().hideProgres();
                    if (getRegister.getResult() > 0) {
                        if (!TextUtils.isEmpty(getRegister.getSesid())&&TextUtils.isEmpty(getRegister.getUser_info().getError())) {

                            AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, true);
                        }
                        UserData.getInstance(App.getInstance()).setString(UserData.Key.USER_SESSID, getRegister.getSesid());
                        AppUtils.setUserData(getRegister.getUser_info());
                        getViewState().setLoginData(getRegister);
                    } else {
                        if (getRegister.getMessage() != null && getRegister.getMessage().size() > 0) {
                            Log.d("getMessage", getRegister.getMessage().get(0).getMsg());
                            String text_error = ErrorMessage.getError(getRegister.getMessage().get(0).getMsg(), App.getInstance());
                            //getViewState().show_error(text_error);
                            if (ErrorMessage.LOGIN_FAIL.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().setErrorEmail(text_error);
                            }else if (ErrorMessage.SOCIAL_REGISTER_EMAIL_INUSE.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().setConfirmPass();
                            }else if (ErrorMessage.ACCOUNT_NOT_ACTIVATED_BY_SMS.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().needSms(getRegister);
                            }
                        } else {
                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("loginRest", jsonObject.toString());
    }
    //user_social_connect (email, pass, provider_id, token, profile_id)

    private void confirmPassRest(String email,String pass, DetailsSocial detailsSocial) {
        getViewState().showProgres();
        JsonObject jsonObject = JsonObjBody.register(email,null,pass, detailsSocial.getProvider_id(),detailsSocial.getToken(),detailsSocial.getProfile_id(), AppUtils.getLocale(App.getInstance()));
        final Single<GetRegister> observable = serverMethod.login(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRegister -> {
                    getViewState().hideProgres();
                    if (getRegister.getResult() > 0) {
                        if (!TextUtils.isEmpty(getRegister.getSesid())&&TextUtils.isEmpty(getRegister.getUser_info().getError())) {
                            UserData.getInstance(App.getInstance()).setString(UserData.Key.USER_SESSID, getRegister.getSesid());
                            AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, true);
                        }

                        AppUtils.setUserData(getRegister.getUser_info());
                        getViewState().setLoginData(getRegister);
                    } else {
                        if (getRegister.getMessage() != null && getRegister.getMessage().size() > 0) {
                            Log.d("getMessage", getRegister.getMessage().get(0).getMsg());
                            String text_error = ErrorMessage.getError(getRegister.getMessage().get(0).getMsg(), App.getInstance());
                            //getViewState().show_error(text_error);
                            if (ErrorMessage.LOGIN_FAIL.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().setErrorEmail(text_error);
                            }else if (ErrorMessage.PASS_FAIL.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().setErrorPass(text_error);
                            }else if (ErrorMessage.USER_NOT_ACTIVE.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().notActive();
                            }
                        } else {
                            getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().show_error(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("loginRest", jsonObject.toString());
    }

}
