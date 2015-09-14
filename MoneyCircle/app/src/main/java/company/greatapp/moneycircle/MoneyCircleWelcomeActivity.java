package company.greatapp.moneycircle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import company.greatapp.moneycircle.manager.PreferenceManager;


public class MoneyCircleWelcomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_circle_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               goAhead();
            }
        },2000);
    }

    private void goAhead() {
        PreferenceManager pf = new PreferenceManager(this);
        if(pf.isRegistrationProcessCompleted()) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);;
            finish();
        } else {
            Intent i = new Intent(this,RegisterActivity.class);
            startActivity(i);;
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_money_circle_welcome, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
