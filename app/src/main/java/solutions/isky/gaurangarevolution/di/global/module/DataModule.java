package solutions.isky.gaurangarevolution.di.global.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.isky.gaurangarevolution.data.network.GhostApiService;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;

/**
 * Created by isky on 02.03.2018.
 */
@Module(includes = {ApiModule.class})
public class DataModule {
    @Provides
    @Singleton
    public ServerMethod provideGithubService(GhostApiService infoAdPresente) {
        return new ServerMethod(infoAdPresente);
    }
}
