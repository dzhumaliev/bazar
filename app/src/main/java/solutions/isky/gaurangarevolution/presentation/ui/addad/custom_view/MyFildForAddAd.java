package solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params.ClearEditTextListener;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;
import solutions.isky.gaurangarevolution.presentation.view.MyPhonesView;

public class MyFildForAddAd extends LinearLayout {
    private TextView tvError;
    private MyCustomEditText editText;
    CheckBox checkBox;
    private ImageView img_click_arrow;
    private ImageView btn_clear_text;
    private boolean disableArrow; // disable clear drawable.
    private boolean enableBtnClear; // disable clear drawable.
    OnClickTextFild onClickTextFild;
    OnClickBtnAdd onClickBtnAdd;
    ClearEditTextListener clearEditTextListener;
    protected ImageView btn_add_phone;

    public MyFildForAddAd(Context context) {
        this(context, null);
    }

    public void setOnClearTExtListener(ClearEditTextListener clearEditTextListener) {
        this.clearEditTextListener = clearEditTextListener;
    }

    public MyFildForAddAd(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //initView();
    }

    private void initView() {
        tvError = findViewById(R.id.text_error);
        editText = findViewById(R.id.text_descr);
        img_click_arrow = findViewById(R.id.img_arrow);
        btn_add_phone = findViewById(R.id.btn_add_phone);
        btn_clear_text = findViewById(R.id.btn_clear_text);
        checkBox = findViewById(R.id.checkBox);
        btn_add_phone.setOnClickListener(v -> {
            if (onClickBtnAdd != null)
                onClickBtnAdd.onclickAdd();
        });

        editText.setOnClickListener(v -> {
            if (onClickTextFild != null)
                if (editText.isClickable())
                    onClickTextFild.setOnClickListenerText();
        });
        editText.addTextChangedListener(cauntListener);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editText.setHintTextColor(Color.BLACK);
            } else {
                if(checkBox.getVisibility()==View.VISIBLE){
                    editText.setHintTextColor(getResources().getColor(R.color.color_hint_search));
                }else{
                    editText.setHintTextColor(Color.BLACK);
                }

            }
        });
        btn_clear_text.setOnClickListener(v -> {
            editText.setText("");
        });
    }

    public MyFildForAddAd(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_fild_add_ad, this, true);
        initView();
    }
    public void setMaxLines(int maxLines) {
        editText.setSingleLine(false);
        editText.setMaxLines(maxLines);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        init(context);
        setOrientation(VERTICAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyFildAdAdd, defStyleAttr, 0);

        disableArrow = a.getBoolean(R.styleable.MyFildAdAdd_my_disableArrow, true);
        enableBtnClear = a.getBoolean(R.styleable.MyFildAdAdd_my_isVisibleCkear, false);

        img_click_arrow.setVisibility(disableArrow ? GONE : VISIBLE);
        String hint_text = a.getString(R.styleable.MyFildAdAdd_my_text_hint);
        boolean is_necessarily = a.getBoolean(R.styleable.MyFildAdAdd_my_isNecessarily, false);
        editText.setClickable(disableArrow ? false : true);
        editText.setFocusable(disableArrow ? true : false);
        //editText.setEnabled(disableArrow ? true : false);
        editText.setFocusableInTouchMode(disableArrow ? true : false);
        if (is_necessarily) {
            SpannableString hint;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                hint = new SpannableString(Html.fromHtml(hint_text, Html.FROM_HTML_MODE_COMPACT) + "*");
            } else {
                hint = new SpannableString(Html.fromHtml(hint_text) + "*");
            }
            hint.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_text_complain)), hint.length() - 1, hint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.setHint(hint);
        } else {
            editText.setHint(Html.fromHtml(hint_text != null ? hint_text : ""));
        }
        int text_lines = a.getInteger(R.styleable.MyFildAdAdd_my_text_lines, 1);
        if (text_lines == 1) {
            editText.setMaxLines(text_lines);
            editText.setSingleLine(true);
        } else {
            editText.setMaxLines(text_lines);
        }

        a.recycle();
    }

    public void setHintAndNecessarily(String hint_text, boolean is_necessarily) {
        if (is_necessarily) {
            SpannableString hint;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                hint = new SpannableString(Html.fromHtml(hint_text, Html.FROM_HTML_MODE_COMPACT) + "*");
            } else {
                hint = new SpannableString(Html.fromHtml(hint_text) + "*");
            }
            hint.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_text_complain)), hint.length() - 1, hint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.setHint(hint);
        } else {
            editText.setHint(Html.fromHtml(hint_text != null ? hint_text : ""));
        }
    }

    public void setHintText(String hint) {
        editText.setHint(hint);
    }

    public void setEnabledTextPrice(boolean b) {
        editText.setEnabled(b);
        editText.setClickable(b);
        if(b){
            editText.setTextColor(Color.BLACK);
        }else{
            editText.setTextColor(getResources().getColor(R.color.color_hint_search));
        }
    }

    public void setText(String text) {
        editText.setText(text);
        editText.setSelection(text.length());
    }
    public void setPattern(int []pattern,String separator) {
        editText.setPattern(pattern,separator);
    }
    public void setOnClickTextFild(OnClickTextFild onClickTextFild) {
        this.onClickTextFild = onClickTextFild;
    }

    public interface OnClickTextFild {
        void setOnClickListenerText();
    }

    public interface OnClickBtnAdd {
        void onclickAdd();
    }

    public void setInterfaceClick(OnClickBtnAdd onClickBtnAdd) {
        this.onClickBtnAdd = onClickBtnAdd;
    }

    public void setVisibleBtnAdd(int visible) {
        btn_add_phone.setVisibility(visible);
    }

    public String getText() {
        return editText.getText().toString().trim();
    }

    TextWatcher cauntListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            if (editable.length() > 0) {
                btn_clear_text.setVisibility(enableBtnClear ? VISIBLE : GONE);
                clearError();
            } else {
                if (clearEditTextListener != null) {
                    clearEditTextListener.etEmpty();
                }
                btn_clear_text.setVisibility(GONE);
            }
            String text = editable.toString().trim();
            if (editable.toString().equalsIgnoreCase(" ")) {
                editable.replace(0, 1, "");
            }

        }
    };
public void setTextWatcher(TextWatcher textWatcher){
    editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
    editText.addTextChangedListener(textWatcher);
}
    public void disableeditText() {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setClickable(true);
       // editText.setEnabled(false);
    }

    public void setCheckBox() {
        checkBox.setVisibility(VISIBLE);
    }

    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
    }

    public boolean isCheack() {
        return checkBox.isChecked();
    }

    public void setInputTypeText(int typeText) {
        editText.setInputType(typeText);
    }

    public void setError(String err) {
        tvError.setVisibility(VISIBLE);
        tvError.setText(err);
    }

    public void clearError() {
        tvError.setVisibility(GONE);
        tvError.setText("");
    }
}
