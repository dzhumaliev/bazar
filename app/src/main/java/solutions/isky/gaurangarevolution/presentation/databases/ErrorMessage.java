package solutions.isky.gaurangarevolution.presentation.databases;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import solutions.isky.gaurangarevolution.R;

/**
 * Created by sergey on 21.03.18.
 */

public class ErrorMessage {


    public static final String LOGIN_FAILD = "LOGIN_FAILD";
    //регистрация
    public static final String USER_EMAIL_INUSE = "USER_EMAIL_INUSE";
    public static final String USER_EMAIL_WRONG = "USER_EMAIL_WRONG";
    public static final String SOCIAL_USER_NOT_EXIST = "SOCIAL_USER_NOT_EXIST";
    public static final String SOCIAL_REGISTER_EMAIL_INUSE = "SOCIAL_REGISTER_EMAIL_INUSE";
    public static final String USER_NOT_ACTIVE = "USER_NOT_ACTIVE";
    public static final String USER_PASSWORD_WRONG = "USER_PASSWORD_WRONG";
    public static final String PHONE_NUMBER_WRONG = "PHONE_NUMBER_WRONG";
    public static final String ERROR_NO_CREATE_PROFILE = "ERROR_NO_CREATE_PROFILE";
    public static final String ACCOUNT_NOT_ACTIVE = "ACCOUNT_NOT_ACTIVE";
    public static final String ACCOUNT_NOT_ACTIVATED_BY_SMS="ACCOUNT_NOT_ACTIVATED_BY_SMS";
    public static final String LOGIN_FAIL = "LOGIN_FAIL";
    public static final String PASS_FAIL = "PASS_FAIL";
    public static final String NO_SESSION = "NO_SESSION";
    public static final String NO_PERMISSION = "NO_PERMISSION";
    public static final String SHOPS_NOT_FOUND = "SHOPS_NOT_FOUND";
    public static final String NO_EMAIL_IN_PROFILE = "NO_EMAIL_IN_PROFILE";
    public static final String ACTIVATION_LIMIT = "ACTIVATION_LIMIT";
    public static final String WRONG_CODE = "WRONG_CODE";
    public static final String USER_EMAIL_OR_PHONE_INUSE = "USER_EMAIL_OR_PHONE_INUSE";
    public static final String WRONG_USER = "WRONG_USER";
    public static final String AUTHOR_NOT_FOUND = "AUTHOR_NOT_FOUND";
    public static final String AUTHOR_NOT_ACTIVE = "AUTHOR_NOT_ACTIVE";
    public static final String PHONE_NOT_VERIFIED = "PHONE_NOT_VERIFIED";
    public static final String ADD_PERIOD_20S = "ADD_PERIOD_20S";
    public static final String DATA_SAVE_ERROR = "DATA_SAVE_ERROR";
    public static final String CURRENT_PASSWORD_WRONG = "CURRENT_PASSWORD_WRONG";
    public static final String NOT_VALID_PHONE = "NOT_VALID_PHONE";
    public static final String EMAIL_IN_USE = "EMAIL_IN_USE";
    public static final String WRONG_PHONE_OWNER = "WRONG_PHONE_OWNER";
    public static final String PHONE_INUSE = "PHONE_INUSE";
    public static final String SOCIAL_ACC_ALREADY_CONNECTED_TO_ANOTHER_USER = "SOCIAL_ACC_ALREADY_CONNECTED_TO_ANOTHER_USER";
    public static final String ITEM_OWNER_MISSMATCH = "ITEM_OWNER_MISSMATCH";
    public static final String SVC_NOT_OR_FAILED = "SVC_NOT_OR_FAILED";
    public static final String LOW_BALANCE = "LOW_BALANCE";
    public static final String MAIL_TIMELIMIT = "MAIL_TIMELIMIT";
    public static final String SVC_NOT_PAIED = "SVC_NOT_PAIED";
    public static final String SHOP_CATS_EMPTY = "SHOP_CATS_EMPTY";
    public static final String TITLE_TOO_SMALL = "TITLE_TOO_SMALL";
    public static final String ITEM_NOT_PUBLICATED_OUT = "ITEM_NOT_PUBLICATED_OUT";
    public static final String SHOP_NOT_ACTIVE = "SHOP_NOT_ACTIVE";
    public static final String SHOP_LIMIT_EXCEED = "SHOP_LIMIT_EXCEED";
    public static final String USER_BLOCKED = "user_blocked";
    public static final String PHP_UPLOAD_ERR_INI_SIZE = "PHP_UPLOAD_ERR_INI_SIZE";
    private static final String SHOP_OPEN_ERROR = "SHOP_OPEN_ERROR";
    private static final String SHOP_OPEN_REQUEST_SPAM = "SHOP_OPEN_REQUEST_SPAM";
    private static final String SHOP_EDIT_REQUEST_SPAM = "SHOP_EDIT_REQUEST_SPAM";
    private static final String USER_ALREADY_HAVE_SHOP = "USER_ALREADY_HAVE_SHOP";
    private static final String SHOP_DESCR_TOO_SHORT = "SHOP_DESCR_TOO_SHORT";
    private static final String MESSAGE_TOO_SHORT = "MESSAGE_TOO_SHORT";
    private static final String ABONEMENT_IS_ALREADY_ACTIVE = "ABONEMENT_IS_ALREADY_ACTIVE";
    public static final String USER_PASSWORD_LENGTH_MISSMATCH="USER_PASSWORD_LENGTH_MISSMATCH";
    public static final String CATEGORY_LIMIT_EXCEED="CATEGORY_LIMIT_EXCEED";

