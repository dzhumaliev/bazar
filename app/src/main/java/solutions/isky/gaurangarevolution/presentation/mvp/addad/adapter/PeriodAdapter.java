package solutions.isky.gaurangarevolution.presentation.mvp.addad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.PeriodItem;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;

public class PeriodAdapter extends BaseAdapter {
    Context context;
    List<PeriodItem> periodItemList;
    LayoutInflater inflter;

    public PeriodAdapter(Context applicationContext, List<PeriodItem> periodItemList) {
        this.context = applicationContext;
        this.periodItemList = periodItemList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return periodItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return periodItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.period_spinner_items, null);
        TextView names = view.findViewById(R.id.textView);
        names.setText(AppUtils.strFormatPeriod(periodItemList.get(i).getDays(),context));
        return view;
    }
}