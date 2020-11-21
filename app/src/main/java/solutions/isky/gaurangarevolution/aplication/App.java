package solutions.isky.gaurangarevolution.aplication;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vk.sdk.VKSdk;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;
import io.fabric.sdk.android.Fabric;
import net.danlew.android.joda.JodaTimeAndroid;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.di.AppComponent;
import solutions.isky.gaurangarevolution.di.DaggerAppComponent;
import solutions.isky.gaurangarevolution.di.global.module.ContextModule;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;

/**
 * Created by isky on 02.03.2018.
 */

public class App extends Application {

    public static boolean liqpay_enable=false;
    public static boolean paypal_enable=false;
    public static boolean yandex_enable=false;

    private static AppComponent appComponent;
    private static App instance;
    public static OkHttpClient mOkHttpClient = null;
    private FirebaseAnalytics mFirebaseAnalytics;

    protected void initOkHttpClient() {
        if (mOkHttpClient != null) {
            return;    }
        mOkHttpClient = new OkHttpClient();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }
    @Override
    public void onCreate() {
        super.onCreate();
        //TypefaceUtil.overrideFont(this, "SERIF", "fonts/12005.ttf");
        Fabric.with(this, new Crashlytics());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ImagePipelineConfig config_fresco = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config_fresco);
        instance = this;
        appComponent = buildComponent();
        VKSdk.initialize(this);
        Realm.init(this);
        RealmConfiguration realmConfiguration=new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        JodaTimeAndroid.init(this);
        initOkHttpClient();
        YandexMetricaConfig.Builder configBuilder = YandexMetricaConfig.newConfigBuilder(getString(R.string.yandex_metrica_kay));
        YandexMetrica.activate(getApplicationContext(), configBuilder.build());
        YandexMetrica.enableActivityAutoTracking(this);

        AppState.getInstance(this).setString(AppState.Key.LANG_APP, Locale.getDefault().getLanguage());
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        AppState.getInstance(this).setString(AppState.Key.LANG_APP, Locale.getDefault().getLanguage());

    }
    public AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static Context getInstance() {
        return instance;
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

}