package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;

public class CountryCode extends RealmObject {
  @NonNull
  @SerializedName("title")
  private String title;
  @NonNull
  @SerializedName("code")
  private String code;
  @NonNull
  @SerializedName("country_code")
  private String country_code;


  @NonNull
  public String getCountry_code() {
    return country_code;
  }

  public void setCountry_code(@NonNull String country_code) {
    this.country_code = country_code;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  @NonNull
  public String getCode() {
    return code;
  }

  public void setCode(@NonNull String code) {
    this.code = code;
  }
}
