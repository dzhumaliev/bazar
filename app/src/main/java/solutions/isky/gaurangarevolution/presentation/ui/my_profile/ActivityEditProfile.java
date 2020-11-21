package solutions.isky.gaurangarevolution.presentation.ui.my_profile;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vk.sdk.VKAccessToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.RegionInfo;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.EditProfilePresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.IEditProfile;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.ui.locations.LocationsActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.FacebookLoginActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.OKLoginActivity;
import solutions.isky.gaurangarevolution.presentation.ui.login.VKLoginActivity;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;
import solutions.isky.gaurangarevolution.presentation.view.MyPhonesLayut;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ActivityEditProfile extends MvpAppCompatActivity implements IEditProfile, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @InjectPresenter
    EditProfilePresenter editProfilePresenter;
    private int mCityId = 0;
    private String mCityNAme = "";
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.et_name)
    MyCustomEditText et_name;
    @BindView(R.id.et_city)
    MyCustomEditText et_city;
    //    @BindView(R.id.et_phone)
//    MyCustomEditText et_phone;
    @BindView(R.id.img_clear_avatar)
    ImageView img_clear_avatar;
    @BindView(R.id.img_photo_user)
    ImageView avatar;
    @BindView(R.id.img_load_avatar)
    ImageView img_load_avatar;
    final static int REQUEST_CITY_ID = 1118;
    final static int REQUEST_AUTORISE_FB = 127;
    final static int REQUEST_AUTH_VK = 135;
    final static int REQUEST_AUTH_OK = 136;
    @BindView(R.id.progress_avatar)
    ProgressBar progress_avatar;
    @BindView(R.id.tv_my_email)
    TextView tv_my_email;
    @BindView(R.id.iv_fc)
    ImageView iv_fb;
    @BindView(R.id.iv_vk)
    ImageView iv_vk;
    @BindView(R.id.iv_ok)
    ImageView iv_ok;
    @BindView(R.id.btn_save_profile)
    Button btn_save_profile;
    @BindView(R.id.et_pass)
    MyCustomEditText et_pass;
    @BindView(R.id.et_email)
    MyCustomEditText et_email;
    @BindView(R.id.my_phone_layout)
    MyPhonesLayut myPhonesLayut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }
        User user = new Gson().fromJson(UserData.getInstance(this).getString(UserData.Key.USER_DATA_JSON), User.class);
        editProfilePresenter.setUser(user);
        img_clear_avatar.setOnClickListener(this);
        avatar.setOnClickListener(this);
        img_load_avatar.setOnClickListener(this);
        et_email.setOnClickListener(this);
        et_city.setOnClickListener(this);
        et_pass.setOnClickListener(this);
        iv_fb.setTag(true);
        iv_ok.setTag(true);
        iv_vk.setTag(true);
        iv_fb.setOnClickListener(this);
        iv_ok.setOnClickListener(this);
        iv_vk.setOnClickListener(this);
        btn_save_profile.setOnClickListener(this);
        initIconSoc();
    }

    private void initIconSoc() {
        iv_fb.setImageResource((Boolean) iv_fb.getTag() ? R.drawable.ic_icon_fb_enabled : R.drawable.ic_icon_fb);
        iv_ok.setImageResource((Boolean) iv_ok.getTag() ? R.drawable.ic_ok_enabled : R.drawable.ic_ok);
        iv_vk.setImageResource((Boolean) iv_vk.getTag() ? R.drawable.ic_vk_enabled : R.drawable.ic_vk);
    }

    @Override
    public void sendError(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void no_session() {

    }

    @Override
    public void setInitUser(User user) {
        iv_fb.setTag(true);
        iv_ok.setTag(true);
        iv_vk.setTag(true);
        if (user != null) {
            mCityId = user.getRegionId();
            et_name.setText(user.getName());
            myPhonesLayut.setMax_fild(AppState.getInstance(this).getInteger(AppState.Key.PHONES_FILD));
            myPhonesLayut.setData(user.getPhones());
            loadAvatar(user.getAvatar(), avatar);
            if (TextUtils.isEmpty(user.getAvatar())) {
                img_clear_avatar.setVisibility(View.GONE);
            } else {
                img_clear_avatar.setVisibility(View.VISIBLE);
            }
            tv_my_email.setText(user.getEmail());

            for (Integer integer : user.getSoc_providers()) {
                if (integer == 2) {
                    iv_fb.setTag(false);
                } else if (integer == 1) {
                    iv_vk.setTag(false);
                } else if (integer == 4) {
                    iv_ok.setTag(false);
                }
            }
            initIconSoc();
        }
    }

    @Override
    public void resetAvatar(String uri) {
        loadAvatar(uri, avatar);
    }

    @Override
    public void setPreview(Uri uri_avatar) {
        loadAvatar(uri_avatar, avatar);
        img_clear_avatar.setVisibility(View.VISIBLE);
    }

    private void loadAvatar(String uri, ImageView view) {
        GlideApp.with(this)
                .load(uri)
                .error(R.drawable.ic_no_avatar)
                .fallback(R.drawable.ic_no_avatar)
                .centerCrop()
                .placeholder(R.drawable.ic_no_avatar)
                .transition(withCrossFade())
                .transform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    private void loadAvatar(Uri uri, ImageView view) {
        GlideApp.with(this)
                .load(uri)
                .error(R.drawable.ic_no_avatar)
                .fallback(R.drawable.ic_no_avatar)
                .centerCrop()
                .placeholder(R.drawable.ic_no_avatar)
                .transition(withCrossFade())
                .transform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    @Override
    public void setCityInfo(RegionInfo cityInfo) {
        et_city.setText(cityInfo.getCity_title());
        mCityId = cityInfo.getCity_id();
    }

    @Override
    public void showProgresAvatar() {
        progress_avatar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgresavatar() {
        progress_avatar.setVisibility(View.GONE);
    }

    @Override
    public void avatar_load() {

    }

    @Override
    public void showErrorPhone(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == img_clear_avatar.getId()) {
            img_clear_avatar.setVisibility(View.GONE);
            editProfilePresenter.resetAvatar();
        } else if (id == img_load_avatar.getId() || id == avatar.getId()) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setActivityMenuIconColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setActivityTitle(getString(R.string.edit_photo))
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setBorderLineColor(Color.BLUE)
                    .setBorderCornerColor(Color.RED)
                    .setGuidelinesColor(Color.GREEN)
//                    .setMaxCropResultSize(400, 400)
//                    .setMinCropResultSize(150, 150)
                    .setCropMenuCropButtonTitle(getString(R.string.submint))
                    .setRequestedSize(400, 400)
                    .setFixAspectRatio(true)
                    //.setCropMenuCropButtonIcon(R.drawable.ic_launcher_logo)
                    .start(this);
        } else if (id == et_city.getId()) {
            KeyboardUtil.hideKeyboard(this);
            startActivityForResult(new Intent(this, LocationsActivity.class).putExtra("add_ad", true), REQUEST_CITY_ID);
        } else if (id == et_pass.getId()) {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        } else if (id == et_email.getId()) {
            startActivity(new Intent(this, ChangeEmailActivity.class));
        } else if (id == iv_fb.getId()) {
            KeyboardUtil.hideKeyboard(this);
            if ((boolean) iv_fb.getTag()) {
                startActivityForResult(new Intent(this, FacebookLoginActivity.class), REQUEST_AUTORISE_FB);
            } else {
                editProfilePresenter.unlink_social(Constants.PROVIDER_FC);
            }

        } else if (id == iv_vk.getId()) {
            KeyboardUtil.hideKeyboard(this);
            if ((boolean) iv_vk.getTag()) {
                startActivityForResult(new Intent(this, VKLoginActivity.class), REQUEST_AUTH_VK);
            } else {
                editProfilePresenter.unlink_social(Constants.PROVIDER_VK);
            }

        } else if (id == iv_ok.getId()) {
            KeyboardUtil.hideKeyboard(this);
            if ((boolean) iv_ok.getTag()) {
                startActivityForResult(new Intent(this, OKLoginActivity.class), REQUEST_AUTH_OK);
            } else {
                editProfilePresenter.unlink_social(Constants.PROVIDER_OK);
            }

        } else if (id == btn_save_profile.getId()) {
            KeyboardUtil.hideKeyboard(this);
            if (!myPhonesLayut.getPhones()) {
                if (TextUtils.isEmpty(et_city.getText().toString().trim())) {
                    mCityId = 0;
                }
                editProfilePresenter.edit_user(et_name.getText().toString().trim(), myPhonesLayut.getPhonesList(), mCityId);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_AUTORISE_FB:
                    AccessToken accesstoken = AccessToken.getCurrentAccessToken();
                    if (accesstoken != null) {
                        editProfilePresenter.bind_social(accesstoken.getUserId(), accesstoken.getToken(), Constants.PROVIDER_FC);
                    }
                    break;
                case REQUEST_AUTH_VK:
                    VKAccessToken vkAccessToken = VKAccessToken.currentToken();
                    if (vkAccessToken != null) {
                        editProfilePresenter.bind_social(vkAccessToken.userId, vkAccessToken.accessToken, Constants.PROVIDER_VK);
                    }
                    break;
                case REQUEST_AUTH_OK:
                    String mTokenOK = data.getStringExtra("token");
                    String mIdOK = data.getStringExtra("id");
                    if (!TextUtils.isEmpty(mTokenOK)) {
                        editProfilePresenter.bind_social(mIdOK, mTokenOK, Constants.PROVIDER_OK);
                    }
                    break;
                case REQUEST_CITY_ID:
                    if (data.hasExtra("id_city") && data.hasExtra("city_name")) {
                        try {
                            mCityId = data.getIntExtra("id_city", 0);
                            mCityNAme = data.getStringExtra("city_name");
                            et_city.setText(mCityNAme);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                editProfilePresenter.setPreview(result);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
