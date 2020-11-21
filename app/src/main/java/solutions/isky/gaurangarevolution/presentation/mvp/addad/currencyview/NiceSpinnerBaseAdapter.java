package solutions.isky.gaurangarevolution.presentation.mvp.addad.currencyview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.Currency;


public abstract class NiceSpinnerBaseAdapter<T> extends BaseAdapter {

    private final SpinnerTextFormatter spinnerTextFormatter;

    private int textColor;
    private int backgroundSelector;

    int selectedIndex;

    NiceSpinnerBaseAdapter(Context context, int textColor, int backgroundSelector,
                           SpinnerTextFormatter spinnerTextFormatter) {
        this.spinnerTextFormatter = spinnerTextFormatter;
        this.backgroundSelector = backgroundSelector;
        this.textColor = textColor;
    }

    @Override public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        TextView textView;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.spinner_list_item, null);
            textView = (TextView) convertView.findViewById(R.id.text_view_spinner);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackground(ContextCompat.getDrawable(context, backgroundSelector));
            }
            convertView.setTag(new ViewHolder(textView));
        } else {
            textView = ((ViewHolder) convertView.getTag()).textView;
        }

        textView.setText(spinnerTextFormatter.format(((Currency)getItem(position)).getTitleShort()));
        textView.setTextColor(textColor);
        return convertView;
    }
    public abstract List<T> getlist();
    public int getSelectedIndex() {
        return selectedIndex;
    }

    void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    public abstract T getItemInDataset(int position);

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public abstract T getItem(int position);

    @Override public abstract int getCount();

    static class ViewHolder {
        TextView textView;

        ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}
