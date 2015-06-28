package company.greatapp.moneycircle.categories;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import company.greatapp.moneycircle.R;

public class ManageCategoriesActivity extends ActionBarActivity {

    CategoryExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ArrayList<String> income;
    private ArrayList<String> Expense;
    private ArrayList<String> lended;
    private ArrayList<String> borrowed;
    private ArrayList<String> split;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new CategoryExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
        listAdapter.notifyDataSetChanged();
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Income");
        listDataHeader.add("Expenses");
        listDataHeader.add("Borrowed Money");
        listDataHeader.add("Lended Money");
        listDataHeader.add("Split Money");

        // Adding child data
        income = new ArrayList<String>();
        income.add("Salary");
        income.add("business profit");
        income.add("Rent");
        income.add("freelancing");
        income.add("donation");
        income.add("Gambling");
        income.add("Share");
        income.add("Equity");

        Expense = new ArrayList<String>();
        Expense.add("Entertainment");
        Expense.add("Bills");
        Expense.add("Clothing");
        Expense.add("Outside Food");
        Expense.add("Kitchen");
        Expense.add("House holds");
        Expense.add("Trip");
        Expense.add("Daily Travel");
        Expense.add("Insurance Policy");

        borrowed = new ArrayList<String>();
        borrowed.add("Bank Loan");
        borrowed.add("Credit Card");
        borrowed.add("From Friends");
        borrowed.add("From Family");
        borrowed.add("Shop");

        lended = new ArrayList<String>();
        lended.add("To Friend");
        lended.add("To Family");
        lended.add("Others");

        split = new ArrayList<String>();
        split.add("Lunch");
        split.add("Pizza");
        split.add("Trip");
        split.add("Rent");
        split.add("Party");
        split.add("Movie");

        listDataChild.put(listDataHeader.get(0), income); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Expense);
        listDataChild.put(listDataHeader.get(2), borrowed);
        listDataChild.put(listDataHeader.get(3), lended);
        listDataChild.put(listDataHeader.get(4), split);
    }

    public void onClickDelete(View view) {
       // Toast.makeText(this,"Delete Clicked",Toast.LENGTH_SHORT).show();
        int group = (Integer)view.getTag(R.string.category_group_id);
        int child = (Integer)view.getTag(R.string.category_child_id);
        removeChild(group,child);
    }

    public void onClickAdd(View view) {
        // Toast.makeText(this,"Add Clicked",Toast.LENGTH_SHORT).show();
        int group = (Integer)view.getTag(R.string.category_group_id);
        showCategoryDialog(group);
    }
    private void removeChild(int group, int child) {
        //this will not remove view immediatley
        //you have to collapse and expand again to view the effect
        //However it will be automatically controlled later on
        //because we are going to use Loaders finally with database support
        switch(group) {
            case 0:
                income.remove(child);
                break;
            case 1:
                Expense.remove(child);
                break;
            case 2:
                borrowed.remove(child);
                break;
            case 3:
                lended.remove(child);
                break;
            case 4:
                split.remove(child);
                break;

        }
    }

    protected void showCategoryDialog(final int groupPos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext= new EditText(this);
        alert.setMessage("Enter name of category");
        alert.setTitle(listDataHeader.get(groupPos));

        alert.setView(edittext);

        alert.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String name = edittext.getText().toString();
                if(!TextUtils.isEmpty(name)) {
                    addGroupChild(name, groupPos);
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();

    }

    private void addGroupChild(String child, int groupPos) {
        switch(groupPos) {
        case 0:
        income.add(child);
        break;
        case 1:
        Expense.add(child);
        break;
        case 2:
        borrowed.add(child);
        break;
        case 3:
        lended.add(child);
        break;
        case 4:
        split.add(child);
        break;

    }
    }

}