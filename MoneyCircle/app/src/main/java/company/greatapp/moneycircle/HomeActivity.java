package company.greatapp.moneycircle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        String[] array = getResources().getStringArray(R.array.drawer_list);
        Bundle bundle = null;
        switch (position) {
            case 0 :
                mTitle = array[position];
                fragmentManager.beginTransaction()
                        .replace(R.id.homeContainerId, new MoneyDiaryFragment())
                        .commit();
                break;
            case 1 :
                ViewerScreenFragment incomeViewerFragment = new ViewerScreenFragment();
                bundle = new Bundle();
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Income");
                incomeViewerFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.homeContainerId, incomeViewerFragment, "Income ViewerFragment").commit();
                break;
            case 2 :
                ViewerScreenFragment expenseViewerFragment = new ViewerScreenFragment();
                bundle = new Bundle();
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Expense");
                expenseViewerFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.homeContainerId, expenseViewerFragment, "Expense ViewerFragment").commit();
                break;
            case 3 :
                ViewerScreenFragment borrowViewerFragment = new ViewerScreenFragment();
                bundle = new Bundle();
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Borrowed");
                borrowViewerFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.homeContainerId, borrowViewerFragment, "Borrowed ViewerFragment").commit();
                break;
            case 4 :
                ViewerScreenFragment lendedViewerFragment = new ViewerScreenFragment();
                bundle = new Bundle();
                bundle.putString(ViewerScreenFragment.MODEL_TYPE, "Lent");
                lendedViewerFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.homeContainerId, lendedViewerFragment, "Lent ViewerFragment").commit();
                break;
            case 5 :
                Toast.makeText(this, "No View is attached", Toast.LENGTH_SHORT).show();
                break;
            case 6 :
                Toast.makeText(this, "No View is attached", Toast.LENGTH_SHORT).show();
                break;
            case 7 :
                Toast.makeText(this, "No View is attached", Toast.LENGTH_SHORT).show();
                break;
            case 8 :
                Toast.makeText(this, "No View is attached", Toast.LENGTH_SHORT).show();
                break;
            case 9 :
                Toast.makeText(this, "No View is attached", Toast.LENGTH_SHORT).show();
                break;
            case 10 :
                Toast.makeText(this, "No View is attached", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        /*fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();*/
    }

    public void onSectionAttached(int number) {
        String[] array = getResources().getStringArray(R.array.drawer_list);
        mTitle = array[number];
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

}
