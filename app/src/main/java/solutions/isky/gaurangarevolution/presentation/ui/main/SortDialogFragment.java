package solutions.isky.gaurangarevolution.presentation.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import solutions.isky.gaurangarevolution.R;
import solutions.isky.gaurangarevolution.presentation.databases.GroupSort;
import solutions.isky.gaurangarevolution.presentation.databases.ParamFilterPostSingleton;

/**
 * Created by sergey on 23.03.18.
 */

public class SortDialogFragment extends MvpAppCompatDialogFragment {
    @BindView(R.id.sort_newest)
    TextView sort_newest;
    @BindView(R.id.sort_cheapest)
    TextView sort_cheapest;
    @BindView(R.id.sort_expensive)
    TextView sort_expensive;

    private ChooseCauseDialogListener chooseCauseDialogListener;

    public SortDialogFragment() {
    }

    public interface ChooseCauseDialogListener {
        void onFinishSortDialog();
    }

    public static SortDialogFragment newInstance(ChooseCauseDialogListener chooseCauseDialogListener) {
        SortDialogFragment frag = new SortDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        frag.chooseCauseDialogListener = chooseCauseDialogListener;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.sort_dialog, container);
        ButterKnife.bind(this, rootView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        sort_newest.setOnClickListener(listener);
        sort_cheapest.setOnClickListener(listener);
        sort_expensive.setOnClickListener(listener);
        init();
        return rootView;
    }

    private void init() {
        sort_newest.setTypeface(Typeface.DEFAULT,((GroupSort.newest.equalsIgnoreCase(ParamFilterPostSingleton.getInstance().getSort()))?Typeface.BOLD:Typeface.NORMAL));
        sort_cheapest.setTypeface(Typeface.DEFAULT,((GroupSort.cheapest.equalsIgnoreCase(ParamFilterPostSingleton.getInstance().getSort()))?Typeface.BOLD:Typeface.NORMAL));
        sort_expensive.setTypeface(Typeface.DEFAULT,((GroupSort.expensive.equalsIgnoreCase(ParamFilterPostSingleton.getInstance().getSort()))?Typeface.BOLD:Typeface.NORMAL));
    }

    private View.OnClickListener listener = v -> {
        if (v.getId() == sort_newest.getId()) {
            ParamFilterPostSingleton.getInstance().setSort(GroupSort.newest);
        } else if (v.getId() == sort_cheapest.getId()) {
            ParamFilterPostSingleton.getInstance().setSort(GroupSort.cheapest);
        } else if (v.getId() == sort_expensive.getId()) {
            ParamFilterPostSingleton.getInstance().setSort(GroupSort.expensive);
        }
        if (chooseCauseDialogListener != null) {
            chooseCauseDialogListener.onFinishSortDialog();
            dismiss();
        }

    };
}
