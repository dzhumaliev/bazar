package solutions.isky.gaurangarevolution.presentation.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.data.models.UserLoginRegister;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;

/**
 * Created by sergey on 21.03.18.
 */

public class JsonObjBody {

  private static JsonObject getCode;
  public static final String SESID = "SESID";

  public static JsonObject getListStart(@NonNull int page, @NonNull int onpage,
      @NonNull String lang) {
    getCode = new JsonObject();
    JsonObject param = new JsonObject();
    if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN)) {
      getCode.addProperty(SESID,
          UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID));
    }
    if (ParamFilterPostSingleton.getInstance().getSort() != null) {
      param.addProperty("sort", ParamFilterPostSingleton.getInstance().getSort());
    }
    param.addProperty("page", page);
    param.addProperty("onpage", onpage);
    param.addProperty("lang", lang);
    getCode.add("items_list", param);
    return getCode;
  }

  public static JsonObject login(@NonNull String email, @NonNull String pass) {
    getCode = new JsonObject();
    JsonObject login = new JsonObject();
    login.addProperty("email", email);
    login.addProperty("pass", pass);
    if (!TextUtils
        .isEmpty(AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH))) {
      login.addProperty("appregid",
          AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH));
    }
    getCode.add("user_login", login);
    return getCode;
  }

  public static JsonObject getAdInfo(@NonNull int id, @NonNull String lang) {
    getCode = new JsonObject();
    JsonObject id_info = new JsonObject();
    if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN)) {
      getCode.addProperty(SESID,
          UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID));
      id_info.addProperty("userfav",
          UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID));
    }
    id_info.addProperty("id", id);
    id_info.addProperty("similar", 1);
    id_info.addProperty("lang", lang);
    getCode.add("item_info", id_info);
    return getCode;
  }

  public static JsonObject setComplain(@NonNull int id, @NonNull int id_complain,
      @NonNull String mess) {
    getCode = new JsonObject();
    JsonObject complain = new JsonObject();
    if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN)) {
      getCode.addProperty(SESID,
          UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID));
    }
    complain.addProperty("item_id", id);
    complain.addProperty("reason", id_complain);
    if (!TextUtils.isEmpty(mess) || mess != null) {
      complain.addProperty("message", mess);
    }
    getCode.add("item_claim", complain);
    return getCode;
  }

  public static JsonObject send_Mess_from_ad(@NonNull String sesid, @NonNull String text,
      @NonNull int id_item) {
    getCode = new JsonObject();

    getCode.addProperty(SESID, sesid);
    JsonObject intmail_add = new JsonObject();
    intmail_add.addProperty("item_id", id_item);
    intmail_add.addProperty("message", text);
    getCode.add("intmail_add", intmail_add);
    return getCode;
  }

  public static JsonObject update_fav(@NonNull String sesid, @NonNull int item_id,
      boolean is_delete_fav) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject fav_update = new JsonObject();
    JsonArray item_fav_list = new JsonArray();
    item_fav_list.add(item_id);
    if (is_delete_fav) {
      fav_update.add("items_del", item_fav_list);
    } else {
      fav_update.add("items_add", item_fav_list);
    }

    getCode.add("fav_update", fav_update);
    return getCode;
  }

  public static JsonObject sendClickPhone(@NonNull int id) {
    getCode = new JsonObject();
    JsonObject item_viewup = new JsonObject();
    item_viewup.addProperty("id", id);
    getCode.add("item_viewup", item_viewup);
    return getCode;
  }

  public static JsonObject login_soc(@NonNull String profile_id, @NonNull String token,
      String email, @NonNull String lang, @NonNull String provider_id) {
    getCode = new JsonObject();
    JsonObject user_login = new JsonObject();
    user_login.addProperty("profile_id", profile_id);
    user_login.addProperty("token", token);
    user_login.addProperty("lang", lang);
    if (!TextUtils
        .isEmpty(AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH))) {
      user_login.addProperty("appregid",
          AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH));
    }
    user_login.addProperty("provider_id", provider_id);
    if (email != null) {
      user_login.addProperty("email", email);
    }
    getCode.add("user_login", user_login);
    return getCode;
  }

  public static JsonObject bind_soc(@NonNull int user_id, @NonNull String profile_id,
      @NonNull String token, @NonNull String sesid, @NonNull String provider_id) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject add_soc = new JsonObject();
    add_soc.addProperty("user_id", user_id);
    add_soc.addProperty("profile_id", profile_id);
    add_soc.addProperty("token", token);
    add_soc.addProperty("provider_id", provider_id);
    getCode.add("add_soc", add_soc);
    return getCode;
  }
  public static JsonObject unlink_soc(@NonNull String sesid, @NonNull String provider_id) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject unlink_soc = new JsonObject();
    unlink_soc.addProperty("provider_id", provider_id);
    getCode.add("unlink_soc", unlink_soc);
    return getCode;
  }
  public static JsonObject log_out(@NonNull String sesid) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject user_logout = new JsonObject();
    getCode.add("user_logout", user_logout);
    return getCode;
  }

  public static JsonObject getUserAds(@NonNull String sesid, @NonNull int page, @NonNull int onpage,
      @NonNull int user_id, @NonNull String lang, @NonNull int shop_id) {
    getCode = new JsonObject();
    if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN)) {
      getCode.addProperty(SESID, sesid);
    }
    JsonObject param = new JsonObject();
    JsonObject filters = new JsonObject();
    param.addProperty("page", page);
    param.addProperty("onpage", onpage);
    if (shop_id > 0) {
      filters.addProperty("shop_id", shop_id);
    } else {
      filters.addProperty("user_id", user_id);
    }
    param.addProperty("lang", lang);
    param.add("filters", filters);
    getCode.add("items_list", param);
    return getCode;
  }

  public static JsonObject register(@NonNull UserLoginRegister userLoginRegister,
      @NonNull String lang) {
    getCode = new JsonObject();
    JsonObject register = new JsonObject();
    if(userLoginRegister.getType_reg()==1){
      register.addProperty("email", userLoginRegister.getLogin());
      register.addProperty("pass", userLoginRegister.getPassw());
    }else if(userLoginRegister.getType_reg()==2){
      register.addProperty("email", userLoginRegister.getLogin());
      register.addProperty("pass", userLoginRegister.getPassw());
      register.addProperty("phone", userLoginRegister.getPhone());
    }else if(userLoginRegister.getType_reg()==3){
      register.addProperty("phone", userLoginRegister.getPhone());
    }

    register.addProperty("key_auth", AppUtils.md5(userLoginRegister.getLogin() + "kazakhstan2017"));
    register.addProperty("lang", lang);
    if (!TextUtils
        .isEmpty(AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH))) {
      register.addProperty("appregid",
          AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH));
    }
    getCode.add("user_register", register);
    return getCode;
  }

  public static JsonObject register(@NonNull String email, String phone,String pass, @NonNull String provider_id,
      @NonNull String token, @NonNull String profile_id, @NonNull String lang) {
    getCode = new JsonObject();
    JsonObject register = new JsonObject();
    register.addProperty("email", email);
    if (pass != null) {
      register.addProperty("pass", pass);
    }
    if (phone != null) {
      register.addProperty("phone", phone);
    }
    register.addProperty("provider_id", provider_id);
    register.addProperty("token", token);
    register.addProperty("profile_id", profile_id);
    register.addProperty("lang", lang);
    if (!TextUtils
        .isEmpty(AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH))) {
      register.addProperty("appregid",
          AppState.getInstance(App.getInstance()).getString(AppState.Key.ID_PUSH));
    }
    if (pass != null) {
      getCode.add("user_social_connect", register);
    } else {
      getCode.add("user_register", register);
    }

    return getCode;
  }

  public static JsonObject getRegion(@NonNull int lvl, @NonNull int pid, @NonNull String lang) {
    getCode = new JsonObject();
    JsonObject region_list = new JsonObject();
    region_list.addProperty("lvl", lvl);
    if (pid > 0) {
      region_list.addProperty("pid", pid);
    }
    region_list.addProperty("lang", lang);
    getCode.add("region_list", region_list);
    return getCode;
  }

  public static JsonObject getListCateg(@NonNull String lang) {
    getCode = new JsonObject();
    JsonObject get_tree_cats = new JsonObject();
    get_tree_cats.addProperty("lang", lang);
    getCode.add("get_tree_cats", get_tree_cats);
    return getCode;
  }
  public static JsonObject getShopListCateg(@NonNull String lang) {
    getCode = new JsonObject();
    JsonObject get_tree_cats = new JsonObject();
    get_tree_cats.addProperty("lang", lang);
    getCode.add("shop_categories", get_tree_cats);
    return getCode;
  }
  public static JsonObject getPropsDop(@NonNull int id_cat, @NonNull String lang) {
    getCode = new JsonObject();
    JsonObject param = new JsonObject();
    param.addProperty("id", id_cat);
    param.addProperty("lang", lang);
    getCode.add("get_cat_dyns", param);
    return getCode;
  }

  public static JsonObject getList(@NonNull int page, @NonNull int onpage, String sort,
      @NonNull int categId,
      String search, @NonNull int city_id, @NonNull int owner,
      JsonArray object, @NonNull int is_image, @NonNull int p_less,
      @NonNull int p_more, @NonNull String lang) {
    getCode = new JsonObject();
    if (AppState.getInstance(App.getInstance()).getBoolean(AppState.Key.LOGGED_IN)) {
      getCode.addProperty(SESID,
          UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID));
    }

    JsonObject param = new JsonObject();
    JsonObject filters = new JsonObject();
    if (sort != null) {
      filters.addProperty("sort", sort);
    }
    if (categId != 0) {
      filters.addProperty("cats_id", categId);
    }
    if (search != null && !TextUtils.isEmpty(search)) {
      filters.addProperty("title", search);
    }
    if (city_id > 0) {
      filters.addProperty("city_id", city_id);
    }
    if (owner > 0) {
      filters.addProperty("owner_type", owner);
    }
    if (is_image > 0) {
      filters.addProperty("is_image", is_image);
    }
    if (p_less > 0) {
      filters.addProperty("p_less", p_less);
    }
    if (p_more > 0) {
      filters.addProperty("p_more", p_more);
    }
    if (object != null && object.size() > 0) {
      filters.add("props", object);
    }
    param.add("filters", filters);
    param.addProperty("lang", lang);
    param.addProperty("page", page);
    param.addProperty("onpage", onpage);
    getCode.add("items_list", param);
    return getCode;
  }

  public static JsonObject getShops(@NonNull int page, @NonNull int onpage, int categId,
      String search, int city_id, @NonNull String lang) {
    getCode = new JsonObject();

    JsonObject param = new JsonObject();
    JsonObject filters = new JsonObject();

    if (categId != 0) {
      filters.addProperty("cats_id", categId);
    }
    if (search != null && !TextUtils.isEmpty(search)) {
      filters.addProperty("title", search);
    }
    if (city_id > 0) {
      filters.addProperty("region", city_id);
    }
    param.add("filters", filters);
    param.addProperty("lang", lang);
    param.addProperty("page", page);
    param.addProperty("onpage", onpage);
    param.addProperty("icon", "v");
    getCode.add("shops_list", param);
    return getCode;
  }

  public static JsonObject getBillings(@NonNull String sesid, @NonNull int page,
      @NonNull int onpage, @NonNull String lang) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject user_balance = new JsonObject();
    user_balance.addProperty("get_bills", 1);
    user_balance.addProperty("page", page);
    user_balance.addProperty("onpage", onpage);
    user_balance.addProperty("lang", lang);
    getCode.add("user_balance", user_balance);
    return getCode;
  }

  public static JsonObject getMyInfo(@NonNull String sesid, @NonNull int id) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject get_info = new JsonObject();
    get_info.addProperty("id", id);
    get_info.addProperty("lang", AppState.getInstance(App.getInstance()).getString(AppState.Key.LANG_APP));
    getCode.add("get_info", get_info);
    return getCode;
  }

  public static JsonObject reg_info(@NonNull int region_id, @NonNull String lang) {
    getCode = new JsonObject();
    JsonObject reg_info = new JsonObject();
    reg_info.addProperty("id", region_id);
    reg_info.addProperty("lang", lang);
    getCode.add("reg_info", reg_info);
    return getCode;
  }

  public static JsonObject avatar(@NonNull String sesid, @NonNull String img) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject avatar = new JsonObject();
    avatar.addProperty("img", img);
    getCode.add("avatar", avatar);
    return getCode;
  }

  public static JsonObject change_password(@NonNull String sesid, @NonNull String pass,
      @NonNull String pass_new) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject change_password = new JsonObject();
    change_password.addProperty("pass", pass);
    change_password.addProperty("pass_new", pass_new);
    getCode.add("change_password", change_password);
    return getCode;
  }

  public static JsonObject change_email(@NonNull String sesid, @NonNull String pass,
      @NonNull String email, @NonNull String lang) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject change_email = new JsonObject();
    change_email.addProperty("pass", pass);
    change_email.addProperty("email", email);
    change_email.addProperty("lang", lang);
    getCode.add("change_email", change_email);
    return getCode;
  }

  public static JsonObject getMyAds(@NonNull String sesid, @NonNull int page, @NonNull int onpage,
      @NonNull int user_id, @NonNull int is_active, @NonNull String lang,
      @NonNull boolean is_shop) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject param = new JsonObject();
    param.addProperty("page", page);
    param.addProperty("onpage", onpage);
    JsonObject filters = new JsonObject();
    filters.addProperty("user_id", user_id);
    if (is_shop) {
      User user_from_json = new Gson()
          .fromJson(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_DATA_JSON),
              User.class);
      if (user_from_json != null && user_from_json.getShop_id() > 0) {
        filters.addProperty("shop_id", user_from_json.getShop_id());
      }
    }
    if (is_active == 0) {
      filters.addProperty("status", 3);
      if (AppState.getInstance(App.getInstance()).getInteger(AppState.Key.PREMODERATION) > 0) {
        filters.addProperty("moderated", 1);
      }
    } else if (is_active == 1) {
      filters.addProperty("!status", 3);
    } else if (is_active == 2) {
      filters.addProperty("status", 3);
      if (AppState.getInstance(App.getInstance()).getInteger(AppState.Key.PREMODERATION) > 0) {
        filters.addProperty("moderated", 0);
      }
    }
    param.add("filters", filters);
    param.addProperty("lang", lang);
    getCode.add("items_list", param);
    return getCode;
  }

  public static JsonObject editUser(@NonNull String sesid, @NonNull String name,
      @NonNull JsonArray phone_numbers, @NonNull int region_id) {
    getCode = new JsonObject();
    JsonObject edit_user = new JsonObject();
    getCode.addProperty(SESID, sesid);
    edit_user.addProperty("name", name);
    edit_user.add("phones", phone_numbers);
    edit_user.addProperty("region_id", region_id);
    getCode.add("edit_info", edit_user);
    return getCode;
  }

  public static JsonObject getSettings() {
    getCode = new JsonObject();
    JsonObject board_settings = new JsonObject();
    getCode.add("board_settings", board_settings);
    return getCode;
  }

  public static JsonObject item_up(@NonNull int id, @NonNull String sesid) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject item_freeup = new JsonObject();
    item_freeup.addProperty("id", id);
    getCode.add("item_freeup", item_freeup);
    return getCode;
  }

  public static JsonObject item_change_status(@NonNull String sesid, @NonNull int id,
      @NonNull String status) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject item_change_status = new JsonObject();
    item_change_status.addProperty("status", status);
    item_change_status.addProperty("id", id);
    getCode.add("item_change_status", item_change_status);
    return getCode;
  }

  public static JsonObject editPoster(@NonNull String sesid, @NonNull int cat_id,
      @NonNull String title, @NonNull String descr,
      @NonNull int city_id, @NonNull JsonObject dop, @NonNull String name,
      @NonNull JsonArray phones,
      @NonNull JsonArray price_ex, @NonNull int price_curr, @NonNull String price,
      @NonNull JsonArray images, @NonNull int id,
      @NonNull double lat, @NonNull double lng, @NonNull String adress) {
    getCode = new JsonObject();
    JsonObject item_add = new JsonObject();
    getCode.addProperty(SESID, sesid);
    item_add.addProperty("id", id);
    item_add.addProperty("cat_id", cat_id);
    item_add.addProperty("title", title);
    item_add.addProperty("title", title);
//        item_add.addProperty("price", price);
//        item_add.addProperty("price_curr", price_curr);
    item_add.addProperty("descr", descr);
    item_add.addProperty("name", name);
    if (dop.size() > 0) {
      item_add.add("dop", dop);
    }

    if (phones.size() > 0) {
      item_add.add("phones", phones);
    }
    if (lat != 0 && lng != 0) {
      item_add.addProperty("lat", lat);
      item_add.addProperty("lon", lng);
    }
    if (adress != null) {
      item_add.addProperty("addr_addr", adress);
    }
    item_add.addProperty("city_id", city_id);
    //if (!TextUtils.isEmpty(price)) {
      item_add.addProperty("price", price);
   // }
    if (price_curr > 0) {
      item_add.addProperty("price_curr", price_curr);
    }
    if (price_ex.size() > 0) {
      item_add.add("price_ex", price_ex);
    }
    if (images != null && images.size() > 0) {
      item_add.add("images", images);
    }
    getCode.add("item_update", item_add);
    return getCode;
  }

  public static JsonObject addNewPoster(@NonNull String sesid, @NonNull int cat_id,
      @NonNull String title, @NonNull String descr,
      @NonNull int city_id, @NonNull JsonObject dop, @NonNull String name,
      @NonNull JsonArray phones,
      @NonNull JsonArray price_ex, @NonNull int price_curr, @NonNull String price,
      @NonNull JsonArray images,
      @NonNull double lat, @NonNull double lng, @NonNull String adress,@NonNull int shop_id,int days) {
    getCode = new JsonObject();
    JsonObject item_add = new JsonObject();
    getCode.addProperty(SESID, sesid);
    item_add.addProperty("cat_id", cat_id);
    item_add.addProperty("title", title);
//        item_add.addProperty("price", price);
//        item_add.addProperty("price_curr", price_curr);
    item_add.addProperty("descr", descr);
    item_add.addProperty("name", name);
    item_add.addProperty("publicated_to", days);
    if (dop.size() > 0) {
      item_add.add("dop", dop);
    }
    if (lat != 0 && lng != 0) {
      item_add.addProperty("lat", lat);
      item_add.addProperty("lon", lng);
    }
    if (adress != null) {
      item_add.addProperty("addr_addr", adress);
    }
    if (phones.size() > 0) {
      item_add.add("phones", phones);
    }
    if (shop_id > 0) {
      item_add.addProperty("shop_id", shop_id);
    }
    item_add.addProperty("city_id", city_id);
    if (!TextUtils.isEmpty(price)) {
      item_add.addProperty("price", price);
    }
    if (price_curr > 0) {
      item_add.addProperty("price_curr", price_curr);
    }
    //if (price_ex.size() > 0) {
      item_add.add("price_ex", price_ex);
    //}
    if (images != null && images.size() > 0) {
      item_add.add("images", images);
    }
    getCode.add("item_add", item_add);
    return getCode;
  }

  public static JsonObject getDialogs(@NonNull String sesid, @NonNull int page,
      @NonNull int onpage, @NonNull String search) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject intmail_list = new JsonObject();
    intmail_list.addProperty("page", page);
    intmail_list.addProperty("onpage", onpage);
    if (!TextUtils.isEmpty(search)) {
      intmail_list.addProperty("search", search);
    }
    User user = new Gson()
        .fromJson(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_DATA_JSON),
            User.class);
    if (user != null) {
      intmail_list.addProperty("shop_id", user.getShop_id());
    }
    getCode.add("chats_list", intmail_list);
    return getCode;
  }

  public static JsonObject svc_data(@NonNull String lang, @NonNull String item_id) {
    getCode = new JsonObject();
    JsonObject svc_list = new JsonObject();
    svc_list.addProperty("lang", lang);
    svc_list.addProperty("item_id", item_id);
    getCode.add("svc_list", svc_list);
    return getCode;
  }
  public static JsonObject svc_abonement(@NonNull String lang, @NonNull String shop_id) {
    getCode = new JsonObject();
    JsonObject svc_abonement_list = new JsonObject();
    svc_abonement_list.addProperty("lang", lang);
    svc_abonement_list.addProperty("shop_id", shop_id);
    getCode.add("shop_svc_abonement_list", svc_abonement_list);
    return getCode;
  }
  public static JsonObject svc_shop_data(@NonNull String lang, @NonNull String shop_id) {
    getCode = new JsonObject();
    JsonObject svc_list = new JsonObject();
    svc_list.addProperty("lang", lang);
    svc_list.addProperty("shop_id", shop_id);
    getCode.add("shop_svc_list", svc_list);
    return getCode;
  }
  public static JsonObject svc_buy(@NonNull String sesid, @NonNull String id_ad,
      @NonNull int svc_id, @NonNull String method, @NonNull String lang) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject svc_buy = new JsonObject();
    svc_buy.addProperty("id", id_ad);
    svc_buy.addProperty("svc_id", svc_id);
    svc_buy.addProperty("lang", lang);
    svc_buy.addProperty("ps", method);//будет параметр оплаты (личный счет или ....)
    getCode.add("svc_buy", svc_buy);
    return getCode;
  }
  public static JsonObject svc_shop_buy(@NonNull String sesid, @NonNull String id_shop,
                                   @NonNull int svc_id, @NonNull String method, @NonNull String lang) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject svc_buy = new JsonObject();
    svc_buy.addProperty("id", id_shop);
    svc_buy.addProperty("svc_id", svc_id);
    svc_buy.addProperty("lang", lang);
    svc_buy.addProperty("ps", method);//будет параметр оплаты (личный счет или ....)
    getCode.add("shop_svc_buy", svc_buy);
    return getCode;
  }
  public static JsonObject svc_abonement_buy(@NonNull String sesid, @NonNull String id_shop,
                                        @NonNull int svc_id,@NonNull String period, @NonNull String method, @NonNull String lang) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject svc_buy = new JsonObject();
    svc_buy.addProperty("shop_id", id_shop);
    svc_buy.addProperty("abonement_id", svc_id);
    svc_buy.addProperty("abonement_period", period);
    svc_buy.addProperty("lang", lang);
    svc_buy.addProperty("ps", method);//будет параметр оплаты (личный счет или ....)
    getCode.add("shop_svc_abonement_buy", svc_buy);
    return getCode;
  }
  public static JsonObject up_balance(@NonNull String sesid, @NonNull String amount,
                                               @NonNull String method, @NonNull String lang) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject up_b = new JsonObject();
    up_b.addProperty("amount", amount);
    up_b.addProperty("lang", lang);
    up_b.addProperty("ps", method);//будет параметр оплаты (личный счет или ....)
    getCode.add("user_balance_buy", up_b);
    return getCode;
  }
  public static JsonObject answerMess(@NonNull String sesid, @NonNull String text,
      @NonNull int interlocutor,
      @NonNull int shop_id, @NonNull int from_shop) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject answerMess = new JsonObject();
    answerMess.addProperty("message", text);
    answerMess.addProperty("interlocutor", (shop_id > 0&&from_shop==0) ? 0 : interlocutor);
    answerMess.addProperty("shop_id", (from_shop>0)?0:shop_id);
    answerMess.addProperty("from_shop", from_shop);
    getCode.add("intmail_add", answerMess);
    return getCode;
  }

  public static JsonObject getMessages(@NonNull String sesid, @NonNull String lang,@NonNull int interlocutor,
      @NonNull int shop_id, @NonNull int from_shop, @NonNull int page, @NonNull int onpage) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject chat_messages = new JsonObject();
    chat_messages.addProperty("interlocutor", (shop_id > 0&&from_shop==0) ? 0 : interlocutor);
    chat_messages.addProperty("shop_id", (from_shop>0)?0:shop_id);
    chat_messages.addProperty("from_shop", from_shop);
    chat_messages.addProperty("page", page);
    chat_messages.addProperty("onpage", onpage);
    chat_messages.addProperty("lang", lang);
    getCode.add("chat_messages", chat_messages);
    return getCode;
  }

  public static JsonObject sendTokenFirebase(@NonNull String sesid, @NonNull String key) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject user_set_android_push_key = new JsonObject();
    user_set_android_push_key.addProperty("key", key);
    getCode.add("user_set_android_push_key", user_set_android_push_key);
    return getCode;
  }

  public static JsonObject getMyFavs(@NonNull String sesid, @NonNull int page, @NonNull int onpage,
      @NonNull String lang) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject param = new JsonObject();
    JsonObject filters = new JsonObject();
    param.addProperty("page", page);
    param.addProperty("onpage", onpage);
    filters.addProperty("myfav", 1);
    param.addProperty("lang", lang);
    param.add("filters", filters);
    getCode.add("items_list", param);
    return getCode;
  }
  public static JsonObject getCodeCauntry(@NonNull String lang) {
    getCode = new JsonObject();
    JsonObject country_code = new JsonObject();
    country_code.addProperty("lang", lang);
    getCode.add("country_code", country_code);
    return getCode;
  }
  public static JsonObject getSmsCode(@NonNull String phone, @NonNull int user_id, @NonNull String sesid, @NonNull boolean change_phone) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject getSmsCode = new JsonObject();
    getSmsCode.addProperty("user_id", user_id);
    getSmsCode.addProperty("phone", phone);
    if (change_phone) {
      getSmsCode.addProperty("change_phone", 1);
    }

    //TODO для смс кода 333, убрать при релизе
    //getSmsCode.addProperty("test", 1);
    getCode.add("get_code", getSmsCode);
    return getCode;
  }
  public static JsonObject check_code(@NonNull String code, @NonNull int user_id, @NonNull String sesid, @NonNull boolean change_phone, @NonNull String phone) {
    getCode = new JsonObject();
    getCode.addProperty(SESID, sesid);
    JsonObject check_code = new JsonObject();
    check_code.addProperty("user_id", user_id);
    check_code.addProperty("code", code);
    if (change_phone) {
      check_code.addProperty("change_phone", 1);
      check_code.addProperty("phone", phone);
    }
    getCode.add("check_code", check_code);
    return getCode;
  }
}
