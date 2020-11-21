package solutions.isky.gaurangarevolution.presentation.mvp.correspondence;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import java.util.HashMap;
import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.event.GetMessList;
import solutions.isky.gaurangarevolution.data.event.GetUserInfo;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.InComeMessModel;
import solutions.isky.gaurangarevolution.data.models.ItemsInMess;
import solutions.isky.gaurangarevolution.data.models.MessModel;
import solutions.isky.gaurangarevolution.data.models.Message;
import solutions.isky.gaurangarevolution.data.models.OutMessModel;
import solutions.isky.gaurangarevolution.data.models.UserInMess;
import solutions.isky.gaurangarevolution.data.network.ServerMethod;
import solutions.isky.gaurangarevolution.presentation.databases.ErrorMessage;
import solutions.isky.gaurangarevolution.presentation.mvp.BasePresenter;
import solutions.isky.gaurangarevolution.presentation.pref.AppState;
import solutions.isky.gaurangarevolution.presentation.pref.UserData;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.JsonObjBody;

@InjectViewState
public class MessageActivityPresenter extends BasePresenter<IMessageActivity> {

    @Inject
    ServerMethod serverMethod;

    public MessageActivityPresenter() {
        App.getComponent().inject(this);
    }
    public void getMessages(JsonObject jsonObject, boolean is_progress) {
        if (is_progress)
            getViewState().showProgres();
        final Single<GetMessList> observable = serverMethod.getMessageList(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .doOnSuccess(getMessList -> {
                    if (getMessList.getResult() > 0) {
                        getMessList.setMessModels(getModels(getMessList.getChatObj().getChatsLists(),getMessList.getChatObj().getItemsInMessHashMap(),getMessList.getChatObj().getUserInMess()));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMessList -> {
                    getViewState().hideProgres();
                    if (getMessList.getResult() > 0) {
                        getViewState().listChats(getMessList.getMessModels());
                        getViewState().setTitle(getMessList.getChatObj().getUserInMess().getName());
                        //getUserInfo();
                    } else {
                        if (getMessList.getMessage() != null && getMessList.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(getMessList.getMessage().get(0).getMsg(), App.getInstance());

                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getMessList.getMessage().get(0).getMsg()) || ErrorMessage.NO_PERMISSION.equalsIgnoreCase(getMessList.getMessage().get(0).getMsg())) {
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
        Log.d("getMessages", jsonObject.toString());
    }


    private ArrayList<MessModel> getModels(ArrayList<Message> messages,HashMap<Integer,ItemsInMess> itemsInMess,UserInMess userInMess) {
        ArrayList<MessModel> messModels = new ArrayList<>();
        int size=messages.size()-1;
        for (Message message:messages) {
            if (message.getMy() == 1) {
                messModels.add(new OutMessModel(message,itemsInMess,userInMess));
            } else {
                messModels.add(new InComeMessModel(message,itemsInMess,userInMess));
            }
        }
        return messModels;
    }
    public void answerMessages(JsonObject jsonObject, boolean is_progress) {
        if (is_progress)
            getViewState().showProgres();
        final Single<CapObject> observable = serverMethod.answerMessages(jsonObject);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(capObject -> {

                    if (capObject.getResult() > 0) {
                        getViewState().answeMess();
                        getUserInfo();
                    } else {
                        getViewState().hideProgres();
                        if (capObject.getMessage() != null && capObject.getMessage().size() > 0) {
                            String text_error = ErrorMessage.getError(capObject.getMessage().get(0).getMsg(), App.getInstance());

                            if (ErrorMessage.MAIL_TIMELIMIT.equalsIgnoreCase(capObject.getMessage().get(0).getMsg())) {
                                getViewState().sendError(String.format(App.getInstance().getString(R.string.mail_time_limit),capObject.getMessage().get(0).getDtime()));
                            }else{
                                getViewState().sendError(text_error);
                            }

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
        Log.d("answerMessages", jsonObject.toString());
    }
    private void getUserInfo() {
        JsonObject object = JsonObjBody.getMyInfo(UserData.getInstance(App.getInstance()).getString(UserData.Key.USER_SESSID), UserData.getInstance(App.getInstance()).getInteger(UserData.Key.USER_ID));
        final Single<GetUserInfo> observable = serverMethod.getInfo(object);
        Disposable subscription = observable
                .subscribeOn(Schedulers.newThread())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfo -> {
                    if (getUserInfo.getResult() > 0) {
                        AppUtils.setUserData(getUserInfo.getUser());
                    } else {
                        if (getUserInfo.getMessage() != null && getUserInfo.getMessage().size() > 0) {

                            if (ErrorMessage.NO_SESSION.equalsIgnoreCase(getUserInfo.getMessage().get(0).getMsg())) {
                                AppState.getInstance(App.getInstance()).setBoolean(AppState.Key.LOGGED_IN, false);
                                AppUtils.clearUserData();

                            }
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
        unsubscribeOnDestroy(subscription);
        Log.d("getUserInfo", "getUserInfo");
    }
}
