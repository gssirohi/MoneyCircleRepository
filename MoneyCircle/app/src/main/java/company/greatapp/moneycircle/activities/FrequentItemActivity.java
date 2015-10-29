package company.greatapp.moneycircle.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.adapters.FrequentItemAdapter;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.manager.FrequentItemManager;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.FrequentItem;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.DateUtils;
import company.greatapp.moneycircle.tools.Tools;
import company.greatapp.moneycircle.view.FrequentItemView;

/**
 * Created by Prateek on 25-10-2015.
 */
public class FrequentItemActivity extends AppCompatActivity implements FrequentItemView.FrequentItemViewCallback{

    private static final String LOG_TAG = FrequentItemActivity.class.getSimpleName();

    private FrequentItemAdapter adapter;

    private SparseBooleanArray mSelectedItems;
    private ArrayList<FrequentItem> mFrequentItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_frequent_item);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ListView lv_frequentList = (ListView)findViewById(R.id.lv_frequent_item);
        FrequentItemManager manager = new FrequentItemManager(this);
        mFrequentItems = manager.getFrequentItemList();
        adapter = new FrequentItemAdapter(this, mFrequentItems);
        lv_frequentList.setAdapter(adapter);

        initializeSpareArray();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_frequent_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_done) {
            if (mSelectedItems != null) {
                FrequentItem frequentItem = null;
                Expense expense = null;
                boolean expenseCreated = false;
                for (int i = 0; i < mSelectedItems.size(); i++) {
                    if (mSelectedItems.valueAt(i)) {
                        frequentItem = (FrequentItem)adapter.getItem(i);
                        expense = Tools.createExpenseFromFrequentItem(frequentItem);
                        if (expense != null) {
                            expense.setDateString(DateUtils.getCurrentDate());
                            expense.insertItemInDB(this);
                            expenseCreated = true;

                            Category cat = expense.getCategory();
                            cat.setSpentAmountOnThis(cat.getSpentAmountOnThis() + expense.getAmount());
                            cat.updateItemInDb(this);

                            Tools.sendMoneyTransactionBroadCast(this, expense, Model.MODEL_TYPE_EXPENSE);
                        } else {
                            expenseCreated =false;
                        }
                    }
                }
                if (!expenseCreated) {
                    Toast.makeText(this,"Expense Not Created", Toast.LENGTH_LONG).show();
                }
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeSpareArray() {
        if (mSelectedItems == null && mFrequentItems != null && mFrequentItems.size() > 0) {
            int itemCount = mFrequentItems.size();
            Log.d(LOG_TAG, "initializeSpareArray Creation of SparseArray capacity :"+itemCount);
            mSelectedItems = new SparseBooleanArray(itemCount);
            for (int i = 0; i < itemCount; i++) {
                mSelectedItems.put(i, false);
                Log.d(LOG_TAG, "initializeSpareArray mSelectedItems Loop :" + mSelectedItems);
            }
            Log.d(LOG_TAG, "initializeSpareArray mSelectedItems intialization :"+mSelectedItems);
        }
    }

    @Override
    public void onItemSelected(int index) {

        Log.d(LOG_TAG, "FrequentItemActivity onItemSelected index : " + index);
        if (mSelectedItems != null) {
            Log.d(LOG_TAG, "FrequentItemActivity onItemSelected before value : " + mSelectedItems);
            mSelectedItems.put(index, !mSelectedItems.valueAt(index));
            Log.d(LOG_TAG, "FrequentItemActivity onItemSelected after value : " + mSelectedItems);
            boolean previousValue = mFrequentItems.get(index).isSelected();
            mFrequentItems.get(index).setIsSelected(!previousValue);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemDeleted(int index) {

        mFrequentItems.get(index).deleteItemInDb(this);
        mFrequentItems.remove(index);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAmountChange(int index, float amount) {
        Log.d(LOG_TAG, "FrequentItemActivity onAmountChange amount : " + amount);
        mFrequentItems.get(index).setAmount(amount);
    }
}
