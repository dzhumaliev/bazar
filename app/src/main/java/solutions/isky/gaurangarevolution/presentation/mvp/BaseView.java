package solutions.isky.gaurangarevolution.presentation.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by sergey on 21.03.18.
 */

public interface BaseView extends MvpView {
    void showProgres();
    void hideProgres();
    @StateStrategyType(SkipStrategy.class)
    void showNoNetworkLayout();

}
