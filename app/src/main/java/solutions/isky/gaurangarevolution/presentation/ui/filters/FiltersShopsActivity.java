package solutions.isky.gaurangarevolution.presentation.ui.filters;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.data.models.ItemShopCategoryList;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterShopSingleton;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.ui.category.CategoryActivity;
import solutions.isky.gaurangarevolution.presentation.ui.category.CategoryShopActivity;
import solutions.isky.gaurangarevolution.presentation.ui.locations.LocationsActivity;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class FiltersShopsActivity extends MvpAppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etSearch)
    MyCustomEditText mSearchText;
    @BindView(R.id.ibClear)
    ImageView clear_loc;
    @BindView(R.id.ibClearCateg)
    ImageView ibClearCateg;
    @BindView(R.id.loc_name)
    MyCustomEditText et_nameLoc;

    final static int REQUEST_CITY_ID = 1118;
    @BindView(R.id.etCateg)
    MyCustomEditText etCateg;
    final static int REQUEST_CATEGORY_ID = 1117;
    final static int REQUEST_CATEGORY_SHOP_ID = 1115;
    Realm realm;
    @BindView(R.id.btn_result)
    RelativeLayout btn_rezult;

    private String mTextSearch;
    private String mCategTitle;
    private int mCityId = 0;
    private String mCityNAme = "";
    private int mCategId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters_shops);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        realm = Realm.getDefaultInstance();
        etCateg.setOnClickListener(this);
        et_nameLoc.setOnClickListener(this);
        clear_loc.setOnClickListener(this);
        ibClearCateg.setOnClickListener(this);
        btn_rezult.setOnClickListener(this);
        et_nameLoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_nameLoc.getText().toString().trim().length() > 0) {
                    clear_loc.setVisibility(View.VISIBLE);

                } else {
                    clear_loc.setVisibility(View.GONE);

                }
            }
        });
        etCateg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etCateg.getText().toString().trim().length() > 0) {
                    ibClearCateg.setVisibility(View.VISIBLE);
                } else {
                    ibClearCateg.setVisibility(View.GONE);
                }
            }
        });

        mCityId = ParamFilterShopSingleton.getInstance().getmCityId();
        mCityNAme = ParamFilterShopSingleton.getInstance().getmCityTitle();
        et_nameLoc.setText(mCityNAme);
        mSearchText.setText(ParamFilterShopSingleton.getInstance().getSearchText());
        mCategId=ParamFilterShopSingleton.getInstance().getMcategId();
        if (ParamFilterShopSingleton.getInstance().getMcategId() > 0) {
            try {
                ItemCategoryList itemCategoryList = realm.where(ItemCategoryList.class).equalTo("id", mCategId).findFirst();
                etCateg.setText(itemCategoryList.getTitle());
            } catch (Exception e) {

            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int i = menuItem.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onClick(View v) {
        KeyboardUtil.hideKeyboard(this);
        int id = v.getId();
        if (id == et_nameLoc.getId()) {
            startActivityForResult(new Intent(FiltersShopsActivity.this, LocationsActivity.class), REQUEST_CITY_ID);
        } else if (id == clear_loc.getId()) {
            mCityId = 0;
            mCityNAme = "";
            et_nameLoc.setText(mCityNAme);
        } else if (id == etCateg.getId()) {
            if(AppState.getInstance(this).getInteger(AppState.Key.SHOPS_CATEGORIES_ENABLED) > 0){
                Intent categoryActivity = new Intent(FiltersShopsActivity.this, CategoryShopActivity.class);
                if (mCategId > 0) {
                    categoryActivity.putExtra("categId", mCategId);
                }
                startActivityForResult(categoryActivity, REQUEST_CATEGORY_SHOP_ID);
            }else{
                Intent categoryActivity = new Intent(FiltersShopsActivity.this, CategoryActivity.class);
                if (mCategId > 0) {
                    categoryActivity.putExtra("categId", mCategId);
                }
                startActivityForResult(categoryActivity, REQUEST_CATEGORY_ID);
            }

        }else if(id==ibClearCateg.getId()){
            mCategId = 0;
            mCategTitle = getString(R.string.select_category);
            etCateg.setText("");
            ibClearCateg.setVisibility(View.GONE);
        }else if (id == btn_rezult.getId()) {
            setResult(RESULT_OK);
            ParamFilterShopSingleton.getInstance().setMcategId(mCategId);
            ParamFilterShopSingleton.getInstance().setCategTitle(mCategTitle);
            mTextSearch = mSearchText.getText().toString().trim();
            ParamFilterShopSingleton.getInstance().setSearchText(mTextSearch);
            ParamFilterShopSingleton.getInstance().setmCityTitle(mCityNAme);
            ParamFilterShopSingleton.getInstance().setmCityId(mCityId);
            finish();
        }
    }
    @Override
    protected void onDestroy() {
        if(realm!=null){
            realm.close();
        }
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "resultCode= " + resultCode + ", requestCode= " + requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CATEGORY_ID:

                    if (data.hasExtra("id_cat")) {
                        mCategId = data.getIntExtra("id_cat", 0);
                        mCategTitle = data.getStringExtra("cat_title");
                        Log.d("mCategId", mCategId + "");
                        try {
                            if (mCategId == 1) {
                                etCateg.setText(getString(R.string.all_categ));
                            } else {
                                ItemCategoryList itemCategoryList = realm.where(ItemCategoryList.class).equalTo("id", mCategId).findFirst();
                                etCateg.setText(itemCategoryList.getTitle());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CATEGORY_SHOP_ID:

                    if (data.hasExtra("id_cat")) {
                        mCategId = data.getIntExtra("id_cat", 0);
                        mCategTitle = data.getStringExtra("cat_title");
                        Log.d("mCategId", mCategId + "");
                        try {
                            if (mCategId == 1) {
                                etCateg.setText(getString(R.string.all_categ));
                            } else {
                                ItemShopCategoryList itemCategoryList = realm.where(ItemShopCategoryList.class).equalTo("id", mCategId).findFirst();
                                etCateg.setText(itemCategoryList.getTitle());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CITY_ID:
                    if (data.hasExtra("id_city") && data.hasExtra("city_name")) {
                        try {
                            mCityId = data.getIntExtra("id_city", 0);
                            mCityNAme = data.getStringExtra("city_name");
                            Log.d("mCityId", mCityId + "");
                            et_nameLoc.setText(mCityNAme);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        mCityId = 0;
                        mCityNAme = "";
                    }
                    break;

            }
        }

    }
}
