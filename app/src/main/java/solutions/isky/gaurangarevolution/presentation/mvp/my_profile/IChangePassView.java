package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IChangePassView extends BaseView {

    void showError(String error);
    void showErrorOldPass();
    void showErrorOldPass(String error);
    void showErrorNewPass(String error);
    void no_session();
    void pass_change_ok();
}
