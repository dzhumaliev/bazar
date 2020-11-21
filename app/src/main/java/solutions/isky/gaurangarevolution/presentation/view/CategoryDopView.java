package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.presentation.mvp.category.ICategDop;

public class CategoryDopView extends LinearLayout implements View.OnClickListener {
    ICategDop iCategDop;
    public CategoryDopView(Context context) {
        super(context);
    }

    public CategoryDopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryDopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addDopView(Context context, ItemCategoryList itemCategoryLis, int position,ICategDop iCategDop) {
        this.iCategDop=iCategDop;
        if (itemCategoryLis != null) {
            ItemCategDop itemCategDop = (ItemCategDop) LayoutInflater.from(context)
                    .inflate(R.layout.item_category_dop, this, false);
            itemCategDop.setData(itemCategoryLis,context);
            itemCategDop.setTag(itemCategoryLis);
            addView(itemCategDop, position);
            itemCategDop.setOnClickListener(this);
            setType();

        }
    }

    public void setType(){
        if(getChildCount()>1){
            for (int y = 0; y < getChildCount(); y++) {
                ((ItemCategDop)getChildAt(y)).setTypefaceInfo(Typeface.NORMAL);
            }
            ((ItemCategDop)getChildAt(getChildCount()-1)).setTypefaceInfo(Typeface.BOLD);
        }else if(getChildCount()==1){
            // ((ItemCategDop)getChildAt(0)).setTypefaceInfo(Typeface.NORMAL);
        }
    }
    public void delView(ItemCategoryList categItem){
        if (categItem.getId() == -1) {
            categItem.setId(1);
        }
        int i = categItem.getLevel();
        if(i==0){
            ((ItemCategDop)getChildAt(0)).setTypefaceInfo(Typeface.NORMAL);
        }
        if(getChildCount()>1){

            for (int y = i; y < getChildCount(); y++) {
                if (i < getChildCount()) {
                    if(i!=getChildCount()-1){
                        removeViewAt(i+1);}
                }
            }
            requestLayout();
        }
        setType();

    }
    @Override
    public void onClick(View view) {
        Log.d("onClick",((ItemCategoryList) view.getTag()).getTitle());

        iCategDop.onCklickView((ItemCategoryList) view.getTag());
        if(((ItemCategoryList) view.getTag()).getTitle().equalsIgnoreCase(getContext().getString(R.string.select_category))){
            ((ItemCategDop)view).hideArrow();
        }
    }
}