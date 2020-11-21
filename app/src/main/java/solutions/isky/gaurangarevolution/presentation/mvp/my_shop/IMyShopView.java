package solutions.isky.gaurangarevolution.presentation.mvp.my_shop;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import solutions.isky.gaurangarevolution.data.models.RegionInfo;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IMyShopView extends BaseView {

    void showProgresAvatar();
    void hideProgresavatar();
    void sendError(String text);

    void no_session();
    void getNewUriLogo(String uri);

    @StateStrategyType(SkipStrategy.class)
    void resetAvatar(String uri);

    void setInitShop(ShopData shopData);

    void setCityInfo(RegionInfo cityInfo);

    void create_shop(String title);
    void edit_shop(String title);

}
