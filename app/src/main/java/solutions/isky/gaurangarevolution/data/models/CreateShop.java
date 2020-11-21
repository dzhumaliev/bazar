package solutions.isky.gaurangarevolution.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateShop implements Serializable {
    @SerializedName("SESID")
    private String SESID;
    @SerializedName("shop_open")
    private ShopDataCreate shop_open;

    public void setSESID(String SESID) {
        this.SESID = SESID;
    }

    public void setShop_open(ShopDataCreate shop_open) {
        this.shop_open = shop_open;
    }

    public CreateShop() {
    }

    public String getSESID() {
        return SESID;
    }

    public ShopDataCreate getShop_open() {
        return shop_open;
    }

    public CreateShop(String SESID, ShopDataCreate shop_open) {
        this.SESID = SESID;
        this.shop_open = shop_open;
    }
}
