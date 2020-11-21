package solutions.isky.gaurangarevolution.presentation.mvp.addad;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import solutions.isky.gaurangarevolution.data.event.GetAdInfo;
import solutions.isky.gaurangarevolution.data.models.CategoryList;
import solutions.isky.gaurangarevolution.data.models.Currency;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.data.models.PeriodItem;
import solutions.isky.gaurangarevolution.data.models.PriceEx;
import solutions.isky.gaurangarevolution.data.models.RegionInfo;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IAddAdView extends BaseView {
    void sendError(String text);
    void image_upload(List<LocalMedia> mCheackPhoto);
    void no_session(String text);
    void getDinProps(List<DinProps> dinPropses, int tpl_title_enabled);
    void setCategParam(CategoryList categParam,int pricecur);
    void setCityInfo(RegionInfo cityInfo);
    //информация об объявлении
    void getInfoPost(GetAdInfo getAdInfo);
    void setBlockPriceData(PriceEx priceData,int pricecur);
    void showErrorTitle(String text);
    void showErrorDescr(String text);
    void showErrorCateg(String text);
    void showErrorLocation(String text);
    void showErrorAdress(String text);
    void showErrorName(String text);
    void add_successfully_submitted(String link);
    void add_successfully_edit();
    void number_not_verified(User user);
    void currency_list(List<Currency>currencyList);

    void setPeriodPublicate(List<PeriodItem>periodPublicate);
}
