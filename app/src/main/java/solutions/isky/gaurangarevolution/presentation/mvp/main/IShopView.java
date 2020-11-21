package solutions.isky.gaurangarevolution.presentation.mvp.main;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdItemMy;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IShopView extends BaseView {
    void listAds(List<ShopData> shopData);
    void sendError(String s);
    void no_session();
}
