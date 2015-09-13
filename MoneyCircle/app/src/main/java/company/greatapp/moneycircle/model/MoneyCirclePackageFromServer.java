package company.greatapp.moneycircle.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.constants.S;

/**
 * Created by Prateek on 09-09-2015.
 */
public class MoneyCirclePackageFromServer {

    public static final int RESPONSE_STATE_NOT_RESPONDED            = 13;
    public static final int RESPONSE_STATE_AGREED                   = 14;
    public static final int RESPONSE_STATE_DISAGREED                = 15;
    public static final int RESPONSE_STATE_REMOVE_ITEM              = 16;
    public static final int RESPONSE_STATE_KEEP_ITEM                = 17;
    public static final int RESPONSE_STATE_RESEND                   = 18;

    private int reqCode;

    private String reqSenderPhone;
    private String reqReceiverPhone;

    private String reqSenderName;
    private Uri reqSenderImageUri;

    private String itemOwnerPhone;
    private String itemAssociatePhone;

    private String moneyReceiverPhone;
    private String moneyPayerPhone;

    private int ownerItemType;
    private int associateItemtype;

    private String ownerItemId;
    private String associateItemId;

    private int itemBodyJsonType;
    private String itemBodyJsonString;

    private String message;

    private String dateString;

    private String itemOwnerName;

    private String itemTitle;
    private String amount;

    private String itemDateString;

    private String itemDueDateString;

    private String itemDescription;


    private String uid;
    private int dbId;

    private boolean isRespondable;
    private int responseState;

    public MoneyCirclePackageFromServer(){

    }
    public MoneyCirclePackageFromServer(Cursor c){
        setUid(c.getString(c.getColumnIndex(DB.UID)));
        setReqCode(c.getInt(c.getColumnIndex(DB.REQUEST_CODE)));
        setReqSenderPhone(c.getString(c.getColumnIndex(DB.REQUEST_SENDER_PHONE)));
        setReqSenderName(c.getString(c.getColumnIndex(DB.REQUEST_SENDER_NAME)));
        setReqReceiverPhone(c.getString(c.getColumnIndex(DB.REQUEST_RECIEVER_PHONE)));
        setItemOwnerPhone(c.getString(c.getColumnIndex(DB.ITEM_OWNER_PHONE)));
        setItemAssociatePhone(c.getString(c.getColumnIndex(DB.ITEM_ASSOCIATE_PHONE)));
        setMoneyReceiverPhone(c.getString(c.getColumnIndex(DB.MONEY_RECIEVER_PHONE)));
        setMoneyPayerPhone(c.getString(c.getColumnIndex(DB.MONEY_PAYER_PHONE)));
        setOwnerItemType(c.getInt(c.getColumnIndex(DB.OWNER_ITEM_TYPE)));
        setAssociateItemtype(c.getInt(c.getColumnIndex(DB.ASSOCIATE_ITEM_TYPE)));
        setItemBodyJsonType(c.getInt(c.getColumnIndex(DB.ITEM_BODY_JSON_TYPE)));
        setItemBodyJsonString(c.getString(c.getColumnIndex(DB.ITEM_BODY_JSON_STRING)));
        setMessage(c.getString(c.getColumnIndex(DB.MESSAGE)));
        setItemTitle(c.getString(c.getColumnIndex(DB.ITEM_TITLE)));
        setAmount(c.getString(c.getColumnIndex(DB.AMOUNT)));
        setItemDateString(c.getString(c.getColumnIndex(DB.ITEM_DATE_STRING)));
        setItemDueDateString(c.getString(c.getColumnIndex(DB.ITEM_DUE_DATE_STRING)));
        setItemDescription(c.getString(c.getColumnIndex(DB.ITEM_DESCRIPTION)));
        int responded = c.getInt(c.getColumnIndex(DB.IS_RESPONDED));
        setIsRespondable((responded == 1) ? true : false);
        setResponseState(c.getInt(c.getColumnIndex(DB.RESPONSE_STATE)));
    }

