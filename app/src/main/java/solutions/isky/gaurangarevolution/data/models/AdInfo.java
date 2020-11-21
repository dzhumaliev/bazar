package solutions.isky.gaurangarevolution.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdInfo implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("status")
    private int status;
    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;
    @SerializedName("img_s")
    private String imgS;
    @SerializedName("img_m")
    private String imgM;
    @SerializedName("imgs")
    private String imgs;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("addr_addr")
    private String addrAddr;
    @SerializedName("price")
    private String price;
    @SerializedName("price_curr")
    private int priceCurr;
    @SerializedName("price_ex")
    private PriceEx priceEx;
    @SerializedName("district_id")
    private String districtId;
    @SerializedName("svc_marked")
    private String svcMarked;
    @SerializedName("svc_fixed")
    private String svcFixed;
    @SerializedName("svc_quick")
    private String svcQuick;
    @SerializedName("svc_up")
    private String svcUp;
    @SerializedName("publicated")
    private String publicated;
    @SerializedName("modified")
    private String modified;
    @SerializedName("price_on")
    private boolean priceOn;
    @SerializedName("cat_title")
    private String catTitle;
    @SerializedName("city_title")
    private String cityTitle;
    @SerializedName("fav")
    private int fav;
    @SerializedName("price_mod")
    private String priceMod;
    @SerializedName("publicated_to")
    private String publicated_to;
    @SerializedName("item_price_display")
    private String item_price_display;

    public String getItem_price_display() {
        return item_price_display;
    }

    public void setItem_price_display(String item_price_display) {
        this.item_price_display = item_price_display;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgS() {
        return imgS;
    }

    public void setImgS(String imgS) {
        this.imgS = imgS;
    }

    public String getImgM() {
        return imgM;
    }

    public void setImgM(String imgM) {
        this.imgM = imgM;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddrAddr() {
        return addrAddr;
    }

    public void setAddrAddr(String addrAddr) {
        this.addrAddr = addrAddr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPriceCurr() {
        return priceCurr;
    }

    public void setPriceCurr(int priceCurr) {
        this.priceCurr = priceCurr;
    }

    public PriceEx getPriceEx() {
        return priceEx;
    }

    public void setPriceEx(PriceEx priceEx) {
        this.priceEx = priceEx;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getSvcMarked() {
        return svcMarked;
    }

    public void setSvcMarked(String svcMarked) {
        this.svcMarked = svcMarked;
    }

    public String getSvcFixed() {
        return svcFixed;
    }

    public void setSvcFixed(String svcFixed) {
        this.svcFixed = svcFixed;
    }

    public String getSvcQuick() {
        return svcQuick;
    }

    public void setSvcQuick(String svcQuick) {
        this.svcQuick = svcQuick;
    }

    public String getSvcUp() {
        return svcUp;
    }

    public void setSvcUp(String svcUp) {
        this.svcUp = svcUp;
    }

    public String getPublicated() {
        return publicated;
    }

    public void setPublicated(String publicated) {
        this.publicated = publicated;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public boolean isPriceOn() {
        return priceOn;
    }

    public void setPriceOn(boolean priceOn) {
        this.priceOn = priceOn;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getPriceMod() {
        return priceMod;
    }

    public void setPriceMod(String priceMod) {
        this.priceMod = priceMod;
    }

    public String getPublicated_to() {
        return publicated_to;
    }

    public void setPublicated_to(String publicated_to) {
        this.publicated_to = publicated_to;
    }
}