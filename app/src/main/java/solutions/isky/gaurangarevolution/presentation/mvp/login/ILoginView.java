package solutions.isky.gaurangarevolution.presentation.mvp.login;

import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.data.models.DetailsSocial;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface ILoginView extends BaseView {
    void show_error(String s);
    void setErrorEmail(String err);
    void setErrorPass(String err);
    void setErrorPhone(String err);
    void setLoginData(GetRegister getRegister);
    void getUser(GetRegister getRegister);
    void confirm_email_social(DetailsSocial detailsSocial);
    void setregisterData(GetRegister getRegister);
    void notActive();
    void block_user(String reason_block);
    void getCodeCountry();
}
