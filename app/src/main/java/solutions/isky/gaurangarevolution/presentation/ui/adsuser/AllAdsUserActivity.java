package solutions.isky.gaurangarevolution.presentation.ui.adsuser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.LayoutParams;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import solutions.isky.gaurangarevolution.data.models.PhoneUser;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.custom_dialog.SweetAlertDialog;
import solutions.isky.gaurangarevolution.presentation.mvp.adsuser.AllAdsUserPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.adsuser.IAllUserAds;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.view.ExpandableTextView;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.info_ad.InfoAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.info_ad.PhonesDialogFragment;
import solutions.isky.gaurangarevolution.presentation.ui.login.LoginActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.view.ButtonMenu;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class AllAdsUserActivity extends MvpAppCompatActivity implements IAllUserAds {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.swiprefresh)
  SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.recyclerViewListAds)
  RecyclerView mRecyclerView;
  ActionBar actionBar;
  private FastItemAdapter<AdItem> fastItemAdapter;
  private ItemAdapter footerAdapter;
  LinearLayoutManager linearLayoutManager;
  Handler handler;
  private int PAGE_SIZE = 20;
  private int PAGE = 0;
  private boolean isLastPage = false;
  private boolean isLoading = false;
  private static final String KEY_LAYOUT_MANAGER = "layoutManager";
  @InjectPresenter
  AllAdsUserPresenter allAdsUserPresenter;
  private static long back_pressed;
  private int delayet_progress = 200;
  private int mUserId;
  private int mShopId;
  @BindView(R.id.tvLastActiv)
  TextView tvLastActiv;
  @BindView(R.id.tvCountAds)
  TextView tvCountAds;
  @BindView(R.id.img_photo_user)
  ImageView img_photo_user;
  final static int REQUEST_INFO_AD = 1279;
  public static final String POSITION_ITEM = "position_item";
  public static final String STARRED = "starred";
  private int mPosition = -1;
  @BindView(R.id.btn_reload)
  ImageView btn_reload;
  @BindView(R.id.view_no_internet)
  View view_no_internet;
  @BindView(R.id.btn_shop_site)
  ButtonMenu btn_shop_site;
  @BindView(R.id.btn_shop_phone)
  ButtonMenu btn_shop_phone;
  @BindView(R.id.block_shop_info)
  LinearLayout block_shop_info;
  @BindView(R.id.tvDescrShop)
  ExpandableTextView tvDescrShop;

  @BindView(R.id.appbar_layout)
  AppBarLayout appBarLayout;
  @BindView(R.id.view_no_ads)
  RelativeLayout view_no_ads;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_all_ads_user);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.show();
      actionBar.setTitle("");
    }
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
    mUserId = getIntent().getIntExtra("user_id", 0);
    mShopId = getIntent().getIntExtra("shop_id", 0);
    User user_detail = (User) getIntent().getSerializableExtra("user_data");
    if (user_detail != null) {
      actionBar.setTitle(!TextUtils.isEmpty(user_detail.getName()) ? user_detail.getName()
          : user_detail.getLogin());
      tvLastActiv.setText(String.format(getString(R.string.last_log),
          AppUtils.getTimeLastIn(user_detail.getLast_activity(), App.getInstance())));
      GlideApp.with(this)
          .load(user_detail.getAvatar())
          .error(R.drawable.ic_no_avatar)
          .fallback(R.drawable.ic_no_avatar)
          .centerCrop()
          .transition(withCrossFade())
          .transform(new CircleTransform(this))
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(img_photo_user);
    }
    allAdsUserPresenter.setParams(JsonObjBody
        .getUserAds(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID),
            PAGE, PAGE_SIZE, mUserId, AppUtils.getLocale(AllAdsUserActivity.this), mShopId));
    fastItemAdapter.withSavedInstanceState(savedInstanceState);
    swipeRefreshLayout.setOnRefreshListener(() -> {
      isLoading = true;
      PAGE = 0;
      allAdsUserPresenter.getAdsList(JsonObjBody
              .getUserAds(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID),
                  PAGE, PAGE_SIZE, mUserId, AppUtils.getLocale(AllAdsUserActivity.this), mShopId),
          false);
    });
    fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
      Intent infoAd = new Intent(AllAdsUserActivity.this, InfoAdActivity.class);
      infoAd.putExtra(InfoAdActivity.ID_ITEM, item.getId());
      infoAd.putExtra(InfoAdActivity.POSITION_ITEM, position);
      //infoAd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivityForResult(infoAd, REQUEST_INFO_AD);
      return false;
    });

    fastItemAdapter.withEventHook(new ClickEventHook<AdItem>() {
      @Override
      public void onClick(View v, int position, FastAdapter<AdItem> fastAdapter, AdItem item) {
        if (AppState.getInstance(AllAdsUserActivity.this).getBoolean(AppState.Key.LOGGED_IN)) {
          JsonObject jsonObject = JsonObjBody.update_fav(
              UserData.getInstance(AllAdsUserActivity.this).getString(UserData.Key.USER_SESSID),
              item.getId(), item.getFav() == 1);
          allAdsUserPresenter.update_fav_list(jsonObject, item.getFav() == 0);
          item.withStarred(!item.mStarred);
          fastAdapter.notifyAdapterDataSetChanged();
          //we animate the heart

        } else {
          startActivity(new Intent(AllAdsUserActivity.this, LoginActivity.class)
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
    btn_reload.setOnClickListener(v -> {
      if (AppUtils.isConnected()) {
        view_no_internet.setVisibility(View.GONE);
        recreate();
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
          allAdsUserPresenter.getAdsList(JsonObjBody.getUserAds(
              UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), PAGE,
              PAGE_SIZE, mUserId, AppUtils.getLocale(AllAdsUserActivity.this), mShopId), false);
        }
      }
    }
  };

  @Override
  public void listAds(List<AdItem> new_adInfo) {
    if (new_adInfo.size() < PAGE_SIZE) {
      isLastPage = true;
    } else {
      isLastPage = false;
    }
    for (AdItem adItem : new_adInfo) {
      adItem.setIs_grid(true);

    }
    if (PAGE == 0) {
      isLastPage = false;
      fastItemAdapter.setNewList(new_adInfo);
//      handler.postDelayed(() -> {
//        updateToolbarBehaviour();
//      }, 200);
    } else {
      fastItemAdapter.add(new_adInfo);
    }
    if (new_adInfo.size() == 0) {
       view_no_ads.setVisibility(View.VISIBLE);
    } else {
      view_no_ads.setVisibility(View.GONE);
    }


  }

  @Override
  public void setCountrAds(String count, User user, ShopData shopData) {
    try {
      if (Integer.parseInt(count) > 0) {
        tvCountAds.setText(count);
      } else {
        tvCountAds.setText("0");
      }
    } catch (Exception e) {
      e.printStackTrace();
      tvCountAds.setText("0");
    }
    if (shopData != null) {
      block_shop_info.setVisibility(View.VISIBLE);
      btn_shop_site.setEnabled(!TextUtils.isEmpty(shopData.getSite()));
      btn_shop_phone.setEnabled(shopData.getPhones().size() > 0);
      tvDescrShop.setText(shopData.getDescr());
      actionBar.setTitle(shopData.getTitle());
      btn_shop_site.setOnClickListener(v -> {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(shopData.getSite())));
      });
      btn_shop_phone.setOnClickListener(v -> {
        if (shopData.getPhones().size() > 0) {
          if (shopData.getPhones().size() > 1) {
            FragmentManager fm = getSupportFragmentManager();
            PhonesDialogFragment phonesDialogFragment = PhonesDialogFragment
                .newInstance(null, shopData.getPhones(),
                    new PhonesDialogFragment.PhonesCauseDialogListener() {
                      @Override
                      public void onFinishEditDialog(PhoneUser phoneUser) {
                        try {
                          Intent phone = new Intent(Intent.ACTION_DIAL,
                              Uri.parse("tel:" + shopData.getPhones().get(0)));
                          startActivity(phone);
                        } catch (Exception e) {
                          e.printStackTrace();
                          showNumberPhone(shopData.getPhones().get(0));
                        }
                      }
                    });
            phonesDialogFragment.show(fm, "fragment_phones");

          } else {
            try {
              Intent phone = new Intent(Intent.ACTION_DIAL,
                  Uri.parse("tel:" + shopData.getPhones().get(0)));
              startActivity(phone);
            } catch (Exception e) {
              e.printStackTrace();
              showNumberPhone(shopData.getPhones().get(0));
            }
          }


        }
      });
      GlideApp.with(this)
          .load(shopData.getCompanylogo())
          .error(R.drawable.ic_placeholder_shop)
          .fallback(R.drawable.ic_placeholder_shop)
          .placeholder(R.drawable.ic_placeholder_shop)
          .centerCrop()
          .transition(withCrossFade())
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(img_photo_user);
      tvLastActiv.setText("");
    } else if (user != null) {
      actionBar.setTitle(!TextUtils.isEmpty(user.getName()) ? user.getName() : user.getLogin());
      tvLastActiv.setText(String.format(getString(R.string.last_log),
          AppUtils.getTimeLastIn(user.getLast_activity(), App.getInstance())));
      GlideApp.with(this)
          .load(user.getAvatar())
          .error(R.drawable.ic_no_avatar)
          .fallback(R.drawable.ic_no_avatar)
          .centerCrop()
          .transition(withCrossFade())
          .transform(new CircleTransform(this))
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(img_photo_user);

    }

  }

  private void showNumberPhone(String phone) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(phone);
//        builder.setPositiveButton(getResources().getString(R.string.yes),
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//        Button yes = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//        yes.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        //.setTitleText(getString(R.string.dialog_log_out))
        .setContentText(phone)
        .setConfirmText(getString(R.string.yes))
        .setConfirmClickListener(sweetAlertDialog -> {
          sweetAlertDialog.dismissWithAnimation();
        })
        .show();

  }

  @Override
  public void sendError(String s) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void no_session(String text) {

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


  public void turnOffToolbarScrolling() {

    //turn off scrolling
    AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) toolbar
        .getLayoutParams();
    toolbarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
    toolbar.setLayoutParams(toolbarLayoutParams);

    CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout
        .getLayoutParams();
    appBarLayoutParams.setBehavior(null);
    appBarLayout.setLayoutParams(appBarLayoutParams);
  }

  public void turnOnToolbarScrolling() {

    //turn on scrolling
    AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) toolbar
        .getLayoutParams();
    toolbarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
        | LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
    toolbar.setLayoutParams(toolbarLayoutParams);

    CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout
        .getLayoutParams();
    appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
    appBarLayout.setLayoutParams(appBarLayoutParams);
  }

  public void updateToolbarBehaviour() {
    if (fastItemAdapter.getItemCount() > 0) {
      turnOnToolbarScrolling();
    } else {
      turnOffToolbarScrolling();
    }
  }
}
