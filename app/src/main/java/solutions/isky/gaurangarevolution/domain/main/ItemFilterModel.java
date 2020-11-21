package solutions.isky.gaurangarevolution.domain.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.TypeFilterAdapt;

/**
 * Created by sergey on 21.03.18.
 */

public class ItemFilterModel extends AbstractItem<ItemFilterModel, ItemFilterModel.ViewHolder> {

    private String title;
    private String text;
    private int typeFilterAdapt;
    @NonNull
    private int type_filter;
    private int id_prop;
    private Object object;
    private DinProps props;

    public DinProps getProps() {
        return props;
    }

    public void setProps(DinProps props) {
        this.props = props;
    }

    @NonNull
    public int getType_filter() {
        return type_filter;
    }

    public void setType_filter(@NonNull int type_filter) {
        this.type_filter = type_filter;
    }

    public int getId_prop() {
        return id_prop;
    }

    public void setId_prop(int id_prop) {
        this.id_prop = id_prop;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTypeFilterAdapt() {
        return typeFilterAdapt;
    }

    public void setTypeFilterAdapt(int typeFilterAdapt) {
        this.typeFilterAdapt = typeFilterAdapt;
    }
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);

    }


    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_filter_adapt;
    }
    @Override
    public void bindView(final ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);
        Context ctx = viewHolder.itemView.getContext();
        viewHolder.tvTitle.setText(title);
        viewHolder.tvText.setText(text);
        if(typeFilterAdapt==TypeFilterAdapt.DINPARAM||typeFilterAdapt== TypeFilterAdapt.PRICEPARAM){
            viewHolder.btn_close.setImageResource(R.drawable.ic_clear_red);
        }else{
            viewHolder.btn_close.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        }

    }
    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        viewHolder.tvTitle.setText(null);
        viewHolder.tvText.setText(null);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.imageButton2)
        public ImageButton btn_close;


        public ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }
}