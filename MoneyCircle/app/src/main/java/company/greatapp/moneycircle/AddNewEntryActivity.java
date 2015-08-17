package company.greatapp.moneycircle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.BaseModelManager;
import company.greatapp.moneycircle.manager.BorrowManager;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.manager.ExpenseManager;
import company.greatapp.moneycircle.manager.IncomeManager;
import company.greatapp.moneycircle.manager.LentManager;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.DatePickerFragment;
import company.greatapp.moneycircle.tools.DateUtils;

public class AddNewEntryActivity extends ActionBarActivity implements DatePickerFragment.DateSetter {

    private int mModelType;     // Model Type
    private int mEntryType;     // Entry type means either it is a display or input


    private TextView tv_new_title;
    private TextView tv_new_before_type;
    private TextView tv_new_type;
    private TextView tv_new_after_type;
    private TextView tv_new_currency;
    private TextView tv_new_category;
    private TextView tv_new_item;
    private TextView tv_new_member_add;
    private TextView tv_new_note;

    private EditText et_new_amount;
    private EditText et_new_item;
    private EditText et_new_note;

    private Button b_new_category;
    private Button b_new_split;
    private Button b_new_member_add;
    private Button b_new_date;
    private String mDateString;
    private String mCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_entry);
        tv_new_title = (TextView)findViewById(R.id.tv_new_title);
        tv_new_before_type = (TextView)findViewById(R.id.tv_new_before_type);
        tv_new_type = (TextView)findViewById(R.id.tv_new_type);
        tv_new_after_type = (TextView)findViewById(R.id.tv_new_after_type);
        tv_new_currency = (TextView)findViewById(R.id.tv_new_currency);
        tv_new_category = (TextView)findViewById(R.id.tv_new_category);
        tv_new_item = (TextView)findViewById(R.id.tv_new_item);
        tv_new_member_add = (TextView)findViewById(R.id.tv_new_member_add);
        tv_new_note = (TextView)findViewById(R.id.tv_new_note);

        et_new_amount = (EditText)findViewById(R.id.et_new_amount);
        et_new_item = (EditText)findViewById(R.id.et_new_item);
        et_new_note = (EditText)findViewById(R.id.et_new_note);

        b_new_category = (Button)findViewById(R.id.b_new_category);
        b_new_member_add = (Button)findViewById(R.id.b_new_member_add);
        b_new_split = (Button)findViewById(R.id.b_new_split);
        b_new_date = (Button)findViewById(R.id.b_new_date);
        b_new_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.TAG_CATEGORIES, ListView.CHOICE_MODE_SINGLE);
            }
        });
        b_new_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        setTextColor();
        setButtonColor();
        Intent intent = getIntent();
        int entryType = intent.getIntExtra(C.ENTRY_TYPE,C.ENTRY_TYPE_INPUT);
        this.mEntryType = entryType;

        // TODO Don't start this activity if entry type is not ENTRY_TYPE_INPUT

        mModelType = intent.getIntExtra(C.MODEL_TYPE, Model.MODEL_TYPE_INCOME);

        switch(mModelType) {
            case Model.MODEL_TYPE_INCOME:
                createIncomeLayout();
                break;
            case Model.MODEL_TYPE_EXPENSE:
                createExpenseLayout();
                break;
            case Model.MODEL_TYPE_BORROW:
                createBorrowLayout();
                break;
            case Model.MODEL_TYPE_LENT:
                createLendedLayout();
                break;
        }

        setCurrentDate();
    }

    private void setButtonColor() {
        int back = getResources().getColor(R.color.material_deep_teal_200);
        b_new_category.setBackgroundColor(back);
        b_new_split.setBackgroundColor(back);
        b_new_member_add.setBackgroundColor(back);
        b_new_date.setBackgroundColor(back);
    }

    private void setTextColor() {
        int color = getResources().getColor(R.color.material_deep_teal_500);
        tv_new_title.setTextColor(color);
        tv_new_before_type.setTextColor(color);
        tv_new_type.setTextColor(color);
        tv_new_after_type.setTextColor(color);
        tv_new_currency.setTextColor(color);
        tv_new_category.setTextColor(color);
        tv_new_item.setTextColor(color);
        tv_new_member_add.setTextColor(color);
        tv_new_note.setTextColor(color);
    }

    private void createBorrowLayout() {
        tv_new_title.setText("N E W   B O R R O W");
        tv_new_before_type.setText("I have ");
        tv_new_type.setText("BORROWED ");
        tv_new_after_type.setText("an amount of");
        tv_new_currency.setText("Rs. ");
        tv_new_category.setText("of type ");
        tv_new_item.setText("for ");                               //<---CHANGED
        tv_new_member_add.setText("from ");                      // CHANGED and NOT HIDDEN
        tv_new_note.setText("and I would like to add below note:");

        et_new_amount.setHint("00.00");
        et_new_item.setHint("e.g: Purchasing new sun glasses");
        et_new_note.setHint("I don't have any note currently.\n I would like to include later.");

        b_new_category.setText("SELECT CATEGORY");
        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //HIDDEN
        b_new_member_add.setText("INCLUDE MEMBER");              //NOT HIDDEN

        tv_new_member_add.setVisibility(View.VISIBLE);    //NOT HIDDEN
        b_new_split.setVisibility(View.GONE);             //HIDDEN
        b_new_member_add.setVisibility(View.VISIBLE);     //NOT HIDDEN
    }


    private void createLendedLayout() {
        tv_new_title.setText("N E W  L E N D I N G");
        tv_new_before_type.setText("I have ");
        tv_new_type.setText("LENDED ");
        tv_new_after_type.setText("an amount of");
        tv_new_currency.setText("Rs. ");
        tv_new_category.setText("of type ");
        tv_new_item.setText("for ");                               //<---CHANGED
        tv_new_member_add.setText("TO");                      // CHANGED and NOT HIDDEN
        tv_new_note.setText("and I would like to add below note:");

        et_new_amount.setHint("00.00");
        et_new_item.setHint("e.g: purchasing the new phone");
        et_new_note.setHint("I don't have any note currently.\n I would like to include later");

        b_new_category.setText("SELECT CATEGORY");
        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //HIDDEN
        b_new_member_add.setText("INCLUDE MEMBER");              //NOT HIDDEN

        tv_new_member_add.setVisibility(View.VISIBLE);    //NOT HIDDEN
        b_new_split.setVisibility(View.GONE);             //HIDDEN
        b_new_member_add.setVisibility(View.VISIBLE);     //NOT HIDDEN
    }

    private void createExpenseLayout() {
        tv_new_title.setText("N E W   E X P E N S E");
        tv_new_before_type.setText("I have a new ");
        tv_new_type.setText("EXPENSE ");
        tv_new_after_type.setText("of worth");
        tv_new_currency.setText("Rs. ");
        tv_new_category.setText("of type ");
        tv_new_item.setText("for ");                       //<---
        tv_new_member_add.setText("FROM/TO");                      //HIDDEN
        tv_new_note.setText("and I would like to add below note:");

        et_new_amount.setHint("00.00");
        et_new_item.setHint("e.g: paying credit card bill");
        et_new_note.setHint("I don't have any note currently.\n I would like to include later");

        b_new_category.setText("SELECT CATEGORY");
        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //NOT HIDDEN
        b_new_member_add.setText("INCLUDE MEMBER");              //HIDDEN

        tv_new_member_add.setVisibility(View.GONE);
        b_new_split.setVisibility(View.VISIBLE);     //NOT HIDDEN
        b_new_member_add.setVisibility(View.GONE);              //HIDDEN
    }

    private void createIncomeLayout() {
        tv_new_title.setText("N E W  I N C O M E");
        tv_new_before_type.setText("I have a new ");
        tv_new_type.setText("INCOME ");
        tv_new_after_type.setText("of worth");
        tv_new_currency.setText("Rs. ");
        tv_new_category.setText("of type ");
        tv_new_item.setText("from the source");
        tv_new_member_add.setText("FROM/TO");                      //HIDDEN
        tv_new_note.setText("and I would like to add below note:");

        et_new_amount.setHint("00.00");
        et_new_item.setHint("e.g: Salary from Google.Inc");
        et_new_note.setHint("I don't have any note currently.\n I would like to include later");

        b_new_category.setText("SELECT CATEGORY");
        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //HIDDEN
        b_new_member_add.setText("INCLUDE MEMBER");              //HIDDEN

        tv_new_member_add.setVisibility(View.GONE);   //HIDDEN
        b_new_split.setVisibility(View.GONE);     // HIDDEN
        b_new_member_add.setVisibility(View.GONE);              //HIDDEN
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_entry) {
            if(validateData()) {
                saveData();
            } else {
                Toast.makeText(this, "Field Empty", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateData() {
        if (TextUtils.isEmpty(mDateString)) return false;
        String amount = et_new_amount.getText().toString();
        if (TextUtils.isEmpty(amount)) return false;

        if (mModelType == Model.MODEL_TYPE_BORROW || mModelType == Model.MODEL_TYPE_LENT) {
            if (b_new_member_add.getText().equals("INCLUDE MEMBER")) return false;
        }

        String title = et_new_item.getText().toString();
        if (TextUtils.isEmpty(title)) return false;

        else return true;
    }

    private void saveData() {

        BaseModelManager manager = null;
        String description = null;
        switch (mModelType) {
            case Model.MODEL_TYPE_INCOME:
                Income income = new Income();
                income.setDateString(mDateString);
                income.setTitle(et_new_item.getText().toString());
                income.setCategory(mCategory);
                income.setAmount(Float.parseFloat(et_new_amount.getText().toString()));
                description = et_new_note.getText().toString();
                if (!TextUtils.isEmpty(description)) {
                    income.setDescription(description);
                }

                manager = new IncomeManager(this);
                manager.insertItemInDB(income);

                Toast.makeText(this, "Income ENTRY SAVED", Toast.LENGTH_SHORT).show();
                break;
            case Model.MODEL_TYPE_EXPENSE:
                Expense expense = new Expense();
                expense.setDateString(mDateString);
                expense.setTitle(et_new_item.getText().toString());
                expense.setCategory(mCategory);
                expense.setAmount(Float.parseFloat(et_new_amount.getText().toString()));
                description = et_new_note.getText().toString();
                if (!TextUtils.isEmpty(description)) {
                    expense.setDescription(description);
                }
                // TODO Split with other member entry needs to be included.

                manager = new ExpenseManager(this);
                manager.insertItemInDB(expense);

                Toast.makeText(this, "Expense ENTRY SAVED", Toast.LENGTH_SHORT).show();
                break;
            case Model.MODEL_TYPE_BORROW:
                Borrow borrow = new Borrow();
                borrow.setDateString(mDateString);
                borrow.setTitle(et_new_item.getText().toString());
                borrow.setCategory(mCategory);
                borrow.setAmount(Float.parseFloat(et_new_amount.getText().toString()));
                description = et_new_note.getText().toString();
                if (!TextUtils.isEmpty(description)) {
                    borrow.setDescription(description);
                }

                // TODO Include member field need to be handled
                //borrow.setLinkedContact(b_new_member_add.getText());

                manager = new BorrowManager(this);
                manager.insertItemInDB(borrow);

                Toast.makeText(this, "Borrow ENTRY SAVED", Toast.LENGTH_SHORT).show();
                break;
            case Model.MODEL_TYPE_LENT:
                Lent lent = new Lent();
                lent.setDateString(mDateString);
                lent.setTitle(et_new_item.getText().toString());
                lent.setCategory(mCategory);
                lent.setAmount(Float.parseFloat(et_new_amount.getText().toString()));
                description = et_new_note.getText().toString();
                if (!TextUtils.isEmpty(description)) {
                    lent.setDescription(description);
                }

                // TODO Include member field need to be handled
//                lent.setLinkedContact(b_new_member_add.getText());

                manager = new LentManager(this);
                manager.insertItemInDB(lent);

                Toast.makeText(this, "Lent ENTRY SAVED", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(this);
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setCurrentDate() {
        mDateString = DateUtils.getCurrentDate();
        b_new_date.setText(mDateString);
    }

    @Override
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        b_new_date.setText(String.format("%d/%d/%d", year, monthOfYear+1, dayOfMonth));
        mDateString = DateUtils.getDateString(year,monthOfYear,dayOfMonth);
        Toast.makeText(this,"DATE:"+ mDateString,Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("split", "onActivityResult : requestCode:" + requestCode + "  resultCode:" + resultCode);
        if (requestCode == C.TAG_CATEGORIES) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
                addCategory(returnedResult.get(0));
            }
        }
    }

    private void addCategory(String uid){
        CategoryManager cm = new CategoryManager(this, mModelType);
        String title = cm.getItemFromListByUID(uid).getTitle();
        mCategory = uid;   // TODO This value has to be properly set
        b_new_category.setText(title);
    }

    private void startItemSelection(int requestCode, int mode){
        Intent i = new Intent(this, ChooserActivity.class);
        i.putExtra(C.CHOOSER_REQUEST,requestCode);
        i.putExtra(C.CHOOSER_CHOICE_MODE,mode);
        i.putExtra(C.CHOOSER_MODEL, mModelType);
        startActivityForResult(i, requestCode);
    }
}
