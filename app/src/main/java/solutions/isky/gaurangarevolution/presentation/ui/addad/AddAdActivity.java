package solutions.isky.gaurangarevolution.presentation.ui.addad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.event.GetAdInfo;
import solutions.isky.gaurangarevolution.data.models.CategoryList;
import solutions.isky.gaurangarevolution.data.models.CategoryProp;
import solutions.isky.gaurangarevolution.data.models.Currency;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.data.models.Image_AdInfo;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.data.models.PeriodItem;
import solutions.isky.gaurangarevolution.data.models.PhoneUser;
import solutions.isky.gaurangarevolution.data.models.PriceEx;
import solutions.isky.gaurangarevolution.data.models.PriceSetting;
import solutions.isky.gaurangarevolution.data.models.RegionInfo;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.databases.RealmHelper;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.AddAdPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.FullyGridLayoutManager;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.IAddAdView;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.adapter.GridImageAdapter;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.adapter.PeriodAdapter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.AppState.Key;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.BlockPrice;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.DinParamsAddAdLayout;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.MyFildForAddAd;
import solutions.isky.gaurangarevolution.presentation.ui.addad.custom_view.MyPhonesLayutAdAdd;
import solutions.isky.gaurangarevolution.presentation.ui.category.CategoryActivity;
import solutions.isky.gaurangarevolution.presentation.ui.info_ad.InfoAdActivity;
import solutions.isky.gaurangarevolution.presentation.ui.locations.LocationsActivity;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.VerificationPhoneActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AddAdActivity extends MvpAppCompatActivity implements IAddAdView {

    private final static String TAG = "AddAdActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    FullyGridLayoutManager manager;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.block_price)
    BlockPrice block_price;
    @BindView(R.id.fild_category)
    MyFildForAddAd fild_category;
    @BindView(R.id.fild_locations_adr)
    MyFildForAddAd fild_locations_adr;

    @BindView(R.id.fild_title)
    MyFildForAddAd fild_title;
    @BindView(R.id.fild_description)
    MyFildForAddAd fild_description;
    @BindView(R.id.is_shop)
    CheckBox is_shop;
    @BindView(R.id.ll_shop)
    LinearLayout ll_shop;
    @BindView(R.id.logo_shop)
    ImageView logo_shop;
    @BindView(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.phoness_ll)
    MyPhonesLayutAdAdd myPhonesLayutAdAdd;
    @BindView(R.id.dinamic_params)
    DinParamsAddAdLayout dinamic_params;
    @BindView(R.id.fild_name)
    MyFildForAddAd fild_name;
    @BindView(R.id.fild_locations)
    MyFildForAddAd fild_locations;
    @BindView(R.id.progressBar4)
    ProgressBar progressBar;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int mMax_photo;
    @BindView(R.id.btn_add_new_ad)
    Button btn_add_ad;
    @BindView(R.id.spinner_period)
    Spinner spinner_period;
    @BindView(R.id.ll_period)
    LinearLayout ll_period;
    PeriodAdapter periodAdapter;
    @InjectPresenter
    AddAdPresenter addAdPresenter;
    private boolean mIsNeedAdress;
    private int mId;
    private boolean mIsPrice;
    private PriceSetting mPrice_sett;
    final static int REQUEST_CITY_ID = 11180;
    final static int REQUEST_ADR_MAP = 11190;
    final static int REQUEST_CATEGORY_ID = 11170;
    final static int REQUEST_ADD_PHOTO = 11150;
    final static int REQUEST_PHONE_CONFIRM = 11220;
    private int mCityId = 0;
    private String mCityNAme;
    private int mCategId = -1;
    private String mCategTitle;
    private int mPrice_curr;
    private String mPrice;
    private JsonArray images;
    private double mLat = 0;
    private double mLng = 0;
    Realm realm;
    private List<CategoryProp> mCatProps = new ArrayList<>();
    private List<Image_AdInfo> mImage_adInfos = new ArrayList<>();
    private int mTpl_title_enabled = 0;


    @Override
    protected void onDestroy() {
        if (realm != null) {
            realm.close();
        }
        super.onDestroy();
        Log.d(TAG, "onDestroy");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_add_ad);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }


        realm = Realm.getDefaultInstance();
        manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        mId = getIntent().getIntExtra(InfoAdActivity.ID_ITEM, 0);
        mMax_photo = 8;
        mIsNeedAdress = false;
        mMax_photo = 8;
        initView();
        adapter.setSelectMax(mMax_photo);
        recyclerView.setAdapter(adapter);
        fild_category.setOnClickTextFild(() -> {
            KeyboardUtil.hideKeyboard(this);
            Intent categoryActivity = new Intent(AddAdActivity.this, CategoryActivity.class);
            if (mCategId > 0) {
                categoryActivity.putExtra("categId", mCategId);
            }
            categoryActivity.putExtra("add_ad", true);
            startActivityForResult(categoryActivity, REQUEST_CATEGORY_ID);
        });
        fild_locations.setOnClickTextFild(() -> {
            KeyboardUtil.hideKeyboard(this);
            startActivityForResult(
                    new Intent(AddAdActivity.this, LocationsActivity.class).putExtra("add_ad", true),
                    REQUEST_CITY_ID);
        });
        fild_locations_adr.setOnClickTextFild(() -> {
            KeyboardUtil.hideKeyboard(this);
            startActivityForResult(new Intent(AddAdActivity.this, SelectOnMap.class), REQUEST_ADR_MAP);
        });
        if (mId > 0) {
            btn_add_ad.setText(getString(R.string.edit_ad));
            JsonObject infoPost = JsonObjBody.getAdInfo(mId, AppUtils.getLocale(this));
            addAdPresenter.getInfoPost(infoPost, true);
        }
        if (AppState.getInstance(this).getInteger(Key.PERIOD_ENABLED) == 1 && mId < 1) {
            ll_period.setVisibility(View.VISIBLE);
            addAdPresenter.initPeriod();
        } else {
            ll_period.setVisibility(View.GONE);
        }

        btn_add_ad.setOnClickListener(view -> {

            KeyboardUtil.hideKeyboard(this);
            String title = fild_title.getText();
            String descr = fild_description.getText();
            String categ = fild_category.getText();
            String location = fild_locations.getText();
            String email = UserData.getInstance(this).getString(UserData.Key.USER_EMAIL);
            int period = 7;
            try {
                period = AppState.getInstance(this).getInteger(Key.PERIOD_ENABLED) == 1 ? ((PeriodItem) spinner_period.getSelectedItem()).getDays() : AppState.getInstance(this).getInteger(Key.PUBLICATION_PERIOD);

            } catch (Exception e) {
            }
            myPhonesLayutAdAdd.getPhones();
            JsonArray phones = myPhonesLayutAdAdd.getPhonesList();
            String adress = fild_locations_adr.getText().trim();
            String name = fild_name.getText();
            mPrice = block_price.getText().trim();
            mPrice_curr = block_price.getCurrencyID();
            images = new JsonArray();
            if (selectList.size() > 0) {
                images = new JsonArray();
                for (LocalMedia itemPhoto : selectList) {
                    images.add(itemPhoto.getNew_path());
                }
            }
            if (is_shop.isChecked()) {
                addAdPresenter.setParamsShop(mTpl_title_enabled, title, descr, categ, location, email, phones, name,
                        (fild_locations_adr.getVisibility() == View.VISIBLE) ? adress : null,
                        dinamic_params, mCategId, mCityId, block_price.getPrice_ex(), mPrice_curr, mPrice,
                        images, mId, mLat, mLng, (int) is_shop.getTag(), period);
            } else {
                addAdPresenter.setParams(mTpl_title_enabled, title, descr, categ, location, email, phones, name,
                        (fild_locations_adr.getVisibility() == View.VISIBLE) ? adress : null,
                        dinamic_params, mCategId, mCityId, block_price.getPrice_ex(), mPrice_curr, mPrice,
                        images, mId, mLat, mLng, period);
            }

        });
