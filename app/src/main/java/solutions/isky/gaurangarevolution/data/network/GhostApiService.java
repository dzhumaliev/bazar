package solutions.isky.gaurangarevolution.data.network;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

public interface GhostApiService {

    @POST("bbs")
    Single<GetListPoster> getListProdukt(@Body JsonObject code);

    @POST("user")
    Single<GetRegister> login(@Body JsonObject code); //вход через email и пароль

    @POST("bbs")
    Single<GetAdInfo> getInfoAd(@Body JsonObject code); // detail info ad

    @POST("bbs")
    Single<UpdateFavorite> update_fav(@Body JsonObject code); //изменение избранных

    @POST("bbs")
    Single<CapObject> sendClickPhone(@Body JsonObject code);// счетчик просмотра телефона

    @POST("user")
    Single<CapObject> sendmess(@Body JsonObject code);// отпрака сообщения из объявления

    @POST("bbs")
    Single<CapObject> sendcomplain(@Body JsonObject code);// отпрака жалобы из объявления

    @POST("user")
    Single<GetRegister> login_fb(@Body JsonObject code); //регистрация через fb

    @POST("user")
    Single<CapObject> user_logout(@Body JsonObject code); //выход

    @POST("user")
    Single<GetRegister> register(@Body JsonObject code); //регистрация через email и пароль

    @POST("bbs")
    Single<GetListLocation> getListLocation(@Body JsonObject code);// список городов

    @POST("cats")
    Single<GetCategoryList> getCategoryList(@Body JsonObject code); // дерево категорий
    @POST("user")
    Single<GetShopCategoryList> getShopCategoryList(@Body JsonObject code); // дерево категорий для магазинов
    @POST("bbs")
    Single<GetCountPosters> getCountPosters(@Body JsonObject code); // количество объявлений

    @POST("cats")
    Single<GetListPropsDyns> getPropsDyns(@Body JsonObject code); // список динамичкеских свойств

    @POST("user")
    Single<GetMyPayments> getBillings(@Body JsonObject code); //список платежей

    @POST("user")
    Single<GetUserInfo> getInfoUser(@Body JsonObject code); //получение профиля

    @POST("bbs")
    Single<GetCityInfo> cityInfo(@Body JsonObject code); //информацияо городе

    @Multipart
    @POST("img")
    Single<SendImage> sendImage(@Part MultipartBody.Part image); //загрузка images

    @POST("user")
    Single<CapObject> sendavatar(@Body JsonObject code); //изменить аватар

    @POST("user")
    Single<CapObject> create_shop(@Body CreateShop shop); //создать магазин
    @POST("user")
    Single<CapObject> create_shop(@Body EditShop shop); //изменить магазин
    @POST("user")
    Single<CapObject> changePassword(@Body JsonObject code); //изменить пароль пользователя
    @POST("user")
    Single<CapObject> changeEmail(@Body JsonObject code); //изменить email пользователя

    @POST("user")
    Single<GetBindSocial> bind_social(@Body JsonObject code); //связать с социальными сетями
    @POST("user")
    Single<GetUserInfo> unlink_social(@Body JsonObject code); //отвязать привязанную сеть
    @POST("bbs")
    Single<GetMyPoster> getMyProdukt(@Body JsonObject code); // мои объявления
    @POST("user")
    Single<GetShopsList> getshops(@Body JsonObject code); // список магазинов
    @POST("user")
    Single<GetUserInfo> editInfoUser(@Body JsonObject code); //изменение профиля

    @POST("user")
    Single<GetSettings> settings(@Body JsonObject code); //получение глобальных настроек

    @POST("bbs")
    Single<CapObject> item_freeup(@Body JsonObject code); // безплатно поднять объявление

    @POST("bbs")
    Single<CapObject> item_change_status(@Body JsonObject code); // изменить статус объявления

    @POST("bbs")
    Single<AddPosterSuccess> addNewAd(@Body JsonObject code); //подача объявления

    @POST("bbs")
    Single<EditInfo> editAd(@Body JsonObject code); //изменение объявления

    @POST("user")
    Single<GetChatList> getChats(@Body JsonObject code); //список диалогов

    @POST("bbs")
    Single<GetSvcData> get_svc(@Body JsonObject code); //получение svc

    @POST("user")
    Single<GetSvcAbonement> get_svc_abonement(@Body JsonObject code); //получение списка абониментов
    @POST("user")
    Single<GetSvcShopData> get_svc_shop(@Body JsonObject code); //получение svc shop
    @POST("bbs")
    Single<BuyAdv> buy_svc_ad(@Body JsonObject code); //оплата svc
    @POST("user")
    Single<BuyAdv> buy_svc_shop(@Body JsonObject code); //оплата svc shop
    @POST("user")
    Single<GetMessList> getMessageList(@Body JsonObject code); //список диалогов
    @POST("user")
    Single<CapObject> answerMessages(@Body JsonObject code); //переписка

    @POST("user")
    Single<CapObject> sendTokenFirebase(@Body JsonObject code); //отправка токена

    @POST("bbs")
    Single<GetCountryCode> getcountrycode(@Body JsonObject code); //получение кодов стран

    @POST("user")
    Single<CapObject> get_code(@Body JsonObject code); //запрос смс кода

    @POST("user")
    Single<GetRegister> check_code(@Body JsonObject code); //поодверждение кода и телефона
}
