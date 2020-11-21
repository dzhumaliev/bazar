package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.data.models.ListOpt;
import solutions.isky.gaurangarevolution.presentation.utils.ArrayAdapter;

public class MultiSpinner extends android.support.v7.widget.AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {
    private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;
    private List<String> items;
    private List<ListOpt> listOpts;
    private DinProps props;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context context, int mode) {
        super(context, mode);
    }

    public MultiSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MultiSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
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
            spinnerText = defaultText;
        }
        if (TextUtils.isEmpty(spinnerText)) {
            spinnerText = defaultText;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_dropdown_item,
                new String[]{spinnerText});
        setAdapter(adapter);
        listener.onItemsSelected(selected, getSum(selected), getSumList(selected), getSumListString(selected), props);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected, int sum, JsonArray sum_arr, String s, DinProps dinProps);
    }

    private int getSum(boolean[] s) {
        int i = 0;
        for (int y = 0; y < s.length; y++) {
            if (s[y]) {
                i = i + listOpts.get(y).getValue();
            }
        }
        return i;
    }

    private JsonArray getSumList(boolean[] s) {
        JsonArray strings = new JsonArray();
        for (int y = 0; y < s.length; y++) {
            if (s[y]) {
                strings.add(listOpts.get(y).getValue());
            }
        }
        return strings;
    }

    private String getSumListString(boolean[] s) {
        String text = "";
        for (int y = 0; y < s.length; y++) {
            if (s[y]) {
                text = TextUtils.isEmpty(text) ? (text + listOpts.get(y).getTite()) : (text + ", " + listOpts.get(y).getTite());
            }
        }
        return text;
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        yes.setBackground(getResources().getDrawable(R.drawable.bg_btn_phone));
        yes.setHeight((int) getResources().getDimension(R.dimen.item_tips));
        yes.setTextColor(getResources().getColor(R.color.selector_btn_text_phone));
        return true;
    }

    public void setItemsSelect(String[] select) {
        for (int i = 0; i < selected.length; i++)
            for (int y = 0; y < select.length; y++) {
                if (items.get(i).equalsIgnoreCase(select[y])) {
                    selected[i] = true;
                }
            }

    }

    public void setItems(List<String> items, String allText,
                         MultiSpinnerListener listener, List<ListOpt> listOpts, DinProps props) {
        this.items = items;
        this.defaultText = allText;
        this.props = props;
        this.listener = listener;
        this.listOpts = listOpts;
        // all selected by default
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++)
            selected[i] = false;


        // all text on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_dropdown_item, new String[]{allText});
        setAdapter(adapter);
    }

    public void setCheaked(List<Integer> integers) {
        for (int i = 0; i < listOpts.size(); i++) {
            for (int y = 0; y < integers.size(); y++) {
                if (listOpts.get(i).getValue() == integers.get(y)) {
                    selected[i] = true;
                }
            }
        }
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
            spinnerText = defaultText;
        }
        if (TextUtils.isEmpty(spinnerText)) {
            spinnerText = defaultText;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_dropdown_item,
                new String[]{spinnerText});
        setAdapter(adapter);
        listener.onItemsSelected(selected, getSum(selected), getSumList(selected), getSumListString(selected), props);
    }

    void unselect() {
        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_dropdown_item, new String[]{defaultText});
        setAdapter(adapter);
    }


}