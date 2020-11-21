package solutions.isky.gaurangarevolution.presentation.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.data.models.PriceSetting;
import solutions.isky.gaurangarevolution.presentation.databases.RealmHelper;
import solutions.isky.gaurangarevolution.presentation.mvp.category.CategoryActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.category.CategoryAdapter;
import solutions.isky.gaurangarevolution.presentation.mvp.category.ICategDop;
import solutions.isky.gaurangarevolution.presentation.mvp.category.ICategoryView;
import solutions.isky.gaurangarevolution.presentation.view.CategoryDopView;

public class CategoryActivity extends MvpAppCompatActivity implements ICategoryView, ICategDop {
    @BindView(R.id.listView)
    MultiLevelListView mListViewCategory;
    CategoryAdapter categoryAdapter;
    Realm realm;
    @InjectPresenter
    CategoryActivityPresenter categoryActivityPresenter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.dopCategContent)
    CategoryDopView categoryDopView;
    @BindView(R.id.btn_cacel)
    Button btn_cancel;
    int mCategId = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean is_add_dd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
        if (getIntent().hasExtra("categId")) {
            mCategId = getIntent().getIntExtra("categId", 0);
        }
        is_add_dd = getIntent().getBooleanExtra("add_ad", false);
//        JsonObject object = new JsonObjBody().getListCateg();
//        getPresenter().getCategoryList(object);
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
    public void listCategory(List<ItemCategoryList> itemCategoryLists) {
        new RealmHelper().addCategorys(itemCategoryLists, realm);
        categoryAdapter = new CategoryAdapter(this, realm, mCategId, is_add_dd);
        mListViewCategory.setAdapter(categoryAdapter);

        categoryAdapter.setDataItems(new RealmHelper().getRootArticlesAdAdd(realm, new ItemCategoryList(1, getString(R.string.all_categ), 0, 1, 1, 8, 0, 0, new PriceSetting()), CategoryActivity.this, is_add_dd));
        categoryDopView.addDopView(CategoryActivity.this, new ItemCategoryList(1, getString(R.string.select_category), 0, 1, 0, 8, 0, 0, new PriceSetting()), 0, CategoryActivity.this);
        mListViewCategory.setOnItemClickListener(mOnItemClickListener);

        if (mCategId > 0) {
            try {
                ItemCategoryList itemCategoryList = realm.where(ItemCategoryList.class).equalTo("id", mCategId).findFirst();
                int level = itemCategoryList.getLevel();
                if (itemCategoryList.getSubs() == 0) {
                    ItemCategoryList itemCategoryRoot = realm.where(ItemCategoryList.class).equalTo("id", itemCategoryList.getPid()).findFirst();
                    categoryAdapter.setDataItems(new RealmHelper().getRootArticlesAdAdd(realm, itemCategoryRoot, CategoryActivity.this, is_add_dd));

                } else {
                    ItemCategoryList itemCategoryRoot = realm.where(ItemCategoryList.class).equalTo("id", itemCategoryList.getId()).findFirst();
                    categoryAdapter.setDataItems(new RealmHelper().getRootArticlesAdAdd(realm, itemCategoryList, CategoryActivity.this, is_add_dd));
                    categoryDopView.addDopView(CategoryActivity.this, itemCategoryRoot, 1, CategoryActivity.this);
                }
                int id = itemCategoryList.getPid();
                while (level > 0 && id > 1) {
                    Log.d("id", id + "");
                    Log.d("level", level + "");
                    ItemCategoryList item = realm.where(ItemCategoryList.class).equalTo("id", id).findFirst();
                    categoryDopView.addDopView(CategoryActivity.this, item, 1, CategoryActivity.this);
                    id = item.getPid();
                    level--;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            intent.putExtra("id_cat", ((ItemCategoryList) item).getId());
            intent.putExtra("cat_title", ((ItemCategoryList) item).getTitle());
            intent.putExtra("photos_max_count", ((ItemCategoryList) item).getPhotos_max_count());
            intent.putExtra("addr", ((ItemCategoryList) item).getAddr());
            setResult(RESULT_OK, intent);
            finish();
        }

        @Override
        public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {

            categoryDopView.addDopView(CategoryActivity.this, (ItemCategoryList) item, ((ItemCategoryList) item).getLevel(), CategoryActivity.this);

            categoryAdapter.setDataItems(new RealmHelper().getRootArticlesAdAdd(realm, (ItemCategoryList) item, CategoryActivity.this, is_add_dd));

        }
    };

    @Override
    public void onCklickView(ItemCategoryList categoryList) {
        deleteView(categoryList);
    }

    void deleteView(ItemCategoryList categItem) {
        categoryDopView.delView(categItem);
        categoryAdapter.setDataItems(new RealmHelper().getRootArticlesAdAdd(realm, categItem, CategoryActivity.this, is_add_dd));
    }
}
