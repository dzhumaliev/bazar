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
import solutions.isky.gaurangarevolution.data.models.ItemShopCategoryList;
import solutions.isky.gaurangarevolution.presentation.mvp.category.ICategShopDop;

public class CategoryShopDopView extends LinearLayout implements View.OnClickListener {

  ICategShopDop iCategDop;

  public CategoryShopDopView(Context context) {
    super(context);
  }

  public CategoryShopDopView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public CategoryShopDopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void addDopView(Context context, ItemShopCategoryList itemCategoryLis, int position,
      ICategShopDop iCategDop) {
    this.iCategDop = iCategDop;
    if (itemCategoryLis != null) {
      ItemCategShopDop itemCategDop = (ItemCategShopDop) LayoutInflater.from(context)
          .inflate(R.layout.item_category_shop_dop, this, false);
      itemCategDop.setData(itemCategoryLis, context);
      itemCategDop.setTag(itemCategoryLis);
      addView(itemCategDop, position);
      itemCategDop.setOnClickListener(this);
      setType();

    }
  }

  public void setType() {
    if (getChildCount() > 1) {
      for (int y = 0; y < getChildCount(); y++) {
        ((ItemCategShopDop) getChildAt(y)).setTypefaceInfo(Typeface.NORMAL);
      }
      ((ItemCategShopDop) getChildAt(getChildCount() - 1)).setTypefaceInfo(Typeface.BOLD);
    } else if (getChildCount() == 1) {
      // ((ItemCategDop)getChildAt(0)).setTypefaceInfo(Typeface.NORMAL);
    }
  }

  public void delView(ItemShopCategoryList categItem) {
    if (categItem.getId() == -1) {
      categItem.setId(1);
    }
    int i = categItem.getNumlevel();
    if (i == 0) {
      ((ItemCategShopDop) getChildAt(0)).setTypefaceInfo(Typeface.NORMAL);
    }
    if (getChildCount() > 1) {

      for (int y = i; y < getChildCount(); y++) {
        if (i < getChildCount()) {
          if (i != getChildCount() - 1) {
            removeViewAt(i + 1);
          }
        }
      }
      requestLayout();
    }
    setType();

  }

  @Override
  public void onClick(View view) {
    Log.d("onClick", ((ItemShopCategoryList) view.getTag()).getTitle());

    iCategDop.onCklickView((ItemShopCategoryList) view.getTag());
    if (((ItemShopCategoryList) view.getTag()).getTitle()
        .equalsIgnoreCase(getContext().getString(R.string.select_category))) {
      ((ItemCategShopDop) view).hideArrow();
    }
  }
}