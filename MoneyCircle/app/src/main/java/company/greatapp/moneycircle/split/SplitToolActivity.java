package company.greatapp.moneycircle.split;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.manager.CircleManager;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.manager.ExpenseManager;
import company.greatapp.moneycircle.manager.LentManager;
import company.greatapp.moneycircle.manager.SplitManager;
import company.greatapp.moneycircle.model.Circle;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Participant;
import company.greatapp.moneycircle.model.Split;
import company.greatapp.moneycircle.tools.DatePickerFragment;
import company.greatapp.moneycircle.tools.DateUtils;
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.view.TagItemView;

public class SplitToolActivity extends ActionBarActivity implements DatePickerFragment.DateSetter {

    public static final int SPLIT_AMOUNT_REQUEST = 33;
    private ArrayList<Participant> participants = new ArrayList<Participant>();
    private ArrayList<Contact> memberContacts = new ArrayList<Contact>();
    private Circle memberCircle;
    
    
    private CircleManager circleManager;
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
    private LinearLayout ll_new_circles;
    private CheckBox cb_include_me;
    private boolean isUserIncluded;
    private Button b_distribution_type;
    private boolean isEqually;
    private TextView tv_distribution_details;
    private TextView tv_total_members;
    private String mCategory;
    private String mDateString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_tool);

        ll_new_contacts = (LinearLayout)findViewById(R.id.ll_new_contacts);
        ll_new_circles = (LinearLayout)findViewById(R.id.ll_new_circles);
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
        circleManager = new CircleManager(this);
    }
    private void handleSplitAction() {
        if(!validateData()) return;
        
        //TITLE
        String title = et_new_item.getText().toString();
        //AMOUNT
        String amountString = et_new_amount.getText().toString();
        float amount = Float.parseFloat(amountString);
        //DATE
        String dateString = mDateString;
        //DUE DATE
        
        //DESC
        String desc = et_new_note.getText().toString();
        //category
          //mCategory has the value

        //total PARTICIPANTS
        ArrayList<Contact> allMembers = new ArrayList<Contact>();
        for(Participant p : participants) {
            Contact c = (Contact)contactManager.getItemFromListByUID(p.memberUID);
            allMembers.add(c);
        }

        JSONArray jArrayParticipants = GreatJSON.getJsonArrayForContactList(allMembers);
        String jsonStringParticipants = jArrayParticipants.toString();
        
        //CONTACTS
        JSONArray jArrayContacts = GreatJSON.getJsonArrayForContactList(memberContacts);
        String jsonStringContacts = jArrayContacts.toString();
        
        //CIRCLE
        JSONObject jsonCircle = GreatJSON.getJsonObjectForCircle(memberCircle);
        String jsonStringCircle = (jsonCircle != null)?jsonCircle.toString():"";
        
        //EXPENSE
        Expense expense = insertExpense();

        String jsonStringExpense = "";
        if(expense != null) {
          //change UID as in DB instance of this instance
          expense.setUID(expense.getUID().replaceAll("NEW","DB"));

            JSONObject obj = GreatJSON.getJsonObjectForExpense(expense);
            if(obj != null) {
                jsonStringExpense = obj.toString();
            }else{
                Log.d("SPLIT","expense json is null");
            }
        }
        //TOTAL LENTS
        ArrayList<Lent> lents = insertLents();
        for(Lent l : lents){
            if (l != null) {
                //change UID as in DB instance of this instance
                l.setUID(l.getUID().replaceAll("NEW","DB"));
            }
        }
        JSONArray jArrayLents = GreatJSON.getJsonArrayForLentList(lents);
        String jsonStringLents = jArrayLents.toString();


        Split split = new Split();

        split.setTitle(title);
        split.setAmount(amount);
        split.setDescription(desc);
        split.setCategory(mCategory);
        split.setDateString(dateString);

        split.setLinkedParticipantsJson(jsonStringParticipants);
        split.setLinkedParticipants(allMembers);

        split.setTotalParticipants(allMembers.size());

        split.setLinkedContactsJson(jsonStringContacts);
        split.setLinkedContacts(memberContacts);

        split.setLinkedCircleJson(jsonStringCircle);
        split.setLinkedCircle(memberCircle);

        split.setLinkedExpenseJson(jsonStringExpense);
        split.setLinkedExpense(expense);//update it with DB expense (DB version will have this split info)

        split.setLinkedLentsJson(jsonStringLents);
        split.setLinkedLents(lents);//update it with DB expense (DB version will have this split info)

        Uri uri = split.insertItemInDB(this);

        //change UID as in DB instance of this instance
        split.setUID(split.getUID().replaceAll("NEW", "DB"));

        JSONObject objS = GreatJSON.getJsonObjectForSplit(split);
        String jsonStringSplit = "";
        if(objS != null) {
            jsonStringSplit = objS.toString();
        }

        //for updation original DB item is needed(dbId)
        if(expense != null) {
            ExpenseManager em = new ExpenseManager(this);
            Expense dbExpense = (Expense) em.getItemFromListByUID(expense.getUID());
            dbExpense.setLinkedSplitJson(jsonStringSplit);
            dbExpense.updateItemInDb(this);
//            split.setLinkedExpense(dbExpense);
        }


        LentManager lm = new LentManager(this);
        ArrayList<Lent> dbLents = new ArrayList<Lent>();
        for(Lent l : lents){
            if (l != null) {
                Lent dbLent = (Lent)lm.getItemFromListByUID(l.getUID());
                dbLent.setLinkedSplitJson(jsonStringSplit);
                dbLent.updateItemInDb(this);
                dbLents.add(dbLent);
            }
        }
//        split.setLinkedLents(dbLents);
//        SplitManager sm = new SplitManager(this);
//        Split dbSplit = (Split)sm.getItemFromListByUID(split.getUID());
//        split.setDbId(dbSplit.getDbId());//update db id from db instance
//        split.updateItemInDb(this);

        finish();

    }

    private ArrayList<Lent> insertLents() {
        //title
        String title = et_new_item.getText().toString();
        //category
           //mCategory has the value
        //desc
            String desc = et_new_note.getText().toString();
        //date
        String dateString = mDateString;
        //is split
        boolean isSplit = true;
        // linked split
        // not yet created it will be updated after split creation in db

        ArrayList<Lent> lents = new ArrayList<Lent>();
        for(Participant p : participants) {
            if(p.memberUID.equals(C.USER_UNIQUE_ID))
                continue;
            //linked contact
            Contact linkedMember = (Contact)contactManager.getItemFromListByUID(p.memberUID);
            //amount
            float amount = p.amount;

            Lent lent = new Lent();
            lent.setTitle(title);
            lent.setAmount(amount);
            lent.setDescription(desc);
            lent.setDateString(dateString);
            lent.setIsLinkedWithSplit(true);
            lent.setCategory(mCategory);
            lent.setLinkedContact(linkedMember);
            lent.insertItemInDB(this);
            lents.add(lent);
        }
        return lents;
    }

    private float getParticipantAmountByUID(String uid) {
        if(participants != null) {
            for (Participant p : participants) {
                if(p.memberUID.equals(uid)) {
                    return p.amount;
                }
            }
        }
        return 0;//when not found
    }
    private Expense insertExpense() {
        if(!isUserIncluded) return null;

        //title
        String title = et_new_item.getText().toString();
        //amount
        String amount =""+ getParticipantAmountByUID(contactManager.getUser().getUID());
        //category
            //mCategory has the value
        //desc
        String desc = et_new_note.getText().toString();
        //date
        String dateString = mDateString;
        //is split
        boolean isSplit = true;
        // linked split
         // not yet created it will be updated after split creation in db

        Expense expense = new Expense();
        expense.setTitle(title);
        expense.setAmount(Float.parseFloat(amount));
        expense.setCategory(mCategory);
        expense.setDescription(desc);
        expense.setDateString(dateString);
        expense.setIsLinkedWithSplit(true);
        //expense.setLinkedSplitJson();
        Uri uri = expense.insertItemInDB(this);
        if(uri != null)
            return expense;
        else
            return expense;//return null;
    }

    private boolean validateData() {
        String amount = getAmountFromBox();
        CharSequence title = et_new_item.getText();
        if(amount.equals("0") || participants.size() < 2
                || TextUtils.isEmpty(title)){
            //TODO: Put all the conditons before starting SetSplitAMountActivity
            Toast.makeText(this,"field empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        i.putExtra(C.CHOOSER_MODEL, Model.MODEL_TYPE_CONTACT);
        i.putExtra(C.CHOOSER_CHOICE_MODE, ListView.CHOICE_MODE_MULTIPLE);
        startActivityForResult(i, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("split", "onActivityResult : requestCode:" + requestCode + "  resultCode:" + resultCode);
       if (requestCode == C.TAG_CONTACTS
               || requestCode == C.TAG_REGISTERED_CONTACTS ) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
                addParticipants(C.TAG_CONTACTS, returnedResult);
                addTagViews(requestCode);
            }
        } else if (requestCode == C.TAG_CIRCLES) {
           ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
           addParticipants(C.TAG_CIRCLES, returnedResult);
           addTagViews(requestCode);
        } else if(requestCode == SPLIT_AMOUNT_REQUEST) {
           if (resultCode == RESULT_OK) {
               participants = data.getParcelableArrayListExtra("participants");
               isEqually = data.getBooleanExtra("isEqually", false);
               b_distribution_type.setText(isEqually ? "EQUALLY" : "UNEQUALLY");
               setDistributedAmountView();
           }
       } else if(requestCode == C.TAG_CATEGORIES) {
           if (resultCode == RESULT_OK) {
               ArrayList<String> returnedResult = data.getStringArrayListExtra("uids");
               addCategory(returnedResult.get(0));
           }
       }
    }

    private void addCategory(String uid){
        CategoryManager cm = new CategoryManager(this, Model.MODEL_TYPE_SPLIT);
        String title = cm.getItemFromListByUID(uid).getTitle();
        mCategory = uid;   // TODO This value has to be properly set
        b_new_category.setText(title);
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
                memberCircle = null;
                String uid = returnedResult.get(0);
                memberCircle = (Circle)circleManager.getItemFromListByUID(uid);
                break;
        }
        participants.clear();
        if(isUserIncluded) {
            participants.add(new Participant(contactManager.getUser()));
        }
        for (Contact c : memberContacts) {
            participants.add(new Participant(c));
        }

        if(memberCircle != null)
        for (Contact c : memberCircle.getContacts()) {
            participants.add(new Participant(c));
        }

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

        if(memberCircle != null)
        for (Contact c :memberCircle.getContacts()) {
            participants.add(new Participant(c));
        }

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
                ll = ll_new_circles;
                break;
            case C.TAG_CONTACTS:
                ll = ll_new_contacts;
                break;
        }
        if(ll == null) return;

        int count = ll.getChildCount();
        ll.removeAllViews();
        TagItemView.RemoveTagListener listener = new TagItemView.RemoveTagListener() {
            @Override
            public void OnTagRemoved(TagItemView view) {
                handleTagRemoved(view);
            }
        };
        if(type == C.TAG_CONTACTS || type == C.TAG_REGISTERED_CONTACTS) {
            for (Contact c : memberContacts) {
                TagItemView tagView = new TagItemView(this, ll, c, true);
                tagView.setRemoveTagListener(listener);
                ll.addView(tagView);
                out = out + c.getContactName() + ",";
            }
        } else if (type == C.TAG_CIRCLES) {
                if(memberCircle == null) return;
                TagItemView tagView = new TagItemView(this, ll, memberCircle, true);
                tagView.setRemoveTagListener(listener);
                ll.addView(tagView);
                out = out + memberCircle.getTitle() + ",";
        }
    }

    private void handleTagRemoved(TagItemView view) {
        Model model = view.getModel();
        if(model == null) return;
        if(Model.MODEL_TYPE_CONTACT == model.getModelType()) {
            memberContacts.remove((Contact)model);
            refreshParticipants();
        } else if (Model.MODEL_TYPE_CIRCLE == model.getModelType()) {
            memberCircle = null;
            refreshParticipants();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_split) {
            handleSplitAction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        b_new_date.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear, year));
        mDateString = DateUtils.getDateString(year, monthOfYear, dayOfMonth);
    }

    private ArrayList<Participant> getParticipants() {
        return participants;
    }


}
