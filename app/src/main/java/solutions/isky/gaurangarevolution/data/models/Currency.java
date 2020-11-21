package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Currency implements Serializable {

    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("rate")
    @NonNull
    private String rate;
    @SerializedName("title")
    @NonNull
    private String title;
    @SerializedName("title_short")
    @NonNull
    private String titleShort;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getRate() {
        return rate;
    }

    public void setRate(@NonNull String rate) {
        this.rate = rate;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(@NonNull String titleShort) {
        this.titleShort = titleShort;
    }
}
