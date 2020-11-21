package solutions.isky.gaurangarevolution.data.models;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;

public class ItemPayments extends AbstractItem<ItemPayments, ItemPayments.ViewHolder> {
    @NonNull
    @SerializedName("id")
    private int id;
    @NonNull
    @SerializedName("svc_id")
    private int svc_id;
    @NonNull
    @SerializedName("item_id")
    private int item_id;
    @NonNull
    @SerializedName("type")
    private int type;
    @NonNull
    @SerializedName("amount")
    private String amount;
    @NonNull
    @SerializedName("date")
    private String date_payment;
    @NonNull
    @SerializedName("title")
    private String title;
    @NonNull
    @SerializedName("user_balance")
    private String user_balance;
    @NonNull
    @SerializedName("svc")
    private SvcModel svcModel;
    @NonNull
    @SerializedName("item_title")
    private AdInfo item_title;

    public void setDate_payment(@NonNull String date_payment) {
        this.date_payment = date_payment;
    }

    @NonNull
    public SvcModel getSvcModel() {
        return svcModel;
    }

    public void setSvcModel(@NonNull SvcModel svcModel) {
        this.svcModel = svcModel;
    }

    @NonNull
    public AdInfo getItem_title() {
        return item_title;
    }

    public void setItem_title(@NonNull AdInfo item_title) {
        this.item_title = item_title;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getSvc_id() {
        return svc_id;
    }

    public void setSvc_id(@NonNull int svc_id) {
        this.svc_id = svc_id;
    }

    @NonNull
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(@NonNull int item_id) {
        this.item_id = item_id;
    }

    public void setType(@NonNull int type) {
        this.type = type;
    }

    @NonNull
    public String getAmount() {
        return amount;
    }

    public void setAmount(@NonNull String amount) {
        this.amount = amount;
    }

    @NonNull
    public String getDate() {
        return AppUtils.getTimePAYMents(date_payment);
    }

    public void setDate(@NonNull String date) {
        this.date_payment = date;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(@NonNull String user_balance) {
        this.user_balance = user_balance;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.tv_data.setText(getDate());
        viewHolder.tv_amount.setTextColor((Constants.TYPE_IN_PAY == type || Constants.TYPE_IN_GIFT == type) ? viewHolder.itemView.getContext().getResources().getColor(R.color.color_add_money) : viewHolder.itemView.getContext().getResources().getColor(R.color.color_text_complain));
        viewHolder.tv_amount.setText((Constants.TYPE_IN_PAY == type || Constants.TYPE_IN_GIFT == type) ? amount : "-" + amount);
        viewHolder.tv_id_tr.setText(String.format(viewHolder.itemView.getContext().getString(R.string.number_tr), id));
        if(svcModel!=null&&svcModel.getTitle()!=null&&!TextUtils.isEmpty(svcModel.getTitle())){
            String name_svc=" \""+svcModel.getTitle()+"\"";
            String text =String.format(viewHolder.itemView.getContext().getString(R.string.pay_svc_title),name_svc);
            viewHolder.tv_title.setText(Html.fromHtml(title));
        }else{
            viewHolder.tv_title.setText(title);
        }
        if(item_title!=null&&!TextUtils.isEmpty(item_title.getTitle())){
//            viewHolder.tv_title_post.setText(item_title.getTitle());
//            viewHolder.tv_title_post.setVisibility(View.VISIBLE);
            viewHolder.tv_title_post.setVisibility(View.GONE);
        }else{
            viewHolder.tv_title_post.setVisibility(View.GONE);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            viewHolder.tv_title_post.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY));
//        } else {
//            viewHolder.tv_title_post.setText(Html.fromHtml(title));
//        }

    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.tv_data.setText(null);
        holder.tv_amount.setText(null);
        holder.tv_id_tr.setText(null);
        holder.tv_title_post.setText(null);
        holder.tv_title.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_data)
        TextView tv_data;

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_title_post)
        TextView tv_title_post;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.tv_id_tr)
        TextView tv_id_tr;

        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }

    @Override
    public int getType() {
        return R.id.item_payment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_payments;
    }
}