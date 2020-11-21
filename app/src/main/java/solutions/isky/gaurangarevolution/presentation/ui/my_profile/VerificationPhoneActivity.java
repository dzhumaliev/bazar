package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.hbb20.CountryCodePicker;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IVerificationPhoneView;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.VerificationPhonePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class VerificationPhoneActivity extends MvpAppCompatActivity implements
    IVerificationPhoneView {



  @Override
  public void showProgres() {

  }

  @Override
  public void hideProgres() {

  }

  @Override
  public void showNoNetworkLayout() {

  }

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.text_pr)
  AppCompatTextView text_pr;
  private int user_id;
  private String user_phone;
  @BindView(R.id.ccp)
  CountryCodePicker codePicker;
  @BindView(R.id.tie_phone)
  MyCustomEditText tie_phone;
  @BindView(R.id.btn_next)
  Button btn_next;
  final static int REQUEST_CONFIRM = 104;
  boolean mChange_phone = false;

  @InjectPresenter
  VerificationPhonePresenter verificationPhonePresenter;


  @SuppressWarnings("deprecation")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verification_phone);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.show();
    }
    codePicker.registerCarrierNumberEditText(tie_phone);
    codePicker.setCustomMasterCountries(AppState.getInstance(this).getString(AppState.Key.CODE_COUNTRY_STRING));
    verificationPhonePresenter.getCountryCode();
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      text_pr.setText(Html.fromHtml(getString(R.string.text_confirm_profile), Html.FROM_HTML_MODE_LEGACY));
    } else {
      text_pr.setText(Html.fromHtml(getString(R.string.text_confirm_profile)));
    }

    if (getIntent().hasExtra("user_id") && getIntent().hasExtra("user_phone")) {
      user_id = getIntent().getIntExtra("user_id", 0);
      user_phone = getIntent().getStringExtra("user_phone");
    } else {
      finish();
    }
    mChange_phone = getIntent().getBooleanExtra("change_phone", false);
    try{
      codePicker.setFullNumber(user_phone);
    }catch (Exception e){
      e.printStackTrace();
    }


//        tie_phone.setText(user_phone);
//        tie_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    btn_next.setOnClickListener(view -> {
      user_phone = codePicker.getFullNumberWithPlus();
      if (codePicker.isValidFullNumber()) {
        startActivityForResult(new Intent(this, ConfirmPhoneActivity.class).putExtra("user_id", user_id).putExtra("user_phone", user_phone).putExtra("change_phone", mChange_phone), REQUEST_CONFIRM);
      } else {
        Toast.makeText(this, R.string.error_invalid_phone, Toast.LENGTH_SHORT).show();
      }
    });
  }
  @Override
  public void getCodeCountry() {
    codePicker.setCustomMasterCountries(AppState.getInstance(this).getString(AppState.Key.CODE_COUNTRY_STRING));
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
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case REQUEST_CONFIRM:
          setResult(RESULT_OK);
          finish();
          break;
      }
    }
  }

  @Override
  public void onBackPressed() {
    KeyboardUtil.hideKeyboard(this);
    super.onBackPressed();
  }
}
