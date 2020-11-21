package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.luck.picture.lib.permissions.RxPermissions;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;


import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.MockConfiguration;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.PaymentParameters;
import ru.yandex.money.android.sdk.TestParameters;
import ru.yandex.money.android.sdk.TokenizationResult;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.enums.PaymentLiqPayStatuses;
import solutions.isky.gaurangarevolution.data.event.BuyAdv;
import solutions.isky.gaurangarevolution.data.models.PaymentResult;
import solutions.isky.gaurangarevolution.presentation.custom_dialog.SweetAlertDialog;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IRefillView;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.RefillPresenter;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.utils.YandexMoneyHelper;

import static android.os.Build.HOST;

public class RefillActivity extends MvpAppCompatActivity implements IRefillView {
    private static final String TAG = "RefillActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar4)
    ProgressBar progressBar;
    @BindView(R.id.et_sum)
    EditText et_sum;
    @BindView(R.id.btn_continue)
    Button btn_continue;
    @InjectPresenter
    RefillPresenter refillPresenter;
    private String mMethod = Constants.BALANCE;
    @BindView(R.id.rg_method)
    RadioGroup radioGroup;
    @BindView(R.id.radioButtonAN)
    RadioButton radioButtonAN;
    @BindView(R.id.radioButtonPayPal)
    RadioButton radioButtonPayPal;
    @BindView(R.id.radioButtonYandex)
    RadioButton radioButtonYandex;

    private static final int REQUEST_CODE_PAYMENT = 174;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refill);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        et_sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    btn_continue.setEnabled(true);
                } else {
                    btn_continue.setEnabled(false);
                }
            }
        });
        btn_continue.setOnClickListener(v -> {
            KeyboardUtil.hideKeyboard(this);
            //yandexCheckout();

            RadioButton checkedRadioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            mMethod = checkedRadioButton.getTag().toString();
            String amount = et_sum.getText().toString().trim();

            switch (mMethod) {
                case Constants.LIQPAYBALANCE:
                    new RxPermissions(RefillActivity.this)
                            .request(Manifest.permission.READ_PHONE_STATE)
                            .subscribe(granted -> {
                                if (granted) {
                                    refillPresenter.up_balance(amount, mMethod);
                                } else {
                                    sendError(getString(R.string.permission_phone_liqpay_need));
                                }
                            });
                    break;
                case Constants.PAYPALBALANCE:
                    refillPresenter.up_balance(amount, mMethod);
                    break;
                case Constants.YANDEXBALANCE:
                    refillPresenter.up_balance(amount, mMethod);
                    break;
            }
        });
        radioButtonAN.setChecked(App.liqpay_enable);
        radioButtonPayPal.setChecked(App.paypal_enable && !App.liqpay_enable);
        radioButtonYandex.setChecked(App.yandex_enable && !App.liqpay_enable && !App.paypal_enable);
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
    public void sendError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void no_session() {

    }

    @Override
    public void payment_success() {

    }

    @Override
    public void payment_liqpay(PaymentResult paymentResult) {
        if (paymentResult != null && paymentResult.getPaymentStatus() == PaymentLiqPayStatuses.success
                && paymentResult.getTransactionId() != null) {
            sendError(getString(R.string.success_pay));
            setResult(RESULT_OK);
            finish();
        } else if (paymentResult != null) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    //.setTitleText(getString(R.string.dialog_log_out))
                    .setContentText(paymentResult.getFullError())
                    .setConfirmText(getString(R.string.dialog_ok))
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                    })
                    .show();
        }
    }

    @Override
    public void payment_paypal(BuyAdv.DataObg paypalObg) {
        PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(Constants.PAYPALCLIENTID)
                .merchantName(paypalObg.getCustom());
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, paypalObg);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    public void payment_yandex(BuyAdv.DataObg dataObg) {
        YandexMoneyHelper.create(this).sendPay(dataObg);
    }

    private PayPalPayment getThingToBuy(String paymentIntent, BuyAdv.DataObg dataObg) {
        return new PayPalPayment(new BigDecimal(Double.valueOf(dataObg.getQuantity()) / 100), "USD", dataObg.getCustom(),
                paymentIntent).custom(dataObg.getCustom());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    sendError(getString(R.string.success_pay));
                    setResult(RESULT_OK);
                    finish();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        //.setTitleText(getString(R.string.dialog_log_out))
                        .setContentText(getString(R.string.user_canceled))
                        .setConfirmText(getString(R.string.dialog_ok))
                        .setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismissWithAnimation();
                        })
                        .show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        //.setTitleText(getString(R.string.dialog_log_out))
                        .setContentText(getString(R.string.unknown_error))
                        .setConfirmText(getString(R.string.dialog_ok))
                        .setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismissWithAnimation();
                        })
                        .show();

            }

        }
        if (requestCode == YandexMoneyHelper.REQUEST_CODE_TOKENIZE) {
            switch (resultCode) {
                case RESULT_OK:
                    // successful tokenization
                    TokenizationResult result = Checkout.createTokenizationResult(data);
                    Log.d("777777", "");
                    sendError(getString(R.string.success_pay));
                    setResult(RESULT_OK);
                    finish();
                    break;
                case RESULT_CANCELED:
                    // user canceled tokenization
                   // TokenizationResult result2 = Checkout.createTokenizationResult(data);
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            //.setTitleText(getString(R.string.dialog_log_out))
                            .setContentText(getString(R.string.unknown_error))
                            .setConfirmText(getString(R.string.dialog_ok))
                            .setConfirmClickListener(sweetAlertDialog -> {
                                sweetAlertDialog.dismissWithAnimation();
                            })
                            .show();
                    break;
            }
        }
    }

}
