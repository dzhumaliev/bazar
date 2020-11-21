package solutions.isky.gaurangarevolution.presentation.databases;

import solutions.isky.gaurangarevolution.data.network.AppApiConstants;

public class Constants {
    public static String FORGOT_PASS = AppApiConstants.URL + "user/forgot";
    public static String PROVIDER_FC = "2";
    public static String PROVIDER_VK = "1";
    public static String PROVIDER_OK = "4";

    public static final int TYPE_IN_PAY = 1; //пополнение счета - оплата
    public static final int TYPE_IN_GIFT = 2; // пополнение счета - подарок

    public static final String NEW_MESSAGE = "new_message";

    public static final String USER_AVATAR_PUSH = "avatar";
    public static final String MESSAGE_PUSH = "message_text";
    public static final String NAME_USER_PUSH = "name_user";
    public static final String NEW_MESS = "new_mess";
    public static final String FOR_USER_ID = "for_user_id";
    public static final String FROM_USER_ID = "user_id";

    //for shop
    public static final int SOCIAL_LINK_FACEBOOK = 1;
    public static final int SOCIAL_LINK_VKONTAKTE = 2;
    public static final int SOCIAL_LINK_ODNOKLASSNIKI = 4;
    public static final int SOCIAL_LINK_GOOGLEPLUS = 8;
    public static final int SOCIAL_LINK_YANDEX = 16;
    public static final int SOCIAL_LINK_MAILRU = 32;


    //платежи
    public static final String BALANCE = "balance"; //личный счет
    public static final String LIQPAYBALANCE = "liqpay"; // liqpay
    public static final String PAYPALBALANCE = "paypal"; // paypal
    public static final String YANDEXBALANCE = "yandex"; // paypal

    public static final String PAYPALCLIENTID="AeStbqGd19ycncUZjzwRQHJkR3TAHG9WRSZjDlExDa7ZZ6CE0Hm9QClXLIjNu0EdeRatIRKdMgLIv8gi";
}
