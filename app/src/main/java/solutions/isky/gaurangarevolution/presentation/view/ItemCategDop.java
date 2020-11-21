package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.facebook.drawee.view.SimpleDraweeView;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.ItemCategoryList;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ItemCategDop extends RelativeLayout {

    private TextView titleCateg;
    private TextView countCateg;
    private ImageView arrowView;
    private ImageView icCateg;
    private LevelBeamView levelBeamView;

    public ItemCategDop(Context context) {
        super(context);
    }

    public ItemCategDop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemCategDop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        titleCateg = findViewById(R.id.item_category_name);
//        titleCateg.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        countCateg = findViewById(R.id.item_category_count);
        arrowView =  findViewById(R.id.ic_item_arrow);
        arrowView.setImageResource(R.drawable.ic_keyboard_arrow_right);
        icCateg = findViewById(R.id.ic_category);
        levelBeamView = findViewById(R.id.dataItemLevelBeam);

    }
    public void setData(ItemCategoryList itemCategoryList,Context context) {

        titleCateg.setText(itemCategoryList.getTitle());
        arrowView.setVisibility(GONE);
        countCateg.setVisibility(GONE);
        levelBeamView.setLevel(itemCategoryList.getLevel());
        if(TextUtils.isEmpty(itemCategoryList.getIc_path())){
            icCateg.setVisibility(GONE);
        }else{
            icCateg.setVisibility(VISIBLE);
            GlideApp.with(context)
                    .load(itemCategoryList.getIc_path())
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