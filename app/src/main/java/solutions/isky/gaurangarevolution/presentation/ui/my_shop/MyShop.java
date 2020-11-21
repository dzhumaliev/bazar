package solutions.isky.gaurangarevolution.presentation.ui.my_shop;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import io.realm.Realm;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.HashMap;
import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.CategoryShop;
import solutions.isky.gaurangarevolution.data.models.Contacts_shop;
import solutions.isky.gaurangarevolution.data.models.CreateShop;
import solutions.isky.gaurangarevolution.data.models.EditShop;
import solutions.isky.gaurangarevolution.data.models.RegionInfo;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.data.models.ShopDataCreate;
import solutions.isky.gaurangarevolution.data.models.ShopDataEdit;
import solutions.isky.gaurangarevolution.data.models.ShopsCategoriesItem;
import solutions.isky.gaurangarevolution.data.models.SocialShop;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.databases.RealmHelper;
import solutions.isky.gaurangarevolution.presentation.mvp.my_shop.IMyShopView;
import solutions.isky.gaurangarevolution.presentation.mvp.my_shop.MyShopPresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.AppState.Key;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.addad.SelectOnMap;
import solutions.isky.gaurangarevolution.presentation.ui.advertising.AbonementShopActivity;
import solutions.isky.gaurangarevolution.presentation.ui.advertising.AdvertisingShopActivity;
import solutions.isky.gaurangarevolution.presentation.ui.category.CategoryShopActivity;
import solutions.isky.gaurangarevolution.presentation.ui.locations.LocationsActivity;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;
import solutions.isky.gaurangarevolution.presentation.view.MyPhonesLayut;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MyShop extends MvpAppCompatActivity implements View.OnClickListener, IMyShopView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.progress_avatar)
    ProgressBar progress_logo;
    final static int REQUEST_ADR_MAP = 11190;
    @BindView(R.id.img_clear_logo)
    ImageView img_clear_logo;
    @BindView(R.id.img_photo_shop)
    ImageView img_photo_shop;
    @BindView(R.id.img_load_logo_shop)
    ImageView img_load_logo_shop;
    final static int REQUEST_CITY_ID = 1118;
    final static int REQUEST_CAT_ID = 8877;
    final static int REQUEST_ADVERTISING = 5577;
    final static int REQUEST_ABONEMENT = 5545;
    @BindView(R.id.btn_create_shop)
    Button btn_save_shop;
    @BindView(R.id.et_city)
    MyCustomEditText et_city;

    @BindView(R.id.et_name_shop)
    MyCustomEditText et_name_shop;
    @BindView(R.id.et_descr)
    MyCustomEditText et_descr_shop;
    @BindView(R.id.et_adress)
    MyCustomEditText et_adress_shop;
    @BindView(R.id.et_icq)
    MyCustomEditText et_icq;
    @BindView(R.id.et_skype)
    MyCustomEditText et_skype;
    @BindView(R.id.et_web_site)
    MyCustomEditText et_web_site;
    @BindView(R.id.et_ok)
    MyCustomEditText et_ok;
    @BindView(R.id.et_fb)
    MyCustomEditText et_fb;
    @BindView(R.id.et_vk)
    MyCustomEditText et_vk;

    @BindView(R.id.view_shop_categ)
    LinearLayout view_shop_categ;
    @BindView(R.id.et_cat)
    Button et_cat;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout tagFlowLayout;

    @BindView(R.id.title_shop_error)
    TextView title_shop_error;
    @BindView(R.id.title_shop)
    TextView title_shop;
    @BindView(R.id.btn_svc_shop)
    Button btn_svc_shop;
    @BindView(R.id.descr_shop_error)
    TextView descr_shop_error;
    @BindView(R.id.descr_shop)
    TextView descr_shop;
    @BindView(R.id.city_shop_error)
    TextView city_shop_error;
    @BindView(R.id.city_shop)
    TextView city_shop;
    @BindView(R.id.my_phone_layout)
    MyPhonesLayut myPhonesLayut;
    @BindView(R.id.view_abonement)
    LinearLayout view_abonement;
    //    private double mLat = 0;
