package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IChangeEmailView extends BaseView {

    void showError(String error);
    void showErrorOldPass(String error);
    void showErrorNewEmail(String error);
    void email_change_ok();
    void no_session();
}
