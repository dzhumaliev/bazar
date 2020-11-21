package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.data.models.User;

/**
 * Created by sergey on 19.03.18.
 */

public class GetListPoster extends CapObject implements Serializable {
    @NonNull
    @SerializedName("items_list")
    private ItemList items_list;


    @NonNull
    public ItemList getItems_list() {
        return items_list;
    }

    public void setItems_list(@NonNull ItemList items_list) {
        this.items_list = items_list;
    }

    public class ItemList implements Serializable {
        @SerializedName("rows")
        @NonNull
        private ArrayList<AdItem> produktList = new ArrayList<AdItem>();

        @SerializedName("count_items")
        @NonNull
        String count_items;

        @SerializedName("user_data")
        @NonNull
        User user;
        @SerializedName("shop_data")
        @NonNull
        ShopData shopData;


        @NonNull
        public User getUser() {
            return user;
        }

        @NonNull
        public String getCount_items() {
            return count_items;
        }

        public void setUser(@NonNull User user) {
            this.user = user;
        }

        @NonNull
        public ShopData getShopData() {
            return shopData;
        }

        public void setShopData(@NonNull ShopData shopData) {
            this.shopData = shopData;
        }

        public void setCount_items(@NonNull String count_items) {
            this.count_items = count_items;
        }

        @NonNull
        public ArrayList<AdItem> getProduktList() {
            return produktList;
        }

        public void setProduktList(@NonNull ArrayList<AdItem> produktList) {
            this.produktList = produktList;
        }
    }
}