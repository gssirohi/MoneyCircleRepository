package company.greatapp.moneycircle;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.adapters.NavDrawerListAdapter;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.dialogs.AddNewEntryDialog;
import company.greatapp.moneycircle.manager.Accountant;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.manager.PreferenceManager;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.NavDrawerItem;
import company.greatapp.moneycircle.services.PendingPackageTransportService;
import company.greatapp.moneycircle.split.SplitToolActivity;
import company.greatapp.moneycircle.view.CircleButton;
import company.greatapp.moneycircle.dialogs.ContactInfoDialog;
import company.greatapp.moneycircle.view.TagItemView;

public class MainActivity extends ActionBarActivity implements TagItemView.TagItemViewCallBacks{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private LinearLayout tabDiary;
    private LinearLayout tabTrends;
    private TabHost tabHost;
    private TabWidget tabWidget;
    private CardDesigner mCardDesigner;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //TODO: this is launcher activity now on
        //hence we need to init application settings and requirments here
        //move below init method to the launcher activity always
        initApp();

        FloatingActionButton fb_add_new = (FloatingActionButton)findViewById(R.id.fb_add_new_entry);
        fb_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddOptionsScreen();
            }
        });
        tabHost = (TabHost)findViewById(R.id.tabHost);
        initialiseTabHost();
        tabWidget = (TabWidget)findViewById(android.R.id.tabs);
        tabWidget.setVisibility(View.VISIBLE);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                handleTabChanged(tabId);
            }
        });

        tabDiary = (LinearLayout)findViewById(R.id.ll_tab1);
        tabTrends = (LinearLayout)findViewById(R.id.ll_tab2);

        mCardDesigner = new CardDesigner(this,null);

        initCardViews();
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));


        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleDrawerItemClick(parent,view,position,id);
            }
        });
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //displayView(0);
        }

        //Trying to send pending packages

        startService(new Intent(this, PendingPackageTransportService.class));
    }

    private void initCardViews() {
        tabDiary.removeAllViews();
        tabTrends.removeAllViews();

    tabDiary.addView(mCardDesigner.getCardView(CardDesigner.CARD_DAILY_REPORT));
    tabDiary.addView(mCardDesigner.getCardView(CardDesigner.CARD_INCOME));
    tabDiary.addView(mCardDesigner.getCardView(CardDesigner.CARD_EXPENSE));
    tabDiary.addView(mCardDesigner.getCardView(CardDesigner.CARD_BORROW));
    tabDiary.addView(mCardDesigner.getCardView(CardDesigner.CARD_LENT));
    tabDiary.addView(mCardDesigner.getCardView(CardDesigner.CARD_SPLIT));

        tabTrends.addView(mCardDesigner.getCardView(CardDesigner.CARD_BUDGET));
        tabTrends.addView(mCardDesigner.getCardView(CardDesigner.CARD_TOP_SPEND_AREAS));
    tabTrends.addView(mCardDesigner.getCardView(CardDesigner.CARD_UPCOMING_BORROW));
    tabTrends.addView(mCardDesigner.getCardView(CardDesigner.CARD_TOP_BORROWER));
        tabTrends.addView(mCardDesigner.getCardView(CardDesigner.CARD_UPCOMING_LENT));
    tabTrends.addView(mCardDesigner.getCardView(CardDesigner.CARD_TOP_LENDERS));


        tabDiary.invalidate();
        tabTrends.invalidate();
    }

    private void initApp(){
        registerReceiver(mAccountantUpdatedReceiver, new IntentFilter(C.ACTION_ACCOUNTANT_DB_UPDATED));

    }
    private void handleDrawerItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0://home
                break;
            case 1://people
                startActivity(new Intent(this,ContactAndCircleActivity.class));
                break;
            case 2://settings
                startActivity(new Intent(this,SettingActivity.class));
                break;

            case 3://MoneyCirclePackageFromServer
                startActivity(new Intent(this,NotificationActivity.class));
                break;
            case 4://profile
                startActivity(new Intent(this,ViewAndEditProfileActivity.class));
                break;
            case 5://developer
                startActivity(new Intent(this,ContactUsActivity.class));
                break;
            default:
                break;

        }
        mDrawerLayout.closeDrawers();
    }

    private void handleTabChanged(String tabId) {
        if(tabId.equals("DIARY")){

        } else {

        }
    }

    private void initialiseTabHost() {
        tabHost.setup();

        // DIARY
        TabHost.TabSpec dailyTab = tabHost.newTabSpec("DIARY");
        dailyTab.setContent(R.id.tab1);
        dailyTab.setIndicator("DIARY");
        tabHost.addTab(dailyTab);
        // TRENDS
        TabHost.TabSpec weeklyTab = tabHost.newTabSpec("TRENDS");
        weeklyTab.setContent(R.id.tab2);
        weeklyTab.setIndicator("TRENDS");
        tabHost.addTab(weeklyTab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
//            case R.id.action_add_new_entry:
//                showAddOptionsScreen();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    Dialog dialog;
    private void showAddOptionsScreen() {
       AddNewEntryDialog dialog = new AddNewEntryDialog(this);
       dialog.showDialogWithAnimation();
    }


    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
       // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAccountantUpdatedReceiver != null)
        unregisterReceiver(mAccountantUpdatedReceiver);
    }
    private  Accountant mAccountant;
    BroadcastReceiver mAccountantUpdatedReceiver  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("SPLIT", "intent action : " + intent.getAction());
          if(intent.getAction().equals(C.ACTION_ACCOUNTANT_DB_UPDATED)) {
              Log.d("SPLIT", "intent action : " + intent.getAction());
              mAccountant = new Accountant(MainActivity.this,true);
              mCardDesigner.initAllCardViews(mAccountant);
              Toast.makeText(MainActivity.this,"Accountant Updated",Toast.LENGTH_SHORT).show();
              //initCardViews();
          }
        }
    };

    @Override
    public void onContactTagClicked(Model model) {
        ContactInfoDialog dialog = new ContactInfoDialog(this,(Contact)model);
        dialog.show();
    }

}