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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.BaseModelManager;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.dialogs.DatePickerFragment;
import company.greatapp.moneycircle.tools.DateUtils;
import company.greatapp.moneycircle.tools.Tools;
import company.greatapp.moneycircle.dialogs.ContactInfoDialog;
import company.greatapp.moneycircle.view.TagItemView;
import company.greatapp.moneycircle.view.TopSegmentItemView;

public class AddNewEntryActivity extends ActionBarActivity implements DatePickerFragment.DateSetter,TagItemView.TagItemViewCallBacks {

    private int mModelType;     // Model Type
    private int mEntryType;     // Entry type means either it is a display or input


    private TextView tv_new_before_type;
    private TextView tv_new_type;
    private TextView tv_new_after_type;
    private TextView tv_new_item_title_text;
    private TextView tv_new_member_add;
    private TextView tv_new_note_text;

    private EditText et_new_amount;
    private EditText et_new_item;
    private EditText et_new_note;

    private Button b_new_split;


    private Contact mMember;
    private TopSegmentItemView tsiv_new_category;
    private TextView tv_new_category_text;
    private TextView tv_new_date_text;
    private TopSegmentItemView tsiv_new_date;
    private TopSegmentItemView tsiv_new_member_add;
    private TextView tv_new_due_date_text;
    private TopSegmentItemView tsiv_new_due_date;

