package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IConfirmPhoneView extends BaseView {
  void show_error(String s);
  void setPhoneText(String phone);
  void phone_number_wrong();
  void code_is_empty(String s);
  void code_is_check(GetRegister register);
}
