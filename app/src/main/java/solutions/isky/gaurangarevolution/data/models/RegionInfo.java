package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegionInfo implements Serializable {
    @SerializedName("id")
    @NonNull
    private int city_id;
    @SerializedName("pid")
    @NonNull
    private int region;
    @SerializedName("c")
    @NonNull
    private int cantry;
    @SerializedName("t")
    @NonNull
    private String  city_title;
    @SerializedName("yc")
    @NonNull
    private String yc;

    @NonNull
    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(@NonNull int city_id) {
        this.city_id = city_id;
    }

    @NonNull
    public int getRegion() {
        return region;
    }

    public void setRegion(@NonNull int region) {
        this.region = region;
    }

    @NonNull
    public int getCantry() {
        return cantry;
    }

    public void setCantry(@NonNull int cantry) {
        this.cantry = cantry;
    }

    @NonNull
    public String getCity_title() {
        return city_title;
    }

    public void setCity_title(@NonNull String city_title) {
        this.city_title = city_title;
    }

    @NonNull
    public String getYc() {
        return yc;
    }

    public void setYc(@NonNull String yc) {
        this.yc = yc;
    }
}
