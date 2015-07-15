package company.greatapp.moneycircle;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.split.SplitToolActivity;

/**
 * Created by Prateek on 27-06-2015.
 */
public class MoneyDiaryFragment extends Fragment implements View.OnClickListener{

    private static final int SECTION_ARGUMENT = 0;
    Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(SECTION_ARGUMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);

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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_addId) {
            showAddNewEntryDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Lent");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.homeContainerId, fragment, "Lent ViewerFragment").commit();
                break;
            case R.id.llSplitId:
                Toast.makeText(getActivity(), "No Split Fragment is attached", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    private void showAddNewEntryDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Select your Entry Type");
        alertDialogBuilder.setItems(R.array.list_add_new_entry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startNewEntryScreen(which + 1);     // To maintain the position starting from 1
            }
        });
        alertDialogBuilder.show();
    }

    private void startNewEntryScreen(int entryType) {

        // 1 -> Income
        // 2 -> Expense
        // 3 -> Borrowed
        // 4 -> Lent
        // 5 -> Split
        switch (entryType) {
            case 1:
            case 2:
            case 3:
            case 4:
                Intent intent = new Intent(getActivity(), AddNewEntryActivity.class);
                intent.putExtra(C.ENTRY_TYPE, entryType);
                startActivity(intent);
                break;
            case 5:
                startActivity(new Intent(getActivity(), SplitToolActivity.class));
                break;
            default:
                break;
        }
    }

}
