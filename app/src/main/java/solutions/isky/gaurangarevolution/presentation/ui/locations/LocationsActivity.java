package solutions.isky.gaurangarevolution.presentation.ui.locations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.JsonObject;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.event.GetListLocation;
import solutions.isky.gaurangarevolution.data.models.ItemLocation;
import solutions.isky.gaurangarevolution.presentation.mvp.locations.ILocationActivityView;
import solutions.isky.gaurangarevolution.presentation.mvp.locations.LocationActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

public class LocationsActivity extends MvpAppCompatActivity implements ILocationActivityView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_locations)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar;
    FastItemAdapter fastItemAdapter;
    private int mLevel = 1;
    private LinearLayoutManager linearLayoutManager;
    @InjectPresenter
    LocationActivityPresenter locationActivityPresenter;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    List<RestLoc> restLocList;
    int mMark = 0;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    private boolean add_ad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        add_ad = getIntent().getBooleanExtra("add_ad", false);
        restLocList = new ArrayList<>();
        setTitle(getString(R.string.selectLocation));
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        fastItemAdapter = new FastItemAdapter();
        mRecyclerView.setAdapter(fastItemAdapter);
        fastItemAdapter.withOnClickListener((v, adapter, item, position) -> {

            if (mLevel == 1) {
                setTitle(((ItemLocation) item).getTitle());
                mLevel++;
                getLocation(mLevel, ((ItemLocation) item).getId(), getString(R.string.region), true);
                mMark++;
                restLocList.add(new RestLoc(mLevel, ((ItemLocation) item).getId(), getString(R.string.region), true, ((ItemLocation) item).getTitle()));
                rl_title.setTag(item);

            } else if (mLevel == 2) {
                setTitle(((ItemLocation) item).getTitle());
                if (((ItemLocation) item).getFav_city() == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("id_city", ((ItemLocation) item).getId());
                    intent.putExtra("city_name", ((ItemLocation) item).getTitle());
                    Log.d("mCityIdLOC", ((ItemLocation) item).getId() + "");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    mLevel++;
                    rl_title.setTag(item);
                    getLocation(mLevel, ((ItemLocation) item).getId(), getString(R.string.city), false);
                }

                restLocList.add(new RestLoc(mLevel, ((ItemLocation) item).getId(), getString(R.string.city), false, ((ItemLocation) item).getTitle()));
                mMark++;
            } else if (mLevel == 3) {
                Intent intent = new Intent();
                intent.putExtra("id_city", ((ItemLocation) item).getId());
                intent.putExtra("city_name", ((ItemLocation) item).getTitle());
                setResult(RESULT_OK, intent);
                finish();
            }

            return false;
        });
        getLocation(mLevel, 0, getString(R.string.cantry), true);

        restLocList.add(new RestLoc(mLevel, 0, getString(R.string.cantry), true, getString(R.string.selectLocation)));
        rl_title.setOnClickListener(view -> {
            Intent intent = new Intent();
            if(view.getTag()!=null){
                intent.putExtra("id_city", ((ItemLocation) view.getTag()).getId());
                intent.putExtra("city_name", ((ItemLocation) view.getTag()).getTitle());
            }
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void getListLocation(GetListLocation getListLocation) {
        if (getListLocation.getResult() > 0) {
            if (getListLocation.getRegion_list().getRows().size() == 0) {
                fastItemAdapter.clear();
                rl_title.setVisibility(View.GONE);
                tv_empty.setVisibility(View.VISIBLE);
            } else {
                tv_empty.setVisibility(View.GONE);
                if (mLevel == 1) {

                    tvTitle.setText(R.string.selectCantry);
                    if (getListLocation.getRegion_list().getRows().size() == 1) {
                        setTitle(getListLocation.getRegion_list().getRows().get(0).getTitle());
                        mLevel++;
                        rl_title.setTag(getListLocation.getRegion_list().getRows().get(0));
                        getLocation(mLevel, getListLocation.getRegion_list().getRows().get(0).getId(), getString(R.string.region), true);
                        restLocList.clear();
                        restLocList.add(new RestLoc(mLevel, getListLocation.getRegion_list().getRows().get(0).getId(), getString(R.string.region), true, getListLocation.getRegion_list().getRows().get(0).getTitle()));
                        return;
                    } else {
                        if (!add_ad)
                            rl_title.setVisibility(View.VISIBLE);
                      //  rl_title.setTag(new ItemLocation());
                    }
                }
                fastItemAdapter.set(getListLocation.getRegion_list().getRows());
                if (mLevel == 2) {
                    if (!add_ad)
                        rl_title.setVisibility(View.VISIBLE);
                    tvTitle.setText(R.string.selectCantryAll);
                } else {
                    if (mLevel == 3) {
                        if (!add_ad)
                            rl_title.setVisibility(View.VISIBLE);
                        tvTitle.setText(R.string.selectRegionAll);
                    }
                }

            }
        }
    }


    @Override
    public void show_error(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    void getLocation(int level, int pid, String subtitle, boolean isImgArrow) {
        JsonObject object = JsonObjBody.getRegion(level, pid, AppUtils.getLocale(this));
        locationActivityPresenter.getListLocation(object, subtitle, isImgArrow);
    }

    @Override
    public void showProgres() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgres() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoNetworkLayout() {

    }

    private class RestLoc {
        int level;
        int pid;
        String subtitle;
        boolean isImgArrow;
        String title;

        public RestLoc() {
        }

        public RestLoc(int level, int pid, String subtitle, boolean isImgArrow, String title) {
            this.level = level;
            this.pid = pid;
            this.subtitle = subtitle;
            this.isImgArrow = isImgArrow;
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public boolean isImgArrow() {
            return isImgArrow;
        }

        public void setImgArrow(boolean imgArrow) {
            isImgArrow = imgArrow;
        }
    }

    @Override
    public void onBackPressed() {
        if (mMark == 0) {
            super.onBackPressed();
        } else {
            if (restLocList != null && restLocList.size() > 0) {
                mMark--;
                setTitle(restLocList.get(mMark).getTitle());
                mLevel = restLocList.get(mMark).getLevel();
                getLocation(restLocList.get(mMark).getLevel(), restLocList.get(mMark).getPid(), restLocList.get(mMark).getSubtitle(), restLocList.get(mMark).isImgArrow());

            } else {
                super.onBackPressed();
            }

        }

    }
}
