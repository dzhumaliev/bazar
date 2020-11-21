package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;

public class ItemLocation extends AbstractItem<ItemLocation, ItemLocation.ViewHolder> {
    @SerializedName("id")
    private int id;
    @SerializedName("pid")
    private int pid;
    @SerializedName("c")
    private String c;
    @SerializedName("t")
    private String title;
    @SerializedName("yc")
    private String latlon;
    @SerializedName("def")
    private int isDefCantry;
    @SerializedName("fav_city")
    @NonNull
    private int fav_city;

    public String subtitle;
    boolean iz_img;



    @Override
    public int getType() {
        return R.id.item_location;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isIz_img() {
        return iz_img;
    }

    public void setIz_img(boolean iz_img) {
        this.iz_img = iz_img;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_locations;
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }
    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.title.setText(null);
        viewHolder.subtitle.setText(null);
    }
    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context ctx = viewHolder.itemView.getContext();
        viewHolder.title.setText(title);
        viewHolder.subtitle.setText((fav_city==1)?ctx.getString(R.string.city):subtitle);
        if (iz_img&&fav_city!=1) {
            viewHolder.img.setVisibility(View.VISIBLE);
        } else {
            viewHolder.img.setVisibility(View.GONE);
        }
    }
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView title;
        @BindView(R.id.tvNameR)
        TextView subtitle;
        @BindView(R.id.dataItemArrow)
        ImageView img;

        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatlon() {
        return latlon;
    }

    public void setLatlon(String latlon) {
        this.latlon = latlon;
    }

    public int getIsDefCantry() {
        return isDefCantry;
    }

    public void setIsDefCantry(int isDefCantry) {
        this.isDefCantry = isDefCantry;
    }

    public int getFav_city() {
        return fav_city;
    }

    public void setFav_city(int fav_city) {
        this.fav_city = fav_city;
    }
}