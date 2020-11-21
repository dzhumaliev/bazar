package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.RegionInfo;

public class GetCityInfo extends CapObject implements Serializable {
    @SerializedName("region_info")
    @NonNull
    private RegionInfo regionInfo;

    @NonNull
    public RegionInfo getRegionInfo() {
        return regionInfo;
    }

    public void setRegionInfo(@NonNull RegionInfo regionInfo) {
        this.regionInfo = regionInfo;
    }
}
