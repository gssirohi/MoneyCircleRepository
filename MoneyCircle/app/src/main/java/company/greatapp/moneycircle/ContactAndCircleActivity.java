package company.greatapp.moneycircle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import company.greatapp.moneycircle.circles.CirclesViewFragment;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.contacts.ContactsViewFragment;
import company.greatapp.moneycircle.contacts.RegisteredContactsViewFragment;
import company.greatapp.moneycircle.dialogs.ContactInfoDialog;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Slot;
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.tools.RegistrationUtils;
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
    private FloatingActionButton fb_refresh_contact;
    private TextView tv_progress;
    private ProgressBar pb_progress;
    private RegisteredContactsViewFragment registeredFragment;

    private BroadcastReceiver mContactSyncResponseReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();

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

    private void initUi() {
        setContentView(R.layout.activity_contacts_and_circle);

        fb_refresh_contact = (FloatingActionButton)findViewById(R.id.fb_refresh_contact);
        fb_refresh_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContacts();
            }
        });
        mToolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mContactSyncResponseReceiver != null ) {
            unregisterReceiver(mContactSyncResponseReceiver);
        }
    }

    private Slot slot;

    private void setReceiver() {
        mContactSyncResponseReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("CONTACT SYNC", "CONTACT SYNC RECEIVER : intent received [" + action + "]");
                if(action.equals(S.ACTION_CHECK_REGISTERED_CONTACTS_RESULT )){
                    handleRegisteredContactsCheckResult(intent);
                }  else if(action.equals(C.ACTION_DISPLAY_MESSAGE )){
                    displayMessage(intent);
                }

            }
        };

    }
    private void refreshContacts() {

        setReceiver();

        setContentView(R.layout.loading_splash_layout);
        IntentFilter IF = new IntentFilter();
        IF.addAction(C.ACTION_DISPLAY_MESSAGE);
        IF.addAction(S.ACTION_CHECK_REGISTERED_CONTACTS_RESULT);

        registerReceiver(mContactSyncResponseReceiver, IF);
        tv_progress = (TextView)findViewById(R.id.tv_loading_splash_progress);
        pb_progress = (ProgressBar)findViewById(R.id.pb_loading_splash);

        tv_progress.setText("Checking Friends in MoneyCircle...");
        displayMessage("checking Contacts In App Server...");

        sendContactSlot();
    }

    public void sendContactSlot() {
        if(slot == null) {
            slot = new Slot(mContactManager.getItemList().size());
        } else {
            if(slot.isNextAvailable()) {
                slot.nextSlot();

            } else {//all slot checked
                updateProgressBy(20);
                resetOriginalScreen();
                return;
            }
        }
        ArrayList<Contact> contactSlot = new ArrayList<Contact>();
        for(int i = slot.getStart(); i <= slot.getEnd(); i++) {
            Contact contact = (Contact)mContactManager.getItemList().get(i);
            contactSlot.add(contact);
        }
        JSONArray phoneNumberJsonArray = GreatJSON.getPhoneNumberArrayForContactList(contactSlot);
        RegistrationUtils.checkRegisteredContactsInAppServer(this, phoneNumberJsonArray);
        displayMessage("Slot [" + slot.getSlotCounter() + "] sent to server");

    }

    private void resetOriginalScreen() {
        if(mContactSyncResponseReceiver != null){
            unregisterReceiver(mContactSyncResponseReceiver);
            mContactSyncResponseReceiver = null;
        }

        mContactManager = new ContactManager(this);

        //TODO: FOR PRATEEK
        // at this point mContactManager has the latest content
        // and loading screen is still showing with progress finish status
        // Now you have to set Original activity screen here and screen should
        // show latest data
    }


    /**
     * Initialise ViewPager
     */
    private void intializeViewPager() {

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        registeredFragment = RegisteredContactsViewFragment.getInstance(mContactManager);
        fragmentList.add(registeredFragment);
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

    private void displayMessage(String message) {
        tv_progress.append("\n" + message);
    }

    private void displayMessage(Intent intent) {
        displayMessage(intent.getStringExtra(C.DISPLAY_MESSAGE_TEXT));
    }


    int mProgress = 0;
    private void updateProgressBy(int progress) {
        mProgress = mProgress + progress;
        pb_progress.setProgress(mProgress);
    }



    private void handleRegisteredContactsCheckResult(Intent intent) {
        String registeredContactsJsonArrayString = intent.getStringExtra(S.KEY_REGISTERED_CONTACTS_RESULT);
        updateProgressBy(30);
        ContactManager cm = new ContactManager(this);
        try {
            JSONArray jsonArray = new JSONArray(registeredContactsJsonArrayString);
            if(jsonArray != null){
                int length = jsonArray.length();
                for (int i = 0; i<length;i++) {
                    JSONObject obj = (JSONObject)jsonArray.get(i);
                    if(obj != null) {
                        String registeredPhoneNumber = obj.getString(S.PHONE_NUMBER);
                        int gender = obj.getInt(S.GENDER);
                        Contact contact = cm.getContactByPhoneNumber(registeredPhoneNumber);
                        if(contact != null) {
                            contact.setRegistered(true);
                            contact.setGender(gender);
                            contact.updateItemInDb(this);
                        }
                    }
                }

            } else {
                Toast.makeText(this,"contacts sync error!",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            displayMessage("Slot [" + slot.getSlotCounter() + "] result received, sending next..");
            sendContactSlot();
        }
    }

}
