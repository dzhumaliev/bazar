package solutions.isky.gaurangarevolution.presentation.mvp.category;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface ICategoryView extends BaseView {

    void listCategory(List<ItemCategoryList> itemCategoryLists);
}
