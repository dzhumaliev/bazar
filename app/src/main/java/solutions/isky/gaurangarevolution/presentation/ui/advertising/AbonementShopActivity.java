package solutions.isky.gaurangarevolution.presentation.ui.advertising;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;
import com.luck.picture.lib.permissions.RxPermissions;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.mikepenz.materialize.MaterializeBuilder;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
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
import solutions.isky.gaurangarevolution.data.models.SvcAbonementItem;
import solutions.isky.gaurangarevolution.data.models.SvcModel;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.custom_dialog.SweetAlertDialog;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.mvp.advertising.AbonementShopPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.advertising.IAbonement;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.YandexMoneyHelper;


public class AbonementShopActivity extends MvpAppCompatActivity implements IAbonement {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar4)
    ProgressBar progressBar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private User user;
    private FastItemAdapter<SvcAbonementItem> fastItemAdapter;
    private static final int REQUEST_CODE_PAYMENT = 174;
    private static final int REQUEST_CODE_TOKENIZE = 214;


    @InjectPresenter
    AbonementShopPresenter abonementShopPresenter;
    private LinearLayoutManager linearLayoutManager;

    private String mIdShop = null;
    private int mIdSvc = 0;
    private String mMethod = Constants.BALANCE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonement_shop);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        user = new Gson().fromJson(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_DATA_JSON), User.class);
        //advertisingPresenter.getUserInfo();
        mIdShop = String.valueOf(user.getShop_id());
        new MaterializeBuilder().withActivity(this).build();
        fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.withSelectable(true);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(fastItemAdapter);
        fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
            if (!item.isSelected()) {
                Set<Integer> selections = adapter.getFastAdapter().getSelections();
                if (!selections.isEmpty()) {
                    int selectedPosition = selections.iterator().next();
                    adapter.getFastAdapter().deselect();
                    adapter.getFastAdapter().notifyItemChanged(selectedPosition);
                }
                adapter.getFastAdapter().select(position);
            } else {
                adapter.getFastAdapter().notifyItemChanged(position);
            }
            return false;
        });
        fastItemAdapter.withEventHook(new ClickEventHook<SvcAbonementItem>() {
            @Override
            public void onClick(View v, int position, FastAdapter<SvcAbonementItem> fastAdapter, SvcAbonementItem svcAbonementItem) {

                if (v.getId() == R.id.btn_bye) {
                    mIdSvc = svcAbonementItem.getId();
                    String period="0";
                    if(svcAbonementItem.getPrice_free()==0){
                        period=svcAbonementItem.getPeriod();
                    }else{
                        period=svcAbonementItem.getPrice_free_period()+"";
                    }
                    mMethod=svcAbonementItem.getPositionPC();
                    switch (mMethod){
                        case Constants.BALANCE:
                            abonementShopPresenter.buySvc(mIdShop, mIdSvc,period, mMethod);
                            break;
                        case Constants.LIQPAYBALANCE:
                            String finalPeriod = period;
                            new RxPermissions(AbonementShopActivity.this)
                                    .request(Manifest.permission.READ_PHONE_STATE)
                                    .subscribe(granted -> {
                                        if (granted) {
                                            abonementShopPresenter.buySvc(mIdShop, mIdSvc, finalPeriod, mMethod);
                                        } else {
                                            sendError(getString(R.string.permission_phone_liqpay_need));
                                        }
                                    });
                            break;
                        case Constants.PAYPALBALANCE:
                            abonementShopPresenter.buySvc(mIdShop, mIdSvc,period, mMethod);
                            break;
                        case Constants.YANDEXBALANCE:
                            abonementShopPresenter.buySvc(mIdShop, mIdSvc,period, mMethod);
                            break;
                    }

                }
            }

            @Nullable
            @Override
            public List<View> onBindMany(@NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof SvcAbonementItem.ViewHolder) {
                    List<View> views = new ArrayList<>();
                    views.add(((SvcAbonementItem.ViewHolder) viewHolder).btn_bye);
                    //views.add(((SvcModel.ViewHolder) viewHolder).ll_btn);
                    return views;
                }
                return super.onBindMany(viewHolder);
            }
        });
        abonementShopPresenter.getSvcAbonement(mIdShop);
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
    public void listAds(List<SvcAbonementItem> svcModels) {
        fastItemAdapter.add(svcModels);
    }

    @Override
    public void payment_success() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void payment_liqpay(PaymentResult paymentResult) {
        if (paymentResult!=null&&paymentResult.getPaymentStatus() == PaymentLiqPayStatuses.success
                && paymentResult.getTransactionId() != null) {
            sendError(getString(R.string.success_pay));
            setResult(RESULT_OK);
            finish();
        }else if(paymentResult!=null){
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
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE,paypalObg);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    public void payment_yandex(BuyAdv.DataObg dataObg) {

        YandexMoneyHelper.create(this).sendPay(dataObg);
    }

    private PayPalPayment getThingToBuy(String paymentIntent,BuyAdv.DataObg dataObg) {
        return new PayPalPayment(new BigDecimal(Double.valueOf(dataObg.getQuantity())/100), "USD", dataObg.getCustom(),
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
    public void sendError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void no_session() {
        finish();
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
                    Log.d("777777","");
                    sendError(getString(R.string.success_pay));
                    setResult(RESULT_OK);
                    finish();
                    break;
                case RESULT_CANCELED:
                    // user canceled tokenization

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
