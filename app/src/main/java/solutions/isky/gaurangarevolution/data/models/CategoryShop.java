package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CategoryShop implements Serializable {
  @NonNull
  @SerializedName("id")
  private int id;
  @NonNull
  @SerializedName("pid")
  private int pid;
  @NonNull
  @SerializedName("title")
  private String title;



  @NonNull
  public int getId() {
    return id;
  }

  public void setId(@NonNull int id) {
    this.id = id;
  }

  @NonNull
  public int getPid() {
    return pid;
  }

  public void setPid(@NonNull int pid) {
    this.pid = pid;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }
}
