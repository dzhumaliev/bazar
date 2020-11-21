package solutions.isky.gaurangarevolution.presentation.mvp.category;

import java.util.List;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.data.models.ItemShopCategoryList;
import solutions.isky.gaurangarevolution.presentation.mvp.BaseView;

public interface IShopCategoryView extends BaseView {

    void listCategory(List<ItemShopCategoryList> itemShopCategoryLists);
}
