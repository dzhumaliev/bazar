package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.IPriceInterval;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.IPriceParamInterval;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.PriceParamInterval;

public class DinParamsTypePrice extends LinearLayout implements IPriceParamInterval {

    private TextView tvTitle;
    private TextView tvTtext;
    ImageButton ibClear;
    Context ctx;
    FragmentManager fm;
    IPriceInterval iPriceInterval;
    boolean is_nec;
    public DinParamsTypePrice(Context context) {
        super(context);
    }

    public DinParamsTypePrice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DinParamsTypePrice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTitle = findViewById(R.id.tv_title);
        tvTtext = findViewById(R.id.tv_text);
        ibClear = findViewById(R.id.ibClear);
    }

    public void setData(Context context, DinProps props, FragmentManager fm, IPriceInterval iPriceInterval) {
        this.iPriceInterval=iPriceInterval;
        this.is_nec=((ParamFilterPostSingleton.getInstance().getP_less()>0)||(ParamFilterPostSingleton.getInstance().getP_more()>0));

        ctx = context;
        this.fm = fm;
        //tvTtext.setHint(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")" : ""));
        tvTtext.setHint(context.getString(R.string.no_matter));
        tvTitle.setText(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")" : ""));
        setTag(props);
        ibClear.setOnClickListener(view -> {
            tvTtext.setText("");
            ibClear.setVisibility(GONE);
            iPriceInterval.getPrice(0,0,"");
        });
        setClickable(true);
        this.setOnClickListener(view -> {
            PriceParamInterval dinParamInterval;
            dinParamInterval = PriceParamInterval.newInstance(this,is_nec,tvTitle.getText().toString());
            dinParamInterval.show(fm, "dinParamInterval");
        });
        tvTtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tvTtext.getText().toString().trim().length() > 0) {

                    ibClear.setVisibility(VISIBLE);
                }else{
                    ibClear.setVisibility(GONE);
                }
            }
        });
        if(ParamFilterPostSingleton.getInstance().getP_more()>0||ParamFilterPostSingleton.getInstance().getP_less()>0){
            set_params(context);
        }

    }


    void set_params(Context ctx) {

        String more,less;
        if (ParamFilterPostSingleton.getInstance().getP_more()>0) {
            more=String.valueOf(ParamFilterPostSingleton.getInstance().getP_more());
        } else {
            more="";
        }
        if (ParamFilterPostSingleton.getInstance().getP_less()>0) {
            less=String.valueOf(ParamFilterPostSingleton.getInstance().getP_less());
        } else {
            less="";
        }
        String text = "";
        if (TextUtils.isEmpty(less) && TextUtils.isEmpty(more)) {

        } else if (TextUtils.isEmpty(less) && !TextUtils.isEmpty(more)) {
            text = String.format(ctx.getString(R.string.and_more),more);
        } else if (!TextUtils.isEmpty(less) && TextUtils.isEmpty(more)) {
            text = String.format(ctx.getString(R.string.and_less),less);
        } else if (!TextUtils.isEmpty(less) && !TextUtils.isEmpty(more)) {
            text = String.format(ctx.getString(R.string.more_les),more,less);
        }
        tvTtext.setText(Html.fromHtml(text));



    }

    @Override
    public void getText(int p_more, int p_less,String s) {
        tvTtext.setText(Html.fromHtml(s));
        tvTitle.setVisibility(VISIBLE);
        this.is_nec=true;
        iPriceInterval.getPrice( p_more,  p_less, s);
    }
}