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
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.MyFildForAddAd;

public class YesNoChoice extends LinearLayout implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener {


    MyFildForAddAd til_text;
    private DinProps props;
    private boolean[] selected;
    private List<String> items;
    private int selekt;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        til_text = findViewById(R.id.til_text);
    }

    public YesNoChoice(Context context) {
        super(context);
    }

    public YesNoChoice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public YesNoChoice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setData(Context context, DinProps props, boolean necessary_to_fill,List<CategoryProp> catProps) {
        this.props = props;
        til_text.setHintAndNecessarily(props.getTitle()+((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""), (props.getReq() > 0));

        til_text.disableeditText();
        selekt = -1;
        setTag(props);
        items = listString(context);
        selected = new boolean[items.size()];
        if (!TextUtils.isEmpty(props.getDef())) {
            StringBuffer spinnerBuffer = new StringBuffer();
            String[] def = props.getDef().split(";");
            for (String v : def) {
                if (1 == Integer.parseInt(v)) {
                    selected[0] = true;
                    selekt = 0;
                    spinnerBuffer.append(items.get(0));
                    spinnerBuffer.append(", ");
                } else if (2 == Integer.parseInt(v)) {
                    selected[1] = true;
                    selekt = 1;
                    spinnerBuffer.append(items.get(1));
                    spinnerBuffer.append(", ");
                }
            }

            String spinnerText;
            if (spinnerBuffer.toString().length() > 2) {
                spinnerText = spinnerBuffer.toString();
                if (spinnerText.length() > 2)
                    spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
            } else {
                spinnerText = "";
            }
            til_text.setText(spinnerText);
        }
        til_text.setOnClearTExtListener(() -> {
            selekt = -1;
            unselect();
        });
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

        if (catProps!=null&&catProps.size()>0) {
            CategoryProp categoryProp=null;
            for(CategoryProp prop:catProps){
                if(props.getId()==prop.getId()){
                    categoryProp=prop;
                }
            }
            if(categoryProp!=null){
                String text="";
                if(categoryProp.getValue().equalsIgnoreCase("1")){
                    til_text.setText(context.getString(R.string.no));
                    selekt=0;
                    for (int y = 0; y < selected.length; y++)
                        selected[y] = false;
                    selected[selekt] = true;
                }else if(categoryProp.getValue().equalsIgnoreCase("2")){
                    til_text.setText(context.getString(R.string.yes));
                    selekt=1;
                    for (int y = 0; y < selected.length; y++)
                        selected[y] = false;
                    selected[selekt] = true;
                }

            }
//            if(ParamFilterPostSingleton.getInstance().getmProps_value().containsKey(props.getId())){
//                JsonObject object= (JsonObject) ParamFilterPostSingleton.getInstance().getmProps_value().get(props.getId()).getObject();
//                JsonArray elements=object.getAsJsonArray("v");
//                List<Integer>integers=new ArrayList<>();
//                for (int i=0;i<elements.size();i++){
//                    integers.add(elements.get(i).getAsInt());
//                }
//                spinner.setCheaked(integers);
//            }
        }
    }

    void unselect() {
        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }
    }

    private ArrayList<String> listString(Context context) {
        ArrayList<String> listOptArrayList = new ArrayList<>();
        listOptArrayList.add(context.getString(R.string.no));
        listOptArrayList.add(context.getString(R.string.yes));
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


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        for (int y = 0; y < selected.length; y++)
            selected[y] = false;
        selected[i] = true;
        selekt=i;
    }


    public ArrayList<HashMap<Integer,Object>> getParams(ArrayList<HashMap<Integer,Object>>maps){
        HashMap<Integer,Object> hashMap=new HashMap<>();
        hashMap.put(props.getId(),getValue(selected));
        maps.add(hashMap);
        return maps;
    }
    public ArrayList<HashMap<Integer,Object>> getParamsEmty(ArrayList<HashMap<Integer,Object>>maps){
        HashMap<Integer,Object> hashMap=new HashMap<>();
        hashMap.put(props.getId(),0);
        maps.add(hashMap);
        return maps;
    }
    public int getIdCateg(){
        int id=props.getCat_id();
        return id;
    }
    private int getValue(boolean[]s){
        int v=0;
        for(int y=0;y<s.length;y++){
            if(s[y]){
                v=y+1;
            }
        }
        return v;
    }
    public boolean isCheack(){
        return !TextUtils.isEmpty(til_text.getText());
    }
    public void setError(String error){
        til_text.setError(error);
    }
}
