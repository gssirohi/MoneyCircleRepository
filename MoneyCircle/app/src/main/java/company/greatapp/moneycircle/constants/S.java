package company.greatapp.moneycircle.constants;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gyanendrasingh on 8/31/2015.
 */
public class S {
    public static final String EMAIL_ID = "email";
    public static final String REG_ID = "regId";
    public static final String MSG = "msg";
    public static final String FROM = "comingfrom";
    public static final String EMAIL_TO = "emailTo";
    public static final String USER_NAME = "userName";
    public static final String PHONE_NUMBER = "ph";
    public static final String GENDER = "gender";
    public static final String AVATOR = "avator";



    public static final String VALUE_CONTENT_TYPE_JSON_OBJECT_STRING ="jsonObjectString";
    public static final String VALUE_CONTENT_TYPE_JSON_ARRAY_STRING ="jsonArrayString";

    public static final String VALUE_NOT_REGISTERED = "NotRegisteredUser";
    public static final short RESPONSE_TYPE_STRING = 12;



    public static String VALUE_REGISTERED = "RegisteredUser";



    public static final String KEY_GCM_REGISTRATION = "KeyGCMregist";
    public static final String KEY_APP_SERVER_REGISTRATION = "KeyGCMregist";

    public static final String KEY_CONTENT_TYPE ="keyContentType";
    public static final String KEY_JSON_OBJECT_STRING ="keyJsonObjectString";
    public static final String KEY_JSON_ARRAY_STRING ="keyJsonArrayString";
    public static final String KEY_REGISTERED_CONTACTS_RESULT ="keyregisteredContactJson";

    public static final String ACTION_GCM_REGISTRATION_RESULT ="actionGCMRegistration";
    public static final String ACTION_APP_SERVER_REGISTRATION_RESULT ="actionServerRegistration";
    public static final String ACTION_CHECK_REGISTERED_CONTACTS_RESULT ="actionCheckContacts";

    public static final String URL_APP_SERVER_REGISTRATION = "http://1-dot-gyan-moneycircle-1727.appspot.com/register";
    public static final String URL_APP_SERVER_SETUP_CONTACTS = "http://1-dot-gyan-moneycircle-1727.appspot.com/setupcontacts";
    public static final String URL_APP_SERVER_TRANSPORT_PACKAGE = "http://1-dot-gyan-moneycircle-1727.appspot.com/transport";


    public static final String SENDER_ID = "470422662474";  // project id app engine

    public static final String NOTIFICATION_TYPE = "notification_type";
    public static final String NOTIFICATION_JSONSTRING = "notification_jsonstring";


    public static final int NOTIFICATION_INFORMATION = 10;

    public static final int NOTIFICATION_REMINDER_REQUEST = 11;

    // Pay
    public static final int NOTIFICATION_PAY_REQUEST = 12;
    public static final int NOTIFICATION_AGREE_PAY = 13;

    // Settle
    public static final int NOTIFICATION_SETTLE_REQUEST = 14;
    public static final int NOTIFICATION_AGREE_SETTLE = 15;

    // Receive
    public static final int NOTIFICATION_RECEIVE_REQUEST = 16;
    public static final int NOTIFICATION_AGREE_RECEIVE = 17;

    // Lent
    public static final int NOTIFICATION_LENT_REQUEST = 18;
    public static final int NOTIFICATION_MODIFY_lENT_REQUEST = 19;
    public static final int NOTIFICATION_AGREE_LENT = 20;
    public static final int NOTIFICATION_DISAGREE_LENT = 21;
    public static final int NOTIFICATION_DELETE_LENT_REQUEST = 22;

    // Borrow
    public static final int NOTIFICATION_BORROW_REQUEST = 23;
    public static final int NOTIFICATION_MODIFY_BORROW_REQUEST = 24;
    public static final int NOTIFICATION_AGREE_BORROW = 25;
    public static final int NOTIFICATION_DISAGREE_BORROW = 26;
    public static final int NOTIFICATION_DELETE_BORROW_REQUEST = 27;


    //--------------------------------------TRANSPORT----------------------------------------//
    public static final String TRANSPORT_REQ_CODE = "transReqCode";
    public static final String TRANSPORT_REQ_SENDER_PHONE = "transSenderPhone";
    public static final String TRANSPORT_REQ_RECEIVER_PHONE = "transReceiverPhone";
    public static final String TRANSPORT_ITEM_OWNER_PHONE = "transItemOwnerPhone";
    public static final String TRANSPORT_ITEM_ASSOCIATE_PHONE = "transItemAssoPhone";
    public static final String TRANSPORT_MONEY_PAYER_PHONE = "transMoneyPayPhone";
    public static final String TRANSPORT_MONEY_RECEIVER_PHONE = "transMoneyRecPhone";
    public static final String TRANSPORT_OWNER_ITEM_TYPE = "transOwnerItemType";
    public static final String TRANSPORT_ASSOCIATE_ITEM_TYPE = "transAssoItemType";
    public static final String TRANSPORT_OWNER_ITEM_ID = "transOwnerItemId";
    public static final String TRANSPORT_ASSOCIATE_ITEM_ID = "transAssoItemId";
    public static final String TRANSPORT_ITEM_BODY_JSON_TYPE = "transItemBodyjsonType";
    public static final String TRANSPORT_ITEM_BODY_JSON_STRING = "transItemBodyJsonString";
    public static final String TRANSPORT_MESSAGE = "tranMessage";

    public static final int TRANSPORT_BODY_TYPE_JSON_OBJECT = 1;

    public static final int TRANSPORT_REQUEST_CODE_LENT = 11;
    public static final short TRANSPORT_REQUEST_CODE_BORROW = 22;


    //---------------------------------------------------------------------------------------//



}