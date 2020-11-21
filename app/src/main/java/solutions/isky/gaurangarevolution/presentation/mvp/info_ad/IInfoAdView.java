package solutions.isky.gaurangarevolution.presentation.mvp.info_ad;


import android.content.Intent;

import solutions.isky.gaurangarevolution.data.event.GetAdInfo;
import solutions.isky.gaurangarevolution.data.models.AdInfoItem;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IInfoAdView extends BaseView {
    void getInfoPost(GetAdInfo getAdInfo);

    void show_content();

    void hide_content();

    void show_error(String s);

    void openMap(Intent iMap);

    void no_session(String text);

    void update_favorite(boolean ia_ad_delete);

    void edit_ad(AdInfoItem adInfoItem);

    void deactive_ad(AdInfoItem adInfoItem);

    void delete();

    void unpublic(AdInfoItem adInfoItem);

    void publicate(AdInfoItem adInfoItem);

    void freeup(AdInfoItem adInfoItem);

    void complain_confirm();

    void message_send();

    void enabledBtnSend();
}
