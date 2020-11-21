package solutions.isky.gaurangarevolution.presentation.databases;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.data.models.ItemShopCategoryList;

public class RealmHelper {

  private final String TAG = getClass().getSimpleName();

  public void addCategorys(final List<ItemCategoryList> itemCategoryLists, Realm realm) {
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.delete(ItemCategoryList.class);
      }
    });
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

        realm.copyToRealm(itemCategoryLists);

      }
    });
    Log.i(TAG, "Category has been added to realm database");
  }

  public void addShopsCategorys(final List<ItemShopCategoryList> itemCategoryLists, Realm realm) {
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.delete(ItemShopCategoryList.class);
      }
    });
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

        realm.copyToRealm(itemCategoryLists);

      }
    });
    Log.i(TAG, "Category has been added to realm database");
  }

  public void addCategorys(final List<ItemCategoryList> itemCategoryLists) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.delete(ItemCategoryList.class);
      }
    });
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

        realm.copyToRealm(itemCategoryLists);

      }
    });
    if (realm != null) {
      realm.close();
    }
    Log.i(TAG, "Category has been added to realm database");
  }

  public List<ItemCategoryList> getSubCategory(Realm realm, int pid) {
    List<ItemCategoryList> itemCategoryLists = realm.where(ItemCategoryList.class)
        .equalTo("pid", pid).findAll();

    return itemCategoryLists;
  }
  public List<ItemShopCategoryList> getSubShopCategory(Realm realm, int pid) {
    List<ItemShopCategoryList> itemCategoryLists = realm.where(ItemShopCategoryList.class)
        .equalTo("pid", pid).findAll();

    return itemCategoryLists;
  }
  public List<ItemCategoryList> getRootArticlesAdAdd(Realm realm, ItemCategoryList categoryList,
      Context ctx, boolean is_add_ad) {
    RealmResults<ItemCategoryList> itemCategoryLists;
    if (!is_add_ad) {
      itemCategoryLists = realm.where(ItemCategoryList.class).beginGroup()
          .equalTo("pid", categoryList.getId()).notEqualTo("count_items", 0).endGroup().findAll();
    } else {
      itemCategoryLists = realm.where(ItemCategoryList.class).equalTo("pid", categoryList.getId())
          .findAll();
    }
    List<ItemCategoryList> result = new ArrayList<>();
    long sum_items = itemCategoryLists.sum("count_items").longValue();
    if (!is_add_ad) {
      if (categoryList.getPid() > 0) {
        result.add(0, new ItemCategoryList(categoryList.getId(),
            ctx.getString(R.string.all_in) + " " + categoryList.getTitle(), (int) sum_items, 0,
            categoryList.getLevel() + 1, categoryList.getPhotos_max_count(), categoryList.getAddr(),
            categoryList.getPrice(), categoryList.getPrice_sett()));
      } else {
        result.add(0, new ItemCategoryList(categoryList.getId(), ctx.getString(R.string.all_categ),
            (int) sum_items, 0, categoryList.getLevel(), categoryList.getPhotos_max_count(),
            categoryList.getAddr(), categoryList.getPrice(), categoryList.getPrice_sett()));
      }
    }
    result.addAll(itemCategoryLists);
    return result;
  }

  public List<ItemShopCategoryList> getRootShopArticlesAdAdd(Realm realm,
      ItemShopCategoryList categoryList, Context ctx, boolean is_add_ad) {
    RealmResults<ItemShopCategoryList> itemCategoryLists;

    if (!is_add_ad) {
      itemCategoryLists = realm.where(ItemShopCategoryList.class).beginGroup().equalTo("pid", categoryList.getId()).notEqualTo("shops", 0).endGroup().findAll();
    } else {
      itemCategoryLists = realm.where(ItemShopCategoryList.class).equalTo("pid", categoryList.getId())
              .findAll();
    }
    List<ItemShopCategoryList> result = new ArrayList<>();
    long sum_items = itemCategoryLists.sum("shops").longValue();
    if (!is_add_ad) {
      if (categoryList.getPid() >0) {
        result.add(0, new ItemShopCategoryList(categoryList.getId(),categoryList.getPid(),1,categoryList.getNumlevel()+1,0,
                ctx.getString(R.string.all_in) + " " + categoryList.getTitle(), (int) sum_items, ""));
      } else {
        result.add(0, new ItemShopCategoryList(categoryList.getId(),categoryList.getPid(),1,categoryList.getNumlevel()+1,0, ctx.getString(R.string.all_categ),
                (int) sum_items, ""));
      }
    }
    result.addAll(itemCategoryLists);
    return result;
  }

  public ItemCategoryList getItemCategoryList(Realm realm, int categId) {
    ItemCategoryList itemCategoryList = realm.where(ItemCategoryList.class).equalTo("id", categId)
        .findFirst();

    return itemCategoryList;
  }
  public ItemShopCategoryList getItemShopCategoryList(Realm realm, int categId) {
    ItemShopCategoryList itemCategoryList = realm.where(ItemShopCategoryList.class).equalTo("id", categId)
        .findFirst();

    return itemCategoryList;
  }
}
