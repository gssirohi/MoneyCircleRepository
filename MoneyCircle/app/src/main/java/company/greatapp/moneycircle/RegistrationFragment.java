package company.greatapp.moneycircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import company.greatapp.moneycircle.dialogs.DatePickerFragment;
import company.greatapp.moneycircle.tools.DateUtils;

/**
 * Created by Prateek on 05-07-2015.
 */
public class RegistrationFragment extends Fragment implements DatePickerFragment.DateSetter, View.OnClickListener{

    private TextView tvDateOfBirth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.registration_screen, container, false);

        Bundle bundle = getArguments();
        String email = bundle.getString("email");

        TextView tvEmail = (TextView)view.findViewById(R.id.tvEmailid);
        tvEmail.setText(email);

        tvDateOfBirth = (TextView)view.findViewById(R.id.tvDobId);
        tvDateOfBirth.setOnClickListener(this);

        Button button = (Button)view.findViewById(R.id.bt_continue);
        button.setOnClickListener(this);

        return view;
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(this);
        datePickerFragment.show(getFragmentManager(), "Date Picker");
    }

    @Override
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
       String dateString = DateUtils.getDateString(year, monthOfYear, dayOfMonth);
        tvDateOfBirth.setText(dateString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDobId:
                showDatePickerDialog();
                break;
            case R.id.bt_continue:
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
