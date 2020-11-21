package solutions.isky.gaurangarevolution.presentation.pref;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Serzhik on 12.06.2016.
 */
public class UserData extends Prefs<UserData.Key> {

    private static final String PREFS_FILE_NAME = "user_data";
    private static UserData sInstance = null;

    // keys
    public static class Key extends BaseKey {

        public static final Key USER_ID = new Key("user_id", Integer.class, 0);
        public static final Key USER_LOGIN = new Key("user_login", String.class, "");
        public static final Key USER_EMAIL = new Key("user_email", String.class, "");
        public static final Key USER_AVATAR = new Key("user_avatar", String.class, "");
        public static final Key USER_SESSID = new Key("user_sessid", String.class, "");
        public static final Key USER_PASSW = new Key("user_passw", String.class, "");
        public static final Key USER_FB_ID = new Key("user_fb_id", String.class, "");
        public static final Key USER_NAME = new Key("user_name", String.class, "");
        public static final Key USER_PHONE = new Key("user_phone", String.class, "");
        public static final Key USER_DATA_JSON = new Key("user_data_json", String.class, "");

        protected <T> Key(String str, Class<T> type, T defaultValue) {
            super(str, type, defaultValue);
        }

    }

    private UserData(@NonNull Context context) {
        super(context.getApplicationContext(), PREFS_FILE_NAME);
    }

    public static UserData getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new UserData(context);
        }

        return sInstance;
    }

}