package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SvcAbonement implements Serializable {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("title")
    @NonNull
    private String title;
    @SerializedName("expire")
    @NonNull
    private String expire;
    @SerializedName("termless")
    @NonNull
    private int termless;
    @SerializedName("items_total")
    @NonNull
    private String items_total;
    @SerializedName("items_available")
    @NonNull
    private String items_available;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getExpire() {
        return expire;
    }

    public void setExpire(@NonNull String expire) {
        this.expire = expire;
    }

    @NonNull
    public int getTermless() {
        return termless;
    }

    public void setTermless(@NonNull int termless) {
        this.termless = termless;
    }

    @NonNull
    public String getItems_total() {
        return items_total;
    }

    public void setItems_total(@NonNull String items_total) {
        this.items_total = items_total;
    }

    @NonNull
    public String getItems_available() {
        return items_available;
    }

    public void setItems_available(@NonNull String items_available) {
        this.items_available = items_available;
    }
}
