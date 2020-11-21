package solutions.isky.gaurangarevolution.presentation.mvp.login;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

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
import solutions.isky.gaurangarevolution.data.event.GetCountryCode;
import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.data.models.CountryCode;
import solutions.isky.gaurangarevolution.data.models.UserLoginRegister;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;


/**
 * Created by Serzhik on 30.08.2017.
 */
@InjectViewState
public class LoginPresenter extends BasePresenter<ILoginView> {
    @Inject
    ServerMethod serverMethod;

    public LoginPresenter() {
        App.getComponent().inject(this);
    }


    public void login(UserLoginRegister userLoginRegister) {
//        if (!checkEmail(userLoginRegister.getLogin())) {
//            getViewState().setErrorEmail(App.getInstance().getString(R.string.error_invalid_email));
//            return;
//        }
//        if (!isValidPass(userLoginRegister.getPassw())) {
//            getViewState().setErrorPass(App.getInstance().getString(R.string.error_emty_text));
//            return;
//        }
        UserData.getInstance(App.getInstance()).setString(UserData.Key.USER_PASSW, userLoginRegister.getPassw());
        loginRest(userLoginRegister.getLogin(), userLoginRegister.getPassw());
    }

    private boolean checkEmail(String sEmail) {
        String sDomen = "^[^\\.]" + "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+";
        Pattern p = Pattern.compile(sDomen);
        Matcher m = p.matcher(sEmail);
        return m.matches() && !TextUtils.isEmpty(sEmail);
    }

