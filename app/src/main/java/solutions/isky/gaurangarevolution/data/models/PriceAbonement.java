package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PriceAbonement {
    @NonNull
    @SerializedName("pr")
    private String pr;
    @NonNull
    @SerializedName("ex")
    private String ex;
    @NonNull
    @SerializedName("m")
    private String m;
    @NonNull
    @SerializedName("period")
    private String period;

    @NonNull
    public String getPeriod() {
        return period;
    }

    public void setPeriod(@NonNull String period) {
        this.period = period;
    }

    @NonNull
    public String getPr() {
        return pr;
    }

    public void setPr(@NonNull String pr) {
        this.pr = pr;
    }

    @NonNull
    public String getEx() {
        return ex;
    }

    public void setEx(@NonNull String ex) {
        this.ex = ex;
    }

    @NonNull
    public String getM() {
        return m;
    }

    public void setM(@NonNull String m) {
        this.m = m;
    }
}
