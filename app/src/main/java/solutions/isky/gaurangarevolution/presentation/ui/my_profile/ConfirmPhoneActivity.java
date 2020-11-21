package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.ConfirmPhonePresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IConfirmPhoneView;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class ConfirmPhoneActivity extends MvpAppCompatActivity implements IConfirmPhoneView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar3)
    ProgressBar progressBar;
    private int user_id;
    private String user_phone;
    @BindView(R.id.textView4)
    TextView tv_phone;
    @BindView(R.id.tie_code)
    MyCustomEditText tie_code;
    @BindView(R.id.btn_repid)
    Button btn_repid;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    @BindView(R.id.til_phone)
    TextInputLayout til_code;
    private boolean mChange_phone = false;
    @InjectPresenter
    ConfirmPhonePresenter phonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_phone);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        if (getIntent().hasExtra("user_id") && getIntent().hasExtra("user_phone")) {
            user_id = getIntent().getIntExtra("user_id", 0);
            user_phone = getIntent().getStringExtra("user_phone");
            mChange_phone = getIntent().getBooleanExtra("change_phone", false);
            phonePresenter.setPhone(user_phone);
            if (mChange_phone)
                phonePresenter.get_code(user_phone, user_id, mChange_phone);
        }
        tv_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        btn_repid.setOnClickListener(view -> {
            KeyboardUtil.hideKeyboard(this);
            if (user_phone != null && user_id != 0) {
                phonePresenter.get_code(user_phone, user_id, mChange_phone);
            }
        });
        btn_confirm.setOnClickListener(view -> checkCode());
        tie_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                til_code.setErrorEnabled(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int i = menuItem.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void show_error(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPhoneText(String phone) {
        tv_phone.setText(phone);
    }

    @Override
    public void phone_number_wrong() {
        finish();
    }

    @Override
    public void code_is_empty(String s) {
        til_code.setError(s);
    }

    @Override
    public void code_is_check(GetRegister register) {
        Log.d("code_is_check", "все окей");
        setResult(RESULT_OK);
        finish();
    }

    void checkCode() {
        KeyboardUtil.hideKeyboard(this);
        String code = tie_code.getText().toString().trim();
        if (mChange_phone) {
            phonePresenter.checkCode(code, user_id, mChange_phone, user_phone);
        } else {
            phonePresenter.checkCode(code, user_id);
        }

    }

    @Override
    public void onBackPressed() {
        KeyboardUtil.hideKeyboard(this);
        super.onBackPressed();
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
}
