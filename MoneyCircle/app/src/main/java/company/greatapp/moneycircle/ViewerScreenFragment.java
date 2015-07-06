package company.greatapp.moneycircle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ViewerScreenFragment extends Fragment {

    public static String MODEL_TYPE;
    public static String MODEL_CATEGORIES;

    private String modelType;
    private String[] mCategories;
    private ArrayAdapter<String> adapter;

    private Spinner mSpinner;

    private FragmentTabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewer_screen, container, false);

        Bundle bundle = getArguments();
        modelType = bundle.getString(MODEL_TYPE);
        getCategories();
        //mCategories = bundle.getStringArray(MODEL_CATEGORIES);

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

    //    ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Income");

    }

    private void getCategories() {
        if (modelType == "Income") {
            mCategories = new String[]{"All", "Salary", "Share", "RD", "FD"};
        } else if (modelType == "Expense") {
            mCategories = new String[]{"Entertainment", "Bills", "Clothing", "Food", "Trip", "Travel"};
        } else if (modelType == "Borrowed") {
            mCategories = new String[]{"Bank Loan", "Credit Card", "From Friends", "From Family"};
        } else if (modelType == "Lended") {
            mCategories = new String[]{"To Family", "To Friends", "To Others"};
        }
    }
}
