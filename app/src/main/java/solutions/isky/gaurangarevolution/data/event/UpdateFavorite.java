package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import solutions.isky.gaurangarevolution.data.models.CapObject;

public class UpdateFavorite extends CapObject {
    @NonNull
    @SerializedName("items_fav")
    private int items_fav;


    @NonNull
    public int getItems_fav() {
        return items_fav;
    }

    public void setItems_fav(@NonNull int items_fav) {
        this.items_fav = items_fav;
    }
}