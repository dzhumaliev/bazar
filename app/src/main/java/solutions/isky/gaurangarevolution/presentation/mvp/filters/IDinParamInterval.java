package solutions.isky.gaurangarevolution.presentation.mvp.filters;

import java.util.HashMap;

import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;

public interface IDinParamInterval {
    void getText(String s, HashMap<Integer, ItemFilterModel> props_value, int id_prop);
}
