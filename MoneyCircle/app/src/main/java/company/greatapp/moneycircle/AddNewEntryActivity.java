package company.greatapp.moneycircle;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import company.greatapp.moneycircle.constants.C;

public class AddNewEntryActivity extends ActionBarActivity {

    private int entryType;


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
        setTextColor();
        setButtonColor();
        Intent intent = getIntent();
        int entryType = intent.getIntExtra(C.ENTRY_TYPE,C.ENTRY_TYPE_INCOME);
        this.entryType = entryType;
        switch(entryType) {
            case C.ENTRY_TYPE_INCOME:
                createIncomeLayout();
                break;
            case C.ENTRY_TYPE_EXPENSE:
                createExpenseLayout();
                break;
            case C.ENTRY_TYPE_BORROW:
                createBorrowLayout();
                break;
            case C.ENTRY_TYPE_LENDED:
                createLendedLayout();
                break;
        }

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
            Toast.makeText(this,"ENTRY SAVED",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
