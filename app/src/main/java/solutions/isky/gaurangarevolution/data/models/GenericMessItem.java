package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mikepenz.fastadapter.items.ModelAbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GenericMessItem extends ModelAbstractItem<MessModel, GenericMessItem, GenericMessItem.ViewHolder> {

    public GenericMessItem(MessModel messModel) {
        super(messModel);
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;
        //item_post
        @BindView(R.id.img_post)
        SimpleDraweeView img_post;
        @BindView(R.id.tv_title_post)
        TextView tv_title_post;
        @BindView(R.id.tv_price)
        TextView tv_price;
        //item_user_mess

        @BindView(R.id.messageUserAvatar)
        @Nullable
        ImageView messageUserAvatar;
        @BindView(R.id.messageText)
        TextView messageText;
        @BindView(R.id.messageTime)
        TextView messageTime;
        @BindView(R.id.view_poster)
        RelativeLayout view_poster;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }


    @Override
    public int getType() {
        return R.id.fastadapter_income_generic_mess_item_id;
    }

    /**
     * defines the layout which will be used for this item in the list
     *
     * @return the layout for this item
     */
    @Override
    public int getLayoutRes() {
        return R.layout.income_mess;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);
        Context ctx = viewHolder.itemView.getContext();
        viewHolder.messageText.setText(Html.fromHtml(getModel().message.getMessage()));
        viewHolder.messageTime.setText(AppUtils.getTimeMessage(getModel().message.getCreated()));
        if (viewHolder.messageUserAvatar != null){
            GlideApp.with(ctx)
                    .load(getModel().userInMess.getAvatar())
                    .error(R.drawable.ic_no_avatar)
                    .fallback(R.drawable.ic_no_avatar)
                    .centerCrop()
                    .placeholder(R.drawable.ic_no_avatar)
                    .transition(withCrossFade())
                    .transform(new CircleTransform(ctx))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.messageUserAvatar);
        }

        if (getModel().message.getItem_id()>0&&getModel().itemsInMess.get(getModel().message.getItem_id())!=null) {
            viewHolder.view_poster.setVisibility(View.VISIBLE);

            viewHolder.tv_title_post.setText(getModel().itemsInMess.get(getModel().message.getItem_id()).getTitle());

            viewHolder.tv_price.setText(getModel().itemsInMess.get(getModel().message.getItem_id()).getPrice());
            if (getModel().message.getPriceEx() != null) {
                viewHolder.tv_price.setText(AppUtils.getPriceEx(getModel().message.getPriceEx(),getModel().message.getItem_price_with_curr()));
            }
            viewHolder.img_post.setImageURI(getModel().itemsInMess.get(getModel().message.getItem_id()).getImg_ad());

        } else {
            viewHolder.view_poster.setVisibility(View.GONE);
        }

    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.messageText.setText(null);
        holder.messageTime.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }
}
