package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.data.models.ItemShopCategoryList;

public class GetShopCategoryList extends CapObject {

  @SerializedName("shops_categories_list")
  @NonNull
  private List<ItemShopCategoryList> itemShopCategoryLists = new ArrayList<>();

  @NonNull
  public List<ItemShopCategoryList> getItemShopCategoryLists() {
    return itemShopCategoryLists;
  }

  public void setItemShopCategoryLists(
      @NonNull List<ItemShopCategoryList> itemShopCategoryLists) {
    this.itemShopCategoryLists = itemShopCategoryLists;
  }
}
