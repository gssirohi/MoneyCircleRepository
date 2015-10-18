package company.greatapp.moneycircle;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Spinner;

import company.greatapp.moneycircle.adapters.CategoryCursorAdapter;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.dialogs.AddNewTitleDialog;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.view.TitleSelectionView;

public class CategoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private int LODER_ID = 34;
    private CategoryCursorAdapter adapter;
    private GridView grid;
    private Spinner spinner;
    private TitleSelectionView titleSelection;
    private String mSelection;
    private int mCategoryType;
    private AddNewTitleDialog mTitleDialog;
    private FloatingActionButton addCategoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        adapter = new CategoryCursorAdapter(this,null,false);

        String[] types = new String[]{"Income","Expense","Borrow","Lent"};

        titleSelection = (TitleSelectionView)findViewById(R.id.title_selection_categories);
        titleSelection.setOnTitleSelectedListner(new TitleSelectionView.OnTitleSelectedListener() {
            @Override
            public void onTitleSelected(int position, String title) {
                handleCategoryTypeChanged(position, title);
            }
        });
        titleSelection.setTitleList(types);
        titleSelection.setDialogHeaderTitle("Category Type");
        titleSelection.setSelection(0);
        mCategoryType = 0;
        titleSelection.setIsDialogCancelButtonRequired(true);

        addCategoryButton = (FloatingActionButton)findViewById(R.id.fb_add_category);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewCategoryDailog();
            }
        });
        grid = (GridView)findViewById(R.id.gv_cat);
        grid.setAdapter(adapter);
        mSelection = DB.CATEGORY_FOR_INCOME + "="+ "1";
        getLoaderManager().initLoader(LODER_ID, null, this);
    }

    private void showAddNewCategoryDailog() {

        if(mTitleDialog == null) {
            mTitleDialog = new AddNewTitleDialog(this, new AddNewTitleDialog.TitleAddedListner() {
                @Override
                public void onTitleAdded(String title) {
                    newEntryAdded(title);
                }
            });

        }

        mTitleDialog.setDialogTitle("New " + titleSelection.getSelectedTitle() + " Category");
        mTitleDialog.setButtonText("Add in " + titleSelection.getSelectedTitle());
        mTitleDialog.show();
    }

    private void newEntryAdded(String title) {
        Category category = new Category(title,R.drawable.icon_cat_unknown);
        switch(mCategoryType) {
            case 0://Income
                category.setForIncome(true);
                break;
            case 1://Expense
                category.setForExpense(true);
                break;
            case 2:///Borrow
                category.setForBorrow(true);
                break;
            case 3://Lent
                category.setForLent(true);
                break;
        }
        category.insertItemInDB(this);
    }

    private void handleCategoryTypeChanged(int position, String title) {
        mCategoryType = position;
        String selection = "";
        switch(position) {
            case 0://Income
                selection = DB.CATEGORY_FOR_INCOME + "="+ "1";
                break;
            case 1://Expense
                selection = DB.CATEGORY_FOR_EXPENSE + "="+ "1";
                break;
            case 2:///Borrow
                selection = DB.CATEGORY_FOR_BORROW + "="+ "1";
                break;
            case 3://Lent
                selection = DB.CATEGORY_FOR_LENT + "="+ "1";
                break;

        }
        mSelection = selection;
        getLoaderManager().restartLoader(LODER_ID,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id == LODER_ID) {
            Log.d("Split", "onCreateLoader notifications loader");
            return new CursorLoader(this, DB.CATEGORY_TABLE_URI,
                    DB.CATEGORY_TABLE_PROJECTION, mSelection, null,
                    "data DESC");
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == LODER_ID) {
            adapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == LODER_ID) {
            adapter.swapCursor(null);
        }
    }

}
