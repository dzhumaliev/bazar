package solutions.isky.gaurangarevolution.presentation.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class VKLoginActivity extends FragmentActivity {
    private boolean isResumed = false;

    /**
     * Scope is set of required permissions for your application
     *
     * @see <a href="https://vk.com/dev/permissions">vk.com api permissions documentation</a>
     */
    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
         //   VKScope.NOHTTPS,
           // VKScope.MESSAGES,
            VKScope.DOCS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_vk);
        if (VKSdk.isLoggedIn()) {
            showLogout();
        } else {
            showLogin();
        }
        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                if (isResumed) {
                    switch (res) {
                        case LoggedOut:
                            //showLogin();
                            finish();
                            break;
                        case LoggedIn:
                            showLogout();
                            break;
                        case Pending:
                            finish();
                            break;
                        case Unknown:
                            finish();
                            break;
                        default:
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void onError(VKError error) {
                finish();
            }
        });

//        String[] fingerprint = VKUtil.getCertificateFingerprint(this, this.getPackageName());
//        Log.d("Fingerprint", fingerprint[0]);
    }

    private void showLogout() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LogoutFragment())
                .commitAllowingStateLoss();
    }

    private void showLogin() {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container, new LoginFragment())
//                .commitAllowingStateLoss();
        VKSdk.login(this, sMyScope);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
//        if (VKSdk.isLoggedIn()) {
//            showLogout();
//        } else {
//            showLogin();
//        }
    }

    @Override
    protected void onPause() {
        isResumed = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(VKError error) {
                if (!TextUtils.isEmpty(error.errorMessage)){
                    Toast.makeText(VKLoginActivity.this, error.errorMessage, Toast.LENGTH_SHORT).show();}else{
                    finish();
                }
            }
        };

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public static class LoginFragment extends android.support.v4.app.Fragment {
        public LoginFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_vk_login, container, false);
            v.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VKSdk.login(getActivity(), sMyScope);
                }
            });
            return v;
        }

    }

    public static class LogoutFragment extends android.support.v4.app.Fragment {
        public LogoutFragment() {

        }

        Handler handler;
        TextView name_user;
        ImageView pic_avatar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_logout, container, false);
            Context context = container.getContext();
            handler = new Handler();
            pic_avatar = v.findViewById(R.id.user_pic);
            name_user = v.findViewById(R.id.user_name);
            v.findViewById(R.id.continue_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().setResult(RESULT_OK);
                    getActivity().finish();
                }
            });

            v.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VKSdk.logout();
                    if (!VKSdk.isLoggedIn()) {
                        ((VKLoginActivity) getActivity()).showLogin();
                    }
                }
            });
//            "id,first_name,last_name,sex,bdate,city,country,photo_50,photo_100," +
//                    "photo_200_orig,photo_200,photo_400_orig,photo_max,photo_max_orig,online," +
//                    "online_mobile,lists,domain,has_mobile,contacts,connections,site,education," +
//                    "universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message," +
//                    "status,last_seen,common_count,relation,relatives,counters"

            VKAccessToken vkAccessToken = VKAccessToken.currentToken();
            if (vkAccessToken != null) {
                VKParameters params = new VKParameters();
                params.put(VKApiConst.FIELDS, "first_name,photo_400_orig");
                VKApi.users().get(params).executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        if (isAdded()) {
                            JSONArray resp = null;
                            try {
                                resp = response.json.getJSONArray("response");
                                JSONObject user = resp.getJSONObject(0);
                                String photo_max_orig_url = user.getString("photo_400_orig");
                                String name = user.getString("first_name");
                                if (!TextUtils.isEmpty(name)) {
                                    handler.post(() -> {
                                        try {
                                            name_user.setText(getString(R.string.welcome) + " " + name);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    });
                                }
                                GlideApp.with(context)
                                        .load(photo_max_orig_url)
                                        .centerCrop()
                                        .transition(withCrossFade())
                                        .transform(new CircleTransform(context))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(pic_avatar);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }
                });
            }

            return v;
        }


    }


}
