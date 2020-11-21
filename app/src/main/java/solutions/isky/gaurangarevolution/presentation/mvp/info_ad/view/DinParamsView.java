package solutions.isky.gaurangarevolution.presentation.mvp.info_ad.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.CategoryProp;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;

public class DinParamsView extends LinearLayout {
    public DinParamsView(Context context) {
        super(context);
    }

    public DinParamsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DinParamsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setUpViews(Context context, List<CategoryProp> categoryProps) {
        setOrientation(VERTICAL);
        if (categoryProps != null) {
            for (int i = 0; i < categoryProps.size(); i++) {
                DinParamsItem dinParamsItem = new DinParamsItem(context);
                dinParamsItem.setData(categoryProps.get(i));
                addView(dinParamsItem);
                addView(createDivider());
            }
        }
    }

    private View createDivider() {
        View divider = new View(getContext());
        divider.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtils.convertDPtoPixels(getContext(), 1)));
        divider.setBackgroundColor(getResources().getColor(R.color.tab_bottom_stroke_colorS));
        return divider;
    }
}
