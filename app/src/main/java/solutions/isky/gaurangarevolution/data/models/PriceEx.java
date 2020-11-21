package solutions.isky.gaurangarevolution.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;

/**
 * Created by sergey on 21.03.18.
 */

public class PriceEx implements Serializable {
    @NonNull
    @SerializedName("mod")
    private int mod;
    @NonNull
    @SerializedName("exchange")
    private int exchange;
    @NonNull
    @SerializedName("free")
    private int free;
    @NonNull
    @SerializedName("agreed")
    private int agreed;

    public int getMod() {
        return mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public int getExchange() {
        return exchange;
    }

    public void setExchange(int exchange) {
        this.exchange = exchange;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getAgreed() {
        return agreed;
    }

    public void setAgreed(int agreed) {
        this.agreed = agreed;
    }
}
