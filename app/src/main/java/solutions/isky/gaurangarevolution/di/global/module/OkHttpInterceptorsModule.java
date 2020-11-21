package solutions.isky.gaurangarevolution.di.global.module;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import solutions.isky.gaurangarevolution.di.global.qualifiers.OkHttpInterceptors;
import solutions.isky.gaurangarevolution.di.global.qualifiers.OkHttpNetworkInterceptors;

import static java.util.Collections.singletonList;

/**
 * Created by isky on 03.03.2018.
 */

@Module
public class OkHttpInterceptorsModule {

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpInterceptors() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return singletonList(httpLoggingInterceptor);
    }

    @Provides @OkHttpNetworkInterceptors
    @Singleton @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return singletonList(new StethoInterceptor());
    }

}