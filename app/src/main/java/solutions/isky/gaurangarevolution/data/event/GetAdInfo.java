package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdInfoItem;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.Currency;

public class GetAdInfo extends CapObject implements Serializable {
    @SerializedName("item_info")
    @NonNull
    private AdInfoItem adInfoItem;

    @SerializedName("currency_list")
    @NonNull
    private List<Currency> currencyList = new ArrayList<Currency>();

    @NonNull
    public AdInfoItem getAdInfoItem() {
        return adInfoItem;
    }

    public void setAdInfoItem(@NonNull AdInfoItem adInfoItem) {
        this.adInfoItem = adInfoItem;
    }

    @NonNull
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(@NonNull List<Currency> currencyList) {
        this.currencyList = currencyList;
    }
}
