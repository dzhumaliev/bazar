package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import solutions.isky.gaurangarevolution.R;

public class MyPhonesLayut extends LinearLayout implements MyPhonesView.OnClickBtnAdd {

    private int max_fild = 5;
    private JsonArray jsonArray = new JsonArray();

    public MyPhonesLayut(Context context) {
        this(context, null);
    }

    public int getMax_fild() {
        return max_fild;
    }

    public void setMax_fild(int max_fild) {
        this.max_fild = max_fild;
    }

    public MyPhonesLayut(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyPhonesLayut(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        jsonArray = new JsonArray();
        if (phones.size() > 0) {
            for (String s : phones) {
                MyPhonesView phonesView = new MyPhonesView(this.getContext());
                phonesView.setPhonetext(s);
                phonesView.setInterfaceClick(this);
                addView(phonesView);
            }

        } else {
            MyPhonesView phonesView = new MyPhonesView(this.getContext());
            phonesView.setInterfaceClick(this);
            addView(phonesView);
        }
        setBtnAdd();
    }

    private void setBtnAdd() {
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            MyPhonesView view = (MyPhonesView) getChildAt(i);
            view.setVisibleBtnAdd(View.GONE);
            if (i == size - 1 && max_fild != size) {
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
            MyPhonesView phonesView = new MyPhonesView(this.getContext());
            phonesView.setInterfaceClick(this);
            addView(phonesView);
            setBtnAdd();
        }
    }

    public boolean getPhones() {
        boolean is_error = false;
        jsonArray = new JsonArray();
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            MyPhonesView view = (MyPhonesView) getChildAt(i);
            if (!TextUtils.isEmpty(view.getText().toString())) {
                jsonArray.add(view.getText().toString());
            }
            if (!isValidPhone(view.getText().toString())) {
                is_error = true;
                view.setError();
            }
        }
        return is_error;
    }

    public JsonArray getPhonesList() {
        return jsonArray;
    }

    public ArrayList<String> stringList() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(jsonArray.get(i).getAsString());
        }
        return list;
    }

    private boolean isValidPhone(String phone) {
        return (TextUtils.isEmpty(phone) ? true : Patterns.PHONE.matcher(phone).matches());
    }
}
