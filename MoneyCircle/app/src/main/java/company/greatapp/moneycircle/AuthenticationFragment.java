package company.greatapp.moneycircle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Prateek on 04-07-2015.
 */
public class AuthenticationFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "AuthenticationFragment";

    private Context mContext;

    /*######################################### Fragment Methods ###########################################*/
    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "--> [onAttach]");
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "--> [onCreate]");
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "--> [onCreateView]");
        View view = inflater.inflate(R.layout.authentication_screen, container, false);

        Button dummyGooglePlusButton = (Button)view.findViewById(R.id.dummyGooglePlusLoginButton);
        dummyGooglePlusButton.setOnClickListener(this);

        Button dummyFacebookButton = (Button)view.findViewById(R.id.dummyFacebookLoginButton);
        dummyFacebookButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "--> [onActivityCreated]");
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        Log.d(TAG, "--> [onStart]");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "--> [onResume]");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "--> [onPause]");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "--> [onStop]");
        super.onStop();
    }

    /*######################################################################################################*/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dummyFacebookLoginButton:
            case R.id.dummyGooglePlusLoginButton:
                launchRegistrationScreen();
                break;
            /*case R.id.tvDobId :
                showDatePickerDialog();
                break;
            case R.id.btGooglePlusId:
                signInWithGooglePlus();*/
            default:
                break;
        }
    }

    private void launchRegistrationScreen() {
        RegistrationFragment fragment = new RegistrationFragment();

        Bundle bundle = new Bundle();
        bundle.putString("email","aprateek212@yahoo.com");
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.loginContainerId, fragment, "Registration Screen");
        transaction.commit();
    }
}
