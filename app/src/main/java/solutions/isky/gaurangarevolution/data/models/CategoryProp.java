package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Serzhik on 06.09.2017.
 */

public class CategoryProp implements Serializable {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("title")
    @NonNull
    private String title;
    @SerializedName("description")
    @NonNull
    private String unit;
    @SerializedName("type")
    @NonNull
    private String type;
    @SerializedName("v")
    @NonNull
    private String value;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }

    @NonNull
    public String getValue() {
        return (value!=null)?value:"";
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }
}