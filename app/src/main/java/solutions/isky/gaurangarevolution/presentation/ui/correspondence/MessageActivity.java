package solutions.isky.gaurangarevolution.presentation.ui.correspondence;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.JsonObject;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ModelAdapter;
import com.mikepenz.materialize.MaterializeBuilder;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.GenericMessItem;
import solutions.isky.gaurangarevolution.data.models.InComeGenericMessItem;
import solutions.isky.gaurangarevolution.data.models.InComeMessModel;
import solutions.isky.gaurangarevolution.data.models.MessModel;
import solutions.isky.gaurangarevolution.data.models.OutGenericMessItem;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.mvp.correspondence.IMessageActivity;
import solutions.isky.gaurangarevolution.presentation.mvp.correspondence.MessageActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.correspondence.custom_view.StickyHeaderAdapterChat;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;


public class MessageActivity extends MvpAppCompatActivity implements IMessageActivity, SwipyRefreshLayout.OnRefreshListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dialogsList)
    RecyclerView dialogsList;
    @BindView(R.id.swipyrefreshlayout)
    SwipyRefreshLayout swipeRefreshLayout;

    @BindView(R.id.messageInput)
    MyCustomEditText messageInput;
    @BindView(R.id.messageSendButton)
    ImageView messageSendButton;

    private FastAdapter fastAdapter;

    private StickyHeaderAdapterChat stickyHeaderAdapter;
    private LinearLayoutManager linearLayoutManager;
    Handler handler;
    private int PAGE_SIZE = 40;
    private int PAGE = 0;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    @BindView(R.id.view_no_mess)
    RelativeLayout view_no_mess;
    private int delayet_progress = 200;
    @InjectPresenter
    MessageActivityPresenter messageActivityPresenter;
    private int mInterlocutor = 0;
    ModelAdapter<MessModel, GenericMessItem> itemAdapter;
    ActionBar actionBar;
    public static boolean active = false;

    private int mShopId;
    private int mFromShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        handler = new Handler();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        mInterlocutor = getIntent().getIntExtra("interlocutor", 0);
        mShopId = getIntent().getIntExtra("mShopId", 0);
        mFromShop = getIntent().getIntExtra("mFromShop", 0);
        new MaterializeBuilder().withActivity(this).build();


        stickyHeaderAdapter = new StickyHeaderAdapterChat();

        itemAdapter=new ModelAdapter<>(messModel -> {
            if (messModel instanceof InComeMessModel) {
                return new InComeGenericMessItem(messModel);
            } else {
                return new OutGenericMessItem(messModel);
            }
        });
        fastAdapter = FastAdapter.with(Arrays.asList(itemAdapter));
        fastAdapter.withSelectable(true);
//        footerAdapter = items();
//        fastAdapter.addAdapter(1, footerAdapter);
        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(stickyHeaderAdapter);
        dialogsList.addItemDecoration(decoration);
        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        dialogsList.setLayoutManager(linearLayoutManager);
        dialogsList.setItemAnimator(new DefaultItemAnimator());
        dialogsList.setAdapter(stickyHeaderAdapter.wrap(fastAdapter));
        //dialogsList.addOnScrollListener(recyclerViewOnScrollListener);
        fastAdapter.withSavedInstanceState(savedInstanceState);
//        if (mInterlocutor > 0) {
//            JsonObject jsonObject = new JsonObjBody().getMessages(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), mInterlocutor, PAGE, PAGE_SIZE);
//            messageActivityPresenter.getMessages(jsonObject, true);
//        }
        messageSendButton.setEnabled(false);
        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (messageInput.getText().toString().trim().length() > 1) {
                    messageSendButton.setEnabled(true);
                } else {
                    messageSendButton.setEnabled(false);
                }
            }
        });
        messageSendButton.setOnClickListener(view -> {
            String text = messageInput.getText().toString().trim();
            JsonObject jsonObject = JsonObjBody.answerMess(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), text, mInterlocutor, mShopId, mFromShop);

            messageActivityPresenter.answerMessages(jsonObject, true);
        });
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
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

        }, delayet_progress);
    }

    @Override
    public void showNoNetworkLayout() {

    }

    @Override
    public void sendError(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void no_session() {
        finish();
    }

    @Override
    public void listChats(ArrayList<MessModel> messModels) {
        if (messModels != null) {
            if (PAGE == 0) {
                isLastPage = false;
                //itemAdapter.clear();
            }
            if (messModels.size() < PAGE_SIZE) {
                isLastPage = true;
            } else {
                isLastPage = false;
            }
            if (PAGE == 0) {
                itemAdapter.set(messModels);
            }else {
                itemAdapter.add(itemAdapter.getAdapterItemCount(),messModels);
            }

            if (messModels.size() == 0 && PAGE == 0) {

                view_no_mess.setVisibility(View.VISIBLE);
            } else {
                view_no_mess.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void setTitle(String title) {

        actionBar.setTitle(title);
    }

    @Override
    public void answeMess() {
        Toast.makeText(this, getString(R.string.mes_send), Toast.LENGTH_SHORT).show();
        messageInput.setText("");
        PAGE = 0;
        messageActivityPresenter.getMessages(getJsonObject(), false);
        isLoading = true;
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if(direction==SwipyRefreshLayoutDirection.BOTTOM){
            PAGE = 0;
            messageActivityPresenter.getMessages(getJsonObject(), false);
            isLoading = true;
        }else if(direction==SwipyRefreshLayoutDirection.TOP){
            if((!isLoading && !isLastPage)){
                PAGE = PAGE+1;
                messageActivityPresenter.getMessages(getJsonObject(), false);
                isLoading = true;
            }else{
                hideProgres();
                Toast.makeText(this, R.string.no_messages, Toast.LENGTH_SHORT).show();
            }

        }

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
        if (mInterlocutor > 0) {
            PAGE=0;
            messageActivityPresenter.getMessages(getJsonObject(), true);
        }
        active = true;
        registerReceiver(mMessageReceiver, new IntentFilter(Constants.NEW_MESSAGE));
    }


    @Override
    protected void onPause() {
        super.onPause();
        active = false;
        unregisterReceiver(mMessageReceiver);
    }



    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mInterlocutor > 0&&intent.getIntExtra("user_id",0)==mInterlocutor) {
                PAGE=0;
                messageActivityPresenter.getMessages(getJsonObject(), true);
            }
        }
    };
    private JsonObject getJsonObject(){
      return JsonObjBody.getMessages(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), AppState.getInstance(App.getInstance()).getString(AppState.Key.LANG_APP), mInterlocutor, mShopId, mFromShop,  PAGE, PAGE_SIZE);
    }
}
