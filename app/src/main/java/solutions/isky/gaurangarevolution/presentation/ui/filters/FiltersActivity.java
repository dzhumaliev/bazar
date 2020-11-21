package solutions.isky.gaurangarevolution.presentation.ui.filters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.data.models.Extraopt;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.databases.GroupSort;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.FilterActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.IPriceInterval;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.IfiltersView;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.ui.category.CategoryActivity;
import solutions.isky.gaurangarevolution.presentation.ui.locations.LocationsActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.DinParamsLayout;
import solutions.isky.gaurangarevolution.presentation.view.DinParamsTypePrice;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class FiltersActivity extends MvpAppCompatActivity implements View.OnClickListener,IfiltersView, IPriceInterval, DinParamsLayout.DinParamsLayoutListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etSearch)
    MyCustomEditText mSearchText;
    @BindView(R.id.ibClear)
    ImageView clear_loc;
    @BindView(R.id.ibClearCateg)
    ImageView ibClearCateg;
    @BindView(R.id.checkbox)
    CheckBox is_photo;
    @BindView(R.id.groupPrivateBusiness)
    RadioGroup groupPrivateBusiness;
    @BindView(R.id.rbAllFirm)
    RadioButton rbAllFirm;
    @BindView(R.id.rbPrivate)
    RadioButton rbPrivate;
    @BindView(R.id.rbBusiness)
    RadioButton rbBusiness;
    @BindView(R.id.groupSort)
    RadioGroup radioGroupSort;
    @BindView(R.id.rbSortNews)
    RadioButton rbsortnews;
    @BindView(R.id.rbSortCheap)
    RadioButton rbsortcheap;
    @BindView(R.id.rbSortExpensive)
    RadioButton rbsortexpensive;
    @BindView(R.id.loc_name)
    MyCustomEditText et_nameLoc;
    @BindView(R.id.groupViews)
    RadioGroup groupViews;
    @BindView(R.id.rbList)
    RadioButton rbList;
    @BindView(R.id.rbTable)
    RadioButton rbTable;

    final static int REQUEST_CITY_ID = 1118;
    @BindView(R.id.etCateg)
    MyCustomEditText etCateg;
    final static int REQUEST_CATEGORY_ID = 1117;
    private int mCityId = 0;
    private String mCityNAme = "";
    private int is_image = 0;
    private String mSort;
    private int mOwner;
    private int mCategId;
    Realm realm;
    private HashMap<Integer, ItemFilterModel> mProps_value;
    private int p_less=0;
    private int p_more=0;
    private String mTextSearch;
    private String mCategTitle;
    JsonArray mProps;
    /////////////////////////////////
    @BindView(R.id.tvQuantityResult)
    TextView quantityResult;
    @BindView(R.id.btn_result)
    RelativeLayout btn_rezult;
    @InjectPresenter
    FilterActivityPresenter filterActivityPresenter;

    @BindView(R.id.ll_params)
    DinParamsLayout dinParamsLayout;
    @BindView(R.id.price_param)
    DinParamsTypePrice paramsTypePrice;
    @BindView(R.id.progressBarFilters)
    ProgressBar progressBar;
    int mView_type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        mProps_value = new HashMap<>();
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
                    getCountPosters();
                } else {
                    clear_loc.setVisibility(View.GONE);
                    getCountPosters();
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
        is_photo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                is_image = 1;
                getCountPosters();
            } else {
                is_image = 0;
                getCountPosters();
            }
        });
        groupViews.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbList:
                    mView_type =1;
                    break;
                case R.id.rbTable:
                    mView_type = 0;
                    break;
                default:
                    break;
            }
        });
        initPAram();
        mSearchText.setText(mTextSearch);
        getCountPosters();
        if (mCategId > 0) {
            try {
                ItemCategoryList itemCategoryList = realm.where(ItemCategoryList.class).equalTo("id", mCategId).findFirst();
                etCateg.setText(itemCategoryList.getTitle());
            } catch (Exception e) {

            }
        }
        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTextSearch = editable.toString();
                getCountPosters();
            }
        });
        groupPrivateBusiness.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case -1:
                    break;
                case R.id.rbPrivate:
                    mOwner = 1;
                    getCountPosters();
                    break;
                case R.id.rbBusiness:
                    mOwner = 2;
                    getCountPosters();
                    break;
                case R.id.rbAllFirm:
                    mOwner = 0;
                    getCountPosters();
                    break;

                default:
                    break;
            }
        });
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
    void initPAram() {
        mSort = ParamFilterPostSingleton.getInstance().getSort();
        mCategId = ParamFilterPostSingleton.getInstance().getMcategId();
        mCategTitle = ParamFilterPostSingleton.getInstance().getCategTitle();
        mTextSearch = ParamFilterPostSingleton.getInstance().getSearchText();
        mCityId = ParamFilterPostSingleton.getInstance().getmCityId();
        mCityNAme = ParamFilterPostSingleton.getInstance().getmCityTitle();
        et_nameLoc.setText(mCityNAme);
        mOwner = ParamFilterPostSingleton.getInstance().getmOwner();
        if (mCategId > 0) {
            getDinPropses(mCategId, true);
        }
        initDinParams();
        initPrivateparam();
        initSortparam();
        initViewListGrid();
    }

    void initDinParams() {
        mProps = new JsonArray();
        mProps_value = ParamFilterPostSingleton.getInstance().getmProps_value();
        is_image = ParamFilterPostSingleton.getInstance().getIs_photo();

        if (mProps_value.size() > 0) {
            for (ItemFilterModel o : new ArrayList<>(mProps_value.values())) {
                mProps.add((JsonElement) o.getObject());
            }

        }
        if (is_image > 0) {
            is_photo.setChecked(true);
        }
    }

    void initIntermediateDinProps(HashMap<Integer, ItemFilterModel> props_value, int id_prop) {
        mProps = new JsonArray();
        if (mProps_value.containsKey(id_prop)) {
            mProps_value.remove(id_prop);
        }
        if (props_value.size() > 0) {
            mProps_value.put(id_prop, props_value.get(id_prop));
        }

        for (ItemFilterModel o : new ArrayList<>(mProps_value.values())) {
            mProps.add((JsonElement) o.getObject());
        }


    }

    void initSortparam() {
        String sort = ParamFilterPostSingleton.getInstance().getSort();
        if (sort.equalsIgnoreCase(GroupSort.newest)) {
            rbsortnews.setChecked(true);
        } else if (sort.equalsIgnoreCase(GroupSort.cheapest)) {
            rbsortcheap.setChecked(true);
        } else if (sort.equalsIgnoreCase(GroupSort.expensive)) {
            rbsortexpensive.setChecked(true);
        }
    }

    void initPrivateparam() {
        int owner = ParamFilterPostSingleton.getInstance().getmOwner();
        if (owner == 1) {
            rbPrivate.setChecked(true);
        } else if (owner == 2) {
            rbBusiness.setChecked(true);
        } else {
            rbAllFirm.setChecked(true);
        }
    }
    void initViewListGrid() {
        mView_type = AppState.getInstance(App.getInstance()).getInteger(AppState.Key.CURRENTLAYOUTTYPE);
        if (mView_type == 0) {
            rbTable.setChecked(true);
        } else if (mView_type == 1) {
            rbList.setChecked(true);
        }
    }
    void setSortParam() {
        if (rbsortnews.isChecked()) {
            ParamFilterPostSingleton.getInstance().setSort(GroupSort.newest);
        } else if (rbsortcheap.isChecked()) {
            ParamFilterPostSingleton.getInstance().setSort(GroupSort.cheapest);
        } else if (rbsortexpensive.isChecked()) {
            ParamFilterPostSingleton.getInstance().setSort(GroupSort.expensive);
        }
    }
    private void getDinPropses(int id, boolean is_nec) {
        JsonObject object = JsonObjBody.getPropsDop(id, AppUtils.getLocale(this));
        filterActivityPresenter.getDinParams(object, is_nec);
    }
    private void getCountPosters() {
        JsonObject object = JsonObjBody.getList(0, 20, mSort, mCategId, mTextSearch, mCityId, mOwner, mProps, is_image,p_less,p_more, AppUtils.getLocale(this));
        filterActivityPresenter.getPostersCount(object);
    }

    @Override
    public void onClick(View v) {
        KeyboardUtil.hideKeyboard(this);
        int id = v.getId();
        if (id == et_nameLoc.getId()) {
            startActivityForResult(new Intent(FiltersActivity.this, LocationsActivity.class), REQUEST_CITY_ID);
        } else if (id == clear_loc.getId()) {
            mCityId = 0;
            ParamFilterPostSingleton.getInstance().setmCityId(mCityId);
            mCityNAme = "";
            ParamFilterPostSingleton.getInstance().setmCityTitle(mCityNAme);
            et_nameLoc.setText(mCityNAme);
        } else if (id == etCateg.getId()) {
            Intent categoryActivity = new Intent(FiltersActivity.this, CategoryActivity.class);
            if (mCategId > 0) {
                categoryActivity.putExtra("categId", mCategId);
            }
            startActivityForResult(categoryActivity, REQUEST_CATEGORY_ID);
        }else if(id==ibClearCateg.getId()){
            mCategId = 0;
            ParamFilterPostSingleton.getInstance().setMcategId(mCategId);
            mCategTitle = getString(R.string.select_category);
            ParamFilterPostSingleton.getInstance().setCategTitle("");
            etCateg.setText("");
            mProps_value.clear();
            dinParamsLayout.setVisibility(View.GONE);
            paramsTypePrice.setVisibility(View.GONE);
            ibClearCateg.setVisibility(View.GONE);
            ParamFilterPostSingleton.getInstance().setP_more(0);
            ParamFilterPostSingleton.getInstance().setP_less(0);
            getCountPosters();
        }else if (id == btn_rezult.getId()) {
            setResult(RESULT_OK);
            setSortParam();
            ParamFilterPostSingleton.getInstance().setmOwner(mOwner);
            ParamFilterPostSingleton.getInstance().setMcategId(mCategId);
            ParamFilterPostSingleton.getInstance().setCategTitle(mCategTitle);
            mTextSearch = mSearchText.getText().toString().trim();
            ParamFilterPostSingleton.getInstance().setSearchText(mTextSearch);
            ParamFilterPostSingleton.getInstance().setmProps_value(mProps_value);
            ParamFilterPostSingleton.getInstance().setmCityTitle(mCityNAme);
            ParamFilterPostSingleton.getInstance().setP_less(p_less);
            ParamFilterPostSingleton.getInstance().setP_more(p_more);
            ParamFilterPostSingleton.getInstance().setIs_photo(is_photo.isChecked() ? 1 : 0);
            AppState.getInstance(this).setInteger(AppState.Key.CURRENTLAYOUTTYPE,mView_type);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "resultCode= " + resultCode + ", requestCode= " + requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CATEGORY_ID:
                   dinParamsLayout.removeAllViews();
                   dinParamsLayout.setVisibility(View.GONE);
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

                            mProps_value.clear();
                            mProps = new JsonArray();
                            getCountPosters();
                            getDinPropses(mCategId, false);
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
                            ParamFilterPostSingleton.getInstance().setmCityId(mCityId);
                            Log.d("mCityId", mCityId + "");
                            et_nameLoc.setText(mCityNAme);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        mCityId = 0;
                        mCityNAme = "";
                        ParamFilterPostSingleton.getInstance().setmCityId(mCityId);
                    }
                    break;

            }
        }

    }


    @Override
    public void count_posters(@NonNull String s) {
        quantityResult.setText(s);
    }

    @Override
    protected void onDestroy() {
        if(realm!=null){
            realm.close();
        }
        super.onDestroy();
    }

    @Override
    public void getDinProps(List<DinProps> dinPropses, boolean necessary_to_fill) {
        Log.d("getDinProps", dinPropses.size() + "");
        setDinParamsView(dinPropses, necessary_to_fill);
    }
    private void setDinParamsView(List<DinProps> dinPropses, boolean necessary_to_fill) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        paramsTypePrice.setVisibility(View.VISIBLE);
        DinProps prop_price = new DinProps();
        prop_price.setTitle(getString(R.string.price));
        prop_price.setExtra(new Extraopt(1));
        paramsTypePrice.setData(this, prop_price, fragmentManager, this);

        dinParamsLayout.setUpViews(this, dinPropses, fragmentManager, this, necessary_to_fill);
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

    @Override
    public void getPrice(int p_more, int p_less, String text) {
        this.p_more=p_more;
        this.p_less=p_less;
        getCountPosters();
    }

    @Override
    public void onFinishParamsEdit(HashMap<Integer, ItemFilterModel> props_value, int id_prop) {
        initIntermediateDinProps(props_value, id_prop);
        getCountPosters();
    }
}
