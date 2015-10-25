package company.greatapp.moneycircle.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.adapters.FrequentItemAdapter;
import company.greatapp.moneycircle.constants.DB;

/**
 * Created by Prateek on 25-10-2015.
 */
public class FrequentItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = FrequentItemActivity.class.getSimpleName();

    private static final int FREQUENT_ITEM_LOADER_ID = 122;
    private FrequentItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_frequent_item);

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
            adapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == FREQUENT_ITEM_LOADER_ID) {
            adapter.swapCursor(null);
        }
    }
}
