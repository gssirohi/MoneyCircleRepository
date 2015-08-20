package company.greatapp.moneycircle.categories;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Model;

public class ManageCategoriesActivity extends ActionBarActivity {

    public static final String LOGTAG = "ManageCategories";

    CategoryExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Model>> listDataChild;
    private ArrayList<Model> mIncomeCategoryList;
    private ArrayList<Model> mExpenseCategoryList;
    private ArrayList<Model> mLentCategoryList;
    private ArrayList<Model> mBorrowCategoryList;
    private ArrayList<Model> mSplitCategoryList;

    private CategoryManager mCategoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        mCategoryManager = new CategoryManager(this, 0);  // 0 is passed to load categories for all the models

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

        Log.d(LOGTAG, "prepareListData");

        if (mCategoryManager == null) {
            return;
        }

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Model>>();

        // Adding child data
        listDataHeader.add("Income");
        listDataHeader.add("Expenses");
        listDataHeader.add("Borrowed Money");
        listDataHeader.add("Lent Money");
        listDataHeader.add("Split Money");

        // Adding child data
        mIncomeCategoryList = mCategoryManager.getIncomeCategoryList();

        mExpenseCategoryList = mCategoryManager.getExpenseCategoryList();

        mBorrowCategoryList = mCategoryManager.getBorrowCategoryList();

        mLentCategoryList = mCategoryManager.getLentCategoryList();

        mSplitCategoryList = mCategoryManager.getSplitCategoryList();

        listDataChild.put(listDataHeader.get(0), mIncomeCategoryList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), mExpenseCategoryList);
        listDataChild.put(listDataHeader.get(2), mBorrowCategoryList);
        listDataChild.put(listDataHeader.get(3), mLentCategoryList);
        listDataChild.put(listDataHeader.get(4), mSplitCategoryList);
    }

    public void onClickDelete(View view) {
        // Toast.makeText(this,"Delete Clicked",Toast.LENGTH_SHORT).show();
        int group = (Integer) view.getTag(R.string.category_group_id);
        int child = (Integer) view.getTag(R.string.category_child_id);
        removeChild(group, child);
    }

    public void onClickAdd(View view) {
        // Toast.makeText(this,"Add Clicked",Toast.LENGTH_SHORT).show();
        int group = (Integer) view.getTag(R.string.category_group_id);
        showCategoryDialog(group);
    }

    private void removeChild(int group, int child) {
        //this will not remove view immediatley
        //you have to collapse and expand again to view the effect
        //However it will be automatically controlled later on
        //because we are going to use Loaders finally with database support

        if (mCategoryManager == null) {
            return;
        }

        Model category = null;
        switch (group) {
            case 0:
                category = mIncomeCategoryList.get(child);
                // TODO instead of updating this list need to change to use loader
//                mIncomeCategoryList.remove(child);
                break;
            case 1:
                category = mExpenseCategoryList.get(child);
//                mExpenseCategoryList.remove(child);
                break;
            case 2:
                category = mBorrowCategoryList.get(child);
//                mBorrowCategoryList.remove(child);
                break;
            case 3:
                category = mLentCategoryList.get(child);
//                mLentCategoryList.remove(child);
                break;
            case 4:
                category = mSplitCategoryList.get(child);
//                mSplitCategoryList.remove(child);
                break;
        }

        if (category != null) {
            mCategoryManager.deleteItemFromDB(category);
        }
    }

    protected void showCategoryDialog(final int groupPos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);
        alert.setMessage("Enter name of category");
        alert.setTitle(listDataHeader.get(groupPos));

        alert.setView(edittext);

        alert.setPositiveButton(R.string.action_create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String categoryName = edittext.getText().toString();
                if (!TextUtils.isEmpty(categoryName)) {
                    addGroupChild(categoryName, groupPos);
                }
                dialog.dismiss();
            }
        });

        alert.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();

    }

    private void addGroupChild(String categoryName, int groupPos) {
        if (mCategoryManager == null) {
            return;
        }

        Category category = null;
        switch (groupPos) {
            case 0:
                category = new Category(categoryName, Model.MODEL_TYPE_INCOME);
                // TODO instead of updating this list need to change to use loader
//        mIncomeCategoryList.add(categoryName);
                break;
            case 1:
                category = new Category(categoryName, Model.MODEL_TYPE_EXPENSE);
//        mExpenseCategoryList.add(categoryName);
                break;
            case 2:
                category = new Category(categoryName, Model.MODEL_TYPE_BORROW);
//        mBorrowCategoryList.add(categoryName);
                break;
            case 3:
                category = new Category(categoryName, Model.MODEL_TYPE_LENT);
//        mLentCategoryList.add(categoryName);
                break;
            case 4:
                category = new Category(categoryName, Model.MODEL_TYPE_SPLIT);
//        mSplitCategoryList.add(categoryName);
                break;

        }
        if (category != null) {
            mCategoryManager.insertItemInDB(category);
        }
    }

}