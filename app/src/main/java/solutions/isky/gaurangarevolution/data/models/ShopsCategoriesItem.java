package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ShopsCategoriesItem implements Serializable {
  @NonNull
  @SerializedName("id")
  private int id;
  @NonNull
  @SerializedName("pid")
  private int pid;
  @NonNull
  @SerializedName("enabled")
  private int enabled;
  @NonNull
  @SerializedName("numlevel")
  private int numlevel;
  @NonNull
  @SerializedName("node")
  private int node;
  @NonNull
  @SerializedName("title")
  private String title;
  @NonNull
  @SerializedName("shops")
  private int shops;
  @NonNull
  @SerializedName("img")
  private String img;

  @NonNull
  public String getImg() {
    return img;
  }

  public ShopsCategoriesItem() {
  }

  public ShopsCategoriesItem(@NonNull ItemShopCategoryList list) {
    this.id = list.getId();
    this.pid = list.getPid();
    this.enabled = list.getEnabled();
    this.numlevel = list.getNumlevel();
    this.node = list.getNode();
    this.title = list.getTitle();
    this.shops = list.getShops();
    this.img = list.getImg();
  }

  public ShopsCategoriesItem(@NonNull CategoryShop categoryShop) {
    this.id = categoryShop.getId();
    this.pid = categoryShop.getPid();
    this.title = categoryShop.getTitle();
  }

  public void setImg(@NonNull String img) {
    this.img = img;
  }

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
  public int getEnabled() {
    return enabled;
  }

  public void setEnabled(@NonNull int enabled) {
    this.enabled = enabled;
  }

  @NonNull
  public int getNumlevel() {
    return numlevel;
  }

  public void setNumlevel(@NonNull int numlevel) {
    this.numlevel = numlevel;
  }

  @NonNull
  public int getNode() {
    return node;
  }

  public void setNode(@NonNull int node) {
    this.node = node;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  @NonNull
  public int getShops() {
    return shops;
  }

  public void setShops(@NonNull int shops) {
    this.shops = shops;
  }
}
