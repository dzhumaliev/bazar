package solutions.isky.gaurangarevolution.presentation.mvp.addad.currencyview;

import android.content.Context;
import android.widget.ListAdapter;

import java.util.List;


public class NiceSpinnerAdapterWrapper extends NiceSpinnerBaseAdapter {

    private final ListAdapter baseAdapter;

    NiceSpinnerAdapterWrapper(Context context, ListAdapter toWrap, int textColor, int backgroundSelector,
                              SpinnerTextFormatter spinnerTextFormatter) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter);
        baseAdapter = toWrap;
    }

    @Override public int getCount() {
        return baseAdapter.getCount() - 1;
    }

    @Override public Object getItem(int position) {
        return baseAdapter.getItem(position >= selectedIndex ? position + 1 : position);
    }

    @Override
    public List getlist() {
        return null;
    }

    @Override public Object getItemInDataset(int position) {
        return baseAdapter.getItem(position);
    }
}