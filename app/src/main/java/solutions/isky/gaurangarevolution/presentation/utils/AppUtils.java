package solutions.isky.gaurangarevolution.presentation.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.google.gson.Gson;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.PriceEx;
import solutions.isky.gaurangarevolution.data.models.SettingsApp;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.AppState.Key;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;

/**
 * Created by sergey on 21.03.18.
 */

public class AppUtils {
    public static int convertDPtoPixels(Context context, float dps) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static String getPriceEx(PriceEx priceEx, String price_display) {

        if (priceEx != null && priceEx.getFree() > 0) {
            return App.getInstance().getResources().getString(R.string.free_price);
        } else if (priceEx != null && priceEx.getAgreed() > 0) {
            return App.getInstance().getResources().getString(R.string.contract_price);
        } else if (priceEx != null && priceEx.getExchange() > 0) {
            return App.getInstance().getResources().getString(R.string.exchange);
        }
        return price_display;
    }

    public static String getPriceEx(PriceEx priceEx, String price_display, String mod) {

        if (priceEx != null && priceEx.getFree() > 0) {
            return App.getInstance().getResources().getString(R.string.free_price);
        } else if (priceEx != null && priceEx.getAgreed() > 0) {
            return App.getInstance().getResources().getString(R.string.contract_price);
        } else if (priceEx != null && priceEx.getExchange() > 0) {
            return App.getInstance().getResources().getString(R.string.exchange);
        } else if (priceEx != null && priceEx.getMod() > 0) {
            return mod;
        }
        return price_display;
    }

    public static String getPriceExInfo(PriceEx priceEx, String price_display, String mod) {
        String price = price_display;
        if (priceEx != null && priceEx.getFree() > 0) {
            price = App.getInstance().getResources().getString(R.string.free_price);
        } else if (priceEx != null && priceEx.getAgreed() > 0) {
            price = App.getInstance().getResources().getString(R.string.contract_price);
        } else if (priceEx != null && priceEx.getExchange() > 0) {
            price = App.getInstance().getResources().getString(R.string.exchange);
        }
        if (priceEx != null && priceEx.getMod() > 0) {
            price = price + "\n" + (TextUtils.isEmpty(mod) ? App.getInstance().getResources().getString(R.string.aukcion) : mod);
        }
        return price;
    }

    public static boolean is_hasPrice(PriceEx priceEx, String price) {
        boolean is_view_pr = false;
        if (priceEx != null && priceEx.getFree() > 0) {
            is_view_pr = true;
        } else if (priceEx != null && priceEx.getAgreed() > 0) {
            is_view_pr = true;
        } else if (priceEx != null && priceEx.getExchange() > 0) {
            is_view_pr = true;
        } else if (priceEx != null && priceEx.getMod() > 0) {
            is_view_pr = true;
        }
        if (!is_view_pr && price == null) {
            is_view_pr = false;
        } else {
            is_view_pr = true;
        }
        // если надо вообще не показать цену при цене ==0
//        if (!is_view_pr && "0.00".equalsIgnoreCase(price)) {
//            is_view_pr = false;
//        } else {
//            is_view_pr = true;
//        }
        return is_view_pr;
    }

    public static String getLocale(Context context) {

        return AppState.getInstance(context).getString(AppState.Key.LANG_APP);
    }

    public static void setUserData(User user) {
        UserData userData = UserData.getInstance(App.getInstance());
        userData.setString(UserData.Key.USER_EMAIL, user.getEmail());
        userData.setInteger(UserData.Key.USER_ID, user.getId());
        userData.setString(UserData.Key.USER_DATA_JSON, new Gson().toJson(user).toString());
    }

    public static void setSettings(SettingsApp settings) {
        if (settings != null) {
            AppState appState = AppState.getInstance(App.getInstance());
            appState.setInteger(AppState.Key.PHONES_FILD, settings.getPhones_fild());
            appState.setInteger(AppState.Key.CURRENCY_DEFAUL, settings.getCurrency_defaul());
            appState.setInteger(AppState.Key.SOCIAL_EMAIL_ACTIVATION, settings.getSocial_email_activation());
            appState.setInteger(AppState.Key.PREMODERATION, settings.getPremoderation());
            appState.setInteger(AppState.Key.SHOP_AVAILABLE, settings.getShops_available());
            appState.setInteger(Key.SHOPS_CATEGORIES_ENABLED, settings.getShops_categories_enabled());
            appState.setInteger(Key.SHOPS_CATEGORIES_LIMIT, settings.getShops_categories_limit());
            appState.setInteger(Key.USERS_REGISTER_PHONE, settings.getUsers_register_phone());
            appState.setInteger(Key.SHOPS_ABONEMENT, settings.getShops_abonement());
            appState.setInteger(Key.USERS_REGISTER_MODE, settings.getUsers_register_mode());

            appState.setString(Key.DATA_PERIODS, settings.getDataPeriods().toString());
            appState.setInteger(Key.PERIOD_ENABLED, settings.getPeriodEenabled());
            appState.setInteger(Key.PUBLICATION_PERIOD, settings.getPublicationPeriod());

        }
    }

