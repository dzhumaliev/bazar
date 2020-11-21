package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailsSocial implements Serializable {
    @NonNull
    @SerializedName("email")
    private String email;
    @NonNull
    @SerializedName("profile_id")
    private String profile_id;
    @NonNull
    @SerializedName("provider_id")
    private String provider_id;
    @NonNull
    @SerializedName("token")
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
