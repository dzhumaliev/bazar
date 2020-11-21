package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.User;

public class GetRegister extends CapObject implements Serializable {
    @NonNull
    @SerializedName("SESID")
    private String sesid;
    @NonNull
    @SerializedName("user_info")
    private User user_info;

    @NonNull
    public String getSesid() {
        return sesid;
    }

    public void setSesid(@NonNull String sesid) {
        this.sesid = sesid;
    }

    @NonNull
    public User getUser_info() {
        return user_info;
    }

    public void setUser_info(@NonNull User user_info) {
        this.user_info = user_info;
    }
}
