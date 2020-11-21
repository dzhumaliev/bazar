package solutions.isky.gaurangarevolution.presentation.mvp.advertising;

import java.util.List;

import solutions.isky.gaurangarevolution.data.event.BuyAdv;
import solutions.isky.gaurangarevolution.data.models.PaymentResult;
import solutions.isky.gaurangarevolution.data.models.SvcModel;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IAdvertisingShop extends BaseView {
    void listAds(List<SvcModel> svcModels);
    void sendError(String s);

    void no_session();

    void payment_success();

    void updateUser(User userdata);

    void payment_liqpay(PaymentResult paymentResult);

    void payment_paypal(BuyAdv.DataObg dataObg);

    void payment_yandex(BuyAdv.DataObg dataObg);
}
