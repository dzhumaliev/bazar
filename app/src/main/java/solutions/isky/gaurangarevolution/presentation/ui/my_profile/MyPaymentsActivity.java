package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.JsonObject;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.data.models.ItemPayments;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IMyPayments;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.MyPaymentsPresenter;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

import static android.support.v7.widget.RecyclerView.VERTICAL;
import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class MyPaymentsActivity extends MvpAppCompatActivity implements IMyPayments {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerViewListAds)
    RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int PAGE_SIZE = 20;
    private int PAGE = 0;
    private int delayet_progress = 200;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private Handler handler;
    @BindView(R.id.tv_balanse)
    TextView my_balance;
    @InjectPresenter
    MyPaymentsPresenter myPaymentsPresenter;
    private FastItemAdapter<ItemPayments> fastItemAdapter;
    private ItemAdapter footerAdapter;
    @BindView(R.id.btn_buy)
    Button btn_buy;

    final static int REQUEST_ACTIVITY_BALANCE = 5412;
    //@BindView(R.id.view_no_payments)
//View view_no_payments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payments);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        handler = new Handler();
        new MaterializeBuilder().withActivity(this).build();
        fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.withSelectable(true);
        footerAdapter = items();
        fastItemAdapter.addAdapter(1, footerAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_payments);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, VERTICAL);
        itemDecoration.setDrawable(drawable);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(fastItemAdapter);
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        fastItemAdapter.withSavedInstanceState(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            PAGE = 0;
            JsonObject jsonObject = JsonObjBody.getBillings(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, AppUtils.getLocale(this));
            myPaymentsPresenter.get_billins(jsonObject, false);
            isLoading = true;

        });
        JsonObject jsonObject = JsonObjBody.getBillings(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, AppUtils.getLocale(this));
        myPaymentsPresenter.get_billins(jsonObject, true);
        btn_buy.setVisibility(!App.liqpay_enable&&!App.paypal_enable?View.GONE:View.VISIBLE);
        btn_buy.setOnClickListener((view) -> startActivityForResult(new Intent(this, RefillActivity.class), REQUEST_ACTIVITY_BALANCE)
        );
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
                    footerAdapter.add(new ProgressItem().withEnabled(false).withEnabled(false));
                    isLoading = true;
                    PAGE = PAGE + 1;
                    JsonObject jsonObject = JsonObjBody.getBillings(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, AppUtils.getLocale(MyPaymentsActivity.this));
                    myPaymentsPresenter.get_billins(jsonObject, false);
                }
            }
        }
    };

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
    public void sendError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void no_session() {

    }

    @Override
    public void setHeader(String balance, String bonus) {
        my_balance.setText(balance);
    }

    @Override
    public void listPayments(ArrayList<ItemPayments> itemPayments) {
        if (itemPayments != null) {
            if (PAGE == 0) {
                isLastPage = false;
                //itemAdapter.clear();
            }
            if (itemPayments.size() < PAGE_SIZE) {
                isLastPage = true;
            } else {
                isLastPage = false;
            }
            if (PAGE == 0) {
                fastItemAdapter.set(itemPayments);
            } else {
                fastItemAdapter.add(itemPayments);
            }
            if (itemPayments.size() == 0 && PAGE == 0) {

                //view_no_mess.setVisibility(View.VISIBLE);
            } else {
                // view_no_mess.setVisibility(View.GONE);
            }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "resultCode= " + resultCode + ", requestCode= " + requestCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ACTIVITY_BALANCE:
                    JsonObject jsonObject = JsonObjBody.getBillings(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE, AppUtils.getLocale(this));
                    myPaymentsPresenter.get_billins(jsonObject, true);
                    break;

            }
        }

    }
}
