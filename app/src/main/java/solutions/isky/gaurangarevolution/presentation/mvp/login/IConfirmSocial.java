package solutions.isky.gaurangarevolution.presentation.mvp.login;

import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IConfirmSocial extends BaseView{
    void setErrorEmail(String err);
    void setErrorPhone(String err);
    void setErrorPass(String err);
    void setLoginData(GetRegister getRegister);
    void show_error(String s);
    void setConfirmPass();
    void notActive();
    void needSms(GetRegister getRegister);
}
