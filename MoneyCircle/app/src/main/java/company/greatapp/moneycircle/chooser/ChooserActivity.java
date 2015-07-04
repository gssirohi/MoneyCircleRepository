package company.greatapp.moneycircle.chooser;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Contact;

public class ChooserActivity extends Activity {
    public static final int REQUEST_CODE_CONTACTS = 1;
    public static final int REQUEST_CODE_REGISTERED_CONTACTS = 2;
    public static final int REQUEST_CODE_CIRCLES = 3;
    public static final int REQUEST_CODE_CATEGORIES = 4;
    private AbsListView.MultiChoiceModeListener actionModeCallBack;
    private ChooserAdapter adapter;
    private ListView chooserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        chooserList = (ListView)findViewById(R.id.lv_chooser_items);
        TextView title = (TextView)findViewById(R.id.tv_chooser_item_title);
        int requestCode  = getIntent().getIntExtra("request",REQUEST_CODE_REGISTERED_CONTACTS);
        title.setText(getChooserTitle(requestCode));
        adapter = new ChooserAdapter(this,0,getItemList(requestCode));
        chooserList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        chooserList.setAdapter(adapter);
        chooserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickListItem(parent, view, position, id);
            }
        });
        chooserList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                chooserList.clearChoices();
                chooserList.setItemChecked(arg2, true);
                chooserList.startActionMode(getActionModeCallBack());
                return true;
            }

        });

        // Capture ListView item click
        chooserList.setMultiChoiceModeListener(getActionModeCallBack());
        adapter.notifyDataSetChanged();
        chooserList.setItemsCanFocus(false);
    }

    private String getChooserTitle(int requestCode) {
        String title = "";
        switch(requestCode) {
            case REQUEST_CODE_REGISTERED_CONTACTS:
                title = "Select Registered Contacts";
                break;
            case REQUEST_CODE_CONTACTS:
                title = "Select Contacts";
                break;
            case REQUEST_CODE_CIRCLES:
                title = "Select Circles";
                break;
            case REQUEST_CODE_CATEGORIES:
                title = "Select Categories";
                break;
            default:
        }
        return title;
    }

    private AbsListView.MultiChoiceModeListener getActionModeCallBack() {
        if (actionModeCallBack == null) {
            setActionModeCallBack();
        }
        return actionModeCallBack;
    }
    private void setActionModeCallBack() {
        actionModeCallBack = new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_chooser, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.select:
                        mode.finish();
                        onClickDone();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                adapter.removeSelection();
            }

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = chooserList.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                adapter.toggleSelection(position);
            }
        };
    }
    private void onClickListItem(AdapterView<?> parent, View view, int position, long id) {
        //TextView tv = (CheckBox)view.findViewById(R.id.tv_chooser_item);
        //tv.setChecked(!tv.isChecked());//toggel
    }

    private void onClickDone() {
        SparseBooleanArray checked = chooserList.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            Log.d("split", "checking values at "+i);
            int position = checked.keyAt(i);
            if (checked.valueAt(i)) {
                Log.d("split", "FOUND checked values at : "+i);
                selectedItems.add(((Contact) adapter.getItem(position)).getContactName());
            }
        }
        Intent data = new Intent();
        //---set the data to pass back---
        //data.putExtras("uids",selectedItems);
        data.putStringArrayListExtra("uids",selectedItems);
        setResult(RESULT_OK, data);
        //---close the activity---
        finish();

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
        int count  = chooserList.getChildCount();
        for (int i = 0; i< count;i++) {
            View v = chooserList.getChildAt(i);
            chooserList.setSelected(state);// for selection
        }
    }

    private ArrayList<Model> getItemList(int code) {
        ArrayList<Model> list = new ArrayList<Model>();
        switch(code) {
            case REQUEST_CODE_CATEGORIES:

            case REQUEST_CODE_CIRCLES:

            case REQUEST_CODE_REGISTERED_CONTACTS:
                list.add(new Contact("Salmaan"));
                list.add(new Contact("Sahrukh"));
                list.add(new Contact("Aamir"));
                list.add(new Contact("Amitaabh"));
                list.add(new Contact("Emraan Hashmi"));
                list.add(new Contact("Faizal"));
                list.add(new Contact("Raamadheer"));
                list.add(new Contact("Sultaan"));
                list.add(new Contact("Shamsaad"));
                list.add(new Contact("Definit"));
                list.add(new Contact("Sardaar"));
                break;

        }
        Contact rc = new Contact("test rc");
        Toast.makeText(this,"UID: "+rc.getUID(),Toast.LENGTH_SHORT).show();
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
