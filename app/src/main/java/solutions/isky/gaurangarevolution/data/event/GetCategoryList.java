package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;

public class GetCategoryList {
    @SerializedName("cats_tree")
    @NonNull
    private CategList categList;

    @NonNull
    public CategList getCategList() {
        return categList;
    }

    public void setCategList(@NonNull CategList categList) {
        this.categList = categList;
    }

    public class CategList {
        @SerializedName("rows")
        @NonNull
        private List<ItemCategoryList> rows = new ArrayList<>();

        @NonNull
        public List<ItemCategoryList> getRows() {
            return rows;
        }

        public void setRows(@NonNull List<ItemCategoryList> rows) {
            this.rows = rows;
        }
    }
}
