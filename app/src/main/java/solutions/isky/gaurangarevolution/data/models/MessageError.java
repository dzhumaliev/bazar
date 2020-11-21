package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sergey on 21.03.18.
 */

public class MessageError implements Serializable{
    @NonNull
    @SerializedName("msg")
    private String msg;

    @NonNull
    @SerializedName("dtime")
    private String dtime;

    @NonNull
    @SerializedName("left")
    private String left;

    @NonNull
    @SerializedName("reason")
    private String reason_text;
    @NonNull
    @SerializedName("min_length")
    private String min_length_pass;


    @NonNull
    public String getDtime() {
        return dtime;
    }

    @NonNull
    public String getLeft() {
        return left;
    }

    @NonNull
    public String getMsg() {
        return msg;
    }

    @NonNull
    public String getReason_text() {
        return reason_text;
    }

    public void setReason_text(@NonNull String reason_text) {
        this.reason_text = reason_text;
    }

    public void setMsg(@NonNull String msg) {
        this.msg = msg;
    }

    @NonNull
    public String getMin_length_pass() {
        return min_length_pass!=null?min_length_pass:"";
    }

    public void setMin_length_pass(@NonNull String min_length_pass) {
        this.min_length_pass = min_length_pass;
    }
}
