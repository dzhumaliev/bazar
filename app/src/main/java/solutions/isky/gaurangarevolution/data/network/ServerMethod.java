package solutions.isky.gaurangarevolution.data.network;


import com.google.gson.JsonObject;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import solutions.isky.gaurangarevolution.data.event.AddPosterSuccess;
import solutions.isky.gaurangarevolution.data.event.BuyAdv;
import solutions.isky.gaurangarevolution.data.event.EditInfo;
import solutions.isky.gaurangarevolution.data.event.GetAdInfo;
import solutions.isky.gaurangarevolution.data.event.GetBindSocial;
import solutions.isky.gaurangarevolution.data.event.GetCategoryList;
import solutions.isky.gaurangarevolution.data.event.GetChatList;
import solutions.isky.gaurangarevolution.data.event.GetCityInfo;
import solutions.isky.gaurangarevolution.data.event.GetCountPosters;
import solutions.isky.gaurangarevolution.data.event.GetCountryCode;
import solutions.isky.gaurangarevolution.data.event.GetListLocation;
import solutions.isky.gaurangarevolution.data.event.GetListPoster;
import solutions.isky.gaurangarevolution.data.event.GetListPropsDyns;
import solutions.isky.gaurangarevolution.data.event.GetMessList;
import solutions.isky.gaurangarevolution.data.event.GetMyPayments;
import solutions.isky.gaurangarevolution.data.event.GetMyPoster;
import solutions.isky.gaurangarevolution.data.event.GetRegister;
import solutions.isky.gaurangarevolution.data.event.GetSettings;
import solutions.isky.gaurangarevolution.data.event.GetShopCategoryList;
import solutions.isky.gaurangarevolution.data.event.GetShopsList;
import solutions.isky.gaurangarevolution.data.event.GetSvcAbonement;
import solutions.isky.gaurangarevolution.data.event.GetSvcData;
import solutions.isky.gaurangarevolution.data.event.GetSvcShopData;
import solutions.isky.gaurangarevolution.data.event.GetUserInfo;
import solutions.isky.gaurangarevolution.data.event.SendImage;
import solutions.isky.gaurangarevolution.data.event.UpdateFavorite;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.CreateShop;
import solutions.isky.gaurangarevolution.data.models.EditShop;

/**
 * Created by isky on 02.03.2018.
 */

public class ServerMethod {
    private GhostApiService mGhostApiService;

    public ServerMethod(GhostApiService ghostApiService) {
        mGhostApiService = ghostApiService;
    }

    public ServerMethod() {
    }

    public Single<GetListPoster> getListPosterSingle(JsonObject code) {
        return mGhostApiService.getListProdukt(code);
    }

    public Single<GetRegister> login(JsonObject code) {
        return mGhostApiService.login(code);
    }

    public Single<GetAdInfo> getInfoAd(JsonObject code) {
        return mGhostApiService.getInfoAd(code);
    }

    public Single<UpdateFavorite> update_fav(JsonObject code) {
        return mGhostApiService.update_fav(code);
    }

    public Single<CapObject> sendClickPhone(JsonObject code) {
        return mGhostApiService.sendClickPhone(code);
    }

    public Single<CapObject> send_mess(JsonObject code) {
        return mGhostApiService.sendmess(code);
    }

    public Single<CapObject> user_logout(JsonObject code) {
        return mGhostApiService.user_logout(code);
    }

    public Single<CapObject> send_complain(JsonObject code) {
        return mGhostApiService.sendcomplain(code);
    }

    public Single<GetRegister> login_fb(JsonObject code) {
        return mGhostApiService.login_fb(code);
    }

    public Single<GetListPoster> getListProdukt(JsonObject code) {
        return mGhostApiService.getListProdukt(code);
    }

    public Single<GetRegister> register(JsonObject code) {
        return mGhostApiService.register(code);
    }

    public Single<GetListLocation> getListLocation(JsonObject code) {
        return mGhostApiService.getListLocation(code);
    }

    public Single<GetCategoryList> getCategoryList(JsonObject code) {
        return mGhostApiService.getCategoryList(code);
    }
    public Single<GetShopCategoryList> getShopCategoryList(JsonObject code) {
        return mGhostApiService.getShopCategoryList(code);
    }
    public Single<GetCountPosters> getCountPosters(JsonObject code) {
        return mGhostApiService.getCountPosters(code);
    }

