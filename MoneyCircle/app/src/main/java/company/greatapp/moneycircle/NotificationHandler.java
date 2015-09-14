package company.greatapp.moneycircle;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.constants.States;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.MoneyCirclePackageFromServer;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.tools.Tools;

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

//        int notificationType = intent.getIntExtra(S.NOTIFICATION_TYPE, S.NOTIFICATION_INFORMATION);
        String messageJsonString = intent.getStringExtra(S.PACKAGE_FROMSERVER_JSON_STRING);
        if (TextUtils.isEmpty(messageJsonString)) {
            return;
        }

        MoneyCirclePackageFromServer packageFromServer = GreatJSON.getServerPackageFromJson(mContext, messageJsonString);



        if (isCustomNotificationRequired(packageFromServer)) {
            showCustomNotification(packageFromServer.getItemTitle(), packageFromServer.getMessage());
        }


        computeReceivedPackage(packageFromServer);

        packageFromServer.insertItemInDB(mContext);
    }

    private boolean isCustomNotificationRequired(MoneyCirclePackageFromServer packageFromServer) {
        return true;
    }

    private void computeReceivedPackage(MoneyCirclePackageFromServer inPackage) {

        User user = new User(mContext);
        String borrowUid = "";
        String lentUid = "";
        String contactUid = "";
        Contact contact;
        Borrow borrow;
        Lent lent;
        switch(inPackage.getReqCode()){
            case S.TRANSPORT_REQUEST_CODE_LENT:
                inPackage.setIsRespondable(true);
                //You have borrowed some amount and, lent owner has sent request for same
                // Before creating this Borrow you need to approve this

                break;


            case S.TRANSPORT_REQUEST_CODE_AGREE_LENT:
                //Lent created by you has been approved by Associate
                //You need to wait for payment
                lentUid = inPackage.getOwnerItemId();
                lent = (Lent)Tools.getDbInstance(mContext,lentUid, Model.MODEL_TYPE_LENT);
                lent.setState(States.LENT_WAITING_FOR_PAYMENT);
                lent.updateItemInDb(mContext);
                Tools.sendTransactionBroadCast(mContext, lent, Model.MODEL_TYPE_LENT);
                break;

            case S.TRANSPORT_REQUEST_CODE_DISAGREE_LENT:
                //Lent created by you has been declined by Associate
                //Now We give you some choices,You need to choose one
                lentUid = inPackage.getOwnerItemId();
                lent = (Lent)Tools.getDbInstance(mContext,lentUid, Model.MODEL_TYPE_LENT);
                lent.setState(States.LENT_DISAPPROVED_ACTION_PENDING);
                lent.updateItemInDb(mContext);
                Tools.sendTransactionBroadCast(mContext, lent, Model.MODEL_TYPE_LENT);
                break;


            case S.TRANSPORT_REQUEST_CODE_BORROW:
            //You have borrowed some amount and, lent owner has sent request for same
            // Before creating this Borrow you need to approve this
                inPackage.setIsRespondable(true);
                break;

            case S.TRANSPORT_REQUEST_CODE_AGREE_BORROW:
                // Borrow item created by you has been approved by Associate(Lent owner)
                // Now he is waiting for payment, you need to make payment.
                borrowUid = inPackage.getOwnerItemId();
                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid, Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_PAYMENT_PENDING);
                borrow.updateItemInDb(mContext);
                Tools.sendTransactionBroadCast(mContext, borrow, Model.MODEL_TYPE_BORROW);
                break;
            case S.TRANSPORT_REQUEST_CODE_DISAGREE_BORROW:
                //Borrow item created by you has been declined by Associate
                //Now we give you some choices, You need to choose one action
                borrowUid = inPackage.getOwnerItemId();
                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid, Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_DISAPPROVED_ACTION_PENDING);
                borrow.updateItemInDb(mContext);
                Tools.sendTransactionBroadCast(mContext, borrow, Model.MODEL_TYPE_BORROW);
                break;
            case S.TRANSPORT_REQUEST_CODE_PAY:
                //borrower has made a payment, user need to approve
                inPackage.setIsRespondable(true);

                lentUid = inPackage.getOwnerItemId();
                lent = (Lent)Tools.getDbInstance(mContext,lentUid, Model.MODEL_TYPE_LENT);
                lent.setState(States.LENT_PAYMENT_RECEIVED_DISAPPROVED_ACTION_PENDING);
                lent.updateItemInDb(mContext);
                Tools.sendTransactionBroadCast(mContext, lent, Model.MODEL_TYPE_LENT);
                break;

            case S.TRANSPORT_REQUEST_CODE_AGREE_PAY:
                // You made a payment and that is approved now by Lent owner
                //

                if(user.getPhoneNumber().equals(inPackage.getItemAssociatePhone())) {
                    borrowUid = inPackage.getAssociateItemId();
                } else {
                    //you are owner of this borrow item
                    borrowUid = inPackage.getOwnerItemId();
                }

                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid, Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_PAYMENT_CLEARED);
                borrow.updateItemInDb(mContext);
                Tools.sendTransactionBroadCast(mContext, borrow, Model.MODEL_TYPE_BORROW);
                break;
            case S.TRANSPORT_REQUEST_CODE_DISAGREE_PAY:
                // You made a payment and that is declined by Lent owner
                // We give you some choices now , perform any action

                if(user.getPhoneNumber().equals(inPackage.getItemAssociatePhone())) {
                    borrowUid = inPackage.getAssociateItemId();
                } else {
                    //you are owner of this borrow item
                    borrowUid = inPackage.getOwnerItemId();
                }

                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid, Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_PAYMENT_PAID_DISAPPROVED_ACTION_PENDING);
                borrow.updateItemInDb(mContext);
                Tools.sendTransactionBroadCast(mContext, borrow, Model.MODEL_TYPE_BORROW);

                break;
            case S.TRANSPORT_REQUEST_CODE_RECEIVE:
                // Giver has received return payment for his lent, He wants to notify you
                // You should update your borrow item as paid
                if(user.getPhoneNumber().equals(inPackage.getItemAssociatePhone())) {
                    borrowUid = inPackage.getAssociateItemId();
                } else {
                    //you are owner of this borrow item
                    borrowUid = inPackage.getOwnerItemId();
                }

                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid, Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_PAYMENT_CLEARED);
                borrow.updateItemInDb(mContext);
                Tools.sendTransactionBroadCast(mContext, borrow, Model.MODEL_TYPE_BORROW);
                break;

        }
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
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        if (!TextUtils.isEmpty(title)) {
            notificationBuilder.setContentTitle(title);
        }

        notificationBuilder.setContentText(message);
        notificationBuilder.setSmallIcon(R.drawable.home_icon);
        notificationBuilder.setContentIntent(pIntent);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.home_icon);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);


        android.app.NotificationManager notificationManager = (android.app.NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
