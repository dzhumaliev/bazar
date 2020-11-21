package solutions.isky.gaurangarevolution.di;

import android.content.Context;

import com.luck.picture.lib.permissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Component;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.di.global.module.ContextModule;
import solutions.isky.gaurangarevolution.di.global.module.DataModule;
import solutions.isky.gaurangarevolution.presentation.mvp.addad.AddAdPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.adsuser.AllAdsUserPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.advertising.AbonementShopPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.advertising.AdvertisingPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.advertising.AdvertisingShopPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.category.CategoryActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.category.CategoryShopActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.correspondence.DialogsActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.correspondence.MessageActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.favorite.MyFavPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.FilterActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.ListActivityAllFilterPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.ListShopsAllFilterPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.info_ad.InfoAdPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.locations.LocationActivityPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.login.LoginPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.login.SocialConfirmPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.main.MainPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.main.ShopsPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.ChangeEmailPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.ChangePasswordPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.ConfirmPhonePresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.EditProfilePresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.MyAdPresenterActive;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.MyPaymentsPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.MyProfilePresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.RefillPresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_profile.VerificationPhonePresenter;
import solutions.isky.gaurangarevolution.presentation.mvp.my_shop.MyShopPresenter;
import solutions.isky.gaurangarevolution.presentation.ui.locations.LocationsActivity;

/**
 * Created by isky on 02.03.2018.
 */

@Singleton
@Component(modules = {ContextModule.class, DataModule.class})
public interface AppComponent {

    Context getContext();

    ServerMethod getAuthService();

    void inject(MainPresenter mainPresenter);

    void inject(LoginPresenter loginPresenter);

    void inject(AllAdsUserPresenter allAdsUserPresenter);

    void inject(InfoAdPresenter infoAdPresenter);

    void inject(LocationActivityPresenter locationActivityPresenter);

    void inject(CategoryActivityPresenter categoryActivityPresenter);

    void inject(FilterActivityPresenter filterActivityPresenter);

    void inject(SocialConfirmPresenter socialConfirmPresenter);

    void inject(ListActivityAllFilterPresenter listActivityAllFilterPresenter);

    void inject(MyProfilePresenter myProfilePresenter);

    void inject(MyPaymentsPresenter myPaymentsPresenter);

    void inject(EditProfilePresenter editProfilePresenter);

    void inject(ChangePasswordPresenter changePasswordPresenter);

    void inject(ChangeEmailPresenter changeEmailPresenter);

    void inject(MyAdPresenterActive myAdPresenterActive);

    void inject(AddAdPresenter addAdPresenter);

    void inject(DialogsActivityPresenter dialogsActivityPresenter);

    void inject(AdvertisingPresenter advertisingPresenter);

    void inject(MessageActivityPresenter messageActivityPresenter);

    void inject(MyFavPresenter myFavPresenter);

    void inject(ShopsPresenter shopsPresenter);

    void inject(ListShopsAllFilterPresenter listShopsAllFilterPresenter);

    void inject(MyShopPresenter myShopPresenter);

    void inject(VerificationPhonePresenter verificationPhonePresenter);

    void inject(ConfirmPhonePresenter confirmPhonePresenter);

    void inject (CategoryShopActivityPresenter categoryShopActivityPresenter);

    void inject (AdvertisingShopPresenter advertisingShopPresenter);

    void inject (AbonementShopPresenter abonementShopPresenter);

    void inject(RefillPresenter refillPresenter);
}
