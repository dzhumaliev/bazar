package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by sergey on 20.03.18.
 */

public class AdItem extends AbstractItem<AdItem, AdItem.ViewHolder> {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("status")
    @NonNull
    private int status;
    @SerializedName("title")
    @NonNull
    private String title;
    @SerializedName("cat_id")
    @NonNull
    private int catId;
    @SerializedName("link")
    @NonNull
    private String link;
    @SerializedName("img_s")
    @NonNull
    private String imgS;
    @SerializedName("img_m")
    @NonNull
    private String imgM;
    @SerializedName("imgs")
    @NonNull
    private String imgs;
    @SerializedName("moderated")
    @NonNull
    private int moderated;
    @SerializedName("lat")
    @NonNull
    private double lat;
    @SerializedName("lon")
    @NonNull
    private double lon;
    @SerializedName("addr_addr")
    @NonNull
    private String addrAddr;
    @SerializedName("price")
    @NonNull
    private String price;
    @SerializedName("price_curr")
    @NonNull
    private int priceCurr;
    @SerializedName("price_ex")
    @NonNull
    private PriceEx priceEx;
    @SerializedName("district_id")
    @NonNull
    private String districtId;
    @SerializedName("svc_marked")
    @NonNull
    private int svcMarked;
    @SerializedName("svc_fixed")
    @NonNull
    private int svcFixed;
    @SerializedName("svc_premium")
    @NonNull
    private int svcPremium;
    @SerializedName("svc_turbo")
    @NonNull
    private int svcTurbo;
    @SerializedName("svc_quick")
    @NonNull
    private int svcQuick;
    @SerializedName("svc_up")
    @NonNull
    private int svcUp;
    @SerializedName("descr")
    @NonNull
    String descr;
    @SerializedName("publicated")
    @NonNull
    private String publicated;
    @SerializedName("created")
    @NonNull
    private String created;
    @SerializedName("modified")
    @NonNull
    private String modified;
    @SerializedName("price_on")
    @NonNull
    private boolean priceOn;
    @SerializedName("cat_title")
    @NonNull
    private String catTitle;
    @SerializedName("city_title")
    @NonNull
    private String cityTitle;
    @SerializedName("price_search")
    @NonNull
    private String priceSearch;
    @SerializedName("curr_display")
    @NonNull
    private String currDisplay;
    @SerializedName("price_mod")
    @NonNull
    private String priceMod;
    @SerializedName("currency_default")
    @NonNull
    private String currencyDefault;
    @SerializedName("item_price_display")
    @NonNull
    private String itemPriceDisplay;
    @SerializedName("fav")
    private int fav;
    public boolean is_grid;
    public boolean mStarred = false;
    @SerializedName("img_size")
    private ImgSize imgSize;


    public AdItem withStarred(boolean starred) {
        fav=starred?1:0;
        this.mStarred = starred;
        return this;

    }
    public boolean is_grid() {
        return is_grid;
    }

