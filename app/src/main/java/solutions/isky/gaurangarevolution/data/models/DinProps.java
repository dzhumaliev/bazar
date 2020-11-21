package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DinProps implements Serializable

{
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("cat_id")
    @NonNull
    private int cat_id;
    @SerializedName("t")
    @NonNull
    private String title;
    @SerializedName("d")
    @NonNull
    private String unit;
    @SerializedName("type")
    @NonNull
    private int type;
    @SerializedName("field")         //тип параметра
    @NonNull
    private int field;
    @SerializedName("def")
    @NonNull
    private String def;
    @SerializedName("req")
    @NonNull
    private int req;                 //обязательный параметр или нет
    @SerializedName("sea")
    @NonNull
    private int sea;                 //учавствует в поиске для фильтра
    @SerializedName("val")
    @NonNull
    private String val;
    @SerializedName("extra")
    private Extraopt extra;
    @SerializedName("list")
    @NonNull
    private List<ListOpt> listOpt;


    @NonNull
    public String getDef() {
        return def;
    }

    public void setDef(@NonNull String def) {
        this.def = def;
    }

    @NonNull
    public int getReq() {
        return req;
    }

    public void setReq(@NonNull int req) {
        this.req = req;
    }

    @NonNull
    public int getSea() {
        return sea;
    }

    public void setSea(@NonNull int sea) {
        this.sea = sea;
    }

    @NonNull
    public String getVal() {
        return val;
    }

    public void setVal(@NonNull String val) {
        this.val = val;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(@NonNull int cat_id) {
        this.cat_id = cat_id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }

    @NonNull
    public int getType() {
        return type;
    }

    public void setType(@NonNull int type) {
        this.type = type;
    }

    @NonNull
    public int getField() {
        return field;
    }

    public void setField(@NonNull int field) {
        this.field = field;
    }

    public Extraopt getExtra() {
        return extra;
    }

    public void setExtra(Extraopt extra) {
        this.extra = extra;
    }

    @NonNull
    public List<ListOpt> getListOpt() {
        return listOpt;
    }

    public void setListOpt(@NonNull List<ListOpt> listOpt) {
        this.listOpt = listOpt;
    }
}
