package solutions.isky.gaurangarevolution.presentation.view;

import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.DinProps;
import solutions.isky.gaurangarevolution.data.models.Search_rang;
import solutions.isky.gaurangarevolution.domain.main.ItemFilterModel;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.IDinParamInterval;
import solutions.isky.gaurangarevolution.presentation.mvp.filters.TypeFilterAdapt;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;

public class DinParamInterval extends DialogFragment {
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.tv_v_more)
    EditText tv_v_more;
    @BindView(R.id.tv_v_less)
    EditText tv_v_less;
    IDinParamInterval iDinParamInterval;
    DinProps props;
    @BindView(R.id.suggested_options)
    RadioGroup layout_soptp;
    boolean is_nec;
    JsonObject jsonObject;
    HashMap<Integer, ItemFilterModel> props_value;
    @BindView(R.id.ttitle_parametrs)
    TextView ttitle_parametrs;
    String title;

    public static DinParamInterval newInstance(IDinParamInterval iDinParamInterval, DinProps props, boolean is_nec, HashMap<Integer, ItemFilterModel> props_value, String title) {
        DinParamInterval frag = new DinParamInterval();
        Bundle args = new Bundle();
        frag.setArguments(args);
        frag.iDinParamInterval = iDinParamInterval;
        frag.props = props;
        frag.is_nec = is_nec;
        frag.props_value = props_value;
        frag.title = title;
        return frag;
    }

    public DinParamInterval() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.interval_param, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ButterKnife.bind(this, view);
        if (title != null) {
            ttitle_parametrs.setText(Html.fromHtml(title));
        }
        if (props.getExtra().getSearch_range_user() == 1) {
            tv_v_more.setEnabled(true);
            tv_v_less.setEnabled(true);
        } else {
            tv_v_more.setEnabled(false);
            tv_v_more.setFocusable(false);
            tv_v_more.setFocusableInTouchMode(false);
            tv_v_less.setFocusableInTouchMode(false);
            tv_v_less.setFocusable(false);
            tv_v_less.setEnabled(false);
        }


        btn_ok.setOnClickListener(view1 -> {
            getparams();
        });
        btn_cancel.setOnClickListener(view1 -> dismiss());


        if (is_nec) {
            if (props_value != null && props_value.size() > 0) {
                JsonObject object = (JsonObject) props_value.get(props.getId()).getObject();
                if (object.has("v_more")) {
                    tv_v_more.setText(object.get("v_more").getAsString());
                } else {
                    tv_v_more.setText("");
                }
                if (object.has("v_less")) {
                    tv_v_less.setText(object.get("v_less").getAsString());
                } else {
                    tv_v_less.setText("");
                }
            } else if (ParamFilterPostSingleton.getInstance().getmProps_value().containsKey(props.getId())) {
                JsonObject object = (JsonObject) ParamFilterPostSingleton.getInstance().getmProps_value().get(props.getId()).getObject();
                if (object.has("v_more")) {
                    tv_v_more.setText(object.get("v_more").getAsString());
                } else {
                    tv_v_more.setText("");
                }
                if (object.has("v_less")) {
                    tv_v_less.setText(object.get("v_less").getAsString());
                } else {
                    tv_v_less.setText("");
                }

            }
        }

        if (props.getExtra().getSearch_ranges() != null && props.getExtra().getSearch_ranges().size() > 0) {
            layout_soptp.setVisibility(View.VISIBLE);
            setViewOpt(new ArrayList<Search_rang>(props.getExtra().getSearch_ranges().values()), view.getContext(), is_nec);
             tv_v_less.addTextChangedListener(textWatcher_less);
            tv_v_more.addTextChangedListener(textWatcher_more);
        } else {
            layout_soptp.setVisibility(View.GONE);
        }
        return view;
    }

    TextWatcher textWatcher_more = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (layout_soptp.getCheckedRadioButtonId() != -1) {
                ButtonOpt radioButton = layout_soptp.findViewById(layout_soptp.getCheckedRadioButtonId());
                if (!tv_v_more.getText().toString().equalsIgnoreCase(((Search_rang) radioButton.getTag()).getFrom())) {
                    layout_soptp.clearCheck();
                    tv_v_more.setText(s.toString());
                    tv_v_more.setSelection(tv_v_more.getText().length());
                }
            }

        }
    };
    TextWatcher textWatcher_less = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (layout_soptp.getCheckedRadioButtonId() != -1) {
                ButtonOpt radioButton = layout_soptp.findViewById(layout_soptp.getCheckedRadioButtonId());
                if (!tv_v_less.getText().toString().equalsIgnoreCase(((Search_rang) radioButton.getTag()).getTo())) {
                    layout_soptp.clearCheck();
                    tv_v_less.setText(s.toString());
                    tv_v_less.setSelection(tv_v_less.getText().length());
                }
            }

        }
    };

    private void setViewOpt(List<Search_rang> viewOpt, Context ctx, boolean is_nec) {
        layout_soptp.removeAllViews();
        for (Search_rang searchRang : viewOpt) {
            ButtonOpt buttonOpt = (ButtonOpt) LayoutInflater.from(ctx)
                    .inflate(R.layout.button_suggested_options, layout_soptp, false);
            buttonOpt.setText((TextUtils.isEmpty(searchRang.getFrom()) ? "< " + searchRang.getTo() : (TextUtils.isEmpty(searchRang.getTo()) ? searchRang.getFrom() + " >" : searchRang.getFrom() + " - " + searchRang.getTo())));
            buttonOpt.setTag(searchRang);
            buttonOpt.setId(View.generateViewId());
            buttonOpt.setBottom(R.drawable.select_rb);

//            layout_soptp.setOnClickListener(view -> {
//                tv_v_more.setText(((Search_rang) view.getTag()).getFrom());
//                tv_v_less.setText(((Search_rang) view.getTag()).getTo());
//            });
            if (layout_soptp.getVisibility() == View.VISIBLE && is_nec) {
                if (tv_v_more.getText().toString().equalsIgnoreCase(searchRang.getFrom()) && tv_v_less.getText().toString().equalsIgnoreCase(searchRang.getTo())) {
                    buttonOpt.setChecked(true);
                }
            }

            layout_soptp.addView(buttonOpt);

        }
        layout_soptp.setOnCheckedChangeListener((group, checkedId) -> {
            ButtonOpt radioButton = group.findViewById(checkedId);
            if (radioButton!=null&&radioButton.getTag() != null) {
                tv_v_more.setText(((Search_rang) radioButton.getTag()).getFrom());
                tv_v_less.setText(((Search_rang) radioButton.getTag()).getTo());
            }
        });
    }

    void getparams() {
        String more = tv_v_more.getText().toString().trim();
        String less = tv_v_less.getText().toString().trim();
        String text = "";
        if (TextUtils.isEmpty(less) && TextUtils.isEmpty(more)) {
            KeyboardUtil.hideKeyboard(getActivity());
            dismiss();
        } else if (TextUtils.isEmpty(less) && !TextUtils.isEmpty(more)) {
            text = String.format(getString(R.string.and_more), more);
            jsonObject = new JsonObject();
            jsonObject.addProperty("n", props.getId());
            jsonObject.addProperty("v_more", more);
            // jsonObject.addProperty("v_less", less);
            props_value = new HashMap<>();
            ItemFilterModel filterModel = new ItemFilterModel();
            filterModel.setId_prop(props.getId());
            filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
            filterModel.setTitle(props.getTitle());
            filterModel.setText(text);
            filterModel.setType_filter(props.getType());
            filterModel.setProps(props);
            filterModel.setObject(jsonObject);
            props_value.put(props.getId(), filterModel);
            iDinParamInterval.getText(text, props_value, props.getId());
            KeyboardUtil.hideKeyboard(getActivity());
            dismiss();
        } else if (!TextUtils.isEmpty(less) && TextUtils.isEmpty(more)) {
            text = String.format(getString(R.string.and_less), less);
            jsonObject = new JsonObject();
            jsonObject.addProperty("n", props.getId());
            //jsonObject.addProperty("v_more", more);
            jsonObject.addProperty("v_less", less);
            props_value = new HashMap<>();
            ItemFilterModel filterModel = new ItemFilterModel();
            filterModel.setId_prop(props.getId());
            filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
            filterModel.setTitle(props.getTitle());
            filterModel.setText(text);
            filterModel.setType_filter(props.getType());
            filterModel.setProps(props);
            filterModel.setObject(jsonObject);
            props_value.put(props.getId(), filterModel);
            iDinParamInterval.getText(text, props_value, props.getId());
            KeyboardUtil.hideKeyboard(getActivity());
            dismiss();
        } else if (!TextUtils.isEmpty(less) && !TextUtils.isEmpty(more)) {
            text = String.format(getString(R.string.more_les), more, less);
            jsonObject = new JsonObject();
            jsonObject.addProperty("n", props.getId());
            jsonObject.addProperty("v_more", more);
            jsonObject.addProperty("v_less", less);
            props_value = new HashMap<>();
            ItemFilterModel filterModel = new ItemFilterModel();
            filterModel.setId_prop(props.getId());
            filterModel.setTypeFilterAdapt(TypeFilterAdapt.DINPARAM);
            filterModel.setTitle(props.getTitle());
            filterModel.setText(text);
            filterModel.setType_filter(props.getType());
            filterModel.setProps(props);
            filterModel.setObject(jsonObject);
            props_value.put(props.getId(), filterModel);
            iDinParamInterval.getText(text, props_value, props.getId());
            KeyboardUtil.hideKeyboard(getActivity());
            dismiss();
        }
    }
}