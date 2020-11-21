package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import solutions.isky.gaurangarevolution.data.models.CapObject;

public class GetCountPosters extends CapObject implements Serializable {

    @NonNull
    @SerializedName("items_list")
    private ItemListP items_list;

    @NonNull
    public ItemListP getItems_list() {
        return items_list;
    }

    public void setItems_list(@NonNull ItemListP items_list) {
        this.items_list = items_list;
    }

    public class ItemListP implements Serializable {
        @NonNull
        @SerializedName("count_items")
        private String count_items;

        @NonNull
        public String getCount_items() {
            return count_items;
        }

        public void setCount_items(@NonNull String count_items) {
            this.count_items = count_items;
        }
    }

}