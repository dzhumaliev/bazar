package solutions.isky.gaurangarevolution.presentation.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.vk.sdk.VKSdk;

import org.json.JSONException;
import org.json.JSONObject;

import ru.ok.android.sdk.Odnoklassniki;
import ru.ok.android.sdk.OkAuthListener;
import ru.ok.android.sdk.OkListener;
import ru.ok.android.sdk.util.OkAuthType;
import ru.ok.android.sdk.util.OkScope;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.view.CircleTransform;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class OKLoginActivity extends FragmentActivity {
    private boolean isResumed = false;
    protected static final String APP_ID = "1265936384";
    protected static final String APP_KEY = "CBAJKFHMEBABABABA";
    protected static final String REDIRECT_URL = "okauth://ok1265936384";
    private String mToken = "";
    private String mUserID = "";
    protected static Odnoklassniki odnoklassniki;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_vk);

        odnoklassniki = Odnoklassniki.createInstance(this, APP_ID, APP_KEY);
        odnoklassniki.checkValidTokens(new OkListener() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.d("checkValidTokens", json.toString());
                try {
                    Log.d("getCurrentUser", json.toString());
                    mToken=json.getString("access_token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showLogout();
            }

            @Override
            public void onError(String error) {
                Log.d("checkValidTokens", "error= " + error);
                showLogin();
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
        odnoklassniki.requestAuthorization(this, REDIRECT_URL, OkAuthType.ANY, OkScope.VALUABLE_ACCESS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;

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
        if (Odnoklassniki.getInstance().isActivityRequestOAuth(requestCode)) {
            Odnoklassniki.getInstance().onAuthActivityResult(requestCode, resultCode, data, getAuthListener());
        } else if (Odnoklassniki.getInstance().isActivityRequestViral(requestCode)) {
            Odnoklassniki.getInstance().onActivityResultResult(requestCode, resultCode, data, getToastListener());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @NonNull
    private OkListener getToastListener() {
        return new OkListener() {
            @Override
            public void onSuccess(final JSONObject json) {
                Toast.makeText(OKLoginActivity.this, json.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(OKLoginActivity.this, String.format("%s: %s", "ошибка", error), Toast.LENGTH_LONG).show();
            }
        };
    }

    /**
     * Creates a listener that is run on OAUTH authorization completion
     */
    @NonNull
    private OkAuthListener getAuthListener() {
        return new OkAuthListener() {
            @Override
            public void onSuccess(final JSONObject json) {
                try {
                    Log.d("getCurrentUser", json.toString());
                    mToken=json.getString("access_token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showLogout();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(OKLoginActivity.this,
                        String.format("%s: %s", "ошибка", error),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(String error) {
                finish();
            }
        };
    }

    public static class LoginFragment extends android.support.v4.app.Fragment {
        public LoginFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_vk_login, container, false);
            v.findViewById(R.id.sign_in_button).setOnClickListener(v1 -> {
                odnoklassniki.requestAuthorization(getActivity(), REDIRECT_URL, OkAuthType.ANY, OkScope.VALUABLE_ACCESS);
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
        Button continue_button;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_logout, container, false);
            Context context = container.getContext();
            handler = new Handler();
            pic_avatar = v.findViewById(R.id.user_pic);
            name_user = v.findViewById(R.id.user_name);
            continue_button = v.findViewById(R.id.continue_button);
            continue_button.setEnabled(false);
            v.findViewById(R.id.continue_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.putExtra("token",((OKLoginActivity) getActivity()).mToken);
                    intent.putExtra("id",((OKLoginActivity) getActivity()).mUserID);
                    getActivity().setResult(RESULT_OK,intent);
                    getActivity().finish();
                }
            });

            v.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    odnoklassniki.clearTokens();
                    ((OKLoginActivity) getActivity()).showLogin();

                }
            });
            odnoklassniki.requestAsync("users.getCurrentUser", null, null, new OkListener() {
                @Override
                public void onSuccess(JSONObject json) {
                    Log.d("getCurrentUser", json.toString());
                    try {
                        String pic = json.getString("pic_3");
                        String name = json.getString("name");
                        ((OKLoginActivity) getActivity()).mUserID=json.getString("uid");
                        if (!TextUtils.isEmpty(name)) {
                            handler.post(() -> {
                                name_user.setText(getString(R.string.welcome) + " " + name);
                            });

                        }
                        GlideApp.with(context)
                                .load(pic)
                                .centerCrop()
                                .transition(withCrossFade())
                                .transform(new CircleTransform(context))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(pic_avatar);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    continue_button.setEnabled(true);
                }

                @Override
                public void onError(String error) {
                    Log.d("getCurrentUser", "error= " + error);
                    continue_button.setEnabled(true);
                }

            });
            return v;
        }
    }


}
