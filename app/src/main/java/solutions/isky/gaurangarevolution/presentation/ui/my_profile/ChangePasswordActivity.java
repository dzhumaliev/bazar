package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.ChangePasswordPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IChangePassView;
import solutions.isky.gaurangarevolution.presentation.ui.main.MainActivity;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class ChangePasswordActivity extends MvpAppCompatActivity implements IChangePassView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_old_pass)
    MyCustomEditText et_old_pass;
    @BindView(R.id.et_new_pass)
    MyCustomEditText et_new_pass;

    @BindView(R.id.text_error_cur_pass)
    TextView text_error_cur_pass;
    @BindView(R.id.text_error_new_pass)
    TextView text_error_new_pass;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectPresenter
    ChangePasswordPresenter changePasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }

        btn_save.setOnClickListener(view -> {
            text_error_new_pass.setText("");
            text_error_cur_pass.setText("");
            text_error_new_pass.setVisibility(View.INVISIBLE);
            text_error_cur_pass.setVisibility(View.INVISIBLE);
            String new_pass = et_new_pass.getText().toString().trim();
            String old_pass = et_old_pass.getText().toString().trim();
            KeyboardUtil.hideKeyboard(this);
            changePasswordPresenter.changePass(new_pass, old_pass);
        });
        et_old_pass.addTextChangedListener(textWatcher);
        et_new_pass.addTextChangedListener(textWatcher);
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

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (et_new_pass.getText().toString().trim().length() == 0 || et_old_pass.getText().toString().trim().length() == 0) {
                btn_save.setEnabled(false);
            } else {
                btn_save.setEnabled(true);
            }
        }
    };

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
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorOldPass() {
        text_error_cur_pass.setText(getString(R.string.error_invalid_pass));
        text_error_cur_pass.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorOldPass(String error) {
        text_error_cur_pass.setText(error);
        text_error_cur_pass.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorNewPass(String s) {
        text_error_new_pass.setText(s);
        text_error_new_pass.setVisibility(View.VISIBLE);
        et_new_pass.requestFocus();
    }

    @Override
    public void no_session() {
        showError(getString(R.string.no_session));
        startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public void pass_change_ok() {
        showError(getString(R.string.change_pass));
        finish();
    }


}
