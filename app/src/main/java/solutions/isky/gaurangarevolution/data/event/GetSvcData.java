package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.SvcModel;

public class GetSvcData extends CapObject {

    @SerializedName("svc_list")
    @NonNull
    List<SvcModel> svcModelList;

    @NonNull
    public List<SvcModel> getSvcModelList() {
        return svcModelList;
    }

    public void setSvcModelList(@NonNull List<SvcModel> svcModelList) {
        this.svcModelList = svcModelList;
    }
}
