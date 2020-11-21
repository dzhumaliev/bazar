package solutions.isky.gaurangarevolution.presentation.ui.info_ad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;

public class ConfirmComplainDialogFragment extends MvpAppCompatDialogFragment {


    @BindView(R.id.btn_send_complain)
    Button btn_back;




    public ConfirmComplainDialogFragment() {
    }

    public static ConfirmComplainDialogFragment newInstance() {
        ConfirmComplainDialogFragment frag = new ConfirmComplainDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.complain_dialog_confirm, container);
        ButterKnife.bind(this, rootView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        btn_back.setOnClickListener(v -> {
            KeyboardUtil.hideKeyboardView(getActivity(),v);
            dismiss();
        });

        return rootView;
    }
}