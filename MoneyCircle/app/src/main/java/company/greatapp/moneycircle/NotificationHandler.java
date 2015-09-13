package company.greatapp.moneycircle;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import java.util.Random;

import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.model.MoneyCirclePackageFromServer;
import company.greatapp.moneycircle.tools.GreatJSON;

/**
 * Created by Prateek on 06-09-2015.
 */
public class NotificationHandler {


    private final Context mContext;
    private final boolean mIsNotifyToUser;

    public NotificationHandler(Context context) {
        mContext = context;
        // TODO Need to add that setting value in prefrences
        mIsNotifyToUser = true;
    }

    public void handleGCM_Message(Intent intent) {

        if (intent == null || /*!(intent.hasExtra(S.NOTIFICATION_TYPE)) ||*/ !(intent.hasExtra(S.PACKAGE_FROMSERVER_JSON_STRING))) {
            return;
        }

        int notificationType = intent.getIntExtra(S.NOTIFICATION_TYPE, S.NOTIFICATION_INFORMATION);
        String messageJsonString = intent.getStringExtra(S.PACKAGE_FROMSERVER_JSON_STRING);
        if (TextUtils.isEmpty(messageJsonString)) {
            return;
        }

        MoneyCirclePackageFromServer packageFromServer = GreatJSON.getServerPackageFromJson(mContext, messageJsonString);

        packageFromServer.insertItemInDB(mContext);

        if (mIsNotifyToUser) {
            showCustomNotification(packageFromServer.getItemTitle(), packageFromServer.getMessage());
            Log.d("Ashu","message"+packageFromServer.getMessage());
        }




//        notification.insertItemInDB(mContext);

        /*switch (notificationType) {
            case S.NOTIFICATION_LENT_REQUEST:
                break;
            case S.NOTIFICATION_BORROW_REQUEST:
                break;
            case S.NOTIFICATION_PAY_REQUEST:
                break;
            case S.NOTIFICATION_SETTLE_REQUEST:
                break;
            case S.NOTIFICATION_REMINDER_REQUEST:
                break;
            case S.NOTIFICATION_AGREE_LENT:
                break;
            case S.NOTIFICATION_DISAGREE:
                break;
            case S.NOTIFICATION_RECEIVE_REQUEST:
                break;
            case S.NOTIFICATION_DELETE_LENT_REQUEST:
                break;
            case S.NOTIFICATION_MODIFY_REQUEST:
                break;
            case S.NOTIFICATION_INFORMATION:
                break;
            default:
                break;
        }*/

    }

    public void showCustomNotification(String title, String message) {

        if (message == null || TextUtils.isEmpty(message)) {
            return;
        }

//        else {
//            message = "YOU OWE "+ Amount +" to "+ reqSenderName + " for this transaction";
//        }

        Intent intent = new Intent(mContext, NotificationActivity.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent, 0);

       Uri sound = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.notifysnd);



        // build notification
        // the addAction re-use the same intent to keep the example short
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        if (!TextUtils.isEmpty(title)) {
            notificationBuilder.setContentTitle(title);
        }

        notificationBuilder.setSmallIcon(R.drawable.home_icon);
        notificationBuilder.setContentText(message);
        notificationBuilder.setContentIntent(pIntent);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(sound);
      //  notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);


        android.app.NotificationManager notificationManager = (android.app.NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }








}
