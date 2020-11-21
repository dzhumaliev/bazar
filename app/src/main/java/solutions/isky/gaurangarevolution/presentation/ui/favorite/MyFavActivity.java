package solutions.isky.gaurangarevolution.presentation.ui.favorite;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
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
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.favorite.IAddFav;
import solutions.isky.gaurangarevolution.presentation.mvp.favorite.MyFavPresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.info_ad.InfoAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.LoginActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.utils.LayoutManagerType;

import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class MyFavActivity extends MvpAppCompatActivity implements IAddFav {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerViewListAds)
    RecyclerView mRecyclerView;
    private FastItemAdapter<AdItem> fastItemAdapter;
    private ItemAdapter footerAdapter;
    Handler handler;
    private int PAGE_SIZE = 20;
    private int PAGE = 0;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    LayoutManagerType mCurrentLayoutManagerType;
    private LinearLayoutManager linearLayoutManager;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private int delayet_progress = 200;
    final static int REQUEST_INFO_AD = 1214;
    @BindView(R.id.view_no_ads)
    RelativeLayout view_no_fav;
    @InjectPresenter
    MyFavPresenter myFavPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        handler = new Handler();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        new MaterializeBuilder().withActivity(this).build();
        fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.withSelectable(true);
        footerAdapter = items();
        fastItemAdapter.addAdapter(1, footerAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        ParamFilterPostSingleton.getInstance().setmCurrentLayoutManagerType((AppState.getInstance(this).getInteger(AppState.Key.CURRENTLAYOUTTYPE) == 0) ? LayoutManagerType.GRID_LAYOUT_MANAGER : LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        mCurrentLayoutManagerType = ParamFilterPostSingleton.getInstance().getmCurrentLayoutManagerType();
        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        fastItemAdapter.withSavedInstanceState(savedInstanceState);
        isLoading = true;
        JsonObject mParams = JsonObjBody.getMyFavs(UserData.getInstance(this).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, AppUtils.getLocale(this));
        myFavPresenter.setParams(mParams);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            PAGE = 0;
            myFavPresenter.getMyFavs(JsonObjBody.getMyFavs(UserData.getInstance(this).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, AppUtils.getLocale(this)), false);
            isLoading = true;

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

    @Override
    public void listAds(List<AdItem> adItems) {
        if (adItems.size() < PAGE_SIZE) {
            isLastPage = true;
        } else {
            isLastPage = false;
        }
        if (PAGE == 0) {
            isLastPage = false;
            fastItemAdapter.clear();
        }
        for (AdItem adItem : adItems) {
            adItem.setIs_grid(mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER);

        }

        if (PAGE == 0) {
            isLastPage = false;
            fastItemAdapter.setNewList(adItems);
            mRecyclerView.setAdapter(fastItemAdapter);
        } else {
            fastItemAdapter.add(adItems);
        }

        if (adItems.size() == 0&&PAGE == 0) {
            view_no_fav.setVisibility(View.VISIBLE);
        } else {
            view_no_fav.setVisibility(View.GONE);
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
    public void sendError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void no_session() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showNoNetworkLayout() {

    }

    private void initAdapter() {
        fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
            Intent infoAd = new Intent(MyFavActivity.this, InfoAdActivity.class);
            infoAd.putExtra(InfoAdActivity.ID_ITEM, item.getId());
            infoAd.putExtra(InfoAdActivity.POSITION_ITEM, position);
            startActivityForResult(infoAd, REQUEST_INFO_AD);
            return false;
        });

        fastItemAdapter.withEventHook(new ClickEventHook<AdItem>() {
            @Override
            public void onClick(View v, int position, FastAdapter<AdItem> fastAdapter, AdItem item) {
                if (AppState.getInstance(MyFavActivity.this).getBoolean(AppState.Key.LOGGED_IN)) {
                    JsonObject jsonObject = JsonObjBody.update_fav(UserData.getInstance(MyFavActivity.this).getString(UserData.Key.USER_SESSID), item.getId(), item.getFav() == 1);
                    myFavPresenter.update_fav_list(jsonObject, item.getFav() == 0);
                    item.withStarred(!item.mStarred);
                    fastAdapter.notifyAdapterDataSetChanged();
                    //we animate the heart

                } else {
                    startActivity(new Intent(MyFavActivity.this, LoginActivity.class).putExtra("title", getString(R.string.sign_in_to_submit_an_fav)));
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
                        && totalItemCount >= PAGE_SIZE) {
                    footerAdapter.clear();
                    footerAdapter.add(new ProgressItem().withEnabled(false));
                    isLoading = true;
                    PAGE = PAGE + 1;
                    myFavPresenter.getMyFavs(new JsonObjBody().getMyFavs(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, AppUtils.getLocale(App.getInstance())), false);
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


        } else if (fastItemAdapter.getAdapterItems() != null && fastItemAdapter.getAdapterItems().size() == 0 && fastItemAdapter != null) {
            fastItemAdapter.clear();
            fastItemAdapter.notifyItemRangeRemoved(0, fastItemAdapter.getItemCount());
            fastItemAdapter.notifyItemRangeChanged(0, fastItemAdapter.getItemCount());
            fastItemAdapter.notifyDataSetChanged();
            fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
                Intent infoAd = new Intent(MyFavActivity.this, InfoAdActivity.class);
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
}