    public Single<GetListPropsDyns> getDinProps(JsonObject code) {
        return mGhostApiService.getPropsDyns(code);
    }

    public Single<GetMyPayments> getBillings(JsonObject code) {
        return mGhostApiService.getBillings(code);
    }

    public Single<GetUserInfo> getInfo(JsonObject jsonObject) {
        return mGhostApiService.getInfoUser(jsonObject);
    }

    public Single<GetCityInfo> cityInfo(JsonObject jsonObject) {
        return mGhostApiService.cityInfo(jsonObject);
    }
    public Single<CapObject> createShop(CreateShop shopData) {
        return mGhostApiService.create_shop(shopData);
    }
    public Single<CapObject> createShop(EditShop editShop) {
        return mGhostApiService.create_shop(editShop);
    }
    public Single<SendImage> sendIMG(MultipartBody.Part part) {
        return mGhostApiService.sendImage(part);
    }

    public Single<CapObject> sendavatar(JsonObject code) {
        return mGhostApiService.sendavatar(code);
    }

    public Single<CapObject> changePassword(JsonObject code) {
        return mGhostApiService.changePassword(code);
    }

    public Single<CapObject> changeEmail(JsonObject code) {
        return mGhostApiService.changeEmail(code);
    }

    public Single<GetBindSocial> bind_social(JsonObject code) {
        return mGhostApiService.bind_social(code);
    }
    public Single<GetUserInfo> unlink_social(JsonObject code) {
        return mGhostApiService.unlink_social(code);
    }
    public Single<GetMyPoster> getMyProdukt(JsonObject code) {
        return mGhostApiService.getMyProdukt(code);
    }
    public Single<GetShopsList> getShops(JsonObject code) {
        return mGhostApiService.getshops(code);
    }

    public Single<GetUserInfo> editInfo(JsonObject jsonObject) {
        return mGhostApiService.editInfoUser(jsonObject);
    }

    public Single<GetSettings> getSettings(JsonObject jsonObject) {
        return mGhostApiService.settings(jsonObject);
    }

    public Single<CapObject> item_freeup(JsonObject code) {
        return mGhostApiService.item_freeup(code);
    }

    public Single<CapObject> item_change_status(JsonObject code) {
        return mGhostApiService.item_change_status(code);
    }

    public Single<AddPosterSuccess> addPoster(JsonObject code) {
        return mGhostApiService.addNewAd(code);
    }

    public Single<EditInfo> editPoster(JsonObject code) {
        return mGhostApiService.editAd(code);
    }

    public Single<GetChatList> getDialogsList(JsonObject code) {
        return mGhostApiService.getChats(code);
    }

    public Single<GetSvcData> get_svc(JsonObject code) {
        return mGhostApiService.get_svc(code);
    }
    public Single<GetSvcShopData> get_svc_shop(JsonObject code) {
        return mGhostApiService.get_svc_shop(code);
    }
    public Single<BuyAdv> buy_svc(JsonObject code) {
        return mGhostApiService.buy_svc_ad(code);
    }
    public Single<BuyAdv> up_balance(JsonObject code) {
        return mGhostApiService.buy_svc_shop(code);
    }
    public Single<BuyAdv> buy_shop_svc(JsonObject code) {
        return mGhostApiService.buy_svc_shop(code);
    }
    public Single<GetMessList> getMessageList(JsonObject code) {
        return mGhostApiService.getMessageList(code);
    }

    public Single<CapObject> answerMessages(JsonObject code) {
        return mGhostApiService.answerMessages(code);
    }
    public Single<CapObject> sendTokenFirebase(JsonObject code) {
        return mGhostApiService.sendTokenFirebase(code);
    }
    public Single<GetCountryCode> getCountryCode(JsonObject code) {
        return mGhostApiService.getcountrycode(code);
    }
    public Single<CapObject> getSmsCode(JsonObject code) {
        return mGhostApiService.get_code(code);
    }
    public Single<GetRegister> checkCode(JsonObject code) {
        return mGhostApiService.check_code(code);
    }

    public Single<GetSvcAbonement> get_svc_abonement(JsonObject code) {
        return mGhostApiService.get_svc_abonement(code);
    }

}