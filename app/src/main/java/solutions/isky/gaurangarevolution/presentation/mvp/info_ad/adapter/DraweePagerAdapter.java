package solutions.isky.gaurangarevolution.presentation.mvp.info_ad.adapter;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import me.relex.photodraweeview.PhotoDraweeView;

public class DraweePagerAdapter extends PagerAdapter {
    private   String[] images;

    public DraweePagerAdapter(String[] images) {
        this.images = images;
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override public Object instantiateItem(ViewGroup viewGroup, int position) {
        final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse(images[position]));
        controller.setOldController(photoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        photoDraweeView.setController(controller.build());

        try {
            viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoDraweeView;
    }
}