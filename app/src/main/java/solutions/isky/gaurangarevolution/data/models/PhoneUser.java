package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;

public class PhoneUser extends AbstractItem<PhoneUser, PhoneUser.ViewHolder> implements Serializable {

    @SerializedName("v")
    @NonNull
    private String v;
    @SerializedName("m")
    @NonNull
    private String m;

    @NonNull
    public String getM() {
        return m;
    }

    public void setM(@NonNull String m) {
        this.m = m;
    }

    @NonNull
    public String getV() {
        return v;
    }

    public void setV(@NonNull String v) {
        this.v = v;
    }

    public PhoneUser() {
    }

    public PhoneUser(@NonNull String v, @NonNull String m) {
        this.v = v;
        this.m = m;
    }

    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context ctx = viewHolder.itemView.getContext();
       viewHolder.tv_number.setText(v);

    }

    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.tv_number.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);

    }

    @Override
    public int getType() {
        return R.id.phone_list;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_phone;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_number)
        TextView tv_number;

        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }
}