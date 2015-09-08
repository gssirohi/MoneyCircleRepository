package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;

import java.util.Date;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Notification extends Model {

    boolean isResponded;
    private int mDbId;
    private String mUid = "";
    private String mTitle = "";
    private int modelType;
	private String jsonString = "";
	 private String mCategory;
    private String mDescription = "";
    private float mAmount;
    private String mDateString ="";
    private Date date;
    private Date dueDate;
    private String dueDateString = "";

    private String ownerNumber ="";
    private String moneyTitle ="";
    private String moneyDate ="";
    private String moneyDueDate="";
    private String moneyDescription ="";
    private String message ="";
    private String moneyReciever="";
    private String moneyPayer="";
    private String ownerItemUid="";
    private String type ="";
    private String senderName ="";
    private String itemOwnerName ="";




    public Notification() {
        setUID(Tools.generateUniqueId());
    }

    public Notification(int dbId, String uid) {
        mDbId = dbId;
        mUid = uid;
    }
   
    @Override
	protected Uri getTableUri() {
        return DB.NOTIFICATION_TABLE_URI;
    }
    @Override
    public void setTitle(String title) {
        mTitle =title;

    }

    @Override
    public void setModelType(int modelType) {

    }

    @Override
    public void setDbId(int dbId) {
        mDbId = dbId;
    }

    @Override
    public void setUID(String uid) {
        this.mUid = uid;
    }

    @Override
    public void setJsonString(String jsonString) {
    this.jsonString = jsonString;
    }

    @Override
    public void setAmount(float amount) {
        mAmount =amount;
    }

    @Override
    public void setDateString(String dateString) {

    }

    @Override
    public void setDueDateString(String dueDateString) {
        this.dueDateString = dueDateString;
    }

    @Override
    public void setLinkedContact(Contact contact) {

    }

    @Override
    public void setCategory(String categoryUid) {

    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public int getModelType() {
        return 0;
    }

    @Override
    public String getUID() {
        return null;
    }

    @Override
    public int getDbId() {
        return 0;
    }

    @Override
    public String getJsonString() {
        return jsonString;
    }

    @Override
    public float getAmount() {
        return mAmount;
    }

    @Override
    public String getDateString() {
        return null;
    }

    @Override
    public String getDueDateString() {
        return null;
    }

    @Override
    public Contact getLinkedContact() {
        return null;
    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public ContentValues getContentValues() {

        ContentValues row = new ContentValues();
        row.put(DB.UID , getUID());
        row.put(DB.NOTIFICATION_IS_RESPONDED, isResponded()?1:0);
        row.put(DB.DUE_DATE_STRING, getDueDateString());
        row.put(DB.DATE_STRING ,getDateString());
        row.put(DB.JSON_STRING ,getJsonString() );
        row.put(DB.SENDER_NUMBER,getOwnerNumber());
        row.put(DB.ITEM_TITLE,getMoneyTitle());
        row.put(DB.DATE_STRING,getMoneyDate());
        row.put(DB.NOTIFICATION_MONEY_DUE_DATE, getMoneyDueDate());
        row.put(DB.MONEY_ITEM_DESCRIPTION,getMoneyDescription());
        row.put(DB.NOTIFICATION_MESSAGE,getMessage());
        row.put(DB.NOTIFICATION_MONEY_RECIEVER,getMoneyReciever());
        row.put(DB.NOTIFICATION_MONEY_PAYER, getMoneyPayer());
        row.put(DB.NOTIFICATION_OWNER_ITEM_UID,getOwnerItemUid());
        row.put(DB.NOTIFICATION_TYPE,getType());
        row.put(DB.SENDER_NAME,getSenderName());
        row.put(DB.ITEM_OWNER_NAME,getOwnerItemUid());
        row.put(DB.AMOUNT,getAmount());

        return row;
    }

    public boolean isResponded() {
        return true;
    }

    public boolean isResponded(boolean b) {
        return isResponded;
    }

    public void setIsResponded(boolean isResponded) {
        this.isResponded = isResponded;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmDateString() {
        return mDateString;
    }

    public void setmDateString(String mDateString) {
        this.mDateString = mDateString;
    }

    public String getMoneyTitle() {
        return moneyTitle;
    }

    public void setMoneyTitle(String moneyTitle) {
        this.moneyTitle = moneyTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoneyDate() {
        return moneyDate;
    }

    public void setMoneyDate(String moneyDate) {
        this.moneyDate = moneyDate;
    }

    public String getMoneyDueDate() {
        return moneyDueDate;
    }

    public void setMoneyDueDate(String moneyDueDate) {
        this.moneyDueDate = moneyDueDate;
    }

    public String getMoneyDescription() {
        return moneyDescription;
    }

    public void setMoneyDescription(String moneyDescription) {
        this.moneyDescription = moneyDescription;
    }

    public String getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public String getMoneyReciever() {
        return moneyReciever;
    }

    public void setMoneyReciever(String moneyReciever) {
        this.moneyReciever = moneyReciever;
    }

    public String getMoneyPayer() {
        return moneyPayer;
    }

    public void setMoneyPayer(String moneyPayer) {
        this.moneyPayer = moneyPayer;
    }

    public String getOwnerItemUid() {
        return ownerItemUid;
    }

    public void setOwnerItemUid(String ownerItemUid) {
        this.ownerItemUid = ownerItemUid;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getItemOwnerName() {
        return itemOwnerName;
    }

    public void setItemOwnerName(String itemOwnerName) {
        this.itemOwnerName = itemOwnerName;
    }
}
