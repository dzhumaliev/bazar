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

import com.google.gson.JsonObject;

import java.util.HashMap;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.IDinParamInterval;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.TypeFilterAdapt;

public class DinParamsType10 extends LinearLayout implements IDinParamInterval {

    private TextView tvTitle;
    private TextView tvTtext;
    ImageButton ibClear;
    Context ctx;
    FragmentManager fm;
    HashMap<Integer, ItemFilterModel> props_value;
    DinProps props;
    DinParamsLayout.DinParamsLayoutListener  dinParamsLayoutListener;
    boolean is_nec;
    public DinParamsType10(Context context) {
        super(context);
    }

    public DinParamsType10(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DinParamsType10(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTtext = (TextView) findViewById(R.id.tv_text);
        ibClear = findViewById(R.id.ibClear);

    }

    public void setData(Context context, DinProps props, FragmentManager fm, boolean is_nece) {
        dinParamsLayoutListener=(DinParamsLayout.DinParamsLayoutListener)context;
        this.is_nec=is_nece;
        props_value=new HashMap<>();
        this.props=props;
        ctx = context;
        this.fm = fm;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // tvTtext.setText(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + Html.fromHtml(props.getUnit(),Html.FROM_HTML_MODE_COMPACT) + ")" : ""));
            tvTitle.setText(Html.fromHtml(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""),Html.FROM_HTML_MODE_COMPACT));
        } else {
            //   tvTtext.setText(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + Html.fromHtml(props.getUnit()) + ")" : ""));
            tvTitle.setText(Html.fromHtml(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : "")));
        }
        setTag(props);
        ibClear.setOnClickListener(view -> {
            tvTtext.setText("");
            ibClear.setVisibility(GONE);
            props_value.clear();
            dinParamsLayoutListener.onFinishParamsEdit(props_value,props.getId());
        });
        setClickable(true);

        this.setOnClickListener(view -> {
            DinParamInterval dinParamInterval;
            if(this.is_nec){
                if(props_value!=null&&props_value.size()>0){
                    dinParamInterval = DinParamInterval.newInstance(this,props,this.is_nec,props_value,props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""));
                }else{
                    dinParamInterval = DinParamInterval.newInstance(this,props,this.is_nec,null,props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""));
                }
            }else{
                dinParamInterval = DinParamInterval.newInstance(this,props,this.is_nec,null,props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""));
            }

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
        if(is_nece){
            set_params(context);
        }

    }

    @Override
    public void getText(String s,HashMap<Integer, ItemFilterModel> props_value, int id_prop) {
        tvTtext.setText(Html.fromHtml(s));
        tvTitle.setVisibility(VISIBLE);
        this.props_value=props_value;
        this.is_nec=true;
        dinParamsLayoutListener.onFinishParamsEdit(props_value,props.getId());
    }
    public JsonObject get_props(){
        return (JsonObject) props_value.get(props.getId()).getObject();
    }

    void set_params(Context ctx) {
        if (ParamFilterPostSingleton.getInstance().getmProps_value().containsKey(props.getId())) {
            JsonObject object = (JsonObject) ParamFilterPostSingleton.getInstance().getmProps_value().get(props.getId()).getObject();
            String more,less;
            if (object.has("v_more")) {
                more=(object.get("v_more").getAsString());
            } else {
                more="";
            }
            if (object.has("v_less")) {
                less=(object.get("v_less").getAsString());
            } else {
                less="";
            }
            String text = "";
            if (TextUtils.isEmpty(less) && TextUtils.isEmpty(more)) {

            } else if (TextUtils.isEmpty(less) && !TextUtils.isEmpty(more)) {
                text = String.format(ctx.getString(R.string.and_more),more);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("n", props.getId());
                jsonObject.addProperty("v_more", more);
                // jsonObject.addProperty("v_less", less);
                props_value = new HashMap<>();
                ItemFilterModel filterModel=new ItemFilterModel();
                filterModel.setId_prop(props.getId());
                filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
                filterModel.setTitle(props.getTitle());
                filterModel.setText(text);
                filterModel.setObject(jsonObject);
                props_value.put(props.getId(), filterModel);
            } else if (!TextUtils.isEmpty(less) && TextUtils.isEmpty(more)) {
                text = String.format(ctx.getString(R.string.and_less),less);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("n", props.getId());
                //jsonObject.addProperty("v_more", more);
                jsonObject.addProperty("v_less", less);
                props_value = new HashMap<>();
                ItemFilterModel filterModel=new ItemFilterModel();
                filterModel.setId_prop(props.getId());
                filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
                filterModel.setTitle(props.getTitle());
                filterModel.setText(text);
                filterModel.setObject(jsonObject);
                props_value.put(props.getId(), filterModel);
            } else if (!TextUtils.isEmpty(less) && !TextUtils.isEmpty(more)) {
                text = String.format(ctx.getString(R.string.more_les),more,less);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("n", props.getId());
                jsonObject.addProperty("v_more", more);
                jsonObject.addProperty("v_less", less);
                props_value = new HashMap<>();
                ItemFilterModel filterModel=new ItemFilterModel();
                filterModel.setId_prop(props.getId());
                filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
                filterModel.setTitle(props.getTitle());
                filterModel.setText(text);
                filterModel.setObject(jsonObject);
                props_value.put(props.getId(), filterModel);

            }
            tvTtext.setText(Html.fromHtml(text));
        }


    }
}