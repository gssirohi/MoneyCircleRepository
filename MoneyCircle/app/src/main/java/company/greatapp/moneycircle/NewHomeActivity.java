package company.greatapp.moneycircle;


import android.app.ActionBar;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;

import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.adapters.MoneyItemAdapter;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.DBFilter;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Period;
import company.greatapp.moneycircle.tools.DateUtils;
import company.greatapp.moneycircle.tools.Tools;
import company.greatapp.moneycircle.view.TagItemView;

public class NewHomeActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>,TagItemView.TagItemViewCallBacks{

    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private ListView lv4;
    private ListView lv5;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    private Spinner sp_cat;

    private ImageButton ivRight;
    private ImageButton ivLeft;
    private TabHost tabHost;
    private TabWidget tabWidget;

    private TextView tv_period;
    private TextView tv_period_start;
    private TextView tv_period_end;

    private ArrayList<Model> mCategories;
    private ArrayAdapter<Model> adapter;

    private int modelType;
    private String category;
    private int periodType;

    private DBFilter filter;


    private final int LODER_ID = 1;

    private MoneyItemAdapter mDailyAdapter;
    private MoneyItemAdapter mWeeklyAdapter;
    private MoneyItemAdapter mMonthlyAdapter;
    private MoneyItemAdapter mYearlyAdapter;
    private MoneyItemAdapter mAllAdapter;

