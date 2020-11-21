package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.HashMap;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.TypeFilterAdapt;

public class DinParamsType4 extends LinearLayout {

    private TextView tvTitle;
    private TextView tvTtext;
    ImageButton ibClear;
    Context ctx;
    HashMap<Integer, ItemFilterModel> props_value;
    DinProps props;
    DinParamsLayout.DinParamsLayoutListener  dinParamsLayoutListener;
    boolean is_nec;
    public DinParamsType4(Context context) {
        super(context);
    }

    public DinParamsType4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DinParamsType4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTtext = (TextView) findViewById(R.id.tv_text);
        ibClear = findViewById(R.id.ibClear);
    }

    public void setData(Context context, DinProps props,boolean is_nece) {
        dinParamsLayoutListener=(DinParamsLayout.DinParamsLayoutListener)context;
        this.is_nec=is_nece;
        props_value=new HashMap<>();
        this.props=props;
        ctx = context;
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
            showMenu(ctx,view);
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


    void set_params(Context ctx) {
        if (ParamFilterPostSingleton.getInstance().getmProps_value().containsKey(props.getId())) {
            JsonObject object = (JsonObject) ParamFilterPostSingleton.getInstance().getmProps_value().get(props.getId()).getObject();
            int v=0;
            if (object.has("v")) {
                try {
                    v=(object.get("v").getAsInt());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            String text = "";
            text =(v>0)? ctx.getString(R.string.yes):ctx.getString(R.string.no);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("n", props.getId());
            jsonObject.addProperty("v", v);
            // jsonObject.addProperty("v_less", less);
            props_value = new HashMap<>();
            ItemFilterModel filterModel=new ItemFilterModel();
            filterModel.setId_prop(props.getId());
            filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
            filterModel.setTitle(props.getTitle());
            filterModel.setType_filter(props.getType());
            filterModel.setProps(props);
            filterModel.setText(text);
            filterModel.setObject(jsonObject);
            props_value.put(props.getId(), filterModel);
            tvTtext.setText(text);
        }
    }
    void showMenu(Context context, View view){
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(R.menu.popup_type4, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            String t="";
            int i=0;
            boolean check=false;
            if (menuItem.getItemId() == R.id.i_yes) {
                t=(ctx.getString(R.string.yes));
                i=1;
                check=true;
            } else if (menuItem.getItemId() == R.id.i_no) {
                t=ctx.getString(R.string.no);
                i=0;
                check=true;
            }
            props_value = new HashMap<>();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("n", props.getId());
            jsonObject.addProperty("v", i);
            ItemFilterModel filterModel=new ItemFilterModel();
            filterModel.setId_prop(props.getId());
            filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
            filterModel.setTitle(props.getTitle());
            filterModel.setType_filter(props.getType());
            filterModel.setProps(props);
            filterModel.setText(t);
            filterModel.setObject(jsonObject);
            props_value.put(props.getId(), filterModel);
            tvTtext.setText(t);
            tvTitle.setVisibility(VISIBLE);
            dinParamsLayoutListener.onFinishParamsEdit(props_value,props.getId());
            if(check){
                return true;
            }
            return false;
        });
        popup.show();
    }
}