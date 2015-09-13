package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.constants.States;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.MoneyCirclePackageForServer;
import company.greatapp.moneycircle.model.MoneyCirclePackageFromServer;
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
    private JsonObjectRequest getJsonObjectRequest(String url,final int reqCode) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        handleJsonObjectResponse(response, reqCode);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       VolleyLog.d(TAG, "Error: " + error.getMessage());
                      // hide the progress dialog
                    }
        });

       return jsonObjReq;
    }

    private JsonArrayRequest getJsonArrayRequest(String url,final int reqCode) {
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                       handleJsonArrayResponse(response, reqCode);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        return req;
    }

    private StringRequest getStringRequest(String url, final int reqCode) {
        StringRequest strReq = new StringRequest(Request.Method.GET,
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
        });
        return strReq;
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

    private JsonObjectRequest getJsonObjectRequestWithPostParams(String url, final int reqCode, final Map<String,String> postParams) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        handleJsonObjectResponse(response, reqCode);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if(postParams != null) {
                    params = postParams;
                }
                return params;
            }

        };
        return jsonObjReq;
    }


    private JsonObjectRequest getJsonObjectRequestWithJsonBody(String url, final int reqCode, JSONObject jsonObj) {
        Log.d(TAG, "Request json : "+jsonObj.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObj,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        handleJsonObjectResponse(response, reqCode);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) ;
        return jsonObjReq;
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
    private void handleJsonArrayResponse(JSONArray response, int reqCode) {
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

    private void handleJsonObjectResponse(JSONObject response, int reqCode) {
        switch(reqCode) {
            case REQ_CODE_REGISTRATION:

                break;
        }
    }

    public void addToQueue(Request req,String tag) {
        // Adding request to request queue
        if(req == null) {
            Log.d(TAG,"Can not add , request is NUll");
        } else {
            AppController controller = AppController.getInstance();
            if(controller == null) {
                Log.d(TAG,"Volley Controller is NULL");
            } else {
                controller.addToRequestQueue(req, tag);
                Log.d(TAG, "volley request is added");
            }

        }
    }
    private void fireVolleyResponseSignal(String action) {
        Intent i = new Intent(action);
        mContext.sendBroadcast(i);
    }


    public  String transportItem(Model model, int modelType) {
         String ownerItemId;
         String itemBodyJsonString;
         User user = new User(mContext);
         MoneyCirclePackageForServer outPackage = new MoneyCirclePackageForServer();

        switch(modelType){

            case Model.MODEL_TYPE_LENT:

                Lent lent = (Lent)model;
                lent.setState(States.LENT_SENDING);
                lent.updateItemInDb(mContext);

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_LENT);
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

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_BORROW);
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

    private  void transportPackage(MoneyCirclePackageForServer outPackage) {
        if(outPackage == null){
            return;
        }
        Request req = getStringRequestForPackage(outPackage);
        String tag = "TRANSPORT";
        outPackage.setAttemptCounter(outPackage.getAttemptCounter() + 1);
        outPackage.insertInDb(mContext);
        addToQueue(req, tag);
    }

    public String transportPackageApproval(MoneyCirclePackageFromServer fromPackage,boolean approved) {

    MoneyCirclePackageForServer outPackage = new MoneyCirclePackageForServer();
        User user = new User(mContext);
        switch(fromPackage.getReqCode()) {

            case S.TRANSPORT_REQUEST_CODE_LENT:

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(approved) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_AGREE_LENT);//changed
                    outPackage.setMessage("Lent Approved");//changed
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_DISAGREE_LENT);//changed
                    outPackage.setMessage("Lent Disapproved");//changed
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

            case S.TRANSPORT_REQUEST_CODE_BORROW:

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(approved) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_AGREE_BORROW);//changed
                    outPackage.setMessage("Borrow Approved");//changed
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_DISAGREE_BORROW);//changed
                    outPackage.setMessage("Borrow Disapproved");//changed
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

            case S.TRANSPORT_REQUEST_CODE_PAY:

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(approved) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_AGREE_PAY);//changed
                    outPackage.setMessage("Payment Approved");//changed
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_DISAGREE_PAY);//changed
                    outPackage.setMessage("Payment Disapproved");//changed
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

            case S.TRANSPORT_REQUEST_CODE_SETTLE:

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(approved) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_AGREE_SETTLE);//changed
                    outPackage.setMessage("Settle Approved");//changed
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_DISAGREE_SETTLE);//changed
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

            case S.TRANSPORT_REQUEST_CODE_MODIFY_BORROW:

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(approved) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_AGREE_MODIFY);//changed
                    outPackage.setMessage("Modify Borrow request Approved");//changed
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_DISAGREE_MODIFY);//changed
                    outPackage.setMessage("Modify Borrow request Disapproved");//changed
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
            case S.TRANSPORT_REQUEST_CODE_MODIFY_LENT:

                outPackage.setUrl(S.URL_APP_SERVER_TRANSPORT_PACKAGE);
                outPackage.setMaxRetryAttempt(0);
                outPackage.setAttemptCounter(0);
                outPackage.setReqResponseType(S.RESPONSE_TYPE_STRING);
                outPackage.setReqType(Request.Method.POST);
                if(approved) {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_AGREE_MODIFY);//changed
                    outPackage.setMessage("Modify Lent request Approved");//changed
                } else {
                    outPackage.setReqCode(S.TRANSPORT_REQUEST_CODE_DISAGREE_MODIFY);//changed
                    outPackage.setMessage("Modify Lent request Disapproved");//changed
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

    private StringRequest getStringRequestForPackage(final MoneyCirclePackageForServer outPackage) {
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

    private void handlePackageError(VolleyError error, MoneyCirclePackageForServer outPackage) {
        // deal with error details

        if(outPackage.getMaxRetryAttempt() - outPackage.getAttemptCounter() >= 0){
            transportPackage(outPackage);
        } else {
            Log.e("SPLIT","Package Sending ALL ATTEMPT FAILED");
            handleNotSentPackageItemState(outPackage);
        }
    }


    private void handlePackageResponse(String response, MoneyCirclePackageForServer outPackage) {
        outPackage.deleteFromDb(mContext);
        handleSentPackageItemState(outPackage);
    }

    private void handleNotSentPackageItemState(MoneyCirclePackageForServer outPackage) {

        String lentUid = "";
        String borrowUid = "";
        Lent lent;
        Borrow borrow;
        switch(outPackage.getReqCode()) {

            case S.TRANSPORT_REQUEST_CODE_LENT:

                lentUid = outPackage.getOwnerItemId();
                lent = (Lent)Tools.getDbInstance(mContext,lentUid,Model.MODEL_TYPE_LENT);
                lent.setState(States.LENT_NOT_SENT);
                lent.updateItemInDb(mContext);
                break;
            case S.TRANSPORT_REQUEST_CODE_BORROW:

                borrowUid = outPackage.getOwnerItemId();
                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid,Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_NOT_SENT);
                borrow.updateItemInDb(mContext);
                break;


        }
    }

    private void handleSentPackageItemState(MoneyCirclePackageForServer outPackage) {

        String lentUid = "";
        String borrowUid = "";
        Lent lent;
        Borrow borrow;
        switch(outPackage.getReqCode()) {

            case S.TRANSPORT_REQUEST_CODE_LENT:

                lentUid = outPackage.getOwnerItemId();
                lent = (Lent)Tools.getDbInstance(mContext,lentUid,Model.MODEL_TYPE_LENT);
                lent.setState(States.LENT_APPROVAL_PENDING);
                lent.updateItemInDb(mContext);
                break;
            case S.TRANSPORT_REQUEST_CODE_BORROW:

                borrowUid = outPackage.getOwnerItemId();
                borrow = (Borrow)Tools.getDbInstance(mContext,borrowUid,Model.MODEL_TYPE_BORROW);
                borrow.setState(States.BORROW_APPROVAL_PENDING);
                borrow.updateItemInDb(mContext);
                break;


        }
    }


}