//    block_price.setPattern(new int[]{3,3,3,3,3}," ");
    }


    private void initView() {
        mIsPrice = false;
        fild_description.setMaxLines(5);
        User user = new Gson()
                .fromJson(UserData.getInstance(this).getString(UserData.Key.USER_DATA_JSON), User.class);
        if (user != null) {
            myPhonesLayutAdAdd
                    .setMax_fild(AppState.getInstance(this).getInteger(AppState.Key.PHONES_FILD));
            myPhonesLayutAdAdd.setData(user.getPhones());
            fild_name.setText(user.getName());
            if (user.getRegionId() > 0) {
                addAdPresenter.getCityInfo(user.getRegionId());
            }
            if (user.getShop_id() > 0 && mId < 1 && user.getShopData().getStatus() == 1) {
                ll_shop.setVisibility(View.VISIBLE);
                // is_shop.setChecked(true);
                GlideApp.with(this)
                        .load(user.getShopData().getCompanylogo())
                        .error(R.drawable.ic_placeholder_shop)
                        .fallback(R.drawable.ic_placeholder_shop)
                        .centerCrop()
                        .transition(withCrossFade())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(logo_shop);
                is_shop.setTag(user.getShop_id());
            } else {
                ll_shop.setVisibility(View.GONE);
                is_shop.setChecked(false);
                is_shop.setTag(0);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KeyboardUtil.hideKeyboard(this);
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {

            PictureSelector.create(AddAdActivity.this)
                    .openGallery(PictureMimeType
                            .ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(
                            R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(mMax_photo)
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(3)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)
                    .previewImage(false)
                    .previewVideo(false)
                    .enablePreviewAudio(false)
                    .isCamera(true)// Отображать ли кнопку камеры
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(false)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    //.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                    .isGif(false)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// Качество сжатия урожая По умолчанию 100
                    .minimumCompressSize(100)// Меньше чем100kbИзображение не сжато
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            // forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PHONE_CONFIRM) {
            if (resultCode == RESULT_OK) {

            } else {
                finish();
            }
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CITY_ID:
                    if (data.hasExtra("id_city") && data.hasExtra("city_name")) {
                        try {
                            mCityId = data.getIntExtra("id_city", 0);
                            mCityNAme = data.getStringExtra("city_name");
                            fild_locations.setText(mCityNAme);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CATEGORY_ID:
                    block_price.resetBlock();
                    if (data.hasExtra("id_cat")) {
                        mCategId = data.getIntExtra("id_cat", 0);
                        mCategTitle = data.getStringExtra("cat_title");
                        Log.d("mCategTitle", mCategTitle + "");
                        try {
                            if (mCategId == 1) {
                                fild_category.setText("");
                            } else {
                                ItemCategoryList itemCategoryList = new RealmHelper()
                                        .getItemCategoryList(realm, mCategId);
                                fild_category.setText(itemCategoryList.getTitle());
                                JsonObject object = JsonObjBody.getPropsDop(mCategId, AppUtils.getLocale(this));
//                                divider_din_p.setVisibility(View.GONE);
                                dinamic_params.removeAllViews();
                                dinamic_params.setVisibility(View.GONE);
                                mIsPrice = itemCategoryList.getPrice() == 1;
                                block_price.setVisibility((mIsPrice) ? View.VISIBLE : View.GONE);
                                mPrice_sett = itemCategoryList.getPrice_sett();
                                block_price.setSettings(mPrice_sett);
                                mPrice_curr = itemCategoryList.getPrice_sett().getCurr();
                                addAdPresenter.getDinProps(object, true);
                            }
                            // getDinPropses(mCategId, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (data.hasExtra("photos_max_count") && data.hasExtra("addr")) {
                            mMax_photo = data.getIntExtra("photos_max_count", 8);

                            if (selectList.size() > mMax_photo) {
                                for (int i = selectList.size() - 1; i >= mMax_photo; i--) {
                                    selectList.remove(i);
                                }
                            }
                            adapter.setList(selectList);
                            adapter.notifyDataSetChanged();
                            mIsNeedAdress = (data.getIntExtra("addr", 0) == 1);
                            fild_locations_adr.setVisibility(mIsNeedAdress ? View.VISIBLE : View.GONE);
                        }
                    }
                    break;
                case REQUEST_ADR_MAP:
                    if (data.hasExtra("locationAddress")) {
                        fild_locations_adr.setText(data.getStringExtra("locationAddress"));
                    }
                    if (data.hasExtra("lat") && data.hasExtra("lng")) {
                        mLat = data.getDoubleExtra("lat", 0);
                        mLng = data.getDoubleExtra("lng", 0);
                    }
                    break;
//                case REQUEST_ADD_PHOTO:
//
//                    int c = 1;
//                    fastItemAdapter.clear();
//                    //mCheackPhoto.clear();
//                    for (ImageData imageData : GallerySingleton.getInstance().getCheckedPhotos()) {
//                        SekectleItemPhoto sekectleItemPhoto;
//                        if (imageData.getImageURI().contains("http")) {
//                            sekectleItemPhoto = new SekectleItemPhoto().withUri(imageData.getImageURI()).withCount(c).setProgress(false).withNewName(imageData.getImageURI()).withOriginName(imageData.getImageURI());
//                        } else {
//                            sekectleItemPhoto = new SekectleItemPhoto().withUri(imageData.getImageURI()).withCount(c).setProgress(true);
//                        }
//
//                        //mCheackPhoto.add(sekectleItemPhoto);
//                        c++;
//                    }
//                    //addAdPresenter.setSelectPhotoView(mCheackPhoto);
//                    // addAdPresenter.sendImages(mCheackPhoto);
////                    if (mCheackPhoto.size() > 0)
////                        mPhotosRV.scrollToPosition(mCheackPhoto.size() - 1);
//                    break;

                case PictureConfig.CHOOSE_REQUEST:
                    selectList = PictureSelector.obtainMultipleResult(data);

                    for (LocalMedia media : selectList) {
                        if (media.getPath().contains("http")) {
                            media.setNew_path(media.getPath());
                            media.setIs_progress(false);
                        } else {
                            if (!TextUtils.isEmpty(media.getNew_path())) {
                                media.setIs_progress(false);
                            } else {
                                media.setIs_progress(true);
                            }

                        }
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    addAdPresenter.sendImages(selectList);
                    break;

            }
        }

    }

    @Override
    public void sendError(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void image_upload(List<LocalMedia> mCheackPhotos) {
        selectList = mCheackPhotos;
        adapter.setList(selectList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void no_session(String text) {

    }

    @Override
    public void getDinProps(List<DinProps> dinPropses, int title_enabled) {
        mTpl_title_enabled = title_enabled;
        if (title_enabled > 0) {
            fild_title.setEnabledTextPrice(false);
            fild_title.setHintAndNecessarily(getString(R.string.tpl_title_hint), true);
            if (mId == 0) {
                fild_title.setText("");
            }

        } else {      //TODO настройки автогенерации заголовка
            fild_title.setEnabledTextPrice(true);
            fild_title.setHintAndNecessarily(getString(R.string.title_ad), true);
        }

        //divider_din_p.setVisibility((dinPropses.size() > 0) ? View.VISIBLE : View.GONE);
        if (mCatProps.size() > 0) {
            dinamic_params.setUpViews(this, dinPropses, mCatProps);
        } else {
            dinamic_params.setUpViews(this, dinPropses);
        }
    }

    @Override
    public void setCategParam(CategoryList categParam, int pricecur) {

        mCategTitle = categParam.getT();
        fild_category.setText(categParam.getT());
        dinamic_params.removeAllViews();
        dinamic_params.setVisibility(View.GONE);
        mIsPrice = categParam.getPrice() == 1;
        block_price.setVisibility((mIsPrice) ? View.VISIBLE : View.GONE);
        mPrice_sett = categParam.getPrice_sett();
        block_price.setSettings(mPrice_sett);
        mPrice_curr = categParam.getPrice_sett().getCurr();
        mMax_photo = categParam.getPhotos_max_count();
        mIsNeedAdress = (categParam.getAddr() == 1);
        fild_locations_adr.setVisibility(mIsNeedAdress ? View.VISIBLE : View.GONE);
        addAdPresenter.setBlockPrice(pricecur);
    }

    @Override
    public void setCityInfo(RegionInfo cityInfo) {
        mCityId = cityInfo.getCity_id();
        mCityNAme = cityInfo.getCity_title();
        fild_locations.setText(mCityNAme);
    }

    @Override
    public void getInfoPost(GetAdInfo getAdInfo) {
        mCatProps = getAdInfo.getAdInfoItem().getCatProps();
        fild_category.setEnabledTextPrice(false); //TODO надоли блокировать рубрику?
        fild_title.setText(getAdInfo.getAdInfoItem().getTitle());
        fild_description.setText(getAdInfo.getAdInfoItem().getDescr());
        try {
            mCategId = Integer.parseInt(getAdInfo.getAdInfoItem().getCatId());
        } catch (Exception e) {
            mCategId = 0;
            e.printStackTrace();
        }

        mCityId = Integer.parseInt(getAdInfo.getAdInfoItem().getCityId());
        fild_locations.setText(getAdInfo.getAdInfoItem().getCityTitle());
        ArrayList<String> phone = new ArrayList<>();
        try {
            for (PhoneUser phoneUser : getAdInfo.getAdInfoItem().getPhones()) {
                phone.add(phoneUser.getV());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        myPhonesLayutAdAdd.setData(phone);
        fild_name.setText(getAdInfo.getAdInfoItem().getName());
        fild_locations_adr.setVisibility(mIsNeedAdress ? View.VISIBLE : View.GONE);
        fild_locations_adr.setText(getAdInfo.getAdInfoItem().getAddrAddr());
        mLat = getAdInfo.getAdInfoItem().getAddrLat();
        mLng = getAdInfo.getAdInfoItem().getAddrLon();
        block_price
                .setText(getAdInfo.getAdInfoItem().getPrice().replaceAll(" ", ""));
        //addAdPresenter.getCategoryList(new JsonObjBody().getListCateg());
        try {
            mCategId = Integer.parseInt(getAdInfo.getAdInfoItem().getCatId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObject object = JsonObjBody.getPropsDop(mCategId, AppUtils.getLocale(this));
        addAdPresenter.setPriceEx(getAdInfo.getAdInfoItem().getPriceEx());
        addAdPresenter.getDinProps(object, true, true);
        mImage_adInfos = new ArrayList<>();
        mImage_adInfos = getAdInfo.getAdInfoItem().getImages();

        setEditPhoto(mImage_adInfos);
    }

    @Override
    public void setBlockPriceData(PriceEx priceData, int pricecur) {
        block_price.setViewEdit(priceData, pricecur);
    }

    @Override
    public void showErrorTitle(String text) {
        fild_title.setError(text);
        mNestedScrollView.scrollTo(0, 0);
    }

    @Override
    public void showErrorDescr(String text) {
        fild_description.setError(text);
        mNestedScrollView.scrollTo(0, 0);
    }

    @Override
    public void showErrorCateg(String text) {
        fild_category.setError(text);
        mNestedScrollView.scrollTo(0, 0);
    }

    @Override
    public void showErrorLocation(String text) {
        fild_locations.setError(text);
        mNestedScrollView.scrollTo(0, 0);
    }

    @Override
    public void showErrorAdress(String text) {
        fild_locations_adr.setError(text);
        mNestedScrollView.scrollTo(0, 0);
    }

    @Override
    public void showErrorName(String text) {
        fild_name.setError(text);
        mNestedScrollView.scrollTo(0, 0);
    }

    @Override
    public void add_successfully_submitted(String link) {
        Toast.makeText(this, getString(R.string.add_successfully_submitted), Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void add_successfully_edit() {
        Toast.makeText(this, getString(R.string.add_successfully_edit), Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void number_not_verified(User user) {
        if (AppState.getInstance(this).getInteger(Key.USERS_REGISTER_PHONE) > 0) {
            startActivityForResult(
                    new Intent(this, VerificationPhoneActivity.class).putExtra("user_id", user.getId())
                            .putExtra("user_phone", user.getPhoneNumber()).putExtra("change_phone", true),
                    REQUEST_PHONE_CONFIRM);

        }
    }

    @Override
    public void currency_list(List<Currency> currencyList) {
        block_price.setCurencyList(currencyList);
    }

    @Override
    public void setPeriodPublicate(List<PeriodItem> periodPublicate) {
        periodAdapter = new PeriodAdapter(this, periodPublicate);
        spinner_period.setAdapter(periodAdapter);
        int size = periodPublicate.size();
        for (int i = 0; i < size; i++) {
            if (periodPublicate.get(i).getIs_def() == 1) {
                spinner_period.setSelection(i);
            }
        }
    }

    private void setEditPhoto(List<Image_AdInfo> image_adInfos) {

        if (image_adInfos.size() > 0) {
            selectList = new ArrayList<>();
            for (Image_AdInfo image_adInfo : image_adInfos) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(image_adInfo.getPath());
                localMedia.setNew_path(image_adInfo.getPath());
                localMedia.setChecked(true);
                localMedia.setIs_progress(false);
                selectList.add(localMedia);
            }
            adapter.setList(selectList);
            adapter.notifyDataSetChanged();

        }
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
