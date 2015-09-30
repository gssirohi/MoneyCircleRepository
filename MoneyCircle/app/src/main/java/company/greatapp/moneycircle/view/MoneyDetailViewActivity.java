package company.greatapp.moneycircle.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.BaseModelManager;
import company.greatapp.moneycircle.manager.BorrowManager;
import company.greatapp.moneycircle.manager.ExpenseManager;
import company.greatapp.moneycircle.manager.IncomeManager;
import company.greatapp.moneycircle.manager.LentManager;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;
import company.greatapp.moneycircle.view.TopSegmentItemView;

import company.greatapp.moneycircle.R;

public class MoneyDetailViewActivity extends AppCompatActivity {

    private int mModelType;
    private int mEntryType;
    private TextView tv_modelName;
    private TextView tv_amount;
    private TextView tv_title;
    private TopSegmentItemView tsiv_category;
    private TopSegmentItemView tsiv_date;
    private TopSegmentItemView tsiv_member;
    private TopSegmentItemView tsiv_dueDate;
    private TextView tv_note;
    private TextView tv_frequent;
    private TextView tv_partOfSplit;
    private TextView tv_duedate_text;
    private TextView tv_member_add_from;

    private BaseModelManager modelManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mModelType = intent.getIntExtra(C.MODEL_TYPE, Model.MODEL_TYPE_INCOME);
        mEntryType = intent.getIntExtra(C.ENTRY_TYPE, C.ENTRY_TYPE_DISPLAY);

        String modelUid = intent.getStringExtra(C.MODEL_UID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Tools.getModelDarkColor(this, mModelType));
        }

        setTheme(Tools.getModelThemeResId(mModelType));

        setContentView(R.layout.activity_money_detail_view);

        tv_modelName = (TextView)findViewById(R.id.tv_model_name);
        tv_amount = (TextView)findViewById(R.id.tv_new_amount);
        tv_title = (TextView)findViewById(R.id.tv_new_item_title);
        tsiv_category = (TopSegmentItemView)findViewById(R.id.tsiv_new_category);
        tsiv_date = (TopSegmentItemView)findViewById(R.id.tsiv_new_date);
        tv_member_add_from = (TextView)findViewById(R.id.tv_due_date_text);
        tsiv_member = (TopSegmentItemView)findViewById(R.id.tsiv_new_member_add);
        tv_duedate_text = (TextView)findViewById(R.id.tv_due_date_text);
        tsiv_dueDate = (TopSegmentItemView)findViewById(R.id.tsiv_new_due_date);
        tv_note = (TextView)findViewById(R.id.tv_new_note);
        tv_frequent = (TextView)findViewById(R.id.tv_add_in_frequent);
        tv_partOfSplit = (TextView)findViewById(R.id.tv_expense_part_of_split);

        invalidateLayout();

        setValue(modelUid);

    }

    private void invalidateLayout(){
        tv_modelName.setText(Tools.getModelName(mModelType));

        switch(mModelType) {
            case Model.MODEL_TYPE_INCOME:
                createIncomeLayout();
                modelManager = new IncomeManager(this);
                break;
            case Model.MODEL_TYPE_EXPENSE:
//                createExpenseLayout();
                modelManager = new ExpenseManager(this);
                break;
            case Model.MODEL_TYPE_BORROW:
//                createBorrowLayout();
                modelManager = new BorrowManager(this);
                break;
            case Model.MODEL_TYPE_LENT:
//                createLendedLayout();
                modelManager = new LentManager(this);
                break;
        }
    }

    private void setValue(String uid) {
        if (modelManager != null) {
            Model model = modelManager.getHeavyItemFromListByUID(uid);
            tv_title.setText(model.getTitle());
            tsiv_category.setItemTitle("Select Category");
            tv_amount.setText(Tools.floatString(model.getAmount()));
            tsiv_date.setItemTitle(model.getDateString());
            String description = ((Income)model).getDescription();
            if (!TextUtils.isEmpty(description)) {
                tv_note.setVisibility(View.VISIBLE);
                tv_note.setText(description);
            }
        }
    }

    /*private void createBorrowLayout() {
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
    }*/

    private void createExpenseLayout() {

        tsiv_category.setItemTitle("SELECT CATEGORY");

        tv_member_add_from.setVisibility(View.GONE);
        tv_duedate_text.setVisibility(View.GONE);
        tsiv_member.setVisibility(View.GONE);
        tsiv_dueDate.setVisibility(View.GONE);    //NOT HIDDEN
        tv_partOfSplit.setVisibility(View.GONE);     // HIDDEN
        tv_frequent.setVisibility(View.GONE);

        //tv_new_title.setText("N E W   E X P E N S E");
//        tv_new_before_type.setText("I have a new ");
//        tv_new_type.setText("EXPENSE ");
//        tv_new_after_type.setText("of worth");
//        tv_new_category_text.setText("of type ");
//        tv_new_item_title_text.setText("for ");                       //<---
//        tv_new_member_add.setText("FROM/TO");                      //HIDDEN
//        tv_new_note_text.setText("more details:");
//
//        et_new_amount.setHint("00.00");
//        et_new_item.setHint("e.g: paying credit card bill");
//        et_new_note.setHint("This is a transaction message entry");
//
//        tsiv_new_category.setItemTitle("SELECT CATEGORY");
//        b_new_split.setText("SPLIT THIS WITH OTHER MEMBERS");     //NOT HIDDEN
//        tsiv_new_member_add.setItemTitle("INCLUDE MEMBER");              //HIDDEN
//
//        tv_new_member_add.setVisibility(View.GONE);
//        tv_new_due_date_text.setVisibility(View.GONE);    //NOT HIDDEN
//        b_new_split.setVisibility(View.VISIBLE);     //NOT HIDDEN
//        tsiv_new_member_add.setVisibility(View.GONE);              //HIDDEN
//        tsiv_new_due_date.setVisibility(View.GONE);     //HIDDEN
    }

    private void createIncomeLayout() {

        tsiv_category.setItemTitle("SELECT CATEGORY");

        tv_member_add_from.setVisibility(View.GONE);
        tv_duedate_text.setVisibility(View.GONE);
        tsiv_member.setVisibility(View.GONE);
        tsiv_dueDate.setVisibility(View.GONE);    //NOT HIDDEN
        tv_partOfSplit.setVisibility(View.GONE);     // HIDDEN
        tv_frequent.setVisibility(View.GONE);

    }

}
