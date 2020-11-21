package solutions.isky.gaurangarevolution.presentation.mvp.my_profile;

import java.util.ArrayList;

import solutions.isky.gaurangarevolution.data.models.ItemPayments;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IMyPayments extends BaseView {
    void sendError(String s);
    void no_session();
    void setHeader(String balance,String bonus);
    void listPayments(ArrayList<ItemPayments> itemPayments);
}
