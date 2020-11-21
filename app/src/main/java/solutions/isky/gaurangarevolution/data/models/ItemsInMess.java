package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemsInMess implements Serializable {

  @SerializedName("id")
  @NonNull
  private int id;
  @SerializedName("img_s")
  @NonNull
  private String img_ad;
  @SerializedName("title")
  @NonNull
  private String title;
  @SerializedName("price")
  @NonNull
  private String price;

  @NonNull
  public String getPrice() {
    return price;
  }

  public void setPrice(@NonNull String price) {
    this.price = price;
  }

  @NonNull
  public int getId() {
    return id;
  }

  public void setId(@NonNull int id) {
    this.id = id;
  }

  @NonNull
  public String getImg_ad() {
    return img_ad;
  }

  public void setImg_ad(@NonNull String img_ad) {
    this.img_ad = img_ad;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }
}
