package solutions.isky.gaurangarevolution.presentation.ui.info_ad;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.CircleIndicator;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.adapter.DraweePagerAdapter;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.adapter.MultiTouchViewPager;

public class PictureActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.view_pager)
    MultiTouchViewPager viewPager;
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_ITEM = "item";
    public static final String EXTRA_TITLE = "title";
    String[] mImageUrl;
    int item;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_picture);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        parseIntent();
        viewPager.setAdapter(new DraweePagerAdapter(mImageUrl));
        indicator.setViewPager(viewPager);
        viewPager.setCurrentItem(item);
        setTitle("");
    }
    private void parseIntent() {
        mImageUrl = getIntent().getStringArrayExtra(EXTRA_IMAGE_URL);
        item=getIntent().getIntExtra(EXTRA_ITEM,0);
        //title=getIntent().getStringExtra(EXTRA_TITLE);
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
}