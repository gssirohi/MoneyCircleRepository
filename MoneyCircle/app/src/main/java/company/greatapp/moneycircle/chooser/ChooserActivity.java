package company.greatapp.moneycircle.chooser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.manager.CircleManager;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Model;

public class ChooserActivity extends Activity {

    private final String LOGTAG = getClass().getSimpleName().toString();
    private ActionMode.Callback mActionModeCallBack;
    private ChooserAdapter mAdapter;
    private ListView mChooserList;

    private SearchView mSearchView;
    private boolean isDonePressed;

    private ActionMode mActionMode;
    private GridView mGridView;
    private ChooserGridAdapter mGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        mChooserList = (ListView)findViewById(R.id.lv_chooser_items);
        mGridView = (GridView)findViewById(R.id.gv_chooser_items);
        TextView title = (TextView)findViewById(R.id.tv_chooser_item_title);

        int requestCode  = getIntent().getIntExtra(C.CHOOSER_REQUEST,C.TAG_CONTACTS);
        int choiceMode  = getIntent().getIntExtra(C.CHOOSER_CHOICE_MODE, ListView.CHOICE_MODE_SINGLE);
        int modelTypeForCategory   = getIntent().getIntExtra(C.CHOOSER_MODEL, Model.MODEL_TYPE_INCOME);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        if (requestCode != C.TAG_CATEGORIES) {
            mSearchView.setVisibility(View.VISIBLE);
            mSearchView.setQueryHint("Search View");

            mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    /*Toast.makeText(getBaseContext(), "onFocusChange " + String.valueOf(hasFocus),
                            Toast.LENGTH_SHORT).show();*/
                }
            });

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    /*Toast.makeText(getBaseContext(), "onQueryTextSubmit " + query,
                            Toast.LENGTH_SHORT).show();*/
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    /*Toast.makeText(getBaseContext(), "onQueryTextChange " + newText,
                            Toast.LENGTH_SHORT).show();*/
                    onQueryChange(newText);
                    return false;
                }
            });
        }

        title.setText(getChooserTitle(requestCode));
        mAdapter = new ChooserAdapter(this, getItemList(requestCode, modelTypeForCategory), choiceMode);
        mGridAdapter = new ChooserGridAdapter(this, getItemList(requestCode, modelTypeForCategory), choiceMode);
        mChooserList.setChoiceMode(choiceMode);
        mGridView.setChoiceMode(choiceMode);
        Log.d(LOGTAG, "onCreate choiceMode" + choiceMode);
        mChooserList.setAdapter(mAdapter);
        mGridView.setAdapter(mGridAdapter);


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOGTAG, "setOnItemClickListener onItemClick");
                if (mActionModeCallBack == null) {
                    mGridView.startActionMode(getActionModeCallBack());
                }
                onClickListItem(parent, view, position, id);
            }
        });
        mChooserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOGTAG, "setOnItemClickListener onItemClick");
                if (mActionModeCallBack == null) {
                    mChooserList.startActionMode(getActionModeCallBack());
                }
                onClickListItem(parent, view, position, id);
            }
        });

        /*mChooserList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                Log.d(LOGTAG, "setOnItemLongClickListener onItemClick");
                mChooserList.clearChoices();
                mChooserList.setItemChecked(arg2, true);
                mChooserList.startActionMode(getActionModeCallBack());
                return true;
            }

        });*/

        // Capture ListView item click
