package company.greatapp.moneycircle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Prateek on 27-06-2015.
 */
public class MoneyDiaryFragment extends Fragment implements View.OnClickListener{

    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_money_diary, container, false);

        mContext = getActivity();

        LinearLayout llIncome = (LinearLayout)view.findViewById(R.id.llIncomeId);
        llIncome.setOnClickListener(this);
        LinearLayout llExpense = (LinearLayout)view.findViewById(R.id.llExpenseId);
        llExpense.setOnClickListener(this);
        LinearLayout llBorrow = (LinearLayout)view.findViewById(R.id.llBorrowId);
        llBorrow.setOnClickListener(this);
        LinearLayout llLend = (LinearLayout)view.findViewById(R.id.llLendedId);
        llLend.setOnClickListener(this);
        LinearLayout llSplit = (LinearLayout)view.findViewById(R.id.llSplitId);
        llSplit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        ViewerScreenFragment fragment = null;
        Bundle bundle = null;
        switch (v.getId()) {
            case R.id.llIncomeId:
                fragment = new ViewerScreenFragment();
                bundle = new Bundle();
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Income");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.homeContainerId, fragment, "Income ViewerFragment").commit();
                break;
            case R.id.llExpenseId:
                fragment = new ViewerScreenFragment();
                bundle = new Bundle();
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Expense");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.homeContainerId, fragment, "Expense ViewerFragment").commit();
                break;
            case R.id.llBorrowId:
                fragment = new ViewerScreenFragment();
                bundle = new Bundle();
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Borrowed");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.homeContainerId, fragment, "Borrowed ViewerFragment").commit();
                break;
            case R.id.llLendedId:
                fragment = new ViewerScreenFragment();
                bundle = new Bundle();
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Lended");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.homeContainerId, fragment, "Lended ViewerFragment").commit();
                break;
            case R.id.llSplitId:
                Toast.makeText(getActivity(), "No Split Fragment is attached", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

}
