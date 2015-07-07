package company.greatapp.moneycircle.split;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Contact;


public class SetSplitAmountActivity extends ActionBarActivity {

    public static final String ACTION_AMOUNT_REFRESH = "ACTION_AMOUNT_REFRESH";
    private static final int EQUAL = 0;
    private static final int UNEQUAL = 1;
    private static final int PERCENT = 2;
    private static final int SHARES = 3;
    private ListView listview;
    private ArrayList<Participant> participantsList;
    private TextView tv_divided_amount;
    private ToggleButton tb_equally;
    private boolean isUserIncluded;
    private SetSplitAmountAdapter adapter;
    private Spinner spinner;

    private float perShareValue;
    private float total_shares;
    private float total_amount;

    private int mDistributionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_split_amount);

        //get below values from the intent
        total_amount = 5000;
        isUserIncluded = true;

        //String[] memberList = {"member1","member2","member3","member4","member5","member6","member7","member8","member9"};
        tv_divided_amount = (TextView) findViewById(R.id.tv_divide_amount);
        tb_equally = (ToggleButton) findViewById(R.id.tb_equally);
        TextView tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);
        listview = (ListView) findViewById(R.id.listView2);
        spinner = (Spinner)findViewById(R.id.sp_split_amount_mode);
        String[] distribution_modes = new String[] {"equally","unequally","percent","shares"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, distribution_modes));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleSpinnerItemClick(parent,view,position,id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        participantsList = getParticipants();
        adapter = new SetSplitAmountAdapter(this, participantsList);
        listview.setAdapter(adapter);
        listview.setFocusable(true);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetInvalidated();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Participant participant = ((SetSplitAmountRowItemView) view).getParticipant();
                Log.d("split", "Name : " + participant.member.getContactName());
                Log.d("split", "Amount : " + participant.amount);
                Toast.makeText(SetSplitAmountActivity.this, "" + participant.member.getContactName() + " : " +
                        participant.amount, Toast.LENGTH_SHORT).show();
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mAmountRefreshReceiver,
                new IntentFilter(ACTION_AMOUNT_REFRESH));
    }

    private void handleSpinnerItemClick(AdapterView<?> parent, View view, int position, long id) {
        int mode = position;
        if(mDistributionMode == mode){

        } else {
            clearDividedAmount();
        }
        mDistributionMode = mode;
    }

    private ArrayList<Participant> getParticipants() {
        ArrayList<Participant> list = new ArrayList<Participant>();
        if (isUserIncluded)
            list.add(new Participant("YOU"));
        list.add(new Participant("SuperMan"));
        list.add(new Participant("IronMan"));
        list.add(new Participant("BatMan"));
        list.add(new Participant("Hulk"));
        list.add(new Participant("Thor"));
        list.add(new Participant("Caption America"));
        list.add(new Participant("SpiderMan"));
        return list;
    }

    public void toggleclick(View v) {
        if (tb_equally.isChecked()) {
            setDividedAmountEqually();
        } else {
            // Toast.makeText(TestActivity.this, "OFF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_split_amount, menu);
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
            printParticipants();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void printParticipants() {
        for (Participant p : participantsList) {
            Log.d("split", "name : " + p.member.getContactName());
            Log.d("split", "amount : " + p.amount);
            Log.d("split", "percent : " + p.percent);
            Log.d("split", "share : " + p.share);
        }
    }

    public class Participant {
        Contact member;
        float editableValue;
        float amount;
        float percent;
        float share;

        public Participant(String name) {
            member = new Contact(name);
        }
    }

    private BroadcastReceiver mAmountRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("receiver", "Amount Refresh Intent action received");
            tb_equally.setChecked(false);
            calculateParticipantsData();
        }
    };

    private void refreshAmount() {
        tv_divided_amount.setText("" + getDividedAmount());
        printParticipants();
    }

    private float getDividedAmount() {
        float amount = 0;
        for (Participant p : participantsList) {
            Log.d("split", "name : " + p.member.getContactName() + " amount : " + p.amount);
            amount = amount + p.amount;
        }
        return amount;
    }

    private void setDividedAmountEqually() {
        float amount = total_amount / participantsList.size();
        for (int i = 0; i< participantsList.size();i++) {
            participantsList.get(i).amount = amount;
        }
        updateDataFromAmount();
        adapter = null;
        adapter = new SetSplitAmountAdapter(this, participantsList);
        listview.setAdapter(adapter);
        refreshAmount();
    }
    private void clearDividedAmount() {
        for (int i = 0; i< participantsList.size();i++) {
            participantsList.get(i).editableValue = 0;
            participantsList.get(i).amount = 0;
            participantsList.get(i).percent = 0;
            participantsList.get(i).share = 0;
        }
        adapter = null;
        adapter = new SetSplitAmountAdapter(this, participantsList);
        listview.setAdapter(adapter);
        refreshAmount();
    }

    private void updateLatestValueFromEditable() {
        for (int i = 0; i< participantsList.size();i++) {
              switch(mDistributionMode) {
                  case EQUAL:
                      participantsList.get(i).amount = participantsList.get(i).editableValue;
                      break;
                  case UNEQUAL:
                      participantsList.get(i).amount = participantsList.get(i).editableValue;
                      break;
                  case PERCENT:
                      participantsList.get(i).percent = participantsList.get(i).editableValue;
                      break;
                  case SHARES:
                      participantsList.get(i).share = participantsList.get(i).editableValue;
                      break;
              }
        }
    }
    private void calculateParticipantsData() {
        updateLatestValueFromEditable();
        int total;
        switch(mDistributionMode) {
            case EQUAL:
                updateDataFromAmount();
                break;
            case UNEQUAL:
                updateDataFromAmount();
                break;
            case PERCENT:
                updateDataFromPercent();
                break;
            case SHARES:
                updateDataFromShares();
                break;
        }

        adapter = null;
        adapter = new SetSplitAmountAdapter(this, participantsList);
        listview.setAdapter(adapter);
        refreshAmount();
    }


    private float getTotalShares(){
        total_shares = 0;
        for (int i = 0; i< participantsList.size();i++) {
            total_shares = total_shares + participantsList.get(i).share;
        }
        return total_shares;
    }
    private void updateDataFromShares() {
        perShareValue = total_amount/getTotalShares();
        for (int i = 0; i< participantsList.size();i++) {
            float totalShare = participantsList.get(i).share;
            participantsList.get(i).amount = totalShare * perShareValue;
            participantsList.get(i).percent = (totalShare * perShareValue * 100)/total_amount;
        }
    }

    private void updateDataFromPercent() {
        total_shares = 100;//default
        perShareValue = total_amount/total_shares;
        for (int i = 0; i< participantsList.size();i++) {
            float percent = participantsList.get(i).percent;
            participantsList.get(i).amount = (total_amount * percent)/100;
            participantsList.get(i).share = total_amount/perShareValue;
        }
    }

    private void updateDataFromAmount() {
        total_shares = 100;//default
        perShareValue = total_amount/total_shares;
        for (int i = 0; i< participantsList.size();i++) {
            float percent = participantsList.get(i).percent;
            float amount = participantsList.get(i).amount;
            participantsList.get(i).share = amount/perShareValue;
            participantsList.get(i).percent = (amount * 100)/total_amount;
        }
    }

}