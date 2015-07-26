package company.greatapp.moneycircle.split;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Circle;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Participant;
import company.greatapp.moneycircle.tools.DatePickerFragment;
import company.greatapp.moneycircle.view.TagItemView;

public class SplitToolActivity extends ActionBarActivity implements DatePickerFragment.DateSetter {

    public static final int SPLIT_AMOUNT_REQUEST = 33;
    private ArrayList<Participant> participants = new ArrayList<Participant>();
    private ArrayList<Contact> memberContacts = new ArrayList<Contact>();
    private Circle memberCircle;
    //TODO: ucomment it for circle
    //private CircleManager circleManager;
    private ContactManager contactManager;

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
    private CheckBox cb_include_me;
    private boolean isUserIncluded;
    private Button b_distribution_type;
    private boolean isEqually;
    private TextView tv_distribution_details;
    private TextView tv_total_members;

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
        cb_include_me = (CheckBox)findViewById(R.id.cb_new_include_me);
        isUserIncluded = cb_include_me.isChecked();
        et_new_amount = (EditText)findViewById(R.id.et_new_amount);
        et_new_item = (EditText)findViewById(R.id.et_new_item);
        et_new_note = (EditText)findViewById(R.id.et_new_note);
        tv_total_members = (TextView)findViewById(R.id.tv_new_total_members);
        tv_distribution_details = (TextView)findViewById(R.id.tv_new_distribution_details);
        b_distribution_type = (Button)findViewById(R.id.b_new_distribution_type);
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
        cb_include_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isUserIncluded = isChecked;
                refreshParticipants();
            }
        });
        isEqually = true;
        b_distribution_type.setText("EQUALLY");
        b_distribution_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDistributionTypeButton(v);
            }
        });

        contactManager = new ContactManager(this);
        //TODO: ucomment it for circle
        //circleManager = new CircleManager(this);
    }

    private void handleDistributionTypeButton(View v) {
        String amount = getAmountFromBox();
        if(amount.equals("0") || participants.size() < 2 ){
            //TODO: Put all the conditons before starting SetSplitAMountActivity
            return;
        }
        Intent intent = new Intent(this,SetSplitAmountActivity.class);
        float total_amount = Float.parseFloat(amount);
        intent.putExtra("total_amount", total_amount);
        intent.putExtra("isEqually", isEqually);
        intent.putParcelableArrayListExtra("participants", participants);
        startActivityForResult(intent, SPLIT_AMOUNT_REQUEST);
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
       if (requestCode == C.TAG_CONTACTS
               || requestCode == C.TAG_REGISTERED_CONTACTS
               || requestCode == C.TAG_CIRCLES) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
                addParticipants(C.TAG_CONTACTS, returnedResult);
                addTagViews(requestCode);
            }
        } else if(requestCode == SPLIT_AMOUNT_REQUEST) {
           if (resultCode == RESULT_OK) {
               participants = data.getParcelableArrayListExtra("participants");
               isEqually = data.getBooleanExtra("isEqually", false);
               b_distribution_type.setText(isEqually ? "EQUALLY" : "UNEQUALLY");
               setDistributedAmountView();
           }
       }
    }

    private void setDistributedAmountView() {
        int total = participants.size();
        String details = "";
        for (Participant p : participants) {
            details =  details +"["+ p.memberName+":"+p.amount+"]";
        }
        tv_total_members.setText("TOTAL:"+total);
        tv_distribution_details.setText(details);
    }

    private void addParticipants(int tag, ArrayList<String> returnedResult) {
        Log.d("SPLIT", "addParticipants");
        switch(tag){
            case C.TAG_CONTACTS:

                memberContacts.clear();
                for (String uid : returnedResult) {
                    Log.d("SPLIT","querying from contact manager");
                    memberContacts.add((Contact)contactManager.getItemFromListByUID(uid));
                }
                break;
            case C.TAG_CIRCLES:
                //TODO: ucomment it for circle
//                memberCircle = null;
//                String uid = returnedResult.get(0);
//                    memberCircle = circleManager.getCircleByUID(uid);
//                break;
        }
        participants.clear();
        if(isUserIncluded) {
            participants.add(new Participant("You","user"));
        }
        for (Contact c : memberContacts) {
            participants.add(new Participant(c));
        }

        //TODO: ucomment it for circle
//        for (Contact c :memberCircle.getMemberList()) {
//            participants.add(new Participant(c));
//        }

        setDividedAmountEqually();
    }

    private void refreshParticipants() {
        Log.d("SPLIT", "refreshParticipants");

        //use existed values to set the participants

        participants.clear();
        if(isUserIncluded) {
            participants.add(new Participant("You","user"));
        }
        for (Contact c : memberContacts) {
            participants.add(new Participant(c));
        }

        //TODO: ucomment it for circle
//        for (Contact c :memberCircle.getMemberList()) {
//            participants.add(new Participant(c));
//        }

        setDividedAmountEqually();
    }

    private void setDividedAmountEqually() {
        int memberCount = participants.size();
        if(memberCount == 0) return;
        float total = Float.parseFloat(getAmountFromBox());
        float amount = total / memberCount;
        float percent = 100/ memberCount;
        float share = 1;//assuming total share equals to number of participants
        for (int i = 0; i< participants.size();i++) {
            participants.get(i).amount = amount;
            participants.get(i).percent = percent;
            participants.get(i).share = share;

        }
        setDistributedAmountView();
    }

    private String getAmountFromBox() {
        String value = et_new_amount.getText().toString();
        if(TextUtils.isEmpty(value)){
            value = "0";
        }else if(!TextUtils.isDigitsOnly(value)) {
            value = "0";
            Toast.makeText(this,"WRONG FORMAT",Toast.LENGTH_SHORT).show();
        }
        return value;
    }

    private void addTagViews(int type) {
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

        for (Contact c : memberContacts) {
            ll.addView(new TagItemView(this,type, c.getContactName()));
            out = out+c.getContactName()+",";
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

    private ArrayList<Participant> getParticipants() {
        return participants;
    }
}
