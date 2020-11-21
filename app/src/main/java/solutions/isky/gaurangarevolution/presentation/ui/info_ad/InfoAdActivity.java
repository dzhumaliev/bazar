package solutions.isky.gaurangarevolution.presentation.ui.info_ad;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mikepenz.materialize.holder.StringHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eightbitlab.com.blurview.BlurView;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetAdInfo;
import solutions.isky.gaurangarevolution.data.models.AdInfo;
import solutions.isky.gaurangarevolution.data.models.AdInfoItem;
import solutions.isky.gaurangarevolution.data.models.Image_AdInfo;
import solutions.isky.gaurangarevolution.data.models.PhoneUser;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.IInfoAdView;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.InfoAdPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.view.DinParamsView;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.view.ExpandableTextView;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.view.SimilarViev;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.adsuser.AllAdsUserActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.LoginActivity;
import solutions.isky.gaurangarevolution.presentation.ui.my_profile.MyAdActivity;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;
import solutions.isky.gaurangarevolution.slider.library.Animations.DescriptionAnimation;
import solutions.isky.gaurangarevolution.slider.library.Indicators.PagerIndicator;
import solutions.isky.gaurangarevolution.slider.library.SliderLayout;
import solutions.isky.gaurangarevolution.slider.library.SliderTypes.BaseSliderView;
import solutions.isky.gaurangarevolution.slider.library.SliderTypes.DefaultSliderView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class InfoAdActivity extends MvpAppCompatActivity implements IInfoAdView, OnMapReadyCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    public static final String ID_ITEM = "id_item";
    public static final String POSITION_ITEM = "position_item";
    public static final String STARRED = "starred";
    public static final String USER_DATA = "user_data";
    private int mIdPost;
    private List<PhoneUser> mPhoneUser;
    @InjectPresenter
    InfoAdPresenter infoAdPresente;
    @BindView(R.id.din_params)
    DinParamsView dinParamsView;
    @BindView(R.id.rltopsl)
    RelativeLayout rlSlider;
    @BindView(R.id.tvPrice)
    TextView text_price;
    @BindView(R.id.tvTitle)
    TextView text_title;
    @BindView(R.id.tvDescr)
    ExpandableTextView tv_descr;

    @BindView(R.id.blurView_price)
    BlurView priceBlurView;
    @BindView(R.id.blurView_top)
    BlurView topBlurView;
    @BindView(R.id.blurView_map)
    BlurView blurView_map;
    @BindView(R.id.root_view)
    RelativeLayout root_view;
    @BindView(R.id.tvLocation)
    TextView tv_location;
    @BindView(R.id.ll_loc)
    LinearLayout ll_loc;
    @BindView(R.id.pic_map)
    View pic_map;
    @BindView(R.id.rl_block_map)
    RelativeLayout rl_block_map;
    GoogleMap mMap;
    @BindView(R.id.tvNameUser)
    TextView tv_nameUser;
    @BindView(R.id.img_photo_user)
    ImageView img_photo_user;
    @BindView(R.id.tvViewsAd)
    TextView tv_views_ad;
    @BindView(R.id.tvIdAd)
    TextView tv_id_ad;
    @BindView(R.id.btn_complaint)
    Button btn_complaint;
    @BindView(R.id.similar_ads)
    SimilarViev similarViev;
    private String[] mImg_url;
    @BindView(R.id.btn_map)
    Button btn_map;
    private double mLat = 0;
    private double mLng = 0;
    private String titleMap;
    @BindView(R.id.rl_similar)
    RelativeLayout rl_similar;

    @BindView(R.id.rltopsl1)
    RelativeLayout rltopsl1;
    @BindView(R.id.imageButton)
    ImageButton btn_phone;
    @BindView(R.id.btn_fav)
    ImageView btn_fav;
    @BindView(R.id.btn_share)
    ImageView btn_share;
    @BindView(R.id.ib_SendMess)
    Button ib_SendMess;
    @BindView(R.id.et_text_mess)
    EditText et_text_mess;

    //    @BindView(R.id.ll_btns_ad)
//    LinearLayout ll_btns_ad;
    private int fav_state = 0;
    private int position = 0;
    private int id_itemAd = 0;
    @BindView(R.id.btn_user_ads)
    Button btn_user_ads;
    private int mUser_id = 0;
    private int mShop_id = 0;
    @BindView(R.id.tv_company)
    TextView tv_company;
    User mUser_data;
    @BindView(R.id.tvAdress)
    TextView tvAdress;
    //    @BindView(R.id.ll_up_ad)
