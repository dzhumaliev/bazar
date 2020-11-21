package solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.din_params;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.CategoryProp;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.data.models.ListOpt;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.MyFildForAddAd;

public class MultipleChoice extends LinearLayout implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {


    MyFildForAddAd til_text;
    private List<ListOpt> listOpts;
    private DinProps props;
    private boolean[] selected;
    private List<String> items;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        til_text = findViewById(R.id.til_text);
    }

    public MultipleChoice(Context context) {
        super(context);
    }

    public MultipleChoice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultipleChoice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setData(Context context, DinProps props, boolean necessary_to_fill, List<CategoryProp> catProps) {
        this.props = props;
        listOpts = getListOpt(props.getListOpt());
        til_text.setHintAndNecessarily(props.getTitle() + ((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""), (props.getReq() > 0));
        til_text.disableeditText();
        setTag(props);
        items = listString(props.getListOpt());
        selected = new boolean[items.size()];
        if (!TextUtils.isEmpty(props.getDef())) {
            StringBuffer spinnerBuffer = new StringBuffer();
            String[] def = props.getDef().split(";");
            for (int i = 0; i < listOpts.size(); i++) {
                for (String v : def) {
                    if (listOpts.get(i).getValue() == Integer.parseInt(v)) {
                        selected[i] = true;
                        spinnerBuffer.append(listOpts.get(i).getTite());
                        spinnerBuffer.append(", ");
                    }
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
        til_text.setOnClickTextFild(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(props.getTitle()+((!TextUtils.isEmpty(props.getUnit())) ? " (" + props.getUnit() + ")\t" : ""));
            builder.setMultiChoiceItems(
                    items.toArray(new CharSequence[items.size()]), selected, this);
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
            unselect();

        });
        if (catProps != null && catProps.size() > 0) {
            CategoryProp categoryProp = null;
            for (CategoryProp prop : catProps) {
                if (props.getId() == prop.getId()) {
                    categoryProp = prop;
                }
            }
            if (categoryProp != null) {
                til_text.setText(categoryProp.getValue());
                String[] list = null;
                list = categoryProp.getValue().split(", ");
                setitemselect(list);
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

    private void setitemselect(String[] select) {
        int size = selected.length;
        for (int i = 0; i < size; i++) {
            for (int y = 0; y < select.length; y++) {
                if (items.get(i).equalsIgnoreCase(select[y])) {
                    selected[i] = true;
                }
            }
        }
    }

    void unselect() {
        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }
    }

    private ArrayList<String> listString(List<ListOpt> listOpts) {
        ArrayList<String> listOptArrayList = new ArrayList<>();
        for (int i = 0; i < listOpts.size(); i++) {
            if (listOpts.get(i).getValue() != 0) {
                listOptArrayList.add(listOpts.get(i).getTite());

            }
        }
        return listOptArrayList;
    }

    private ArrayList<ListOpt> getListOpt(List<ListOpt> listOpt) {
        ArrayList<ListOpt> list = new ArrayList<>();
        for (int i = 0; i < listOpt.size(); i++) {
            if (listOpt.get(i).getValue() != 0) {
                list.add(listOpt.get(i));
            }
        }
        return list;
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
    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }


    private JsonArray getSumList(boolean[] s) {
        JsonArray jsonArray = new JsonArray();
        for (int y = 0; y < s.length; y++) {
            if (s[y]) {
                jsonArray.add(listOpts.get(y).getValue());
            }
        }
        return jsonArray;
    }

    public ArrayList<HashMap<Integer, Object>> getParams(ArrayList<HashMap<Integer, Object>> maps) {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(props.getId(), getSumList(selected));
        maps.add(hashMap);
        return maps;
    }
    public ArrayList<HashMap<Integer, Object>> getParamsEmpty(ArrayList<HashMap<Integer, Object>> maps) {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(props.getId(), new JsonArray());
        maps.add(hashMap);
        return maps;
    }

    public int getIdCateg() {
        int id = props.getCat_id();
        return id;
    }
    public boolean isCheack(){
        return !TextUtils.isEmpty(til_text.getText());
    }
    public void setError(String error){
        til_text.setError(error);
    }
}
