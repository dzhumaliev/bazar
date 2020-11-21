package solutions.isky.gaurangarevolution.data.models;

import com.google.gson.annotations.SerializedName;

public class ShopDataEdit extends ShopDataCreate {
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ShopDataEdit(ShopDataCreate shopData, int id) {
        super(shopData);
        this.id = id;
    }

    public ShopDataEdit(int id) {
        this.id = id;
    }
}
