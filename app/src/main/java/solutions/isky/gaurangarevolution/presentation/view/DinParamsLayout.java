package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;

public class DinParamsLayout extends LinearLayout {
    private HashMap<Integer, ItemFilterModel> props_value;

    public DinParamsLayout(Context context) {
        super(context);
    }

    public DinParamsLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DinParamsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public interface DinParamsLayoutListener {
        void onFinishParamsEdit(HashMap<Integer, ItemFilterModel> props_value, int id_prop);
    }
    public void setUpViews(Context context, List<DinProps> dinPropses, FragmentManager fragmentManager, DinParamsLayoutListener paramsLayoutListener, boolean necessary_to_fill) {
        DinParamsLayoutListener activity = (DinParamsLayoutListener)context;
        if (dinPropses != null && dinPropses.size() > 0) {
           setVisibility(VISIBLE);
           for (DinProps props : dinPropses) {
              if (props.getSea() == 1) {
                   switch (props.getType()) {
                        case 9:
                            setViewType9(context, props,activity,necessary_to_fill);
                            break;
                        case 6:
                            setViewType6_8(context, props,paramsLayoutListener, necessary_to_fill);
                            break;
                        case 8:
                            setViewType6_8(context, props,paramsLayoutListener, necessary_to_fill);
                            break;
                        case 10:
                            setViewType10(context, props, fragmentManager,necessary_to_fill);
                            break;
                        case 11:
                            setViewType10(context, props, fragmentManager,necessary_to_fill);
                            break;
                        case 4:
                            setViewType4(context, props, necessary_to_fill);
                            break;
                        case 5:
                            setViewType5(context, props, necessary_to_fill);
                            break;
                   }
              }
            }
        } else {
            setVisibility(GONE);
        }

    }

    private void setViewType9(Context context, DinProps props,DinParamsLayoutListener paramsLayoutListener,boolean necessary_to_fill) {
        DinParamsType9 dinParamsType9 = (DinParamsType9) LayoutInflater.from(context)
                .inflate(R.layout.din_param_type9, this, false);
        dinParamsType9.setData(context, props,paramsLayoutListener,necessary_to_fill);
        addView(dinParamsType9);
    }

    private void setViewType6_8(Context context, DinProps props,DinParamsLayoutListener paramsLayoutListener,boolean necessary_to_fill) {
        DinParamsType9 dinParamsType6_8 = (DinParamsType9) LayoutInflater.from(context)
                .inflate(R.layout.din_param_type9, this, false);
        dinParamsType6_8.setData(context, props,paramsLayoutListener,necessary_to_fill);
        addView(dinParamsType6_8);
    }
    private void setViewType4(Context context, DinProps props,boolean necessary_to_fill) {
        DinParamsType4 dinParamsType4 = (DinParamsType4) LayoutInflater.from(context)
                .inflate(R.layout.din_param_type4, this, false);
        dinParamsType4.setData(context, props,necessary_to_fill);
        addView(dinParamsType4);
    }
    private void setViewType5(Context context, DinProps props,boolean necessary_to_fill) {
        DinParamsType5 dinParamsType5 = (DinParamsType5) LayoutInflater.from(context)
                .inflate(R.layout.din_param_type5, this, false);
        dinParamsType5.setData(context, props,necessary_to_fill);
        addView(dinParamsType5);
    }
    private void setViewType10(Context context, DinProps props, FragmentManager fragmentManager,boolean necessary_to_fill) {
        DinParamsType10 dinParamsType10 = (DinParamsType10) LayoutInflater.from(context)
                .inflate(R.layout.din_param_type10, this, false);
        if ((props.getExtra() != null && props.getExtra().getSearch_range_user() == 1) ||
                (props.getExtra() != null && props.getExtra().getSearch_range_user() == 0 && props.getExtra().getSearch_ranges() != null && props.getExtra().getSearch_ranges().size() > 0)) {
            dinParamsType10.setData(context, props, fragmentManager, necessary_to_fill);
            addView(dinParamsType10);
        }
    }
//
//    public void setParams() {
//        props_value = new HashMap<>();
//        for (int i = 0; i < getChildCount(); i++) {
//            enumerationParams(((DinProps) getChildAt(i).getTag()), getChildAt(i));
//        }
//    }
//
//    public void enumerationParams(DinProps dinProps, View view) {
//        switch (dinProps.getType()) {
//            case 9:
//                if (((DinParamsType9) view).isCheckParam()) {
//                    ItemFilterModel filterModel=new ItemFilterModel();
//                    filterModel.setId_prop(dinProps.getId());
//                    filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
//                    filterModel.setTitle(dinProps.getTitle());
//                    filterModel.setObject(((DinParamsType9) view).get_props());
//                    filterModel.setType_filter(dinProps.getType());
//                    props_value.put(dinProps.getId(),filterModel);
//                }
//                ParamFilterPostSingleton.getInstance().setmProps_value(props_value);
//                break;
//            case 6:
//                if (((DinParamsType9) view).isCheckParam()) {
//                    ItemFilterModel filterModel=new ItemFilterModel();
//                    filterModel.setId_prop(dinProps.getId());
//                    filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
//                    filterModel.setTitle(dinProps.getTitle());
//                    filterModel.setObject(((DinParamsType9) view).get_props());
//                    filterModel.setType_filter(dinProps.getType());
//                    props_value.put(dinProps.getId(), filterModel);
//                }
//                ParamFilterPostSingleton.getInstance().setmProps_value(props_value);
//                break;
//            case 8:
//                if (((DinParamsType9) view).isCheckParam()) {
//                    ItemFilterModel filterModel=new ItemFilterModel();
//                    filterModel.setId_prop(dinProps.getId());
//                    filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
//                    filterModel.setTitle(dinProps.getTitle());
//                    filterModel.setObject(((DinParamsType9) view).get_props());
//                    filterModel.setType_filter(dinProps.getType());
//                    props_value.put(dinProps.getId(), filterModel);
//                }
//                ParamFilterPostSingleton.getInstance().setmProps_value(props_value);
//                break;
//            case 10:
//                if (((DinParamsType10) view).isCheckParam()) {
//                    ItemFilterModel filterModel=new ItemFilterModel();
//                    filterModel.setId_prop(dinProps.getId());
//                    filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
//                    filterModel.setTitle(dinProps.getTitle());
//                    filterModel.setObject(((DinParamsType9) view).get_props());
//                    filterModel.setType_filter(dinProps.getType());
//                    props_value.put(dinProps.getId(), filterModel);
//                }
//                ParamFilterPostSingleton.getInstance().setmProps_value(props_value);
//                break;
//        }
//    }

}