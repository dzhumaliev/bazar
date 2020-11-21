package solutions.isky.gaurangarevolution.presentation.mvp.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

/**
 * Created by sergey on 21.03.18.
 */
@StateStrategyType(SkipStrategy.class)
public interface IMainView extends BaseView {
    void listAds(List<AdItem> adInfos);
    void sendError(String s);
    void no_session(String text);

    void userLogin();
    void userNotLogin();
    void userLogOut();

    void get_setSettings();
}
