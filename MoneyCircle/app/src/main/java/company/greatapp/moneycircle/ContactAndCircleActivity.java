package company.greatapp.moneycircle;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import company.greatapp.moneycircle.circles.CirclesViewFragment;
import company.greatapp.moneycircle.contacts.ContactsViewFragment;
import company.greatapp.moneycircle.contacts.RegisteredContactsViewFragment;
import company.greatapp.moneycircle.dialogs.ContactInfoDialog;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.view.TagItemView;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ContactAndCircleActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, TagItemView.TagItemViewCallBacks{

    private ViewPager mViewPager;
    private ContactAndCircleTabAdapter mTabAdapter;

    private Toolbar mToolbar;
    private ContactManager mContactManager;
    private TabLayout mTabLayout = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_and_circle);

        mToolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager)findViewById(R.id.contactTabPagerId);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);

        // Create Tabs
        mTabLayout.addTab(mTabLayout.newTab().setText("Registered Contact"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Contacts"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Circles"));

        mTabLayout.setOnTabSelectedListener(this);

        mContactManager = new ContactManager(this);

        intializeViewPager();
    }


    /**
     * Initialise ViewPager
     */
    private void intializeViewPager() {

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(RegisteredContactsViewFragment.getInstance(mContactManager));
        fragmentList.add(ContactsViewFragment.getInstance(mContactManager));
        fragmentList.add(CirclesViewFragment.getInstance(mContactManager));

        mTabAdapter = new ContactAndCircleTabAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onContactTagClicked(Model model) {
        ContactInfoDialog dialog = new ContactInfoDialog(this,(Contact)model);
        dialog.show();
    }
}