//    LinearLayout ll_up_ad;
//    @BindView(R.id.btn_up_ad)
//    ImageButton btn_up_ad;
//    @BindView(R.id.tv_up_ad)
//    TextView tv_up_ad;
//
//    @BindView(R.id.ll_up_adve)
//    LinearLayout ll_up_adve;
//    @BindView(R.id.btn_up_adve)
//    ImageButton btn_up_adve;
//    @BindView(R.id.tv_up_adve)
//    TextView tv_up_adve;
//
//    @BindView(R.id.ll_edit)
//    LinearLayout ll_edit;
//    @BindView(R.id.btn_edit)
//    ImageButton btn_edit;
//    @BindView(R.id.tv_edit)
//    TextView tv_edit;
//
//    @BindView(R.id.ll_deactive)
//    LinearLayout ll_deactive;
//    @BindView(R.id.btn_deactive)
//    ImageButton btn_deactive;
//    @BindView(R.id.tv_deactive)
    TextView tv_deactive;
    Drawable windowBackground;
    @BindView(R.id.content_info)
    NestedScrollView content_info;
    public static String PUBLICATE = "publicate";
    public static String UNPUBLICATE = "unpublicate";
    public static String DELETE = "delete";
    final static int REQUEST_EDIT_POST = 1579;
    private String mShareLink;
    @BindView(R.id.btn_reload)
    ImageView btn_reload;
    @BindView(R.id.container_price)
    LinearLayout container_price;
    @BindView(R.id.view_no_internet)
    View view_no_internet;

    @Override
    public void onBackPressed() {
        KeyboardUtil.hideKeyboard(this);
        Intent intent = new Intent();
        intent.putExtra(POSITION_ITEM, position);
        intent.putExtra(STARRED, fav_state == 1);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        KeyboardUtil.hideKeyboard(this);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_info_ad);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("");
        mPhoneUser = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        id_itemAd = getIntent().getIntExtra(ID_ITEM, 0);
        position = getIntent().getIntExtra(POSITION_ITEM, 0);

        if (id_itemAd > 0) {
            JsonObject infoPost = new JsonObjBody().getAdInfo(id_itemAd, AppUtils.getLocale(this));
            infoAdPresente.setId(id_itemAd);
            // getInfoAdPresente().getInfoPost(infoPost);
        } else {
            Toast.makeText(this, R.string.error_retrieving_data, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btn_map.setOnClickListener(view -> {
            if (mLat > 0 && mLng > 0) {
                infoAdPresente.openMap(InfoAdActivity.this, mLat, mLng, titleMap);
            }
        });

        btn_phone.setOnClickListener(view -> {
            if (mIdPost != 0) {
                JsonObject object = JsonObjBody.sendClickPhone(mIdPost);
                infoAdPresente.sendClickPhone(object);
            }
            if (mPhoneUser.size() > 0) {
                if (mPhoneUser.size() > 1) {
                    FragmentManager fm = getSupportFragmentManager();
                    PhonesDialogFragment phonesDialogFragment = PhonesDialogFragment.newInstance(mPhoneUser,null, new PhonesDialogFragment.PhonesCauseDialogListener() {
                        @Override
                        public void onFinishEditDialog(PhoneUser phoneUser) {
                            try {
                                Intent phone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneUser.getV()));
                                startActivity(phone);
                            } catch (Exception e) {
                                e.printStackTrace();
                                showNumberPhone(phoneUser.getV());
                            }
                        }
                    });
                    phonesDialogFragment.show(fm, "fragment_phones");
                } else {
                    try {
                        Intent phone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mPhoneUser.get(0).getV()));
                        startActivity(phone);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showNumberPhone(mPhoneUser.get(0).getV());
                    }
                }


            }
        });
        btn_fav.setOnClickListener(view -> {
            if (AppState.getInstance(this).getBoolean(AppState.Key.LOGGED_IN)) {
                JsonObject jsonObject = JsonObjBody.update_fav(UserData.getInstance(this).getString(UserData.Key.USER_SESSID), infoAdPresente.getmId(), fav_state == 1);
                infoAdPresente.update_fav_list(jsonObject, fav_state == 0);
            } else {
                startActivity(new Intent(this, LoginActivity.class).putExtra("title", getString(R.string.sign_in_to_submit_an_fav)));
            }
        });
        btn_share.setOnClickListener(v -> {
            if (mShareLink != null) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mShareLink);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_gl)));
            }
        });
        btn_user_ads.setOnClickListener(view -> {
            if (mShop_id > 0) {
                User user = new Gson().fromJson(UserData.getInstance(this).getString(UserData.Key.USER_DATA_JSON), User.class);
                if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN) && user != null && mShop_id == user.getShop_id()) {
                    startActivity(new Intent(this, MyAdActivity.class).putExtra("shop_id", true));
                    finish();
                } else {
                       startActivity(new Intent(this, AllAdsUserActivity.class).putExtra("shop_id", mShop_id).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }
            } else {
                if (mUser_id > 0) {
                    if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN) && mUser_id == UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID)) {
                        startActivity(new Intent(this, MyAdActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(this, AllAdsUserActivity.class).putExtra("user_data", mUser_data).putExtra("user_id", mUser_id).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    }
                }
            }

        });
        ib_SendMess.setEnabled(false);
        et_text_mess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_text_mess.getText().toString().length() > 1) {
                    ib_SendMess.setEnabled(true);
                } else {
                    ib_SendMess.setEnabled(false);
                }
            }
        });
        ib_SendMess.setOnClickListener(view -> {

            KeyboardUtil.hideKeyboard(this);
            if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN)) {
                if (id_itemAd > 0) {
                    JsonObject jsonObject = JsonObjBody.send_Mess_from_ad(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), et_text_mess.getText().toString().trim(), id_itemAd);
                    infoAdPresente.sendMess(jsonObject);
                    ib_SendMess.setEnabled(false);
                }

            } else {
                startActivityForResult(new Intent(this, LoginActivity.class),1789);
            }
        });
        btn_complaint.setOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            ComplainDialogFragment chooseCauseDialogFragment = ComplainDialogFragment.newInstance((id, inputText) -> {
                if (mIdPost != 0) {
                    infoAdPresente.setComplain(mIdPost, id, inputText);
                }

            });
            chooseCauseDialogFragment.show(fm, "fragment_complain");
        });
        btn_reload.setOnClickListener(v -> {
            if (AppUtils.isConnected()) {
                view_no_internet.setVisibility(View.GONE);
                recreate();
            }
        });
    }

    private void showNumberPhone(String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(phone);
        builder.setPositiveButton(getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        Button yes = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(getResources().getColor(R.color.colorPrimary));
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

    void setViewSlayder(final GetAdInfo getAdInfo) {

        RelativeLayout item_sl = (RelativeLayout) getLayoutInflater().inflate(R.layout.slayder_top, null);
        final SliderLayout mSlider = (SliderLayout) item_sl.findViewById(R.id.slider);
        rlSlider.removeAllViews();
        rlSlider.addView(item_sl);
        Image_AdInfo image_adInfo_one = new Image_AdInfo();
        image_adInfo_one.setPath(getAdInfo.getAdInfoItem().getImgM());
        List<Image_AdInfo> image_adInfos_one = new ArrayList<>();
        image_adInfos_one.add(image_adInfo_one);
        List<Image_AdInfo> image_adInfos = (getAdInfo.getAdInfoItem().getImages().size() > 0) ? getAdInfo.getAdInfoItem().getImages() : image_adInfos_one;

        final int size = image_adInfos.size();
        mImg_url = new String[size];
        for (int i = 0; i < size; i++) {
            String url_im = image_adInfos.get(i).getPath();
            mImg_url[i] = url_im;
            final DefaultSliderView textSliderView = new DefaultSliderView(InfoAdActivity.this);
            textSliderView.image(url_im)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .empty(R.drawable.ic_placeholder_ad)
                    .error(R.drawable.ic_placeholder_ad);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putInt(PictureActivity.EXTRA_ITEM, i);
            mSlider.addSlider(textSliderView);
            int finalI = i;
            textSliderView.setOnSliderClickListener(slider ->
                    startPictureActivity(mImg_url, finalI, getAdInfo.getAdInfoItem().getTitle()));

        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mSlider.setCustomIndicator((PagerIndicator) item_sl.findViewById(R.id.custom_indicator));
        mSlider.setCurrentPosition(0);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        windowBackground = getWindow().getDecorView().getBackground();
        priceBlurView.setupWith(rltopsl1)
                .windowBackground(windowBackground)
                .blurAlgorithm(new SupportRenderScriptBlur(this))
                .blurRadius(3.f)
                .setHasFixedTransformationMatrix(false);
        topBlurView.setupWith(root_view)
                .windowBackground(windowBackground)
                .blurAlgorithm(new SupportRenderScriptBlur(this))
                .blurRadius(3.f)
                .setHasFixedTransformationMatrix(false);

    }

    private void startPictureActivity(String[] s, int item, String title) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, s);
        intent.putExtra(PictureActivity.EXTRA_ITEM, item);
        intent.putExtra(PictureActivity.EXTRA_TITLE, title);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void getInfoPost(GetAdInfo getAdInfo) {
        setViewSlayder(getAdInfo);
        mIdPost = getAdInfo.getAdInfoItem().getId();
        mShareLink = getAdInfo.getAdInfoItem().getLink();
        fav_state = getAdInfo.getAdInfoItem().getFav();
        btn_fav.setImageResource(fav_state == 0 ? R.drawable.ic_star : R.drawable.ic_star_fav);
        if (getAdInfo.getAdInfoItem().getPhones().size() > 0) {
            mPhoneUser = getAdInfo.getAdInfoItem().getPhones();
        }
        //btn_phone.setVisibility((mPhoneUser != null && !TextUtils.isEmpty(mPhoneUser)) ? View.VISIBLE : View.INVISIBLE);
        btn_phone.setEnabled((mPhoneUser.size() > 0 && !TextUtils.isEmpty(mPhoneUser.get(0).getV())) ? true : false);
        if (getAdInfo.getAdInfoItem().getCatProps().size() > 0) {
            dinParamsView.setUpViews(InfoAdActivity.this, getAdInfo.getAdInfoItem().getCatProps());
        }


        setViewInfo(getAdInfo.getAdInfoItem());

    }

    private void setViewInfo(final AdInfoItem info) {
//        if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN)) {
//            if (info.getUserId() == UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID)) {
//                ll_mess_and_phone.setVisibility(View.GONE);
//                ll_btns_ad.setVisibility(View.VISIBLE);
//                setVievBlockEditMyAd(info);
//            }
//        }
        mLat = info.getAddrLat();
        mLng = info.getAddrLon();
        titleMap = info.getTitle();
        tv_company.setVisibility(Integer.parseInt(info.getShopId()) > 0 ? View.VISIBLE : View.GONE);
        StringHolder price = new StringHolder(info.getItem_price_display()), title = new StringHolder(info.getTitle()), data, location = new StringHolder(info.getCityTitle()), nameUser;
        price.applyToOrHide(text_price);

        text_price.setText(AppUtils.getPriceExInfo(info.getPriceEx(), info.getItem_price_display(), info.getPriceMod()));

        text_price.setVisibility(AppUtils.is_hasPrice(info.getPriceEx(), info.getPrice()) ? View.VISIBLE : View.INVISIBLE);
        container_price.setVisibility(text_price.getText().toString().isEmpty()?View.GONE:View.VISIBLE);
        priceBlurView.setVisibility(AppUtils.is_hasPrice(info.getPriceEx(), info.getPrice()) ? View.VISIBLE : View.GONE);
        title.applyToOrHide(text_title);
        //setTitle(info.getTitle().toString());
//        DateTimeFormatter mDateFormatOld = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//        DateTime ord = new DateTime(mDateFormatOld.parseDateTime(info.getModified()));
//        DateTimeFormatter mDateFormat = DateTimeFormat.forPattern("dd MMMM, HH:mm");
        // data = new StringHolder(mDateFormat.print(ord));
        // data.applyToOrHide(text_data);
        // text_data.setText(mDateFormat.print(ord));
        tv_descr.setText(info.getDescr());
        location.applyToOrHide(tv_location);
        tvAdress.setVisibility(TextUtils.isEmpty(info.getAddrAddr())?View.GONE:View.VISIBLE);
        tvAdress.setText(info.getAddrAddr());
        if (mMap != null && (mLat > 0 && mLng > 0)) {
            LatLng item = new LatLng(mLat, mLng);
            mMap.addMarker(new MarkerOptions().position(item));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(item, 11));
            rl_block_map.setVisibility(View.VISIBLE);
            ll_loc.setVisibility(View.GONE);
            pic_map.setVisibility(View.INVISIBLE);
        } else {
            //rl_block_map.setVisibility(View.GONE);
            pic_map.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Drawable windowBackground1 = pic_map.getBackground();
                blurView_map.setupWith(rl_block_map)
                        .windowBackground(windowBackground1)
                        .blurAlgorithm(new SupportRenderScriptBlur(this))
                        .blurRadius(1)
                        .setHasFixedTransformationMatrix(false);
            }, 100);

            if (!TextUtils.isEmpty(location.getText())) {
                ll_loc.setVisibility(View.VISIBLE);
            }
        }
        mUser_id = info.getUserId();
        mShop_id = Integer.parseInt(info.getShopId());
        String name = info.getName();


        if (Integer.parseInt(info.getShopId()) > 0) {

            GlideApp.with(this)
                    .load(info.getShopData().getCompanylogo())
                    .error(R.drawable.ic_no_avatar)
                    .fallback(R.drawable.ic_no_avatar)
                    .centerCrop()
                    .transition(withCrossFade())
                    .transform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_photo_user);
            name = info.getShopData().getTitle();
        } else {
            if (info.getUser() != null) {
                GlideApp.with(this)
                        .load(info.getUser().getAvatar())
                        .error(R.drawable.ic_no_avatar)
                        .fallback(R.drawable.ic_no_avatar)
                        .placeholder(R.drawable.ic_no_avatar)
                        .centerCrop()
                        .transition(withCrossFade())
                        .transform(new CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img_photo_user);
                //img_photo_user.setImageURI(Uri.parse(info.getUser().getAvatar()));
                mUser_data = info.getUser();
            }
            if (name.equalsIgnoreCase("") && info.getUser() != null && info.getUser().getName() != null) {
                name = info.getUser().getName();
            }
        }
        nameUser = new StringHolder(name);
        nameUser.applyToOrHide(tv_nameUser);

        tv_id_ad.setText(getString(R.string.id_post) +" "+ info.getId());
        tv_views_ad.setText(getString(R.string.views) + " " + info.getViewsTotal());
        new Handler().postDelayed(() -> setsimilar(info.getSimilar().getAdInfos()), 500);
        //setsimilar(info.getSimilar().getAdInfos());
      visibility_block_mess();

    }
