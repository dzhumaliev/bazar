package solutions.isky.gaurangarevolution.di.global.module;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import solutions.isky.gaurangarevolution.data.network.AppApiConstants;
import solutions.isky.gaurangarevolution.data.network.ErrorHandler;
import solutions.isky.gaurangarevolution.data.network.NetworkChecker;
import solutions.isky.gaurangarevolution.data.network.interceptors.HttpCheckInterceptor;
import solutions.isky.gaurangarevolution.data.network.interceptors.NetworkCheckInterceptor;
import solutions.isky.gaurangarevolution.di.global.qualifiers.OkHttpInterceptors;
import solutions.isky.gaurangarevolution.di.global.qualifiers.OkHttpNetworkInterceptors;

/**
 * Created by isky on 02.03.2018.
 */

@Module
public class RetrofitModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient) {
        return builder.baseUrl(AppApiConstants.URL_API).client(okHttpClient).build();
    }

    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder(Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                // .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory);
    }

    @Provides
    @Singleton
    public Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setFieldNamingStrategy(new CustomFieldNamingPolicy())
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create();
    }

    @Provides
    @NonNull
    @Singleton
    OkHttpClient provideOkHttpClient(NetworkChecker networkChecker,
                                     @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                     @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {//TODO убрать логирование //HttpLoggingInterceptor.Level.NONE
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS)
//                .addInterceptor(new HttpLoggingInterceptor()
//                .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new NetworkCheckInterceptor(networkChecker));

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        return okHttpBuilder.build();
    }


    @Provides
    @Singleton
    ErrorHandler provideErrorHandler(Gson gson) {
        return new ErrorHandler(gson);
    }


    private static class CustomFieldNamingPolicy implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            String name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
            name = name.substring(2, name.length()).toLowerCase();
            return name;
        }
    }
}
