package solutions.isky.gaurangarevolution.presentation.databases;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;

import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.utils.LayoutManagerType;

/**
 * Created by sergey on 21.03.18.
 */

public class ParamFilterPostSingleton {
    private static ParamFilterPostSingleton ourInstance = new ParamFilterPostSingleton();
    @NonNull
    private int mcategId;
    @NonNull
    private int mCityId;
    @NonNull
    private String mCityTitle;
    @NonNull
    private String categTitle;
    @NonNull
    private int is_photo;
    @NonNull
    private int p_more;
    @NonNull
    private int p_less;
    private LayoutManagerType mCurrentLayoutManagerType;
    private String sort;
    @NonNull
    private int mOwner;
    private String searchText;
    private HashMap<Integer, ItemFilterModel> mProps_value;
    private ParamFilterPostSingleton() {
    }

    public static ParamFilterPostSingleton getInstance() {
        return ourInstance;
    }

    @NonNull
    public int getP_more() {
        return p_more;
    }

    public void setP_more(@NonNull int p_more) {
        this.p_more = p_more;
    }

    @NonNull
    public int getP_less() {
        return p_less;
    }

    public void setP_less(@NonNull int p_less) {
        this.p_less = p_less;
    }

    @NonNull
    public int getIs_photo() {
        return is_photo;
    }

    public void setIs_photo(@NonNull int is_photo) {
        this.is_photo = is_photo;
    }

    public void setMcategId(@NonNull int mcategId) {
        this.mcategId = mcategId;
    }

    @NonNull
    public int getMcategId() {
        return mcategId;
    }

    @NonNull
    public String getmCityTitle() {
        return mCityTitle;
    }

    public void setmCityTitle(@NonNull String mCityTitle) {
        this.mCityTitle = mCityTitle;
    }

    public HashMap<Integer, ItemFilterModel> getmProps_value() {
        if(mProps_value==null){
            mProps_value=new HashMap<>();
        }
        return mProps_value;
    }

    public void setmProps_value(HashMap<Integer, ItemFilterModel> mProps_value) {
        this.mProps_value = mProps_value;
    }

    public void resetFilter(){
        mcategId=0;
        sort= GroupSort.newest;
        searchText="";
        mCityId=0;
        mOwner=0;
        try {
            mProps_value.clear();
        }catch (Exception e){
            e.printStackTrace();
        }

        mCityTitle="";
        is_photo=0;
        p_less=0;
        p_more=0;
    }

    @NonNull
    public String getCategTitle() {
        return categTitle;
    }

    public void setCategTitle(@NonNull String categTitle) {
        this.categTitle = categTitle;
    }

    public String getSearchText() {
        if(searchText==null){
            searchText="";
        }
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public LayoutManagerType getmCurrentLayoutManagerType() {
        if(mCurrentLayoutManagerType==null){
            return LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
        return mCurrentLayoutManagerType;
    }

    public void setmCurrentLayoutManagerType(LayoutManagerType mCurrentLayoutManagerType) {
        this.mCurrentLayoutManagerType = mCurrentLayoutManagerType;
    }

    public String getSort() {
        if(TextUtils.isEmpty(sort)){
            sort=GroupSort.newest;
        }
        return sort;
    }

    @NonNull
    public int getmOwner() {
        return mOwner;
    }

    public void setmOwner(@NonNull int mOwner) {
        this.mOwner = mOwner;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @NonNull
    public int getmCityId() {
        return mCityId;
    }

    public void setmCityId(@NonNull int mCityId) {
        this.mCityId = mCityId;
    }

    public int getCountFilters(){
        int count=0;
        return count+getmProps_value().size()+(mcategId>0?1:0)+(mCityId>0?1:0)+(p_less>0||p_more>0?1:0)+(TextUtils.isEmpty(searchText)?0:1);

    }
}
