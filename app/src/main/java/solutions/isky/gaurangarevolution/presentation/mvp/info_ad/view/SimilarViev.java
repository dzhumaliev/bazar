package solutions.isky.gaurangarevolution.presentation.mvp.info_ad.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import solutions.isky.gaurangarevolution.data.models.AdInfo;

public class SimilarViev extends LinearLayout {

    public SimilarViev(Context context) {
        super(context);
    }

    public SimilarViev(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimilarViev(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SimilarViev(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setUpViews(Context context, List<AdInfo> prodAdInfos, OnClickListener clickListener) {
        setOrientation(HORIZONTAL);
        if (prodAdInfos != null && prodAdInfos.size() > 0) {
            int width = getWidth();
            if (width > 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((width - pxFromDp(18, context)) / 3), ViewGroup.LayoutParams.WRAP_CONTENT);
                int size_h = (int) (((width - pxFromDp(15, context)) / 3) * 0.75);
                params.setMargins((int) pxFromDp(3,context), 0, (int) pxFromDp(3,context), 0);
                for (AdInfo produkt : prodAdInfos) {
                    SimilarItemView similarItemView = new SimilarItemView(context);
                    similarItemView.setLayoutParams(params);
                    similarItemView.setData(produkt, size_h);
                    similarItemView.setOnClickListener(clickListener);
                    similarItemView.setTag(produkt);
                    addView(similarItemView);
                }
            }
        }
    }

    private float pxFromDp(float dp, Context context) {
        return dp
                * context.getResources().getDisplayMetrics().density;
    }
}