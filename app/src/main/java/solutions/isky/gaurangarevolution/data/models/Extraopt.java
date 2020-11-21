package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.TreeMap;

public class Extraopt implements Serializable {
    @SerializedName("group_one_row")
    @NonNull
    private int group_one_row;
    @SerializedName("search_range_user")
    @NonNull
    private int search_range_user;
    @SerializedName("search_ranges")
    @NonNull
    private TreeMap<Integer, Search_rang> search_ranges;
    @SerializedName("start")
    @NonNull
    private String start;
    @SerializedName("end")
    @NonNull
    private String end;
    @SerializedName("step")
    @NonNull
    private String step;



    @NonNull
    public int getSearch_range_user() {
        return search_range_user;
    }

    public void setSearch_range_user(@NonNull int search_range_user) {
        this.search_range_user = search_range_user;
    }

    @NonNull
    public int getGroup_one_row() {
        return group_one_row;
    }

    public void setGroup_one_row(@NonNull int group_one_row) {
        this.group_one_row = group_one_row;
    }

    @NonNull
    public TreeMap<Integer, Search_rang> getSearch_ranges() {
        return search_ranges;
    }

    public void setSearch_ranges(@NonNull TreeMap<Integer, Search_rang> search_ranges) {
        this.search_ranges = search_ranges;
    }

    @NonNull
    public String getStart() {
        return start;
    }

    public void setStart(@NonNull String start) {
        this.start = start;
    }

    @NonNull
    public String getEnd() {
        return end;
    }

    public void setEnd(@NonNull String end) {
        this.end = end;
    }

    @NonNull
    public String getStep() {
        return step;
    }

    public void setStep(@NonNull String step) {
        this.step = step;
    }

    public Extraopt() {
    }

    public Extraopt(@NonNull int search_range_user) {
        this.search_range_user = search_range_user;
    }
}