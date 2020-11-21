package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import android.net.Uri;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import solutions.isky.gaurangarevolution.data.models.RegionInfo;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IEditProfile extends BaseView {
    void sendError(String text);

    void no_session();

    void setInitUser(User user);

    @StateStrategyType(SkipStrategy.class)
    void resetAvatar(String uri);

    @StateStrategyType(SkipStrategy.class)
    void setPreview(Uri uri_avatar);

    void setCityInfo(RegionInfo cityInfo);

    void showProgresAvatar();
    void hideProgresavatar();
    void avatar_load();

    void showErrorPhone(String text);
}
