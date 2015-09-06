package company.greatapp.moneycircle;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.model.Notification;
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

        if (intent == null || /*!(intent.hasExtra(S.NOTIFICATION_TYPE)) ||*/ !(intent.hasExtra(S.NOTIFICATION_JSONSTRING))) {
            return;
        }

//        int notificationType = intent.getIntExtra(S.NOTIFICATION_TYPE, S.NOTIFICATION_INFORMATION);
        String notificationJsonString = intent.getStringExtra(S.NOTIFICATION_JSONSTRING);
        if (TextUtils.isEmpty(notificationJsonString)) {
            return;
        }

        Notification notification = GreatJSON.getNotificationFromJSONString(mContext, notificationJsonString);

        if (mIsNotifyToUser) {
//            showCustomNotification(notification.getMoneyTitle(), notification.getMessage());
        }

        notification.insertItemInDB(mContext);

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
            case S.NOTIFICATION_AGREE:
                break;
            case S.NOTIFICATION_DISAGREE:
                break;
            case S.NOTIFICATION_RECEIVE_REQUEST:
                break;
            case S.NOTIFICATION_DELETE_REQUEST:
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

        Intent intent = new Intent(mContext, NotificationActivity.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        android.app.Notification.Builder notificationBuilder = new android.app.Notification.Builder(mContext);
        if (!TextUtils.isEmpty(title)) {
            notificationBuilder.setContentTitle("New mail from " + "test@gmail.com");
        }

        notificationBuilder.setContentText(message);
        notificationBuilder.setContentIntent(pIntent);
        notificationBuilder.setAutoCancel(true);


        android.app.NotificationManager notificationManager = (android.app.NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