    public static final String AMOUNT_EMPTY="AMOUNT_EMPTY";
    
    public static String getError(@NonNull String error, Context ctx) {
        String text = "";
        if (LOGIN_FAILD.equalsIgnoreCase(error)) {
            text = LOGIN_FAILD;
        } else if (USER_EMAIL_INUSE.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.user_email_inuse);

        } else if (SHOP_DESCR_TOO_SHORT.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.shop_descr_too_short);
        } else if (MESSAGE_TOO_SHORT.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.message_too_short);
        } else if (PHP_UPLOAD_ERR_INI_SIZE.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.php_upload_err_ini_size);
        } else if (ABONEMENT_IS_ALREADY_ACTIVE.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.abonement_is_already_active);
        }
        else if (CATEGORY_LIMIT_EXCEED.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.limit_category);
        }
        else if (AMOUNT_EMPTY.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.sum_not_empty);
        }
//        else if(USER_EMAIL_WRONG.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.user_email_wrong);
//        }
//        else if(USER_PASSWORD_WRONG.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.user_password_wrong);
//        }
//        else if(PHONE_NUMBER_WRONG.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.phone_number_wrong);
//        }
//        else if(NO_SESSION.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.no_session);
//        }
//        else if(ERROR_NO_CREATE_PROFILE.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.error_no_create_profile);
//        }
        else if (LOGIN_FAIL.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.fail_login_or_pass);
        } else if (USER_ALREADY_HAVE_SHOP.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.already_have_shop);
        } else if (SHOP_OPEN_REQUEST_SPAM.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.shop_open_request_spam);
        } else if (SHOP_EDIT_REQUEST_SPAM.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.shop_edit_request_spam);
        }
        else if(ACTIVATION_LIMIT.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.limit_sms);
        }
        else if (PASS_FAIL.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.fail_login_or_pass);
        } else if (SHOP_OPEN_ERROR.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.error_create_shop);
        } else if (SHOPS_NOT_FOUND.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.shops_empty);
        } else if (SHOP_CATS_EMPTY.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.shops_cats_empty);
        } else if (WRONG_CODE.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.wrong_code);
        } else if (USER_EMAIL_OR_PHONE_INUSE.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.user_email_phone_inuse);
        }
//        else if(AUTHOR_NOT_FOUND.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.author_not_found);
//        }else if(AUTHOR_NOT_ACTIVE.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.author_not_active);
//        }else if(PHONE_NOT_VERIFIED.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.phone_not_verified);
//        }
        else if (ADD_PERIOD_20S.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.add_period_20s);
        }
// else if(DATA_SAVE_ERROR.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.data_save_error);
//        }else if(WRONG_USER.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.wrong_user);
//        }
        else if (CURRENT_PASSWORD_WRONG.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.current_password_wrong);
        } else if (NOT_VALID_PHONE.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.phone_number_wrong);
        }
//        else if(EMAIL_IN_USE.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.user_email_inuse);
//        }
        else if (WRONG_PHONE_OWNER.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.phone_number_wrong);
        }
        else if(PHONE_INUSE.equalsIgnoreCase(error)){
            text= ctx.getString(R.string.user_phone_inuse);
        }
        else if (SOCIAL_ACC_ALREADY_CONNECTED_TO_ANOTHER_USER.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.social_alredy_another_user);
        }
//        else if(ITEM_OWNER_MISSMATCH.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.ad_missmatch);
//        }else if(SVC_NOT_OR_FAILED.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.svc_not_or_failed);
//        }
        else if (LOW_BALANCE.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.low_balance);
        }
//        else if(MAIL_TIMELIMIT.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.mail_time_limit);
//        }
//        else if(SVC_NOT_PAIED.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.svc_not_paied);
//        }
//        else if(TITLE_TOO_SMALL.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.short_request);
//        }
//        else if(ITEM_NOT_PUBLICATED_OUT.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.item_not_publicated);
//        }
//        else if(SHOP_NOT_ACTIVE.equalsIgnoreCase(error)){
//            text= ctx.getString(R.string.shop_not_active);
        //  }
        else if (SHOP_LIMIT_EXCEED.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.shop_limit);
        } else if (USER_BLOCKED.equalsIgnoreCase(error)) {
            text = ctx.getString(R.string.user_block);
        } else {
            text = error;
        }
        return text;
    }
}
