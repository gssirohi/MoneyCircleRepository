package company.greatapp.moneycircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import company.greatapp.moneycircle.categories.ManageCategoriesActivity;

/**
 * Created by Prateek on 28-06-2015.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvDailyReminder;
    private TextView tvPassword;
    private TextView tvCategory;
    private TextView tvProfile;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        tvDailyReminder = (TextView) findViewById(R.id.tv_dailyReminderId);
        tvDailyReminder.setOnClickListener(this);
        tvPassword = (TextView) findViewById(R.id.tv_passwordId);
        tvPassword.setOnClickListener(this);
        tvCategory = (TextView) findViewById(R.id.tv_categoriesId);
        tvCategory.setOnClickListener(this);
        tvProfile = (TextView) findViewById(R.id.tv_profileId);
        tvProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_dailyReminderId:
                break;
            case R.id.tv_passwordId:
                break;
            case R.id.tv_categoriesId:
               // intent = new Intent(this, ManageCategoriesActivity.class);
                intent = new Intent(this, CategoryActivity.class);
                break;
            case R.id.tv_profileId:
                break;
            default:
                intent = null;
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
