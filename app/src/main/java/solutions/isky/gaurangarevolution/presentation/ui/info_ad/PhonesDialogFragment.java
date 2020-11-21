package solutions.isky.gaurangarevolution.presentation.ui.info_ad;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.data.models.PhoneUser;
import solutions.isky.gaurangarevolution.presentation.utils.KeyboardUtil;
import solutions.isky.gaurangarevolution.presentation.view.SimpleDividerItemDecoration;

public class PhonesDialogFragment extends MvpAppCompatDialogFragment {
    private FastItemAdapter<PhoneUser> phoneUserFastItemAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;
    private List<PhoneUser> phoneUsers;
    private List<String> phoneStrings;
    private PhonesCauseDialogListener chooseCauseDialogListener;

    public PhonesDialogFragment() {
    }
    public interface PhonesCauseDialogListener {
        void onFinishEditDialog(PhoneUser phoneUser);
    }
    public static PhonesDialogFragment newInstance(List<PhoneUser> phoneUsers,List<String> phoneStrings,PhonesCauseDialogListener chooseCauseDialogListener) {
        PhonesDialogFragment frag = new PhonesDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        frag.chooseCauseDialogListener=chooseCauseDialogListener;
        frag.phoneUsers=phoneUsers;
        frag.phoneStrings=phoneStrings;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.phone_dialog, container);
        ButterKnife.bind(this, rootView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        phoneUserFastItemAdapter = new FastItemAdapter<>();
        phoneUserFastItemAdapter.withSelectable(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        SimpleDividerItemDecoration itemDecoration=new SimpleDividerItemDecoration(getActivity());
        mRecyclerView.addItemDecoration(itemDecoration);
        if(phoneUsers==null){
            phoneUsers=new ArrayList<>();
            for(String s:phoneStrings){
                PhoneUser user=new PhoneUser(s,s);
                phoneUsers.add(user);
            }
        }
        phoneUserFastItemAdapter.set(phoneUsers);
        phoneUserFastItemAdapter.withOnClickListener((v, adapter, item, position) -> {
            chooseCauseDialogListener.onFinishEditDialog(item);
            dismiss();
            return false;
        });
        mRecyclerView.setAdapter(phoneUserFastItemAdapter);
        phoneUserFastItemAdapter.withSavedInstanceState(savedInstanceState);
        return rootView;
    }


}