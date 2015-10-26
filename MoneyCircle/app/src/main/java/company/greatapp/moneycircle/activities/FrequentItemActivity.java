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

import java.util.Collections;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.adapters.FrequentItemAdapter;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.FrequentItem;
import company.greatapp.moneycircle.tools.DateUtils;
import company.greatapp.moneycircle.tools.Tools;
import company.greatapp.moneycircle.view.FrequentItemView;

/**
 * Created by Prateek on 25-10-2015.
 */
public class FrequentItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, FrequentItemView.FrequentItemViewCallback{

    private static final String LOG_TAG = "Prateek";//FrequentItemActivity.class.getSimpleName();

    private static final int FREQUENT_ITEM_LOADER_ID = 122;
    private FrequentItemAdapter adapter;

    private SparseBooleanArray mSelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_frequent_item);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ListView lv_frequentList = (ListView)findViewById(R.id.lv_frequent_item);
        adapter = new FrequentItemAdapter(this, null, false);
        lv_frequentList.setAdapter(adapter);

        getLoaderManager().initLoader(FREQUENT_ITEM_LOADER_ID, null, this);
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
                Cursor cursor = null;
                FrequentItem frequentItem = null;
                Expense expense = null;
                boolean expenseNotCreated = false;
                for (int i = 0; i < mSelectedItems.size(); i++) {
                    if (mSelectedItems.valueAt(i)) {
                        cursor = (Cursor)adapter.getItem(i);
                        frequentItem = new FrequentItem(this, cursor);
                        expense = Tools.createExpenseFromFrequentItem(frequentItem);
                        if (expense != null) {
                            expense.setDateString(DateUtils.getCurrentDate());
                            expense.insertItemInDB(this);
                        } else {
                            expenseNotCreated = true;

                        }
                    }
                }
                if (expenseNotCreated) {
                    Toast.makeText(this,"Expense Not Created", Toast.LENGTH_LONG).show();
                }
            }
            finish();
//                adapter.getItem()
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id == FREQUENT_ITEM_LOADER_ID) {
            Log.d(LOG_TAG, "onCreateLoader FrequentItem loader");
            return new CursorLoader(this, DB.FREQUENT_ITEM_TABLE_URI,
                    DB.FREQUENT_ITEM_TABLE_PROJECTION, null, null,
                    "data DESC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == FREQUENT_ITEM_LOADER_ID) {
            Log.d(LOG_TAG, "onLoadFinished FrequentItem loader");
            adapter.swapCursor(data);
        }
        if (mSelectedItems == null) {
            Log.d(LOG_TAG, "onLoadFinished Creation of SparseArray capacity :"+data.getCount());
            mSelectedItems = new SparseBooleanArray(data.getCount());
            for (int i = 0; i < mSelectedItems.size(); i++) {
                mSelectedItems.put(i, false);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == FREQUENT_ITEM_LOADER_ID) {
            Log.d(LOG_TAG, "onLoaderReset FrequentItem loader");
            adapter.swapCursor(null);
        }
    }

    @Override
    public void onItemSelected(int index) {

        Log.d(LOG_TAG, "FrequentItemActivity onItemSelected index : " + index);
        if (mSelectedItems != null) {
            Log.d(LOG_TAG, "FrequentItemActivity onItemSelected before value : " + mSelectedItems);
            mSelectedItems.put(index, !mSelectedItems.valueAt(index));
            Log.d(LOG_TAG, "FrequentItemActivity onItemSelected after value : " + mSelectedItems);
        }
    }
}
