package company.greatapp.moneycircle.tools;

/**
 * Created by Gyanendrasingh on 8/30/2015.
 */

        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;
        import com.google.android.gcm.GCMRegistrar;

        import org.json.JSONArray;

        import company.greatapp.moneycircle.constants.C;
        import company.greatapp.moneycircle.constants.S;
        import company.greatapp.moneycircle.manager.Transporter;
        import company.greatapp.moneycircle.model.User;

public class RegistrationUtils {

    static String TAG  = "RegistrationManager";


    public  static boolean validateCredentials() {
        return false;
    }

    public  static boolean isRegisteredIOnGCMServer(Context context) {
        final String regId = GCMRegistrar.getRegistrationId(context);

        if (regId.equals("")) {
            Log.d(TAG,"Not registered in GCM Server");
            User u = new User(context);
            u.updateInfo(User.U_GCM_IS_REGISTERED, ""+false);
            u.updateInfo(User.U_REGID,regId);//invalidate info may be new version installed
            //u.updateInfo(User.U_App_IS_REGISTERED,""+false);
            return false;
        } else {
            User u = new User(context);
            if(!u.isRegisteredOnGCM() ||  !u.getRegId().equals(regId)) {
                u.updateInfo(User.U_GCM_IS_REGISTERED, "" + true);
                u.updateInfo(User.U_REGID, regId);
            }
            return true;
        }

    }

    public static boolean isDeviceRegisteredAtBothServer(Context context) {
        return (isRegisteredIOnGCMServer(context) && isRegisteredOnAppServer(context));
    }

    public  static boolean isRegisteredOnAppServer(Context context) {
        return GCMRegistrar.isRegisteredOnServer(context);
    }


    public static void registerUserOnGCM(Context context) {
        Log.d("REGISTRATION","Registering on GCM");
        GCMRegistrar.checkDevice(context);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(context);
        GCMRegistrar.register(context, S.SENDER_ID);

    }

    public static void registerUserOnAppServer(final Context context) {
        Transporter vp = new Transporter(context);
        vp.registerUserOnAppServer(new User(context));

    }

    public static void unregisterFromAppServer(Context context, String registrationId) {
//        Transporter vp = new Transporter();
//        vp.unregisterUserFromAppServer();
    }

    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(C.ACTION_DISPLAY_MESSAGE);
        intent.putExtra(C.DISPLAY_MESSAGE_TEXT, message);
        context.sendBroadcast(intent);
    }

    public static void sendGCMRegistrationResult(Context context, boolean result) {
        Intent intent = new Intent(S.ACTION_GCM_REGISTRATION_RESULT);
        intent.putExtra(S.KEY_GCM_REGISTRATION, result);
        context.sendBroadcast(intent);
    }

    public static void sendAppServerRegistrationResult(Context context, boolean result) {
        Intent intent = new Intent(S.ACTION_APP_SERVER_REGISTRATION_RESULT);
        intent.putExtra(S.KEY_APP_SERVER_REGISTRATION, result);
        context.sendBroadcast(intent);
    }

    public static void checkRegisteredContactsInAppServer(Context context,JSONArray phoneNumberJsonArray) {
        Transporter vp = new Transporter(context);
        vp.checkRegisteredContactsInAppServer(phoneNumberJsonArray);
    }

    public static void sendRegisteredContactsCheckResult(Context context, String phoneNumberListJsonString) {
        Intent intent = new Intent(S.ACTION_CHECK_REGISTERED_CONTACTS_RESULT);
        intent.putExtra(S.KEY_REGISTERED_CONTACTS_RESULT, phoneNumberListJsonString);
        context.sendBroadcast(intent);
    }


//
//    public static void sendRegistrationResult(Context context,boolean result) {

//    }

}
