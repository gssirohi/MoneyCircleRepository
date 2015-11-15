package company.greatapp.moneycircle;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.S;

/*
 * Created by Prateek on 26-06-2015.
 */
public class RegisterAndSignInActivity extends AppCompatActivity {

    private static final String TAG = "RegisterAndSignIn";

    /*###################################### Facebook related variables ####################################*/
    private CallbackManager mFacebookCallbackManager;

    private String facebookId = "NA";
    private String name = "NA";
    private String first_name = "NA";
    private String last_name = "NA";
    private String email = "NA";
    private String gender = "NA";
    private String phone_number = "NA";
    private String birthday = "NA";


    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult result) {
            Log.d(TAG, "[FacebookCallback] [onSuccess]");
            Log.d(TAG,
                    "[FacebookCallback] [onSuccess] AccessToken :"
                            + result.getAccessToken());
            Profile profile = Profile.getCurrentProfile();
            Log.d(TAG, "[FacebookCallback] [onSuccess] :" + profile);
            doGraphRequest(result);
        }

        @Override
        public void onError(FacebookException error) {
            Log.d(TAG, "[FacebookCallback] [onError]");
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "[FacebookCallback] [onCancel]");
        }
    };

    /*######################################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "--> [onCreate]");

        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.authentication_screen);
        getKeyHashValue();
        initFacebookSdk();
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.loginContainerId, new AuthenticationFragment(), "Authentication Screen");
        transaction.commit();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "--> [onActivityResult]");

        // if onActivityResult of FacebookCallBackManager is called without registering callback for
        // facebook login button, then on Successful login the button will automatically changed to Log Out button
        if (mFacebookCallbackManager != null) {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*################################### Facebook Related Methods ######################################*/

    public void initFacebookSdk() {

        // Facebook login button
        LoginButton facebookLoginButton = (LoginButton)findViewById(R.id.facebook_login_button);

        // Facebook callbackManager creation
        mFacebookCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton.setReadPermissions(Arrays.asList("email", "public_profile")); /*"user_birthday", "user_mobile_phone"*/
        facebookLoginButton.registerCallback(mFacebookCallbackManager, mFacebookCallback);
    }

    private void getKeyHashValue() {
        Log.d(TAG, "[getKeyHashValue]");
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("company.greatapp.moneycircle", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG, "[getKeyHashValue] KeyHash ["+ Base64.encodeToString(md.digest(), Base64.DEFAULT)+"]");

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void doGraphRequest(LoginResult result) {
        Log.d(TAG, "[doGraphRequest]");
        GraphRequest request = GraphRequest.newMeRequest(
                result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        if (object == null) {
                            Log.v(TAG, "doGraphRequest : object == null");
                            return;
                        }
                        try {
                            facebookId = object.getString("id");
                            name = object.getString("name");
                            email = object.getString("email");
                            first_name = object.getString("first_name");
                            last_name = object.getString("last_name");
                            gender = object.getString("gender");
//                            birthday = object.getString("birthday");
//                            phone_number = object.getString("user_mobile_phone");
                            Log.v(TAG, "id :" + facebookId);
                            Log.v(TAG, "name :" + name);
                            Log.v(TAG, "email :" + email);
                            Log.v(TAG, "first_name :" + first_name);
                            Log.v(TAG, "last_name :" + last_name);
                            Log.v(TAG, "gender :" + gender);
//                            Log.v(TAG, "email :" + birthday);
//                            Log.v(TAG, "email :" + phone_number);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.v(TAG, "facebook response :" +  response.toString());
                        startRegistrationActiviyWithFacebookContent();
                    }
                });
        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email,birthday,first_name,last_name,gender,user_mobile_phone");
        parameters.putString("fields", "id,name,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }
    /*######################################################################################################*/

    public void startRegistrationActiviyWithFacebookContent() {

        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(S.LOGIN_FROM, S.FACEBOOK_LOGIN);
        intent.putExtra(S.USER_NAME, name);
        intent.putExtra(S.USER_FIRST_NAME, first_name);
        intent.putExtra(S.USER_LAST_NAME, last_name);
        intent.putExtra(S.EMAIL_ID, email);
        intent.putExtra(S.FACEBOOK_ACCOUNT_ID, facebookId);
        intent.putExtra(S.GENDER, gender);
        intent.putExtra(S.BIRTHDAY, birthday);
        intent.putExtra(S.PHONE_NUMBER, phone_number);
        startActivity(intent);
        finish();
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(S.LOGIN_FROM, S.NORMAL_LOGIN);
        startActivity(intent);
        finish();
    }

}
