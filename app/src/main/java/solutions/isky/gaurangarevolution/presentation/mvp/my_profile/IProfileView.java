package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdItem;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

/**
 * Created by sergey on 21.03.18.
 */

public interface IProfileView extends BaseView {

    void sendError(String s);
    void no_session(String text);
    void userLogOut();
}
