package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SvcModel extends AbstractItem<SvcModel, SvcModel.ViewHolder> {
    @SerializedName("id")
    @NonNull
    private int id;

    @SerializedName("keyword")
    @NonNull
    private String keyword;
    @SerializedName("module")
    @NonNull
    private String module;
    @SerializedName("module_title")
    @NonNull
    private String moduleTitle;
    @SerializedName("title")
    @NonNull
    private String title;
    @SerializedName("price")
    @NonNull
    private double price;
    @SerializedName("modified")
    @NonNull
    private String modified;
    @SerializedName("modified_uid")
    @NonNull
    private String modifiedUid;
    @SerializedName("icon_b")
    @NonNull
    private String iconB;
    @SerializedName("icon_s")
    @NonNull
    private String iconS;
    @SerializedName("num")
    @NonNull
    private String num;
    @SerializedName("period")
    @NonNull
    private long period;
    @SerializedName("color")
    @NonNull
    private String color;
    @SerializedName("add_form")
    @NonNull
    private long addForm;
    @SerializedName("on")
    @NonNull
    private long on;
    @SerializedName("title_view")
    @NonNull
    private String titleView;
    @SerializedName("description")
    @NonNull
    private String description;
    @SerializedName("description_full")
    @NonNull
    private String descriptionFull;
    @SerializedName("price_display")
    @NonNull
    private String price_display;

    @SerializedName("price_bonus")
    @NonNull
    private float price_bonus;

    private String positionPC= Constants.BALANCE;


    public String getPositionPC() {
        return positionPC;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }



    @NonNull
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(@NonNull String keyword) {
        this.keyword = keyword;
    }

    @NonNull
    public String getModule() {
        return module;
    }

    public void setModule(@NonNull String module) {
        this.module = module;
    }

    @NonNull
    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(@NonNull String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public double getPrice() {
        return price;
    }

    public void setPrice(@NonNull double price) {
        this.price = price;
    }

    @NonNull
    public String getModified() {
        return modified;
    }

    public void setModified(@NonNull String modified) {
        this.modified = modified;
    }

    @NonNull
    public String getModifiedUid() {
        return modifiedUid;
    }

    public void setModifiedUid(@NonNull String modifiedUid) {
        this.modifiedUid = modifiedUid;
    }

    @NonNull
    public String getIconB() {
        return iconB;
    }

    public void setIconB(@NonNull String iconB) {
        this.iconB = iconB;
    }

    @NonNull
    public String getIconS() {
        return iconS;
    }

    public void setIconS(@NonNull String iconS) {
        this.iconS = iconS;
    }

    @NonNull
    public String getNum() {
        return num;
    }

    public void setNum(@NonNull String num) {
        this.num = num;
    }

    @NonNull
    public long getPeriod() {
        return period;
    }

    public void setPeriod(@NonNull long period) {
        this.period = period;
    }

    @NonNull
    public String getColor() {
        return color;
    }

    public void setColor(@NonNull String color) {
        this.color = color;
    }

    @NonNull
    public long getAddForm() {
        return addForm;
    }

    public void setAddForm(@NonNull long addForm) {
        this.addForm = addForm;
    }

    @NonNull
    public long getOn() {
        return on;
    }

    public void setOn(@NonNull long on) {
        this.on = on;
    }

    @NonNull
    public String getTitleView() {
        return titleView;
    }

    public void setTitleView(@NonNull String titleView) {
        this.titleView = titleView;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getDescriptionFull() {
        return descriptionFull;
    }

    public void setDescriptionFull(@NonNull String descriptionFull) {
        this.descriptionFull = descriptionFull;
    }

    @NonNull
    public String getPrice_display() {
        return price_display;
    }

    public void setPrice_display(@NonNull String price_display) {
        this.price_display = price_display;
    }

    @NonNull
    public float getPrice_bonus() {
        return price_bonus;
    }

    public void setPrice_bonus(@NonNull float price_bonus) {
        this.price_bonus = price_bonus;
    }

    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.radioButtonPayPal.setVisibility(App.paypal_enable?View.VISIBLE:View.GONE);
        viewHolder.radioButtonAN.setVisibility(App.liqpay_enable?View.VISIBLE:View.GONE);
        viewHolder.radioButtonYandex.setVisibility(App.yandex_enable?View.VISIBLE:View.GONE);
        Context ctx = viewHolder.itemView.getContext();
        GlideApp.with(ctx)
                .load(iconB)
//                .error(R.drawable.ic_no_avatar)
//                .fallback(R.drawable.ic_no_avatar)
                .centerCrop()
                // .placeholder(R.drawable.ic_no_avatar)
                .transition(withCrossFade())
                .transform(new CircleTransform(ctx))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img_svc);
        viewHolder.title_svc.setText(titleView);
        viewHolder.ll_vieW_descr.setVisibility(isSelected() ? View.VISIBLE : View.GONE);
        viewHolder.descr_svc.setText(fromHtml(description));
        viewHolder.price_svc.setText(String.valueOf(price_display));
        viewHolder.radioGroupPC.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checkedRadioButton = viewHolder.radioGroupPC.findViewById(checkedId);
            positionPC = checkedRadioButton.getTag().toString();
        });
        viewHolder.radioGroupPC.setVisibility(price>0?View.VISIBLE:View.GONE);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.title_svc.setText(null);
        viewHolder.descr_svc.setText(null);
        viewHolder.price_svc.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);

    }



    @Override
    public int getType() {
        return R.id.svc_model_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_svc;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;
        @BindView(R.id.img_svc)
        ImageView img_svc;
        @BindView(R.id.tv_title)
        TextView title_svc;
        @BindView(R.id.tv_Descr)
        TextView descr_svc;
        @BindView(R.id.price_svc)
        TextView price_svc;
        @BindView(R.id.ll_vieW_descr)
        LinearLayout ll_vieW_descr;
        @BindView(R.id.ll_btn)
        LinearLayout ll_btn;
        @BindView(R.id.btn_bye)
        public Button btn_bye;
        @BindView(R.id.radioGroupPC)
        RadioGroup radioGroupPC;
        @BindView(R.id.radioButtonPayPal)
        RadioButton radioButtonPayPal;
        @BindView(R.id.radioButtonAN)
        RadioButton radioButtonAN;
        @BindView(R.id.radioButtonYandex)
        RadioButton radioButtonYandex;

        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
            this.view = convertView;
        }
    }
}
