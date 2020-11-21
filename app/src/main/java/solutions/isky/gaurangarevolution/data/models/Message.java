package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Message implements Serializable {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("shop_id")
    @NonNull
    private int shop_id;
    @SerializedName("item_id")
    @NonNull
    private int item_id;
    @SerializedName("author")
    @NonNull
    private int author;
    @SerializedName("recipient")
    @NonNull
    private int recipient;
    @SerializedName("message")
    @NonNull
    private String message;
    @SerializedName("attach")
    @NonNull
    private String attach;

    @SerializedName("is_new")
    @NonNull
    private int is_new;
    @SerializedName("created")
    @NonNull
    private String created;

    @SerializedName("readed")
    @NonNull
    private String readed;
    @SerializedName("blocked")
    @NonNull
    private int blocked;
    @SerializedName("my")
    @NonNull
    private int my;
    @SerializedName("new")
    @NonNull
    private int new_mess;
    //************* data ads
    @SerializedName("item_img")

    private String item_img;
    @SerializedName("item_title")

    private String item_title;
    @SerializedName("item_publicated")
    @NonNull
    private String item_publicated;
    @SerializedName("item_price")

    private double item_price;
    @SerializedName("item_price_curr")

    private int item_price_curr;
    @SerializedName("item_city")

    private String item_city;
    @SerializedName("item_price_with_curr")

    private String item_price_with_curr;
    @SerializedName("username")

    private String username;
    @SerializedName("avatar")
    @NonNull
    private String avatar;
    @SerializedName("price_ex")
    PriceEx priceEx;

    public PriceEx getPriceEx() {
        return priceEx;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(@NonNull int shop_id) {
        this.shop_id = shop_id;
    }

    @NonNull
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(@NonNull int item_id) {
        this.item_id = item_id;
    }

    @NonNull
    public int getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull int author) {
        this.author = author;
    }

    @NonNull
    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(@NonNull int recipient) {
        this.recipient = recipient;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    @NonNull
    public String getAttach() {
        return attach;
    }

    public void setAttach(@NonNull String attach) {
        this.attach = attach;
    }

    @NonNull
    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(@NonNull int is_new) {
        this.is_new = is_new;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    public void setCreated(@NonNull String created) {
        this.created = created;
    }

    @NonNull
    public String getReaded() {
        return readed;
    }

    public void setReaded(@NonNull String readed) {
        this.readed = readed;
    }

    @NonNull
    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(@NonNull int blocked) {
        this.blocked = blocked;
    }

    @NonNull
    public int getMy() {
        return my;
    }

    public void setMy(@NonNull int my) {
        this.my = my;
    }

    @NonNull
    public int getNew_mess() {
        return new_mess;
    }

    public void setNew_mess(@NonNull int new_mess) {
        this.new_mess = new_mess;
    }

    @NonNull
    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(@NonNull String item_img) {
        this.item_img = item_img;
    }

    @NonNull
    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(@NonNull String item_title) {
        this.item_title = item_title;
    }

    @NonNull
    public String getItem_publicated() {
        return item_publicated;
    }

    public void setItem_publicated(@NonNull String item_publicated) {
        this.item_publicated = item_publicated;
    }

    @NonNull
    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(@NonNull double item_price) {
        this.item_price = item_price;
    }

    @NonNull
    public int getItem_price_curr() {
        return item_price_curr;
    }

    public void setItem_price_curr(@NonNull int item_price_curr) {
        this.item_price_curr = item_price_curr;
    }

    @NonNull
    public String getItem_city() {
        return item_city;
    }

    public void setItem_city(@NonNull String item_city) {
        this.item_city = item_city;
    }

    @NonNull
    public String getItem_price_with_curr() {
        return item_price_with_curr;
    }

    public void setItem_price_with_curr(@NonNull String item_price_with_curr) {
        this.item_price_with_curr = item_price_with_curr;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@NonNull String avatar) {
        this.avatar = avatar;
    }
}