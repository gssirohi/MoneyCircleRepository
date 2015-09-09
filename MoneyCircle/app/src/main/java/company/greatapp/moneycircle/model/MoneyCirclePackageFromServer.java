package company.greatapp.moneycircle.model;

import android.net.Uri;

/**
 * Created by Prateek on 09-09-2015.
 */
public class MoneyCirclePackageFromServer {

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

}
