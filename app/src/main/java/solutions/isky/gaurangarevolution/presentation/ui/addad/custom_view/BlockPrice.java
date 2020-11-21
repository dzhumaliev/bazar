package solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;


import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.Currency;
import solutions.isky.gaurangarevolution.data.models.PriceEx;
import solutions.isky.gaurangarevolution.data.models.PriceSetting;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.currencyview.NiceSpinner;

public class BlockPrice extends LinearLayout {
    private RadioGroup groupPrice;
    private RadioButton rbPrice;
    private RadioButton rbFreePrice;
    private RadioButton rbexchange;
    private CheckBox cb_auction;
    private CheckBox cb_contract_price;
    private MyFildForAddAd et_text_price;
    private NiceSpinner niceSpinner;
    private String mPrice = "";
    View ll_auction;
    View ll_contract_price;
    PriceSetting settings;
    private int mIDPriceCur=-1;
    private static int PRICE_EX_PRICE = 0;// Базовая настройка цены
    private static int PRICE_EX_MOD = 1; // Модификатор (Торг, По результатам собеседования)
    private static int PRICE_EX_EXCHANGE = 2; // Обмен
    private static int PRICE_EX_FREE = 4; // Бесплатно (Даром)
    private static int PRICE_EX_AGREED = 8; // Договорная

    private JsonArray price_ex;

    public BlockPrice(Context context) {
        this(context, null, 0);
    }

    public BlockPrice(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlockPrice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.block_price, this, true);
        initView();
    }

    private void initView() {
        groupPrice = findViewById(R.id.groupPrice);
        niceSpinner = findViewById(R.id.cur_spinner);
        rbPrice = findViewById(R.id.rbPrice);
        rbFreePrice = findViewById(R.id.rbFreePrice);
        rbexchange = findViewById(R.id.rbexchange);
        et_text_price = findViewById(R.id.fild_price);
        cb_auction = findViewById(R.id.cb_auction);
        cb_contract_price = findViewById(R.id.cb_contract_price);
        ll_auction = findViewById(R.id.ll_auction);
        ll_contract_price = findViewById(R.id.ll_contract_price);
        cb_contract_price.setOnCheckedChangeListener((compoundButton, b) -> {
            setViewContract_price(b);
            if (b) {
                price_ex = new JsonArray();
                price_ex.add(PRICE_EX_AGREED);
            } else {
                price_ex = new JsonArray();
            }
        });
        cb_auction.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                price_ex = new JsonArray();
                price_ex.add(PRICE_EX_MOD);
            } else {
                price_ex = new JsonArray();
            }
        });
        groupPrice.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rbPrice:
                    setViewPrice();
                    price_ex = new JsonArray();
                    price_ex.add(PRICE_EX_PRICE);
                    break;
                case R.id.rbFreePrice:
                    setViewFreePrice();
                    price_ex = new JsonArray();
                    price_ex.add(PRICE_EX_FREE);
                    break;
                case R.id.rbexchange:
                    setViewExchange();
                    price_ex = new JsonArray();
                    price_ex.add(PRICE_EX_EXCHANGE);
                    break;
            }
        });

        et_text_price.setTextWatcher(textWatcherPrice);

    }

    public void setSettings(PriceSetting settings) {
        this.settings = settings;
        setSettingView();

    }

    public void setPattern(int[] pattern, String separator) {
        et_text_price.setPattern(pattern, separator);
    }

    TextWatcher textWatcherPrice = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //setPrice(s.toString());

        }
    };

    private void setPrice(String price) {
        if (!mPrice.equalsIgnoreCase(price)) {
            String text = price.replaceAll(" ", "");
            mPrice = text.replaceAll("\\B(?=(\\d{3})+(?!\\d))", " ");
            setText(mPrice);
        }
    }

    public void setViewEdit(PriceEx priceEx,int cur) {
        if (priceEx != null) {
            if (priceEx.getFree() == 1) {
                rbFreePrice.setChecked(true);
            }
            if (priceEx.getExchange() == 1) {
                rbexchange.setChecked(true);
            }
            if (priceEx.getMod() == 1) {
                cb_auction.setChecked(true);
            }
            if (priceEx.getAgreed() == 1) {
                cb_contract_price.setChecked(true);
            }
            mIDPriceCur=cur;
            invalidate();
        }

    }

    public void setText(String price) {
        et_text_price.setText(String.valueOf(price));
    }

    public void resetBlock() {
        rbPrice.setChecked(true);
        cb_auction.setChecked(false);
        cb_contract_price.setChecked(false);
    }

    public JsonArray getPrice_ex() {
        return (price_ex != null) ? price_ex : new JsonArray();
    }

    private void setSettingView() {
        rbFreePrice.setEnabled(settings.getFree() == 1);
        rbexchange.setEnabled(settings.getExchange() == 1);
        if (!TextUtils.isEmpty(settings.getPrice_title())) {
            et_text_price.setHintText(settings.getPrice_title());
        }
        ll_contract_price.setVisibility((settings.getAgreed() == 0) ? GONE : VISIBLE);
        ll_auction.setVisibility((settings.getMod() == 0) ? GONE : VISIBLE);

        if (!TextUtils.isEmpty(settings.getMod_title())) {
            cb_auction.setText(settings.getMod_title());

        }

        invalidate();
    }

    public void setCurencyList(List<Currency> curencyList) {
        if(curencyList.size()>0){
            niceSpinner.attachDataSource(curencyList);
            niceSpinner.setSelectedID(mIDPriceCur>=0?mIDPriceCur:settings.getCurr());
            mIDPriceCur=-1;
        }

    }
    public int getCurrencyID() {
        return niceSpinner.getSelectedCurrencyID();
    }
    private void setViewPrice() {
        et_text_price.setEnabledTextPrice(true);
        cb_auction.setChecked(false);
        cb_contract_price.setChecked(false);
        et_text_price.setVisibility(VISIBLE);
        niceSpinner.setVisibility(VISIBLE);
        if (settings != null && settings.getMod() == 1) {
            ll_auction.setVisibility(VISIBLE);
        }
        if (settings != null && settings.getAgreed() == 1) {
            ll_contract_price.setVisibility(VISIBLE);
        }

    }

    private void setViewFreePrice() {
        //et_text.setText("");
        cb_auction.setChecked(false);
        cb_contract_price.setChecked(false);
        et_text_price.setVisibility(GONE);
        niceSpinner.setVisibility(GONE);
        ll_auction.setVisibility(GONE);
        ll_contract_price.setVisibility(GONE);

    }

    private void setViewExchange() {
        // et_text.setText("");
        cb_auction.setChecked(false);
        cb_contract_price.setChecked(false);
        et_text_price.setVisibility(GONE);
        ll_auction.setVisibility(GONE);
        ll_contract_price.setVisibility(GONE);
    }

    private void setViewContract_price(boolean b) {
        if (b) {
            // et_text.setText("");
            cb_auction.setChecked(false);
            et_text_price.setVisibility(GONE);
            niceSpinner.setVisibility(GONE);
            ll_auction.setVisibility(GONE);
        } else {
            et_text_price.setVisibility(VISIBLE);
            niceSpinner.setVisibility(VISIBLE);
            if (settings != null && settings.getMod() == 1) {
                ll_auction.setVisibility(VISIBLE);
            }
        }
    }

    public String getText() {
        return et_text_price.getText().toString();
    }
}
