package solutions.isky.gaurangarevolution.presentation.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import ru.yandex.money.android.sdk.Amount;
import ru.yandex.money.android.sdk.Checkout;
import ru.yandex.money.android.sdk.MockConfiguration;
import ru.yandex.money.android.sdk.PaymentMethodType;
import ru.yandex.money.android.sdk.PaymentParameters;
import ru.yandex.money.android.sdk.TestParameters;
import solutions.isky.gaurangarevolution.data.event.BuyAdv;

public class YandexMoneyHelper {
    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;
    private static long lastClickTime;
    private final static long TIME = 800;
    public static final int REQUEST_CODE_TOKENIZE = 214;


    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    private YandexMoneyHelper(Activity activity) {
        this(activity, null);
    }

    private YandexMoneyHelper(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private YandexMoneyHelper(Activity activity, Fragment fragment) {
        mActivity = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    /**
     * Start PictureSelector for Activity.
     *
     * @param activity
     * @return PictureSelector instance.
     */
    public static YandexMoneyHelper create(Activity activity) {
        return new YandexMoneyHelper(activity);
    }

    /**
     * Start PictureSelector for Fragment.
     *
     * @param fragment
     * @return PictureSelector instance.
     */
    public static YandexMoneyHelper create(Fragment fragment) {
        return new YandexMoneyHelper(fragment);
    }
    public void sendPay(BuyAdv.DataObg dataObg) {


        //startActivityForResult(intent, REQUEST_CODE_TOKENIZE);


        if ( !isFastDoubleClick()) {
            Activity activity = getActivity();
            if (activity == null) {
                return;
            }
            Set<PaymentMethodType> paymentMethodTypeList = new HashSet<>();
            // paymentMethodTypeList.add(PaymentMethodType.YANDEX_MONEY);
            paymentMethodTypeList.add(PaymentMethodType.BANK_CARD);
            MockConfiguration mockConfiguration = new MockConfiguration(false,
                    false,
                    1);
            TestParameters testParameters = new TestParameters(true, false, mockConfiguration);
            PaymentParameters paymentParameters = new PaymentParameters(
                    new Amount(new BigDecimal(dataObg.getSum()), Currency.getInstance("RUB")),
                    dataObg.getTargets(),
                    dataObg.getFormcomment(),
                    "068094b6140142e5803816b30e1cc6c5",
                    "12345",
                    paymentMethodTypeList,
                    dataObg.getTargets(),
                    dataObg.getSuccessURL().replaceFirst("http:", "https:")
            );
            Intent intent = Checkout.createTokenizeIntent(activity, paymentParameters, testParameters);
            Fragment fragment = getFragment();
            if (fragment != null) {
                fragment.startActivityForResult(intent, REQUEST_CODE_TOKENIZE);
            } else {
                activity.startActivityForResult(intent, REQUEST_CODE_TOKENIZE);
            }
            activity.overridePendingTransition(com.luck.picture.lib.R.anim.a5, 0);
        }
    }
    @Nullable
    Activity getActivity() {
        return mActivity.get();
    }

    /**
     * @return Fragment.
     */
    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }
}
