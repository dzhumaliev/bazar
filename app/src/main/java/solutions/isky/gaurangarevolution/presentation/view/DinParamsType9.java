package solutions.isky.gaurangarevolution.presentation.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.data.models.ListOpt;
import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.TypeFilterAdapt;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;

public class DinParamsType9 extends LinearLayout implements MultiSpinner.MultiSpinnerListener {

    private TextView tvTitle;
    MultiSpinner spinner;
    ImageButton ibClear;
    List<ListOpt> mListOpts;
    JsonArray mSum_arr;
    JsonObject jsonObject;
    DinParamsLayout.DinParamsLayoutListener paramsLayoutListener;
    HashMap<Integer, ItemFilterModel> props_value;

    public DinParamsType9(Context context) {
        super(context);
    }

    public DinParamsType9(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DinParamsType9(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTitle = findViewById(R.id.tv_title);
        spinner =  findViewById(R.id.spinner);
        ibClear=findViewById(R.id.ibClear);
    }

    public void setData(Context context, DinProps props,DinParamsLayout.DinParamsLayoutListener paramsLayoutListener,boolean necessary_to_fill) {
        this.paramsLayoutListener=paramsLayoutListener;
        mListOpts=new ArrayList<>();
        tvTitle.setText(props.getTitle());
        spinner.setItems(listString(props.getListOpt()), "", this, mListOpts,props);

        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    KeyboardUtil.hideKeyboard((Activity) context);
                }
                return false;
            }
        });
        setTag(props);
        ibClear.setOnClickListener(view -> {
            spinner.unselect();
            ibClear.setVisibility(GONE);
            props_value.clear();
            paramsLayoutListener.onFinishParamsEdit(props_value,((DinProps)getTag()).getId());
        });
        if(necessary_to_fill){
            if(ParamFilterPostSingleton.getInstance().getmProps_value().containsKey(props.getId())){
                JsonObject object= (JsonObject) ParamFilterPostSingleton.getInstance().getmProps_value().get(props.getId()).getObject();
                JsonArray elements=object.getAsJsonArray("v");
                List<Integer>integers=new ArrayList<>();
                for (int i=0;i<elements.size();i++){
                    integers.add(elements.get(i).getAsInt());
                }
                spinner.setCheaked(integers);
            }
        }
    }

    private ArrayList<String> listString(List<ListOpt> listOpts) {
        ArrayList<String> listOptArrayList = new ArrayList<>();
        for (int i = 0; i < listOpts.size(); i++) {
            if (listOpts.get(i).getValue() != 0){
                listOptArrayList.add(listOpts.get(i).getTite());
                mListOpts.add(listOpts.get(i));
            }
        }
        return listOptArrayList;
    }

    @Override
    public void onItemsSelected(boolean[] selected, int sum, JsonArray sum_arr, String s,DinProps dinProps) {
        if (sum_arr != null && sum_arr.size() > 0) {
            ibClear.setVisibility(VISIBLE);
            mSum_arr=sum_arr;
            jsonObject=new JsonObject();
            jsonObject.addProperty("n",((DinProps)getTag()).getId());
            jsonObject.add("v",mSum_arr);
            props_value=new HashMap<>();
            ItemFilterModel filterModel=new ItemFilterModel();
            filterModel.setId_prop(((DinProps)getTag()).getId());
            filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
            filterModel.setTitle(((DinProps)getTag()).getTitle());
            filterModel.setType_filter(((DinProps)getTag()).getType());
            filterModel.setProps(((DinProps)getTag()));
            filterModel.setText(s);
            filterModel.setObject(get_props());
            props_value.put(((DinProps)getTag()).getId(), filterModel);
            paramsLayoutListener.onFinishParamsEdit(props_value,((DinProps)getTag()).getId());
            tvTitle.setVisibility(VISIBLE);
        } else {
            ibClear.setVisibility(GONE);
            mSum_arr=new JsonArray();
            jsonObject=new JsonObject();

        }
    }

    public JsonObject get_props(){
        return jsonObject;
    }
}