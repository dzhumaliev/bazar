package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.ShopData;

public class GetShopsList extends CapObject {

    @SerializedName("shops_list")
    @NonNull
    private ArrayList<ShopData> shopData = new ArrayList<>();

    @NonNull
    public ArrayList<ShopData> getShopData() {
        return shopData;
    }

    public void setShopData(@NonNull ArrayList<ShopData> shopData) {
        this.shopData = shopData;
    }
}
