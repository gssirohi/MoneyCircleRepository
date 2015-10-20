package company.greatapp.moneycircle;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.constants.States;
import company.greatapp.moneycircle.manager.Accountant;
import company.greatapp.moneycircle.manager.AppController;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.InPackage;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.TransactionalMessage;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.DateUtils;
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

        if (packageFromServer == null) {
            return;
        }

        if (isCustomNotificationRequired()) {

            Cursor cursor = Tools.getUnSeenInPackageItemCursor(mContext);
            String notificationTitle = packageFromServer.getItemTitle();
            String notificationMessage = packageFromServer.getMessage();
            if(cursor != null && cursor.getCount() > 0) {
                int unSeenNotificationCount = cursor.getCount()+1;
                String unSeenNotificationMessage = Tools.getUnSeenNotificationMessage(cursor);
                if (unSeenNotificationMessage != null) {
                    notificationTitle = ""+unSeenNotificationCount+" new Notifications";
                    notificationMessage = notificationMessage + unSeenNotificationMessage;
                }
            }

            showCustomNotification(notificationTitle, notificationMessage);
        }


        computeReceivedPackage(packageFromServer);

        packageFromServer.insertItemInDB(mContext);
    }

    public void handleTransactionMessage(TransactionalMessage messageItem) {

        InPackage inPackage = getInPackageFromTransactionalMessage(messageItem);

        if (inPackage == null) {
            return;
        }

        if (isCustomNotificationRequired()) {
            showCustomNotification(inPackage.getItemTitle(), inPackage.getMessage());
        }

        inPackage.insertItemInDB(mContext);
    }

    public InPackage getInPackageFromTransactionalMessage(TransactionalMessage messageItem) {

        if (messageItem == null) {
         return null;
        }
        InPackage inPackage = new InPackage();
        switch (messageItem.getType()) {
            case TransactionalMessage.MESSAGE_TYPE_EXPENSE:
                inPackage.setReqCode(S.REQUEST_CODE_EXPENSE_MESSAGE);
                break;
            case TransactionalMessage.MESSAGE_TYPE_INCOME:
                inPackage.setReqCode(S.REQUEST_CODE_INCOME_MESSAGE);
                break;
            case TransactionalMessage.MESSAGE_TYPE_BILL:
                inPackage.setReqCode(S.REQUEST_CODE_BILL_MESSAGE);
                break;
            default:
                break;
        }

        inPackage.setDateString(DateUtils.getCurrentDate());
        inPackage.setItemDateString(DateUtils.getCurrentDate());
        inPackage.setState(InPackage.ITEM_STATE_UNSEEN);
        inPackage.setAmount("" + messageItem.getAmount());
        inPackage.setItemTitle(messageItem.getTitle());
        inPackage.setMessage(messageItem.getDescription());
        inPackage.setItemBodyJsonString(messageItem.getJsonString());
        inPackage.setIsRespondable(true);
        inPackage.setResponseState(InPackage.RESPONSE_STATE_NOT_RESPONDED);

        return inPackage;
    }

    private boolean isCustomNotificationRequired() {
        return (!AppController.isNotificationActivityVisible());
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
            case S.TRANSPORT_REQUEST_CODE_MODIFIED_LENT:
                borrow = new Borrow(mContext,inPackage);
                if(borrow != null) {
                    float previousAmount = Tools.getAmountForParticularUID(mContext, Model.MODEL_TYPE_BORROW, borrow.getUID());
                    borrow.updateItemInDb(mContext);
                    /*float amountToUpdateInContact = 0;
                    if (previousAmount < borrow.getAmount()) {
                        amountToUpdateInContact = borrow.getAmount() - previousAmount;
                    } else if (previousAmount > borrow.getAmount()) {
                        amountToUpdateInContact = previousAmount - borrow.getAmount();
                    }*/
                    float amountToUpdateInContact = borrow.getAmount() - previousAmount;
                    contact = borrow.getLinkedContact();
                    if(contact != null) {
                        contact.setBorrowedAmountfromThis(contact.getBorrowedAmountfromThis() + amountToUpdateInContact);
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
            case S.TRANSPORT_REQUEST_CODE_MODIFIED_BORROW:
                lent = new Lent(mContext,inPackage);
                if(lent != null) {
                    float previousAmount = Tools.getAmountForParticularUID(mContext, Model.MODEL_TYPE_LENT, lent.getUID());
                    lent.updateItemInDb(mContext);

                    float amountToUpdateInContact = lent.getAmount() - previousAmount;
                    contact = lent.getLinkedContact();
                    if(contact != null) {
                        contact.setLentAmountToThis(contact.getLentAmountToThis() +  amountToUpdateInContact);
                        contact.updateItemInDb(mContext);
                    }
                    Tools.sendMoneyTransactionBroadCast(mContext,lent,Model.MODEL_TYPE_BORROW);
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
//                inPackage.updateItemInDb(mContext);   // This entry is inserted in DB after this function call
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
//        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);

        Notification notification = notificationBuilder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;


        android.app.NotificationManager notificationManager = (android.app.NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);
    }
}
