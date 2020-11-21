package solutions.isky.gaurangarevolution.presentation.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RSRuntimeException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.data.models.DetailsSocial;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.FastBlur;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.RSBlur;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.login.IConfirmSocial;
import solutions.isky.gaurangarevolution.presentation.mvp.login.SocialConfirmPresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class SocialConfirmActivity extends MvpAppCompatActivity implements View.OnClickListener,IConfirmSocial {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_fon_icon)
    ImageView img_fon_icon;
    @BindView(R.id.img_fon_icon2)
    ImageView img_fon_icon2;
    Handler handler;
    private DetailsSocial mDetailsSocial=null;
    @BindView(R.id.et_login)
    MyCustomEditText et_login;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.text_error_pass)
    TextView text_error_pass;
    @BindView(R.id.text_error_login)
    TextView text_error_login;
    @BindView(R.id.et_pass)
    MyCustomEditText et_pass;
    private boolean mIsEmail = true;
    @BindView(R.id.btn_login)
    Button btn_confirm;
    @BindView(R.id.ll_pass)
    LinearLayout ll_pass;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tie_phone)
    MyCustomEditText tie_phone;
    @BindView(R.id.text_error_phone)
    TextView text_error_phone;
    @BindView(R.id.ccp)
    CountryCodePicker codePicker;


    @InjectPresenter
    SocialConfirmPresenter socialConfirmPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_confirm);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        handler = new Handler();
        handler.postDelayed(() -> {
            initBlurEffect();
        }, 400);
        if (getIntent().hasExtra("detail")) {
            mDetailsSocial = (DetailsSocial) getIntent().getSerializableExtra("detail");
        }
        if(mDetailsSocial!=null){
            et_login.setText(mDetailsSocial.getEmail());
        }
        iv_back.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        et_login.addTextChangedListener(textWatcher);
        et_pass.addTextChangedListener(textWatcher);
        codePicker.registerCarrierNumberEditText(tie_phone);
        codePicker.setCustomMasterCountries(AppState.getInstance(this).getString(AppState.Key.CODE_COUNTRY_STRING));
        tie_phone.addTextChangedListener(textWatcher);
        text_title.setText(AppState.getInstance(this).getInteger(AppState.Key.USERS_REGISTER_MODE)==1?getString(R.string.info_complit_email):getString(R.string.info_complit_email_and_phone));
        et_login.setHint(AppState.getInstance(this).getInteger(AppState.Key.USERS_REGISTER_MODE)==3?"E-mail (не обязательно)":getString(R.string.email));
        ll_phone.setVisibility(AppState.getInstance(this).getInteger(AppState.Key.USERS_REGISTER_MODE)==1?View.GONE:View.VISIBLE);
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
            Bitmap finalBlurred = blurred;

            img_fon_icon2.post(() -> {
                img_fon_icon2.setAlpha(0.0f);
                img_fon_icon.setAlpha(1.0f);
                img_fon_icon2.setImageBitmap(finalBlurred);
                img_fon_icon2.invalidate();
                getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), finalBlurred));
                //root_view.setBackground(new BitmapDrawable(getResources(), finalBlurred));
            });

            showImage();

        }).start();
    }

    public void showImage() {
        // img_fon_icon2.animate().alpha(1.0f).setDuration(400);
        img_fon_icon.animate().alpha(0.0f).setDuration(400);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        KeyboardUtil.hideKeyboard(SocialConfirmActivity.this);
        if(id==iv_back.getId()){
            onBackPressed();
        }else if (id == btn_confirm.getId()) {
            if (mIsEmail) {
                clearError();
                socialConfirmPresenter.confirm_email(et_login.getText().toString(),AppState.getInstance(this).getInteger(AppState.Key.USERS_REGISTER_MODE)==1?null:codePicker.getFullNumberWithPlus(), mDetailsSocial,codePicker.isValidFullNumber());
            } else {
                socialConfirmPresenter.confirm_pass(et_login.getText().toString(), et_pass.getText().toString().trim().replaceAll(" ", ""), mDetailsSocial);
            }

        }
    }

    @Override
    public void setErrorEmail(String err) {
        text_error_login.setText(err);
        text_error_login.setVisibility(View.VISIBLE);
        et_login.requestFocus();
    }

    @Override
    public void setErrorPhone(String err) {
        text_error_phone.setText(err);
        text_error_phone.setVisibility(View.VISIBLE);
        tie_phone.requestFocus();
    }

    @Override
    public void setErrorPass(String err) {
        text_error_pass.setText(err);
        text_error_pass.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLoginData(GetRegister getRegister) {
        if(TextUtils.isEmpty(getRegister.getUser_info().getError())){
            setResult(RESULT_OK);
            finish();
        }else if (ErrorMessage.ACCOUNT_NOT_ACTIVE.equalsIgnoreCase(getRegister.getUser_info().getError())) {
            Log.d("setregisterData", ErrorMessage.ACCOUNT_NOT_ACTIVE);
            notactive();
        }else if (ErrorMessage.ACCOUNT_NOT_ACTIVATED_BY_SMS.equalsIgnoreCase(getRegister.getUser_info().getError())) {
            Log.d("setregisterData", ErrorMessage.ACCOUNT_NOT_ACTIVATED_BY_SMS);
            needSms(getRegister);
        }  else {
            show_error(getRegister.getUser_info().getError());
        }
    }

    @Override
    public void show_error(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setConfirmPass() {
        et_login.setEnabled(false);
        ll_pass.setVisibility(View.VISIBLE);
        text_title.setText(R.string.pass_confirm);
        mIsEmail=false;
    }

    @Override
    public void notActive() {
        notactive();
    }

    @Override
    public void needSms(GetRegister getRegister) {
        Intent i=new Intent();
       // i.RES(RESULT_OK);
        i.putExtra("user_id",getRegister.getUser_info().getUser_id());
        i.putExtra("phone",getRegister.getUser_info().getPhone());
        setResult(RESULT_OK,i);
        finish();
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
            finish();
        });

        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            clearError();
        }
    };
    private void clearError() {
        text_error_pass.setVisibility(View.INVISIBLE);
        text_error_pass.setText("");
        text_error_login.setVisibility(View.INVISIBLE);
        text_error_login.setText("");
        text_error_phone.setVisibility(View.INVISIBLE);
        text_error_phone.setText("");
    }
}
