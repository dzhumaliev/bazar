package solutions.isky.gaurangarevolution.presentation.mvp.info_ad.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur;

import eightbitlab.com.blurview.BlurView;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.AdInfo;
import solutions.isky.gaurangarevolution.domain.login.transformations.BlurTransformation;
import solutions.isky.gaurangarevolution.presentation.utils.AppUtils;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SimilarItemView extends LinearLayout {

  private TextView tvTitle;
  private TextView tvPrice;
  private ImageView img_ad;
  FrameLayout root_view_similar;

  public SimilarItemView(Context context) {
    super(context);
    init(context);
  }

  public SimilarItemView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public SimilarItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {

    LayoutInflater.from(context).inflate(R.layout.item_similar, this, true);
    tvTitle = findViewById(R.id.tvTitle);
    tvPrice = findViewById(R.id.tvPrice);
    img_ad = findViewById(R.id.img_similar);
    root_view_similar = findViewById(R.id.root_view_similar);
    setOrientation(VERTICAL);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();


  }

  public void setData(AdInfo adInfo, int size_h) {
    tvTitle.setText(adInfo.getTitle());
    //tvPrice.setText(adInfo.getItem_price_display());
    tvPrice.setText(AppUtils.getPriceEx(adInfo.getPriceEx(), adInfo.getItem_price_display()));
    tvPrice.setVisibility(
        AppUtils.is_hasPrice(adInfo.getPriceEx(), adInfo.getPrice()) ? View.VISIBLE
            : View.INVISIBLE);
    FrameLayout.LayoutParams paramsdrw = new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, size_h);
    img_ad.setLayoutParams(paramsdrw);
    try {
      GlideApp.with(this)
          .load(adInfo.getImgM())
//                .centerCrop()
          .transition(withCrossFade())
          .placeholder(R.drawable.ic_placeholder_ad)
          .error(R.drawable.ic_placeholder_ad)
          .fallback(R.drawable.ic_placeholder_ad)
          .transform(new MultiTransformation<>(new CenterCrop(),
              new RoundedCorners(AppUtils.convertDPtoPixels(this.getContext(), 8))))
//                .apply(RequestOptions.centerCropTransform())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(AppUtils.convertDPtoPixels(this.getContext(),8))))
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(img_ad);
    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}