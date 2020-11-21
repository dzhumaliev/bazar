package solutions.isky.gaurangarevolution.presentation.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;

public class FacebookLoginActivity extends AppCompatActivity {


    private CallbackManager callbackManager;
    @BindView(R.id.user_pic)
    ProfilePictureView profilePictureView;
    @BindView(R.id.user_name)
    TextView userNameView;
    @BindView(R.id._fb_login)
    LoginButton fbLoginButton;
    @BindView(R.id.btn_ok)
    Button btn_ok;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        profilePictureView.setCropped(true);
        btn_ok.setVisibility(isLoggedIn() ? View.VISIBLE : View.GONE);
        btn_ok.setOnClickListener(view -> {
            setResult(RESULT_OK);
            finish();
        });
        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                updateUI();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(final FacebookException exception) {
                exception.printStackTrace();
                Toast.makeText(
                        FacebookLoginActivity.this,
                        R.string.unknown_error,
                        Toast.LENGTH_LONG).show();
            }
        });
        new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    final Profile oldProfile,
                    final Profile currentProfile) {
                updateUI();
            }
        };
        updateUI();
    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void updateUI() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            profilePictureView.setProfileId(profile.getId());
            userNameView
                    .setText(String.format("%s %s", profile.getFirstName(), profile.getLastName()));
        } else {
            profilePictureView.setProfileId(null);
            userNameView.setText(getString(R.string.welcome));
        }
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}