//        startMultipleModeActionCallbak();
//        mChooserList.setMultiChoiceModeListener(actionMultiModeCallBack);
//        mAdapter.notifyDataSetChanged();
        mChooserList.setItemsCanFocus(false);

    }

    private String getChooserTitle(int requestCode) {
        String title = "";
        switch(requestCode) {
            case C.TAG_REGISTERED_CONTACTS:
                title = "Select Registered Contacts";
                break;
            case C.TAG_CONTACTS:
                title = "Select Contacts";
                break;
            case C.TAG_CIRCLES:
                title = "Select Circles";
                break;
            case C.TAG_CATEGORIES:
                title = "Select Categories";
                break;
            default:
        }
        return title;
    }

    /*private AbsListView.MultiChoiceModeListener getActionModeCallBack() {
        if (mActionModeCallBack == null) {
            setActionModeCallBack();
        }
        return mActionModeCallBack;
    }*/
    private ActionMode.Callback getActionModeCallBack() {
        if (mActionModeCallBack == null) {
            setActionModeCallBack();
        }
        return mActionModeCallBack;
    }

    private void setActionModeCallBack() {
        mActionModeCallBack = new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                Log.d(LOGTAG,"onCreateActionMode");
                mActionMode = mode;
                mode.getMenuInflater().inflate(R.menu.menu_chooser, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                Log.d(LOGTAG,"onPrepareActionMode");
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                Log.d(LOGTAG, "onActionItemClicked");
                switch (item.getItemId()) {
                    case R.id.select:
                        onClickDone();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                Log.d(LOGTAG,"onDestroyActionMode");
                mAdapter.removeSelection();
                mGridAdapter.removeSelection();
                mChooserList.clearChoices();
                mGridView.clearChoices();
                if (mActionModeCallBack != null) {
                    mActionModeCallBack = null;
                    mActionMode = null;
                }
            }
        };
        /*actionMultiModeCallBack = new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                Log.d(LOGTAG,"onCreateActionMode");
                mode.getMenuInflater().inflate(R.menu.menu_chooser, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                Log.d(LOGTAG,"onPrepareActionMode");
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                Log.d(LOGTAG,"onActionItemClicked");
                switch (item.getItemId()) {
                    case R.id.select:
                        onClickDone();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                Log.d(LOGTAG,"onDestroyActionMode");
                    mAdapter.removeSelection();
                    mChooserList.clearChoices();
            }

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                Log.d(LOGTAG,"onItemCheckedStateChanged");
                // Capture total checked items
                final int checkedCount = mChooserList.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                mAdapter.toggleSelection(position);
            }
        };*/
    }

    private void onClickListItem(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOGTAG, "onClickListItem position:" + position);
        mAdapter.toggleSelection(position);
        mGridAdapter.toggleSelection(position);
        if (mChooserList.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
            Log.d(LOGTAG, "onClickListItem Multiple");

           // int checkedCount = mAdapter.getSelectedItemCount();
            int checkedCount = mGridAdapter.getSelectedItemCount();
            if (mActionMode != null) {
                // Set the CAB title according to total checked items
                mActionMode.setTitle(checkedCount + " Selected");
            }
        }
        if (mGridView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
            Log.d(LOGTAG, "onClickListItem Multiple");

            //int checkedCount = mAdapter.getSelectedItemCount();
            int checkedCount = mGridAdapter.getSelectedItemCount();
            if (mActionMode != null) {
                // Set the CAB title according to total checked items
                mActionMode.setTitle(checkedCount + " Selected");
            }
            CheckBox cb = (CheckBox)view.findViewById(R.id.cb_chooser_grid_item);
            if(cb.isChecked()) {
                 cb.setChecked(false);
            } else {
                 cb.setChecked(true);
            }
        }

    }

    private void onClickDone() {
        Log.d(LOGTAG, "onClickDone");
        //ArrayList<String> selectedItems = mAdapter.getSelectedItems();
        ArrayList<String> selectedItems = mGridAdapter.getSelectedItems();

        /*SparseBooleanArray checked = mChooserList.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            Log.d("split", "checking values at "+i);
            int position = checked.keyAt(i);
            if (checked.valueAt(i)) {
                Log.d("split", "FOUND checked values at : "+position);
                selectedItems.add((mAdapter.getItem(position)).getUID());
            }
        }*/
        Intent data = new Intent();
        //---set the data to pass back---
        //data.putExtras("uids",selectedItems);
        data.putStringArrayListExtra("uids", selectedItems);
        if (selectedItems.size() != 0) {
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED, data);
        }

        //---close the activity---
        finish();

    }

    private ArrayList<Model> getItemList(int requiredModelListCode, int modelTypeForCategoryList) {
        Log.d(LOGTAG, "getItemList");
        ArrayList<Model> list = new ArrayList<Model>();
        switch(requiredModelListCode) {
            case C.TAG_CATEGORIES:
                CategoryManager categoryManager = new CategoryManager(this, modelTypeForCategoryList);
                return categoryManager.getItemListByModel(modelTypeForCategoryList);
            case C.TAG_CIRCLES:
                CircleManager circleManager = new CircleManager(this);
                return circleManager.getItemList();
            case C.TAG_REGISTERED_CONTACTS:
                break;
            case C.TAG_CONTACTS:
                ContactManager cm = new ContactManager(this);
                list = cm.getItemList();
                break;

        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void onQueryChange(String newText) {
        mAdapter.filter(newText);
        mGridAdapter.filter(newText);
    }

//    private void onClickSelectAll(View v) {
//        CheckBox cb = (CheckBox)v;
//        if(cb.isChecked()) {
//            changeCheckedState(true);
//        } else {
//            changeCheckedState(false);
//        }
//    }

//    private void changeCheckedState(boolean state) {
//        Toast.makeText(this,"SELECT ALL : "+state,Toast.LENGTH_SHORT).show();
//        int count  = mChooserList.getChildCount();
//        for (int i = 0; i< count;i++) {
//            View v = mChooserList.getChildAt(i);
//            mChooserList.setSelected(state);// for selection
//        }
//    }

    /*public void startMultipleModeActionCallbak() {
        actionMultiModeCallBack = new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                Log.d(LOGTAG,"actionMultiModeCallBack onCreateActionMode");
                mode.getMenuInflater().inflate(R.menu.menu_chooser, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                Log.d(LOGTAG,"actionMultiModeCallBack onPrepareActionMode");
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                Log.d(LOGTAG,"actionMultiModeCallBack onActionItemClicked");
                switch (item.getItemId()) {
                    case R.id.select:
                        onClickDone();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                Log.d(LOGTAG,"actionMultiModeCallBack onDestroyActionMode");
                mAdapter.removeSelection();
                mChooserList.clearChoices();
            }

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                Log.d(LOGTAG,"actionMultiModeCallBack onItemCheckedStateChanged");
                // Capture total checked items
                final int checkedCount = mChooserList.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                mAdapter.toggleSelection(position);
            }
        };
    }*/


}
