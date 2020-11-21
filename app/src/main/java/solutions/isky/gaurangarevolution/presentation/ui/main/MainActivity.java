package solutions.isky.gaurangarevolution.presentation.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.custom_dialog.SweetAlertDialog;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterShopSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.main.IMainView;
import solutions.isky.gaurangarevolution.presentation.mvp.main.MainPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.main.MyDrawerLayout;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.addad.AddAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.correspondence.DialogsActivity;
import solutions.isky.gaurangarevolution.presentation.ui.favorite.MyFavActivity;
import solutions.isky.gaurangarevolution.presentation.ui.filters.FiltersActivity;
import solutions.isky.gaurangarevolution.presentation.ui.filters.FiltersShopsActivity;
import solutions.isky.gaurangarevolution.presentation.ui.filters.ListActivityAllFilter;
import solutions.isky.gaurangarevolution.presentation.ui.filters.ListShopsAllFilter;
import solutions.isky.gaurangarevolution.presentation.ui.info_ad.InfoAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.LoginActivity;
import solutions.isky.gaurangarevolution.presentation.ui.main.fragment.ShopListFragment;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.MyAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.MyProfile;
import solutions.isky.gaurangarevolution.presentation.ui.my_shop.NoShopActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.utils.LayoutManagerType;
import solutions.isky.gaurangarevolution.presentation.view.ButtonMenu;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class MainActivity extends MvpAppCompatActivity implements IMainView,
    SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.drawer_layout)
  MyDrawerLayout drawer;
  @BindView(R.id.list_view)
  ImageView list_view;
  @BindView(R.id.list_sort)
  Button list_sort;
  @BindView(R.id.button_filters)
  ImageView button_filters;
  @BindView(R.id.view_no_internet)
  View view_no_internet;
  @BindView(R.id.btn_reload)
  ImageView btn_reload;
  LinearLayoutManager linearLayoutManager;
  LayoutManagerType mCurrentLayoutManagerType;
  private static final String KEY_LAYOUT_MANAGER = "layoutManager";
  Handler handler;
  private int PAGE_SIZE = 20;
  private int PAGE = 0;
  private boolean isLastPage = false;
  private boolean isLoading = false;
  private FastItemAdapter<AdItem> fastItemAdapter;
  private ItemAdapter footerAdapter;
  @BindView(R.id.swiprefresh)
  SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.recyclerViewListAds)
  RecyclerView mRecyclerView;
  private static long back_pressed;
  private int delayet_progress = 100;
  @InjectPresenter
  MainPresenter mainPresenter;
  final static int REQUEST_ACTIVITY_FIRST = 1118;
  final static int REQUEST_ACTIVITY_FILTER = 1117;
  final static int REQUEST_INFO_AD = 1279;
  final static int REQUEST_ALL_FILTER = 1114;
  final static int REQUEST_MYPROFILE = 2112;
  final static int REQUEST_NEW_AD = 1233;
  @BindView(R.id.rl_no_register)
  RelativeLayout rl_no_register;
  @BindView(R.id.rl_register)
  RelativeLayout rl_register;
  @BindView(R.id.textViewName)
  TextView textViewName;
  @BindView(R.id.im_avatar)
  ImageView img_photo_user;
  @BindView(R.id.btn_menu)
  ImageButton btn_menu;

  @BindView(R.id.btn_profile)
  ButtonMenu btn_profile;
  @BindView(R.id.btn_shop)
  ButtonMenu btn_shop;
  @BindView(R.id.btn_favorite)
  ButtonMenu btn_favorite;
  @BindView(R.id.fav_count)
  TextView fav_count;
  @BindView(R.id.view_btn_fav)
  RelativeLayout view_btn_fav;
  @BindView(R.id.btn_message)
  ButtonMenu btn_message;
  @BindView(R.id.viewMess)
  RelativeLayout viewMess;
  @BindView(R.id.view_new_messages)
  RelativeLayout view_new_mess;
  @BindView(R.id.msg)
  TextView msg;
  @BindView(R.id.btn_logout)
  ButtonMenu btn_logout;
  @BindView(R.id.btn_login)
  Button btn_login;
  @BindView(R.id.editText)
  MyCustomEditText editText;
  @BindView(R.id.fab)
  FloatingActionButton btn_add_new_ad;
  @BindView(R.id.view_no_ads)
  RelativeLayout view_no_ads;
  @BindView(R.id.btn_shop_show)
  ButtonMenu btn_shop_show;
  @BindView(R.id.ll_container_shop)
  LinearLayout ll_container_shop;
  @BindView(R.id.buble_new)
  ImageView buble_new;
  ShopListFragment shopListFragment;

  @Override
  protected void onResume() {
    mainPresenter.setSideMenu();
    btn_add_new_ad.setEnabled(true);
    if (AppState.getInstance(this).getBoolean(AppState.Key.LOGGED_IN)) {
      mainPresenter.getUserInfo();
      mainPresenter.sendPushID();
    }
    super.onResume();
    registerReceiver(mMessageReceiver, new IntentFilter(Constants.NEW_MESSAGE));
    if (swipeRefreshLayout.isRefreshing()) {
      swipeRefreshLayout.setRefreshing(false);
      onRefresh();
    }
  }

  private void show_shop_list(boolean is_show) {
    findViewById(R.id.fragment_shop_content).setVisibility(is_show ? View.VISIBLE : View.GONE);
    init_btn_shop_add(is_show);
  }

  private void init_btn_shop_add(boolean is_show) {
    btn_shop_show.setTag(is_show);
    btn_shop_show
        .setText(is_show ? getString(R.string.go_to_add) : getString(R.string.go_to_shops));
    btn_shop_show.setCompoundDrawablesWithIntrinsicBounds(
        is_show ? R.drawable.ic_add_btn : R.drawable.ic_shop_btn, 0, 0, 0);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    show_shop_list(false);
    handler = new Handler();
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
    list_view.setOnClickListener(this);
    list_sort.setOnClickListener(this);
    button_filters.setOnClickListener(this);
    btn_reload.setOnClickListener(this);
    btn_menu.setOnClickListener(this);
    btn_shop_show.setOnClickListener(this);
    btn_shop.setVisibility(AppState.getInstance(this).getInteger(AppState.Key.SHOP_AVAILABLE)==1?View.VISIBLE:View.GONE);
    getlistAds(true);
    fastItemAdapter.withSavedInstanceState(savedInstanceState);
    if (AppState.getInstance(this).getBoolean(AppState.Key.FIRST_START)) {
      AppState.getInstance(this).setBoolean(AppState.Key.FIRST_START, true);
      if (!AppState.getInstance(this).getBoolean(AppState.Key.LOGGED_IN) && AppUtils
          .isConnected()) {
        startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_ACTIVITY_FIRST);
      }
    }
    btn_add_new_ad.setOnClickListener(v -> {
      btn_add_new_ad.setEnabled(false);
      if (AppState.getInstance(this).getBoolean(AppState.Key.LOGGED_IN)) {
        // GallerySingleton.getInstance().clearCheckedPhotos();
        startActivityForResult(new Intent(this, AddAdActivity.class), REQUEST_NEW_AD);
      } else {
        startActivity(new Intent(this, LoginActivity.class));
      }
    });
    swipeRefreshLayout.setOnRefreshListener(this);
    editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        if ((Boolean) btn_shop_show.getTag()) {
          if (editText.getText().toString().trim().length() > 2) {
            String text = editText.getText().toString().trim();
            ParamFilterShopSingleton.getInstance().setSearchText(text);
            editText.setText("");
            KeyboardUtil.hideKeyboard(MainActivity.this);
            startActivityFilterAllShop();
          } else {
            Toast.makeText(MainActivity.this, R.string.search_more_words_shop, Toast.LENGTH_SHORT)
                .show();
          }
        } else {
          if (editText.getText().toString().trim().length() > 1) {
            String text = editText.getText().toString().trim();
            ParamFilterPostSingleton.getInstance().setSearchText(text);
            editText.setText("");
            KeyboardUtil.hideKeyboard(MainActivity.this);
            startActivityFilterAll();
          } else {
            Toast.makeText(MainActivity.this, R.string.search_more_words, Toast.LENGTH_SHORT)
                .show();
          }
        }

      }
      return false;
    });
    ll_container_shop.setVisibility(
            AppState.getInstance(App.getInstance()).getInteger(AppState.Key.SHOP_AVAILABLE) > 0 ? View.VISIBLE
                    : View.GONE);

  }

  private void initAdapter() {
    fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
      Intent infoAd = new Intent(MainActivity.this, InfoAdActivity.class);
      infoAd.putExtra(InfoAdActivity.ID_ITEM, item.getId());
      infoAd.putExtra(InfoAdActivity.POSITION_ITEM, position);
      infoAd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivityForResult(infoAd, REQUEST_INFO_AD);
      return false;
    });

    fastItemAdapter.withEventHook(new ClickEventHook<AdItem>() {
      @Override
      public void onClick(View v, int position, FastAdapter<AdItem> fastAdapter, AdItem item) {
        if (AppState.getInstance(MainActivity.this).getBoolean(AppState.Key.LOGGED_IN)) {
          JsonObject jsonObject = JsonObjBody.update_fav(
              UserData.getInstance(MainActivity.this).getString(UserData.Key.USER_SESSID),
              item.getId(), item.getFav() == 1);
          mainPresenter.update_fav_list(jsonObject, item.getFav() == 0);
          item.withStarred(!item.mStarred);
          fastAdapter.notifyAdapterDataSetChanged();
          //we animate the heart

        } else {
          startActivity(new Intent(MainActivity.this, LoginActivity.class)
              .putExtra("title", getString(R.string.sign_in_to_submit_an_fav))
              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    //add the values which need to be saved from the adapter to the bundle
    outState = fastItemAdapter.saveInstanceState(outState);
    outState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
    super.onSaveInstanceState(outState);
  }

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
        list_view.setImageResource(R.drawable.ic_list_ads);
        linearLayoutManager = new GridLayoutManager(this, 2);
        mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        break;
      case LINEAR_LAYOUT_MANAGER:
        list_view.setImageResource(R.drawable.ic_grid);
        linearLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        //scrollPosition=scrollPosition+2;
        break;
      default:
        list_view.setImageResource(R.drawable.ic_grid);
        linearLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
    }

    if (fastItemAdapter.getAdapterItems() != null && fastItemAdapter.getAdapterItems().size() > 0) {
      for (int i = 0; i < fastItemAdapter.getAdapterItems().size(); i++) {
        fastItemAdapter.getAdapterItems().get(i)
            .setIs_grid(mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER);
        fastItemAdapter.notifyItemChanged(i);
      }
      fastItemAdapter.notifyItemRangeRemoved(0, fastItemAdapter.getItemCount());
      fastItemAdapter.notifyItemRangeChanged(0, fastItemAdapter.getItemCount());

      initAdapter();


    } else if (fastItemAdapter.getAdapterItems() != null
        && fastItemAdapter.getAdapterItems().size() == 0 && fastItemAdapter != null) {
      fastItemAdapter.clear();
      fastItemAdapter.notifyItemRangeRemoved(0, fastItemAdapter.getItemCount());
      fastItemAdapter.notifyItemRangeChanged(0, fastItemAdapter.getItemCount());
      fastItemAdapter.notifyDataSetChanged();
      fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
        Intent infoAd = new Intent(MainActivity.this, InfoAdActivity.class);
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
          mainPresenter.getAdsList(
              JsonObjBody.getListStart(PAGE, PAGE_SIZE, AppUtils.getLocale(MainActivity.this)),
              false);
        }
      }
    }
  };

  private void getlistAds(boolean progress) {
    isLoading = true;
    PAGE = 0;
    mainPresenter
        .getAdsList(JsonObjBody.getListStart(PAGE, PAGE_SIZE, AppUtils.getLocale(this)), progress);
  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      if (back_pressed + 2000 > System.currentTimeMillis()) {
        super.onBackPressed();
      } else {
        Toast.makeText(getBaseContext(), R.string.for_exit,
            Toast.LENGTH_SHORT).show();
      }
      back_pressed = System.currentTimeMillis();
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
    view_no_internet.setVisibility(View.VISIBLE);
  }

  @Override
  public void listAds(List<AdItem> new_adInfo) {
    if (new_adInfo.size() < PAGE_SIZE) {
      isLastPage = true;
    } else {
      isLastPage = false;
    }

    for (AdItem adItem : new_adInfo) {
      adItem.setIs_grid(mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER);

    }
    if (PAGE == 0) {
      isLastPage = false;
      fastItemAdapter.setNewList(new_adInfo);
//                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
      mRecyclerView.setAdapter(fastItemAdapter);
//                mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    } else {
      fastItemAdapter.add(new_adInfo);
    }

    if (new_adInfo.size() == 0 && PAGE == 0) {
      view_no_ads.setVisibility(View.VISIBLE);
    } else {
      view_no_ads.setVisibility(View.GONE);
    }
  }

  @Override
  public void sendError(String s) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void no_session(String text) {
    startActivity(new Intent(this, LoginActivity.class));
  }

  @Override
  public void userLogin() {
    rl_no_register.setVisibility(View.GONE);
    rl_register.setVisibility(View.VISIBLE);
    User user_from_json = new Gson()
        .fromJson(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_DATA_JSON),
            User.class);
    if (user_from_json != null) {
      textViewName.setText(user_from_json.getName());
      String url_img = user_from_json.getAvatar();
      GlideApp.with(this)
          .load(url_img)
          .error(R.drawable.ic_no_avatar)
          .fallback(R.drawable.ic_no_avatar)
          .centerCrop()
          .placeholder(R.drawable.ic_no_avatar)
          .transition(withCrossFade())
          .transform(new CircleTransform(this))
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(img_photo_user);
      if (user_from_json.getItems_fav() > 0) {
        fav_count.setText(String.valueOf(user_from_json.getItems_fav()));
      } else {
        fav_count.setText("");
      }
      try {
        view_new_mess.setVisibility((Integer.parseInt(user_from_json.getInternalmail_new()) > 0) ? View.VISIBLE : View.GONE);
        buble_new.setVisibility((Integer.parseInt(user_from_json.getInternalmail_new()) > 0) ? View.VISIBLE : View.GONE);
        msg.setText(user_from_json.getInternalmail_new());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    btn_profile.setVisibility(View.VISIBLE);
    btn_shop.setVisibility(AppState.getInstance(this).getInteger(AppState.Key.SHOP_AVAILABLE)==1?View.VISIBLE:View.GONE);
    view_btn_fav.setVisibility(View.VISIBLE);
    viewMess.setVisibility(View.VISIBLE);

    btn_logout.setVisibility(View.VISIBLE);
    btn_login.setVisibility(View.GONE);
  }

  @Override
  public void userNotLogin() {
    rl_no_register.setVisibility(View.VISIBLE);
    rl_register.setVisibility(View.GONE);
    btn_profile.setVisibility(View.GONE);
    btn_shop.setVisibility(View.GONE);
    view_btn_fav.setVisibility(View.GONE);
    viewMess.setVisibility(View.GONE);
    btn_logout.setVisibility(View.GONE);
    btn_login.setVisibility(View.VISIBLE);
    buble_new.setVisibility(View.GONE);
  }

  @Override
  public void userLogOut() {
    Toast.makeText(this, R.string.you_logout, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void get_setSettings() {
    ll_container_shop.setVisibility(
            AppState.getInstance(App.getInstance()).getInteger(AppState.Key.SHOP_AVAILABLE) > 0 ? View.VISIBLE
                    : View.GONE);
  }

  @Override
  public void onRefresh() {
    handler.post(() -> {
      fastItemAdapter = new FastItemAdapter<>();
      fastItemAdapter.withSelectable(true);
      footerAdapter = items();
      fastItemAdapter.addAdapter(1, footerAdapter);
      initAdapter();
      mRecyclerView.setAdapter(fastItemAdapter);

      getlistAds(false);
    });
  }

  public void side_menu_click(View v) {
    int id = v.getId();
    if (id == R.id.btn_profile) {
      startActivityForResult(new Intent(this, MyProfile.class), REQUEST_MYPROFILE);
    } else if (id == R.id.btn_shop) {
      User user_from_json = new Gson()
          .fromJson(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_DATA_JSON),
              User.class);
      if (user_from_json != null && (user_from_json.getShop_id() > 0)) {
        if (user_from_json.getShopData() != null && user_from_json.getShopData().getStatus() ==1) {
          //startActivity(new Intent(this, MyShop.class));
          startActivity(new Intent(this, MyAdActivity.class).putExtra("shop_id", true));
        } else  if (user_from_json.getShopData() != null && user_from_json.getShopData().getStatus() ==0){
          startActivity(new Intent(this, NoShopActivity.class).putExtra("is_on_moderation", true)
              .putExtra("title_shop", user_from_json.getShopData().getTitle()));
        }else  if (user_from_json.getShopData() != null && user_from_json.getShopData().getStatus() ==2){
          startActivity(new Intent(this, NoShopActivity.class).putExtra("is_not_active", true)
              .putExtra("title_shop", user_from_json.getShopData().getTitle()));
        }else  if (user_from_json.getShopData() != null && user_from_json.getShopData().getStatus() ==3){
          startActivity(new Intent(this, NoShopActivity.class).putExtra("is_block", true).putExtra("blocked_reason",user_from_json.getShopData().getBlocked_reason())
              .putExtra("title_shop", user_from_json.getShopData().getTitle()));
        }
      } else {
        startActivity(new Intent(this, NoShopActivity.class));
      }
    } else if (id == R.id.btn_favorite) {
      startActivity(new Intent(this, MyFavActivity.class));
    } else if (id == R.id.btn_message) {
      startActivity(new Intent(this, DialogsActivity.class));
    } else if (id == btn_login.getId()) {
      startActivity(new Intent(this, LoginActivity.class));
    } else if (id == R.id.btn_logout) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle(getResources().getString(R.string.dialog_log_out));
//            builder.setPositiveButton(getResources().getString(R.string.yes),
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mainPresenter.user_logout(true);
//                            dialog.cancel();
//                        }
//                    });
//            builder.setNegativeButton(getResources().getString(R.string.no), (dialogInterface, i) -> {
//                dialogInterface.cancel();
//            });
//            AlertDialog alert = builder.create();
//            alert.show();
//            Button yes = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//            yes.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
      new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
          //.setTitleText(getString(R.string.dialog_log_out))
          .setContentText(getString(R.string.dialog_log_out))
          .setCancelText(getString(R.string.cancel))
          .setConfirmText(getString(R.string.yes_logout))
          .setConfirmClickListener(sweetAlertDialog -> {
            mainPresenter.user_logout(true);
            sweetAlertDialog.dismissWithAnimation();
          })
          .show();
    }

    drawer.closeDrawer(GravityCompat.START);
  }

  private void setViewType() {
    handler.post(() -> {
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
  public void onClick(View v) {
    KeyboardUtil.hideKeyboard(this);
    int id = v.getId();
    if (id == list_view.getId()) {
      handler.post(() -> {
        if (mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER) {
          list_view.setImageResource(R.drawable.ic_grid);
          setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
          mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
          AppState.getInstance(this).setInteger(AppState.Key.CURRENTLAYOUTTYPE, 1);

        } else {
          list_view.setImageResource(R.drawable.ic_list_ads);
          setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
          mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
          AppState.getInstance(this).setInteger(AppState.Key.CURRENTLAYOUTTYPE, 0);
        }
        ParamFilterPostSingleton.getInstance()
            .setmCurrentLayoutManagerType(mCurrentLayoutManagerType);

      });
    } else if (id == list_sort.getId()) {
      FragmentManager fm = getSupportFragmentManager();
      SortDialogFragment sortDialogFragment = SortDialogFragment.newInstance(() -> {
        fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.withSelectable(true);
        footerAdapter = items();
        fastItemAdapter.addAdapter(1, footerAdapter);
        getlistAds(true);
      });
      sortDialogFragment.show(fm, "fragment_sort");
    } else if (id == btn_reload.getId()) {
      if (AppUtils.isConnected()) {
        view_no_internet.setVisibility(View.GONE);
        recreate();
      }
    } else if (id == button_filters.getId()) {
      if ((Boolean) btn_shop_show.getTag()) {
        startActivityForResult(new Intent(MainActivity.this, FiltersShopsActivity.class),
            REQUEST_ACTIVITY_FILTER);
      } else {
        startActivityForResult(new Intent(MainActivity.this, FiltersActivity.class),
            REQUEST_ACTIVITY_FILTER);
      }

    } else if (id == btn_menu.getId()) {
      if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
      } else {
        drawer.openDrawer(GravityCompat.START);
      }
    } else if (id == btn_shop_show.getId()) {
      show_shop_list(!(Boolean) btn_shop_show.getTag());
      editText.setText("");
    }
  }

  void startActivityFilterAll() {
    Intent allFilter = new Intent(MainActivity.this, ListActivityAllFilter.class);
    startActivityForResult(allFilter, REQUEST_ALL_FILTER);
  }

  void startActivityFilterAllShop() {
    Intent allFilter = new Intent(MainActivity.this, ListShopsAllFilter.class);
    startActivityForResult(allFilter, REQUEST_ALL_FILTER);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.d("onActivityResult", "resultCode= " + resultCode + ", requestCode= " + requestCode);
    switch (requestCode) {
      case REQUEST_ALL_FILTER:
        if (!(Boolean) btn_shop_show.getTag()) {
          ParamFilterPostSingleton.getInstance().resetFilter();
          ParamFilterPostSingleton.getInstance().setmCurrentLayoutManagerType(
              (AppState.getInstance(this).getInteger(AppState.Key.CURRENTLAYOUTTYPE) == 0)
                  ? LayoutManagerType.GRID_LAYOUT_MANAGER
                  : LayoutManagerType.LINEAR_LAYOUT_MANAGER);
          mCurrentLayoutManagerType = ParamFilterPostSingleton.getInstance()
              .getmCurrentLayoutManagerType();
          setViewType();
        }else{
          ParamFilterShopSingleton.getInstance().clearFilter();
        }

        break;
      case REQUEST_MYPROFILE:

        break;

      case REQUEST_ACTIVITY_FIRST:
        getlistAds(true);
        break;
    }
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
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
        case REQUEST_ACTIVITY_FILTER:
          if ((Boolean) btn_shop_show.getTag()) {
            startActivityFilterAllShop();
          } else {
            startActivityFilterAll();
          }

          break;
        case REQUEST_NEW_AD:
          getlistAds(true);
          break;
      }
    }

  }

  private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      mainPresenter.getUserInfo();
    }
  };

  @Override
  protected void onPause() {
    super.onPause();
    unregisterReceiver(mMessageReceiver);
  }
}