    public void setIs_grid(boolean is_grid) {
        this.is_grid = is_grid;

    }

    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context ctx = viewHolder.itemView.getContext();
        mStarred=fav!=0;
        viewHolder.imageViewStarFav.setImageResource(mStarred?R.drawable.ic_star_fav:R.drawable.ic_star);
        viewHolder.categ_ad.setText(catTitle);
        viewHolder.city_name_ad.setText(cityTitle);
        if("0.00".equalsIgnoreCase(price)){
            viewHolder.price_ad.setText(AppUtils.getPriceEx(priceEx,itemPriceDisplay,priceMod));
        }else{
            viewHolder.price_ad.setText(AppUtils.getPriceEx(priceEx,itemPriceDisplay));
        }
        viewHolder.price_ad.setVisibility(AppUtils.is_hasPrice(priceEx,price)? View.VISIBLE:View.INVISIBLE);
        viewHolder.tvASAP.setVisibility((svcQuick == 0) ? View.GONE : View.VISIBLE);
        GlideApp.with(ctx)
                .load(imgM)
                .placeholder(R.drawable.ic_placeholder_ad)
                .error(R.drawable.ic_placeholder_ad)
                .fallback(R.drawable.ic_placeholder_ad)
                .centerCrop()
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img_item);

        viewHolder.title_ad.setText(title);

        viewHolder.imageViewFixed.setVisibility((svcFixed == 0) ? View.GONE : View.VISIBLE);
        viewHolder.title_ad.setTypeface(Typeface.DEFAULT, (svcFixed == 1 || svcMarked == 1 || svcPremium == 1 || svcQuick == 1 || svcTurbo == 1) ? (Typeface.BOLD) : (Typeface.NORMAL));


        viewHolder.view.setBackgroundResource((svcPremium == 1) ? android.R.color.white : android.R.color.white);
        viewHolder.tv_premium.setVisibility((svcPremium == 1) ? View.VISIBLE : View.GONE);

        if (svcPremium == 0 && svcMarked == 1) {
            viewHolder.view.setBackgroundResource(R.drawable.bg_mark);
        } else if (svcPremium == 0 && svcMarked == 0) {
            viewHolder.view.setBackgroundResource(android.R.color.white);
        }

    }

    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.title_ad.setText(null);
        viewHolder.categ_ad.setText(null);
        viewHolder.city_name_ad.setText(null);
        viewHolder.price_ad.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);

    }

    @Override
    public int getType() {
        return R.id.ad_list;
    }

    @Override
    public int getLayoutRes() {
        return (is_grid) ? R.layout.item_ad_grid : R.layout.item_ad;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_contetnt)
        LinearLayout view;
        @BindView(R.id.img_ad)
        ImageView img_item;
        @BindView(R.id.tv_title_ad)
        TextView title_ad;
        @BindView(R.id.tv_CityName)
        TextView city_name_ad;
        @BindView(R.id.tv_Categ)
        TextView categ_ad;
        @BindView(R.id.tv_Price)
        TextView price_ad;
        @BindView(R.id.imageView_fund)
        ImageView imageViewFixed;
        @BindView(R.id.iv_star)
        public ImageView imageViewStarFav;
        @BindView(R.id.imageView_asop)
        ImageView tvASAP;
        @BindView(R.id.tv_premium)
        TextView tv_premium;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgS() {
        return imgS;
    }

    public void setImgS(String imgS) {
        this.imgS = imgS;
    }

    public String getImgM() {
        return imgM;
    }

    public void setImgM(String imgM) {
        this.imgM = imgM;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddrAddr() {
        return addrAddr;
    }

    public void setAddrAddr(String addrAddr) {
        this.addrAddr = addrAddr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPriceCurr() {
        return priceCurr;
    }

    public void setPriceCurr(int priceCurr) {
        this.priceCurr = priceCurr;
    }

    @NonNull
    public PriceEx getPriceEx() {
        return priceEx;
    }

    public void setPriceEx(@NonNull PriceEx priceEx) {
        this.priceEx = priceEx;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    @NonNull
    public int getCatId() {
        return catId;
    }

    public void setCatId(@NonNull int catId) {
        this.catId = catId;
    }

    @NonNull
    public int getModerated() {
        return moderated;
    }

    public void setModerated(@NonNull int moderated) {
        this.moderated = moderated;
    }

    @NonNull
    public int getSvcMarked() {
        return svcMarked;
    }

    public void setSvcMarked(@NonNull int svcMarked) {
        this.svcMarked = svcMarked;
    }

    @NonNull
    public int getSvcFixed() {
        return svcFixed;
    }

    public void setSvcFixed(@NonNull int svcFixed) {
        this.svcFixed = svcFixed;
    }

    @NonNull
    public int getSvcPremium() {
        return svcPremium;
    }

    public void setSvcPremium(@NonNull int svcPremium) {
        this.svcPremium = svcPremium;
    }

    @NonNull
    public int getSvcTurbo() {
        return svcTurbo;
    }

    public void setSvcTurbo(@NonNull int svcTurbo) {
        this.svcTurbo = svcTurbo;
    }

    @NonNull
    public int getSvcQuick() {
        return svcQuick;
    }

    public void setSvcQuick(@NonNull int svcQuick) {
        this.svcQuick = svcQuick;
    }

    @NonNull
    public int getSvcUp() {
        return svcUp;
    }

    public void setSvcUp(@NonNull int svcUp) {
        this.svcUp = svcUp;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    public void setCreated(@NonNull String created) {
        this.created = created;
    }

    @NonNull
    public String getPriceSearch() {
        return priceSearch;
    }

    public void setPriceSearch(@NonNull String priceSearch) {
        this.priceSearch = priceSearch;
    }

    @NonNull
    public String getCurrDisplay() {
        return currDisplay;
    }

    public void setCurrDisplay(@NonNull String currDisplay) {
        this.currDisplay = currDisplay;
    }

    @NonNull
    public String getCurrencyDefault() {
        return currencyDefault;
    }

    public void setCurrencyDefault(@NonNull String currencyDefault) {
        this.currencyDefault = currencyDefault;
    }

    @NonNull
    public String getItemPriceDisplay() {
        return itemPriceDisplay;
    }

    public void setItemPriceDisplay(@NonNull String itemPriceDisplay) {
        this.itemPriceDisplay = itemPriceDisplay;
    }

    public String getPublicated() {
        return publicated;
    }

    public void setPublicated(String publicated) {
        this.publicated = publicated;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public boolean isPriceOn() {
        return priceOn;
    }

    public void setPriceOn(boolean priceOn) {
        this.priceOn = priceOn;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getPriceMod() {
        return priceMod;
    }

    public void setPriceMod(String priceMod) {
        this.priceMod = priceMod;
    }

    @NonNull
    public String getDescr() {
        return descr;
    }

    public void setDescr(@NonNull String descr) {
        this.descr = descr;
    }


    public class ImgSize{
        @SerializedName("width")
        @NonNull
        private int width;
        @SerializedName("height")
        @NonNull
        private int height;

        @NonNull
        public int getWidth() {
            return width;
        }

        public void setWidth(@NonNull int width) {
            this.width = width;
        }

        @NonNull
        public int getHeight() {
            return height;
        }

        public void setHeight(@NonNull int height) {
            this.height = height;
        }
    }


}