    private final int CHANGE_TYPE_PERIOD_TAB = 1;
    private final int CHANGE_TYPE_CATEGORY = 2;
    private final int CHANGE_TYPE_ARROW = 3;
    private final int ARROW_LEFT = 4;
    private final int ARROW_RIGHT = 5;
    private LinearLayout ll_period;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);

        modelType = getIntent().getIntExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_INCOME);
        ActionBar ab = getActionBar();
        if(ab != null)ab.setTitle(Tools.getModelName(modelType));
        //ab.setSubtitle("sub-title");

        lv1 = (ListView)findViewById(R.id.lv_1);
        lv2 = (ListView)findViewById(R.id.lv_2);
        lv3 = (ListView)findViewById(R.id.lv_3);
        lv4 = (ListView)findViewById(R.id.lv_4);
        lv5 = (ListView)findViewById(R.id.lv_5);

        tv1 = (TextView)findViewById(R.id.tv_1);
        tv2 = (TextView)findViewById(R.id.tv_2);
        tv3 = (TextView)findViewById(R.id.tv_3);
        tv4 = (TextView)findViewById(R.id.tv_4);

        ll_period = (LinearLayout)findViewById(R.id.ll_period);
        tv_period = (TextView)findViewById(R.id.tv_period);
        tv_period_start = (TextView)findViewById(R.id.tv_period_start);
        tv_period_end = (TextView)findViewById(R.id.tv_period_end);

        sp_cat = (Spinner)findViewById(R.id.sp_category);

        ivLeft = (ImageButton)findViewById(R.id.ib_left_arrow);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowButtonTouched(ARROW_LEFT);
            }
        });
        ivRight = (ImageButton)findViewById(R.id.ib_right_arrow);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleArrowButtonTouched(ARROW_RIGHT);
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
        getCategories();
        adapter = new ArrayAdapter<Model>(this, android.R.layout.simple_spinner_item, mCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cat.setAdapter(adapter);
        sp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleSpinnerItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "all";
            }
        });
        
        init();
    }
    
    public void init(){
        category = "all";//uid
        periodType = Period.PERIOD_DATE;
        filter = new DBFilter(modelType,Period.PERIOD_DATE,category);
        setPeriodView();
        mDailyAdapter = new MoneyItemAdapter(this, null, false, modelType);
        mWeeklyAdapter = new MoneyItemAdapter(this, null, false, modelType);
        mMonthlyAdapter = new MoneyItemAdapter(this, null, false, modelType);
        mYearlyAdapter = new MoneyItemAdapter(this, null, false, modelType);
        mAllAdapter = new MoneyItemAdapter(this, null, false, modelType);
        lv1.setAdapter(mDailyAdapter);
        lv2.setAdapter(mWeeklyAdapter);
        lv3.setAdapter(mMonthlyAdapter);
        lv4.setAdapter(mYearlyAdapter);
        lv5.setAdapter(mAllAdapter);
        getLoaderManager().initLoader(LODER_ID, null,this);

    }

    private void setPeriodView() {
        if(periodType == Period.PERIOD_DATE) {
            ll_period.setVisibility(View.VISIBLE);
            tv_period.setText(filter.getPeriod().getStartDate());
            tv_period.setVisibility(View.VISIBLE);
            tv_period_start.setVisibility(View.GONE);
            tv_period_end.setVisibility(View.GONE);
        } else if(periodType == Period.PERIOD_ALL){
            ll_period.setVisibility(View.GONE);
        } else {
            ll_period.setVisibility(View.VISIBLE);
            tv_period_start.setText(filter.getPeriod().getStartDate());
            tv_period_end.setText(filter.getPeriod().getEndDate());
            tv_period_start.setVisibility(View.VISIBLE);
            tv_period_end.setVisibility(View.VISIBLE);
            tv_period.setText("<->");
            //tv_period.setVisibility(View.GONE);

        }
    }

    ListView getListView(){
    switch(periodType){
        case Period.PERIOD_DATE:
            return lv1;
        case Period.PERIOD_WEEK:
            return lv2;
        case Period.PERIOD_MONTH:
            return lv3;
        case Period.PERIOD_YEAR:
            return lv4;
    }
    return null;
}
    private void handleSpinnerItemSelected(int position) {
        String item = adapter.getItem(position).getTitle();
        setView("category", item);
        changeContentView(CHANGE_TYPE_CATEGORY,position);
    }
    private void handleTabChanged(String tabId) {
        int id = Integer.parseInt(tabId);
        changeContentView(CHANGE_TYPE_PERIOD_TAB, id);
        setView("tab", "tabId");
    }

    private void handleArrowButtonTouched(int arrow_value){
       changeContentView(CHANGE_TYPE_ARROW,arrow_value);
    }
    private void setView(String type, String item) {
        if(type.equals("category")) {

        } else if (type.equals("tab")) {

        } else if (type.equals("arrow_left")) {

        } else if (type.equals("arrow_right")) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_home, menu);
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

    private void getCategories() {
        mCategories = new ArrayList<>();
        mCategories.add(new Category("All", "all"));
        CategoryManager categoryManager = new CategoryManager(this, modelType);
        mCategories.addAll(categoryManager.getItemListByModel(modelType));
    }

    private void initialiseTabHost() {
        tabHost.setup();

        // DAILY
        TabHost.TabSpec dailyTab = tabHost.newTabSpec(""+Period.PERIOD_DATE);
        dailyTab.setContent(R.id.tab1);
        dailyTab.setIndicator("DATE");
        tabHost.addTab(dailyTab);
        // WEEKLY
        TabHost.TabSpec weeklyTab = tabHost.newTabSpec(""+Period.PERIOD_WEEK);
        weeklyTab.setContent(R.id.tab2);
        weeklyTab.setIndicator("WEEK");
        tabHost.addTab(weeklyTab);
        // MONTHLY
        TabHost.TabSpec monthlyTab = tabHost.newTabSpec(""+Period.PERIOD_MONTH);
        monthlyTab.setContent(R.id.tab3);
        monthlyTab.setIndicator("MONTH");
        tabHost.addTab(monthlyTab);
        // DAILY
        TabHost.TabSpec yearlyTab = tabHost.newTabSpec(""+Period.PERIOD_YEAR);
        yearlyTab.setContent(R.id.tab4);
        yearlyTab.setIndicator("YEAR");
        tabHost.addTab(yearlyTab);
        //ALL
        TabHost.TabSpec allTab = tabHost.newTabSpec(""+Period.PERIOD_ALL);
        allTab.setContent(R.id.tab5);
        allTab.setIndicator("ALL");
        tabHost.addTab(allTab);

    }


    private void changeContentView(int change, int value) {
        switch(change) {
            case CHANGE_TYPE_PERIOD_TAB:
                //TODO:
                //try to check if change is not required
                // so that for every TAB change fresh loading not required
                // If period is current period then dont load
                periodType = value;
                filter.setPeriodType(value);
                break;
            case CHANGE_TYPE_CATEGORY:
                category = mCategories.get(value).getUID();
                filter.setCategory(category);
                break;
            case CHANGE_TYPE_ARROW:
                if(value == ARROW_LEFT) {
                    filter.previousPeriod();
                } else if(value == ARROW_RIGHT){
                    filter.nextPeriod();
                }
                break;
        }
        getLoaderManager().restartLoader(LODER_ID, null, this);
        setPeriodView();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id == LODER_ID) {
            filter.print();
            Log.d("Split", "onCreateLoader loader");
            Log.d("Split", "onCreateLoader loader filter.getDbUri()" + filter.getDbUri());
            for (int i = 0; i < filter.getProjection().length; i++) {
                Log.d("Split","onCreateLoader loader i["+i+"] filter.getProjection()"+filter.getProjection()[i]);
            }
            Log.d("Split","onCreateLoader loader filter.getSelection()"+filter.getSelection());
            if (filter.getArgs() != null) {
                for (int i = 0; i < filter.getArgs().length; i++) {
                    Log.d("Split","onCreateLoader loader i["+i+"] filter.getArgs()"+filter.getArgs()[i]);
                }
            } else {
                Log.d("Split","onCreateLoader loader filter.getArgs()"+filter.getArgs());
            }

            return new CursorLoader(this, filter.getDbUri(),
                    filter.getProjection(), filter.getSelection(), filter.getArgs(),
                    "data DESC");
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == LODER_ID) {
            if(data != null){
                Log.d("Split", "onLoadFinished  loader: COUNT: "+data.getCount());
            }
            Log.d("Split", "setting cursur in Adapter :"+periodType);
            switch(periodType){

                case Period.PERIOD_DATE:
                    mDailyAdapter.swapCursor(data);
                    break;
                case Period.PERIOD_WEEK:
                    mWeeklyAdapter.swapCursor(data);
                    break;
                case Period.PERIOD_MONTH:
                    mMonthlyAdapter.swapCursor(data);
                    break;
                case Period.PERIOD_YEAR:
                    mYearlyAdapter.swapCursor(data);
                    break;
                case Period.PERIOD_ALL:
                    mAllAdapter.swapCursor(data);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == LODER_ID) {
            Log.d("Split","reseting  loader");
            mDailyAdapter.swapCursor(null);
            mWeeklyAdapter.swapCursor(null);
            mMonthlyAdapter.swapCursor(null);
            mYearlyAdapter.swapCursor(null);
            mAllAdapter.swapCursor(null);
        }
    }

    @Override
    public void onContactTagClicked(Model model) {
        //Toast.makeText(this,"callback : "+model.getTitle()+"["+model.getModelType()+"]",Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.contact_info_dialog_layout, null, false);

        Dialog dialog = new Dialog(this);
        if(C.CONTACT_INFO_DIALOG_TRANSPARENT) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        dialog.setContentView(viewGroup);
        TextView tv_name = (TextView)viewGroup.findViewById(R.id.tv_contact_name);
        TextView tv_value = (TextView)viewGroup.findViewById(R.id.tv_contact_money_value);
        Contact c = (Contact)model;

        float value = c.getLentAmountToThis() - c.getBorrowedAmountfromThis();
        String msg = "";
        if(value > 0) {
            msg = "owes you "+value;
            tv_value.setTextColor(getResources().getColor(R.color.lent));
        } else if(value < 0) {
            tv_value.setTextColor(getResources().getColor(R.color.borrow));
            msg = "you owe "+value;
        } else {
            tv_value.setTextColor(getResources().getColor(R.color.white));
            msg = "settled";
        }
        tv_value.setText(msg);
        tv_name.setText(c.getContactName());
        dialog.setTitle("Contact Cash Flow");
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(C.CONTACT_INFO_DIALOG_TRANSPARENT) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.show();
    }
}
