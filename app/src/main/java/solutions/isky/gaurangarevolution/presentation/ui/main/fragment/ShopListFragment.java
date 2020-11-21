package solutions.isky.gaurangarevolution.presentation.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterShopSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.main.IShopView;
import solutions.isky.gaurangarevolution.presentation.mvp.main.ShopsPresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.adsuser.AllAdsUserActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.LoginActivity;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.MyAdActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class ShopListFragment extends MvpAppCompatFragment implements IShopView {
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerViewListAds)
    RecyclerView mRecyclerView;
    @BindView(R.id.view_no_shops)
    RelativeLayout view_no_ads;
    @BindView(R.id.view_no_ads)
    RelativeLayout view_no_ads2;
    private FastItemAdapter<ShopData> fastItemAdapter;
    private ItemAdapter footerAdapter;
    private LinearLayoutManager linearLayoutManager;
    Handler handler;
    private int PAGE_SIZE = 20;
    private int PAGE = 0;
    private int delayet_progress = 200;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    @InjectPresenter
    ShopsPresenter shopsPresenter;


    public ShopListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, rootView);
        handler = new Handler();
        ParamFilterShopSingleton.getInstance().clearFilter();
//        new MaterializeBuilder().withActivity(getActivity()).build();
        fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.withSelectable(true);
        footerAdapter = items();
        fastItemAdapter.addAdapter(1, footerAdapter);
        linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(fastItemAdapter);
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        fastItemAdapter.withSavedInstanceState(savedInstanceState);
        fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
            User user = new Gson().fromJson(UserData.getInstance(getActivity()).getString(UserData.Key.USER_DATA_JSON), User.class);
            if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN) && user != null && item.getId() == user.getShop_id()) {

                startActivity(new Intent(getActivity(), MyAdActivity.class).putExtra("shop_id", true));
            } else {
                startActivity(new Intent(getActivity(), AllAdsUserActivity.class).putExtra("shop_id", item.getId()).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
            return false;
        });

        isLoading = true;
        JsonObject mParams = JsonObjBody.getShops(PAGE, PAGE_SIZE,0, "", 0, AppUtils.getLocale(getActivity()));
        if(AppState.getInstance(getActivity()).getInteger(AppState.Key.SHOP_AVAILABLE)>0){
            shopsPresenter.getShops(mParams, true);
        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            PAGE = 0;
            shopsPresenter.getShops(JsonObjBody.getShops(PAGE, PAGE_SIZE,0, "", 0, AppUtils.getLocale(getActivity())),false);
            isLoading = true;

        });

        return rootView;
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
                    shopsPresenter.getShops(JsonObjBody.getShops(PAGE, PAGE_SIZE,0, "", 0, AppUtils.getLocale(getActivity())),false);
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
        view_no_ads2.setVisibility(View.GONE);
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
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void no_session() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
