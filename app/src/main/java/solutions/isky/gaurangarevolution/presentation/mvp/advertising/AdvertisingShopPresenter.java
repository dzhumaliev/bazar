package solutions.isky.gaurangarevolution.presentation.mvp.advertising;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.BuyAdv;
import solutions.isky.gaurangarevolution.data.event.GetSvcData;
import solutions.isky.gaurangarevolution.data.event.GetSvcShopData;
import solutions.isky.gaurangarevolution.data.event.GetUserInfo;
import solutions.isky.gaurangarevolution.data.models.PaymentResult;
import solutions.isky.gaurangarevolution.data.models.User;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.Constants;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import ua.privatbank.paylibliqpay.ErrorCode;
import ua.privatbank.paylibliqpay.LiqPay;
import ua.privatbank.paylibliqpay.LiqPayCallBack;
import ua.privatbank.paylibliqpay.LogUtil;

@InjectViewState
public class AdvertisingShopPresenter extends BasePresenter<IAdvertisingShop> {

    @Inject
    ServerMethod serverMethod;


    public AdvertisingShopPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getUserInfo();

    }

    private void getShopSvcData(String shop_id) {
        getViewState().showProgres();
        JsonObject jsonObject = JsonObjBody.svc_shop_data(AppState.getInstance(App.getInstance()).getString(AppState.Key.LANG_APP), shop_id);
        final Single<GetSvcShopData> observable = serverMethod.get_svc_shop(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSvcData -> {
                    getViewState().hideProgres();
                    if (getSvcData.getResult() > 0) {
                        getViewState().listAds(getSvcData.getSvcModelList());
                    } else {
                        if (getSvcData.getMessage() != null && getSvcData.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getSvcData.getMessage().get(0).getMsg(), App.getInstance());
                            getViewState().sendError(text_error);
                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getSvcShopData", jsonObject.toString());
    }

    public void buy_shop_svc(String id_ad, int id_svc,String method) {
        if (id_ad != null && id_svc != 0&&method!=null) {
            buySvc(id_ad, id_svc,method);
        } else {
            getViewState().sendError(App.getInstance().getString(R.string.error_buy_svc));
        }
    }

    public void buySvc(String id_ad, int id_svc,String method) {
        getViewState().showProgres();
        JsonObject jsonObject = JsonObjBody.svc_shop_buy(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), id_ad, id_svc,method,AppState.getInstance(App.getInstance()).getString(AppState.Key.LANG_APP));
        final Single<BuyAdv> observable = serverMethod.buy_shop_svc(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(buyAdv ->  {
                    getViewState().hideProgres();
                    if (buyAdv.getResult() > 0) {
                        if (buyAdv.getDataObg() != null) {
                            if (method.equals(Constants.LIQPAYBALANCE)) {
                                LiqPay.checkout(App.getInstance(), buyAdv.getDataObg().getData(),
                                        buyAdv.getDataObg().getSignature(), new LiqPayCallBack() {
                                            @Override
                                            public void onResponseSuccess(String s) {
                                                PaymentResult mPaymentResult = null;
                                                try {
                                                    mPaymentResult = new PaymentResult(s);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                getViewState().payment_liqpay(mPaymentResult);
                                                LogUtil.log(s);
                                            }

                                            @Override
                                            public void onResponceError(ErrorCode errorCode) {
                                                PaymentResult mPaymentResult = null;
                                                mPaymentResult = new PaymentResult(errorCode);
                                                getViewState().payment_liqpay(mPaymentResult);

                                                LogUtil.log(errorCode.toString());
                                            }
                                        });
                            }else if(method.equals(Constants.PAYPALBALANCE)){
                                getViewState().payment_paypal(buyAdv.getDataObg());
                            }else if(method.equals(Constants.YANDEXBALANCE)){
                                getViewState().payment_yandex(buyAdv.getDataObg());
                            }

                        } else {
                            getViewState().payment_success();
                            User user=new Gson().fromJson(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_DATA_JSON), User.class);
                            if(user!=null){
                                user.setBalance(buyAdv.getUserBalans().getBalance());
                                AppUtils.setUserData(user);
                            }
                            getViewState().sendError(App.getInstance().getString(R.string.success_pay));
                        }

                    } else {
                        if (buyAdv.getMessage() != null && buyAdv.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(buyAdv.getMessage().get(0).getMsg(), App.getInstance());
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(buyAdv.getMessage().get(0).getMsg())) {
                                getViewState().no_session();
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }
                            getViewState().sendError(text_error);

                        } else {
                            getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideProgres();
                    getViewState().sendError(App.getInstance().getString(R.string.unknown_error));
                });
        unsubscribeOnDestroy(subscription);
        Log.d("buySvc", jsonObject.toString());
    }

    public void getUserInfo() {
        JsonObject object = new JsonObjBody().getMyInfo(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID));
        final Single<GetUserInfo> observable = serverMethod.getInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo -> {
                    if (getUserInfo.getResult() > 0) {
                        AppUtils.setUserData(getUserInfo.getUser());
                        if (getUserInfo.getUser().getShop_id() >0)
                            getShopSvcData(String.valueOf(getUserInfo.getUser().getShop_id()));
                    } else {
                        if (getUserInfo.getMessage() != null && getUserInfo.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getUserInfo.getMessage().get(0).getMsg(), App.getInstance());
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getUserInfo.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();
                            }
                            getViewState().sendError(text_error);
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getUserInfo", "getUserInfo");
    }
}
