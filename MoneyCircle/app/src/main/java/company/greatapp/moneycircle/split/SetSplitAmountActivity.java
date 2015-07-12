package company.greatapp.moneycircle.split;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Participant;


public class SetSplitAmountActivity extends ActionBarActivity {

    public static final String ACTION_AMOUNT_REFRESH = "ACTION_AMOUNT_REFRESH";
    private static final int AMOUNT = 0;
    private static final int PERCENT = 1;
    private static final int SHARES = 2;
    private ListView listview;
    private TextView tv_divided_amount;
    private ToggleButton tb_equally;
    private SetSplitAmountAdapter adapter;
    private Spinner spinner;

    private float perShareValue;
    private float total_shares;
    private float total_amount;
    private float divided_amount;
    private float remaining_amount;

    private int mDistributionMode;
    private TextView tv_total_shares;
    private ArrayList<Participant> participantsList = new ArrayList<Participant>();
    private Button b_done;
    private boolean isEqually;
    private TextView tv_remaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_split_amount);
        Intent intent = getIntent();
        //get below values from the intent
        total_amount = intent.getFloatExtra("total_amount", 0);
        participantsList = intent.getParcelableArrayListExtra("participants");
        isEqually = intent.getBooleanExtra("isEqually", false);
        total_shares = participantsList.size();
        perShareValue = total_amount/total_shares;

        tv_remaining = (TextView) findViewById(R.id.tv_remaining);
        tv_divided_amount = (TextView) findViewById(R.id.tv_divide_amount);
        tv_total_shares = (TextView) findViewById(R.id.tv_total_shares);
        tb_equally = (ToggleButton) findViewById(R.id.tb_equally);
        TextView tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);
        tv_total_amount.setText(floatstr(total_amount));
        listview = (ListView) findViewById(R.id.listView2);
        b_done = (Button)findViewById(R.id.b_set_split_amount_done);
        spinner = (Spinner)findViewById(R.id.sp_split_amount_mode);
        String[] distribution_modes = new String[] {"amount","percent","shares"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, distribution_modes));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleSpinnerItemClick(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new SetSplitAmountAdapter(this, participantsList,mDistributionMode);
        listview.setAdapter(adapter);
        listview.setFocusable(true);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetInvalidated();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Participant participant = ((SetSplitAmountRowItemView) view).getParticipant();
                Log.d("split", "Name : " + participant.memberName);
                Log.d("split", "Amount : " + participant.amount);
                Toast.makeText(SetSplitAmountActivity.this, "" + participant.memberName + " : " +
                        participant.amount, Toast.LENGTH_SHORT).show();
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mAmountRefreshReceiver,
                new IntentFilter(ACTION_AMOUNT_REFRESH));

        tv_total_shares.setText("" + floatstr(total_shares) + " (" + floatstr(perShareValue) + " per share)");

        b_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDone();
            }
        });

        tb_equally.setChecked(isEqually);
        if(isEqually) {
            setDividedAmountEqually();
            tv_remaining.setVisibility(View.GONE);
            b_done.setVisibility(View.VISIBLE);
        } else {
            tv_remaining.setVisibility(View.VISIBLE);
            b_done.setVisibility(View.GONE);
        }


    }

    private void handleDone() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("isEqually",isEqually);
        resultIntent.putParcelableArrayListExtra("participants",participantsList);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void handleSpinnerItemClick(AdapterView<?> parent, View view, int position, long id) {
        int mode = position;
        if(mDistributionMode == mode){

        } else {
            clearDividedAmount();
        }
        mDistributionMode = mode;
    }



    public void toggleclick(View v) {
        if (tb_equally.isChecked()) {
            isEqually = true;
            setDividedAmountEqually();
        } else {
            isEqually = false;
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
            Log.d("split", "name : " + p.memberName);
            Log.d("split", "amount : " + p.amount);
            Log.d("split", "percent : " + p.percent);
            Log.d("split", "share : " + p.share);
        }
    }



    private BroadcastReceiver mAmountRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("receiver", "Amount Refresh Intent action received");
            tb_equally.setChecked(false);
            isEqually = false;
            calculateParticipantsData();
        }
    };

    private void refreshAmount() {
        divided_amount = getDividedAmount();
        remaining_amount = total_amount - divided_amount;
        tv_divided_amount.setText(floatstr(divided_amount));
        if((remaining_amount < 1 && remaining_amount > -1)) {
            tv_remaining.setVisibility(View.GONE);
            b_done.setVisibility(View.VISIBLE);
        }

        else if(remaining_amount < 0) {
            tv_remaining.setText(floatstr(remaining_amount) + " exceeded");
            tv_remaining.setVisibility(View.VISIBLE);
            b_done.setVisibility(View.GONE);
        }
        else if(remaining_amount > 0) {
            tv_remaining.setText(floatstr(remaining_amount) + " remaining");
            tv_remaining.setVisibility(View.VISIBLE);
            b_done.setVisibility(View.GONE);
        }
        tv_total_shares.setText("" + floatstr(total_shares)+" ("+floatstr(perShareValue)+" per share)");
        printParticipants();
    }

    private float getDividedAmount() {
        float amount = 0;
        for (Participant p : participantsList) {
            Log.d("split", "name : " + p.memberName + " amount : " + p.amount);
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
        adapter = new SetSplitAmountAdapter(this, participantsList,mDistributionMode);
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
        adapter = new SetSplitAmountAdapter(this, participantsList,mDistributionMode);
        listview.setAdapter(adapter);
        refreshAmount();
    }

    private void updateLatestValueFromEditable() {
        for (int i = 0; i< participantsList.size();i++) {
              switch(mDistributionMode) {
                  case AMOUNT:
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
            case AMOUNT:
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
        adapter = new SetSplitAmountAdapter(this, participantsList,mDistributionMode);
        listview.setAdapter(adapter);
        refreshAmount();
    }


    private float getTotalShares(){
       float total = 0;
        for (int i = 0; i< participantsList.size();i++) {
            total = total + participantsList.get(i).share;
        }
        total_shares = total;
        return total;
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
       // total_shares = 100;//default
        perShareValue = total_amount/total_shares;
        for (int i = 0; i< participantsList.size();i++) {
            float percent = participantsList.get(i).percent;
            float amount = (total_amount * percent)/100;
            participantsList.get(i).amount = amount;
            participantsList.get(i).share = amount/perShareValue;
        }
    }

    private void updateDataFromAmount() {
        //total_shares = 100;//default
        perShareValue = total_amount/total_shares;
        for (int i = 0; i< participantsList.size();i++) {
            float percent = participantsList.get(i).percent;
            float amount = participantsList.get(i).amount;
            participantsList.get(i).share = amount/perShareValue;
            participantsList.get(i).percent = (amount * 100)/total_amount;
        }
    }

    private String floatstr(float value) {
        return new DecimalFormat("##.##").format(value);
    }

}