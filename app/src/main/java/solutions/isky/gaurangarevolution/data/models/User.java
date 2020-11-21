package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serzhik on 21.04.2016.
 */
public class User implements Serializable {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("shop_id")
    @NonNull
    private int shop_id;

    @SerializedName("user_id_ex")
    @NonNull
    private String userIdEx;
    @SerializedName("admin")
    @NonNull
    private String admin;
    @SerializedName("member")
    @NonNull
    private String member;
    @SerializedName("login")
    @NonNull
    private String login;
    @SerializedName("email")
    @NonNull
    private String email;
    @SerializedName("phones")
    @NonNull
    ArrayList<String> phones;
    @SerializedName("phone_number")
    @NonNull
    private String phoneNumber;
    @SerializedName("balance")
    @NonNull
    private String balance;
    @SerializedName("balance_spent")
    @NonNull
    private String balanceSpent;
    @SerializedName("reg1_country")
    @NonNull
    private String reg1Country;
    @SerializedName("reg2_region")
    @NonNull
    private String reg2Region;
    @SerializedName("reg3_city")
    @NonNull
    private String reg3City;
    @SerializedName("region_id")
    @NonNull
    private int regionId;
    @SerializedName("name")
    @NonNull
    private String name;
    @SerializedName("surname")
    @NonNull
    private String surname;
    @SerializedName("avatar")
    @NonNull
    private String avatar;
    @SerializedName("avatar_crop")
    @NonNull
    private String avatarCrop;
    @SerializedName("blocked")
    @NonNull
    private String blocked;
    @SerializedName("blocked_reason")
    @NonNull
    private String blockedReason;
    @SerializedName("birthdate")
    @NonNull
    private String birthdate;
    @SerializedName("sex")
    @NonNull
    private String sex;
    @SerializedName("about")
    @NonNull
    private String about;
    @SerializedName("skype")
    @NonNull
    private String skype;
    @SerializedName("icq")
    @NonNull
    private String icq;
    @SerializedName("site")
    @NonNull
    private String site;
    @SerializedName("im_noreply")
    @NonNull
    private String imNoreply;
    @SerializedName("enotify")
    @NonNull
    private String enotify;
    @SerializedName("social_id")
    @NonNull
    private String socialId;
    @SerializedName("password")
    @NonNull
    private String password;
    @SerializedName("currency")
    @NonNull
    private String currency;
    @SerializedName("internalmail_new")
    @NonNull
    private String internalmail_new;
    @SerializedName("error")
    @NonNull
    private String error;
    @SerializedName("phone")
    @NonNull
    private String phone;
    @SerializedName("user_id")
    @NonNull
    private int user_id;
    @SerializedName("phone_number_verified")
    @NonNull
    private int phone_number_verified;
    @SerializedName("activated")
    @NonNull
    private int activated;
    @SerializedName("soc_providers")
    @NonNull
    private List<Integer> soc_providers;
    @NonNull
    @SerializedName("balance_display")
    private String balance_display;
    @NonNull
    @SerializedName("items_fav")
    private int items_fav;
    @NonNull
    @SerializedName("last_activity")
    private String last_activity;
    @NonNull
    @SerializedName("created")
    private String created;
    @NonNull
    @SerializedName("addr_lat")
    private double addr_lat;
    @NonNull
    @SerializedName("addr_lon")
    private double addr_lon;
    @NonNull
    @SerializedName("balance_bonus")
    private float balance_bonus;
    @NonNull
    @SerializedName("shop_title")
    private String shop_title;
    @NonNull
    @SerializedName("shop_descr")
    private String shop_descr;
    @NonNull
    @SerializedName("shop_logo")
    private String shop_logo;
    @NonNull
    @SerializedName("shop_data")
    private ShopData  shopData;


    @NonNull
    public ShopData getShopData() {
        return shopData;
    }

    public void setShopData(@NonNull ShopData shopData) {
        this.shopData = shopData;
    }

    @NonNull
    @SerializedName("fav")
    private int fav;

    @NonNull
    public ArrayList<String> getPhones() {
        return (phones!=null)?phones:new ArrayList<>();
    }

    @NonNull
    public int getFav() {
        return fav;
    }

    public void setFav(@NonNull int fav) {
        this.fav = fav;
    }

    @NonNull
    public String getShop_title() {
        return shop_title;
    }

    public void setShop_title(@NonNull String shop_title) {
        this.shop_title = shop_title;
    }

    @NonNull
    public String getShop_descr() {
        return shop_descr;
    }

    public void setShop_descr(@NonNull String shop_descr) {
        this.shop_descr = shop_descr;
    }

    @NonNull
    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(@NonNull String shop_logo) {
        this.shop_logo = shop_logo;
    }

    @NonNull
    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(@NonNull int shop_id) {
        this.shop_id = shop_id;
    }

    @NonNull
    public float getBalance_bonus() {
        return balance_bonus;
    }

    public void setBalance_bonus(@NonNull float balance_bonus) {
        this.balance_bonus = balance_bonus;
    }

