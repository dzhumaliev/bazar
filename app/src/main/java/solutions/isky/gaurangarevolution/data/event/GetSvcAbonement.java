package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.SvcAbonement;
import solutions.isky.gaurangarevolution.data.models.SvcAbonementItem;
import solutions.isky.gaurangarevolution.data.models.SvcModel;

public class GetSvcAbonement extends CapObject {

    @SerializedName("shop_svc_abonement_list")
    @NonNull
    List<SvcAbonementItem> svcAbonementItems;

    @NonNull
    public List<SvcAbonementItem> getSvcAbonementItems() {
        return svcAbonementItems;
    }

    public void setSvcAbonementItems(@NonNull List<SvcAbonementItem> svcAbonementItems) {
        this.svcAbonementItems = svcAbonementItems;
    }
}
