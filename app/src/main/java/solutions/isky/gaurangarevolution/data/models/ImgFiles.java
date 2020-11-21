package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImgFiles implements Serializable {
    @NonNull
    @SerializedName("orig")
    private String orig_name;
    @NonNull
    @SerializedName("new")
    private String new_name;

    @NonNull
    public String getOrig_name() {
        return orig_name;
    }

    public void setOrig_name(@NonNull String orig_name) {
        this.orig_name = orig_name;
    }

    @NonNull
    public String getNew_name() {
        return new_name;
    }

    public void setNew_name(@NonNull String new_name) {
        this.new_name = new_name;
    }

}
