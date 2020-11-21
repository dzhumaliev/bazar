package solutions.isky.gaurangarevolution.presentation.view;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.ItemShopCategoryList;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;

public class ItemCategShopDop extends RelativeLayout {

    private TextView titleCateg;
    private ImageView arrowView;
    private ImageView icCateg;
    private LevelBeamView levelBeamView;

    public ItemCategShopDop(Context context) {
        super(context);
    }

    public ItemCategShopDop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemCategShopDop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        titleCateg = findViewById(R.id.item_category_name);
//        titleCateg.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        arrowView =  findViewById(R.id.ic_item_arrow);
        arrowView.setImageResource(R.drawable.ic_keyboard_arrow_right);
        icCateg = findViewById(R.id.ic_category);
        levelBeamView = findViewById(R.id.dataItemLevelBeam);

    }
    public void setData(ItemShopCategoryList itemCategoryList,Context context) {

        titleCateg.setText(itemCategoryList.getTitle());
        arrowView.setVisibility(GONE);
        levelBeamView.setLevel(itemCategoryList.getNumlevel());
        if(TextUtils.isEmpty(itemCategoryList.getImg())){
            icCateg.setVisibility(GONE);
        }else{
            icCateg.setVisibility(VISIBLE);
            GlideApp.with(context)
                    .load(itemCategoryList.getImg())
                    .transform(new CenterInside())
                    .transition(withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(icCateg);

        }
//        countCateg.setText(itemCategoryList.getCount_items());


    }
    public void setTypefaceInfo(int style) {
        titleCateg.setTypeface(Typeface.DEFAULT,style);
        if(style==0){
            arrowView.setVisibility(VISIBLE);
        }else{
            arrowView.setVisibility(GONE);
        }

    }
    public void hideArrow() {
        arrowView.setVisibility(GONE);

    }
}