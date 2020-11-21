package solutions.isky.gaurangarevolution.presentation.ui.login;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RSRuntimeException;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.customtabs.CustomTabsIntent;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.support.transition.TransitionManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.AccessToken;
import com.vk.sdk.VKAccessToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.data.models.DetailsSocial;
import solutions.isky.gaurangarevolution.data.models.UserLoginRegister;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.FastBlur;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.RSBlur;
import solutions.isky.gaurangarevolution.presentation.custom_dialog.SweetAlertDialog;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.login.ILoginView;
import solutions.isky.gaurangarevolution.presentation.mvp.login.LoginPresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.ui.login.fragments.SocialConfirm;
import solutions.isky.gaurangarevolution.presentation.ui.login.view.LoginAndRegisterLayout;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.ConfirmPhoneActivity;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.VerificationPhoneActivity;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;


public class LoginActivity extends MvpAppCompatActivity implements ILoginView, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_fon_icon)
    ImageView img_fon_icon;
    @BindView(R.id.context_all_login)
    ConstraintLayout mConstraintLayout;
    private Handler handler;
    @InjectPresenter
    LoginPresenter loginPresenter;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.login_and_register)
    LoginAndRegisterLayout loginAndRegisterLayout;
    @BindView(R.id.iv_close)
    ImageView btn_close;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.iv_fc)
    ImageView iv_fc;
    @BindView(R.id.iv_vk)
    ImageView iv_vk;
    @BindView(R.id.iv_ok)
    ImageView iv_ok;
    @BindView(R.id.ll_btn_login)
    LinearLayout ll_btn_login;
    @BindView(R.id.ll_root_register)
    LinearLayout ll_root_register;
    private boolean mIsLogin = true;
    @BindView(R.id.btn_forget_pass)
    Button btn_forget_pass;
    final static int REQUEST_AUTH = 107;
    final static int REQUEST_AUTH_FB = 134;
    final static int REQUEST_AUTH_VK = 135;
    final static int REQUEST_AUTH_OK = 136;
    final static int REQUEST_SOCIALCONFIRM = 139;
    private String mTokenOK = "";
    String mIdOK = "";
    @BindView(R.id.root_view_scroll)
    ScrollView root_view;
    UserLoginRegister userLoginRegister;
    final static int REQUEST_PHONE_CONFIRM = 11220;

    final static int REGISTER_TYPE_EMAIL = 1; // обязательный e-mail
    final static int REGISTER_TYPE_BOTH  = 2; // обязательный телефон и email
    final static int REGISTER_TYPE_PHONE = 3; // обязательный телефон

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        handler = new Handler();
        handler.postDelayed(() -> {
            setAnim();
           // initBlurEffect();
        }, 400);
        userLoginRegister=new UserLoginRegister();

        btn_login.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forget_pass.setOnClickListener(this);

        iv_fc.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, FacebookLoginActivity.class), REQUEST_AUTH_FB);
        });
        iv_vk.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, VKLoginActivity.class), REQUEST_AUTH_VK);
        });
        iv_ok.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, OKLoginActivity.class), REQUEST_AUTH_OK);
        });
        loginPresenter.getCountryCode();
    }


    private void setAnim() {
        ConstraintSet mConstraintEnd = new ConstraintSet();
        mConstraintEnd.clone(mConstraintLayout);
        Transition transition = new AutoTransition();
        transition.setDuration(400);
        mConstraintEnd.setVisibility(R.id.linearLayout, ConstraintSet.VISIBLE);
//        mConstraintEnd.constrainHeight(R.id.linearLayout,ConstraintSet.WRAP_CONTENT);
//        mConstraintEnd.constrainWidth(R.id.linearLayout,ConstraintSet.MATCH_CONSTRAINT);
        TransitionManager.beginDelayedTransition(mConstraintLayout, transition);
        mConstraintEnd.applyTo(mConstraintLayout);

    }

    private void initBlurEffect() {
        new Thread(() -> {
            img_fon_icon.setDrawingCacheEnabled(true);
            Bitmap bitmap = img_fon_icon.getDrawingCache();
            int radius = 20;
            Bitmap blurred;//second parametre is radius
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                try {
                    blurred = RSBlur.blur(this, bitmap, radius);
                } catch (RSRuntimeException e) {
                    blurred = FastBlur.blur(bitmap, radius / 3, true);
                }
            } else {
                blurred = FastBlur.blur(bitmap, radius / 3, true);
            }

        }).start();
    }


    @Override
    public void show_error(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorEmail(String err) {
        loginAndRegisterLayout.setErrorEmail(err);
    }

    @Override
    public void setErrorPass(String err) {
        loginAndRegisterLayout.setErrorPass(err);
    }

    @Override
    public void setErrorPhone(String err) {
        loginAndRegisterLayout.setErrorPhone(err);
    }

    @Override
    public void setLoginData(GetRegister getRegister) {
        show_error(getString(R.string.you_login));
        if (TextUtils.isEmpty(getRegister.getUser_info().getError())) {
            setResult(RESULT_OK);
            finish();
        } else {
            //TODO временная заглушка, уточнить логику


           }

    }

    @Override
    public void getUser(GetRegister getRegister) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void confirm_email_social(DetailsSocial detailsSocial) {
//        FragmentManager fm = getSupportFragmentManager();
//        SocialConfirm socialConfirm = SocialConfirm.newInstance(detailsSocial);
//        socialConfirm.show(fm, "fragment_complain");
        startActivityForResult(new Intent(this, SocialConfirmActivity.class).putExtra("detail", detailsSocial), REQUEST_SOCIALCONFIRM);
    }

//    @Override
//    public void no_email_in_profile(int provaider_id) {
//        createAndDisplayDialog(provaider_id);
//    }

    @Override
    public void setregisterData(GetRegister getRegister) {
        if (TextUtils.isEmpty(getRegister.getUser_info().getError())) {
            getUser(getRegister);
        } else if (ErrorMessage.ACCOUNT_NOT_ACTIVE.equalsIgnoreCase(getRegister.getUser_info().getError())) {
            Log.d("setregisterData", ErrorMessage.ACCOUNT_NOT_ACTIVE);
            notactive();
        }else if (ErrorMessage.ACCOUNT_NOT_ACTIVATED_BY_SMS.equalsIgnoreCase(getRegister.getUser_info().getError())) {
            Log.d("setregisterData", ErrorMessage.ACCOUNT_NOT_ACTIVATED_BY_SMS);
            startActivityForResult(new Intent(this, ConfirmPhoneActivity.class).putExtra("user_id", getRegister.getUser_info().getUser_id()).putExtra("user_phone", getRegister.getUser_info().getPhone()).putExtra("change_phone", false), REQUEST_PHONE_CONFIRM);

        }  else {
            show_error(getRegister.getUser_info().getError());
        }
    }

    @Override
    public void notActive() {
        notactive();
    }

    @Override
    public void block_user(String reason) {
        blockUserAlert(reason);
    }

    @Override
    public void getCodeCountry() {
        loginAndRegisterLayout.setCodeCountry();
        loginAndRegisterLayout.setView(mIsLogin, AppState.getInstance(this).getInteger(AppState.Key.USERS_REGISTER_MODE));

    }

    private void notactive() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View layout = getLayoutInflater().inflate(
                R.layout.dialog_not_active, null);
        alertDialogBuilder.setView(layout);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        Button btn_ok = (Button) layout.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(view -> {
            alertDialog.cancel();
            mIsLogin = true;
            setView();
        });
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    private void blockUserAlert(String reason) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                //.setTitleText(getString(R.string.dialog_log_out))
                .setContentText(String.format(getString(R.string.user_block),reason))
                .setConfirmText(getString(R.string.yes))
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                })
                .show();

    }
    private void createAndDisplayDialog(int provaider_id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View layout = getLayoutInflater().inflate(
                R.layout.dialog_add_email, null);
        alertDialogBuilder.setView(layout);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        Button btn_cancel = (Button) layout.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) layout.findViewById(R.id.btn_ok);
        EditText et_email = layout.findViewById(R.id.et_email);
        btn_cancel.setOnClickListener(view -> alertDialog.cancel());
        btn_ok.setOnClickListener(view -> {
            String email = et_email.getText().toString().trim();
            if (!isValidEmail(email)) {
                Toast.makeText(this, getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show();
            } else {
                if (provaider_id == 2) {
                    AccessToken accesstoken = AccessToken.getCurrentAccessToken();
                    if (accesstoken != null) {
                        loginPresenter.login_soc(accesstoken.getUserId(), accesstoken.getToken(), email, Constants.PROVIDER_FC);
                    }
                    alertDialog.cancel();
                } else if (provaider_id == 1) {
                    VKAccessToken vkAccessToken = VKAccessToken.currentToken();
                    if (vkAccessToken != null) {
                        loginPresenter.login_soc(vkAccessToken.userId, vkAccessToken.accessToken, email, Constants.PROVIDER_VK);
                    }
                } else if (provaider_id == 4) {

                    if (!TextUtils.isEmpty(mTokenOK)) {
                        loginPresenter.login_soc(mIdOK, mTokenOK, email, Constants.PROVIDER_OK);
                    }
                }
            }
        });

        alertDialog.show();
    }

    private boolean isValidEmail(String email) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email));
    }

    @Override
    public void showProgres() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgres() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoNetworkLayout() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        KeyboardUtil.hideKeyboard(this);
        if (id == btn_login.getId()) {
            userLoginRegister=loginAndRegisterLayout.getDataUser();
            if (mIsLogin) {
                clearError();
                loginPresenter.login(userLoginRegister);
            } else {
                loginPresenter.register(userLoginRegister);
            }

        } else if (id == btn_close.getId()) {
            finish();
        } else if (id == iv_back.getId()) {
            onBackPressed();
        } else if (id == btn_register.getId()) {
            //startActivityForResult(new Intent(this, RegisterActivity.class), REQUEST_AUTH);
            mIsLogin = false;
            setView();
        } else if (id == btn_forget_pass.getId()) {
            Uri address = Uri.parse(Constants.FORGOT_PASS);
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
           // startActivity(openlinkIntent);

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                .setToolbarColor(this.getResources().getColor(R.color.colorPrimaryDark))
                .setShowTitle(true);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, address);
        }
    }

    private void clearError() {
        loginAndRegisterLayout.clearError();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "requestCode= " + requestCode + ", resultCode= " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_AUTH:
                    finish();
                    break;
                case REQUEST_AUTH_FB:
                    AccessToken accesstoken = AccessToken.getCurrentAccessToken();
                    if (accesstoken != null) {
                        loginPresenter.login_soc(accesstoken.getUserId(), accesstoken.getToken(), Constants.PROVIDER_FC);
                    }
                    break;
                case REQUEST_AUTH_VK:
                    VKAccessToken vkAccessToken = VKAccessToken.currentToken();
                    if (vkAccessToken != null) {
                        loginPresenter.login_soc(vkAccessToken.userId, vkAccessToken.accessToken, Constants.PROVIDER_VK);
                        Log.d("VKAccessToken", vkAccessToken.accessToken);
                        Log.d("VKAccessToken", vkAccessToken.userId);
                    }
                    break;
                case REQUEST_AUTH_OK:
                    mTokenOK = data.getStringExtra("token");
                    mIdOK = data.getStringExtra("id");
                    if (!TextUtils.isEmpty(mTokenOK)) {
                        loginPresenter.login_soc(mIdOK, mTokenOK, Constants.PROVIDER_OK);
                    }
                    break;
                case REQUEST_SOCIALCONFIRM:
                    if(data!=null&&data.hasExtra("user_id")&&data.hasExtra("phone")){
                        startActivityForResult(new Intent(this, ConfirmPhoneActivity.class).putExtra("user_id", data.getIntExtra("user_id",0)).putExtra("user_phone", data.getStringExtra("phone")).putExtra("change_phone", false), REQUEST_PHONE_CONFIRM);
                    }else{
                        show_error(getString(R.string.you_login));
                        finish();
                    }

                    break;
                case REQUEST_PHONE_CONFIRM:
                    finish();
                    break;
            }
        }

    }

    @Override
    protected void onResume() {
        setView();
        super.onResume();
    }

    private void setView() {
        loginAndRegisterLayout.setView(mIsLogin, AppState.getInstance(this).getInteger(AppState.Key.USERS_REGISTER_MODE));
        if (mIsLogin) {
            ll_btn_login.setVisibility(View.VISIBLE);
            //ll_root_register.setVisibility(View.GONE);
            iv_back.setVisibility(View.GONE);
            btn_login.setText(getString(R.string.login_in));

        } else {
            ll_btn_login.setVisibility(View.GONE);
            //ll_root_register.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            btn_login.setText(getString(R.string.register));
        }
    }

    @Override
    public void onBackPressed() {
        if (mIsLogin) {
            super.onBackPressed();
        } else {
            mIsLogin = true;
            setView();
        }

    }
}
