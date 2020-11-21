package solutions.isky.gaurangarevolution.presentation.mvp.info_ad.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.aplication.App;
import solutions.isky.gaurangarevolution.data.models.CategoryProp;

public class DinParamsItem extends RelativeLayout {
    private TextView tvName;
    private TextView tvValue;

    public DinParamsItem(Context context) {
        super(context);
        init(context);
    }

    public DinParamsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DinParamsItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DinParamsItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvName =  findViewById(R.id.tvTitleParam);
        tvValue = findViewById(R.id.tvValue);

    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_din_param, this, true);
        tvName =  findViewById(R.id.tvTitleParam);
        tvValue = findViewById(R.id.tvValue);
        //LayoutInflater.from(context).inflate(R.layout.item_din_param, this, true);
    }
    public void setData(CategoryProp categoryProp) {
        tvName.setText(categoryProp.getTitle());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            if(categoryProp.getType().equalsIgnoreCase("4")){
                String text="";
                text=(categoryProp.getValue().equalsIgnoreCase("2")? App.getInstance().getString(R.string.yes):App.getInstance().getString(R.string.no));
                tvValue.setText(Html.fromHtml(text+" "+categoryProp.getUnit()+"\t",Html.FROM_HTML_MODE_COMPACT));
            }else if(categoryProp.getType().equalsIgnoreCase("5")){
                String text="";
                text=(categoryProp.getValue().equalsIgnoreCase("1")? App.getInstance().getString(R.string.yes):App.getInstance().getString(R.string.no));
                tvValue.setText(Html.fromHtml(text+" "+categoryProp.getUnit()+"\t",Html.FROM_HTML_MODE_COMPACT));
            }else{
                tvValue.setText(Html.fromHtml(categoryProp.getValue()+" "+categoryProp.getUnit()+"\t",Html.FROM_HTML_MODE_COMPACT));
            }

        } else {
            if(categoryProp.getType().equalsIgnoreCase("4")){
                String text="";
                text=(categoryProp.getValue().equalsIgnoreCase("2")? App.getInstance().getString(R.string.yes):App.getInstance().getString(R.string.no));
                tvValue.setText(Html.fromHtml(text+" "+categoryProp.getUnit()+"\t"));
            }else if(categoryProp.getType().equalsIgnoreCase("5")){
                String text="";
                text=(categoryProp.getValue().equalsIgnoreCase("1")? App.getInstance().getString(R.string.yes):App.getInstance().getString(R.string.no));
                tvValue.setText(Html.fromHtml(text+" "+categoryProp.getUnit()+"\t"));
            }else{
                tvValue.setText(Html.fromHtml(categoryProp.getValue()+" "+categoryProp.getUnit()+"\t"));
            }
            //tvValue.setText(Html.fromHtml(categoryProp.getValue()+" "+categoryProp.getUnit()+"\t"));
        }


    }
//    private boolean setUnit(String t){
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            return Html.fromHtml(t,Html.FROM_HTML_MODE_COMPACT).length()<6;
//
//        }else{
//            return Html.fromHtml(t).length()<6;
//
//        }
//    }
}
