package solutions.isky.gaurangarevolution.data.models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ChatsList extends AbstractItem<ChatsList, ChatsList.ViewHolder> {

    private void applyDefaultStyle(ViewHolder viewHolder) {


        if (viewHolder.tvDate != null) {
            viewHolder.tvDate.setTextColor(Color.BLACK);
            viewHolder.tvDate.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        }

        if (viewHolder.tvLastMessage != null) {
            viewHolder.tvLastMessage.setTextColor(Color.parseColor("#969797"));
            viewHolder.tvLastMessage.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        }

    }

    private void applyUnreadStyle(ViewHolder viewHolder) {


        if (viewHolder.tvDate != null) {
            viewHolder.tvDate.setTextColor(Color.parseColor("#027db9"));
            viewHolder.tvDate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        }

        if (viewHolder.tvLastMessage != null) {
            viewHolder.tvLastMessage.setTextColor(Color.parseColor("#000000"));
            viewHolder.tvLastMessage.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        }

    }

    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);
        if (messages_new > 0) {
            applyUnreadStyle(viewHolder);
        } else {
            applyDefaultStyle(viewHolder);
        }
        //get the context
        Context ctx = viewHolder.itemView.getContext();
        viewHolder.tvName.setText(username);
        viewHolder.tvName.setCompoundDrawablesWithIntrinsicBounds(0,0,(messages_new > 0)?R.drawable.bubble_circle:0,0);
        viewHolder.tvDate.setText(AppUtils.getTimeDialogs(getLast_message_date()));
        GlideApp.with(ctx)
                .load(user_avatar)
                .error(R.drawable.ic_no_avatar)
                .fallback(R.drawable.ic_no_avatar)
                .centerCrop()
                .placeholder(R.drawable.ic_no_avatar)
                .transition(withCrossFade())
                .transform(new CircleTransform(ctx))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.avatar_user);
        viewHolder.tvBubble.setText(String.valueOf(messages_new));
       // viewHolder.tvBubble.setVisibility(messages_new > 0 ? View.VISIBLE : View.GONE);
        viewHolder.tvLastMessage.setText(message);


    }

    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.tvName.setText(null);
        viewHolder.tvBubble.setText(null);
        viewHolder.tvLastMessage.setText(null);

    }


    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.list_dialog;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_dialog;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dialogAvatar)
        ImageView avatar_user;
        @BindView(R.id.dialogContainer)
        ViewGroup container;
        @BindView(R.id.dialogRootLayout)
        ViewGroup root;
        @BindView(R.id.dialogName)
        TextView tvName;
        @BindView(R.id.dialogDate)
        TextView tvDate;
        @BindView(R.id.dialogLastMessage)
        TextView tvLastMessage;
        @BindView(R.id.dialogUnreadBubble)
        TextView tvBubble;
        @BindView(R.id.dialogDividerContainer)
        ViewGroup dividerContainer;
        @BindView(R.id.dialogDivider)
        View divider;

        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }

    @SerializedName("user_id")
    private int user_id;
    @SerializedName("interlocutor_id")
    private int interlocutor_id;
    @SerializedName("last_message_id")
    private int last_message_id;
    @SerializedName("last_message_date")
    private String last_message_date;
    @SerializedName("messages_new")
    private int messages_new;
    @SerializedName("messages_total")
    private int messages_total;
    @SerializedName("message")
    private String message;
    @SerializedName("username")
    private String username;
    @SerializedName("avatar")
    private String user_avatar;
    @SerializedName("lastmy")
    private int lastmy;



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getInterlocutor_id() {
        return interlocutor_id;
    }

    public void setInterlocutor_id(int interlocutor_id) {
        this.interlocutor_id = interlocutor_id;
    }

    public int getLast_message_id() {
        return last_message_id;
    }

    public void setLast_message_id(int last_message_id) {
        this.last_message_id = last_message_id;
    }

    public String getLast_message_date() {
        return last_message_date;
    }

    public void setLast_message_date(String last_message_date) {
        this.last_message_date = last_message_date;
    }

    public int getMessages_new() {
        return messages_new;
    }

    public void setMessages_new(int messages_new) {
        this.messages_new = messages_new;
    }

    public int getMessages_total() {
        return messages_total;
    }

    public void setMessages_total(int messages_total) {
        this.messages_total = messages_total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public int getLastmy() {
        return lastmy;
    }

    public void setLastmy(int lastmy) {
        this.lastmy = lastmy;
    }
}
