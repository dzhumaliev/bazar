package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.ItemLocation;

public class GetListLocation extends CapObject implements Serializable {
    @SerializedName("region_list")
    @NonNull
    private RegionList region_list;
    @NonNull
    public RegionList getRegion_list() {
        return region_list;
    }

    public void setRegion_list(@NonNull RegionList region_list) {
        this.region_list = region_list;
    }

    public class RegionList {
        @SerializedName("rows")
        @NonNull
        private List<ItemLocation> rows = new ArrayList<ItemLocation>();

        @NonNull
        public List<ItemLocation> getRows() {
            return rows;
        }

        public void setRows(@NonNull List<ItemLocation> rows) {
            this.rows = rows;
        }
    }
}