    @NonNull
    public double getAddr_lon() {
        return addr_lon;
    }

    @NonNull
    public double getAddr_lat() {
        return addr_lat;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    @NonNull
    public String getLast_activity() {
        return last_activity;
    }

    @NonNull
    public int getItems_fav() {
        return items_fav;
    }

    public void setItems_fav(@NonNull int items_fav) {
        this.items_fav = items_fav;
    }

    @NonNull
    public String getBalance_display() {
        return balance_display;
    }

    public void setBalance_display(@NonNull String balance_display) {
        this.balance_display = balance_display;
    }

    @NonNull
    public List<Integer> getSoc_providers() {
        return (soc_providers!=null)?soc_providers:new ArrayList<>();
    }

    public void setSoc_providers(@NonNull List<Integer> soc_providers) {
        this.soc_providers = soc_providers;
    }

    @NonNull
    public int getPhone_number_verified() {
        return phone_number_verified;
    }

    public void setPhone_number_verified(@NonNull int phone_number_verified) {
        this.phone_number_verified = phone_number_verified;
    }

    @NonNull
    public int getActivated() {
        return activated;
    }

    public void setActivated(@NonNull int activated) {
        this.activated = activated;
    }

    @NonNull
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(@NonNull int user_id) {
        this.user_id = user_id;
    }

    @NonNull
    public String getError() {
        return error;
    }

    public void setError(@NonNull String error) {
        this.error = error;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getAvatarCrop() {
        return avatarCrop;
    }

    @NonNull
    public String getBlockedReason() {
        return blockedReason;
    }

    @NonNull
    public String getAbout() {
        return about;
    }

    public void setAvatarCrop(@NonNull String avatarCrop) {
        this.avatarCrop = avatarCrop;
    }

    public void setBlockedReason(@NonNull String blockedReason) {
        this.blockedReason = blockedReason;
    }

    public void setAbout(@NonNull String about) {
        this.about = about;
    }

    @NonNull
    public String getInternalmail_new() {
        return internalmail_new;
    }

    public void setInternalmail_new(@NonNull String internalmail_new) {
        this.internalmail_new = internalmail_new;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@NonNull String currency) {
        this.currency = currency;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }


    @NonNull
    public String getAdmin() {
        return admin;
    }

    public void setAdmin(@NonNull String admin) {
        this.admin = admin;
    }

    @NonNull
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@NonNull String avatar) {
        this.avatar = avatar;
    }



    @NonNull
    public String getBalance() {
        return balance;
    }

    public void setBalance(@NonNull String balance) {
        this.balance = balance;
    }

    @NonNull
    public String getBalanceSpent() {
        return balanceSpent;
    }

    public void setBalanceSpent(@NonNull String balanceSpent) {
        this.balanceSpent = balanceSpent;
    }

    @NonNull
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(@NonNull String birthdate) {
        this.birthdate = birthdate;
    }

    @NonNull
    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(@NonNull String blocked) {
        this.blocked = blocked;
    }



    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getEnotify() {
        return enotify;
    }

    public void setEnotify(@NonNull String enotify) {
        this.enotify = enotify;
    }

    @NonNull
    public String getIcq() {
        return icq;
    }

    public void setIcq(@NonNull String icq) {
        this.icq = icq;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getImNoreply() {
        return imNoreply;
    }

    public void setImNoreply(@NonNull String imNoreply) {
        this.imNoreply = imNoreply;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    @NonNull
    public String getMember() {
        return member;
    }

    public void setMember(@NonNull String member) {
        this.member = member;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getReg1Country() {
        return reg1Country;
    }

    public void setReg1Country(@NonNull String reg1Country) {
        this.reg1Country = reg1Country;
    }

    @NonNull
    public String getReg2Region() {
        return reg2Region;
    }

    public void setReg2Region(@NonNull String reg2Region) {
        this.reg2Region = reg2Region;
    }

    @NonNull
    public String getReg3City() {
        return reg3City;
    }

    public void setReg3City(@NonNull String reg3City) {
        this.reg3City = reg3City;
    }

    @NonNull
    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(@NonNull int regionId) {
        this.regionId = regionId;
    }

    @NonNull
    public String getSex() {
        return sex;
    }

    public void setSex(@NonNull String sex) {
        this.sex = sex;
    }

    @NonNull
    public String getSite() {
        return site;
    }

    public void setSite(@NonNull String site) {
        this.site = site;
    }

    @NonNull
    public String getSkype() {
        return skype;
    }

    public void setSkype(@NonNull String skype) {
        this.skype = skype;
    }

    @NonNull
    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(@NonNull String socialId) {
        this.socialId = socialId;
    }

    @NonNull
    public String getSurname() {
        return surname;
    }

    public void setSurname(@NonNull String surname) {
        this.surname = surname;
    }

    @NonNull
    public String getUserIdEx() {
        return userIdEx;
    }

    public void setUserIdEx(@NonNull String userIdEx) {
        this.userIdEx = userIdEx;
    }
}
