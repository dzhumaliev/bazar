package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class PriceSetting extends RealmObject implements Serializable {
    @SerializedName("price_title")
    @NonNull
    private String price_title;
    @SerializedName("curr")
    @NonNull
    private int curr;
    @SerializedName("mod")
    @NonNull
    private int mod;
    @SerializedName("exchange")
    @NonNull
    private int exchange;
    @SerializedName("free")
    @NonNull
    private int free;
    @SerializedName("agreed")
    @NonNull
    private int agreed;
    @SerializedName("mod_title")
    @NonNull
    private String mod_title;


    @NonNull
    public String getPrice_title() {
        return price_title;
    }

    public void setPrice_title(@NonNull String price_title) {
        this.price_title = price_title;
    }

    @NonNull
    public int getCurr() {
        return curr;
    }

    public void setCurr(@NonNull int curr) {
        this.curr = curr;
    }

    @NonNull
    public int getMod() {
        return mod;
    }

    public void setMod(@NonNull int mod) {
        this.mod = mod;
    }

    @NonNull
    public int getExchange() {
        return exchange;
    }

    public void setExchange(@NonNull int exchange) {
        this.exchange = exchange;
    }

    @NonNull
    public int getFree() {
        return free;
    }

    public void setFree(@NonNull int free) {
        this.free = free;
    }

    @NonNull
    public int getAgreed() {
        return agreed;
    }

    public void setAgreed(@NonNull int agreed) {
        this.agreed = agreed;
    }

    @NonNull
    public String getMod_title() {
        return mod_title;
    }

    public void setMod_title(@NonNull String mod_title) {
        this.mod_title = mod_title;
    }
}