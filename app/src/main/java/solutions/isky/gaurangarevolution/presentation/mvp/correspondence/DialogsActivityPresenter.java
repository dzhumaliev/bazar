package solutions.isky.gaurangarevolution.presentation.mvp.correspondence;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetChatList;
import solutions.isky.gaurangarevolution.data.models.ChatsListNew;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;

@InjectViewState
public class DialogsActivityPresenter extends BasePresenter<IDialogsActivity> {
    @Inject
    ServerMethod serverMethod;

    public DialogsActivityPresenter() {
        App.getComponent().inject(this);
    }

    public void getDialogs(JsonObject jsonObject, boolean is_progress) {
        if (is_progress)
            getViewState().showProgres();
        final Single<GetChatList> observable = serverMethod.getDialogsList(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getChatList -> {
                    getViewState().hideProgres();
                    if (getChatList.getResult() > 0) {
                        ArrayList<ChatsListNew> chatsListNews=new ArrayList<>(getChatList.getChatsLists().values());
                        Collections.sort(chatsListNews);
                        getViewState().listChats(chatsListNews);

                    } else {
                        if (getChatList.getMessage() != null && getChatList.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getChatList.getMessage().get(0).getMsg(), App.getInstance());
                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getChatList.getMessage().get(0).getMsg()) || ErrorMessage.NO_PERMISSION.equalsIgnoreCase(getChatList.getMessage().get(0).getMsg())) {
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
        Log.d("getDialogsList", jsonObject.toString());
    }
}
