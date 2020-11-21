package solutions.isky.gaurangarevolution.presentation.mvp.filters;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IfiltersView extends BaseView{
    void count_posters(@NonNull String s);
    void getDinProps(List<DinProps> dinPropses, boolean necessary_to_fill);
}
