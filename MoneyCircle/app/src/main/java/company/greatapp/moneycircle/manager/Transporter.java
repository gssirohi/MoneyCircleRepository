package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.constants.States;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.OutPackage;
import company.greatapp.moneycircle.model.InPackage;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.tools.RegistrationUtils;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by Gyanendrasingh on 8/30/2015.
 */
public class Transporter {
    private static final String TAG = "Transporter";
    private static final int REQ_CODE_REGISTRATION = 1;
    private static final int REQ_CODE_SETUP_CONTACTS = 2;

    private final Context mContext;


    public Transporter(Context context) {
        mContext = context;
    }


    private StringRequest getStringRequestWithParams(String url, final int reqCode, final Map<String,String> postParams) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                handleStringResponse(response, reqCode);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if(postParams != null) {
                    params = postParams;
                }
                return params;
            }

        };
        return strReq;
    }


    public void checkRegisteredContactsInAppServer(JSONArray phoneNumberJsonArray) {

        if(phoneNumberJsonArray != null) {
            String jsonString = phoneNumberJsonArray.toString();
            String TYPE = S.VALUE_CONTENT_TYPE_JSON_ARRAY_STRING;
            Map<String, String> params = new HashMap<String, String>();
            params.put(S.KEY_CONTENT_TYPE,TYPE);
            params.put(S.KEY_JSON_ARRAY_STRING,jsonString);
            Request req = getStringRequestWithParams(S.URL_APP_SERVER_SETUP_CONTACTS, REQ_CODE_SETUP_CONTACTS,params);
            String tag = "";
            addToQueue(req, tag);
        }
    }
    public void registerUserOnAppServer(User user) {
        if(!user.isRegisteredOnGCM()){
            Log.d(TAG,"user is not registered on GCM");
            return;
        }
        JSONObject obj = user.getJsonObject();
        if(obj != null) {
            String jsonString = obj.toString();
            String TYPE = S.VALUE_CONTENT_TYPE_JSON_OBJECT_STRING;
            Map<String, String> params = new HashMap<String, String>();
            params.put(S.KEY_CONTENT_TYPE,TYPE);
            params.put(S.KEY_JSON_OBJECT_STRING,jsonString);
            Request req = getStringRequestWithParams(S.URL_APP_SERVER_REGISTRATION, REQ_CODE_REGISTRATION,params);
            String tag = "";
            addToQueue(req, tag);
        }
    }

    private void handleStringResponse(String response, int reqCode) {
         switch(reqCode) {
             case REQ_CODE_REGISTRATION:
                 if(response != null) {
                     boolean result = false;
                     Log.i(TAG, "REGISTRATION STRING RESPONSE : " + response.toString());
                     if(response.equals(S.VALUE_REGISTERED)) {
                         result = true;
                     } else {
                         Log.e(TAG,"This means ["+response+"] is not equal to ["+S.VALUE_REGISTERED+"]");
                         result = false;
                     }
                     RegistrationUtils.sendAppServerRegistrationResult(mContext,result);
                 }
                 break;
             case REQ_CODE_SETUP_CONTACTS:
                 if(response != null){
                     Log.i(TAG, "SETUP CONTACTS STRING RESPONSE : " + response.toString());
                     RegistrationUtils.sendRegisteredContactsCheckResult(mContext, response);

                 }
                 break;
         }

    }


    public void addToQueue(final Request req, final String tag) {
        // Adding request to request queue
        if(req == null) {
            Log.d(TAG,"Can not add , request is NUll");
        } else {
            final AppController controller = AppController.getInstance();
            if(controller == null) {
                Log.d(TAG,"Volley Controller is NULL");
            } else {
                goAhead(controller, req, tag);
/*                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goAhead(controller, req, tag);
                    }
                }, 2000);*/

            }

        }
    }

    private void goAhead(AppController controller, Request req, String tag) {
        controller.addToRequestQueue(req, tag);
        Log.d(TAG, "volley request is added");
    }

    public  String transportReceivedPaymentInfo(Lent lent) {
        String ownerItemId;
        String itemBodyJsonString;
        User user = new User(mContext);
        OutPackage outPackage = new OutPackage();

        //borrow.setState(States.LENT_SENDING);

        outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
        outPackage.setMaxRetryAttempt(0);
        outPackage.setAttemptCounter(0);
        outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
        outPackage.setReqType(Request.Method.POST);
        outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_RECEIVE);
        outPackage.setReqSenderPhone(user.getPhoneNumber());
        Contact thatContact  = GreatJSON.getContactFromJsonString(lent.getLinkedContactJson(),mContext);
        if(thatContact != null)
            outPackage.setReqReceiverPhone(thatContact.getPhone());
        else
            outPackage.setReqReceiverPhone(null);
        
        if(user.getPhoneNumber().equals(lent.getOwnerPhone())) {
            outPackage.setItemOwnerPhone(lent.getOwnerPhone());
            outPackage.setItemAssociatePhone(thatContact.getPhone());
            outPackage.setOwnerItemType(Model.MODEL_TYPE_LENT);
            outPackage.setAssociateItemtype(Model.MODEL_TYPE_BORROW);
            ownerItemId = lent.getUID().replaceAll("NEW","DB");
            outPackage.setOwnerItemId(ownerItemId);

            outPackage.setAssociateItemId(lent.getUID().replaceAll("NEW","DB"));
        } else {
            outPackage.setItemOwnerPhone(thatContact.getPhone());
            outPackage.setItemAssociatePhone(lent.getOwnerPhone());
            outPackage.setOwnerItemType(Model.MODEL_TYPE_BORROW);
            outPackage.setAssociateItemtype(Model.MODEL_TYPE_LENT);
            ownerItemId = lent.getUID().replaceAll("NEW","DB");
            outPackage.setOwnerItemId(ownerItemId);

            outPackage.setAssociateItemId(lent.getUID().replaceAll("NEW","DB"));
        }

        outPackage.setMoneyPayerPhone(thatContact.getPhone());
        outPackage.setMoneyReceiverPhone(user.getPhoneNumber());


        outPackage.setItemBodyJsonType(S.TRANSPORT_BODY_TYPE_JSON_OBJECT);
        JSONObject obj = GreatJSON.getJsonObjectForLent(lent);
        if(obj != null) {
            itemBodyJsonString = obj.toString();
        } else {
            Log.d("SPLIT","Error is adding itemBodyJsonString");
            itemBodyJsonString = "";
        }
        outPackage.setItemBodyJsonString(itemBodyJsonString);
        outPackage.setMessage("PAYMENT RECEIVED");


        transportPackage(outPackage);
        return outPackage.getTransportId();
    }


    public  String transportMadePaymentInfo(Borrow borrow) {
        String ownerItemId;
        String itemBodyJsonString;
        User user = new User(mContext);
        OutPackage outPackage = new OutPackage();

                //borrow.setState(States.LENT_SENDING);

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_PAY);
                outPackage.setReqSenderPhone(user.getPhoneNumber());


        Contact thatContact  = GreatJSON.getContactFromJsonString(borrow.getLinkedContactJson(),mContext);
        if(thatContact != null)
            outPackage.setReqReceiverPhone(thatContact.getPhone());
        else
            outPackage.setReqReceiverPhone(null);


        if(user.getPhoneNumber().equals(borrow.getOwnerPhone())) {
            outPackage.setItemOwnerPhone(borrow.getOwnerPhone());
            outPackage.setItemAssociatePhone(thatContact.getPhone());
            outPackage.setOwnerItemType(Model.MODEL_TYPE_BORROW);
            outPackage.setAssociateItemtype(Model.MODEL_TYPE_LENT);
            ownerItemId = borrow.getUID().replaceAll("NEW","DB");
            outPackage.setOwnerItemId(ownerItemId);

            outPackage.setAssociateItemId(borrow.getUID().replaceAll("NEW", "DB"));
        } else {

            outPackage.setItemOwnerPhone(thatContact.getPhone());
            outPackage.setItemAssociatePhone(borrow.getOwnerPhone());
            outPackage.setOwnerItemType(Model.MODEL_TYPE_LENT);
            outPackage.setAssociateItemtype(Model.MODEL_TYPE_BORROW);
            ownerItemId = borrow.getUID().replaceAll("NEW","DB");
            outPackage.setOwnerItemId(ownerItemId);

            outPackage.setAssociateItemId(borrow.getUID().replaceAll("NEW", "DB"));
        }

        outPackage.setMoneyPayerPhone(user.getPhoneNumber());
        outPackage.setMoneyReceiverPhone(thatContact.getPhone());


                outPackage.setItemBodyJsonType(S.TRANSPORT_BODY_TYPE_JSON_OBJECT);
                JSONObject obj = GreatJSON.getJsonObjectForBorrow(borrow);
                if(obj != null) {
                    itemBodyJsonString = obj.toString();
                } else {
                    Log.d("SPLIT","Error is adding itemBodyJsonString");
                    itemBodyJsonString = "";
                }
                outPackage.setItemBodyJsonString(itemBodyJsonString);
                outPackage.setMessage("PAYMENT DONE");


        transportPackage(outPackage);
        return outPackage.getTransportId();
    }


    public String transportSettleUpRequest(Contact contact) {

        User user = new User(mContext);
        OutPackage outPackage = new OutPackage();



        contact.setState(States.CONTACT_SETTLE_REQ_SENDING);

        contact.updateItemInDb(mContext);

        outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
        outPackage.setMaxRetryAttempt(0);
        outPackage.setAttemptCounter(0);
        outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
        outPackage.setReqType(Request.Method.POST);
        outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_SETTLE);
        outPackage.setReqSenderPhone(user.getPhoneNumber());
        outPackage.setReqReceiverPhone(contact.getPhone());
        outPackage.setItemOwnerPhone("NA");
        outPackage.setItemAssociatePhone("NA");
        outPackage.setMoneyPayerPhone("NA");
        outPackage.setMoneyReceiverPhone("NA");
        outPackage.setOwnerItemType(0);
        outPackage.setAssociateItemtype(0);

        outPackage.setOwnerItemId("NA");
        outPackage.setAssociateItemId("NA");

        outPackage.setItemBodyJsonType(S.TRANSPORT_BODY_TYPE_JSON_OBJECT);
        outPackage.setItemBodyJsonString("NA");
        outPackage.setMessage("SETTLE UP REQUEST");

        transportPackage(outPackage);
        return outPackage.getTransportId();
    }

    public  String transportItem(Model model, int modelType, boolean isEditedModel) {
         String ownerItemId;
         String itemBodyJsonString;
         User user = new User(mContext);
         OutPackage outPackage = new OutPackage();

        switch(modelType){

            case Model.MODEL_TYPE_LENT:

                Lent lent = (Lent)model;
                lent.setState(States.LENT_SENDING);

                lent.updateItemInDb(mContext);
                Log.d("SPLIT", "Transporting Lent[" + lent.getTitle() + "] ,state moved to ["
                        + States.getStateString(States.LENT_SENDING) + "]");
                Log.d("SPLIT", "CHECKING LENT STATE----");
                lent.printModelData();

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(isEditedModel) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_MODIFIED_LENT);
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_LENT);
                }
                outPackage.setReqSenderPhone(user.getPhoneNumber());
                outPackage.setReqReceiverPhone(lent.getLinkedContact().getPhone());
                outPackage.setItemOwnerPhone(user.getPhoneNumber());
                outPackage.setItemAssociatePhone(lent.getLinkedContact().getPhone());
                outPackage.setMoneyPayerPhone(lent.getLinkedContact().getPhone());
                outPackage.setMoneyReceiverPhone(user.getPhoneNumber());
                outPackage.setOwnerItemType(Model.MODEL_TYPE_LENT);
                outPackage.setAssociateItemtype(Model.MODEL_TYPE_BORROW);

                ownerItemId = lent.getUID().replaceAll("NEW","DB");
                outPackage.setOwnerItemId(ownerItemId);
                outPackage.setAssociateItemId("NA");

                outPackage.setItemBodyJsonType(S.TRANSPORT_BODY_TYPE_JSON_OBJECT);
                JSONObject obj = GreatJSON.getJsonObjectForLent(lent);
                if(obj != null) {
                    itemBodyJsonString = obj.toString();
                } else {
                    Log.d("SPLIT","Error is adding itemBodyJsonString");
                    itemBodyJsonString = "";
                }
                outPackage.setItemBodyJsonString(itemBodyJsonString);
                outPackage.setMessage("NA");

                break;

            case Model.MODEL_TYPE_BORROW:

                Borrow item = (Borrow)model;

                item.setState(States.BORROW_SENDING);
                item.updateItemInDb(mContext);
                Log.d("SPLIT", "Transporting Borrow[" + item.getTitle() + "] ,state moved to ["
                        + States.getStateString(States.BORROW_SENDING) + "]");
                Log.d("SPLIT", "CHECKING BORROW STATE----");
                item.printModelData();
                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(isEditedModel) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_MODIFIED_BORROW);
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_BORROW);
                }

                outPackage.setReqSenderPhone(user.getPhoneNumber());
                outPackage.setReqReceiverPhone(item.getLinkedContact().getPhone());
                outPackage.setItemOwnerPhone(user.getPhoneNumber());
                outPackage.setItemAssociatePhone(item.getLinkedContact().getPhone());
                outPackage.setMoneyPayerPhone(user.getPhoneNumber());
                outPackage.setMoneyReceiverPhone(item.getLinkedContact().getPhone());
                outPackage.setOwnerItemType(Model.MODEL_TYPE_BORROW);
                outPackage.setAssociateItemtype(Model.MODEL_TYPE_LENT);

                ownerItemId = item.getUID().replaceAll("NEW","DB");
                outPackage.setOwnerItemId(ownerItemId);
                outPackage.setAssociateItemId("NA");

                outPackage.setItemBodyJsonType(S.TRANSPORT_BODY_TYPE_JSON_OBJECT);
                JSONObject objBorrow = GreatJSON.getJsonObjectForBorrow(item);
                if(objBorrow != null) {
                    itemBodyJsonString = objBorrow.toString();
                } else {
                    Log.d("SPLIT","Error is adding itemBodyJsonString");
                    itemBodyJsonString = "";
                }
                outPackage.setItemBodyJsonString(itemBodyJsonString);
                outPackage.setMessage("NA");
                break;
        }

        transportPackage(outPackage);
        return outPackage.getTransportId();
    }

    public void transportPendingPackage(OutPackage outPackage) {

        if(outPackage == null){
            return;
        }
        Request req = getStringRequestForPackage(outPackage);
        String tag = "TRANSPORT";
        outPackage.setAttemptCounter(1);//reset attempt
        outPackage.updateItemInDb(mContext);
        addToQueue(req, tag);
    }

    private  void transportPackage(OutPackage outPackage) {
        if(outPackage == null){
            return;
        }
        Request req = getStringRequestForPackage(outPackage);
        String tag = "TRANSPORT";
        outPackage.setAttemptCounter(outPackage.getAttemptCounter() + 1);
        outPackage.insertItemInDB(mContext);
        addToQueue(req, tag);
    }

    public String transportSettleUpApproval(InPackage fromPackage,boolean approved) {

    OutPackage outPackage = new OutPackage();
        User user = new User(mContext);
        switch(fromPackage.getReqCode()) {

            case S.TRANSPORT_REQUEST_CODE_SETTLE:

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(approved) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_SETTLE_AGREE);//changed
                    outPackage.setMessage("Settle Approved");//changed
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_SETTLE_DISAGREE);//changed
                    outPackage.setMessage("Settle Disapproved");//changed
                }
                outPackage.setReqSenderPhone(user.getPhoneNumber());//changed
                outPackage.setReqReceiverPhone(fromPackage.getReqSenderPhone());//changed
                outPackage.setItemOwnerPhone(fromPackage.getItemOwnerPhone());
                outPackage.setItemAssociatePhone(fromPackage.getItemAssociatePhone());
                outPackage.setMoneyPayerPhone(fromPackage.getMoneyPayerPhone());
                outPackage.setMoneyReceiverPhone(fromPackage.getMoneyReceiverPhone());
                outPackage.setOwnerItemType(fromPackage.getOwnerItemType());
                outPackage.setAssociateItemtype(fromPackage.getAssociateItemtype());


                outPackage.setOwnerItemId(fromPackage.getOwnerItemId());

                outPackage.setAssociateItemId(fromPackage.getAssociateItemId());//changed

                outPackage.setItemBodyJsonType(fromPackage.getItemBodyJsonType());

                outPackage.setItemBodyJsonString(fromPackage.getItemBodyJsonString());


                break;

        }

        transportPackage(outPackage);
        return outPackage.getTransportId();
    }

    private StringRequest getStringRequestForPackage(final OutPackage outPackage) {
        StringRequest strReq = new StringRequest(outPackage.getReqType(),
                outPackage.getUrl(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                handlePackageResponse(response, outPackage);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                handlePackageError(error,outPackage);
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if(outPackage != null) {
                    params = outPackage.getParams();
                }
                return params;
            }

        };
        return strReq;
    }

    private void handlePackageError(VolleyError error, OutPackage outPackage) {
        // deal with error details

        if(outPackage.getMaxRetryAttempt() - outPackage.getAttemptCounter() >= 0){
            transportPackage(outPackage);
        } else {
            Log.e("SPLIT","Package Sending ALL ATTEMPT FAILED");
            handleNotSentPackageItemState(outPackage);
        }
    }


    private void handlePackageResponse(String response, OutPackage outPackage) {
        outPackage.deleteItemInDb(mContext);

        handleSentPackageItemState(outPackage);
    }

    private void handleNotSentPackageItemState(OutPackage outPackage) {

        String lentUid = "";
        String borrowUid = "";
        String contactPhone = "";
        Contact contact;
        Lent lent;
        Borrow borrow;
        switch(outPackage.getReqCode()) {

            case S.TRANSPORT_REQUEST_CODE_LENT:


                lentUid = outPackage.getOwnerItemId();
                lent = (Lent)Tools.getDbInstance(mContext,lentUid,Model.MODEL_TYPE_LENT);
                lent.setState(States.LENT_NOT_SENT);
                lent.updateItemInDb(mContext);
                Log.d("SPLIT", "Lent[" + lent.getTitle() + "] is NOT SENT, moved to ["
                        + States.getStateString(States.LENT_NOT_SENT) + "]");
                Tools.sendTransactionBroadCast(mContext, lent, Model.MODEL_TYPE_LENT);

                break;
            case S.TRANSPORT_REQUEST_CODE_BORROW:

                borrowUid = outPackage.getOwnerItemId();
                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid,Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_NOT_SENT);
                borrow.updateItemInDb(mContext);
                Log.d("SPLIT", "Borrow[" + borrow.getTitle() + "] is NOT SENT, moved to ["
                        + States.getStateString(States.BORROW_NOT_SENT) + "]");
                Tools.sendTransactionBroadCast(mContext, borrow, Model.MODEL_TYPE_BORROW);
                break;

            case S.TRANSPORT_REQUEST_CODE_SETTLE:

                contactPhone = outPackage.getReqReceiverPhone();
                contact = Tools.getContactFromPhoneNumber(mContext,contactPhone);
                contact.setState(States.CONTACT_SETTLE_REQ_NOT_SENT);
                contact.updateItemInDb(mContext);
                break;
        }
    }

    private void handleSentPackageItemState(OutPackage outPackage) {

        String lentUid = "";
        String borrowUid = "";
        Lent lent;
        Borrow borrow;
        String contactPhone = "";
        Contact contact;

        switch(outPackage.getReqCode()) {

            case S.TRANSPORT_REQUEST_CODE_LENT:

                lentUid = outPackage.getOwnerItemId();
                lent = (Lent)Tools.getDbInstance(mContext,lentUid,Model.MODEL_TYPE_LENT);
                lent.setState(States.LENT_WAITING_FOR_PAYMENT);
                lent.updateItemInDb(mContext);
                Log.d("SPLIT", "Lent[" + lent.getTitle() + "] is SENT, moved to ["
                        + States.getStateString(States.LENT_WAITING_FOR_PAYMENT) + "]");
                Tools.sendTransactionBroadCast(mContext,lent, Model.MODEL_TYPE_LENT);
                break;
            case S.TRANSPORT_REQUEST_CODE_BORROW:

                borrowUid = outPackage.getOwnerItemId();
                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid,Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_PAYMENT_PENDING);
                borrow.updateItemInDb(mContext);
                Log.d("SPLIT", "Borrow[" + borrow.getTitle() + "] is SENT, moved to ["
                        + States.getStateString(States.BORROW_PAYMENT_PENDING) + "]");
                Tools.sendTransactionBroadCast(mContext, borrow, Model.MODEL_TYPE_BORROW);
                break;

            case S.TRANSPORT_REQUEST_CODE_SETTLE:

                contactPhone = outPackage.getReqReceiverPhone();
                contact = Tools.getContactFromPhoneNumber(mContext,contactPhone);
                contact.setState(States.CONTACT_WAITING_FOR_SETTLE_APPROVAL);
                contact.updateItemInDb(mContext);
                break;

        }

    }


}
