package solutions.isky.gaurangarevolution.presentation.mvp.filters;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;

public class PriceParamInterval extends DialogFragment {
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.tv_v_more)
    EditText tv_v_more;
    @BindView(R.id.tv_v_less)
    EditText tv_v_less;
    IPriceParamInterval iPriceParamInterval;
    int p_more;
    int p_les;
    @BindView(R.id.suggested_options)
    LinearLayout layout_soptp;
    @BindView(R.id.ttitle_parametrs)
    TextView ttitle_parametrs;
    boolean is_nec;
    String title;
    JsonObject jsonObject;
    HashMap<Integer, ItemFilterModel> props_value;

    public static PriceParamInterval newInstance(IPriceParamInterval iPriceParamInterval, boolean is_nec, String title) {
        PriceParamInterval frag = new PriceParamInterval();
        Bundle args = new Bundle();
        frag.setArguments(args);
        frag.iPriceParamInterval = iPriceParamInterval;
        frag.p_more = ParamFilterPostSingleton.getInstance().getP_more();
        frag.is_nec = is_nec;
        frag.title=title;
        frag.p_les = ParamFilterPostSingleton.getInstance().getP_less();
        return frag;
    }
    public PriceParamInterval() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.interval_param, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ButterKnife.bind(this, view);
        tv_v_more.setEnabled(true);
        tv_v_less.setEnabled(true);
        if(title!=null){
            ttitle_parametrs.setText(Html.fromHtml(title));
        }
        layout_soptp.setVisibility(View.GONE);

        btn_ok.setOnClickListener(view1 -> {
            getparams();
        });
        btn_cancel.setOnClickListener(view1 -> dismiss());
        tv_v_more.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tv_v_more.getText().toString().trim().length() > 0) {
                   // btn_clear_more.setVisibility(View.VISIBLE);
                } else {
                   // btn_clear_more.setVisibility(View.GONE);
                }
            }
        });
        //btn_clear_more.setOnClickListener(view1 -> tv_v_more.setText(""));
        tv_v_less.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tv_v_less.getText().toString().trim().length() > 0) {
                  //  btn_clear_less.setVisibility(View.VISIBLE);
                } else {
                  //  btn_clear_less.setVisibility(View.GONE);
                }
            }
        });
       // btn_clear_less.setOnClickListener(view1 -> tv_v_less.setText(""));
        if (is_nec) {
            tv_v_more.setText((ParamFilterPostSingleton.getInstance().getP_more()>0)?ParamFilterPostSingleton.getInstance().getP_more()+"":"");
            tv_v_less.setText((ParamFilterPostSingleton.getInstance().getP_less()>0)?ParamFilterPostSingleton.getInstance().getP_less()+"":"");
        }
        return view;
    }


    void getparams() {
        String more = tv_v_more.getText().toString().trim();
        String less = tv_v_less.getText().toString().trim();
        String text = "";
        if (TextUtils.isEmpty(less) && TextUtils.isEmpty(more)) {
            KeyboardUtil.hideKeyboard(getActivity());
            dismiss();
        } else if (TextUtils.isEmpty(less) && !TextUtils.isEmpty(more)) {
            //text = getString(R.string.more) + " " + more + " " + getString(R.string.and_more);
            text = String.format(getString(R.string.and_more),more);
            try{
                p_more=Integer.parseInt(more);
            }catch (Exception e){
                e.printStackTrace();
                p_more=0;
            }

            iPriceParamInterval.getText(p_more,0,text);
            KeyboardUtil.hideKeyboard(getActivity());
            dismiss();
        } else if (!TextUtils.isEmpty(less) && TextUtils.isEmpty(more)) {
            //text = getString(R.string.and_less) + " " + less;
            text = String.format(getString(R.string.and_less),less);
            try{
                p_les=Integer.parseInt(less);
            }catch (Exception e){
                e.printStackTrace();
                p_les=0;
            }

            iPriceParamInterval.getText(0,p_les,text);
            KeyboardUtil.hideKeyboard(getActivity());
            dismiss();
        } else if (!TextUtils.isEmpty(less) && !TextUtils.isEmpty(more)) {
            //text = getString(R.string.more) + " " + more + " " + getString(R.string.less) + " " + less;
            text= String.format(getString(R.string.more_les),more,less);
            try{
                p_les=Integer.parseInt(less);
            }catch (Exception e){
                e.printStackTrace();
                p_les=0;
            }
            try{
                p_more=Integer.parseInt(more);
            }catch (Exception e){
                e.printStackTrace();
                p_more=0;
            }
            iPriceParamInterval.getText(p_more,p_les,text);
            KeyboardUtil.hideKeyboard(getActivity());
            dismiss();
        }
    }
}