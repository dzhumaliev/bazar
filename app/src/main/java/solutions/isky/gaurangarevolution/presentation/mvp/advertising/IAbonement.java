package solutions.isky.gaurangarevolution.presentation.mvp.advertising;

import java.util.List;

import solutions.isky.gaurangarevolution.data.event.BuyAdv;
import solutions.isky.gaurangarevolution.data.models.PaymentResult;
import solutions.isky.gaurangarevolution.data.models.SvcAbonement;
import solutions.isky.gaurangarevolution.data.models.SvcAbonementItem;
import solutions.isky.gaurangarevolution.data.models.SvcModel;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IAbonement extends BaseView {
    void listAds(List<SvcAbonementItem> svcModels);
    void sendError(String s);

    void no_session();

    void payment_success();

    void payment_liqpay(PaymentResult paymentResult);

    void payment_paypal(BuyAdv.DataObg dataObg);

    void payment_yandex(BuyAdv.DataObg dataObg);
}
