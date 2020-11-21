package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.custom_dialog.SweetAlertDialog;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IProfileView;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.MyProfilePresenter;

public class MyProfile extends MvpAppCompatActivity implements View.OnClickListener, IProfileView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_tools)
    LinearLayout btn_tools;
    @BindView(R.id.btn_my_ads)
    LinearLayout btn_my_ads;
    @BindView(R.id.btn_my_payments)
    LinearLayout btn_my_payments;
    @BindView(R.id.btn_exit)
    LinearLayout btn_exit;
    @InjectPresenter
    MyProfilePresenter myProfilePresenter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        initButtons();
    }

    private void initButtons() {
        btn_tools.setOnClickListener(this);
        btn_my_ads.setOnClickListener(this);
        btn_my_payments.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == btn_tools.getId()) {
            startActivity(new Intent(this, ActivityEditProfile.class));
        } else if (id == btn_my_ads.getId()) {
            startActivity(new Intent(this, MyAdActivity.class));
        } else if (id == btn_my_payments.getId()) {
            startActivity(new Intent(this, MyPaymentsActivity.class));
        } else if (id == btn_exit.getId()) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    //.setTitleText(getString(R.string.dialog_log_out))
                    .setContentText(getString(R.string.dialog_log_out))
                    .setCancelText(getString(R.string.cancel))
                    .setConfirmText(getString(R.string.yes_logout))
                    .setConfirmClickListener(sweetAlertDialog -> {
                        myProfilePresenter.user_logout(true);
                        sweetAlertDialog.dismissWithAnimation();
                    })
                    .show();
        }
    }

    @Override
    public void sendError(String s) {

    }

    @Override
    public void no_session(String text) {

    }

    @Override
    public void userLogOut() {
        setResult(RESULT_OK);
        finish();
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
