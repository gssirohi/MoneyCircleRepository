package company.greatapp.moneycircle;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import company.greatapp.moneycircle.adapters.NotificationCursorAdapter;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.dialogs.ContactInfoDialog;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Period;
import company.greatapp.moneycircle.view.TagItemView;


public class NotificationActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>,TagItemView.TagItemViewCallBacks{

    private static final int LODER_ID = 30;
    private ListView lv_notification;
    private View no_item_view;
    private NotificationCursorAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        lv_notification = (ListView)findViewById(R.id.lv_notification);
        no_item_view = (View)findViewById(R.id.no_item_view);
        no_item_view.setVisibility(View.GONE);
        adapter = new NotificationCursorAdapter(this, null, false);
        lv_notification.setAdapter(adapter);
        getLoaderManager().initLoader(LODER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id == LODER_ID) {
            Log.d("Split", "onCreateLoader notifications loader");
            return new CursorLoader(this, DB.PACKAGE_FROM_SERVER_TABLE_URI,
                    DB.PACKAGE_FROM_SERVER_TABLE_PROJECTION, null, null,
                    "data DESC");
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == LODER_ID) {
            adapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == LODER_ID) {
            adapter.swapCursor(null);
        }
    }

    @Override
    public void onContactTagClicked(Model model) {
        ContactInfoDialog dialog = new ContactInfoDialog(this,(Contact)model);
        dialog.show();
    }
}