//    private double mLng = 0;
//    private String mCityNAme = "";
//    private String mLogo = "";
    private ShopDataCreate mShopData = new ShopDataCreate();
    @InjectPresenter
    MyShopPresenter myShopPresenter;
    private boolean mShop_create;
    private int mShop_id = 0;
    List<ShopsCategoriesItem> shopsCategoriesItems = new ArrayList<>();
    Realm realm;

    @BindView(R.id.title_abonement)
    TextView title_abonement;
    @BindView(R.id.period_abonement)
    TextView period_abonement;
    @BindView(R.id.ad_availability)
    TextView ad_availability;
    @BindView(R.id.no_abonement)
    TextView no_abonement;
    @BindView(R.id.info_abonement)
    LinearLayout info_abonement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        mShop_create = getIntent().getBooleanExtra("shop_create", false);
        initView();
        tagFlowLayout.setOnTagClickListener((view, position, parent) -> {
            shopsCategoriesItems.remove(position);
            tagFlowLayout.getAdapter().notifyDataChanged();
            init_btn_categ();
            return true;
        });
        final LayoutInflater mInflater = LayoutInflater.from(this);
        tagFlowLayout.setAdapter(new TagAdapter<ShopsCategoriesItem>(
                shopsCategoriesItems) {
            @Override
            public View getView(FlowLayout parent, int position, ShopsCategoriesItem o) {
                View view = mInflater.inflate(R.layout.tv,
                        tagFlowLayout, false);
                TextView tv = view.findViewById(R.id.name_categ);
                tv.setText(o.getTitle());
                return view;
            }
        });
        User user = new Gson()
                .fromJson(UserData.getInstance(this).getString(UserData.Key.USER_DATA_JSON), User.class);
        if (user != null && user.getShop_id() > 0) {
            ShopData shopData = user.getShopData();
            mShop_id = user.getShop_id();
            if (shopData != null) {
                mShopData = new ShopDataCreate(shopData);
                setInitShop(shopData);


            }
        }

    }

    @Override
    protected void onDestroy() {
        if (realm != null) {
            realm.close();
        }
        super.onDestroy();
    }

    private void initView() {

        view_shop_categ.setVisibility(
                (AppState.getInstance(this).getInteger(Key.SHOPS_CATEGORIES_ENABLED) > 0) ? View.VISIBLE
                        : View.GONE);

        myPhonesLayut.setMax_fild(AppState.getInstance(this).getInteger(AppState.Key.PHONES_FILD));
        myPhonesLayut.setData(new ArrayList<>());
        img_clear_logo.setVisibility(View.GONE);
        img_clear_logo.setOnClickListener(this);
        img_load_logo_shop.setOnClickListener(this);
        img_photo_shop.setOnClickListener(this);
        et_city.setOnClickListener(this);
        et_cat.setOnClickListener(this);
        et_adress_shop.setOnClickListener(this);
        btn_save_shop.setOnClickListener(this);
        btn_svc_shop.setOnClickListener(this);
        btn_save_shop
                .setText(mShop_create ? getString(R.string.create_shop) : getString(R.string.edit_ad));
        btn_svc_shop.setVisibility(mShop_create ? View.GONE : View.VISIBLE);
        et_name_shop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                title_shop_error.setVisibility(View.GONE);
                title_shop.setVisibility(View.VISIBLE);
            }
        });
        et_descr_shop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                descr_shop.setVisibility(View.VISIBLE);
                descr_shop_error.setVisibility(View.GONE);
            }
        });
        et_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                city_shop_error.setVisibility(View.GONE);
                city_shop.setVisibility(View.GONE);
            }
        });
        view_abonement.setOnClickListener(this);
        view_abonement.setVisibility((AppState.getInstance(this).getInteger(Key.SHOPS_ABONEMENT) == 1 && !mShop_create) ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == img_clear_logo.getId()) {
            img_clear_logo.setVisibility(View.GONE);
            myShopPresenter.resetAvatar();
        } else if (id == img_load_logo_shop.getId() || id == img_photo_shop.getId()) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setActivityMenuIconColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setActivityTitle(getString(R.string.edit_photo))
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setBorderLineColor(Color.BLUE)
                    .setBorderCornerColor(Color.RED)
                    .setGuidelinesColor(Color.GREEN)
                    .setAspectRatio(200, 80)
