package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import solutions.isky.gaurangarevolution.presentation.mvp.advertising.PeriodAdapter;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SvcAbonementItem extends AbstractItem<SvcAbonementItem, SvcAbonementItem.ViewHolder> {
    @NonNull
    @SerializedName("id")
    private int id;
    @NonNull
    @SerializedName("price_free")
    private int price_free;
    @NonNull
    @SerializedName("price_free_period")
    private int price_free_period;
    @NonNull
    @SerializedName("items")
    private int items;
    @NonNull
    @SerializedName("import")
    private int opportunity_import;
    @NonNull
    @SerializedName("svc_mark")
    private int svc_mark;
    @NonNull
    @SerializedName("svc_fix")
    private int svc_fix;
    @NonNull
    @SerializedName("one_time")
    private int one_time;
    @NonNull
    @SerializedName("title")
    private String title;
    @NonNull
    @SerializedName("img")
    private String img;

    @SerializedName("price")
    private List<PriceAbonement>price;

    private String mPeriod;

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
    public int getPrice_free() {
        return price_free;
    }

    public void setPrice_free(@NonNull int price_free) {
        this.price_free = price_free;
    }

    @NonNull
    public int getPrice_free_period() {
        return price_free_period;
    }

    public void setPrice_free_period(@NonNull int price_free_period) {
        this.price_free_period = price_free_period;
    }

    @NonNull
    public int getItems() {
        return items;
    }

    public void setItems(@NonNull int items) {
        this.items = items;
    }

    @NonNull
    public int getOpportunity_import() {
        return opportunity_import;
    }

    public void setOpportunity_import(@NonNull int opportunity_import) {
        this.opportunity_import = opportunity_import;
    }

    @NonNull
    public int getSvc_mark() {
        return svc_mark;
    }

    public void setSvc_mark(@NonNull int svc_mark) {
        this.svc_mark = svc_mark;
    }

    @NonNull
    public int getSvc_fix() {
        return svc_fix;
    }

    public void setSvc_fix(@NonNull int svc_fix) {
        this.svc_fix = svc_fix;
    }

    @NonNull
    public int getOne_time() {
        return one_time;
    }

    public void setOne_time(@NonNull int one_time) {
        this.one_time = one_time;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getImg() {
        return img;
    }

    public void setImg(@NonNull String img) {
        this.img = img;
    }

    public List<PriceAbonement> getPrice() {
        return price;
    }

    public void setPrice(List<PriceAbonement> price) {
        this.price = price;
    }


    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context ctx = viewHolder.itemView.getContext();
        viewHolder.radioButtonPayPal.setVisibility(App.paypal_enable?View.VISIBLE:View.GONE);
        viewHolder.radioButtonAN.setVisibility(App.liqpay_enable?View.VISIBLE:View.GONE);
        viewHolder.radioButtonYandex.setVisibility(App.yandex_enable?View.VISIBLE:View.GONE);
        GlideApp.with(ctx)
                .load(img)
//                .error(R.drawable.ic_no_avatar)
//                .fallback(R.drawable.ic_no_avatar)
                .centerInside()
                // .placeholder(R.drawable.ic_no_avatar)
                .transition(withCrossFade())
                //.transform(new CircleTransform(ctx))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img_svc);
        viewHolder.title_svc.setText(title);
        viewHolder.ll_vieW_descr.setVisibility(isSelected()?View.VISIBLE:View.GONE);
        //viewHolder.descr_svc.setText(fromHtml(description));
        viewHolder.items_abonement.setText(ctx.getResources().getQuantityString(R.plurals.count_ads,items,(items==0?"∞":items )));
        setText(viewHolder.tv_import,opportunity_import);
        setText(viewHolder.svc_mark,svc_mark);
        setText(viewHolder.svc_fix,svc_fix);
        if(price_free==0){
            viewHolder.spinner.setVisibility(View.VISIBLE);
            viewHolder.tv_free_period.setVisibility(View.GONE);
            PeriodAdapter mAdapter = new PeriodAdapter(ctx, price);
            viewHolder.spinner.setAdapter(mAdapter);

            viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    PriceAbonement clickedItem = (PriceAbonement) parent.getItemAtPosition(position);
                    String period = clickedItem.getEx();
                    String price= clickedItem.getPr();
                    viewHolder.tv_period.setText(period);
                    viewHolder.for_pay.setText(String.format(ctx.getString(R.string.to_pay),price));
                    mPeriod=clickedItem.getPeriod();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    mPeriod="0";
                }
            });
            viewHolder.spinner.setSelection(0);
        }else{
            viewHolder.spinner.setVisibility(View.GONE);
            viewHolder.tv_free_period.setVisibility(View.VISIBLE);
            viewHolder.tv_free_period.setText(price_free_period==0?ctx.getString(R.string.on_time):price.get(0).getM());
            viewHolder.tv_period.setText(price_free_period==0?"":price.get(0).getEx());
            viewHolder.for_pay.setText(String.format(ctx.getString(R.string.to_pay),ctx.getString(R.string.free_price)));
        }
        viewHolder.radioGroupPC.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checkedRadioButton = viewHolder.radioGroupPC.findViewById(checkedId);
            positionPC = checkedRadioButton.getTag().toString();
        });
        viewHolder.radioGroupPC.setVisibility(price_free>0?View.GONE:View.VISIBLE);
    }
    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.title_svc.setText(null);
        viewHolder.descr_svc.setText(null);
        viewHolder.items_abonement.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);

    }


    @Override
    public int getType() {
        return R.id.svc_abonement_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_svc_abonement;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;
        @BindView(R.id.img_svc)
        ImageView img_svc;
        @BindView(R.id.tv_title)
        TextView title_svc;
        @BindView(R.id.tv_Descr)
        TextView descr_svc;
        @BindView(R.id.items_abonement)
        TextView items_abonement;
        @BindView(R.id.ll_vieW_descr)
        LinearLayout ll_vieW_descr;
        @BindView(R.id.ll_btn)
        LinearLayout ll_btn;
        @BindView(R.id.btn_bye)
        public Button btn_bye;

        @BindView(R.id.tv_import)
        TextView tv_import;
        @BindView(R.id.svc_mark)
        TextView svc_mark;
        @BindView(R.id.svc_fix)
        TextView svc_fix;
        @BindView(R.id.tv_period)
        TextView tv_period;
        @BindView(R.id.for_pay)
        TextView for_pay;
        @BindView(R.id.spinner)
        Spinner spinner;
        @BindView(R.id.tv_free_period)
        TextView tv_free_period;
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
            this.view=convertView;
        }
    }

    private void setText(TextView v, int i){
        if(i==0){
            v.setText(Html.fromHtml("<s>"+v.getText().toString()+"</s>"));
            v.setTextColor(Color.GRAY);
        }else{
            v.setTextColor(Color.BLACK);
            v.setText(v.getText().toString());
        }
    }

    public String getPeriod(){
        return mPeriod;
    }
}
