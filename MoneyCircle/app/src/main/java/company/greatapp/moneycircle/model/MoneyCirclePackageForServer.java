package company.greatapp.moneycircle.model;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import company.greatapp.moneycircle.constants.S;

/**
 * Created by Gyanendrasingh on 9/8/2015.
 */
public class MoneyCirclePackageForServer {


    private int maxRetryAttempt;
    private int attemptCounter;

    private String url;

    private int reqResponseType;

    private String reqDBId;
    private String transportId;
    private int reqType;   // GET OR POST

//-----------------------WILL BE USED BY SERVER TO CREATE NOTIFICATION--------------------//

    private int reqCode;

    private String reqSenderPhone;
    private String reqReceiverPhone;

    private String itemOwnerPhone;
    private String itemAssociatePhone;

    private String moneyReceiverPhone;
    private String moneyPayerPhone;

    private int ownerItemType;
    private int associateItemtype;

    private String ownerItemId;
    private String associateItemId;

    private String dateString;

    private int itemBodyJsonType;
    private String itemBodyJsonString;

    private String message;
//----------------------------------------------------------------------------------------//

    public MoneyCirclePackageForServer() {

    }

    public String getAssociateItemId() {
        return associateItemId;
    }

    public void setAssociateItemId(String associateItemId) {
        this.associateItemId = associateItemId;
    }

    public int getAssociateItemtype() {
        return associateItemtype;
    }

    public void setAssociateItemtype(int associateItemtype) {
        this.associateItemtype = associateItemtype;
    }

    public int getAttemptCounter() {
        return attemptCounter;
    }

    public void setAttemptCounter(int attemptCounter) {
        this.attemptCounter = attemptCounter;
    }

    public String getItemAssociatePhone() {
        return itemAssociatePhone;
    }

    public void setItemAssociatePhone(String itemAssociatePhone) {
        this.itemAssociatePhone = itemAssociatePhone;
    }

    public String getItemBodyJsonString() {
        return itemBodyJsonString;
    }

    public void setItemBodyJsonString(String itemBodyJsonString) {
        this.itemBodyJsonString = itemBodyJsonString;
    }

    public int getItemBodyJsonType() {
        return itemBodyJsonType;
    }

    public void setItemBodyJsonType(int itemBodyJsonType) {
        this.itemBodyJsonType = itemBodyJsonType;
    }

    public String getItemOwnerPhone() {
        return itemOwnerPhone;
    }

    public void setItemOwnerPhone(String itemOwnerPhone) {
        this.itemOwnerPhone = itemOwnerPhone;
    }

    public int getMaxRetryAttempt() {
        return maxRetryAttempt;
    }

    public void setMaxRetryAttempt(int maxRetryAttempt) {
        this.maxRetryAttempt = maxRetryAttempt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMoneyPayerPhone() {
        return moneyPayerPhone;
    }

    public void setMoneyPayerPhone(String moneyPayerPhone) {
        this.moneyPayerPhone = moneyPayerPhone;
    }

    public String getMoneyReceiverPhone() {
        return moneyReceiverPhone;
    }

    public void setMoneyReceiverPhone(String moneyReceiverPhone) {
        this.moneyReceiverPhone = moneyReceiverPhone;
    }

    public String getOwnerItemId() {
        return ownerItemId;
    }

    public void setOwnerItemId(String ownerItemId) {
        this.ownerItemId = ownerItemId;
    }

    public int getOwnerItemType() {
        return ownerItemType;
    }

    public void setOwnerItemType(int ownerItemType) {
        this.ownerItemType = ownerItemType;
    }

    public int getReqCode() {
        return reqCode;
    }

    public void setReqCode(int reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqDBId() {
        return reqDBId;
    }

    public void setReqDBId(String reqDBId) {
        this.reqDBId = reqDBId;
    }

    public String getReqReceiverPhone() {
        return reqReceiverPhone;
    }

    public void setReqReceiverPhone(String reqReceiverPhone) {
        this.reqReceiverPhone = reqReceiverPhone;
    }

    public int getReqResponseType() {
        return reqResponseType;
    }

    public void setReqResponseType(int reqResponseType) {
        this.reqResponseType = reqResponseType;
    }

    public String getReqSenderPhone() {
        return reqSenderPhone;
    }

    public void setReqSenderPhone(String reqSenderPhone) {
        this.reqSenderPhone = reqSenderPhone;
    }

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public Map<String,String> getParams() {
        Map<String, String> params = new HashMap<String, String>();

        params.put(S.TRANSPORT_REQ_CODE, ""+this.getReqCode());
        params.put(S.TRANSPORT_REQ_SENDER_PHONE,this.getReqSenderPhone());
        params.put(S.TRANSPORT_REQ_RECEIVER_PHONE,this.getReqReceiverPhone());
        params.put(S.TRANSPORT_ITEM_OWNER_PHONE,this.getItemOwnerPhone());
        params.put(S.TRANSPORT_ITEM_ASSOCIATE_PHONE,this.getItemAssociatePhone());
        params.put(S.TRANSPORT_MONEY_PAYER_PHONE,this.getMoneyPayerPhone());
        params.put(S.TRANSPORT_MONEY_RECEIVER_PHONE, this.getMoneyReceiverPhone());
        params.put(S.TRANSPORT_OWNER_ITEM_TYPE,""+this.getOwnerItemType());
        params.put(S.TRANSPORT_ASSOCIATE_ITEM_TYPE, "" + this.getAssociateItemtype());

        params.put(S.TRANSPORT_OWNER_ITEM_ID,this.getOwnerItemId());
        params.put(S.TRANSPORT_ASSOCIATE_ITEM_ID,this.getAssociateItemId());

        params.put(S.TRANSPORT_ITEM_BODY_JSON_TYPE, "" + this.getItemBodyJsonType());
        params.put(S.TRANSPORT_ITEM_BODY_JSON_STRING,this.getItemBodyJsonString());
        params.put(S.TRANSPORT_MESSAGE,this.getMessage());

        return params;
    }

    public void insertInDb(Context context) {


    }

    public void deleteFromDb() {



    }
}
