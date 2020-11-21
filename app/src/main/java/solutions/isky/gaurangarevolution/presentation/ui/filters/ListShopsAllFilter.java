package solutions.isky.gaurangarevolution.presentation.ui.filters;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterShopSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.ListShopsAllFilterPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.main.IShopView;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.adsuser.AllAdsUserActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.LoginActivity;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.MyAdActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class ListShopsAllFilter extends MvpAppCompatActivity implements IShopView {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.swiprefresh)
  SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.recyclerViewListAds)
  RecyclerView mRecyclerView;
  @BindView(R.id.btn_back)
  ImageButton btn_back;
  @BindView(R.id.button_filters)
  ImageView btn_filter;
  private FastItemAdapter<ShopData> fastItemAdapter;
  private ItemAdapter footerAdapter;
  @BindView(R.id.editText)
  MyCustomEditText search;
  Handler handler;
  private boolean isLastPage = false;
  private boolean isLoading = false;
  private LinearLayoutManager linearLayoutManager;
  @InjectPresenter
  ListShopsAllFilterPresenter shopsAllFilterPresenter;
  @BindView(R.id.view_no_shops)
  RelativeLayout view_no_ads;
  final static int REQUEST_ACTIVITY_FILTER = 1117;
  private int PAGE_SIZE = 20;
  private int PAGE = 0;
  private int delayet_progress = 200;
  @BindView(R.id.count_filters)
  TextView count_filters;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_all_filter);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeButtonEnabled(false);
    handler = new Handler();
    new MaterializeBuilder().withActivity(this).build();
    fastItemAdapter = new FastItemAdapter<>();
    fastItemAdapter.withSelectable(true);
    footerAdapter = items();
    fastItemAdapter.addAdapter(1, footerAdapter);
    linearLayoutManager = new GridLayoutManager(this, 2);
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setAdapter(fastItemAdapter);
    mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    fastItemAdapter.withSavedInstanceState(savedInstanceState);
    fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
      User user = new Gson()
          .fromJson(UserData.getInstance(this).getString(UserData.Key.USER_DATA_JSON), User.class);
      if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN) && user != null
          && item.getId() == user.getShop_id()) {

        startActivity(new Intent(this, MyAdActivity.class).putExtra("shop_id", true));
      } else {
        startActivity(new Intent(this, AllAdsUserActivity.class).putExtra("shop_id", item.getId())
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

      }
      return false;
    });
    btn_back.setOnClickListener(view -> onBackPressed());
    btn_filter.setOnClickListener(
        view -> startActivityForResult(new Intent(this, FiltersShopsActivity.class),
            REQUEST_ACTIVITY_FILTER));

    search.setText(ParamFilterShopSingleton.getInstance().getSearchText());

    search.setOnEditorActionListener((textView, actionId, keyEvent) -> {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        if (search.getText().toString().trim().length() > 1) {
          //search.setText("");
          PAGE = 0;
          KeyboardUtil.hideKeyboard(ListShopsAllFilter.this);
          ParamFilterShopSingleton.getInstance().setSearchText(search.getText().toString().trim());

          JsonObject mParams = JsonObjBody
              .getShops(PAGE, PAGE_SIZE, ParamFilterShopSingleton.getInstance().getMcategId(),
                  ParamFilterShopSingleton.getInstance().getSearchText(),
                  ParamFilterShopSingleton.getInstance().getmCityId(), AppUtils.getLocale(this));
          shopsAllFilterPresenter.getShops(mParams, true);
          initParams();
        } else {
          Toast.makeText(ListShopsAllFilter.this, R.string.search_more_words_shop,
              Toast.LENGTH_SHORT).show();
        }

      }
      return false;
    });
    search.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (search.getText().toString().trim().length() > 0) {

        } else {
          PAGE = 0;
          ParamFilterShopSingleton.getInstance().setSearchText(search.getText().toString().trim());

          JsonObject mParams = JsonObjBody
              .getShops(PAGE, PAGE_SIZE, ParamFilterShopSingleton.getInstance().getMcategId(),
                  ParamFilterShopSingleton.getInstance().getSearchText(),
                  ParamFilterShopSingleton.getInstance().getmCityId(),
                  AppUtils.getLocale(ListShopsAllFilter.this));
          shopsAllFilterPresenter.getShops(mParams, true);
          initParams();
        }
      }
    });

    isLoading = true;
    JsonObject mParams = JsonObjBody
        .getShops(PAGE, PAGE_SIZE, ParamFilterShopSingleton.getInstance().getMcategId(),
            ParamFilterShopSingleton.getInstance().getSearchText(),
            ParamFilterShopSingleton.getInstance().getmCityId(), AppUtils.getLocale(this));
    shopsAllFilterPresenter.getShops(mParams, true);
    swipeRefreshLayout.setOnRefreshListener(() -> {
      PAGE = 0;
      shopsAllFilterPresenter.getShops(JsonObjBody
              .getShops(PAGE, PAGE_SIZE, ParamFilterShopSingleton.getInstance().getMcategId(),
                  ParamFilterShopSingleton.getInstance().getSearchText(),
                  ParamFilterShopSingleton.getInstance().getmCityId(), AppUtils.getLocale(this)),
          false);
      isLoading = true;

    });
    initParams();
  }

  void initParams() {
    count_filters.setVisibility(
        ParamFilterShopSingleton.getInstance().getCountFilters() > 0 ? View.VISIBLE : View.GONE);
    count_filters.setText(String.valueOf(ParamFilterShopSingleton.getInstance().getCountFilters()));
  }

  private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
      int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
      int totalItemCount = recyclerView.getLayoutManager().getItemCount();
      int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
      if (!isLoading && !isLastPage) {
        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
            && firstVisibleItemPosition >= 0
            && totalItemCount >= PAGE_SIZE) {
          footerAdapter.clear();
          footerAdapter.add(new ProgressItem().withEnabled(false));
          isLoading = true;
          PAGE = PAGE + 1;
          shopsAllFilterPresenter.getShops(JsonObjBody
              .getShops(PAGE, PAGE_SIZE, ParamFilterShopSingleton.getInstance().getMcategId(),
                  ParamFilterShopSingleton.getInstance().getSearchText(),
                  ParamFilterShopSingleton.getInstance().getmCityId(),
                  AppUtils.getLocale(ListShopsAllFilter.this)), false);
        }
      }
    }
  };

  @Override
  public void listAds(List<ShopData> shopData) {
    if (shopData.size() < PAGE_SIZE) {
      isLastPage = true;
    } else {
      isLastPage = false;
    }
    if (PAGE == 0) {
      isLastPage = false;
      fastItemAdapter.clear();
    }
    fastItemAdapter.add(shopData);
    if (shopData.size() == 0 && PAGE == 0) {

      view_no_ads.setVisibility(View.VISIBLE);
    } else {
      view_no_ads.setVisibility(View.GONE);
    }
  }

  @Override
  public void showProgres() {
    handler.postDelayed(() -> {
      swipeRefreshLayout.setRefreshing(true);
      isLoading = true;
    }, delayet_progress);

  }

  @Override
  public void hideProgres() {
    isLoading = false;
    handler.postDelayed(() -> {
      if (swipeRefreshLayout.isRefreshing()) {
        swipeRefreshLayout.setRefreshing(false);
      }
      footerAdapter.clear();
    }, delayet_progress);
  }

  @Override
  public void showNoNetworkLayout() {

  }

  @Override
  public void sendError(String s) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void no_session() {
    startActivity(new Intent(this, LoginActivity.class));
    finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.d("onActivityResult", "resultCode= " + resultCode + ", requestCode= " + requestCode);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case REQUEST_ACTIVITY_FILTER:
          initParams();
          isLoading = true;
          PAGE = 0;
          shopsAllFilterPresenter.getShops(JsonObjBody
              .getShops(PAGE, PAGE_SIZE, ParamFilterShopSingleton.getInstance().getMcategId(),
                  ParamFilterShopSingleton.getInstance().getSearchText(),
                  ParamFilterShopSingleton.getInstance().getmCityId(),
                  AppUtils.getLocale(ListShopsAllFilter.this)), true);
          search.setText(ParamFilterShopSingleton.getInstance().getSearchText());
          initParams();
          break;

      }
    }

  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    //add the values which need to be saved from the adapter to the bundel
    outState = fastItemAdapter.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }
}
