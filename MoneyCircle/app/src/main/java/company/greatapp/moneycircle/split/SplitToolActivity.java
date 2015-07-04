package company.greatapp.moneycircle.split;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    private TextView tv_date;
    private LinearLayout ll_contacts;
    private Button b_contacts;
    private Button b_circle;
    private Button b_category;
    private LinearLayout ll_category;
    private LinearLayout ll_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_tool);
        ll_contacts = (LinearLayout)findViewById(R.id.ll_split_contacts);
        ll_category = (LinearLayout)findViewById(R.id.ll_split_categories);
        ll_circle = (LinearLayout)findViewById(R.id.ll_split_circle);
        b_contacts = (Button)findViewById(R.id.b_split_contacts);
        b_category = (Button)findViewById(R.id.b_split_category);
        b_circle = (Button)findViewById(R.id.b_split_circle);
        b_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.REQUEST_CODE_CONTACTS);
            }
        });
        b_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.REQUEST_CODE_CATEGORIES);
            }
        });
        b_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemSelection(C.REQUEST_CODE_CIRCLES);
            }
        });
        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new View.OnClickListener() {
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
//        if (requestCode == ChooserActivity.REQUEST_CODE_REGISTERED_CONTACTS) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
                addTagViews(requestCode, returnedResult);
            }
        //}
    }

    private void addTagViews(int type, ArrayList<String> returnedResult) {
        /*TODO returnedResult will be uids finally not the String
        so we need to get names/titles of the item from manager classes using these uids
        */
        String out = "";
        LinearLayout ll = null;
        Button b = null;
        switch(type) {
            case C.REQUEST_CODE_REGISTERED_CONTACTS:
                ll = ll_contacts;
                b = b_contacts;
                break;
            case C.REQUEST_CODE_CATEGORIES:
                ll = ll_category;
                b = b_category;
                break;
            case C.REQUEST_CODE_CIRCLES:
                ll = ll_circle;
                b = b_circle;
                break;
            case C.REQUEST_CODE_CONTACTS:
                ll = ll_contacts;
                b = b_contacts;
                break;
        }
        if(ll == null || b == null) return;

        int count = ll.getChildCount();
        for ( int i = 0; i < count ;i++) {
            ll.removeAllViews();
        }
        for (String s : returnedResult) {
            ll.addView(new TagItemView(this, TagItemView.TYPE_REGISTERED_CONTACT, s));
            out = out+s+",";
        }
        ll.addView(b);
        Toast.makeText(this,out,Toast.LENGTH_SHORT).show();
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
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        tv_date.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear, year));
    }
}
