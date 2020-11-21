package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.CategoryList;
import solutions.isky.gaurangarevolution.data.models.Currency;
import solutions.isky.gaurangarevolution.data.models.DinProps;

public class GetListPropsDyns extends CapObject implements Serializable {
    @SerializedName("add_info")
    @NonNull
    private List<DinProps> rows = new ArrayList<DinProps>();
    @SerializedName("cat_info")
    @NonNull
    CategoryList categoryList;
    @SerializedName("currency_list")
    @NonNull
    List<Currency> currencyList;

    @NonNull
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    @NonNull
    public CategoryList getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(@NonNull CategoryList categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    public List<DinProps> getRows() {
        return rows;
    }

    public void setRows(@NonNull List<DinProps> rows) {
        this.rows = rows;
    }
}