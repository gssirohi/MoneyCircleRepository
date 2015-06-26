package company.greateapp.moneycircle;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/*
 * Created by Prateek on 26-06-2015.
 */
public class RegisterAndSignInActivity extends ActionBarActivity implements DatePickerFragment.DateSetter{

    TextView tvDateOfBirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_sign_in_screen);
        tvDateOfBirth = (TextView)findViewById(R.id.tvDobId);
        tvDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(this);
        datePickerFragment.show(getSupportFragmentManager(), "ddatePicker");
    }

    @Override
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        tvDateOfBirth.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear, year));
    }
}
