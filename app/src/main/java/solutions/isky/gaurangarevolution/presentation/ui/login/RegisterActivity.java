package solutions.isky.gaurangarevolution.presentation.ui.login;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RSRuntimeException;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.FastBlur;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.RSBlur;

public class RegisterActivity extends MvpAppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_fon_icon)
    ImageView img_fon_icon;
    @BindView(R.id.img_fon_icon2)
    ImageView img_fon_icon2;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        handler = new Handler();
        handler.postDelayed(() -> {
            initBlurEffect();
        }, 100);
    }
    private void initBlurEffect() {
        new Thread(() -> {
            img_fon_icon.setDrawingCacheEnabled(true);
            Bitmap bitmap = img_fon_icon.getDrawingCache();
            int radius = 25;
            Bitmap blurred;//second parametre is radius
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                try {
                    blurred = RSBlur.blur(this, bitmap, radius);
                } catch (RSRuntimeException e) {
                    blurred = FastBlur.blur(bitmap, radius, true);
                }
            } else {
                blurred = FastBlur.blur(bitmap, radius, true);
            }
            Bitmap finalBlurred = blurred;

            img_fon_icon2.post(() -> {
                img_fon_icon2.setAlpha(0.0f);
                img_fon_icon.setAlpha(1.0f);
                img_fon_icon2.setImageBitmap(finalBlurred);
                img_fon_icon2.invalidate();
            });
            showImage();

        }).start();
    }
    public void showImage() {
        img_fon_icon2.animate().alpha(1.0f).setDuration(50);
        img_fon_icon.animate().alpha(0.0f).setDuration(50);

    }
}
