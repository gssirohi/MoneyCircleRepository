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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Contact;


public class SetSplitAmountActivity extends ActionBarActivity {

    public static final String ACTION_AMOUNT_REFRESH = "ACTION_AMOUNT_REFRESH";
    private ListView listview;
    private ArrayList<Participant> list;
    private TextView tv_divided_amount;
    private ToggleButton tb_equally;
    private int total_amount;
    private boolean isUserIncluded;
    private SetSplitAmountAdapter adapter;

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
        list = getParticipants();
        adapter = new SetSplitAmountAdapter(this, list);
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
        for (Participant p : list) {
            Log.d("split", "name : " + p.member.getContactName());
            Log.d("split", "name : " + p.amount);
        }
    }

    public class Participant {
        Contact member;
        int amount;

        public Participant(String name) {
            member = new Contact(name);
        }
    }

    private BroadcastReceiver mAmountRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("receiver", "Amount Refresh Intent action received");
            tb_equally.setChecked(false);
            refreshAmount();
        }
    };

    private void refreshAmount() {
        tv_divided_amount.setText("" + getDividedAmount());
        printParticipants();
    }

    private int getDividedAmount() {
        int amount = 0;
        for (Participant p : list) {
            Log.d("split", "name : " + p.member.getContactName() + " amount : " + p.amount);
            amount = amount + p.amount;
        }
        return amount;
    }

    private void setDividedAmountEqually() {
        int amount = total_amount / list.size();
        for (int i = 0; i<list.size();i++) {
            list.get(i).amount = amount;
        }
        adapter = null;
        adapter = new SetSplitAmountAdapter(this, list);
        listview.setAdapter(adapter);
        refreshAmount();
    }
}