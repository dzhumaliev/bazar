package solutions.isky.gaurangarevolution.presentation.mvp.favorite;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IAddFav extends BaseView {
    void listAds(List<AdItem> adItems);
    void sendError(String s);
    void no_session();
}
