package solutions.isky.gaurangarevolution.presentation.mvp.correspondence;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.ChatsList;
import solutions.isky.gaurangarevolution.data.models.ChatsListNew;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IDialogsActivity extends BaseView{
    void sendError(String text);

    void no_session();

    void listChats(List<ChatsListNew> chatsLists);
}