    private String mDateString;
    private String mCategory;
    private String mDueDateString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_new_entry);
        setContentView(R.layout.new_entry_layout);
        //tv_new_title = (TextView)findViewById(R.id.tv_new_title);
        tv_new_before_type = (TextView)findViewById(R.id.tv_new_before_type);
        tv_new_type = (TextView)findViewById(R.id.tv_new_type);
        tv_new_after_type = (TextView)findViewById(R.id.tv_new_after_type);

        et_new_amount = (EditText)findViewById(R.id.et_new_amount);

        tv_new_item_title_text = (TextView)findViewById(R.id.tv_new_item_title_text);
        et_new_item = (EditText)findViewById(R.id.et_new_item_title);

        tv_new_category_text = (TextView)findViewById(R.id.tv_new_category_text);
        tsiv_new_category = (TopSegmentItemView)findViewById(R.id.tsiv_new_category);

        tv_new_date_text = (TextView)findViewById(R.id.tv_new_date_text);
        tsiv_new_date = (TopSegmentItemView)findViewById(R.id.tsiv_new_date);

        tv_new_member_add = (TextView)findViewById(R.id.tv_new_member_add);
        tsiv_new_member_add = (TopSegmentItemView)findViewById(R.id.tsiv_new_member_add);

        tv_new_due_date_text = (TextView)findViewById(R.id.tv_new_due_date_text);
        tsiv_new_due_date = (TopSegmentItemView)findViewById(R.id.tsiv_new_due_date);

        tv_new_note_text = (TextView)findViewById(R.id.tv_new_note_text);
        et_new_note = (EditText)findViewById(R.id.et_new_note);

        b_new_split = (Button)findViewById(R.id.b_new_split);
      ///----------------------------------------------------------------------------//

        tsiv_new_member_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.TAG_CONTACTS, ListView.CHOICE_MODE_SINGLE);
            }
        });

        tsiv_new_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.TAG_CATEGORIES, ListView.CHOICE_MODE_SINGLE);
            }
        });

        tsiv_new_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        tsiv_new_date.setModeSingleText();
        tsiv_new_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        tsiv_new_due_date.setModeSingleText();

        Intent intent = getIntent();
        int entryType = intent.getIntExtra(C.ENTRY_TYPE,C.ENTRY_TYPE_INPUT);
        this.mEntryType = entryType;

        mCategory = C.CATEGORY_NONE_UID;
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

        setDefaultCategory();
        setDefaultDate();
        setDefaultDueDate();
       // setTextColor();
       // setButtonColor();
    }

    private void setButtonColor() {
        int back = Tools.getModelColorResId(this,mModelType);
       // int back = getResources().getColor(resId);
       // int back = getResources().getColor(R.color.app_light);
        b_new_split.setBackgroundColor(back);
       // b_new_member_add.setBackgroundColor(back);
       // b_new_date.setBackgroundColor(back);
       // b_due_date.setBackgroundColor(back);
    }

    private void setTextColor() {
        int color = Tools.getModelColorResId(this,mModelType);
        //int color = getResources().getColor(resId);
        //int color = getResources().getColor(R.color.app_darkest);
        //tv_new_title.setTextColor(color);
        tv_new_before_type.setTextColor(color);
        tv_new_type.setTextColor(color);
        tv_new_after_type.setTextColor(color);
        tv_new_category_text.setTextColor(color);
        tv_new_item_title_text.setTextColor(color);
        //tv_new_due_date.setTextColor(color);
        tv_new_member_add.setTextColor(color);
        tv_new_note_text.setTextColor(color);
    }

    private void createBorrowLayout() {
        //tv_new_title.setText("N E W   B O R R O W");
        tv_new_before_type.setText("I have ");
        tv_new_type.setText("BORROWED ");
        tv_new_after_type.setText("an amount of");
        tv_new_category_text.setText("of type ");
        tv_new_item_title_text.setText("for ");                               //<---CHANGED
        tv_new_member_add.setText("from ");                      // CHANGED and NOT HIDDEN
        tv_new_note_text.setText("more details:");

        et_new_amount.setHint("00.00");
        et_new_item.setHint("e.g: Purchasing new sun glasses");
        et_new_note.setHint("I would like to include later.");

        tsiv_new_category.setItemTitle("SELECT CATEGORY");
        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //HIDDEN
        tsiv_new_member_add.setItemTitle("INCLUDE MEMBER");              //NOT HIDDEN

        tv_new_member_add.setVisibility(View.VISIBLE);    //NOT HIDDEN
        tv_new_due_date_text.setVisibility(View.VISIBLE);    //NOT HIDDEN
        b_new_split.setVisibility(View.GONE);             //HIDDEN
        tsiv_new_member_add.setVisibility(View.VISIBLE);     //NOT HIDDEN
        tsiv_new_due_date.setVisibility(View.VISIBLE);     //NOT HIDDEN
    }

    private void createLendedLayout() {
        //tv_new_title.setText("N E W  L E N D I N G");
        tv_new_before_type.setText("I have ");
        tv_new_type.setText("LENDED ");
        tv_new_after_type.setText("an amount of");
        tv_new_category_text.setText("of type ");
        tv_new_item_title_text.setText("for ");                               //<---CHANGED
        tv_new_member_add.setText("TO");                      // CHANGED and NOT HIDDEN
        tv_new_note_text.setText("more details:");

        et_new_amount.setHint("00.00");
        et_new_item.setHint("e.g: purchasing the new phone");
        et_new_note.setHint("I would like to include later");

        tsiv_new_category.setItemTitle("SELECT CATEGORY");
        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //HIDDEN
        tsiv_new_member_add.setItemTitle("INCLUDE MEMBER");              //NOT HIDDEN

        tv_new_member_add.setVisibility(View.VISIBLE);    //NOT HIDDEN
        tv_new_due_date_text.setVisibility(View.VISIBLE);    //NOT HIDDEN
        b_new_split.setVisibility(View.GONE);             //HIDDEN
        tsiv_new_member_add.setVisibility(View.VISIBLE);     //NOT HIDDEN
        tsiv_new_due_date.setVisibility(View.VISIBLE);     //NOT HIDDEN
    }

    private void createExpenseLayout() {
        //tv_new_title.setText("N E W   E X P E N S E");
        tv_new_before_type.setText("I have a new ");
        tv_new_type.setText("EXPENSE ");
        tv_new_after_type.setText("of worth");
        tv_new_category_text.setText("of type ");
        tv_new_item_title_text.setText("for ");                       //<---
        tv_new_member_add.setText("FROM/TO");                      //HIDDEN
        tv_new_note_text.setText("more details:");

        et_new_amount.setHint("00.00");
        et_new_item.setHint("e.g: paying credit card bill");
        et_new_note.setHint("This is a transaction message entry");

        tsiv_new_category.setItemTitle("SELECT CATEGORY");
        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //NOT HIDDEN
        tsiv_new_member_add.setItemTitle("INCLUDE MEMBER");              //HIDDEN

        tv_new_member_add.setVisibility(View.GONE);
        tv_new_due_date_text.setVisibility(View.GONE);    //NOT HIDDEN
        b_new_split.setVisibility(View.VISIBLE);     //NOT HIDDEN
        tsiv_new_member_add.setVisibility(View.GONE);              //HIDDEN
        tsiv_new_due_date.setVisibility(View.GONE);     //HIDDEN
    }

    private void createIncomeLayout() {
        //tv_new_title.setText("N E W  I N C O M E");
        tv_new_before_type.setText("I have a new ");
        tv_new_type.setText("INCOME ");
        tv_new_after_type.setText("of worth");
        tv_new_category_text.setText("of type ");
        tv_new_item_title_text.setText("from ");
        tv_new_member_add.setText("INCLUDE MEMBER");                      //HIDDEN
        tv_new_note_text.setText("more details:");

        et_new_amount.setHint("00.00");
        et_new_item.setHint("e.g: Salary from Google.Inc");
        et_new_note.setHint("I would like to include later");

        tsiv_new_category.setItemTitle("SELECT CATEGORY");
        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //HIDDEN
        tsiv_new_member_add.setItemTitle("FROM");              //HIDDEN

        tv_new_member_add.setVisibility(View.GONE);
        tv_new_due_date_text.setVisibility(View.GONE);    //NOT HIDDEN
        b_new_split.setVisibility(View.GONE);     // HIDDEN
        tsiv_new_due_date.setVisibility(View.GONE);              //HIDDEN
        tsiv_new_member_add.setVisibility(View.GONE);              //HIDDEN
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
            if (mMember == null) return false;
        }

        String title = et_new_item.getText().toString();
        if (TextUtils.isEmpty(title)) return false;

        else return true;
    }

    private void saveData() {

        BaseModelManager manager = null;
        String description = null;
        float amount;
        switch (mModelType) {
            case Model.MODEL_TYPE_INCOME:
                Income income = new Income();
                income.setDateString(mDateString);
                income.setTitle(et_new_item.getText().toString());
                income.setCategory(mCategory);
                amount = Float.parseFloat(et_new_amount.getText().toString());
                income.setAmount(amount);
                description = et_new_note.getText().toString();
                if (!TextUtils.isEmpty(description)) {
                    income.setDescription(description);
                }

                income.insertItemInDB(this);

                Toast.makeText(this, "Income ENTRY SAVED", Toast.LENGTH_SHORT).show();
                Tools.sendMoneyTransactionBroadCast(this, income, Model.MODEL_TYPE_INCOME);
                break;
            case Model.MODEL_TYPE_EXPENSE:
                Expense expense = new Expense();
                expense.setDateString(mDateString);
                expense.setTitle(et_new_item.getText().toString());
                expense.setCategory(mCategory);
                amount = Float.parseFloat(et_new_amount.getText().toString());
                expense.setAmount(amount);
                description = et_new_note.getText().toString();
                if (!TextUtils.isEmpty(description)) {
                    expense.setDescription(description);
                }
                // TODO Split with other member entry needs to be included.

                expense.insertItemInDB(this);
                Category cat = (Category) Tools.getDbInstance(this, mCategory, Model.MODEL_TYPE_CATEGORY);
                float spent = cat.getSpentAmountOnThis();
                cat.setSpentAmountOnThis(spent + amount);
                cat.updateItemInDb(this);

                Toast.makeText(this, "Expense ENTRY SAVED", Toast.LENGTH_SHORT).show();
                Tools.sendMoneyTransactionBroadCast(this, expense, Model.MODEL_TYPE_EXPENSE);
                break;
            case Model.MODEL_TYPE_BORROW:
                Borrow borrow = new Borrow();
                borrow.setDateString(mDateString);
                borrow.setTitle(et_new_item.getText().toString());
                borrow.setCategory(mCategory);
                amount = Float.parseFloat(et_new_amount.getText().toString());
                borrow.setAmount(amount);
                borrow.setLinkedContact(mMember);
                description = et_new_note.getText().toString();
                if (!TextUtils.isEmpty(description)) {
                    borrow.setDescription(description);
                }

                // TODO Include member field need to be handled
                //borrow.setLinkedContact(b_new_member_add.getText());

                borrow.insertItemInDB(this);

                if(!mMember.getUID().equals(C.USER_UNIQUE_ID)) {
                    float borrowAmount = mMember.getBorrowedAmountfromThis();
                    mMember.setBorrowedAmountfromThis(borrowAmount + amount);
                    mMember.updateItemInDb(this);//update contact's borrow amount
                }
                Toast.makeText(this, "Borrow ENTRY SAVED", Toast.LENGTH_SHORT).show();
                Tools.sendMoneyTransactionBroadCast(this, borrow, Model.MODEL_TYPE_BORROW);
                break;
            case Model.MODEL_TYPE_LENT:
                Lent lent = new Lent();
                lent.setDateString(mDateString);
                lent.setTitle(et_new_item.getText().toString());
                lent.setCategory(mCategory);
                lent.setLinkedContact(mMember);
                amount = Float.parseFloat(et_new_amount.getText().toString());
                lent.setAmount(amount);
                description = et_new_note.getText().toString();
                if (!TextUtils.isEmpty(description)) {
                    lent.setDescription(description);
                }

                // TODO Include member field need to be handled
//                lent.setLinkedContact(b_new_member_add.getText());

                lent.printModelData();
                lent.insertItemInDB(this);

                if(!mMember.getUID().equals(C.USER_UNIQUE_ID)) {
                    float lentAmount = mMember.getLentAmountToThis();
                    mMember.setLentAmountToThis(lentAmount + amount);
                    mMember.updateItemInDb(this);//update contact's lent amount
                }
                Toast.makeText(this, "Lent ENTRY SAVED", Toast.LENGTH_SHORT).show();
                Tools.sendMoneyTransactionBroadCast(this,lent,Model.MODEL_TYPE_LENT);
                break;
        }

        finish();
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(this);
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setDefaultDate() {
        mDateString = DateUtils.getCurrentDate();
        tsiv_new_date.setItemTitle(mDateString);
    }

    public void setDefaultDueDate() {
        mDueDateString = DateUtils.getCurrentWeekLastDate();
        tsiv_new_due_date.setItemTitle(mDateString);
    }

    public void setDefaultCategory() {
        mCategory = C.CATEGORY_NONE_UID;
    }
    @Override
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        mDateString = DateUtils.getDateString(year, monthOfYear, dayOfMonth);
        tsiv_new_date.setItemTitle(mDateString);
        tsiv_new_due_date.setItemTitle(DateUtils.getNextWeekLastDate(mDateString));
//        Toast.makeText(this,"DATE:"+ mDateString,Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("split", "onActivityResult : requestCode:" + requestCode + "  resultCode:" + resultCode);
        if (requestCode == C.TAG_CATEGORIES) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
                addCategory(returnedResult.get(0));
            }
        } else if (requestCode == C.TAG_CONTACTS) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
                addMember(returnedResult.get(0));
            }
        }
    }

    private void addMember(String s) {
        if(TextUtils.isEmpty(s))return;
        ContactManager cm = new ContactManager(this);
        Contact member = (Contact)cm.getHeavyItemFromListByUID(s);
//        TagItemView tagView = new TagItemView(this,f_member,member,true);
//        TagItemView.RemoveTagListener listener = new TagItemView.RemoveTagListener() {
//            @Override
//            public void OnTagRemoved(TagItemView view) {
//                handleMemberRemoved(view);
//            }
//        };
//        tagView.setRemoveTagListener(listener);
//        f_member.addView(tagView);
//        b_new_member_add.setVisibility(View.GONE);
//        f_member.setVisibility(View.VISIBLE);
        tsiv_new_member_add.setModel(member,Model.MODEL_TYPE_CONTACT);
        mMember = member;
    }

    private void handleMemberRemoved(TagItemView view) {
//        b_new_member_add.setVisibility(View.VISIBLE);
//        f_member.setVisibility(View.GONE);
//        mMember = null;
    }

    private void addCategory(String uid){
        CategoryManager cm = new CategoryManager(this, mModelType);
        Category category = (Category)cm.getHeavyItemFromListByUID(uid);
        String title = category.getTitle();
        mCategory = uid;   // TODO This value has to be properly set
        tsiv_new_category.setModel(category,Model.MODEL_TYPE_CATEGORY);
        //tv_new_category_text.setText(title);
    }

    private void startItemSelection(int requestCode, int mode){
        Intent i = new Intent(this, ChooserActivity.class);
        i.putExtra(C.CHOOSER_REQUEST,requestCode);
        i.putExtra(C.CHOOSER_CHOICE_MODE,mode);
        i.putExtra(C.CHOOSER_MODEL, mModelType);
        startActivityForResult(i, requestCode);
    }

    @Override
    public void onContactTagClicked(Model model) {
        ContactInfoDialog dialog = new ContactInfoDialog(this,(Contact)model);
        dialog.show();
    }
}
