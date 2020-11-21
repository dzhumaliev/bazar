package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AdItemMy extends AbstractItem<AdItemMy, AdItemMy.ViewHolder> {
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
    @NonNull
    @SerializedName("fav")
    private int fav;

    @NonNull
    @SerializedName("views_item_total")
    private int views_item_total;
    @NonNull
    @SerializedName("views_contacts_total")
    private int views_contacts_total;
    @NonNull
    @SerializedName("messages_total")
    private int messages_total;
    @NonNull
    @SerializedName("blocked_reason")
    private String blocked_reason;
    @NonNull
    @SerializedName("publicated_to")
    private String publicated_to;
    @NonNull
    @SerializedName("up_free")
    private int up_free;

    @NonNull
    @SerializedName("refresh")
    private int refresh;


    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        //get the context
        Context ctx = viewHolder.itemView.getContext();
        viewHolder.title_ad.setText(title);

        viewHolder.tv_Price.setText(AppUtils.getPriceEx(priceEx, itemPriceDisplay));

        GlideApp.with(ctx)
                .load(imgM)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder_ad)
                .error(R.drawable.ic_placeholder_ad)
                .fallback(R.drawable.ic_placeholder_ad)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img_item);
        viewHolder.tv_total_view.setText(String.valueOf(views_item_total));
        viewHolder.tv_view_phone.setText(String.valueOf(views_contacts_total));
        viewHolder.tv_mess_count.setText(String.valueOf(messages_total));
        String date = String.format(ctx.getResources().getString(R.string.date_ads), AppUtils.getTimeMyPostFrom(publicated, ctx), AppUtils.getTimeMyPostFrom(publicated_to, ctx));
        viewHolder.tv_period.setText(Html.fromHtml(date));
        viewHolder.btn_up_ad.setVisibility((status == 3) ? View.VISIBLE : View.INVISIBLE);
        viewHolder.btn_up_ad.setText((status == 3) ? (refresh == 1) ? ctx.getString(R.string.refresh) : ctx.getResources().getString(R.string.up_ad) : ctx.getResources().getString(R.string.activate_post));

        viewHolder.btn_actions_ad.setVisibility((status == 3) ? View.VISIBLE : View.GONE);
        viewHolder.btn_delete_post.setVisibility((status == 3) ? View.GONE : View.VISIBLE);

        if (status == 3) {
            if(AppState.getInstance(ctx).getInteger(AppState.Key.PREMODERATION) == 1){
                viewHolder.btn_up_ad.setEnabled((up_free == 1 || refresh == 1) ? true : false);
                viewHolder.btn_actions_ad.setEnabled(true);
                viewHolder.view_btn_other.setVisibility(moderated == 1 ? View.VISIBLE : View.GONE);
                viewHolder.btn_deactive.setVisibility((moderated == 1 ? View.VISIBLE : View.GONE));
                viewHolder.btn_share.setVisibility((moderated == 1 ? View.VISIBLE : View.GONE));
                viewHolder.btn_active.setVisibility(View.GONE);
            }else{
                viewHolder.btn_up_ad.setEnabled((up_free == 1 || refresh == 1) ? true : false);
                viewHolder.btn_actions_ad.setEnabled(true);
                viewHolder.view_btn_other.setVisibility(moderated == 1 ? View.VISIBLE : View.VISIBLE);
                viewHolder.btn_deactive.setVisibility((moderated == 1 ? View.VISIBLE : View.VISIBLE));
                viewHolder.btn_share.setVisibility((moderated == 1 ? View.VISIBLE : View.VISIBLE));
                viewHolder.btn_active.setVisibility(View.GONE);
            }
        } else {
            viewHolder.btn_up_ad.setEnabled((status == 5) ? false : true);
            viewHolder.btn_actions_ad.setEnabled(true);
            viewHolder.btn_deactive.setVisibility(View.GONE);
            viewHolder.btn_share.setVisibility(View.GONE);
            viewHolder.btn_active.setVisibility(View.VISIBLE);
        }

        if (status == 5 && !TextUtils.isEmpty(blocked_reason)) {
            viewHolder.blocked_reason.setText(blocked_reason);
            viewHolder.blocked_reason.setVisibility(View.VISIBLE);
        } else {
            viewHolder.blocked_reason.setVisibility(View.GONE);
        }
        viewHolder.btn_open_extra.setOnClickListener(v -> {
            viewHolder.ll_title_price.setVisibility(View.GONE);
            viewHolder.extra_view.setVisibility(View.VISIBLE);
        });

        viewHolder.btn_close_extra.setOnClickListener(v -> {
            viewHolder.extra_view.setVisibility(View.GONE);
            viewHolder.ll_title_price.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.title_ad.setText(null);
        viewHolder.tv_Price.setText(null);
        viewHolder.tv_period.setText(null);
        viewHolder.tv_total_view.setText(null);
        viewHolder.tv_view_phone.setText(null);
        viewHolder.tv_mess_count.setText(null);

    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);

    }

    @Override
    public int getType() {
        return R.id.my_ad_item_active;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.my_item_ad;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_ad)
        ImageView img_item;
        @BindView(R.id.tv_Title)
        TextView title_ad;
        @BindView(R.id.tv_period)
        TextView tv_period;
        @BindView(R.id.tv_Price)
        TextView tv_Price;
        @BindView(R.id.tv_total_view)
        TextView tv_total_view;
        @BindView(R.id.tv_view_phone)
        TextView tv_view_phone;
        @BindView(R.id.tv_mess_count)
        TextView tv_mess_count;
        @BindView(R.id.btn_up_ad)
        public Button btn_up_ad;
        @BindView(R.id.btn_delete_post)
        public Button btn_delete_post;
        @BindView(R.id.btn_actions_ad)
        public Button btn_actions_ad;
        @BindView(R.id.ll_extra)
        LinearLayout extra_view;
        @BindView(R.id.ll_title_price)
        LinearLayout ll_title_price;
        @BindView(R.id.btn_extra)
        public ImageView btn_open_extra;
        @BindView(R.id.btn_close_extra)
        public ImageView btn_close_extra;
        @BindView(R.id.blocked_reason)
        TextView blocked_reason;
        @BindView(R.id.view_btn_other)
        LinearLayout view_btn_other;
        @BindView(R.id.btn_edit)
        public Button btn_edit;
        @BindView(R.id.btn_deactive)
        public Button btn_deactive;
        @BindView(R.id.btn_share)
        public Button btn_share;
        @BindView(R.id.btn_active)
        public Button btn_active;


        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }

    @NonNull
    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(@NonNull int refresh) {
        this.refresh = refresh;
    }

    @NonNull
    public int getUp_free() {
        return up_free;
    }

    public void setUp_free(@NonNull int up_free) {
        this.up_free = up_free;
    }

    @NonNull
    public int getViews_item_total() {
        return views_item_total;
    }

    public void setViews_item_total(@NonNull int views_item_total) {
        this.views_item_total = views_item_total;
    }

    @NonNull
    public int getViews_contacts_total() {
        return views_contacts_total;
    }

    public void setViews_contacts_total(@NonNull int views_contacts_total) {
        this.views_contacts_total = views_contacts_total;
    }

    @NonNull
    public int getMessages_total() {
        return messages_total;
    }

    public void setMessages_total(@NonNull int messages_total) {
        this.messages_total = messages_total;
    }

    @NonNull
    public String getBlocked_reason() {
        return blocked_reason;
    }

    public void setBlocked_reason(@NonNull String blocked_reason) {
        this.blocked_reason = blocked_reason;
    }

    @NonNull
    public String getPublicated_to() {
        return publicated_to;
    }

    public void setPublicated_to(@NonNull String publicated_to) {
        this.publicated_to = publicated_to;
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

    private void setupImage(final SimpleDraweeView simpleDraweeView, final Uri uri) {
        simpleDraweeView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                simpleDraweeView.getViewTreeObserver().removeOnPreDrawListener(this);
                ImageRequestBuilder request = ImageRequestBuilder.newBuilderWithSource(uri);
                if (UriUtil.isNetworkUri(uri)) {
                    request.setProgressiveRenderingEnabled(true);
                } else {
                    request.setResizeOptions(new ResizeOptions(
                            simpleDraweeView.getLayoutParams().width,
                            simpleDraweeView.getLayoutParams().height));
                }
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(simpleDraweeView.getController())
                        .setImageRequest(request.build())
                        .build();
                simpleDraweeView.setController(controller);
                return true;
            }
        });
    }

    private void loadImage(Uri fileUri, final SimpleDraweeView simpeDraweeView) {


        ImageRequest requestBuilder = ImageRequestBuilder.newBuilderWithSource(fileUri)
                .setProgressiveRenderingEnabled(true)
                .build();


        ControllerListener<ImageInfo> contollerListener = new BaseControllerListener<ImageInfo>() {

            public void onFinalImageSet(String id, ImageInfo imageinfo, Animatable animatable) {

                if (imageinfo != null) {
                    updateViewSize(imageinfo, simpeDraweeView);
                }
            }

            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                if (imageInfo != null) {
                    updateViewSize(imageInfo, simpeDraweeView);
                }
            }
        };
        DraweeController contoller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(contollerListener)
                .setImageRequest(requestBuilder)
                .build();


        simpeDraweeView.setController(contoller);


    }

    private void updateViewSize(ImageInfo imageinfo, final SimpleDraweeView simpleDraweeView) {
        simpleDraweeView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        simpleDraweeView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        simpleDraweeView.setAspectRatio((float) imageinfo.getWidth() / imageinfo.getHeight());
        simpleDraweeView.requestLayout();
    }
}
