package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.ChangeEmailPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IChangeEmailView;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.main.MainActivity;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class ChangeEmailActivity extends MvpAppCompatActivity implements IChangeEmailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_cur_email)
    TextView text_cur_email;

    @BindView(R.id.text_error_new_email)
    TextView text_error_new_email;
    @BindView(R.id.et_new_email)
    MyCustomEditText et_new_email;
    @BindView(R.id.text_error_cur_pass)
    TextView text_error_cur_pass;
    @BindView(R.id.et_old_pass)
    MyCustomEditText et_old_pass;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @InjectPresenter
    ChangeEmailPresenter changeEmailPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        btn_save.setOnClickListener(view -> {
            text_error_new_email.setText("");
            text_error_cur_pass.setText("");
            text_error_new_email.setVisibility(View.INVISIBLE);
            text_error_cur_pass.setVisibility(View.INVISIBLE);
            String new_email = et_new_email.getText().toString().trim();
            String old_pass = et_old_pass.getText().toString().trim();
            changeEmailPresenter.changeEmail(new_email, old_pass);
        });
        text_cur_email.setText(UserData.getInstance(this).getString(UserData.Key.USER_EMAIL)+" :");
        et_old_pass.addTextChangedListener(textWatcher);
        et_new_email.addTextChangedListener(textWatcher);

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
            if (et_old_pass.getText().toString().trim().length() == 0 || et_new_email.getText().toString().trim().length() == 0) {
                btn_save.setEnabled(false);
            } else {
                btn_save.setEnabled(true);
            }
        }
    };
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
    public void showErrorOldPass(String error) {
        text_error_cur_pass.setText(error);
        text_error_cur_pass.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorNewEmail(String error) {
        text_error_new_email.setText(error);
        text_error_new_email.setVisibility(View.VISIBLE);
    }

    @Override
    public void email_change_ok() {
        finish();
    }

    @Override
    public void no_session() {
        showError(getString(R.string.no_session));
        startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
