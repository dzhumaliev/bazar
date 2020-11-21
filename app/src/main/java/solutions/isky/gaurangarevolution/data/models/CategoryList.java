package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryList implements Serializable {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("pid")
    @NonNull
    private int pid;
    @SerializedName("i")
    @NonNull
    private String i;
    @SerializedName("t")
    @NonNull
    private String t;
    @SerializedName("k")
    @NonNull
    private String k;
    @SerializedName("items")
    @NonNull
    private int items;
    @SerializedName("subs")
    @NonNull
    private int subs;
    @SerializedName("lvl")
    @NonNull
    private int lvl;
    @SerializedName("photos_max_count")
    @NonNull
    private int photos_max_count;
    @SerializedName("addr")
    @NonNull
    private int addr;
    @SerializedName("price")
    @NonNull
    private int price;

    @SerializedName("tpl_title_enabled")
    @NonNull
    private int tpl_title_enabled;

    @SerializedName("price_sett")
    @NonNull
    private PriceSetting price_sett;


    @NonNull
    public int getTpl_title_enabled() {
        return tpl_title_enabled;
    }

    public void setTpl_title_enabled(@NonNull int tpl_title_enabled) {
        this.tpl_title_enabled = tpl_title_enabled;
    }

    @NonNull
    public PriceSetting getPrice_sett() {
        return price_sett;
    }

    public void setPrice_sett(@NonNull PriceSetting price_sett) {
        this.price_sett = price_sett;
    }

    @NonNull
    public int getPrice() {
        return price;
    }

    public void setPrice(@NonNull int price) {
        this.price = price;
    }

    @NonNull
    public int getPhotos_max_count() {
        return photos_max_count;
    }

    public void setPhotos_max_count(@NonNull int photos_max_count) {
        this.photos_max_count = photos_max_count;
    }

    @NonNull
    public int getAddr() {
        return addr;
    }

    public void setAddr(@NonNull int addr) {
        this.addr = addr;
    }

    @NonNull
    public String getI() {
        return i;
    }

    public void setI(@NonNull String i) {
        this.i = i;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getItems() {
        return items;
    }

    public void setItems(@NonNull int items) {
        this.items = items;
    }

    @NonNull
    public String getK() {
        return k;
    }

    public void setK(@NonNull String k) {
        this.k = k;
    }

    @NonNull
    public int getLvl() {
        return lvl;
    }

    public void setLvl(@NonNull int lvl) {
        this.lvl = lvl;
    }

    @NonNull
    public int getPid() {
        return pid;
    }

    public void setPid(@NonNull int pid) {
        this.pid = pid;
    }

    @NonNull
    public int getSubs() {
        return subs;
    }

    public void setSubs(@NonNull int subs) {
        this.subs = subs;
    }

    @NonNull
    public String getT() {
        return t;
    }

    public void setT(@NonNull String t) {
        this.t = t;
    }
}