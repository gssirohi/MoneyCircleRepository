package company.greateapp.moneycircle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class LauncherActivity extends ActionBarActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                int itemPosition     = i;
                startNewActivity(i);
            }
        });
    }

    private void startNewActivity(int i) {

        switch(i) {
           /*
            Add below code for each Activity Entry
            case 1:
            startActivity(<your activity class>);
            break;
            */
            case 2:
                startActivity(new Intent(this,WelcomeActivity.class));
                break;
            case 16:
                startActivity(new Intent(this,ContactUs.class));
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
        list.add("Activity1");
        list.add("Activity2");
        list.add("Activity3");
        list.add("Activity4");
        list.add("Activity5");
        list.add("Activity6");
        list.add("Activity7");
        list.add("Activity8");
        list.add("Activity9");
        list.add("Activity10");
        list.add("Activity11");
        list.add("Activity12");
        list.add("Activity13");
        list.add("Activity14");
        list.add("Activity15");
        list.add("Activity16");
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