package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by isky on 11.01.2018.
 */

public class ShopData extends AbstractItem<ShopData, ShopData.ViewHolder> implements Serializable {
    @NonNull
    @SerializedName("title")
    private String title;
    @NonNull
    @SerializedName("status")
    private int status;
    @NonNull
    @SerializedName("descr")
    private String descr;
    @NonNull
    @SerializedName("logo")
    private String Companylogo;

    @NonNull
    @SerializedName("region_id")
    private int region_id;
    @NonNull
    @SerializedName("addr_lat")
    private double addr_lat;
    @NonNull
    @SerializedName("addr_lon")
    private double addr_lon;
    @NonNull
    @SerializedName("fav")
    private int fav;
    @NonNull
    @SerializedName("id")
    private int id;
    @NonNull
    @SerializedName("id_ex")
    private String id_ex;
    @NonNull
    @SerializedName("site")
    private String site;
    @NonNull
    @SerializedName("phones")
    private ArrayList<String> phones;
    @NonNull
    @SerializedName("addr_addr")
    private String addr_addr;
    @NonNull
    @SerializedName("items")
    private int items_count;
    @NonNull
    @SerializedName("region_title")
    private String region_title;
    @NonNull
    @SerializedName("svc_marked")
    private int svc_marked;
    @NonNull
    @SerializedName("svc_fixed")
    private int svc_fixed;
    @NonNull
    @SerializedName("contacts")
    private Contacts_shop contacts_shop;
    @NonNull
    @SerializedName("social")
    private List<SocialShop> socialShops;
    @NonNull
    @SerializedName("cats")
    private List<CategoryShop> cats;
    @SerializedName("blocked_reason")
    private String blocked_reason;

    @SerializedName("svc_abonement")
    private SvcAbonement svcAbonement;


    public SvcAbonement getSvcAbonement() {
        return svcAbonement;
    }

    public void setSvcAbonement(SvcAbonement svcAbonement) {
        this.svcAbonement = svcAbonement;
    }

    public String getBlocked_reason() {
        return blocked_reason;
    }

    public void setBlocked_reason(String blocked_reason) {
        this.blocked_reason = blocked_reason;
    }

    public List<CategoryShop> getCats() {
        return (cats != null) ? cats : new ArrayList<>();
    }

    public void setCats(List<CategoryShop> cats) {
        this.cats = cats;
    }

    public Contacts_shop getContacts_shop() {
        return contacts_shop;
    }

    public void setContacts_shop(Contacts_shop contacts_shop) {
        this.contacts_shop = contacts_shop;
    }

    public List<SocialShop> getSocialShops() {
        return (socialShops != null) ? socialShops : new ArrayList<>();
    }

    @NonNull
    public int getStatus() {
        return status;
    }

    public void setStatus(@NonNull int status) {
        this.status = status;
    }

    public void setSocialShops(List<SocialShop> socialShops) {
        this.socialShops = socialShops;
    }

    @NonNull
    public int getFav() {
        return fav;
    }

    public void setFav(@NonNull int fav) {
        this.fav = fav;
    }

    @NonNull
    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(@NonNull int region_id) {
        this.region_id = region_id;
    }

    @NonNull
    public double getAddr_lat() {
        return addr_lat;
    }

    public void setAddr_lat(@NonNull double addr_lat) {
        this.addr_lat = addr_lat;
    }

    @NonNull
    public double getAddr_lon() {
        return addr_lon;
    }

    public void setAddr_lon(@NonNull double addr_lon) {
        this.addr_lon = addr_lon;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescr() {
        return descr;
    }

    public void setDescr(@NonNull String descr) {
        this.descr = descr;
    }

    @NonNull
    public String getCompanylogo() {
        return Companylogo;
    }

    public void setCompanylogo(@NonNull String companylogo) {
        Companylogo = companylogo;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getId_ex() {
        return id_ex;
    }

    public void setId_ex(@NonNull String id_ex) {
        this.id_ex = id_ex;
    }

    @NonNull
    public String getSite() {
        return site;
    }

    public void setSite(@NonNull String site) {
        this.site = site;
    }

    @NonNull
    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(@NonNull ArrayList<String> phones) {
        this.phones = phones;
    }

    @NonNull
    public String getAddr_addr() {
        return addr_addr;
    }

    public void setAddr_addr(@NonNull String addr_addr) {
        this.addr_addr = addr_addr;
    }

    @NonNull
    public int getItems() {
        return items_count;
    }

    public void setItems(@NonNull int items) {
        this.items_count = items;
    }

    @NonNull
    public String getRegion_title() {
        return region_title;
    }

    public void setRegion_title(@NonNull String region_title) {
        this.region_title = region_title;
    }

    @NonNull
    public int getSvc_marked() {
        return svc_marked;
    }

    public void setSvc_marked(@NonNull int svc_marked) {
        this.svc_marked = svc_marked;
    }

    @NonNull
    public int getSvc_fixed() {
        return svc_fixed;
    }

    public void setSvc_fixed(@NonNull int svc_fixed) {
        this.svc_fixed = svc_fixed;
    }

    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context ctx = viewHolder.itemView.getContext();

        viewHolder.city_name_ad.setText(region_title);
        viewHolder.items_ad.setText(String.valueOf(items_count));

        GlideApp.with(ctx)
                .load(Companylogo)
                .placeholder(R.drawable.ic_placeholder_shop)
                .error(R.drawable.ic_placeholder_shop)
                .fallback(R.drawable.ic_placeholder_shop)
                .fitCenter()
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img_item);

        viewHolder.title_ad.setText(title);
        viewHolder.tv_descr.setText(descr);
        viewHolder.imageViewFixed.setVisibility((svc_fixed == 0) ? View.GONE : View.VISIBLE);

        if (svc_marked == 1) {
            viewHolder.view.setBackgroundResource(R.drawable.bg_mark);
        } else {
            viewHolder.view.setBackgroundResource(android.R.color.white);
        }

    }

    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.title_ad.setText(null);
        viewHolder.city_name_ad.setText(null);
        viewHolder.items_ad.setText(null);
        viewHolder.tv_descr.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);

    }

    @Override
    public int getType() {
        return R.id.item_shop;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_shop;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_ad)
        ImageView img_item;
        @BindView(R.id.tv_title_ad)
        TextView title_ad;
        @BindView(R.id.tv_CityName)
        TextView city_name_ad;
        @BindView(R.id.tv_Items)
        TextView items_ad;
        @BindView(R.id.tv_descr)
        TextView tv_descr;
        @BindView(R.id.imageView_fund)
        ImageView imageViewFixed;
        @BindView(R.id.view_contetnt)
        LinearLayout view;

        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }
}
