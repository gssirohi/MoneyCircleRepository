package company.greatapp.moneycircle;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.Tools;

public class ViewAndEditProfileActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_email;
    private TextView tv_gender;
    private ImageView iv_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_edit_profle);

        mToolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        tv_name = (TextView)findViewById(R.id.tv_profile_name);
        tv_phone = (TextView)findViewById(R.id.tv_profile_phone);
        tv_email = (TextView)findViewById(R.id.tv_profile_email);
        tv_gender = (TextView)findViewById(R.id.tv_profile_gender);

        iv_pic = (ImageView)findViewById(R.id.iv_profile_pic);

        User user = new User(this);

        tv_name.setText(user.getName());
        tv_phone.setText(user.getPhoneNumber());
        tv_email.setText(user.getEmail());
        tv_gender.setText(user.getGender() == User.MALE?"MALE":"FEMALE");

        iv_pic.setImageResource(Tools.getContactAvatorResId(user.getGender()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_and_edit_profle, menu);
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
