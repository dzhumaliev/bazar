package solutions.isky.gaurangarevolution.di.global.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import solutions.isky.gaurangarevolution.data.network.GhostApiService;

/**
 * Created by isky on 02.03.2018.
 */

@Module(includes = {RetrofitModule.class,OkHttpInterceptorsModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public GhostApiService provideAuthApi(Retrofit retrofit) {
        return retrofit.create(GhostApiService.class);
    }
}