void visibility_block_mess(){
  if (mShop_id > 0) {
    User user = new Gson().fromJson(UserData.getInstance(this).getString(UserData.Key.USER_DATA_JSON), User.class);
    if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN) && user != null && mShop_id == user.getShop_id()) {
      ib_SendMess.setVisibility(View.GONE);
      et_text_mess.setVisibility(View.GONE);
    } else {
      ib_SendMess.setVisibility(View.VISIBLE);
      et_text_mess.setVisibility(View.VISIBLE);
    }
  } else {
    if (mUser_id > 0) {
      if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN) && mUser_id == UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID)) {
        ib_SendMess.setVisibility(View.GONE);
        et_text_mess.setVisibility(View.GONE);
      } else {
        ib_SendMess.setVisibility(View.VISIBLE);
        et_text_mess.setVisibility(View.VISIBLE);
      }
    }
  }
}
    void setsimilar(List<AdInfo> prodAdInfos) {
        if (prodAdInfos.size() == 0) {
            findViewById(R.id.rl_similar).setVisibility(View.GONE);
            return;
        } else {
            findViewById(R.id.rl_similar).setVisibility(View.VISIBLE);
        }
        similarViev.setUpViews(this, prodAdInfos, clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(((AdInfo) v.getTag()).getId());

        }
    };

    private void startActivity(int id) {
        Intent intent = newIntent(id);
        startActivity(intent);
        finish();
    }

    public Intent newIntent(int id) {
        Intent intent = new Intent(this, InfoAdActivity.class);
        intent.putExtra(InfoAdActivity.ID_ITEM, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void show_content() {
        root_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide_content() {

    }

    @Override
    public void show_error(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMap(Intent iMap) {
        startActivity(iMap);
    }

    @Override
    public void no_session(String text) {

    }

    @Override
    public void update_favorite(boolean ia_ad_delete) {
        fav_state = ia_ad_delete ? 1 : 0;
        btn_fav.setImageResource(ia_ad_delete ? R.drawable.ic_star_fav : R.drawable.ic_star);
        showToast((ia_ad_delete) ? getString(R.string.add_fav_ad) : getString(R.string.delete_fav_ad));
    }

    void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void edit_ad(AdInfoItem adInfoItem) {

    }

    @Override
    public void deactive_ad(AdInfoItem adInfoItem) {

    }

    @Override
    public void delete() {

    }

    @Override
    public void unpublic(AdInfoItem adInfoItem) {

    }

    @Override
    public void publicate(AdInfoItem adInfoItem) {

    }

    @Override
    public void freeup(AdInfoItem adInfoItem) {

    }

    @Override
    public void complain_confirm() {
        FragmentManager fm = getSupportFragmentManager();
        ConfirmComplainDialogFragment confirmComplainDialogFragment = ConfirmComplainDialogFragment.newInstance();
        confirmComplainDialogFragment.show(fm, "fragment_confirm");
    }

    @Override
    public void message_send() {
        et_text_mess.setText("");

    }

    @Override
    public void enabledBtnSend() {
        ib_SendMess.setEnabled(true);
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
        view_no_internet.setVisibility(View.VISIBLE);
    }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.d("onActivityResult", "resultCode= " + resultCode + ", requestCode= " + requestCode);
    switch (requestCode) {
      case 1789:
       visibility_block_mess();

        break;

    }

  }
}
