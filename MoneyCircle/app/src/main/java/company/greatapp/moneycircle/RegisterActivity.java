package company.greatapp.moneycircle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.manager.Accountant;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.manager.PreferenceManager;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.tools.RegistrationUtils;


public class RegisterActivity extends ActionBarActivity {

    private EditText et_email;
    private EditText et_name;
    private EditText et_phone;
    private TextView tv_phone;
    private Button b_register;
    private TextView tv_progress;
    private TextView tv_fail;
    private ProgressBar pb_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        IntentFilter IF = new IntentFilter();
        IF.addAction(S.ACTION_APP_SERVER_REGISTRATION_RESULT);
        IF.addAction(C.ACTION_DISPLAY_MESSAGE);
        IF.addAction(S.ACTION_GCM_REGISTRATION_RESULT);
        IF.addAction(S.ACTION_CHECK_REGISTERED_CONTACTS_RESULT);

        registerReceiver(mRegistrationResponseReceiver, IF);
    }

    private void init() {
        setContentView(R.layout.activity_register);
        et_email = (EditText)findViewById(R.id.et_register_email);
        et_name = (EditText)findViewById(R.id.et_register_name);
        et_phone = (EditText)findViewById(R.id.et_register_phone);
        tv_phone = (TextView)findViewById(R.id.tv_register_phone_text);
        tv_fail = (TextView)findViewById(R.id.tv_register_fail);
        tv_fail.setVisibility(View.GONE);
        b_register = (Button)findViewById(R.id.b_register_register_action);

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegisterButtonClick();
            }
        });
    }

    private void handleRegisterButtonClick() {
        if(validateFields()) {
            setContentView(R.layout.loading_splash_layout);
            tv_progress = (TextView)findViewById(R.id.tv_loading_splash_progress);
            pb_progress = (ProgressBar)findViewById(R.id.pb_loading_splash);
            tv_progress.setText("Registering profile to Server .........");
            String name = et_name.getText().toString();
            String phone = et_phone.getText().toString();
            String email = et_email.getText().toString();
            int gender = 0;
            int avator =  0;

            User user = new User(this, name, phone, email, gender, avator);
            user.printUser();
            if(!user.isRegisteredOnGCM() ) {
                RegistrationUtils.registerUserOnGCM(this);
                //Once GCM registration is done app will try to register on Server automatically
            } else {
                RegistrationUtils.registerUserOnAppServer(this);
            }
        }
    }

    private boolean validateFields() {
        if(TextUtils.isEmpty(et_email.getText()) || TextUtils.isEmpty(et_name.getText())
                || TextUtils.isEmpty(et_phone.getText()))
            return false;
        else
            return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mRegistrationResponseReceiver);
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

    private BroadcastReceiver mRegistrationResponseReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.d("REGISTER","VOLLEY RECEIVER : intent received ["+action+"]");

                    if(action.equals(S.ACTION_APP_SERVER_REGISTRATION_RESULT)) {
                        handleAppServerRegistrationResult(intent);
                    } else if (action.equals(S.ACTION_GCM_REGISTRATION_RESULT)) {
                        handleGCMRegistrationResult(intent);
                    }  else if(action.equals(C.ACTION_DISPLAY_MESSAGE )){
                        displayMessage(intent);
                    }   else if(action.equals(S.ACTION_CHECK_REGISTERED_CONTACTS_RESULT )){
                        handleRegisteredContactsCheckResult(intent);
                    }

                }
            };

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
                        Contact contact = cm.getContactByPhoneNumber(registeredPhoneNumber);
                        if(contact != null) {
                            contact.setRegistered(true);
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
            displayMessage("Slot [" + slot.slotCounter + "] result received, sending next..");
            sendContactSlot();
        }
    }

    private void welcomeUser() {
        PreferenceManager pm = new PreferenceManager(this);

        SharedPreferences.Editor et = pm.getEditor();
        et.putBoolean(C.PREF_REGISTRATION_PROCESS_COMPLETED, true);
        et.commit();

        Intent intent = new Intent(this,UserWelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleGCMRegistrationResult(Intent intent) {
        boolean success = intent.getBooleanExtra(S.KEY_GCM_REGISTRATION,false);
        if(success) {
            updateProgressBy(20);
            tv_progress.setText("Registering profile to Server ...");
            displayMessage("GCM Registraion successfull!");
            RegistrationUtils.registerUserOnAppServer(this);
        } else {
            displayMessage("GCM Registraion Failed!");
            init();
            tv_fail.setVisibility(View.VISIBLE);
            tv_fail.append("[APP_SERVER]");
        }
    }

    private void handleAppServerRegistrationResult(Intent intent) {
        boolean success = intent.getBooleanExtra(S.KEY_APP_SERVER_REGISTRATION,false);
        if(success) {
            User user = new User(this);
            user.updateInfo(User.U_APP_SERVER_IS_REGISTERED,""+true);
            updateProgressBy(30);
            tv_progress.setText("Registering profile to Server .........");
            displayMessage("App Server Registraion successfull!");
            initializeApp();
        } else {

            displayMessage("App Server Registraion Failed!");
            init();
            tv_fail.setVisibility(View.VISIBLE);
            tv_fail.append("[APP_SERVER]");
        }
    }

    private void initializeApp() {
        retriveContacts();
        checkingContactsInAppServer();

        initApp();
    }

    private void initApp(){

        PreferenceManager pm = new PreferenceManager(this);
        if (!pm.isDefaultCategoriesLoadedInDB()) {
            CategoryManager categoryManager = new CategoryManager(this);
            categoryManager.insertDefaultCategoriesInDB();
            SharedPreferences.Editor et = pm.getEditor();
            et.putBoolean(C.PREF_DEFAULT_CATEGORIES_LOADED, true);
            et.commit();

            Accountant accountant = new Accountant(this,false);
            accountant.initializeDb();

//            Tools.sendMoneyTransactionBroadCast(this);
        }
    }

    private  ContactManager cm;
    private Slot slot;

    private void checkingContactsInAppServer() {
        cm = new ContactManager(this);
        tv_progress.setText("Checking Friends in MoneyCircle...");
        displayMessage("checking Contacts In App Server...");

        sendContactSlot();
    }

    public void sendContactSlot() {
        if(slot == null) {
            slot = new Slot(cm.getItemList().size());
        } else {
            if(slot.isNextAvailable()) {
                slot.nextSlot();

            } else {//all slot checked
                updateProgressBy(20);
                welcomeUser();
                return;
            }
        }
        ArrayList<Contact> contactSlot = new ArrayList<Contact>();
        for(int i = slot.start; i <= slot.end; i++) {
            Contact contact = (Contact)cm.getItemList().get(i);
            contactSlot.add(contact);
        }
        JSONArray phoneNumberJsonArray = GreatJSON.getPhoneNumberArrayForContactList(contactSlot);
        RegistrationUtils.checkRegisteredContactsInAppServer(this, phoneNumberJsonArray);
        displayMessage("Slot ["+slot.slotCounter+"] sent to server");

    }

    class Slot {
        int start;
        int end;
        int maxIndex;
        int slotCounter;
        boolean isNextSlotAvailable;
        public Slot(int size){
            slotCounter = 1;
            if(size <= 50){
                start = 0;
                end = size - 1;
                maxIndex = size - 1;
                isNextSlotAvailable = false;
            } else {
                if((size-50) > 10) {
                    start = 0;
                    end = 50;
                    maxIndex = size - 1;
                    isNextSlotAvailable = true;
                } else {
                    start = 0;
                    end = size - 1;
                    maxIndex = size -1;
                    isNextSlotAvailable = false;
                }
            }
        }

        public boolean isNextAvailable(){
            return isNextSlotAvailable;
        }
        public void nextSlot() {
            if (isNextSlotAvailable) {
                slotCounter++;
                if ((maxIndex - end) <= 50) {
                    start = end + 1;
                    end = maxIndex;
                    isNextSlotAvailable = false;
                } else {
                    if (((maxIndex - end) - 50) > 10) {
                        start = end + 1;
                        end = end + 50;
                        isNextSlotAvailable = true;
                    } else {
                        start = end;
                        end = maxIndex;
                        isNextSlotAvailable = false;
                    }
                }
            }
        }
    }
    private void retriveContacts() {
        tv_progress.setText("Retriving phone contacts...");
        displayMessage("Retriving phone contacts....");
        PreferenceManager pm = new PreferenceManager(this);
        if(!pm.isDeviceContactsRetrived()) {
            ContactManager contactManager = new ContactManager(this);
            contactManager.retriveContactsFromDevice();//contact initialization
            //Tools.addDummyEntries(this);
            SharedPreferences.Editor et =  pm.getEditor();
            et.putBoolean(C.PREF_CONTACTS_RETRIVED, true);
            et.commit();
        }

        updateProgressBy(20);
    }

    private void displayMessage(String message) {
        tv_progress.append("\n"+message);
    }

    private void displayMessage(Intent intent) {
        displayMessage(intent.getStringExtra(C.DISPLAY_MESSAGE_TEXT));
    }

    private void handleRegistrationResponse(boolean success) {
        Toast.makeText(this,"RESPONSE : "+success,Toast.LENGTH_SHORT).show();
        displayMessage("response : "+success);
    }

    int mProgress = 0;
    private void updateProgressBy(int progress) {
        mProgress = mProgress + progress;
        pb_progress.setProgress(mProgress);
    }
}
