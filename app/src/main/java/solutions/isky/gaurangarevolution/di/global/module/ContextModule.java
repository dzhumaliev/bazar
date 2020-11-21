package solutions.isky.gaurangarevolution.di.global.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by isky on 02.03.2018.
 */

@Module
public class ContextModule {

    private Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }
}