    public static void clearUserData() {
        UserData userData = UserData.getInstance(App.getInstance());
        userData.setString(UserData.Key.USER_EMAIL, "");
        userData.setInteger(UserData.Key.USER_ID, 0);
        userData.setString(UserData.Key.USER_DATA_JSON, "");
    }

    public static void update_user_fav(int count) {
        User user_from_json = new Gson().fromJson(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_DATA_JSON), User.class);
        if (user_from_json != null) {
            user_from_json.setItems_fav(count);
        }
        setUserData(user_from_json);
    }

    public static String getTimeLastIn(String timestamp, Context ctx) {
        try {
            DateTimeFormatter mDateFormatOld = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

            DateTime date_post = new DateTime(mDateFormatOld.parseDateTime(timestamp));
            DateTimeFormatter mDateFormat;

            //Today
            if (DateUtils.isToday(date_post)) {
                mDateFormat = DateTimeFormat.forPattern("HH:mm");
                return mDateFormat.print(date_post);
            }


            mDateFormat = DateTimeFormat.forPattern("dd MMM yyyy");
            return mDateFormat.print(date_post);
        } catch (Exception e) {
            e.printStackTrace();
            return ctx.getString(R.string.heaven_knows);
        }
    }

    public static String getTimeDialogs(String timestamp) {
        DateTimeFormatter mDateFormatOld = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime date_post = new DateTime(mDateFormatOld.parseDateTime(timestamp));
        DateTimeFormatter mDateFormat;
        mDateFormat = DateTimeFormat.forPattern("HH:mm");
        if (DateUtils.isToday(date_post)) {

            return mDateFormat.print(date_post);
        } else {
            //mDateFormat = DateTimeFormat.forPattern("dd MMM yyyy");
            mDateFormat = DateTimeFormat.forPattern("HH:mm");
            return mDateFormat.print(date_post);
        }

    }

    public static String strFormatPeriod(int i,Context ctx) {
        String text = "";
        switch (i) {
            case 3:
                text=ctx.getResources().getQuantityString(R.plurals.count_days,3,3);
                break;
            case 7:
                text=ctx.getResources().getQuantityString(R.plurals.count_week,1,1);
                break;
            case 14:
                text=ctx.getResources().getQuantityString(R.plurals.count_week,2,2);
                break;
            case 30:
                text=ctx.getResources().getQuantityString(R.plurals.count_month,1,1);
                break;
            case 60:
                text=ctx.getResources().getQuantityString(R.plurals.count_month,2,2);
                break;
            case 90:
                text=ctx.getResources().getQuantityString(R.plurals.count_month,3,3);
                break;
            case 180:
                text=ctx.getResources().getQuantityString(R.plurals.count_month,6,6);
                break;
            case 365:
                text=ctx.getResources().getQuantityString(R.plurals.count_years,1,1);
                break;
            case 730:
                text=ctx.getResources().getQuantityString(R.plurals.count_years,2,2);
                break;
        }
        return text;
    }

    public static String getTimePAYMents(String timestamp) {
        DateTimeFormatter mDateFormatOld = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime date_post = new DateTime(mDateFormatOld.parseDateTime(timestamp));
        DateTimeFormatter mDateFormat;
        mDateFormat = DateTimeFormat.forPattern("dd.MM.yyyy  HH:mm");
        return mDateFormat.print(date_post);
    }

    public static String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String getTimeMyPostFrom(String timestamp, Context ctx) {
        DateTimeFormatter mDateFormatOld = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        try {
            DateTime date_post = new DateTime(mDateFormatOld.parseDateTime(timestamp));
            DateTimeFormatter mDateFormat;
            //Today
            if (DateUtils.isToday(date_post)) {
                return ctx.getString(R.string.today);
            }

            //Yesterday
            if (DateUtils.isToday(date_post.plusDays(1))) {
                return ctx.getString(R.string.yesterday);
            }
            mDateFormat = DateTimeFormat.forPattern("dd.MM.yy");
            return mDateFormat.print(date_post);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getTimeMessage(String timestamp) {
        DateTimeFormatter mDateFormatOld = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime date_post = new DateTime(mDateFormatOld.parseDateTime(timestamp));
        DateTimeFormatter mDateFormat;
        mDateFormat = DateTimeFormat.forPattern("HH:mm");
        return mDateFormat.print(date_post);
    }

    public static long getHeaderID(String timestamp) {

        DateTimeFormatter mDateFormatOld = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime date_post = new DateTime(mDateFormatOld.parseDateTime(timestamp));

        return date_post.getDayOfYear();
    }
}
