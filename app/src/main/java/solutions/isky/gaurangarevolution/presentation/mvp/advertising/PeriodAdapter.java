package solutions.isky.gaurangarevolution.presentation.mvp.advertising;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.PriceAbonement;

public class PeriodAdapter extends ArrayAdapter<PriceAbonement> {

    public PeriodAdapter(Context context, List<PriceAbonement> priceAbonements) {
        super(context, 0, priceAbonements);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.abonement_spinner_row, parent, false
            );
        }


        TextView textViewName = (TextView) convertView.findViewById(R.id.text_view_name);

        PriceAbonement currentItem = getItem(position);

        if (currentItem != null) {

            textViewName.setText(currentItem.getM());
        }

        return convertView;
    }
}
