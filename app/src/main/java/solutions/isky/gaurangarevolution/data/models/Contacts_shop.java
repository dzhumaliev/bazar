package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contacts_shop implements Serializable {

    @SerializedName("skype")
    @NonNull
    private String skype_shop;
    @SerializedName("icq")
    @NonNull
    private String icq_shop;

    @NonNull
    public String getSkype_shop() {
        return skype_shop;
    }

    public void setSkype_shop(@NonNull String skype_shop) {
        this.skype_shop = skype_shop;
    }

    @NonNull
    public String getIcq_shop() {
        return icq_shop;
    }

    public void setIcq_shop(@NonNull String icq_shop) {
        this.icq_shop = icq_shop;
    }
}
