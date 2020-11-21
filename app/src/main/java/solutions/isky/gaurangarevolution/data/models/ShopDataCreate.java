package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopDataCreate implements Serializable{
    @NonNull
    @SerializedName("title")
    private String title;


    @NonNull
    @SerializedName("descr")
    private String descr;
    @NonNull
    @SerializedName("logo")
    private String Companylogo;

    @NonNull
    @SerializedName("region_id")
    private int region_id;
    @NonNull
    @SerializedName("addr_lat")
    private double addr_lat;
    @NonNull
    @SerializedName("addr_lon")
    private double addr_lon;
    @NonNull
    @SerializedName("site")
    private String site;
    @NonNull
    @SerializedName("phones")
    private ArrayList<String> phones;
    @NonNull
    @SerializedName("addr_addr")
    private String addr_addr;

    @SerializedName("contacts")
    private Contacts_shop contacts_shop;
    @SerializedName("social")
    private List<SocialShop> socialShops;

    @SerializedName("cats")
    private List<Integer> cats;



    public ShopDataCreate() {
    }



    public ShopDataCreate(ShopData shopData) {
        this.title = shopData.getTitle();
        this.descr = shopData.getDescr();
        Companylogo = shopData.getCompanylogo();
        this.region_id = shopData.getRegion_id();
        this.addr_lat = shopData.getAddr_lat();
        this.addr_lon = shopData.getAddr_lon();
        this.site = shopData.getSite();
        this.phones = shopData.getPhones();
        this.addr_addr = shopData.getAddr_addr();
        this.contacts_shop = shopData.getContacts_shop();
        this.socialShops = shopData.getSocialShops();
        this.cats = getids(shopData.getCats());
    }
    public ShopDataCreate(ShopDataCreate shopData) {
        this.title = shopData.getTitle();
        this.descr = shopData.getDescr();
        Companylogo = shopData.getCompanylogo();
        this.region_id = shopData.getRegion_id();
        this.addr_lat = shopData.getAddr_lat();
        this.addr_lon = shopData.getAddr_lon();
        this.site = shopData.getSite();
        this.phones = shopData.getPhones();
        this.addr_addr = shopData.getAddr_addr();
        this.contacts_shop = shopData.getContacts_shop();
        this.socialShops = shopData.getSocialShops();
        this.cats = shopData.getCats();
    }
private List<Integer> getids(List<CategoryShop>categoryShops){
        List<Integer>list=new ArrayList<>();
        for(CategoryShop categoryShop:categoryShops){
            list.add(categoryShop.getId());
        }
        return list;
}
    public List<Integer> getCats() {
        return cats;
    }

    public void setCats(List<Integer> cats) {
        this.cats = cats;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescr() {
        return descr;
    }

    public void setDescr(@NonNull String descr) {
        this.descr = descr;
    }

    @NonNull
    public String getCompanylogo() {
        return Companylogo;
    }

    public void setCompanylogo(@NonNull String companylogo) {
        Companylogo = companylogo;
    }

    @NonNull
    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(@NonNull int region_id) {
        this.region_id = region_id;
    }

    @NonNull
    public double getAddr_lat() {
        return addr_lat;
    }

    public void setAddr_lat(@NonNull double addr_lat) {
        this.addr_lat = addr_lat;
    }

    @NonNull
    public double getAddr_lon() {
        return addr_lon;
    }

    public void setAddr_lon(@NonNull double addr_lon) {
        this.addr_lon = addr_lon;
    }

    @NonNull
    public String getSite() {
        return site;
    }

    public void setSite(@NonNull String site) {
        this.site = site;
    }

    @NonNull
    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(@NonNull ArrayList<String> phones) {
        this.phones = phones;
    }

    @NonNull
    public String getAddr_addr() {
        return addr_addr;
    }

    public void setAddr_addr(@NonNull String addr_addr) {
        this.addr_addr = addr_addr;
    }

    public Contacts_shop getContacts_shop() {
        return contacts_shop;
    }

    public void setContacts_shop(Contacts_shop contacts_shop) {
        this.contacts_shop = contacts_shop;
    }

    public List<SocialShop> getSocialShops() {
        return socialShops;
    }

    public void setSocialShops(List<SocialShop> socialShops) {
        this.socialShops = socialShops;
    }
}
