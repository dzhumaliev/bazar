package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import solutions.isky.gaurangarevolution.R;

/**
 * Created by Serzhik on 28.03.2018.
 */

public class MyEditTextLayout extends LinearLayout {
    private TextView tvError;
    private MyCustomEditText editText;

    public MyEditTextLayout(Context context) {
        this(context, null);
    }

    public MyEditTextLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public MyEditTextLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs,defStyleAttr);

    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_edittext_with_error, this, true);

    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvError = findViewById(R.id.text_error);
        editText = findViewById(R.id.text_descr);
    }
    private TextView getTvError(){
        return tvError;
    }

    public void setError(String error) {
        tvError.setVisibility(VISIBLE);
        tvError.setText(error);
    }

    public void clearError() {
        tvError.setVisibility(INVISIBLE);
        tvError.setText("");
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        init(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MEditTextLayout, defStyleAttr, 0);
        if (editText == null) {
            editText = findViewById(R.id.text_descr);;}

        editText.setDisableEmoji(a.getBoolean(R.styleable.MEditTextLayout_m_disableEmoji, false));
        int inputType=(a.getInt(R.styleable.MEditTextLayout_android_inputType, EditorInfo.TYPE_NULL));
        if (inputType != EditorInfo.TYPE_NULL) {
            editText.setInputType(inputType);
        }
        editText.setHint(a.getString(R.styleable.MEditTextLayout_android_hint));
        editText.setMaxLines(a.getInt(R.styleable.MEditTextLayout_android_maxLines,1));
    }
}