//                    .setMaxCropResultSize(400, 400)
//                    .setMinCropResultSize(150, 150)
                    .setCropMenuCropButtonTitle(getString(R.string.submint))
                    .setRequestedSize(240, 96)
                    .setFixAspectRatio(true)
                    //.setCropMenuCropButtonIcon(R.drawable.ic_launcher_logo)
                    .start(this);
        } else if (id == et_city.getId()) {
            KeyboardUtil.hideKeyboard(this);
            startActivityForResult(new Intent(this, LocationsActivity.class).putExtra("add_ad", true),
                    REQUEST_CITY_ID);
        } else if (id == btn_svc_shop.getId()) {
            KeyboardUtil.hideKeyboard(this);
            startActivityForResult(new Intent(this, AdvertisingShopActivity.class),
                    REQUEST_ADVERTISING);
        } else if (id == view_abonement.getId()) {
            KeyboardUtil.hideKeyboard(this);
            startActivityForResult(new Intent(this, AbonementShopActivity.class),
                    REQUEST_ABONEMENT);
        } else if (id == et_cat.getId()) {
            KeyboardUtil.hideKeyboard(this);
            Intent categoryShop = new Intent(this, CategoryShopActivity.class);
            categoryShop.putExtra("categId", 0);
            categoryShop.putExtra("add_ad", true);
            startActivityForResult(categoryShop,
                    REQUEST_CAT_ID);
        } else if (id == btn_save_shop.getId()) {
            KeyboardUtil.hideKeyboard(this);
            if (!myPhonesLayut.getPhones()) {
                Contacts_shop constants_shop = new Contacts_shop();
                constants_shop.setIcq_shop(et_icq.getText().toString().trim());
                constants_shop.setSkype_shop(et_skype.getText().toString().trim());
                mShopData.setContacts_shop(constants_shop);
                ArrayList<SocialShop> socialShops = new ArrayList<>();
                socialShops
                        .add(getSocialShop(Constants.SOCIAL_LINK_FACEBOOK, et_fb.getText().toString().trim()));
                socialShops.add(
                        getSocialShop(Constants.SOCIAL_LINK_ODNOKLASSNIKI, et_ok.getText().toString().trim()));
                socialShops
                        .add(getSocialShop(Constants.SOCIAL_LINK_VKONTAKTE, et_vk.getText().toString().trim()));
                mShopData.setSocialShops(socialShops);
                mShopData.setPhones(myPhonesLayut.stringList());
                mShopData.setTitle(et_name_shop.getText().toString().trim());
                if (TextUtils.isEmpty(et_name_shop.getText().toString().trim())
                        || et_name_shop.getText().toString().trim().length() < 6) {
                    title_shop.setVisibility(View.GONE);
                    title_shop_error.setText(getString(R.string.error_title_shop));
                    title_shop_error.setVisibility(View.VISIBLE);
                    et_name_shop.requestFocus();
                    return;
                }
                mShopData.setDescr(et_descr_shop.getText().toString().trim());
                if (TextUtils.isEmpty(et_descr_shop.getText().toString().trim())
                        || et_descr_shop.getText().toString().trim().length() < 12) {
                    descr_shop.setVisibility(View.GONE);
                    descr_shop_error.setText(getString(R.string.error_descr_shop));
                    descr_shop_error.setVisibility(View.VISIBLE);
                    et_descr_shop.requestFocus();
                    return;
                }
                mShopData.setAddr_addr(et_adress_shop.getText().toString().trim());
                mShopData.setSite(et_web_site.getText().toString().trim());
                mShopData.setCats(getCatsId());
                if (mShopData.getRegion_id() == 0) {
                    city_shop.setVisibility(View.GONE);
                    city_shop_error.setText(getString(R.string.select_city));
                    city_shop_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (mShop_create) {
                    myShopPresenter.createShop(
                            new CreateShop(UserData.getInstance(this).getString(UserData.Key.USER_SESSID),
                                    mShopData));
                } else {
                    myShopPresenter.editShop(
                            new EditShop(UserData.getInstance(this).getString(UserData.Key.USER_SESSID),
                                    new ShopDataEdit(mShopData, mShop_id)));
                }


            }

        } else if (id == et_adress_shop.getId()) {
            KeyboardUtil.hideKeyboard(this);
            startActivityForResult(new Intent(this, SelectOnMap.class), REQUEST_ADR_MAP);
        }
    }

    private List<Integer> getCatsId() {
        List<Integer> integers = new ArrayList<>();
        for (ShopsCategoriesItem item : shopsCategoriesItems) {
            integers.add(item.getId());
        }
        return integers;
    }

    private SocialShop getSocialShop(int idSocial, String link) {
        SocialShop socialShop = new SocialShop();
        socialShop.setId_social(idSocial);
        socialShop.setLink_social(link);
        return socialShop;
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

    private void loadAvatar(String uri, ImageView view) {
        GlideApp.with(this)
                .load(uri)
                .error(R.drawable.ic_placeholder_shop)
                .fallback(R.drawable.ic_placeholder_shop)
                .centerCrop()
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    private void loadAvatar(Uri uri, ImageView view) {
        GlideApp.with(this)
                .load(uri)
                .error(R.drawable.ic_placeholder_shop)
                .fallback(R.drawable.ic_placeholder_shop)
                .centerCrop()
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case REQUEST_CITY_ID:
                    if (data.hasExtra("id_city") && data.hasExtra("city_name")) {
                        try {
                            mShopData.setRegion_id(data.getIntExtra("id_city", 0));
                            et_city.setText(data.getStringExtra("city_name"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CAT_ID:
                    if (data.hasExtra("id_cat") && data.hasExtra("cat_title")) {
                        int cat_new_id = data.getIntExtra("id_cat", 0);
                        ShopsCategoriesItem itemShopCategoryList = new ShopsCategoriesItem(new RealmHelper()
                                .getItemShopCategoryList(realm, cat_new_id));
                        if (itemShopCategoryList != null && !cat_is_contains(cat_new_id)) {
                            shopsCategoriesItems.add(itemShopCategoryList);
                        }
                        tagFlowLayout.getAdapter().notifyDataChanged();
                        init_btn_categ();
                    }

                    break;
                case REQUEST_ADR_MAP:
                    if (data.hasExtra("locationAddress")) {
                        et_adress_shop.setText(data.getStringExtra("locationAddress"));
                    }
                    if (data.hasExtra("lat") && data.hasExtra("lng")) {
                        mShopData.setAddr_lat(data.getDoubleExtra("lat", 0));
                        mShopData.setAddr_lon(data.getDoubleExtra("lng", 0));
                    }
                    break;
                case REQUEST_ABONEMENT:
                    myShopPresenter.getUserInfo();
                    break;
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                setPreview(result.getUri());
                myShopPresenter.sendImages(result.getUri());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void init_btn_categ() {
        if (AppState.getInstance(this).getInteger(Key.SHOPS_CATEGORIES_LIMIT) > 0
                && shopsCategoriesItems.size() >= AppState.getInstance(this)
                .getInteger(Key.SHOPS_CATEGORIES_LIMIT)) {
            et_cat.setEnabled(false);
        } else {
            et_cat.setEnabled(true);
        }
    }

    private boolean cat_is_contains(
            int cat_new_id) {
        for (ShopsCategoriesItem item : shopsCategoriesItems) {
            if (item.getId() == cat_new_id) {
                return true;
            }
        }
        return false;
    }

    private void setPreview(Uri uri_avatar) {
        loadAvatar(uri_avatar, img_photo_shop);
        img_clear_logo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgresAvatar() {
        progress_logo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgresavatar() {
        progress_logo.setVisibility(View.GONE);
    }

    @Override
    public void sendError(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void no_session() {

    }

    @Override
    public void getNewUriLogo(String uri) {
        mShopData.setCompanylogo(uri);
    }

    @Override
    public void resetAvatar(String uri) {
        mShopData.setCompanylogo(uri);
        loadAvatar(uri, img_photo_shop);
    }

    @Override
    public void setInitShop(ShopData shopData) {
        et_city.setText(shopData.getRegion_title());
        et_name_shop.setText(shopData.getTitle());
        et_descr_shop.setText(shopData.getDescr());
        et_adress_shop.setText(shopData.getAddr_addr());
        et_icq.setText(shopData.getContacts_shop().getIcq_shop());
        et_skype.setText(shopData.getContacts_shop().getSkype_shop());
        et_web_site.setText(shopData.getSite());
        myPhonesLayut.setData(shopData.getPhones());
        if (shopData.getSocialShops() != null && shopData.getSocialShops().size() > 0) {
            for (SocialShop socialShop : shopData.getSocialShops()) {
                if (socialShop.getId_social() == Constants.SOCIAL_LINK_FACEBOOK) {
                    et_fb.setText(socialShop.getLink_social());
                } else if (socialShop.getId_social() == Constants.SOCIAL_LINK_ODNOKLASSNIKI) {
                    et_ok.setText(socialShop.getLink_social());
                } else if (socialShop.getId_social() == Constants.SOCIAL_LINK_VKONTAKTE) {
                    et_vk.setText(socialShop.getLink_social());
                }
            }
        }
        mShopData = new ShopDataCreate(shopData);
        loadAvatar(shopData.getCompanylogo(), img_photo_shop);
        if (TextUtils.isEmpty(shopData.getCompanylogo())) {
            img_clear_logo.setVisibility(View.GONE);
        } else {
            img_clear_logo.setVisibility(View.VISIBLE);
        }
        if (shopData.getCats().size() > 0) {
            shopsCategoriesItems.clear();
            for (CategoryShop categoryShop : shopData.getCats()) {
                if (!cat_is_contains(categoryShop.getId())) {
                    ShopsCategoriesItem item = new ShopsCategoriesItem(categoryShop);
                    shopsCategoriesItems.add(item);
                }
            }
            tagFlowLayout.getAdapter().notifyDataChanged();
            init_btn_categ();
        }
        if (shopData.getSvcAbonement() != null) {
            title_abonement.setText(Html.fromHtml(String.format(getString(R.string.title_abonement), shopData.getSvcAbonement().getTitle())));
            period_abonement.setText(Html.fromHtml(String.format(getString(R.string.period_abonement), shopData.getSvcAbonement().getTermless() == 0 ? shopData.getSvcAbonement().getExpire() : getString(R.string.on_time))));
            ad_availability.setText(Html.fromHtml(String.format(getString(R.string.ad_availability_shop), shopData.getSvcAbonement().getItems_available(), shopData.getSvcAbonement().getItems_total())));
            no_abonement.setVisibility(View.GONE);
            info_abonement.setVisibility(View.VISIBLE);
        } else {
            no_abonement.setVisibility(View.VISIBLE);
            info_abonement.setVisibility(View.GONE);
        }
    }

    @Override
    public void setCityInfo(RegionInfo cityInfo) {
        mShopData.setRegion_id(cityInfo.getCity_id());
        et_city.setText(cityInfo.getCity_title());
    }

    @Override
    public void create_shop(String title) {
        Toast.makeText(this,
                (Html.fromHtml(String.format(getString(R.string.shop_in_moderation), title))),
                Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void edit_shop(String title) {
        Toast.makeText(this,
                (Html.fromHtml(String.format(getString(R.string.shop_in_moderation_edit), title))),
                Toast.LENGTH_LONG).show();
        finish();
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
}
