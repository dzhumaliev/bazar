package solutions.isky.gaurangarevolution.presentation.ui.filters;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.IListActivityFilterView;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.ListActivityAllFilterPresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.info_ad.InfoAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.LoginActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.utils.LayoutManagerType;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class ListActivityAllFilter extends MvpAppCompatActivity implements IListActivityFilterView {

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
  private FastItemAdapter<AdItem> fastItemAdapter;
  private ItemAdapter footerAdapter;
  @BindView(R.id.editText)
  MyCustomEditText search;
  LayoutManagerType mCurrentLayoutManagerType;
  Handler handler;
  private boolean isLastPage = false;
  private boolean isLoading = false;
  private LinearLayoutManager linearLayoutManager;
  private static final String KEY_LAYOUT_MANAGER = "layoutManager";
  final static int REQUEST_CATEGORY_ID = 1111;
  List<AdItem> mAdInfos = new ArrayList<>();
  @InjectPresenter
  ListActivityAllFilterPresenter listActivityAllFilterPresenter;
  final static int REQUEST_ACTIVITY_FILTER = 1117;
  final static int REQUEST_CITY_ID = 1118;
  final static int REQUEST_INFO_AD = 1279;
  @BindView(R.id.view_no_ads)
  RelativeLayout view_no_ads;
  @BindView(R.id.btn_reload)
  ImageView btn_reload;
  @BindView(R.id.view_no_internet)
  View view_no_internet;
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
    mAdInfos = new ArrayList<>();
    new MaterializeBuilder().withActivity(this).build();
    fastItemAdapter = new FastItemAdapter<>();
    fastItemAdapter.withSelectable(true);
    footerAdapter = items();
    fastItemAdapter.addAdapter(1, footerAdapter);
    linearLayoutManager = new LinearLayoutManager(this);
    ParamFilterPostSingleton.getInstance().setmCurrentLayoutManagerType(
        (AppState.getInstance(this).getInteger(AppState.Key.CURRENTLAYOUTTYPE) == 0)
            ? LayoutManagerType.GRID_LAYOUT_MANAGER : LayoutManagerType.LINEAR_LAYOUT_MANAGER);
    mCurrentLayoutManagerType = ParamFilterPostSingleton.getInstance()
        .getmCurrentLayoutManagerType();
    if (savedInstanceState != null) {
      mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
          .getSerializable(KEY_LAYOUT_MANAGER);
    }
    setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
    fastItemAdapter.withSavedInstanceState(savedInstanceState);

    swipeRefreshLayout.setOnRefreshListener(() -> {
      isLoading = true;
      listActivityAllFilterPresenter.setPAGE(0);
      fastItemAdapter = new FastItemAdapter<>();
      fastItemAdapter.withSelectable(true);
      footerAdapter = items();
      fastItemAdapter.addAdapter(1, footerAdapter);
      initAdapter();
      mRecyclerView.setAdapter(fastItemAdapter);
      getList(false);
    });
    btn_back.setOnClickListener(view -> onBackPressed());
    btn_filter.setOnClickListener(view -> startActivityForResult(
        new Intent(ListActivityAllFilter.this, FiltersActivity.class), REQUEST_ACTIVITY_FILTER));

    initParams();
    search.setText(listActivityAllFilterPresenter.getmTextSearch());

    search.setOnEditorActionListener((textView, actionId, keyEvent) -> {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        if (search.getText().toString().trim().length() > 1) {
          //search.setText("");
          listActivityAllFilterPresenter.setPAGE(0);
          KeyboardUtil.hideKeyboard(ListActivityAllFilter.this);
          ParamFilterPostSingleton.getInstance().setSearchText(search.getText().toString().trim());
          initParams();
          getList(true);
        } else {
          Toast.makeText(ListActivityAllFilter.this, R.string.search_more_words, Toast.LENGTH_SHORT)
              .show();
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
          listActivityAllFilterPresenter.setPAGE(0);
          ParamFilterPostSingleton.getInstance().setSearchText(search.getText().toString().trim());
          initParams();
          getList(true);
          initParams();
        }
      }
    });
    btn_reload.setOnClickListener(v -> {
      if (AppUtils.isConnected()) {
        view_no_internet.setVisibility(View.GONE);
        recreate();
      }
    });
  }

  private void initAdapter() {
    fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
      Intent infoAd = new Intent(ListActivityAllFilter.this, InfoAdActivity.class);
      infoAd.putExtra(InfoAdActivity.ID_ITEM, item.getId());
      infoAd.putExtra(InfoAdActivity.POSITION_ITEM, position);
      infoAd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivityForResult(infoAd, REQUEST_INFO_AD);
      return false;
    });

    fastItemAdapter.withEventHook(new ClickEventHook<AdItem>() {
      @Override
      public void onClick(View v, int position, FastAdapter<AdItem> fastAdapter, AdItem item) {
        if (AppState.getInstance(ListActivityAllFilter.this).getBoolean(AppState.Key.LOGGED_IN)) {
          JsonObject jsonObject = JsonObjBody.update_fav(
              UserData.getInstance(ListActivityAllFilter.this).getString(UserData.Key.USER_SESSID),
              item.getId(), item.getFav() == 1);
          listActivityAllFilterPresenter.update_fav_list(jsonObject, item.getFav() == 0);
          item.withStarred(!item.mStarred);
          fastAdapter.notifyAdapterDataSetChanged();
          //we animate the heart

        } else {
          startActivity(new Intent(ListActivityAllFilter.this, LoginActivity.class)
              .putExtra("title", getString(R.string.sign_in_to_submit_an_fav)));
        }
      }

      @Nullable
      @Override
      public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof AdItem.ViewHolder) {
          return ((AdItem.ViewHolder) viewHolder).imageViewStarFav;
        }
        return super.onBind(viewHolder);
      }

    });
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
            && totalItemCount >= listActivityAllFilterPresenter.getPAGE_SIZE()) {
          footerAdapter.clear();
          footerAdapter.add(new ProgressItem().withEnabled(false));
          isLoading = true;
          listActivityAllFilterPresenter.setPAGE(listActivityAllFilterPresenter.getPAGE() + 1);
          getList(false);
        }
      }
    }
  };


  public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
    int scrollPosition = 0;

    if (mRecyclerView.getLayoutManager() != null) {
      int pos;
      if (mRecyclerView.getLayoutManager() != null) {
        scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
            .findFirstCompletelyVisibleItemPosition();
      }
    }

    switch (layoutManagerType) {
      case GRID_LAYOUT_MANAGER:
        linearLayoutManager = new GridLayoutManager(this, 2);
        mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        break;
      case LINEAR_LAYOUT_MANAGER:
        linearLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        //scrollPosition=scrollPosition+2;
        break;
      default:
        linearLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
    }

    if (fastItemAdapter.getAdapterItems() != null && fastItemAdapter.getAdapterItems().size() > 0) {
      for (AdItem info : fastItemAdapter.getAdapterItems()) {
        info.setIs_grid(mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER);
        fastItemAdapter.notifyAdapterDataSetChanged();
      }
      fastItemAdapter.notifyItemRangeRemoved(0, fastItemAdapter.getItemCount());
      fastItemAdapter.notifyItemRangeChanged(0, fastItemAdapter.getItemCount());
      fastItemAdapter.notifyDataSetChanged();

      initAdapter();


    } else if (fastItemAdapter.getAdapterItems() != null
        && fastItemAdapter.getAdapterItems().size() == 0 && fastItemAdapter != null) {
      fastItemAdapter.clear();
      fastItemAdapter.notifyItemRangeRemoved(0, fastItemAdapter.getItemCount());
      fastItemAdapter.notifyItemRangeChanged(0, fastItemAdapter.getItemCount());
      fastItemAdapter.notifyDataSetChanged();
      fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
        Intent infoAd = new Intent(ListActivityAllFilter.this, InfoAdActivity.class);
        infoAd.putExtra(InfoAdActivity.ID_ITEM, item.getId());
        infoAd.putExtra(InfoAdActivity.POSITION_ITEM, position);
        startActivityForResult(infoAd, REQUEST_INFO_AD);
        return false;
      });

      initAdapter();
    }
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setAdapter(fastItemAdapter);
    mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    mRecyclerView.scrollToPosition(scrollPosition);

  }

  @Override
  public void listAds(List<AdItem> new_adInfo) {
    if (new_adInfo.size() < listActivityAllFilterPresenter.getPAGE_SIZE()) {
      isLastPage = true;
    } else {
      isLastPage = false;
    }
    for (AdItem adItem : new_adInfo) {
      adItem.setIs_grid(mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER);

    }
    if (listActivityAllFilterPresenter.getPAGE() == 0) {
      isLastPage = false;
      fastItemAdapter.setNewList(new_adInfo);
      mRecyclerView.setAdapter(fastItemAdapter);
    } else {
      fastItemAdapter.add(new_adInfo);
    }
    if (new_adInfo.size() == 0 && listActivityAllFilterPresenter.getPAGE() == 0) {
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
    }, 100);
  }

  @Override
  public void hideProgres() {
    isLoading = false;
    handler.postDelayed(() -> {
      if (swipeRefreshLayout.isRefreshing()) {
        swipeRefreshLayout.setRefreshing(false);

      }
      footerAdapter.clear();
    }, 100);
  }

  @Override
  public void showNoNetworkLayout() {
    view_no_internet.setVisibility(View.VISIBLE);
  }


  void initParams() {
    listActivityAllFilterPresenter.setmSort(ParamFilterPostSingleton.getInstance().getSort());
    listActivityAllFilterPresenter
        .setmCategId(ParamFilterPostSingleton.getInstance().getMcategId());
    listActivityAllFilterPresenter
        .setmCategTitle(ParamFilterPostSingleton.getInstance().getCategTitle());
    listActivityAllFilterPresenter
        .setmCityname(ParamFilterPostSingleton.getInstance().getmCityTitle());
    listActivityAllFilterPresenter
        .setmTextSearch(ParamFilterPostSingleton.getInstance().getSearchText());
    listActivityAllFilterPresenter.setmCityID(ParamFilterPostSingleton.getInstance().getmCityId());
    listActivityAllFilterPresenter.setmOwner(ParamFilterPostSingleton.getInstance().getmOwner());
    listActivityAllFilterPresenter
        .setmIs_photo(ParamFilterPostSingleton.getInstance().getIs_photo());
    listActivityAllFilterPresenter.setmP_less(ParamFilterPostSingleton.getInstance().getP_less());
    listActivityAllFilterPresenter.setmP_more(ParamFilterPostSingleton.getInstance().getP_more());
    listActivityAllFilterPresenter.setmProps(new JsonArray());
    if (ParamFilterPostSingleton.getInstance().getmProps_value().size() > 0) {
      for (ItemFilterModel o : new ArrayList<>(
          ParamFilterPostSingleton.getInstance().getmProps_value().values())) {
        listActivityAllFilterPresenter.getmProps().add((JsonElement) o.getObject());
      }
    }
    if (!search.getText().toString().trim()
        .equalsIgnoreCase(listActivityAllFilterPresenter.getmTextSearch())) {
      search.setText(listActivityAllFilterPresenter.getmTextSearch());
    }
    count_filters.setVisibility(ParamFilterPostSingleton.getInstance().getCountFilters()>0?View.VISIBLE:View.GONE);
    count_filters.setText(String.valueOf(ParamFilterPostSingleton.getInstance().getCountFilters()));

  }

  void getList(boolean isProgress) {
    listActivityAllFilterPresenter.getAdsList(isProgress);
  }

  private void setViewType() {
    handler.post(() -> {
      fastItemAdapter = new FastItemAdapter<>();
      fastItemAdapter.withSelectable(true);
      footerAdapter = items();
      fastItemAdapter.addAdapter(1, footerAdapter);
      if (mCurrentLayoutManagerType == LayoutManagerType.LINEAR_LAYOUT_MANAGER) {
        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
      } else {
        setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
      }
      ParamFilterPostSingleton.getInstance()
          .setmCurrentLayoutManagerType(mCurrentLayoutManagerType);

    });
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
          listActivityAllFilterPresenter.setPAGE(0);
          ParamFilterPostSingleton.getInstance().setmCurrentLayoutManagerType(
              (AppState.getInstance(this).getInteger(AppState.Key.CURRENTLAYOUTTYPE) == 0)
                  ? LayoutManagerType.GRID_LAYOUT_MANAGER
                  : LayoutManagerType.LINEAR_LAYOUT_MANAGER);
          mCurrentLayoutManagerType = ParamFilterPostSingleton.getInstance()
              .getmCurrentLayoutManagerType();
          setViewType();
          getList(true);
          break;
        case REQUEST_INFO_AD:
          if (data.hasExtra(InfoAdActivity.POSITION_ITEM) && data
              .hasExtra(InfoAdActivity.STARRED)) {
            try {
              fastItemAdapter.getAdapterItem(data.getIntExtra(InfoAdActivity.POSITION_ITEM, 0))
                  .withStarred(data.getBooleanExtra(InfoAdActivity.STARRED, false));
              fastItemAdapter
                  .notifyAdapterItemChanged(data.getIntExtra(InfoAdActivity.POSITION_ITEM, 0));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          break;
      }
    }

  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    //add the values which need to be saved from the adapter to the bundel
    outState = fastItemAdapter.saveInstanceState(outState);
    outState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
    super.onSaveInstanceState(outState);
  }


  @Override
  public void showError(String error) {
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
  }


  @Override
  public void setLastPage(boolean is_last) {
    isLastPage = is_last;
  }

  @Override
  public void no_session(String text) {
    startActivity(new Intent(this, LoginActivity.class));
  }
}
