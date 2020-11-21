package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetCountryCode;
import solutions.isky.gaurangarevolution.data.models.CountryCode;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;
import javax.inject.Inject;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class VerificationPhonePresenter extends BasePresenter<IVerificationPhoneView>{
    @Inject
    ServerMethod serverMethod;

    public VerificationPhonePresenter() {
        App.getComponent().inject(this);
    }

    public void getCountryCode() {
        JsonObject jsonObject = JsonObjBody.getCodeCauntry(AppUtils.getLocale(App.getInstance()));
        final Single<GetCountryCode> observable = serverMethod.getCountryCode(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCountryCode -> {
                if (getCountryCode.getResult() > 0) {
                    StringBuilder code = new StringBuilder();
                    for (CountryCode countryCode : getCountryCode.getCountryCodes()) {
                        if (code.length() > 1) {
                            code.append("," + countryCode.getCountry_code());
                        }else{
                            code.append(countryCode.getCountry_code());
                        }
                    }
                    if (code.length() > 2) {
                        AppState.getInstance(App.getInstance()).setString(AppState.Key.CODE_COUNTRY_STRING,code.toString());
                        getViewState().getCodeCountry();
                    }
                }
            }, throwable -> {
                throwable.printStackTrace();
            });
        unsubscribeOnDestroy(subscription);
        Log.d("getCountryCode", jsonObject.toString());
    }
}