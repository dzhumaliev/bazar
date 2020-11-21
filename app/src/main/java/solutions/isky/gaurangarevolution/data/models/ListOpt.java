package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListOpt implements Serializable {
    @SerializedName("n")
    @NonNull
    private String tite;
    @SerializedName("v")
    @NonNull
    private int value;

    @NonNull
    public String getTite() {
        return tite;
    }

    public void setTite(@NonNull String tite) {
        this.tite = tite;
    }

    @NonNull
    public int getValue() {
        return value;
    }

    public void setValue(@NonNull int value) {
        this.value = value;
    }
}
