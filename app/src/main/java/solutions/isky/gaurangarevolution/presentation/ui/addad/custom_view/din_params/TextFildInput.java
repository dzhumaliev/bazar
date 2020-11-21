package solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.CategoryProp;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.MyFildForAddAd;

public class TextFildInput extends LinearLayout {
    MyFildForAddAd til_text;
    private DinProps props;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        til_text = findViewById(R.id.til_text);
    }

    public TextFildInput(Context context) {
        super(context);
    }

    public TextFildInput(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextFildInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setData(Context context, DinProps props,int type, boolean necessary_to_fill,List<CategoryProp> catProps) {
        this.props = props;

        til_text.setHintAndNecessarily(props.getTitle()+((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""), (props.getReq() > 0));
        til_text.setInputTypeText(type);
        setTag(props);
        til_text.setText(props.getDef());

        if (catProps!=null&&catProps.size()>0) {
            CategoryProp categoryProp = null;
            for (CategoryProp prop : catProps) {
                if (props.getId() == prop.getId()) {
                    categoryProp = prop;
                }
            }
            if (categoryProp != null) {
                til_text.setText(categoryProp.getValue());
            }
        }
    }


    public ArrayList<HashMap<Integer,Object>> getParams(ArrayList<HashMap<Integer,Object>>maps){
        HashMap<Integer,Object> hashMap=new HashMap<>();
        hashMap.put(props.getId(),getValue());
        maps.add(hashMap);
        return maps;
    }
    public ArrayList<HashMap<Integer,Object>> getParamsEmty(ArrayList<HashMap<Integer,Object>>maps){
        HashMap<Integer,Object> hashMap=new HashMap<>();
        hashMap.put(props.getId(),"");
        maps.add(hashMap);
        return maps;
    }
    public int getIdCateg(){
        int id=props.getCat_id();
        return id;
    }
    private String getValue(){
        String v=til_text.getText();
        return v;
    }
    public boolean isCheack(){
        return !TextUtils.isEmpty(til_text.getText());
    }
    public void setError(String error){
        til_text.setError(error);
    }
}
