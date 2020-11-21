package solutions.isky.gaurangarevolution.presentation.ui.login.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RSRuntimeException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DetailsSocial;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.FastBlur;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.RSBlur;

public class SocialConfirm extends MvpAppCompatDialogFragment {
    DetailsSocial detailsSocial;
    @BindView(R.id.img_fon_icon)
    ImageView img_fon_icon;
    @BindView(R.id.img_fon_icon2)
    ImageView img_fon_icon2;
    public static SocialConfirm newInstance(DetailsSocial detailsSocial) {
        SocialConfirm frag = new SocialConfirm();
        Bundle args = new Bundle();
        frag.detailsSocial = detailsSocial;
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.social_confirm, container);
        ButterKnife.bind(this,rootView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        new Handler().postDelayed(() -> {
            initBlurEffect();
        }, 400);
        return rootView;
    }
    private void initBlurEffect() {
        new Thread(() -> {
            img_fon_icon.setDrawingCacheEnabled(true);
            Bitmap bitmap = img_fon_icon.getDrawingCache();
            int radius = 20;
            Bitmap blurred;//second parametre is radius
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                try {
                    blurred = RSBlur.blur(getActivity(), bitmap, radius);
                } catch (RSRuntimeException e) {
                    blurred = FastBlur.blur(bitmap, radius/3, true);
                }
            } else {
                blurred = FastBlur.blur(bitmap, radius/3, true);
            }
            Bitmap finalBlurred = blurred;

            img_fon_icon2.post(() -> {
                img_fon_icon2.setAlpha(0.0f);
                img_fon_icon.setAlpha(1.0f);
                img_fon_icon2.setImageBitmap(finalBlurred);
                img_fon_icon2.invalidate();
                //getDialog().getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), finalBlurred));
                //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

            });

            img_fon_icon.animate().alpha(0.0f).setDuration(400);
            img_fon_icon2.animate().alpha(1.0f).setDuration(400);

        }).start();
    }
}
