package solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.JsonArray;

import java.util.ArrayList;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.view.MyPhonesView;

public class MyPhonesLayutAdAdd extends LinearLayout implements MyFildForAddAd.OnClickBtnAdd{
    private int max_fild = 5;
    private JsonArray jsonArray = new JsonArray();

    public MyPhonesLayutAdAdd(Context context) {
        this(context, null);
    }

    public int getMax_fild() {
        return max_fild;
    }

    public void setMax_fild(int max_fild) {
        this.max_fild = max_fild;
    }

    public MyPhonesLayutAdAdd(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyPhonesLayutAdAdd(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
    }

    private void findViews() {

    }

    private void init(Context context, AttributeSet attrs) {

        // enforces vertical orientation
        setOrientation(LinearLayout.VERTICAL);

    }

    public void setData(ArrayList<String> phones) {
        removeAllViews();
        jsonArray=new JsonArray();
        if (phones.size() > 0) {
            for (String s : phones) {
                MyFildForAddAd phonesView = new MyFildForAddAd(this.getContext());
                phonesView.setHintText(getResources().getString(R.string.edit_phone));
                phonesView.setText(s);
                phonesView.setInputTypeText(InputType.TYPE_CLASS_PHONE);
                phonesView.setInterfaceClick(this);
                addView(phonesView);
            }

        } else {
            MyFildForAddAd phonesView = new MyFildForAddAd(this.getContext());
            phonesView.setInputTypeText(InputType.TYPE_CLASS_PHONE);
            phonesView.setHintText(getResources().getString(R.string.edit_phone));
            phonesView.setInterfaceClick(this);
            addView(phonesView);
        }
        setBtnAdd();
    }

    private void setBtnAdd() {
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            MyFildForAddAd view = (MyFildForAddAd) getChildAt(i);

            view.setVisibleBtnAdd(View.GONE);
            if (i == size - 1) {
                view.setVisibleBtnAdd(View.VISIBLE);
            }
        }
    }

    @Override
    public void onclickAdd() {
        addFild();
    }

    private void addFild() {
        if (getChildCount() < max_fild) {
            MyFildForAddAd phonesView = new MyFildForAddAd(this.getContext());
            phonesView.setInputTypeText(InputType.TYPE_CLASS_PHONE);
            phonesView.setHintText(getResources().getString(R.string.edit_phone));
            phonesView.setInterfaceClick(this);
            addView(phonesView);
            setBtnAdd();
        }
    }

    public boolean getPhones() {
        boolean is_error = false;
        int size = getChildCount();
        jsonArray=new JsonArray();
        for (int i = 0; i < size; i++) {
            MyFildForAddAd view = (MyFildForAddAd) getChildAt(i);
            if (!TextUtils.isEmpty(view.getText().toString())) {
                jsonArray.add(view.getText().toString());
            }
            if (!isValidPhone(view.getText().toString())) {
                is_error = true;
                view.setError(getResources().getString(R.string.error_invalid_phone));
            }
        }
        return is_error;
    }

    public JsonArray getPhonesList() {
        return jsonArray;
    }

    private boolean isValidPhone(String phone) {
        return (TextUtils.isEmpty(phone)?true: Patterns.PHONE.matcher(phone).matches());
    }
}
