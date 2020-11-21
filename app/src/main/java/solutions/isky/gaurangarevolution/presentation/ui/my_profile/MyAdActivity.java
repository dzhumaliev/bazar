package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IMyAdCount;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.fragments.MyAdsFragment;
import solutions.isky.gaurangarevolution.presentation.ui.my_shop.MyShop;

public class MyAdActivity extends MvpAppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.btn_my_shop)
    ImageView btn_my_shop;
    MyAdsFragment myActivityAdd;
    MyAdsFragment myNoActivityAdd;
    MyAdsFragment myModeratedAdd;
    final static int REQUEST_INFO_AD = 1479;
    final static int REQUEST_EDIT = 2478;
    private boolean is_shop = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ad);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        if (getIntent().getBooleanExtra("shop_id", false)) {
            setTitle(getString(R.string.my_shop));
            is_shop = true;
        }
        btn_my_shop.setVisibility(is_shop? View.VISIBLE:View.GONE);
        btn_my_shop.setOnClickListener(v -> {
            startActivity(new Intent(this, MyShop.class));
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }

    }

    public void setTitlePages(String active, String no_active, String shop_active, String shop_no_active) {
        if (is_shop) {
            ((SectionsPagerAdapter) mViewPager.getAdapter()).setactiveTitle(shop_active, shop_no_active);
        } else {
            ((SectionsPagerAdapter) mViewPager.getAdapter()).setactiveTitle(active, no_active);
        }

        mViewPager.getAdapter().notifyDataSetChanged();
    }

    public void updata(int isActive) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
       new Handler().postDelayed(() -> {
           mViewPager.setCurrentItem(isActive);
       },100);

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



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private String title_active = getString(R.string.active);
        private String title_no_active = getString(R.string.no_active);
        private String title_moderation = getString(R.string.title_moderation);

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setactiveTitle(String active, String no_active) {
            try {
                if (Integer.parseInt(active) > 0) {
                    title_active = getString(R.string.active) + "(" + active + ")";
                } else {
                    title_active = getString(R.string.active);
                }
                if (Integer.parseInt(no_active) > 0) {
                    title_no_active = getString(R.string.no_active) + "(" + no_active + ")";
                } else {
                    title_no_active = getString(R.string.no_active);
                }
            } catch (Exception e) {
                e.printStackTrace();
                title_active = getString(R.string.active);
                title_no_active = getString(R.string.no_active);
            }

        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                myActivityAdd = MyAdsFragment.newInstance(position, is_shop);
                myActivityAdd.setOnCount(isActive -> {
                    updata(isActive);
                });
//                myActivityAdd.setOnCount(new IMyAdCountA() {
//                    @Override
//                    public void setOnCount(String active,String no_active,String shop_active,String shop_no_active) {
//                        setTitlePages(active, no_active,shop_active,shop_no_active);
//                    }
//
//                    @Override
//                    public void rebild(boolean isActive) {
//                        updata(isActive);
//                    }
//
//                });
                return myActivityAdd;
            } else if (position == 1) {
                myNoActivityAdd = MyAdsFragment.newInstance(position, is_shop);
                myNoActivityAdd.setOnCount(isActive -> {
                    updata(isActive);
                });
//                myNoActivityAdd.setOnCount(new IMyAdCountA() {
//                    @Override
//                    public void setOnCount(String active,String no_active,String shop_active,String shop_no_active) {
//                        setTitlePages(active, no_active,shop_active,shop_no_active);
//                    }
//
//                    @Override
//                    public void rebild(boolean isActive) {
//                        updata(isActive);
//                    }
//
//                });
                return myNoActivityAdd;
            } else if (position == 2) {
                myModeratedAdd = MyAdsFragment.newInstance(position, is_shop);
                myModeratedAdd.setOnCount(isActive -> {
                    updata(isActive);
                });
//                myModeratedAdd.setOnCount(new IMyAdCountA() {
//                    @Override
//                    public void setOnCount(String active,String no_active,String shop_active,String shop_no_active) {
//                        setTitlePages(active, no_active,shop_active,shop_no_active);
//                    }
//
//                    @Override
//                    public void rebild(boolean isActive) {
//                        updata(isActive);
//                    }
//
//                });
                return myModeratedAdd;
            }
            return MyAdsFragment.newInstance(position, is_shop);
        }

        @Override
        public int getCount() {
            return AppState.getInstance(MyAdActivity.this).getInteger(AppState.Key.PREMODERATION) == 1 ? 3 : 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return title_active;
                case 1:
                    return title_no_active;
                case 2:
                    return title_moderation;
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_INFO_AD:
                updata(0);
                break;
            case REQUEST_EDIT:
                if(resultCode==RESULT_OK){
                    updata(0);
                }
                break;
        }
    }
}
