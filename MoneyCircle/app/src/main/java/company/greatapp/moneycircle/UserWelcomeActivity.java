package company.greatapp.moneycircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import company.greatapp.moneycircle.model.User;


public class UserWelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_welcome_screen);
        TextView tv_name = (TextView)findViewById(R.id.tv_welcome_name);
        ImageView iv_profile_pic = (ImageView)findViewById(R.id.ivProfilePicId);
        Button b_continue = (Button)findViewById(R.id.b_welcome_continue);
        User user = new User(this);
        tv_name.setText(user.getName());
        iv_profile_pic.setImageResource(user.getAvator());
        b_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleContinue();
            }
        });
    }

    private void handleContinue() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onStartButton(View v) {
        Toast.makeText(getApplicationContext(),"Start Button Pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
