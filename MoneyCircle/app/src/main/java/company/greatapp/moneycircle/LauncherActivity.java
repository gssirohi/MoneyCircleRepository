package company.greatapp.moneycircle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.categories.ManageCategoriesActivity;
import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.manager.PreferenceManager;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.split.SetSplitAmountActivity;
import company.greatapp.moneycircle.split.SplitToolActivity;
import company.greatapp.moneycircle.tools.Tools;


public class LauncherActivity extends ActionBarActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        listView = (ListView)findViewById(R.id.listView);
        ArrayList<String> list = new ArrayList<String>();
        initActivityList(list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // ListView Clicked item index
                int itemPosition = i;
                startNewActivity(i);
            }
        });

        PreferenceManager pm = new PreferenceManager(this);
        if(!pm.isDeviceContactsRetrived()) {
             ContactManager cm = new ContactManager(this);
             cm.retriveContactsFromDevice();//contact initialization
             Tools.addDummyEntries(this);
             SharedPreferences.Editor et =  pm.getEditor();
             et.putBoolean(C.PREF_CONTACTS_RETRIVED, true);
             et.commit();
        }

    }

    private void startNewActivity(int i) {

        switch(i) {
           /*
            Add below code for each Activity Entry
            case 1:
            startActivity(<your activity class>);
            break;
            */
            case 1:
                startActivity(new Intent(this,RegisterAndSignInActivity.class));
                break;
            case 2:
                startActivity(new Intent(this,WelcomeActivity.class));
                break;
            case 3:
                startActivity(new Intent(this,HomeActivity.class));
                break;
            case 4:
                startActivity(new Intent(this,ContactAndCircleActivity.class));
                break;
            case 5:
                startActivity(new Intent(this,SettingActivity.class));
                break;
            case 8:
                Intent intent = new Intent(this,AddNewEntryActivity.class);
                intent.putExtra(C.ENTRY_TYPE,C.ENTRY_TYPE_INCOME);
                startActivity(intent);
                break;
            case 9:
                startActivity(new Intent(this,SplitToolActivity.class));
                break;
            case 10:
                startActivity(new Intent(this,SetSplitAmountActivity.class));
                break;
            case 13:
                startActivity(new Intent(this,SetNewPasswordActivity.class));
                break;
            case 14:
                startActivity(new Intent(this,ManageCategoriesActivity.class));
                break;
            case 15:
                startActivity(new Intent(this, ViewAndEditProfileActivity.class));
                break;
            case 16:
                startActivity(new Intent(this,ContactUsActivity.class));
                break;
            case 17:
                intent = new Intent(this,ChooserActivity.class);
                intent.putExtra(C.CHOOSER_REQUEST,C.TAG_CONTACTS);
                startActivity(intent);
                break;
            case 18:
                 intent = new Intent(this,AddNewEntryActivity.class);
                intent.putExtra(C.ENTRY_TYPE,C.ENTRY_TYPE_EXPENSE);
                startActivity(intent);
                break;
            case 19:
                 intent = new Intent(this,AddNewEntryActivity.class);
                intent.putExtra(C.ENTRY_TYPE,C.ENTRY_TYPE_BORROW);
                startActivity(intent);
                break;
            case 20:
                 intent = new Intent(this,AddNewEntryActivity.class);
                intent.putExtra(C.ENTRY_TYPE,C.ENTRY_TYPE_LENDED);
                startActivity(intent);
                break;
            case 21:
                intent = new Intent(this,NewHomeActivity.class);
                intent.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_INCOME);
                startActivity(intent);
                break;
            case 22:
                intent = new Intent(this,NewHomeActivity.class);
                intent.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_EXPENSE);
                startActivity(intent);
                break;
            case 23:
                intent = new Intent(this,NewHomeActivity.class);
                intent.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_BORROW);
                startActivity(intent);
                break;
            case 24:
                intent = new Intent(this,NewHomeActivity.class);
                intent.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_LENT);
                startActivity(intent);
                break;
            case 25:
                intent = new Intent(this,NewHomeActivity.class);
                intent.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_SPLIT);
                startActivity(intent);
                break;

            default :
                String  itemValue    = (String) listView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),
                        "NO ACTIVITY ADDED for Position :" + i + "  ListItem : " + itemValue, Toast.LENGTH_SHORT)
                        .show();

        }

    }


    private void initActivityList(ArrayList<String> list) {
        /*
        Append Activity name in the string as below:
        list.add("Activity5: Settings");
         */
        list.add("Activity0");
        list.add("Activity1:Register And SignIn");
        list.add("Activity2:Welcome");
        list.add("Activity3:Home Activity");
        list.add("Activity4:Contact & Circle Activity");
        list.add("Activity5:Settings");
        list.add("Activity6");
        list.add("Activity7");
        list.add("Activity8: Add Entry[INCOME]");
        list.add("Activity9:SplitToolActivity");
        list.add("Activity10: SetSplitAmountActivity");
        list.add("Activity11");
        list.add("Activity12");
        list.add("Activity13:SetNewPasswordActivity");
        list.add("Activity14:Manage Categories");
        list.add("Activity15:Edit Profile");
        list.add("Activity16:Contact Us");
        list.add("Activity17:Chooser Activity");
        list.add("Activity18:Add Entry[EXPENSE]");
        list.add("Activity19:Add Entry[BORROW]");
        list.add("Activity20:Add Entry[LENDED]");
        list.add("Activity21:HOME[income]");
        list.add("Activity21:HOME[expense]");
        list.add("Activity21:HOME[borrow]");
        list.add("Activity21:HOME[lent]");
        list.add("Activity21:HOME[split]");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}