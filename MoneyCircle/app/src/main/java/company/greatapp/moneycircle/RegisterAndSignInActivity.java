package company.greatapp.moneycircle;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/*
 * Created by Prateek on 26-06-2015.
 */
public class RegisterAndSignInActivity extends AppCompatActivity {

    private static final String TAG = "RegisterAndSignIn";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sign_in);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.loginContainerId, new AuthenticationFragment(), "Authentication Screen");
        transaction.commit();
    }

}
