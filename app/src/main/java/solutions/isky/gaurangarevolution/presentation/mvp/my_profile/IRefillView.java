package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import solutions.isky.gaurangarevolution.data.event.BuyAdv;
import solutions.isky.gaurangarevolution.data.models.PaymentResult;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IRefillView extends BaseView {

    void sendError(String s);
    void no_session();

    void payment_success();

    void payment_liqpay(PaymentResult paymentResult);

    void payment_paypal(BuyAdv.DataObg dataObg);
    void payment_yandex(BuyAdv.DataObg dataObg);
}

