package company.greatapp.moneycircle.split;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.DatePickerFragment;
import company.greatapp.moneycircle.DatePickerFragment.DateSetter;
import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.view.TagItemView;

public class SplitToolActivity extends ActionBarActivity implements DateSetter{

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
    private Button b_new_members_add;
    private Button b_new_circles_add;
    private Button b_new_date;
    private LinearLayout ll_new_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_tool);

        ll_new_contacts = (LinearLayout)findViewById(R.id.ll_new_contacts);
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
        b_new_members_add = (Button)findViewById(R.id.b_new_member_add);
        b_new_circles_add = (Button)findViewById(R.id.b_new_add_circles);
        b_new_split = (Button)findViewById(R.id.b_new_split);
        b_new_date = (Button)findViewById(R.id.b_new_date);
        b_new_members_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.TAG_CONTACTS);
            }
        });
        b_new_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.TAG_CATEGORIES);
            }
        });
        b_new_circles_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.TAG_CIRCLES);
            }
        });
        b_new_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(this);
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_split_tool, menu);
        return true;
    }
    private void startItemSelection(int requestCode){
        Intent i = new Intent(this, ChooserActivity.class);
        i.putExtra(C.CHOOSER_REQUEST,requestCode);
        startActivityForResult(i, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("split", "onActivityResult : requestCode:" + requestCode + "  resultCode:" + resultCode);
       //if (requestCode == ChooserActivity.TAG_REGISTERED_CONTACTS) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
                addTagViews(requestCode, returnedResult);
            }
        //}
    }

    private void addTagViews(int type, ArrayList<String> returnedResult) {
        /*TODO returned Result will be uids finally not the String
        so we need to get names/titles of the item from manager classes using these uids
        */
        String out = "";
        LinearLayout ll = null;
        switch(type) {
            case C.TAG_REGISTERED_CONTACTS:
             //   ll = ll_contacts;
                break;
            case C.TAG_CATEGORIES:

                break;
            case C.TAG_CIRCLES:
              //  ll = ll_circle;
                break;
            case C.TAG_CONTACTS:
                ll = ll_new_contacts;
                break;
        }
        if(ll == null) return;

        int count = ll.getChildCount();
        ll.removeAllViews();

        for (String s : returnedResult) {
            ll.addView(new TagItemView(this,type, s));
            out = out+s+",";
        }
        Toast.makeText(this,out,Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_split) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        b_new_date.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear, year));
    }
}
