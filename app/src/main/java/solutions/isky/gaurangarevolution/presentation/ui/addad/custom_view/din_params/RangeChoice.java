package solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.CategoryProp;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.data.models.ListOpt;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.MyFildForAddAd;

public class RangeChoice extends LinearLayout implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener {


    MyFildForAddAd til_text;
    private List<ListOpt> listOpts;
    private DinProps props;
    private boolean[] selected;
    private List<String> items;
    private int selekt;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        til_text = findViewById(R.id.til_text);
    }

    public RangeChoice(Context context) {
        super(context);
    }

    public RangeChoice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RangeChoice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setData(Context context, DinProps props, boolean necessary_to_fill,List<CategoryProp> catProps) {
        this.props = props;
        listOpts = new ArrayList<>();
        til_text.setHintAndNecessarily(props.getTitle()+((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""), (props.getReq() > 0));
        til_text.disableeditText();
        til_text.disableeditText();
        selekt=-1;
        setTag(props);
        try {
            items = listString(Integer.parseInt(props.getExtra().getStart()),Integer.parseInt(props.getExtra().getEnd()),Integer.parseInt(props.getExtra().getStep()));
        }catch (Exception e) {
            e.printStackTrace();
            items=new ArrayList<>();
        }
        selected = new boolean[items.size()];

        til_text.setOnClickTextFild(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(props.getTitle()+((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""));
            builder.setSingleChoiceItems(items.toArray(new CharSequence[items.size()]), selekt, this);
            builder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.setOnCancelListener(this);
            AlertDialog alert = builder.create();
            alert.show();
            Button yes = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            yes.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        });
        til_text.setOnClearTExtListener(() -> {
            selekt=-1;
            unselect();

        });
        if (catProps!=null&&catProps.size()>0) {
            CategoryProp categoryProp=null;
            for(CategoryProp prop:catProps){
                if(props.getId()==prop.getId()){
                    categoryProp=prop;
                }
            }
            if(categoryProp!=null){
                til_text.setText(categoryProp.getValue());
                setitemselect(categoryProp.getValue());
            }
        }
    }
    private void setitemselect(String text){
        int size=selected.length;
        for(int i=0;i<size;i++){
            if(items.get(i).equalsIgnoreCase(text)){
                for (int y = 0; y < selected.length; y++)
                    selected[y] = false;
                selected[i] = true;
                selekt=i;
            }
        }
    }
    private ArrayList<String> listString(int start, int end, int step) {
        ArrayList<String> listOptArrayList = new ArrayList<>();

        if(start>end){
            for (int i = start; i >= end; i=i-step) {
                listOptArrayList.add(String.valueOf(i));
            }
        }else{
            for (int i = start; i <= end; i=i+step) {
                listOptArrayList.add(String.valueOf(i));
            }
        }

        return listOptArrayList;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        StringBuffer spinnerBuffer = new StringBuffer();
        boolean someUnselected = false;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i] == true) {
                spinnerBuffer.append(items.get(i));
                spinnerBuffer.append(", ");
            } else {
                someUnselected = true;
            }
        }
        String spinnerText;
        if (someUnselected || spinnerBuffer.toString().length() > 2) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = "";
        }
        if (TextUtils.isEmpty(spinnerText)) {
            spinnerText = "";
        }
        til_text.setText(spinnerText);
    }
    void unselect() {
        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        for (int y = 0; y < selected.length; y++)
            selected[y] = false;
        selected[i] = true;
        selekt=i;
    }


    public ArrayList<HashMap<Integer,Object>> getParams(ArrayList<HashMap<Integer,Object>>maps){
        HashMap<Integer,Object> hashMap=new HashMap<>();
        hashMap.put(props.getId(),getValue(selekt));
        maps.add(hashMap);
        return maps;
    }
    public ArrayList<HashMap<Integer,Object>> getParamsEmpty(ArrayList<HashMap<Integer,Object>>maps){
        HashMap<Integer,Object> hashMap=new HashMap<>();
        hashMap.put(props.getId(),"");
        maps.add(hashMap);
        return maps;
    }
    public int getIdCateg(){
        int id=props.getCat_id();
        return id;
    }
    private String getValue(int s){
        String v=items.get(s);
        return v;
    }
    public boolean isCheack(){
        return !TextUtils.isEmpty(til_text.getText());
    }
    public void setError(String error){
        til_text.setError(error);
    }
}
