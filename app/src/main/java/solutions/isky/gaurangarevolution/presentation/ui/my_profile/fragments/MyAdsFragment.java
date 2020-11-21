package solutions.isky.gaurangarevolution.presentation.ui.my_profile.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
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
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.AdItemMy;
import solutions.isky.gaurangarevolution.presentation.custom_dialog.SweetAlertDialog;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IMyAdCount;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IMyAdsView;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.MyAdPresenterActive;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.addad.AddAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.advertising.AdvertisingActivity;
import solutions.isky.gaurangarevolution.presentation.ui.info_ad.InfoAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.LoginActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class MyAdsFragment extends MvpAppCompatFragment implements IMyAdsView {
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerViewListAds)
    RecyclerView mRecyclerView;

    @BindView(R.id.view_no_ads)
    RelativeLayout view_no_ads;
    private FastItemAdapter<AdItemMy> fastItemAdapter;
    private ItemAdapter footerAdapter;
    private LinearLayoutManager linearLayoutManager;
    Handler handler;
    private int PAGE_SIZE = 20;
    private int PAGE = 0;
    private int delayet_progress = 200;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private static final String IS_ACTIVE = "is_active";
    private static final String IS_SHOP = "is_shop";
    @InjectPresenter
    MyAdPresenterActive myAdPresenter;
    //    @BindView(R.id.view_no_ads)
//    RelativeLayout view_no_ads;
    IMyAdCount iMyAdCountA;
    private int is_active;
    //    @BindView(R.id.tv_limits)
//    TextView tv_limits;
    public static String PUBLICATE = "publicate";
    public static String UNPUBLICATE = "unpublicate";
    public static String REFRESH = "refresh";
    public static String DELETE = "delete";
    final static int REQUEST_INFO_AD = 1479;
    final static int REQUEST_EDIT = 2478;
    private boolean is_shop = false;

    public MyAdsFragment() {
    }

    public void setOnCount(IMyAdCount iMyAdCountA) {
        this.iMyAdCountA = iMyAdCountA;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public static MyAdsFragment newInstance(int is_active, boolean is_shop) {
        MyAdsFragment fragment = new MyAdsFragment();
        Bundle args = new Bundle();
        args.putInt(IS_ACTIVE, is_active);
        args.putBoolean(IS_SHOP, is_shop);
        fragment.setArguments(args);
        return fragment;
    }

//    public void setOnCount(IMyAdCountA iMyAdCountA) {
//        this.iMyAdCountA = iMyAdCountA;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, rootView);
        handler = new Handler();
        new MaterializeBuilder().withActivity(getActivity()).build();
        fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.withSelectable(true);
        footerAdapter = items();
        fastItemAdapter.addAdapter(1, footerAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(fastItemAdapter);
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        fastItemAdapter.withSavedInstanceState(savedInstanceState);
        fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
            Intent infoAd = new Intent(getActivity(), InfoAdActivity.class);
            infoAd.putExtra(InfoAdActivity.ID_ITEM, item.getId());
            infoAd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getActivity().startActivityForResult(infoAd, REQUEST_INFO_AD);
            return false;
        });
        is_active = getArguments().getInt(IS_ACTIVE);
        is_shop = getArguments().getBoolean(IS_SHOP);
        isLoading = true;
        JsonObject mParams = JsonObjBody.getMyAds(UserData.getInstance(getActivity()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, UserData.getInstance(getActivity()).getInteger(UserData.Key.USER_ID), is_active, AppUtils.getLocale(getActivity()), is_shop);
        myAdPresenter.setParams(mParams);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            PAGE = 0;
            myAdPresenter.getMyAds(JsonObjBody.getMyAds(UserData.getInstance(getActivity()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, UserData.getInstance(getActivity()).getInteger(UserData.Key.USER_ID), is_active, AppUtils.getLocale(getActivity()), is_shop), false);
            isLoading = true;

        });
        fastItemAdapter.withEventHook(new ClickEventHook<AdItemMy>() {
            @Nullable
            @Override
            public List<View> onBindMany(@NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof AdItemMy.ViewHolder) {
                    List<View> views = new ArrayList<>();
                    views.add(((AdItemMy.ViewHolder) viewHolder).btn_up_ad);
                    views.add(((AdItemMy.ViewHolder) viewHolder).btn_deactive);
                    views.add(((AdItemMy.ViewHolder) viewHolder).btn_active);
                    views.add(((AdItemMy.ViewHolder) viewHolder).btn_share);
                    views.add(((AdItemMy.ViewHolder) viewHolder).btn_delete_post);
                    views.add(((AdItemMy.ViewHolder) viewHolder).btn_edit);
                    views.add(((AdItemMy.ViewHolder) viewHolder).btn_actions_ad);
                    return views;
                }
                return super.onBindMany(viewHolder);
            }

            @Override
            public void onClick(View v, int position, FastAdapter<AdItemMy> fastAdapter, AdItemMy item) {
                if (v.getId() == R.id.btn_up_ad) {
                    if (item.getStatus() == 3) {
                        if (item.getRefresh() == 0) {
                            v.setEnabled(false);
                            myAdPresenter.item_freeup(JsonObjBody.item_up(item.getId(), UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID)), true);
                        } else if (item.getRefresh() == 1) {
                            JsonObject jsonObject = JsonObjBody.item_change_status(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), item.getId(), myAdPresenter.REFRESH);
                            myAdPresenter.item_change_status(jsonObject, true, item, myAdPresenter.REFRESH);

                        }
                    }
                } else if (v.getId() == R.id.btn_active) {
                    JsonObject jsonObject = JsonObjBody.item_change_status(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), item.getId(), PUBLICATE);
                    myAdPresenter.item_change_status(jsonObject, true, item, PUBLICATE);

                }
                else if (v.getId() == R.id.btn_deactive) {
                    JsonObject jsonObject = new JsonObjBody().item_change_status(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), item.getId(), UNPUBLICATE);
                    myAdPresenter.item_change_status(jsonObject, true, item, UNPUBLICATE);

                }
                else if (v.getId() == R.id.btn_share) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, item.getLink());
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_gl)));

                }
                else if (v.getId() == R.id.btn_delete_post) {
                    JsonObject jsonObject = JsonObjBody.item_change_status(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), item.getId(), DELETE);
                    deletePost(jsonObject,  item, DELETE);

                }
