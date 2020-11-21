package solutions.isky.gaurangarevolution.presentation.mvp.adsuser;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.data.models.ShopData;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IAllUserAds extends BaseView {
    void listAds(List<AdItem> adInfos);
    void setCountrAds(String count, User user, ShopData shopData);
    void sendError(String s);
    void no_session(String text);
}
