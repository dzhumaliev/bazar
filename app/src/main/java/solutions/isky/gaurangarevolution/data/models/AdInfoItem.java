package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdInfoItem implements Serializable {
    @SerializedName("id")
    @NonNull
    private int id;
    @SerializedName("user_id")
    @NonNull
    private int userId;
    @SerializedName("user_ip")
    @NonNull
    private String userIp;
    @SerializedName("shop_id")
    @NonNull
    private String shopId;
    @SerializedName("status")
    @NonNull
    private String status;
    @SerializedName("status_prev")
    @NonNull
    private String statusPrev;
    @SerializedName("status_changed")
    @NonNull
    private String statusChanged;
    @SerializedName("deleted")
    @NonNull
    private String deleted;
    @SerializedName("activate_key")
    @NonNull
    private String activateKey;
    @SerializedName("activate_expire")
    @NonNull
    private String activateExpire;
    @SerializedName("cat_id")
    @NonNull
    private String catId;
    @SerializedName("cat_id1")
    @NonNull
    private String catId1;
    @SerializedName("cat_id2")
    @NonNull
    private String catId2;
    @SerializedName("cat_id3")
    @NonNull
    private String catId3;
    @SerializedName("cat_id4")
    @NonNull
    private String catId4;
    @SerializedName("cat_type")
    @NonNull
    private String catType;
    @SerializedName("owner_type")
    @NonNull
    private String ownerType;
    @SerializedName("reg1_country")
    @NonNull
    private String reg1Country;
    @SerializedName("reg2_region")
    @NonNull
    private String reg2Region;
    @SerializedName("reg3_city")
    @NonNull
    private String reg3City;
    @SerializedName("city_id")
    @NonNull
    private String cityId;
    @SerializedName("district_id")
    @NonNull
    private String districtId;
    @SerializedName("metro_id")
    @NonNull
    private String metroId;
    @SerializedName("addr_addr")
    @NonNull
    private String addrAddr;
    @SerializedName("addr_lat")
    @NonNull
    private double addrLat;
    @SerializedName("addr_lon")
    @NonNull
    private double addrLon;
    @SerializedName("title")
    @NonNull
    private String title;
    @SerializedName("title_edit")
    @NonNull
    private String titleEdit;
    @SerializedName("keyword")
    @NonNull
    private String keyword;
    @SerializedName("link")
    @NonNull
    private String link;
    @SerializedName("descr")
    @NonNull
    private String descr;
    @SerializedName("imgfav")
    @NonNull
    private String imgfav;
    @SerializedName("imgcnt")
    @NonNull
    private String imgcnt;
    @SerializedName("img_s")
    @NonNull
    private String imgS;
    @SerializedName("img_m")
    @NonNull
    private String imgM;
    @SerializedName("created")
    @NonNull
    private String created;
    @SerializedName("modified")
    @NonNull
    private String modified;
    @SerializedName("price")
    @NonNull
    private String price;
    @SerializedName("price_search")
    @NonNull
    private String priceSearch;
    @SerializedName("price_curr")
    @NonNull
    private int priceCurr;
    @SerializedName("price_ex")
    @NonNull
    private PriceEx priceEx;
    @SerializedName("name")
    @NonNull
    private String name;
    @SerializedName("phones")
    @NonNull
    private List<PhoneUser> phones = new ArrayList<PhoneUser>();
    @SerializedName("icq")
    @NonNull
    private String icq;
    @SerializedName("skype")
    @NonNull
    private String skype;
    @SerializedName("video")
    @NonNull
    private String video;
    @SerializedName("video_embed")
    @NonNull
    private String videoEmbed;
    @SerializedName("publicated")
    @NonNull
    private String publicated;
    @SerializedName("publicated_to")
    @NonNull
    private String publicatedTo;
    @SerializedName("publicated_order")
    @NonNull
    private String publicatedOrder;
    @SerializedName("svc")
    @NonNull
    private String svc;
    @SerializedName("svc_up_activate")
    @NonNull
    private String svcUpActivate;
    @SerializedName("svc_up_date")
    @NonNull
    private String svcUpDate;
    @SerializedName("svc_fixed_to")
    @NonNull
    private String svcFixedTo;
    @SerializedName("svc_fixed_order")
    @NonNull
    private String svcFixedOrder;
    @SerializedName("svc_premium_to")
    @NonNull
    private String svcPremiumTo;
    @SerializedName("svc_premium_order")
    @NonNull
    private String svcPremiumOrder;
    @SerializedName("svc_marked_to")
    @NonNull
    private String svcMarkedTo;
    @SerializedName("svc_press_status")
    @NonNull
    private String svcPressStatus;
    @SerializedName("svc_press_date")
    @NonNull
    private String svcPressDate;
    @SerializedName("svc_press_date_last")
    @NonNull
    private String svcPressDateLast;
    @SerializedName("svc_quick_to")
    @NonNull
    private String svcQuickTo;
    @SerializedName("moderated")
    @NonNull
    private String moderated;
    @SerializedName("blocked_num")
    @NonNull
    private String blockedNum;
    @SerializedName("blocked_reason")
    @NonNull
    private Object blockedReason;
    @SerializedName("views_total")
    @NonNull
    private String viewsTotal;
    @SerializedName("views_item_total")
    @NonNull
    private String viewsItemTotal;
    @SerializedName("views_contacts_total")
    @NonNull
    private String viewsContactsTotal;
    @SerializedName("views_today")
    @NonNull
    private String viewsToday;
    @SerializedName("comments_cnt")
    @NonNull
    private String commentsCnt;
    @SerializedName("claims_cnt")
    @NonNull
    private String claimsCnt;
    @SerializedName("messages_total")
    @NonNull
    private String messagesTotal;
    @SerializedName("messages_new")
    @NonNull
    private String messagesNew;
    @SerializedName("import")
    @NonNull
    private String _import;
    @SerializedName("city_title")
    @NonNull
    private String cityTitle;
    @SerializedName("price_on")
    @NonNull
    private Boolean priceOn;
    @SerializedName("price_mod")
    @NonNull
    private String priceMod;
    @SerializedName("cat_list")
    @NonNull
    private List<CategoryList> catList = new ArrayList<CategoryList>();
    @SerializedName("cat_props")
    @NonNull
    private List<CategoryProp> catProps = new ArrayList<>();
    @SerializedName("images")
    @NonNull
    private List<Image_AdInfo> images = new ArrayList<Image_AdInfo>();
    @SerializedName("user")
    @NonNull
    private User user;
    @SerializedName("similar")
    private Similar similar;
    @SerializedName("category_edit")
    private int category_edit;
    @SerializedName("item_price_display")
    private String item_price_display;
    @SerializedName("fav")
    @NonNull
    private int fav;
    @SerializedName("up_free")
    @NonNull
    private int up_free;

    @NonNull
    @SerializedName("shop_data")
    ShopData shopData;


    @NonNull
    public ShopData getShopData() {
        return shopData;
    }

    public void setShopData(@NonNull ShopData shopData) {
        this.shopData = shopData;
    }

    public void setUp_free(@NonNull int up_free) {
        this.up_free = up_free;
    }

    @NonNull
    public int getUp_free() {
        return up_free;
    }

    @NonNull
    public int getFav() {
        return fav;
    }

    public void setFav(@NonNull int fav) {
        this.fav = fav;
    }

    public String getItem_price_display() {
        return item_price_display;
    }

    public void setItem_price_display(String item_price_display) {
        this.item_price_display = item_price_display;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getUserId() {
        return userId;
    }

    public void setUserId(@NonNull int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(@NonNull String userIp) {
        this.userIp = userIp;
    }

    @NonNull
    public String getShopId() {
        return shopId;
    }

    public void setShopId(@NonNull String shopId) {
        this.shopId = shopId;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public String getStatusPrev() {
        return statusPrev;
    }

    public void setStatusPrev(@NonNull String statusPrev) {
        this.statusPrev = statusPrev;
    }

    @NonNull
    public String getStatusChanged() {
        return statusChanged;
    }

    public void setStatusChanged(@NonNull String statusChanged) {
        this.statusChanged = statusChanged;
    }

    @NonNull
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(@NonNull String deleted) {
        this.deleted = deleted;
    }

    @NonNull
    public String getActivateKey() {
        return activateKey;
    }

    public void setActivateKey(@NonNull String activateKey) {
        this.activateKey = activateKey;
    }

    @NonNull
    public String getActivateExpire() {
        return activateExpire;
    }

    public void setActivateExpire(@NonNull String activateExpire) {
        this.activateExpire = activateExpire;
    }

    @NonNull
    public String getCatId() {
        return catId;
    }

    public void setCatId(@NonNull String catId) {
        this.catId = catId;
    }

    @NonNull
    public String getCatId1() {
        return catId1;
    }

    public void setCatId1(@NonNull String catId1) {
        this.catId1 = catId1;
    }

    @NonNull
    public String getCatId2() {
        return catId2;
    }

    public void setCatId2(@NonNull String catId2) {
        this.catId2 = catId2;
    }

    @NonNull
    public String getCatId3() {
        return catId3;
    }

    public void setCatId3(@NonNull String catId3) {
        this.catId3 = catId3;
    }

    @NonNull
    public String getCatId4() {
        return catId4;
    }

    public void setCatId4(@NonNull String catId4) {
        this.catId4 = catId4;
    }

    @NonNull
    public String getCatType() {
        return catType;
    }

    public void setCatType(@NonNull String catType) {
        this.catType = catType;
    }

    @NonNull
    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(@NonNull String ownerType) {
        this.ownerType = ownerType;
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
    public String getCityId() {
        return cityId;
    }

    public void setCityId(@NonNull String cityId) {
        this.cityId = cityId;
    }

    @NonNull
    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(@NonNull String districtId) {
        this.districtId = districtId;
    }

    @NonNull
    public String getMetroId() {
        return metroId;
    }

    public void setMetroId(@NonNull String metroId) {
        this.metroId = metroId;
    }

    @NonNull
    public String getAddrAddr() {
        return addrAddr;
    }

    public void setAddrAddr(@NonNull String addrAddr) {
        this.addrAddr = addrAddr;
    }

    @NonNull
    public double getAddrLat() {
        return addrLat;
    }

    public void setAddrLat(@NonNull double addrLat) {
        this.addrLat = addrLat;
    }

    @NonNull
    public double getAddrLon() {
        return addrLon;
    }

    public void setAddrLon(@NonNull double addrLon) {
        this.addrLon = addrLon;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitleEdit() {
        return titleEdit;
    }

    public void setTitleEdit(@NonNull String titleEdit) {
        this.titleEdit = titleEdit;
    }

    @NonNull
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(@NonNull String keyword) {
        this.keyword = keyword;
    }

    @NonNull
    public String getLink() {
        return link;
    }

    public void setLink(@NonNull String link) {
        this.link = link;
    }

    @NonNull
    public String getDescr() {
        return descr;
    }

    public void setDescr(@NonNull String descr) {
        this.descr = descr;
    }

    @NonNull
    public String getImgfav() {
        return imgfav;
    }

    public void setImgfav(@NonNull String imgfav) {
        this.imgfav = imgfav;
    }

    @NonNull
    public String getImgcnt() {
        return imgcnt;
    }

    public void setImgcnt(@NonNull String imgcnt) {
        this.imgcnt = imgcnt;
    }

    @NonNull
    public String getImgS() {
        return imgS;
    }

    public void setImgS(@NonNull String imgS) {
        this.imgS = imgS;
    }

    @NonNull
    public String getImgM() {
        return imgM;
    }

    public void setImgM(@NonNull String imgM) {
        this.imgM = imgM;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    public void setCreated(@NonNull String created) {
        this.created = created;
    }

    @NonNull
    public String getModified() {
        return modified;
    }

    public void setModified(@NonNull String modified) {
        this.modified = modified;
    }

    @NonNull
    public String getPrice() {
        return price;
    }

    public void setPrice(@NonNull String price) {
        this.price = price;
    }

    @NonNull
    public String getPriceSearch() {
        return priceSearch;
    }

    public void setPriceSearch(@NonNull String priceSearch) {
        this.priceSearch = priceSearch;
    }

    @NonNull
    public int getPriceCurr() {
        return priceCurr;
    }

    public void setPriceCurr(@NonNull int priceCurr) {
        this.priceCurr = priceCurr;
    }

    @NonNull
    public PriceEx getPriceEx() {
        return priceEx;
    }

    public void setPriceEx(@NonNull PriceEx priceEx) {
        this.priceEx = priceEx;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public List<PhoneUser> getPhones() {
        return phones;
    }

    public void setPhones(@NonNull List<PhoneUser> phones) {
        this.phones = phones;
    }

    @NonNull
    public String getIcq() {
        return icq;
    }

    public void setIcq(@NonNull String icq) {
        this.icq = icq;
    }

    @NonNull
    public String getSkype() {
        return skype;
    }

    public void setSkype(@NonNull String skype) {
        this.skype = skype;
    }

    @NonNull
    public String getVideo() {
        return video;
    }

    public void setVideo(@NonNull String video) {
        this.video = video;
    }

    @NonNull
    public String getVideoEmbed() {
        return videoEmbed;
    }

    public void setVideoEmbed(@NonNull String videoEmbed) {
        this.videoEmbed = videoEmbed;
    }

    @NonNull
    public String getPublicated() {
        return publicated;
    }

    public void setPublicated(@NonNull String publicated) {
        this.publicated = publicated;
    }

    @NonNull
    public String getPublicatedTo() {
        return publicatedTo;
    }

    public void setPublicatedTo(@NonNull String publicatedTo) {
        this.publicatedTo = publicatedTo;
    }

    @NonNull
    public String getPublicatedOrder() {
        return publicatedOrder;
    }

    public void setPublicatedOrder(@NonNull String publicatedOrder) {
        this.publicatedOrder = publicatedOrder;
    }

    @NonNull
    public String getSvc() {
        return svc;
    }

    public void setSvc(@NonNull String svc) {
        this.svc = svc;
    }

    @NonNull
    public String getSvcUpActivate() {
        return svcUpActivate;
    }

    public void setSvcUpActivate(@NonNull String svcUpActivate) {
        this.svcUpActivate = svcUpActivate;
    }

    @NonNull
    public String getSvcUpDate() {
        return svcUpDate;
    }

    public void setSvcUpDate(@NonNull String svcUpDate) {
        this.svcUpDate = svcUpDate;
    }

    @NonNull
    public String getSvcFixedTo() {
        return svcFixedTo;
    }

    public void setSvcFixedTo(@NonNull String svcFixedTo) {
        this.svcFixedTo = svcFixedTo;
    }

    @NonNull
    public String getSvcFixedOrder() {
        return svcFixedOrder;
    }

    public void setSvcFixedOrder(@NonNull String svcFixedOrder) {
        this.svcFixedOrder = svcFixedOrder;
    }

    @NonNull
    public String getSvcPremiumTo() {
        return svcPremiumTo;
    }

    public void setSvcPremiumTo(@NonNull String svcPremiumTo) {
        this.svcPremiumTo = svcPremiumTo;
    }

    @NonNull
    public String getSvcPremiumOrder() {
        return svcPremiumOrder;
    }

    public void setSvcPremiumOrder(@NonNull String svcPremiumOrder) {
        this.svcPremiumOrder = svcPremiumOrder;
    }

    @NonNull
    public String getSvcMarkedTo() {
        return svcMarkedTo;
    }

    public void setSvcMarkedTo(@NonNull String svcMarkedTo) {
        this.svcMarkedTo = svcMarkedTo;
    }

    @NonNull
    public String getSvcPressStatus() {
        return svcPressStatus;
    }

    public void setSvcPressStatus(@NonNull String svcPressStatus) {
        this.svcPressStatus = svcPressStatus;
    }

    @NonNull
    public String getSvcPressDate() {
        return svcPressDate;
    }

    public void setSvcPressDate(@NonNull String svcPressDate) {
        this.svcPressDate = svcPressDate;
    }

    @NonNull
    public String getSvcPressDateLast() {
        return svcPressDateLast;
    }

    public void setSvcPressDateLast(@NonNull String svcPressDateLast) {
        this.svcPressDateLast = svcPressDateLast;
    }

    @NonNull
    public String getSvcQuickTo() {
        return svcQuickTo;
    }

    public void setSvcQuickTo(@NonNull String svcQuickTo) {
        this.svcQuickTo = svcQuickTo;
    }

    @NonNull
    public String getModerated() {
        return moderated;
    }

    public void setModerated(@NonNull String moderated) {
        this.moderated = moderated;
    }

    @NonNull
    public String getBlockedNum() {
        return blockedNum;
    }

    public void setBlockedNum(@NonNull String blockedNum) {
        this.blockedNum = blockedNum;
    }

    @NonNull
    public Object getBlockedReason() {
        return blockedReason;
    }

    public void setBlockedReason(@NonNull Object blockedReason) {
        this.blockedReason = blockedReason;
    }

    @NonNull
    public String getViewsTotal() {
        return viewsTotal;
    }

    public void setViewsTotal(@NonNull String viewsTotal) {
        this.viewsTotal = viewsTotal;
    }

    @NonNull
    public String getViewsItemTotal() {
        return viewsItemTotal;
    }

    public void setViewsItemTotal(@NonNull String viewsItemTotal) {
        this.viewsItemTotal = viewsItemTotal;
    }

    @NonNull
    public String getViewsContactsTotal() {
        return viewsContactsTotal;
    }

    public void setViewsContactsTotal(@NonNull String viewsContactsTotal) {
        this.viewsContactsTotal = viewsContactsTotal;
    }

    @NonNull
    public String getViewsToday() {
        return viewsToday;
    }

    public void setViewsToday(@NonNull String viewsToday) {
        this.viewsToday = viewsToday;
    }

    @NonNull
    public String getCommentsCnt() {
        return commentsCnt;
    }

    public void setCommentsCnt(@NonNull String commentsCnt) {
        this.commentsCnt = commentsCnt;
    }

    @NonNull
    public String getClaimsCnt() {
        return claimsCnt;
    }

    public void setClaimsCnt(@NonNull String claimsCnt) {
        this.claimsCnt = claimsCnt;
    }

    @NonNull
    public String getMessagesTotal() {
        return messagesTotal;
    }

    public void setMessagesTotal(@NonNull String messagesTotal) {
        this.messagesTotal = messagesTotal;
    }

    @NonNull
    public String getMessagesNew() {
        return messagesNew;
    }

    public void setMessagesNew(@NonNull String messagesNew) {
        this.messagesNew = messagesNew;
    }

    @NonNull
    public String get_import() {
        return _import;
    }

    public void set_import(@NonNull String _import) {
        this._import = _import;
    }

    @NonNull
    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(@NonNull String cityTitle) {
        this.cityTitle = cityTitle;
    }

    @NonNull
    public Boolean getPriceOn() {
        return priceOn;
    }

    public void setPriceOn(@NonNull Boolean priceOn) {
        this.priceOn = priceOn;
    }

    @NonNull
    public String getPriceMod() {
        return priceMod;
    }

    public void setPriceMod(@NonNull String priceMod) {
        this.priceMod = priceMod;
    }

    @NonNull
    public List<CategoryList> getCatList() {
        return catList;
    }

    public void setCatList(@NonNull List<CategoryList> catList) {
        this.catList = catList;
    }

    @NonNull
    public List<CategoryProp> getCatProps() {
        return catProps;
    }

    public void setCatProps(@NonNull List<CategoryProp> catProps) {
        this.catProps = catProps;
    }

    @NonNull
    public List<Image_AdInfo> getImages() {
        return (images != null) ? images : new ArrayList<>();
    }

    public void setImages(@NonNull List<Image_AdInfo> images) {
        this.images = images;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    public Similar getSimilar() {
        return similar;
    }

    public void setSimilar(Similar similar) {
        this.similar = similar;
    }

    public int getCategory_edit() {
        return category_edit;
    }

    public void setCategory_edit(int category_edit) {
        this.category_edit = category_edit;
    }

    public class Similar implements Serializable {
        @SerializedName("rows")
        @NonNull
        private List<AdInfo> adInfos = new ArrayList<AdInfo>();

        @NonNull
        public List<AdInfo> getAdInfos() {
            return adInfos;
        }

        public void setAdInfos(@NonNull List<AdInfo> adInfos) {
            this.adInfos = adInfos;
        }
    }
}