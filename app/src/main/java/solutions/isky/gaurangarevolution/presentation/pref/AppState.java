package solutions.isky.gaurangarevolution.presentation.pref;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Serzhik on 21.04.2016.
 */
public class AppState extends Prefs<AppState.Key> {

    private static final String PREFS_FILE_NAME = "app_state";
    private static AppState sInstance = null;

    // keys
    public static class Key extends BaseKey {

        public static final Key LOGGED_IN = new Key("logged_in", Boolean.class, false);
        public static final Key FIRST_START= new Key("first_start", Boolean.class, true);
        public static final Key CURRENTLAYOUTTYPE= new Key("currentlayouttype", Integer.class, 1);
        public static final Key PHONES_FILD = new Key("phones_fild", Integer.class, 5);
        public static final Key CURRENCY_DEFAUL = new Key("currency_defaul", Integer.class, 0);
        public static final Key SOCIAL_EMAIL_ACTIVATION = new Key("social_email_activation", Integer.class, 0);
        public static final Key PREMODERATION = new Key("premoderation", Integer.class, 0);
      public static final Key SHOP_AVAILABLE = new Key("shop_available", Integer.class, 0);
      public static final Key SHOPS_CATEGORIES_ENABLED = new Key("shops_categories_enabled", Integer.class, 0);
      public static final Key SHOPS_CATEGORIES_LIMIT = new Key("shops_categories_limit", Integer.class, 0);
      public static final Key USERS_REGISTER_PHONE = new Key("users_register_phone", Integer.class, 0);
        public static final Key SHOPS_ABONEMENT=new Key("shops_abonement", Integer.class, 0);
        public static final Key USERS_REGISTER_MODE=new Key("users_register_mode",Integer.class, 1);
//        public static final Key TITLE_REGION_API = new Key("title_region_api", String.class, "");
//        public static final Key CATEG_ID = new Key("CATEG_ID", Integer.class, 0);
//        public static final Key TIME_STAMP = new Key("time_stamp", Long.class, 0l);
//        public static final Key IS_CATEG = new Key("IS_CATEG", Boolean.class, false);
          public static final Key ID_PUSH = new Key("id_push", String.class, "");
//        public static final Key IMG_PATH = new Key("IMG_PATH", String.class, "");
        public static final Key TIME_STAMP = new Key("time_stamp", Long.class, 0l);
        public static final Key CODE_COUNTRY_STRING = new Key("code_country_string", String.class, "ua");
        public static final Key LANG_APP = new Key("lang_app", String.class, "ru");

       //период публикации
       public static final Key DATA_PERIODS = new Key("dataperiods", String.class, "");
        public static final Key PERIOD_ENABLED = new Key("periodenabled", Integer.class, 0);
        public static final Key PUBLICATION_PERIOD = new Key("publicationperiod", Integer.class, 30);


        protected <T> Key(String str, Class<T> type, T defaultValue) {
            super(str, type, defaultValue);
        }

    }

    private AppState(@NonNull Context context) {
        super(context.getApplicationContext(), PREFS_FILE_NAME);
    }

    public static AppState getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new AppState(context);
        }

        return sInstance;
    }

}
