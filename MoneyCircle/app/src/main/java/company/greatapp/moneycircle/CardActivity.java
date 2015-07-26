package company.greatapp.moneycircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.adapters.DiaryRecyclerViewAdapter;
import company.greatapp.moneycircle.model.CardViewObject;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Prateek on 19-07-2015.
 */
public class CardActivity extends AppCompatActivity {

    private TabHost mTabHost;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String LOG_TAG = "CardActivity";

    private int[] mCardToShow = {Model.MODEL_TYPE_INCOME, Model.MODEL_TYPE_EXPENSE, Model.MODEL_TYPE_BORROW, Model.MODEL_TYPE_LENT, Model.MODEL_TYPE_SPLIT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card);

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);


        // Setup the tabContent contatiner to the tabhost
        mTabHost.setup();

        TabHost.TabSpec tabSpec1 = mTabHost.newTabSpec("Diary Tab");
        tabSpec1.setIndicator("Diary");
        tabSpec1.setContent(R.id.tabcontent1);
        mTabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = mTabHost.newTabSpec("Trendz Tab");
        tabSpec2.setIndicator("Trendz");
        tabSpec2.setContent(R.id.tabcontent2);
        mTabHost.addTab(tabSpec2);

        // Setting the content of Diary Tab
        mRecyclerView = (RecyclerView)findViewById(R.id.tabcontent1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiaryRecyclerViewAdapter(getDataSet());
        ((DiaryRecyclerViewAdapter)mAdapter).setOnItemClickListener(new DiaryRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + (position + 1));
                Toast.makeText(CardActivity.this, "Clicked on Item " + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    private ArrayList<CardViewObject> getDataSet() {
        ArrayList diaryCardList = new ArrayList<CardViewObject>();
        for (int card : mCardToShow) {
            CardViewObject obj = new CardViewObject(card, "100000", "Last Transaction");
            diaryCardList.add(obj);
        }
        return diaryCardList;
    }
}
