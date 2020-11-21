package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SocialShop implements Serializable {

    @SerializedName("t")
    @NonNull
    private int id_social;
    @SerializedName("v")
    @NonNull
    private String link_social;

    @NonNull
    public int getId_social() {
        return id_social;
    }

    public void setId_social(@NonNull int id_social) {
        this.id_social = id_social;
    }

    @NonNull
    public String getLink_social() {
        return link_social;
    }

    public void setLink_social(@NonNull String link_social) {
        this.link_social = link_social;
    }
}
