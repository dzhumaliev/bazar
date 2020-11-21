package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class ItemCategoryList extends RealmObject implements Serializable {
    @SerializedName("id")

    @NonNull
    private int id;
    @SerializedName("pid")
    @NonNull
    private int pid;
    @SerializedName("i")
    @NonNull
    private String ic_path;
    @SerializedName("t")
    @NonNull
    private String title;
    @SerializedName("k")
    @NonNull
    private String title_full_path;
    @SerializedName("items")
    @NonNull
    private int count_items;
    @SerializedName("subs")
    @NonNull
    private int subs;

    @SerializedName("lvl")
    @NonNull
    private int level;
    @SerializedName("title")
    @NonNull
    private String title_two;
    @SerializedName("numleft")
    @NonNull
    private int numleft;
    @SerializedName("numright")
    @NonNull
    private int numright;

    @SerializedName("photos_max_count")
    @NonNull
    private int photos_max_count;
    @SerializedName("addr")
    @NonNull
    private int addr;
    @SerializedName("price")
    @NonNull
    private int price;
    @SerializedName("price_sett")
    @NonNull
    private PriceSetting price_sett;


    @NonNull
    public int getPrice() {
        return price;
    }

    public void setPrice(@NonNull int price) {
        this.price = price;
    }

    @NonNull
    public PriceSetting getPrice_sett() {
        return price_sett;
    }

    public void setPrice_sett(@NonNull PriceSetting price_sett) {
        this.price_sett = price_sett;
    }

    public ItemCategoryList() {
    }

    public ItemCategoryList(@NonNull int id, @NonNull String title, @NonNull int count_items, @NonNull int subs,
                            @NonNull int level,@NonNull int photos_max_count,@NonNull int addr,@NonNull int price,@NonNull PriceSetting price_sett) {
        this.id = id;
        this.title = title;
        this.count_items = count_items;
        this.subs = subs;
        this.level = level;
        this.photos_max_count = photos_max_count;
        this.addr = addr;
        this.price=price;
        this.price_sett=price_sett;

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
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getPid() {
        return pid;
    }

    public void setPid(@NonNull int pid) {
        this.pid = pid;
    }

    @NonNull
    public String getIc_path() {
        return ic_path;
    }

    public void setIc_path(@NonNull String ic_path) {
        this.ic_path = ic_path;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitle_full_path() {
        return title_full_path;
    }

    public void setTitle_full_path(@NonNull String title_full_path) {
        this.title_full_path = title_full_path;
    }

    @NonNull
    public int getCount_items() {
        return count_items;
    }

    public void setCount_items(@NonNull int count_items) {
        this.count_items = count_items;
    }

    @NonNull
    public int getSubs() {
        return subs;
    }

    public void setSubs(@NonNull int subs) {
        this.subs = subs;
    }

    @NonNull
    public int getLevel() {
        return level;
    }

    public void setLevel(@NonNull int level) {
        this.level = level;
    }

    @NonNull
    public String getTitle_two() {
        return title_two;
    }

    public void setTitle_two(@NonNull String title_two) {
        this.title_two = title_two;
    }

    @NonNull
    public int getNumleft() {
        return numleft;
    }

    public void setNumleft(@NonNull int numleft) {
        this.numleft = numleft;
    }

    @NonNull
    public int getNumright() {
        return numright;
    }

    public void setNumright(@NonNull int numright) {
        this.numright = numright;
    }
}