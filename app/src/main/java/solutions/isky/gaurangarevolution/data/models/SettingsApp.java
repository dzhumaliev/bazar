package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class SettingsApp {

    @SerializedName("users.profile.phones")
    @NonNull
    private int phones_fild;
    @SerializedName("currency.defaul")
    @NonNull
    private int currency_defaul;
    @SerializedName("users.register.social.email.activation")
    @NonNull
    private int social_email_activation;
    @SerializedName("bbs.premoderation")
    @NonNull
    private int premoderation;
    @SerializedName("shops.exist")
    @NonNull
    private int shops_available;
    @SerializedName("shops.categories.enabled")
    @NonNull
    private int shops_categories_enabled;
    @SerializedName("shops.categories.limit")
    @NonNull
    private int shops_categories_limit;
    @SerializedName("users.register.phone")
    @NonNull
    private int users_register_phone;
    @SerializedName("shops.abonement")
    @NonNull
    private int shops_abonement;
    @SerializedName("users.register.mode")
    @NonNull
    private int users_register_mode;

    @SerializedName("doirep.noitacilbup.mrof.sbb")
    @NonNull
    private int periodEenabled;

    @SerializedName("bbs_item_publication_period")
    @NonNull
    private int publicationPeriod;

    @SerializedName("bbs_item_publication_periods")
    @NonNull
    private JsonElement dataPeriods;

    @NonNull
    public JsonElement getDataPeriods() {
        return dataPeriods!=null?dataPeriods:new JsonObject();
    }

    public void setDataPeriods(@NonNull JsonElement dataPeriods) {
        this.dataPeriods = dataPeriods;
    }

    public int getPublicationPeriod() {
        return publicationPeriod;
    }

    public void setPublicationPeriod(int publicationPeriod) {
        this.publicationPeriod = publicationPeriod;
    }

    public int getPeriodEenabled() {
        return periodEenabled;
    }

    public void setPeriodEenabled(int periodEenabled) {
        this.periodEenabled = periodEenabled;
    }

    public int getUsers_register_mode() {
        return users_register_mode;
    }

    public void setUsers_register_mode(int users_register_mode) {
        this.users_register_mode = users_register_mode;
    }

    @NonNull
    public int getShops_abonement() {
        return shops_abonement;
    }

    public void setShops_abonement(@NonNull int shops_abonement) {
        this.shops_abonement = shops_abonement;
    }

    @NonNull
    public int getShops_available() {
        return shops_available;
    }

    public void setShops_available(@NonNull int shops_available) {
        this.shops_available = shops_available;
    }

    @NonNull
    public int getShops_categories_enabled() {
        return shops_categories_enabled;
    }

    public void setShops_categories_enabled(@NonNull int shops_categories_enabled) {
        this.shops_categories_enabled = shops_categories_enabled;
    }

    @NonNull
    public int getShops_categories_limit() {
        return shops_categories_limit;
    }

    public void setShops_categories_limit(@NonNull int shops_categories_limit) {
        this.shops_categories_limit = shops_categories_limit;
    }

    @NonNull
    public int getUsers_register_phone() {
        return users_register_phone;
    }

    public void setUsers_register_phone(@NonNull int users_register_phone) {
        this.users_register_phone = users_register_phone;
    }

    @NonNull
    public int getPhones_fild() {
        return phones_fild;
    }

    public void setPhones_fild(@NonNull int phones_fild) {
        this.phones_fild = phones_fild;
    }

    @NonNull
    public int getCurrency_defaul() {
        return currency_defaul;
    }

    public void setCurrency_defaul(@NonNull int currency_defaul) {
        this.currency_defaul = currency_defaul;
    }

    @NonNull
    public int getSocial_email_activation() {
        return social_email_activation;
    }

    public void setSocial_email_activation(@NonNull int social_email_activation) {
        this.social_email_activation = social_email_activation;
    }

    @NonNull
    public int getPremoderation() {
        return premoderation;
    }

    public void setPremoderation(@NonNull int premoderation) {
        this.premoderation = premoderation;
    }
}
