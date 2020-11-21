package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.ImgFiles;

public class SendImage extends CapObject implements Serializable {

    @NonNull
    @SerializedName("img_files")
    private List<ImgFiles> img_files;

    @NonNull
    public List<ImgFiles> getImg_files() {
        return img_files;
    }

    public void setImg_files(@NonNull List<ImgFiles> img_files) {
        this.img_files = img_files;
    }
}