// else if (v.getId() == R.id.item_menu) {
//                    if (item.getStatus() == 3) {
//                        myAdPresenter.showPopupMenuActive(v, getActivity(), item);
//                    } else {
//                        myAdPresenter.showPopupMenuNoActive(v, getActivity(), item);
//                    }
//
//                }
                else if (v.getId() == R.id.btn_actions_ad) {
                    if (item.getStatus() == 3) {
                        if (AppState.getInstance(getActivity()).getBoolean(AppState.Key.LOGGED_IN)) {
                             startActivity(new Intent(getActivity(), AdvertisingActivity.class).putExtra("title_ad", item.getTitle()).putExtra("id_ad", String.valueOf(item.getId())));
                        } else {
                            sendError(getString(R.string.no_session));
                            no_session();
                        }

                    } else {
                        //editPost(item.getId());
                    }

                }else if(v.getId()==R.id.btn_edit){
                    Intent infoAd = new Intent(getActivity(), AddAdActivity.class);
                    //infoAd.putExtra(InfoAdActivity.ID_ITEM, item.getId());
                    infoAd.putExtra(InfoAdActivity.ID_ITEM, item.getId());
                    getActivity().startActivityForResult(infoAd,REQUEST_EDIT);
                }
            }
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
                    myAdPresenter.getMyAds(JsonObjBody.getMyAds(UserData.getInstance(getActivity()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, UserData.getInstance(getActivity()).getInteger(UserData.Key.USER_ID), is_active, AppUtils.getLocale(getActivity()), is_shop), false);
                }
            }
        }
    };

    @Override
    public void listAds(List<AdItemMy> adItemMies) {
        if (adItemMies.size() < PAGE_SIZE) {
            isLastPage = true;
        } else {
            isLastPage = false;
        }
        if (PAGE == 0) {
            isLastPage = false;
            fastItemAdapter.clear();
        }
        fastItemAdapter.add(adItemMies);
        if (adItemMies.size() == 0 && PAGE == 0) {

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


//    @Override
//    public void setTitlePage(String active, String no_active, String shop_active, String shop_no_active) {
//        if (iMyAdCountA != null)
//            iMyAdCountA.setOnCount(active, no_active, shop_active, shop_no_active);
//    }

    //    @Override
//    public void edit_ad(AdItemMy adItemMy) {
//        editPost(adItemMy.getId());
//    }
//
//    private void editPost(int id) {
//        Intent infoAd = new Intent(getActivity(), AddAdActivity.class);
//        infoAd.putExtra(InfoAdActivity.ID_ITEM, id);
//        startActivity(infoAd);
//    }
//
    @Override
    public void deactive_ad(AdItemMy adItemMy) {
        PAGE = 0;
        //fastItemAdapter.clear();
        JsonObject mParams = JsonObjBody.getMyAds(UserData.getInstance(getActivity()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, UserData.getInstance(getActivity()).getInteger(UserData.Key.USER_ID), is_active, AppUtils.getLocale(getActivity()), is_shop);
        myAdPresenter.updater(mParams, true, is_active);

    }

    @Override
    public void if_need_update(int is_active) {
        if (iMyAdCountA != null)
            iMyAdCountA.rebild(is_active);
    }
//
//    @Override
//    public void share_ad(AdItemMy adItemMy) {
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, adItemMy.getLink());
//        sendIntent.setType("text/plain");
//        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_gl)));
//    }
//
//
//
//
    public void deletePost(JsonObject jsonObject,  AdItemMy adItemMy, String status) {

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                //.setTitleText(getString(R.string.dialog_log_out))
                .setContentText(getString(R.string.dialog_delet))
                .setCancelText(getString(R.string.cancel))
                .setConfirmText(getString(R.string.delete_post))
                .setConfirmClickListener(sweetAlertDialog -> {
                    myAdPresenter.item_change_status(jsonObject, true, adItemMy, status);
                    sweetAlertDialog.dismissWithAnimation();
                })
                .show();
    }


}