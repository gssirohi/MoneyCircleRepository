package company.greatapp.moneycircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import company.greatapp.moneycircle.constants.C;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ViewerScreenFragment extends Fragment {

    public static String MODEL_TYPE;

    private int SECTION_ARGUMENT;

    private String modelType;
    private String[] mCategories;
    private ArrayAdapter<String> adapter;

    private Spinner mSpinner;

    private FragmentTabHost mTabHost;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle bundle = getArguments();
        modelType = bundle.getString(MODEL_TYPE);
        getCategories();

        ((HomeActivity)activity).onSectionAttached(SECTION_ARGUMENT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewer_screen, container, false);

        mTabHost = (FragmentTabHost)view.findViewById(R.id.tabhostId);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        // 1-> Daily Tab
        Bundle arg1 = new Bundle();
        arg1.putInt("Tab Type", 1);
        mTabHost.addTab(mTabHost.newTabSpec("daily").setIndicator("Daily"),
                TabFragment.class, arg1);

        // 2-> Weekly Tab
        Bundle arg2 = new Bundle();
        arg2.putInt("Tab Type", 2);
        mTabHost.addTab(mTabHost.newTabSpec("weekly").setIndicator("Weekly"),
                TabFragment.class, arg2);

        // 3-> Monthly Tab
        Bundle arg3 = new Bundle();
        arg3.putInt("Tab Type", 3);
        mTabHost.addTab(mTabHost.newTabSpec("monthly").setIndicator("Monthly"),
                TabFragment.class, arg3);

        // 4-> Yearly Tab
        Bundle arg4 = new Bundle();
        arg4.putInt("Tab Type", 4);
        mTabHost.addTab(mTabHost.newTabSpec("yearly").setIndicator("Yearly"),
                TabFragment.class, arg4);


        mSpinner = (Spinner)view.findViewById(R.id.spCategoryId);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        /*mSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_addId) {
            startNewEntryScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCategories() {
        if (modelType == "Income") {
            SECTION_ARGUMENT = C.ENTRY_TYPE_INCOME;
            mCategories = new String[]{"All", "Salary", "Share", "RD", "FD"};
        } else if (modelType == "Expense") {
            SECTION_ARGUMENT = C.ENTRY_TYPE_EXPENSE;
            mCategories = new String[]{"Entertainment", "Bills", "Clothing", "Food", "Trip", "Travel"};
        } else if (modelType == "Borrowed") {
            SECTION_ARGUMENT = C.ENTRY_TYPE_BORROW;
            mCategories = new String[]{"Bank Loan", "Credit Card", "From Friends", "From Family"};
        } else if (modelType == "Lent") {
            SECTION_ARGUMENT = C.ENTRY_TYPE_LENDED;
            mCategories = new String[]{"To Family", "To Friends", "To Others"};
        }
    }

    private void startNewEntryScreen() {

        Intent intent = new Intent(getActivity(), AddNewEntryActivity.class);
        intent.putExtra(C.ENTRY_TYPE,SECTION_ARGUMENT);
        startActivity(intent);
    }
}
