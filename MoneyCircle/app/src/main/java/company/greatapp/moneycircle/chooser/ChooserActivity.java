package company.greatapp.moneycircle.chooser;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.RegisteredContact;

public class ChooserActivity extends ActionBarActivity {

    private ChooserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        ListView chooserList = (ListView)findViewById(R.id.lv_chooser_items);
        TextView title = (TextView)findViewById(R.id.tv_chooser_item_title);
        CheckBox selectAll = (CheckBox)findViewById(R.id.cb_chooser_select_all);
        adapter = new ChooserAdapter(this,0,getItemList());
        chooserList.setAdapter(adapter);
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelectAll(v);
            }
        });
    }

    private void onClickSelectAll(View v) {
        CheckBox cb = (CheckBox)v;
        if(cb.isChecked()) {
            changeCheckedState(true);
        } else {
            changeCheckedState(false);
        }
    }

    private void changeCheckedState(boolean state) {
        Toast.makeText(this,"SELECT ALL : "+state,Toast.LENGTH_SHORT).show();
        int count  = adapter.getCount();
        for (int i = 0; i< count;i++) {
            View view = adapter.getView(i,null,null);
            CheckBox cb = (CheckBox)view.findViewById(R.id.cb_chooser_item);
            cb.setSelected(state);
            cb.setChecked(state);
        }
    }

    private ArrayList<Model> getItemList() {
        ArrayList<Model> list = new ArrayList<Model>();
        list.add(new RegisteredContact("Salmaan"));
        list.add(new RegisteredContact("Sahrukh"));
        list.add(new RegisteredContact("Aamir"));
        list.add(new RegisteredContact("Amitaabh"));
        list.add(new RegisteredContact("Emraan Hashmi"));
        list.add(new RegisteredContact("Faizal"));
        list.add(new RegisteredContact("Raamadheer"));
        list.add(new RegisteredContact("Sultaan"));
        list.add(new RegisteredContact("Shamsaad"));
        list.add(new RegisteredContact("Definit"));
        list.add(new RegisteredContact("Sardaar"));
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chooser, menu);
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
