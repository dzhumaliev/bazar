package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.User;

public class GetBindSocial extends CapObject {
    @NonNull
    @SerializedName("user_info")
    private User user;

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }
}
