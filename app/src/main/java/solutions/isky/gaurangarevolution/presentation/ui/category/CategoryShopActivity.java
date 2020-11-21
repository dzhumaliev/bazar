package solutions.isky.gaurangarevolution.presentation.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import io.realm.Realm;
import java.util.List;
import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.ItemShopCategoryList;
import solutions.isky.gaurangarevolution.presentation.databases.RealmHelper;
import solutions.isky.gaurangarevolution.presentation.mvp.category.CategoryShopActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.category.CategoryShopAdapter;
import solutions.isky.gaurangarevolution.presentation.mvp.category.ICategShopDop;
import solutions.isky.gaurangarevolution.presentation.mvp.category.IShopCategoryView;
import solutions.isky.gaurangarevolution.presentation.view.CategoryShopDopView;

public class CategoryShopActivity extends MvpAppCompatActivity implements IShopCategoryView,
    ICategShopDop {
  @BindView(R.id.listView)
  MultiLevelListView mListViewCategory;
  CategoryShopAdapter categoryShopAdapter;
  Realm realm;
  @InjectPresenter
  CategoryShopActivityPresenter categoryShopActivityPresenter;
  @BindView(R.id.progressBar)
  ProgressBar progressBar;
  @BindView(R.id.dopCategContent)
  CategoryShopDopView categoryDopView;
  @BindView(R.id.btn_cacel)
  Button btn_cancel;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  private boolean is_add_dd = false;
  int mCategId = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_shop);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    realm = Realm.getDefaultInstance();
    if (getIntent().hasExtra("categId")) {
      mCategId = getIntent().getIntExtra("categId", 0);
    }
    is_add_dd = getIntent().getBooleanExtra("add_ad", false);
    btn_cancel.setOnClickListener(view -> onBackPressed());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (realm != null) {
      realm.close();
    }
  }

  @Override
  public void showProgres() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideProgres() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void showNoNetworkLayout() {
    Toast.makeText(this, getString(R.string.no_internet_connectoin), Toast.LENGTH_SHORT).show();
  }

  private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
    @Override
    public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
      Intent intent = new Intent();
      intent.putExtra("id_cat", ((ItemShopCategoryList) item).getId());
      intent.putExtra("cat_title", ((ItemShopCategoryList) item).getTitle());
      setResult(RESULT_OK, intent);
      finish();
    }

    @Override
    public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
      categoryDopView.addDopView(CategoryShopActivity.this, (ItemShopCategoryList) item, ((ItemShopCategoryList) item).getNumlevel(), CategoryShopActivity.this);

      categoryShopAdapter.setDataItems(new RealmHelper().getRootShopArticlesAdAdd(realm, (ItemShopCategoryList) item, CategoryShopActivity.this,is_add_dd));

    }
  };

  @Override
  public void listCategory(List<ItemShopCategoryList> itemShopCategoryLists) {
    new RealmHelper().addShopsCategorys(itemShopCategoryLists, realm);
    categoryShopAdapter = new CategoryShopAdapter(this, realm, mCategId, is_add_dd);
    mListViewCategory.setAdapter(categoryShopAdapter);
    categoryShopAdapter.setDataItems(new RealmHelper().getRootShopArticlesAdAdd(realm, new ItemShopCategoryList(1,0,1,1,1, getString(R.string.all_categ),1,""),CategoryShopActivity.this, is_add_dd));
    categoryDopView.addDopView(CategoryShopActivity.this, new ItemShopCategoryList(1,0,1,0,0, getString(R.string.select_category), 1,""),0, CategoryShopActivity.this);

    mListViewCategory.setOnItemClickListener(mOnItemClickListener);

      if (mCategId > 0) {
          try {
              ItemShopCategoryList itemCategoryList = realm.where(ItemShopCategoryList.class).equalTo("id", mCategId).findFirst();
              int level = itemCategoryList.getNumlevel();
              if (itemCategoryList.getShops() == 0) {
                  ItemShopCategoryList itemCategoryRoot = realm.where(ItemShopCategoryList.class).equalTo("id", itemCategoryList.getPid()).findFirst();
                  categoryShopAdapter.setDataItems(new RealmHelper().getRootShopArticlesAdAdd(realm, itemCategoryRoot, CategoryShopActivity.this, is_add_dd));

              } else {
                  ItemShopCategoryList itemCategoryRoot = realm.where(ItemShopCategoryList.class).equalTo("id", itemCategoryList.getId()).findFirst();
                  categoryShopAdapter.setDataItems(new RealmHelper().getRootShopArticlesAdAdd(realm, itemCategoryList, CategoryShopActivity.this, is_add_dd));
                  categoryDopView.addDopView(CategoryShopActivity.this, itemCategoryRoot, 1, CategoryShopActivity.this);
              }
              int id = itemCategoryList.getPid();
              while (level > 0 && id > 1) {
                  Log.d("id", id + "");
                  Log.d("level", level + "");
                  ItemShopCategoryList item = realm.where(ItemShopCategoryList.class).equalTo("id", id).findFirst();
                  categoryDopView.addDopView(CategoryShopActivity.this, item, 1, CategoryShopActivity.this);
                  id = item.getPid();
                  level--;
              }
          } catch (Exception e) {
              e.printStackTrace();
          }

      }
  }

  @Override
  public void onCklickView(ItemShopCategoryList categoryList) {

    deleteView(categoryList);
  }

  void deleteView(ItemShopCategoryList categItem) {
    categoryDopView.delView(categItem);
    categoryShopAdapter.setDataItems(new RealmHelper().getRootShopArticlesAdAdd(realm, categItem, CategoryShopActivity.this,is_add_dd));
  }
}
