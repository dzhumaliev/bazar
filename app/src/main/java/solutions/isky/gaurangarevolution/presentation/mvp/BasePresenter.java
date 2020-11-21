package solutions.isky.gaurangarevolution.presentation.mvp;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by sergey on 21.03.18.
 */

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {
    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    protected void unsubscribeOnDestroy(@NonNull Disposable subscription) {
        compositeSubscription.add(subscription);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }
}
