package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sergey on 21.03.18.
 */

public class CapObject implements Serializable {

    @NonNull
    @SerializedName("RESULT")
    private int result;
    @NonNull
    @SerializedName("VERSION")
    private String version;
    @NonNull
    @SerializedName("details")
    private DetailsSocial detailsSocial;
    @NonNull
    @SerializedName("MESSAGE")
    private List<MessageError> message;

    public CapObject() {
    }

    @NonNull
    public List<MessageError> getMessage() {
        return message;
    }

    public void setMessage(@NonNull List<MessageError> message) {
        this.message = message;
    }

    @NonNull
    public int getResult() {
        return result;
    }

    public void setResult(@NonNull int result) {
        this.result = result;
    }

    @NonNull
    public String getVersion() {
        return version;
    }

    public void setVersion(@NonNull String version) {
        this.version = version;
    }

    @NonNull
    public DetailsSocial getDetailsSocial() {
        return detailsSocial;
    }

    public void setDetailsSocial(@NonNull DetailsSocial detailsSocial) {
        this.detailsSocial = detailsSocial;
    }
}