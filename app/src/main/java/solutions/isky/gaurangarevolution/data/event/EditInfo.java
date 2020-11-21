package solutions.isky.gaurangarevolution.data.event;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;
import solutions.isky.gaurangarevolution.data.models.AdInfo;
import solutions.isky.gaurangarevolution.data.models.CapObject;

public class EditInfo extends CapObject implements Serializable {
    @SerializedName("item")
    @NonNull
    private AdInfo adInfo;

    public AdInfo getAdInfo() {
        return adInfo;
    }

    public void setAdInfo(AdInfo adInfo) {
        this.adInfo = adInfo;
    }
}
