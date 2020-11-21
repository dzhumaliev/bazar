package solutions.isky.gaurangarevolution.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PeriodItem implements Serializable {
    @SerializedName("a")
    private int active;
    @SerializedName("def")
    private int is_def;
    @SerializedName("days")
    private int days;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getIs_def() {
        return is_def;
    }

    public void setIs_def(int is_def) {
        this.is_def = is_def;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
