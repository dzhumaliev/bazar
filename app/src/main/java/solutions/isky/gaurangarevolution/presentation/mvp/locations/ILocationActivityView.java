package solutions.isky.gaurangarevolution.presentation.mvp.locations;

import solutions.isky.gaurangarevolution.data.event.GetListLocation;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface ILocationActivityView extends BaseView {
    void getListLocation(GetListLocation getListLocation);
    void show_error(String s);
}
