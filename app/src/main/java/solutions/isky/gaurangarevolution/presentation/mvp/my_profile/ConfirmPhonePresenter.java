package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import android.text.TextUtils;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class ConfirmPhonePresenter extends BasePresenter<IConfirmPhoneView> {

  @Inject
  ServerMethod serverMethod;

  public ConfirmPhonePresenter() {
    App.getComponent().inject(this);
  }

  public void get_code(String phone, int user_id, boolean change_phone) {
    getViewState().showProgres();
    JsonObject jsonObject = JsonObjBody.getSmsCode(phone, user_id,
        UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), change_phone);
    final Single<CapObject> observable = serverMethod.getSmsCode(jsonObject);
    Disposable subscription = observable
        .subscribeOn(Schedulers.newThread())
        .retry(2)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(capObject -> {
          getViewState().hideProgres();
          if (capObject.getResult() > 0) {
            getViewState().show_error(App.getInstance().getString(R.string.send_code));
          } else {
            if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
              Log.d("getMessage", capObject.getMessage().get(0).getMsg());
              String text_error = ErrorMessage
                  .getError(capObject.getMessage().get(0).getMsg(), App.getInstance());
              getViewState().show_error(text_error);
              if (ErrorMessage.PHONE_NUMBER_WRONG
                  .equalsIgnoreCase(capObject.getMessage().get(0).getMsg())
                  || ErrorMessage.WRONG_PHONE_OWNER
                  .equalsIgnoreCase(capObject.getMessage().get(0).getMsg())
                  || ErrorMessage.PHONE_INUSE
                  .equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                getViewState().phone_number_wrong();
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
    Log.d("getSmsCode", jsonObject.toString());
  }


  public void setPhone(String phone) {
    getViewState().setPhoneText(phone);
  }

  public void checkCode(String code, int user_id) {
    checkCode(code, user_id, false, "");
  }


  public void checkCode(String code, int user_id, boolean change_phone, String phone) {
    if (TextUtils.isEmpty(code)) {
      getViewState().code_is_empty(App.getInstance().getString(R.string.error_emty_text));
      return;
    }
    getViewState().showProgres();
    JsonObject jsonObject = JsonObjBody.check_code(code, user_id,
        UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), change_phone,
        phone);
    final Single<GetRegister> observable = serverMethod.checkCode(jsonObject);
    Disposable subscription = observable
        .subscribeOn(Schedulers.newThread())
        .retry(2)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getRegister -> {
          getViewState().hideProgres();
          if (getRegister.getResult() > 0) {
            AppUtils.setUserData(getRegister.getUser_info());
            if (!TextUtils.isEmpty(getRegister.getSesid())) {
              UserData.getInstance(App.getInstance())
                  .setString(UserData.Key.USER_SESSID, getRegister.getSesid());
              AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, true);
              if (!change_phone) {
                getViewState().show_error(App.getInstance().getString(R.string.welcome));
              }
            }
            getViewState().code_is_check(getRegister);
          } else {
            if (getRegister.getMessage() != null && getRegister.getMessage().size() > 0) {
              Log.d("getMessage", getRegister.getMessage().get(0).getMsg());
              String text_error = ErrorMessage
                  .getError(getRegister.getMessage().get(0).getMsg(), App.getInstance());
              getViewState().show_error(text_error);
              if (ErrorMessage.PHONE_NUMBER_WRONG
                  .equalsIgnoreCase(getRegister.getMessage().get(0).getMsg())) {
                getViewState().phone_number_wrong();
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
    Log.d("checkCode", jsonObject.toString());
  }
}
