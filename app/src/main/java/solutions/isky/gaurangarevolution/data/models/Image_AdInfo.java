package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image_AdInfo implements Serializable {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("path")
    @NonNull
    private String path;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }
}