package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class UserInMess implements Serializable {

  @SerializedName("id")
  @NonNull
  private int id;
  @SerializedName("name")
  @NonNull
  private String name;
  @SerializedName("avatar")
  @NonNull
  private String avatar;
  @SerializedName("is_shop")
  @NonNull
  private int is_shop;

  @NonNull
  public int getId() {
    return id;
  }

  public void setId(@NonNull int id) {
    this.id = id;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(@NonNull String avatar) {
    this.avatar = avatar;
  }

  @NonNull
  public int getIs_shop() {
    return is_shop;
  }

  public void setIs_shop(@NonNull int is_shop) {
    this.is_shop = is_shop;
  }
}
