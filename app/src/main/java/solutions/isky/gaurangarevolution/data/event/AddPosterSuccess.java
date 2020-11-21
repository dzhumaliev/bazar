package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import solutions.isky.gaurangarevolution.data.models.CapObject;

public class AddPosterSuccess extends CapObject {
    @SerializedName("add_info")
    private AddInfoPost addInfoPost;

    public AddInfoPost getAddInfoPost() {
        return addInfoPost;
    }

    public void setAddInfoPost(AddInfoPost addInfoPost) {
        this.addInfoPost = addInfoPost;
    }

    public class AddInfoPost {
        @SerializedName("add_id")
        @NonNull
        private String add_id;
        @SerializedName("item_link")
        @NonNull
        private String item_link;

        @NonNull
        public String getAdd_id() {
            return add_id;
        }

        public void setAdd_id(@NonNull String add_id) {
            this.add_id = add_id;
        }

        @NonNull
        public String getItem_link() {
            return item_link;
        }

        public void setItem_link(@NonNull String item_link) {
            this.item_link = item_link;
        }
    }
}