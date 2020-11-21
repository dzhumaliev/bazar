package solutions.isky.gaurangarevolution.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EditShop implements Serializable {
    @SerializedName("SESID")
    private String SESID;
    @SerializedName("shop_edit")
    private ShopDataEdit shop_edit;

    public void setSESID(String SESID) {
        this.SESID = SESID;
    }

    public ShopDataEdit getShop_edit() {
        return shop_edit;
    }

    public void setShop_edit(ShopDataEdit shop_edit) {
        this.shop_edit = shop_edit;
    }

    public EditShop() {
    }

    public String getSESID() {
        return SESID;
    }



    public EditShop(String SESID, ShopDataEdit shop_edit) {
        this.SESID = SESID;
        this.shop_edit = shop_edit;
    }
}
