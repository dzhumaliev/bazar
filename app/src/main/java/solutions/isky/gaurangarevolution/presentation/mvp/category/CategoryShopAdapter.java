package solutions.isky.gaurangarevolution.presentation.mvp.category;


import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import io.realm.Realm;
import java.util.List;
import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.ItemShopCategoryList;
import solutions.isky.gaurangarevolution.presentation.databases.RealmHelper;
import solutions.isky.gaurangarevolution.presentation.utils.GlideApp;
import solutions.isky.gaurangarevolution.presentation.view.LevelBeamView;

public class CategoryShopAdapter extends MultiLevelListAdapter {
    Context ctx;
    Realm realm;
    int mCategId;
    boolean is_add_ad;

    public CategoryShopAdapter(Context ctx, Realm realm, int mCategId,boolean is_add_ad) {
        this.ctx = ctx;
        this.realm = realm;
        this.mCategId = mCategId;
        this.is_add_ad=is_add_ad;

    }

    @Override
    protected boolean isExpandable(Object object) {
        return ((ItemShopCategoryList) object).getNode() == 0 ? false : true;
    }

    @Override
    protected List<?> getSubObjects(Object object) {
        return new RealmHelper().getSubShopCategory(realm, ((ItemShopCategoryList) object).getId());
    }

    @Override
    protected View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_shop_category, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemShopCategoryList itemCategoryList = (ItemShopCategoryList) object;
        if(itemCategoryList!=null) {
            viewHolder.titleCateg.setText(itemCategoryList.getTitle());
            viewHolder.countCateg.setText(ctx.getResources().getQuantityString(R.plurals.count_shops,itemCategoryList.getShops(),itemCategoryList.getShops()));
            //viewHolder.countCateg.setText(itemCategoryList.getShops()+ " " + ctx.getString(R.string.count_shops));
            if (TextUtils.isEmpty(itemCategoryList.getImg())) {
                viewHolder.icCateg.setVisibility(View.GONE);
                if (itemCategoryList.getTitle().equalsIgnoreCase(ctx.getString(R.string.all_categ))) {
                    viewHolder.icCateg.setVisibility(View.VISIBLE);
                    GlideApp.with(ctx)
                            .load(R.drawable.ic_category)
                            //.centerCrop()
                            .transition(withCrossFade())
                            .transform(new CenterInside())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(viewHolder.icCateg);
                } else {
                    viewHolder.icCateg.setVisibility(View.GONE);
                }
            } else {
                viewHolder.icCateg.setVisibility(View.VISIBLE);
                GlideApp.with(ctx)
                        .load(itemCategoryList.getImg())
                        .transform(new CenterInside())
                        .transition(withCrossFade())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.icCateg);
            }

            if (itemInfo.isExpandable()) {
                viewHolder.arrowView.setVisibility(View.VISIBLE);
                viewHolder.arrowView.setImageResource(itemInfo.isExpanded() ?
                        R.drawable.ic_icon_arrow_down : R.drawable.ic_keyboard_arrow_right);
                viewHolder.titleCateg.setTextColor(ctx.getResources().getColor(android.R.color.black));
            } else {

                viewHolder.arrowView.setVisibility(View.GONE);

                if (mCategId > 0 && mCategId == (itemCategoryList.getId())) {
                    viewHolder.arrowView.setVisibility(View.VISIBLE);
                    viewHolder.arrowView.setImageResource(R.drawable.ic_cheack);
                    viewHolder.titleCateg.setTextColor(ctx.getResources().getColor(R.color.colorPrimaryDark));


                } else {
                    viewHolder.arrowView.setVisibility(View.GONE);
                    viewHolder.titleCateg.setTextColor(ctx.getResources().getColor(android.R.color.black));
                }
            }
            if (is_add_ad) {
                viewHolder.countCateg.setVisibility(View.GONE);
            }
            viewHolder.levelBeamView.setLevel(itemCategoryList.getNumlevel());
        }
        return convertView;
    }

    public class ViewHolder {
        @BindView(R.id.item_category_name)
        TextView titleCateg;
        @BindView(R.id.item_category_count)
        TextView countCateg;
        @BindView(R.id.ic_item_arrow)
        ImageView arrowView;
        @BindView(R.id.ic_category)
        ImageView icCateg;
        @BindView(R.id.dataItemLevelBeam)
        LevelBeamView levelBeamView;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}