package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Search_rang implements Serializable {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("from")
    @NonNull
    private String from;
    @SerializedName("to")
    @NonNull
    private String to;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getFrom() {
        return from;
    }

    public void setFrom(@NonNull String from) {
        this.from = from;
    }

    @NonNull
    public String getTo() {
        return to;
    }

    public void setTo(@NonNull String to) {
        this.to = to;
    }
}