    public int getResponseState() {
        return responseState;
    }

    public void setResponseState(int responseState) {
        this.responseState = responseState;
    }

    public boolean isRespondable() {
        return isRespondable;
    }

    public void setIsRespondable(boolean isRespondable) {
        this.isRespondable = isRespondable;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getReqCode() {
        return reqCode;
    }

    public void setReqCode(int reqCode) {
        this.reqCode = reqCode;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getReqSenderPhone() {
        return reqSenderPhone;
    }

    public void setReqSenderPhone(String reqSenderPhone) {
        this.reqSenderPhone = reqSenderPhone;
    }

    public String getReqReceiverPhone() {
        return reqReceiverPhone;
    }

    public void setReqReceiverPhone(String reqReceiverPhone) {
        this.reqReceiverPhone = reqReceiverPhone;
    }

    public String getReqSenderName() {
        return reqSenderName;
    }

    public void setReqSenderName(String reqSenderName) {
        this.reqSenderName = reqSenderName;
    }

    public Uri getReqSenderImageUri() {
        return reqSenderImageUri;
    }

    public void setReqSenderImageUri(Uri reqSenderImageUri) {
        this.reqSenderImageUri = reqSenderImageUri;
    }

    public String getItemOwnerPhone() {
        return itemOwnerPhone;
    }

    public void setItemOwnerPhone(String itemOwnerPhone) {
        this.itemOwnerPhone = itemOwnerPhone;
    }

    public String getItemOwnerName() {
        return itemOwnerName;
    }

    public void setItemOwnerName(String itemOwnerName) {
        this.itemOwnerName = itemOwnerName;
    }

    public String getItemAssociatePhone() {
        return itemAssociatePhone;
    }

    public void setItemAssociatePhone(String itemAssociatePhone) {
        this.itemAssociatePhone = itemAssociatePhone;
    }

    public String getMoneyReceiverPhone() {
        return moneyReceiverPhone;
    }

    public void setMoneyReceiverPhone(String moneyReceiverPhone) {
        this.moneyReceiverPhone = moneyReceiverPhone;
    }

    public String getMoneyPayerPhone() {
        return moneyPayerPhone;
    }

    public void setMoneyPayerPhone(String moneyPayerPhone) {
        this.moneyPayerPhone = moneyPayerPhone;
    }

    public int getOwnerItemType() {
        return ownerItemType;
    }

    public void setOwnerItemType(int ownerItemType) {
        this.ownerItemType = ownerItemType;
    }

    public int getAssociateItemtype() {
        return associateItemtype;
    }

    public void setAssociateItemtype(int associateItemtype) {
        this.associateItemtype = associateItemtype;
    }

    public String getOwnerItemId() {
        return ownerItemId;
    }

    public void setOwnerItemId(String ownerItemId) {
        this.ownerItemId = ownerItemId;
    }

    public String getAssociateItemId() {
        return associateItemId;
    }

    public void setAssociateItemId(String associateItemId) {
        this.associateItemId = associateItemId;
    }

    public String getItemBodyJsonString() {
        return itemBodyJsonString;
    }

    public void setItemBodyJsonString(String itemBodyJsonString) {
        this.itemBodyJsonString = itemBodyJsonString;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItemDateString() {
        return itemDateString;
    }

    public void setItemDateString(String itemDateString) {
        this.itemDateString = itemDateString;
    }

    public String getItemDueDateString() {
        return itemDueDateString;
    }

    public void setItemDueDateString(String itemDueDateString) {
        this.itemDueDateString = itemDueDateString;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemBodyJsonType() {
        return itemBodyJsonType;
    }

    public void setItemBodyJsonType(int itemBodyJsonType) {
        this.itemBodyJsonType = itemBodyJsonType;
    }

    public Uri insertItemInDB(Context context) {
        if(context == null) {
            Log.d("PACKAGEFROM", "context is NULL");
            return null;
        }
        ContentResolver resolver = context.getContentResolver();
        if(resolver == null) {
            Log.d("PACKAGEFROM", "context is NULL");
            return null;
        }
        ContentValues values = getContentValues();
        return resolver.insert(DB.PACKAGE_FROM_SERVER_TABLE_URI, values);
    }

    public ContentValues getContentValues() {
        ContentValues row = new ContentValues();

        row.put(DB.UID,getUid());
        row.put(DB.REQUEST_CODE,getReqCode());
        row.put(DB.REQUEST_SENDER_PHONE,getReqSenderPhone());
        row.put(DB.REQUEST_SENDER_NAME,getReqSenderName());
        row.put(DB.REQUEST_RECIEVER_PHONE,getReqReceiverPhone());
        row.put(DB.ITEM_OWNER_PHONE,getItemOwnerPhone());
        row.put(DB.ITEM_ASSOCIATE_PHONE,getItemAssociatePhone());
        row.put(DB.MONEY_RECIEVER_PHONE,getMoneyReceiverPhone());
        row.put(DB.MONEY_PAYER_PHONE,getMoneyPayerPhone());
        row.put(DB.OWNER_ITEM_TYPE,getOwnerItemType());
        row.put(DB.ASSOCIATE_ITEM_TYPE,getAssociateItemtype());
        row.put(DB.ITEM_BODY_JSON_TYPE,getItemBodyJsonType());
        row.put(DB.ITEM_BODY_JSON_STRING,getItemBodyJsonString());
        row.put(DB.MESSAGE,getMessage());
        row.put(DB.ITEM_TITLE,getItemTitle());
        row.put(DB.AMOUNT,""+getAmount());
        row.put(DB.ITEM_DATE_STRING,getDateString());
        row.put(DB.ITEM_DUE_DATE_STRING,getItemDueDateString());
        row.put(DB.ITEM_DESCRIPTION,getItemDescription());
        row.put(DB.IS_RESPONDED, isRespondable()?1:0);
        row.put(DB.RESPONSE_STATE,getResponseState());
        return row;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }


    public String createNotificationMessage() {

        String message = null;
        switch (getReqCode()) {

            case S.TRANSPORT_REQUEST_CODE_LENT:
                message = "YOU OWE "+ getAmount() +" to "+ getReqSenderName() + " for this transaction";
                break;

            case S.TRANSPORT_REQUEST_CODE_BORROW:
                message = getReqSenderName() + " OWES YOU" + getAmount() + " for this transaction";
                break;
//            case S.NOTIFICATION_LENT_REQUEST:
//                message = "YOU OWE "+ amount +" to "+ name + " for this transaction";
//                break;
//            case S.NOTIFICATION_BORROW_REQUEST:
//                message = name + " OWES YOU" + amount + " for this transaction";
//                break;
//            case S.NOTIFICATION_PAY_REQUEST:
//                message = name + " PAYED " + amount + " to YOU for this transaction";
//                break;
//            case S.NOTIFICATION_SETTLE_REQUEST:
//                message = name + " SETTLED UP with YOU";
//                break;
//            case S.NOTIFICATION_REMINDER_REQUEST:
//                message = "REMINDER to pay " + amount + " to " + name;
//                break;
//            case S.NOTIFICATION_AGREE_LENT:
//                message = name + " agree to pay "+ amount + " for this transaction";
//                break;
//            case S.NOTIFICATION_DISAGREE_LENT:
//                message = name + " disagreed to pay "+ amount + " for this transaction";
//                break;
//            case S.NOTIFICATION_RECEIVE_REQUEST:
//                message = name + " RECEIVED "+ amount + " for this transaction";
//                break;
//            case S.NOTIFICATION_DELETE_LENT_REQUEST:
//                message = name + " deleted  this transaction";
//                break;
//            case S.NOTIFICATION_MODIFY_lENT_REQUEST:
//                message = name + " modified this transaction";
//                break;
//            case S.NOTIFICATION_INFORMATION:
//                message = "Information Message of Money Circle";
//                break;
            default:
                break;
        }
        return message;
    }
}
