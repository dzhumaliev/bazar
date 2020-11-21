package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;

public class ItemShop extends AbstractItem<ItemShop, ItemShop.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {




        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }

}
