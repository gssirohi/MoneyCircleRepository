package company.greatapp.moneycircle;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.constants.States;
import company.greatapp.moneycircle.manager.Accountant;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.InPackage;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
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

        InPackage packageFromServer = GreatJSON.getServerPackageFromJson(mContext, messageJsonString);



        if (isCustomNotificationRequired(packageFromServer)) {
            showCustomNotification(packageFromServer.getItemTitle(), packageFromServer.getMessage());
        }


        computeReceivedPackage(packageFromServer);

        packageFromServer.insertItemInDB(mContext);
    }

    private boolean isCustomNotificationRequired(InPackage packageFromServer) {
        return true;
    }

    private void computeReceivedPackage(InPackage inPackage) {

        User user = new User(mContext);
        String borrowUid = "";
        String lentUid = "";
        String contactJson = "";
        String contactUid = "";
        String contactPhone = "";
        Contact contact;
        Borrow borrow;
        Lent lent;
        switch(inPackage.getReqCode()){

            case S.TRANSPORT_REQUEST_CODE_LENT:
                borrow = new Borrow(mContext,inPackage);
                if(borrow != null) {
                    borrow.insertItemInDB(mContext);
                    contact = borrow.getLinkedContact();
                    if(contact != null) {
                        contact.setBorrowedAmountfromThis(contact.getBorrowedAmountfromThis() + borrow.getAmount());
                        contact.updateItemInDb(mContext);
                    }
                    Tools.sendMoneyTransactionBroadCast(mContext,borrow,Model.MODEL_TYPE_BORROW);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_BORROW:
                lent = new Lent(mContext,inPackage);
                if(lent != null) {
                    lent.insertItemInDB(mContext);
                    contact = lent.getLinkedContact();
                    if(contact != null) {
                        contact.setLentAmountToThis(contact.getLentAmountToThis() + lent.getAmount());
                        contact.updateItemInDb(mContext);
                    }
                    Tools.sendMoneyTransactionBroadCast(mContext,lent,Model.MODEL_TYPE_LENT);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_PAY:
                if(user.getPhoneNumber().equals(inPackage.getItemOwnerPhone())){
                    lentUid = inPackage.getOwnerItemId();
                } else {
                    lentUid = inPackage.getAssociateItemId();
                }
                 lent = (Lent)Tools.getDbInstance(mContext,lentUid,Model.MODEL_TYPE_LENT);
                if(lent != null) {
                    lent.setState(States.LENT_AMOUNT_RECEIVED);
                    lent.updateItemInDb(mContext);
                    contactJson = lent.getLinkedContactJson();
                    contact = GreatJSON.getContactFromJsonString(contactJson,mContext);
                    if(contact != null) {
                        contact.setLentAmountToThis(contact.getLentAmountToThis() - lent.getAmount());
                        contact.updateItemInDb(mContext);
                    }
                    Tools.sendMoneyTransactionBroadCast(mContext,lent,Model.MODEL_TYPE_LENT);
                }
                break;

            case S.TRANSPORT_REQUEST_CODE_RECEIVE:
                if(user.getPhoneNumber().equals(inPackage.getItemOwnerPhone())){
                    borrowUid = inPackage.getOwnerItemId();
                } else {
                    borrowUid = inPackage.getAssociateItemId();
                }
                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid,Model.MODEL_TYPE_BORROW);
                if(borrow != null) {
                    borrow.setState(States.BORROW_PAYMENT_CLEARED);
                    borrow.updateItemInDb(mContext);
                    contactJson = borrow.getLinkedContactJson();
                    contact = GreatJSON.getContactFromJsonString(contactJson, mContext);
                    if(contact != null) {
                        contact.setBorrowedAmountfromThis(contact.getBorrowedAmountfromThis() - borrow.getAmount());
                        contact.updateItemInDb(mContext);
                    }
                    Tools.sendMoneyTransactionBroadCast(mContext,borrow,Model.MODEL_TYPE_BORROW);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_SETTLE:
                inPackage.setIsRespondable(true);
                inPackage.setResponseState(InPackage.RESPONSE_STATE_NOT_RESPONDED);
                contactPhone = inPackage.getReqSenderPhone();
                contact = Tools.getContactFromPhoneNumber(mContext,contactPhone);
                if(contact != null) {
                    contact.setState(States.CONTACT_SETTLE_REQ_APPROVAL_PENDING);
                    contact.updateItemInDb(mContext);
                }
                inPackage.updateItemInDb(mContext);
                break;
            case S.TRANSPORT_REQUEST_CODE_REMINDER:
                break;
            case S.TRANSPORT_REQUEST_CODE_NOTIFICATION:
                break;
            case S.TRANSPORT_REQUEST_CODE_DELETE_BORROW:
                break;
            case S.TRANSPORT_REQUEST_CODE_DELETE_LENT:
                break;
            case S.TRANSPORT_REQUEST_CODE_SETTLE_AGREE:
                contactPhone = inPackage.getReqSenderPhone();
                contact = Tools.getContactFromPhoneNumber(mContext,contactPhone);
                if(contact != null) {
                    Accountant.performSettleUpWithContact(mContext,contact);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_SETTLE_DISAGREE:
                contactPhone = inPackage.getReqSenderPhone();
                contact = Tools.getContactFromPhoneNumber(mContext,contactPhone);
                if(contact != null) {
                    contact.setState(States.CONTACT_SETTLE_REQUEST_DISAPPROVED_ACTION_PENDING);
                    contact.updateItemInDb(mContext);
                }
                break;
            default:
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
