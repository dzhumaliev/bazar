package solutions.isky.gaurangarevolution.presentation.databases;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;

import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.utils.LayoutManagerType;

/**
 * Created by sergey on 21.03.18.
 */

public class ParamFilterShopSingleton {
    private static ParamFilterShopSingleton ourInstance = new ParamFilterShopSingleton();
    @NonNull
    private int mcategId;
    @NonNull
    private int mCityId;
    @NonNull
    private String mCityTitle;
    @NonNull
    private String categTitle;

    private String searchText;

    private ParamFilterShopSingleton() {
    }

    public static ParamFilterShopSingleton getInstance() {
        return ourInstance;
    }

    @NonNull
    public int getMcategId() {
        return mcategId;
    }

    public void setMcategId(@NonNull int mcategId) {
        this.mcategId = mcategId;
    }

    @NonNull
    public int getmCityId() {
        return mCityId;
    }

    public void setmCityId(@NonNull int mCityId) {
        this.mCityId = mCityId;
    }

    @NonNull
    public String getmCityTitle() {
        return mCityTitle;
    }

    public void setmCityTitle(@NonNull String mCityTitle) {
        this.mCityTitle = mCityTitle;
    }

    @NonNull
    public String getCategTitle() {
        return categTitle;
    }

    public void setCategTitle(@NonNull String categTitle) {
        this.categTitle = categTitle;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void clearFilter(){
        mcategId=0;
        mCityId=0;
        mCityTitle="";
        categTitle="";
    }

    public int getCountFilters(){
        int count=0;
        return count+(TextUtils.isEmpty(searchText)?0:1)+(mcategId>0?1:0)+(mCityId>0?1:0);

    }
}
