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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.RegistrationUtils;
import company.greatapp.moneycircle.tools.RegistrationUtils;

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
                        handleJsonObjectResponse(response,reqCode);
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


}
