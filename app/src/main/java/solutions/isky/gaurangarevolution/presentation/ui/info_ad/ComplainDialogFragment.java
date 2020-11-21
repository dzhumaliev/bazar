package solutions.isky.gaurangarevolution.presentation.ui.info_ad;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;

public class ComplainDialogFragment extends MvpAppCompatDialogFragment {

    @BindView(R.id.cb_invalid_heading)
    CheckBox cb_invalid_heading;
    @BindView(R.id.cb_prohibited_product)
    CheckBox cb_prohibited_product;
    @BindView(R.id.cb_add_not_relevant)
    CheckBox cb_add_not_relevant;
    @BindView(R.id.cb_invalid_address)
    CheckBox cb_invalid_address;
    @BindView(R.id.cb_other)
    CheckBox cb_other;
    @BindView(R.id.btn_send_complain)
    Button btn_send_complain;
    @BindView(R.id.et_text)
    EditText et_text;

    private List<CheckBox> mCheckBoxList;
    private ChooseCauseDialogListener chooseCauseDialogListener;

    public ComplainDialogFragment() {
    }
    public interface ChooseCauseDialogListener {
        void onFinishEditDialog(int id,String inputText);
    }
    public static ComplainDialogFragment newInstance(ChooseCauseDialogListener chooseCauseDialogListener) {
        ComplainDialogFragment frag = new ComplainDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        frag.chooseCauseDialogListener=chooseCauseDialogListener;
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.complain_dialog, container);
        ButterKnife.bind(this, rootView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        mCheckBoxList = new ArrayList<>();

        cb_invalid_heading.setTag(1);
        mCheckBoxList.add(cb_invalid_heading);
        cb_prohibited_product.setTag(2);
        mCheckBoxList.add(cb_prohibited_product);
        cb_add_not_relevant.setTag(4);
        mCheckBoxList.add(cb_add_not_relevant);
        cb_invalid_address.setTag(8);
        mCheckBoxList.add(cb_invalid_address);
        cb_other.setTag(1024);
        mCheckBoxList.add(cb_other);



        btn_send_complain.setOnClickListener(clickListener);
        cb_other.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                et_text.setVisibility(View.VISIBLE);
            }else{
                et_text.setVisibility(View.GONE);
            }
        });

        return rootView;
    }
    View.OnClickListener clickListener=v -> {

        if(cb_other.isChecked()&&( TextUtils.isEmpty(et_text.getText().toString().trim())||(et_text.getText().toString().trim().length()<10))){
            Toast.makeText(getActivity(), R.string.describe_the_reason_more,Toast.LENGTH_SHORT).show();
            return;
        }
        int sb = 0;
        for (int i = 0; i < mCheckBoxList.size(); ++i) {
            CheckBox checkBox = mCheckBoxList.get(i);

            if(checkBox.isChecked()) {
                sb=sb+((Integer) checkBox.getTag());
            }
        }
        if(sb==0){
            Toast.makeText(getActivity(),getString(R.string.enter_the_reason_for_the_complaint),Toast.LENGTH_SHORT).show();
            return;
        }
        String s;
        if(cb_other.isChecked()){
            s=et_text.getText().toString().trim();
        }else{
            s=null;
        }
        KeyboardUtil.hideKeyboardView(getActivity(),v);
        chooseCauseDialogListener.onFinishEditDialog(sb,s);
        dismiss();
    };

}