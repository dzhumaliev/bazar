package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import solutions.isky.gaurangarevolution.data.models.AdItemMy;
import solutions.isky.gaurangarevolution.data.models.CapObject;

public class GetMyPoster extends CapObject implements Serializable {
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
        private ArrayList<AdItemMy> produktList = new ArrayList<AdItemMy>();
        @SerializedName("my_counters")
        MyCounters myCounters;
        @SerializedName("limitsPayed")
//        @NonNull
//        private boolean limitsPayed;


//        @NonNull
//        public boolean getLimitsPayed() {
//            return limitsPayed;
//        }
//
//        public void setLimitsPayed(@NonNull boolean limitsPayed) {
//            this.limitsPayed = limitsPayed;
//        }

        @NonNull
        public ArrayList<AdItemMy> getProduktList() {
            return produktList;
        }

        public MyCounters getMyCounters() {
            return myCounters;
        }

        public void setMyCounters(MyCounters myCounters) {
            this.myCounters = myCounters;
        }

        public void setProduktList(@NonNull ArrayList<AdItemMy> produktList) {
            this.produktList = produktList;

        }
    }
    public class MyCounters implements Serializable{
        @NonNull
        @SerializedName("active")
        private String active;
        @NonNull
        @SerializedName("not_active")
        private String not_active;
        @NonNull
        @SerializedName("shop_active")
        private String shop_active;
        @NonNull
        @SerializedName("shop_not_active")
        private String shop_not_active;

        @NonNull
        public String getShop_active() {
            return shop_active;
        }

        public void setShop_active(@NonNull String shop_active) {
            this.shop_active = shop_active;
        }

        @NonNull
        public String getShop_not_active() {
            return shop_not_active;
        }

        public void setShop_not_active(@NonNull String shop_not_active) {
            this.shop_not_active = shop_not_active;
        }

        @NonNull
        public String getActive() {
            return active;
        }

        public void setActive(@NonNull String active) {
            this.active = active;
        }

        @NonNull
        public String getNot_active() {
            return not_active;
        }

        public void setNot_active(@NonNull String not_active) {
            this.not_active = not_active;
        }
    }
}
