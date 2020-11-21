package solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.CategoryProp;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.MyFildForAddAd;

public class FlagChoice extends LinearLayout {

    private MyFildForAddAd et_text;
    private DinProps props;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        et_text = findViewById(R.id.til_text);


    }

    public FlagChoice(Context context) {
        super(context);
    }

    public FlagChoice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlagChoice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(Context context, DinProps props, boolean necessary_to_fill, List<CategoryProp> catProps) {
        setTag(props);
        this.props = props;
        et_text.setHintAndNecessarily(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""), (props.getReq() > 0));
        et_text.setCheckBox();
        et_text.disableeditText();
        if (!TextUtils.isEmpty(props.getDef())) {
            et_text.setChecked(true);
        } else {
            et_text.setChecked(false);
        }

        if (catProps != null && catProps.size() > 0) {
            CategoryProp categoryProp = null;
            for (CategoryProp prop : catProps) {
                if (props.getId() == prop.getId()) {
                    categoryProp = prop;
                }
            }
            if (categoryProp != null) {

                if (categoryProp.getValue().equalsIgnoreCase("1")) {
                    et_text.setChecked(true);
                }
            }
        }
        et_text.setOnClickTextFild(() -> {
            if (et_text.isCheack()) {
                et_text.setChecked(false);
            } else {
                et_text.setChecked(true);
            }
        });
    }


    public ArrayList<HashMap<Integer, Object>> getParams(ArrayList<HashMap<Integer, Object>> maps) {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(props.getId(), getValue());
        maps.add(hashMap);
        return maps;
    }
    public ArrayList<HashMap<Integer, Object>> getParamsEmpty(ArrayList<HashMap<Integer, Object>> maps) {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(props.getId(), "");
        maps.add(hashMap);
        return maps;
    }
    public int getIdCateg() {
        int id = props.getCat_id();
        return id;
    }

    private int getValue() {
        int v = (isCheack()) ? 1 : 0;
        return v;
    }

    public boolean isCheack() {
        return et_text.isCheack();
    }
    public void setError(String error){
        et_text.setError(error);
    }
}
