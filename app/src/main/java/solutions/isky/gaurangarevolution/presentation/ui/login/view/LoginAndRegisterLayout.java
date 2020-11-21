package solutions.isky.gaurangarevolution.presentation.ui.login.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.UserLoginRegister;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class LoginAndRegisterLayout extends LinearLayout {

    final static int REGISTER_TYPE_EMAIL = 1; // обязательный e-mail
    final static int REGISTER_TYPE_BOTH = 2; // обязательный телефон и email
    final static int REGISTER_TYPE_PHONE = 3; // обязательный телефон
    MyCustomEditText et_login;
    MyCustomEditText et_pass;
    MyCustomEditText et_pass2;
    TextView text_error_pass;
    TextView text_error_login;
    TextView text_error_phone;
    CountryCodePicker codePicker;
    MyCustomEditText tie_phone;
    LinearLayout til_new_phone;
    LinearLayout ll_root_register;
    LinearLayout ll_login;
    LinearLayout ll_passw;
    UserLoginRegister userLoginRegister;


    public LoginAndRegisterLayout(Context context) {
        this(context, null, 0);
    }

    public LoginAndRegisterLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginAndRegisterLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.login_register_layout, this, true);
        initView(context);
    }

    private void initView(Context context) {
        et_login = findViewById(R.id.et_login);
        et_pass = findViewById(R.id.et_pass);
        et_pass2 = findViewById(R.id.et_pass2);
        text_error_pass = findViewById(R.id.text_error_pass);
        text_error_login = findViewById(R.id.text_error_login);
        text_error_phone=findViewById(R.id.text_error_phone);
        tie_phone = findViewById(R.id.tie_phone);
        codePicker = findViewById(R.id.ccp);
        til_new_phone = findViewById(R.id.til_new_phone);
        ll_root_register = findViewById(R.id.ll_root_register);
        ll_login = findViewById(R.id.ll_login);
        ll_passw = findViewById(R.id.ll_passw);
        codePicker.registerCarrierNumberEditText(tie_phone);
        codePicker.setCustomMasterCountries(AppState.getInstance(context).getString(AppState.Key.CODE_COUNTRY_STRING));
        et_login.addTextChangedListener(textWatcher);
        et_pass.addTextChangedListener(textWatcher);
        tie_phone.addTextChangedListener(textWatcher);
        userLoginRegister=new UserLoginRegister();
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
    public void clearError() {
        text_error_pass.setVisibility(View.INVISIBLE);
        text_error_pass.setText("");
        text_error_login.setVisibility(View.INVISIBLE);
        text_error_login.setText("");
        text_error_phone.setVisibility(View.INVISIBLE);
        text_error_phone.setText("");
    }
    public void setErrorEmail(String error){
        text_error_login.setText(error);
        text_error_login.setVisibility(View.VISIBLE);
        et_login.requestFocus();
    }
    public void setErrorPass(String err) {
        text_error_pass.setText(err);
        text_error_pass.setVisibility(View.VISIBLE);
    }
    public void setErrorPhone(String err) {
        text_error_phone.setText(err);
        text_error_phone.setVisibility(View.VISIBLE);
    }
    public UserLoginRegister getDataUser(){
        userLoginRegister.setLogin(et_login.getText().toString());
        userLoginRegister.setPassw(et_pass.getText().toString().trim().replaceAll(" ", ""));
        userLoginRegister.setPassw2(et_pass2.getText().toString().trim().replaceAll(" ", ""));
        userLoginRegister.setPhone(codePicker.getFullNumberWithPlus());
        userLoginRegister.setType_reg(AppState.getInstance(getContext()).getInteger(AppState.Key.USERS_REGISTER_MODE));
        userLoginRegister.setIs_email_valid(checkEmail(et_login.getText().toString()));
        userLoginRegister.setIs_phone_valid(codePicker.isValidFullNumber());
        return userLoginRegister;
    }
    public void setCodeCountry() {
        codePicker.setCustomMasterCountries(AppState.getInstance(getContext()).getString(AppState.Key.CODE_COUNTRY_STRING));

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
    public void setView(boolean is_login, int type_reg) {
        et_pass.setText("");
        et_pass2.setText("");
        et_login.setText("");

        if (is_login) {
            ll_login.setVisibility(VISIBLE);
            ll_passw.setVisibility(VISIBLE);
            til_new_phone.setVisibility(GONE);
            ll_root_register.setVisibility(GONE);
            et_login.setHint(type_reg == 1 ? getContext().getString(R.string.email) : getContext().getString(R.string.email_or_phone));

        } else {
            et_login.setHint(getContext().getString(R.string.email));
            switch (type_reg) {
                case 1:
                    til_new_phone.setVisibility(GONE);
                    ll_login.setVisibility(VISIBLE);
                    ll_root_register.setVisibility(VISIBLE);
                    ll_passw.setVisibility(VISIBLE);

                    break;
                case 2:
                    til_new_phone.setVisibility(VISIBLE);
                    ll_login.setVisibility(VISIBLE);
                    ll_root_register.setVisibility(VISIBLE);
                    ll_passw.setVisibility(VISIBLE);
                    break;
                case 3:
                    til_new_phone.setVisibility(VISIBLE);
                    ll_login.setVisibility(GONE);
                    ll_root_register.setVisibility(GONE);
                    ll_passw.setVisibility(GONE);
                    break;

            }
        }

    }

}