    private boolean isValidEmail(String email) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email));
    }

    private boolean isValidPass(String pass) {
        return !TextUtils.isEmpty(pass);
    }

    private void loginRest(String email, String pass) {
        getViewState().showProgres();
        JsonObject jsonObject = JsonObjBody.login(email, pass);
        final Single<GetRegister> observable = serverMethod.login(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRegister -> {
                    getViewState().hideProgres();
                    if (getRegister.getResult() > 0) {
                        if (!TextUtils.isEmpty(getRegister.getSesid())) {
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
                            } else if (ErrorMessage.PASS_FAIL.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().setErrorEmail(text_error);
                            } else if (ErrorMessage.USER_NOT_ACTIVE.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().notActive();
                            } else if (ErrorMessage.USER_PASSWORD_LENGTH_MISSMATCH.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().setErrorPass(String.format(App.getInstance().getString(R.string.error_invalid_pass), getRegister.getMessage().get(0).getMin_length_pass()));
                            } else if (ErrorMessage.USER_BLOCKED.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                String reason_block = "";
                                if (getRegister.getMessage().get(0).getReason_text() != null) {
                                    reason_block = getRegister.getMessage().get(0).getReason_text();
                                }
                                getViewState().block_user(reason_block);
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

    public void login_soc(String profile_id, String token, String provider_id) {
        login_soc(profile_id, token, null, provider_id);
    }

    public void login_soc(String profile_id, String token, String email, String provider_id) {
        getViewState().showProgres();
        JsonObject object = JsonObjBody.login_soc(profile_id, token, email, AppState.getInstance(App.getInstance()).getString(AppState.Key.LANG_APP), provider_id);
        final Single<GetRegister> observable = serverMethod.login_fb(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRegister -> {
                    getViewState().hideProgres();
                    if (getRegister.getResult() > 0) {
                        AppUtils.setUserData(getRegister.getUser_info());
                        if (!TextUtils.isEmpty(getRegister.getUser_info().getPassword())) {
                            UserData.getInstance(App.getInstance()).setString(UserData.Key.USER_PASSW, getRegister.getUser_info().getPassword());
                        }
                        if (!TextUtils.isEmpty(getRegister.getSesid()) && TextUtils.isEmpty(getRegister.getUser_info().getError())) {
                            UserData.getInstance(App.getInstance()).setString(UserData.Key.USER_SESSID, getRegister.getSesid());
                            AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, true);
                        }
                        // getViewState().getUser(getRegister);
                        getViewState().setregisterData(getRegister);
                        getViewState().show_error(App.getInstance().getString(R.string.welcome));

                    } else {
                        if (getRegister.getMessage() != null && getRegister.getMessage().size() > 0) {
                            if (ErrorMessage.SOCIAL_USER_NOT_EXIST.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                if (getRegister.getDetailsSocial() != null) {
                                    getViewState().confirm_email_social(getRegister.getDetailsSocial());
                                }
                                return;
                            }
                            String text_error = ErrorMessage.getError(getRegister.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().show_error(text_error);
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
        Log.d("login_fb", object.toString());
    }

    public void register(UserLoginRegister userLoginRegister) {
        if (userLoginRegister.getType_reg() == 1) {
            if (!userLoginRegister.isIs_email_valid()) {
                getViewState().setErrorEmail(App.getInstance().getString(R.string.error_invalid_email));
                return;
            }
            if (!userLoginRegister.getPassw().equals(userLoginRegister.getPassw2())) {
                getViewState().setErrorPass(App.getInstance().getString(R.string.error_invalid_passes));
                return;
            }
            if (!isValidPass(userLoginRegister.getPassw())) {
                getViewState().setErrorPass(App.getInstance().getString(R.string.error_emty_text));
                return;
            }
        } else if (userLoginRegister.getType_reg() == 2) {
            if (!userLoginRegister.isIs_email_valid()) {
                getViewState().setErrorEmail(App.getInstance().getString(R.string.error_invalid_email));
                return;
            }
            if (!userLoginRegister.getPassw().equals(userLoginRegister.getPassw2())) {
                getViewState().setErrorPass(App.getInstance().getString(R.string.error_invalid_passes));
                return;
            }
            if (!isValidPass(userLoginRegister.getPassw())) {
                getViewState().setErrorPass(App.getInstance().getString(R.string.error_emty_text));
                return;
            }
            if (!userLoginRegister.isIs_phone_valid()) {
                getViewState().setErrorPhone(App.getInstance().getString(R.string.error_invalid_phone));
                return;
            }

        } else if (userLoginRegister.getType_reg() == 3) {
            if (!userLoginRegister.isIs_phone_valid()) {
                getViewState().setErrorPhone(App.getInstance().getString(R.string.error_invalid_phone));
                return;
            }
        }

        UserData.getInstance(App.getInstance()).setString(UserData.Key.USER_PASSW, userLoginRegister.getPassw());
        registerRest(userLoginRegister);
    }


    private void registerRest(UserLoginRegister userLoginRegister) {
        getViewState().showProgres();
        JsonObject jsonObject = JsonObjBody.register(userLoginRegister, AppUtils.getLocale(App.getInstance()));
        final Single<GetRegister> observable = serverMethod.register(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRegister -> {
                    getViewState().hideProgres();
                    if (getRegister.getResult() > 0) {
                        if (!TextUtils.isEmpty(getRegister.getSesid())) {
                            UserData.getInstance(App.getInstance()).setString(UserData.Key.USER_SESSID, getRegister.getSesid());
                        }
                        getViewState().setregisterData(getRegister);
                    } else {
                        if (getRegister.getMessage() != null && getRegister.getMessage().size() > 0) {
                            Log.d("getMessage", getRegister.getMessage().get(0).getMsg());
                            String text_error = ErrorMessage.getError(getRegister.getMessage().get(0).getMsg(), App.getInstance());

                            if (ErrorMessage.USER_EMAIL_INUSE.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())
                                    || ErrorMessage.USER_EMAIL_WRONG.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().setErrorEmail(text_error);
                            } else if (ErrorMessage.USER_PASSWORD_LENGTH_MISSMATCH.equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                                getViewState().setErrorPass(String.format(App.getInstance().getString(R.string.error_invalid_pass), getRegister.getMessage().get(0).getMin_length_pass()));
                            } else {
                                getViewState().show_error(text_error);
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
        Log.d("getRegister", jsonObject.toString());
    }

    public void getCountryCode() {
        JsonObject jsonObject = JsonObjBody.getCodeCauntry(AppUtils.getLocale(App.getInstance()));
        final Single<GetCountryCode> observable = serverMethod.getCountryCode(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCountryCode -> {
                    if (getCountryCode.getResult() > 0) {
                        StringBuilder code = new StringBuilder();
                        for (CountryCode countryCode : getCountryCode.getCountryCodes()) {
                            if (code.length() > 1) {
                                code.append("," + countryCode.getCountry_code());
                            } else {
                                code.append(countryCode.getCountry_code());
                            }
                        }
                        if (code.length() >=  2) {
                            AppState.getInstance(App.getInstance()).setString(AppState.Key.CODE_COUNTRY_STRING, code.toString());
                            getViewState().getCodeCountry();
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getCountryCode", jsonObject.toString());
    }
}
