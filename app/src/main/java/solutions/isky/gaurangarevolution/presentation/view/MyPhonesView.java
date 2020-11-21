package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import solutions.isky.gaurangarevolution.R;

public class MyPhonesView extends LinearLayout {

    protected TextView mTitle;
    protected TextView mError;
    protected MyCustomEditText mEditText;
    protected ImageView btn_add_phone;
    OnClickBtnAdd onClickBtnAdd;

    public MyPhonesView(Context context) {
        this(context, null);
    }

    public void setInterfaceClick(OnClickBtnAdd onClickBtnAdd) {
        this.onClickBtnAdd = onClickBtnAdd;
    }

    public MyPhonesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyPhonesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
    }

    private void findViews() {
        mTitle = findViewById(R.id.text_title);
        mError = findViewById(R.id.text_login);
        mEditText = findViewById(R.id.et_phone_text);
        btn_add_phone = findViewById(R.id.btn_add_phone);
        btn_add_phone.setOnClickListener(v -> {
            if (onClickBtnAdd != null)
                onClickBtnAdd.onclickAdd();
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mError.setVisibility(GONE);
                mTitle.setVisibility(VISIBLE);
            }
        });
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.item_phone_my_profile, this, true);
        // enforces vertical orientation
        setOrientation(LinearLayout.VERTICAL);
        findViews();
    }

    @Nullable
    public CharSequence getText() {
        if (mEditText == null) {
            return "";
        }
        return mEditText.getText().toString().trim();
    }

    public void setPhonetext(String phone) {
        mEditText.setText(phone);
    }

    public void setTextError(@Nullable CharSequence text) {
        mError.setText(text);
        mError.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTitle.setVisibility(TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
    }

    public void setVisibleBtnAdd(int visible) {
        btn_add_phone.setVisibility(visible);
    }

    public interface OnClickBtnAdd {
        void onclickAdd();
    }

    public void setError(){
        mError.setVisibility(VISIBLE);
        mTitle.setVisibility(GONE);
    }
}
