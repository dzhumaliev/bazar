package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdItemMy;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IMyAdsView extends BaseView {
    void listAds(List<AdItemMy> adItemMies);
    void sendError(String s);
    void no_session();
    void deactive_ad(AdItemMy adItemMy);
    void if_need_update(int is_active);
}
