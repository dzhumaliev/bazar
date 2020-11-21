package solutions.isky.gaurangarevolution.presentation.ui.login.fragments;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RSRuntimeException;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.FastBlur;
import solutions.isky.gaurangarevolution.domain.login.transformations.internal.RSBlur;
import solutions.isky.gaurangarevolution.presentation.ui.common.BackButtonListener;
import solutions.isky.gaurangarevolution.presentation.view.MyCustomEditText;

public class LoginFragment extends MvpAppCompatFragment implements BackButtonListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_fon_icon)
    ImageView img_fon_icon;
    @BindView(R.id.img_fon_icon2)
    ImageView img_fon_icon2;
    @BindView(R.id.et_login)
    MyCustomEditText et_login;
    @BindView(R.id.context_all_login)
    ConstraintLayout mConstraintLayout;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        handler = new Handler();
        handler.postDelayed(() -> {
            setAnim();
            initBlurEffect();
        }, 200);

    }

    private void setAnim() {
        ConstraintSet mConstraintEnd = new ConstraintSet();
        mConstraintEnd.clone(mConstraintLayout);
        Transition transition = new AutoTransition();
        transition.setDuration(700);
        mConstraintEnd.setVisibility(R.id.linearLayout, ConstraintSet.VISIBLE);
        //mConstraintEnd.constrainHeight(R.id.linearLayout,ConstraintSet.WRAP_CONTENT);
        TransitionManager.beginDelayedTransition(mConstraintLayout, transition);
        mConstraintEnd.applyTo(mConstraintLayout);
    }

    private void initBlurEffect() {
        new Thread(() -> {
            img_fon_icon.setDrawingCacheEnabled(true);
            Bitmap bitmap = img_fon_icon.getDrawingCache();
            int radius = 25;
            Bitmap blurred;//second parametre is radius
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                try {
                    blurred = RSBlur.blur(getActivity(), bitmap, radius);
                } catch (RSRuntimeException e) {
                    blurred = FastBlur.blur(bitmap, 3, true);
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
        img_fon_icon2.animate().alpha(1.0f).setDuration(700);
        img_fon_icon.animate().alpha(0.0f).setDuration(700);

    }

    @Override
    public boolean onBackPressed() {
       // presenter.onBackCommandClick();
        return true;
    }
}
