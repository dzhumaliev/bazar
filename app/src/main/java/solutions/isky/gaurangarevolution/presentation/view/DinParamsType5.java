package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
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
import solutions.isky.gaurangarevolution.presentation.mvp.filters.TypeFilterAdapt;

public class DinParamsType5 extends LinearLayout {

    // private TextView tvTitle;
    private TextView tvTtext;
    ImageButton ibClear;
    CheckBox checkBox;
    Context ctx;
    HashMap<Integer, ItemFilterModel> props_value;
    DinProps props;
    DinParamsLayout.DinParamsLayoutListener dinParamsLayoutListener;
    boolean is_nec;

    public DinParamsType5(Context context) {
        super(context);
    }

    public DinParamsType5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DinParamsType5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTtext = (TextView) findViewById(R.id.tv_text);
        ibClear = findViewById(R.id.ibClear);
        checkBox = findViewById(R.id.checkbox);
    }

    public void setData(Context context, DinProps props, boolean is_nece) {
        dinParamsLayoutListener = (DinParamsLayout.DinParamsLayoutListener) context;
        this.is_nec = is_nece;
        props_value = new HashMap<>();
        //checkBox.setEnabled(true);
        this.props = props;
        ctx = context;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // tvTtext.setText(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + Html.fromHtml(props.getUnit(),Html.FROM_HTML_MODE_COMPACT) + ")" : ""));
            tvTtext.setText(Html.fromHtml(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""),Html.FROM_HTML_MODE_COMPACT));
        } else {
            //   tvTtext.setText(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + Html.fromHtml(props.getUnit()) + ")" : ""));
            tvTtext.setText(Html.fromHtml(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : "")));
        }
        setTag(props);
        ibClear.setOnClickListener(view -> {

            ibClear.setVisibility(GONE);
            props_value.clear();
            checkBox.setChecked(false);
            props_value.clear();
            dinParamsLayoutListener.onFinishParamsEdit(props_value, props.getId());
        });
        setClickable(true);
        this.setOnClickListener(view -> {
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
            } else {
                checkBox.setChecked(true);
            }
        });
        checkBox.setOnCheckedChangeListener((compoundButton, b) ->
        {
            int v = 0;
            if (b) {
                v = 1;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("n", props.getId());
                jsonObject.addProperty("v", v);
                // jsonObject.addProperty("v_less", less);
                props_value = new HashMap<>();
                ItemFilterModel filterModel = new ItemFilterModel();
                filterModel.setId_prop(props.getId());
                filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
                filterModel.setTitle(props.getTitle());
                filterModel.setText(ctx.getString(R.string.extra));
                filterModel.setObject(jsonObject);
                props_value.put(props.getId(), filterModel);
                dinParamsLayoutListener.onFinishParamsEdit(props_value, props.getId());
            } else {
                v = 0;
                props_value.clear();
                dinParamsLayoutListener.onFinishParamsEdit(props_value, props.getId());
            }


        });
        if (is_nece) {
            set_params(context);
        }

    }


    void set_params(Context ctx) {
        if (ParamFilterPostSingleton.getInstance().getmProps_value().containsKey(props.getId())) {
            JsonObject object = (JsonObject) ParamFilterPostSingleton.getInstance().getmProps_value().get(props.getId()).getObject();
            int v = 0;
            if (object.has("v")) {
                try {
                    v = (object.get("v").getAsInt());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (v > 0) {
                checkBox.setChecked(true);
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("n", props.getId());
            jsonObject.addProperty("v", v);
            // jsonObject.addProperty("v_less", less);
            props_value = new HashMap<>();
            ItemFilterModel filterModel = new ItemFilterModel();
            filterModel.setId_prop(props.getId());
            filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
            filterModel.setTitle(props.getTitle());
            filterModel.setText(ctx.getString(R.string.extra));
            filterModel.setObject(jsonObject);
            props_value.put(props.getId(), filterModel);

        }
    }
}