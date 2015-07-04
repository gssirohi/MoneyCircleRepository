package company.greatapp.moneycircle;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ContactAndCircleActivity extends AppCompatActivity implements ActionBar.TabListener{

    private static final int TOTAL_TABS = 3;

    private ViewPager mViewPager;
    private ContactAndCircleTabAdapter mTabAdapter;

    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_and_circle);

        mActionBar = getSupportActionBar();

        mViewPager = (ViewPager)findViewById(R.id.contactTabPagerId);

        mTabAdapter = new ContactAndCircleTabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);

        mActionBar.setHomeButtonEnabled(false);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

       // for (int i = 0; i < TOTAL_TABS; i++) {
            mActionBar.addTab(mActionBar.newTab().setText("Registered Contact").setTabListener(this));
            mActionBar.addTab(mActionBar.newTab().setText("Contacts").setTabListener(this));
            mActionBar.addTab(mActionBar.newTab().setText("Circles").setTabListener(this));
       // }

        setPageChangeListener();
    }

    public void setPageChangeListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // When swiping between pages, select the
                // corresponding tab.
                Log.d("Prateek", "onPageSelected");
                mActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

        // When the tab is selected, switch to the
        // corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }
}
