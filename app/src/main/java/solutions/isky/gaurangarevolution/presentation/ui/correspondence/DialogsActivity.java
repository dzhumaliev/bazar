package solutions.isky.gaurangarevolution.presentation.ui.correspondence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.JsonObject;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.ChatsList;
import solutions.isky.gaurangarevolution.data.models.ChatsListNew;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.mvp.correspondence.DialogsActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.correspondence.IDialogsActivity;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

import static com.mikepenz.fastadapter.adapters.ItemAdapter.items;

public class DialogsActivity extends MvpAppCompatActivity implements IDialogsActivity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.dialogsList)
  RecyclerView dialogsList;
  @BindView(R.id.swiprefresh)
  SwipeRefreshLayout swipeRefreshLayout;
  public static boolean active = false;
  private ArrayList<ChatsListNew> dialogs;
  private FastItemAdapter<ChatsListNew> fastItemAdapter;
  private ItemAdapter footerAdapter;
  private LinearLayoutManager linearLayoutManager;
  Handler handler;
  private int PAGE_SIZE = 40;
  private int PAGE = 0;
  private boolean isLastPage = false;
  private boolean isLoading = false;
  @InjectPresenter
  DialogsActivityPresenter dialogsActivityPresenter;
  @BindView(R.id.view_no_mess)
  RelativeLayout view_no_mess;
  private int delayet_progress = 200;
  private String mSearch = "";
  @BindView(R.id.editText)
  MyCustomEditText searchEditText;
@BindView(R.id.btn_search)
  Button btn_search;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialogs);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.show();
    }
    handler = new Handler();
    searchEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        mSearch = s.toString().trim();
        if (TextUtils.isEmpty(mSearch)){
          PAGE = 0;
          dialogsActivityPresenter.getDialogs(getJsonObject(), true);
          KeyboardUtil.hideKeyboard(DialogsActivity.this);
          searchEditText.clearFocus();
          btn_search.setEnabled(false);
        }else if (mSearch.length()>2){
          btn_search.setEnabled(true);
        }
      }
    });
    btn_search.setOnClickListener(v -> {
      if (mSearch.length()>2){
        PAGE = 0;
        dialogsActivityPresenter.getDialogs(getJsonObject(), true);
        searchEditText.clearFocus();
        KeyboardUtil.hideKeyboard(DialogsActivity.this);
      }

    });
    new MaterializeBuilder().withActivity(this).build();
    fastItemAdapter = new FastItemAdapter<>();
    fastItemAdapter.withSelectable(true);
    footerAdapter = items();
    fastItemAdapter.addAdapter(1, footerAdapter);
    linearLayoutManager = new LinearLayoutManager(this);
    dialogsList.setLayoutManager(linearLayoutManager);
    dialogsList.setItemAnimator(new DefaultItemAnimator());
    dialogsList.setAdapter(fastItemAdapter);
    dialogsList.addOnScrollListener(recyclerViewOnScrollListener);
    fastItemAdapter.withSavedInstanceState(savedInstanceState);
    fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
      item.setMessages_new(0);
      //adapter.getFastAdapter().notifyAdapterDataSetChanged();
      startActivity(
          new Intent(this, MessageActivity.class).putExtra("interlocutor", item.getUser_id())
              .putExtra("mShopId", item.getShop_id())
              .putExtra("mFromShop", item.isShop_id_my() ? 1 : 0));
      return false;
    });
    swipeRefreshLayout.setOnRefreshListener(() -> {
      PAGE = 0;
      dialogsActivityPresenter.getDialogs(getJsonObject(), false);
      isLoading = true;

    });
//        JsonObject jsonObject = new JsonObjBody().getDialogs(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), PAGE, PAGE_SIZE);
//        dialogsActivityPresenter.getDialogs(jsonObject,true);
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
          dialogsActivityPresenter.getDialogs(getJsonObject(), false);
        }
      }
    }
  };

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

    finish();
  }

  @Override
  public void listChats(List<ChatsListNew> chatsLists) {
    if (chatsLists.size() < PAGE_SIZE) {
      isLastPage = true;
    } else {
      isLastPage = false;
    }
    if (PAGE == 0) {
      isLastPage = false;
      fastItemAdapter.clear();
    }
    fastItemAdapter.add(chatsLists);
    if (chatsLists.size() == 0 && PAGE == 0) {

      view_no_mess.setVisibility(View.VISIBLE);
    } else {
      view_no_mess.setVisibility(View.GONE);
    }
//        dialogs = (ArrayList<ChatsList>) chatsLists;
//        fastItemAdapter.set(dialogs);

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
  public void onResume() {
    super.onResume();
    PAGE = 0;
    dialogsActivityPresenter.getDialogs(getJsonObject(), true);
    registerReceiver(mMessageReceiver, new IntentFilter(Constants.NEW_MESSAGE));
    active = true;
  }


  @Override
  protected void onPause() {
    super.onPause();
    unregisterReceiver(mMessageReceiver);
    active = false;
  }


  private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      PAGE = 0;
      dialogsActivityPresenter.getDialogs(getJsonObject(), true);

    }
  };

  private JsonObject getJsonObject() {
    return JsonObjBody
        .getDialogs(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID),
            PAGE, PAGE_SIZE, mSearch);
  }
}
