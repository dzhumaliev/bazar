package solutions.isky.gaurangarevolution.presentation.mvp.info_ad;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import java.util.Locale;

import solutions.isky.gaurangarevolution.R;

public class CircleIndicator extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mAnimatorResId = me.relex.circleindicator.R.animator.scale_with_alpha;
    private int mAnimatorReverseResId = 0;
    private int mIndicatorBackgroundResId = me.relex.circleindicator.R.drawable.white_radius;
    private int mIndicatorUnselectedBackgroundResId = me.relex.circleindicator.R.drawable.white_radius;
    private Animator mAnimatorOut;
    private Animator mAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private Animator mImmediateAnimatorIn;
    private final static float AGLE=16.2f;
    private boolean up=true;
    private int mLastPosition = -1;

    public CircleIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, me.relex.circleindicator.R.styleable.CircleIndicator);
        mIndicatorWidth =
                typedArray.getDimensionPixelSize(me.relex.circleindicator.R.styleable.CircleIndicator_ci_width, -1);
        mIndicatorHeight =
                typedArray.getDimensionPixelSize(me.relex.circleindicator.R.styleable.CircleIndicator_ci_height, -1);
        mIndicatorMargin =
                typedArray.getDimensionPixelSize(me.relex.circleindicator.R.styleable.CircleIndicator_ci_margin, -1);

        mAnimatorResId = typedArray.getResourceId(me.relex.circleindicator.R.styleable.CircleIndicator_ci_animator,
                me.relex.circleindicator.R.animator.scale_with_alpha);
        mAnimatorReverseResId =
                typedArray.getResourceId(me.relex.circleindicator.R.styleable.CircleIndicator_ci_animator_reverse, 0);
        mIndicatorBackgroundResId =
                typedArray.getResourceId(me.relex.circleindicator.R.styleable.CircleIndicator_ci_drawable,
                        me.relex.circleindicator.R.drawable.white_radius);
        mIndicatorUnselectedBackgroundResId =
                typedArray.getResourceId(me.relex.circleindicator.R.styleable.CircleIndicator_ci_drawable_unselected,
                        mIndicatorBackgroundResId);

        int orientation = typedArray.getInt(me.relex.circleindicator.R.styleable.CircleIndicator_ci_orientation, -1);
        setOrientation(orientation == VERTICAL ? VERTICAL : HORIZONTAL);

        int gravity = typedArray.getInt(me.relex.circleindicator.R.styleable.CircleIndicator_ci_gravity, -1);
        setGravity(gravity >= 0 ? gravity : Gravity.CENTER);

        typedArray.recycle();
    }

    /**
     * Create and configure Indicator in Java code.
     */
    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin) {
        configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin,
                me.relex.circleindicator.R.animator.scale_with_alpha, 0, me.relex.circleindicator.R.drawable.white_radius, me.relex.circleindicator.R.drawable.white_radius);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin,
                                   @AnimatorRes int animatorId, @AnimatorRes int animatorReverseId,
                                   @DrawableRes int indicatorBackgroundId,
                                   @DrawableRes int indicatorUnselectedBackgroundId) {

        mIndicatorWidth = indicatorWidth;
        mIndicatorHeight = indicatorHeight;
        mIndicatorMargin = indicatorMargin;

        mAnimatorResId = animatorId;
        mAnimatorReverseResId = animatorReverseId;
        mIndicatorBackgroundResId = indicatorBackgroundId;
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;

        checkIndicatorConfig(getContext());
    }

    private void checkIndicatorConfig(Context context) {
        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight =
                (mIndicatorHeight < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin =
                (mIndicatorMargin < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;

        mAnimatorResId = (mAnimatorResId == 0) ? me.relex.circleindicator.R.animator.scale_with_alpha : mAnimatorResId;

        mAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut.setDuration(0);

        mAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn.setDuration(0);

        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ? me.relex.circleindicator.R.drawable.white_radius
                : mIndicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId =
                (mIndicatorUnselectedBackgroundResId == 0) ? mIndicatorBackgroundResId
                        : mIndicatorUnselectedBackgroundResId;
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, mAnimatorResId);
    }

    private Animator createAnimatorIn(Context context) {
        Animator animatorIn;
        if (mAnimatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            animatorIn.setInterpolator(new CircleIndicator.ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
        }
        return animatorIn;
    }
    private Animator createAnimatorRotater(Context context) {
        Animator animatorIn;

            animatorIn = AnimatorInflater.loadAnimator(context, R.animator.rotet);

        return animatorIn;
    }
    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.addOnPageChangeListener(mInternalPageChangeListener);
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
        }
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override public void onPageSelected(int position) {

            if (mViewpager.getAdapter() == null || mViewpager.getAdapter().getCount() <= 0) {
                return;
            }

            if (mAnimatorIn.isRunning()) {
                mAnimatorIn.end();
                mAnimatorIn.cancel();
            }

            if (mAnimatorOut.isRunning()) {
                mAnimatorOut.end();
                mAnimatorOut.cancel();
            }

            View currentIndicator;
            if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {
                currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                mAnimatorIn.setTarget(currentIndicator);
                mAnimatorIn.start();
            }

            View selectedIndicator = getChildAt(position+position);
            if (selectedIndicator != null) {
                selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
                mAnimatorOut.setTarget(selectedIndicator);
                mAnimatorOut.start();
            }

            mLastPosition = position+position;
//            if(2*position%4==0){
//                up=true;
//            }else{
//                up=false;
//            }
//            setRotationX(180);

        }

        @Override public void onPageScrollStateChanged(int state) {
        }
    };

    public DataSetObserver getDataSetObserver() {
        return mInternalDataSetObserver;
    }

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override public void onChanged() {
            super.onChanged();
            if (mViewpager == null) {
                return;
            }

            int newCount = mViewpager.getAdapter().getCount();
            int currentCount = getChildCount();

            if ((newCount+newCount-1) == currentCount) {  // No change
                return;
            } else if (mLastPosition < newCount+newCount-1) {
                mLastPosition = mViewpager.getCurrentItem()+mViewpager.getCurrentItem();
            } else {
                mLastPosition = -1;
            }

            createIndicators();
        }
    };

    /**
     * @deprecated User ViewPager addOnPageChangeListener
     */
    @Deprecated public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        mViewpager.removeOnPageChangeListener(onPageChangeListener);
        mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    private void createIndicators() {
        removeAllViews();

        int count = mViewpager.getAdapter().getCount();
        if (count <= 0) {
            return;
        }


        int currentItem = mViewpager.getCurrentItem()+mViewpager.getCurrentItem();
        int orientation = getOrientation();

        for (int i = 0; i < count+count-1; i++) {
            if(i% 2 == 0){
                if (currentItem == i) {
                    addIndicator(orientation, mIndicatorBackgroundResId, mImmediateAnimatorOut,i);
                } else {
                    addIndicator(orientation, mIndicatorUnselectedBackgroundResId,
                            mImmediateAnimatorIn,i);
                }
            }else{
                View liner = new View(getContext());
                liner.setBackgroundResource(R.drawable.divider_indikator);
                if((i+1)%4==0){
                    liner.setRotation(Locale.getDefault().getLanguage().equals("ar")?AGLE :-AGLE);
                }else{
                    liner.setRotation(Locale.getDefault().getLanguage().equals("ar")?-AGLE :AGLE);
                }
                addView(liner);
                LayoutParams lp = (LayoutParams) liner.getLayoutParams();
                lp.height=dip2px(1);
                lp.width=dip2px(18);
                liner.setLayoutParams(lp);
            }

        }
//        setDividerDrawable(getResources().getDrawable(R.drawable.divider));
//        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    }

    private void addIndicator(int orientation, @DrawableRes int backgroundDrawableId,
                              Animator animator, int position) {
        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        addView(Indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();

        if (orientation == HORIZONTAL) {
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
            lp.gravity=(isEven(position))?Gravity.TOP:Gravity.BOTTOM;
        } else {
            lp.topMargin = mIndicatorMargin;
            lp.bottomMargin = mIndicatorMargin;
        }

        Indicator.setLayoutParams(lp);

        animator.setTarget(Indicator);
        animator.start();
    }
    private boolean isEven(int n){
        return (n % 4 == 0);
    }
    private class ReverseInterpolator implements Interpolator {
        @Override public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}