package solutions.isky.gaurangarevolution.presentation.mvp.filters;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IListActivityFilterView extends BaseView {
    void listAds(List<AdItem> adInfos);
    void setLastPage(boolean is_last);
    void no_session(String text);
    void showError(String error);
}
