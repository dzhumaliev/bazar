package solutions.isky.gaurangarevolution.presentation.mvp.correspondence;

import java.util.ArrayList;

import solutions.isky.gaurangarevolution.data.models.MessModel;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IMessageActivity extends BaseView {
    void sendError(String text);

    void no_session();

    void listChats(ArrayList<MessModel> messModels);

    void setTitle(String title);


    void answeMess();
}
