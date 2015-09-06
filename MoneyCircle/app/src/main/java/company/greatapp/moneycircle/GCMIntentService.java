/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package company.greatapp.moneycircle;



import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.manager.TransactionManager;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.RegistrationUtils;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    @SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(S.SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        User u = new User(context);
        u.updateInfo(User.U_GCM_IS_REGISTERED, ""+true);
        u.updateInfo(User.U_REGID, registrationId);
        RegistrationUtils.displayMessage(context, "Device Registered on GCM Successfully !!");
        RegistrationUtils.sendGCMRegistrationResult(context, true);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        RegistrationUtils.displayMessage(context,"Device Unregistered on GCM Successfully !!");
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            RegistrationUtils.unregisterFromAppServer(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.i(TAG, "Ignoring unregister callback");
        }
    }
//	private void notifyBroadCast(Context context, String message) {
//			    Intent intent = new Intent(C.NOTIFY_BROADCAST_ACTION);
//			    intent.putExtra("msg",message);
//			    context.sendBroadcast(intent);
//			    Log.d(TAG,"Broadcast sent for Notify");
//	}
//
    @Override
    protected void onMessage(Context context, Intent intent) {
    	User u = new User(context);
    	Log.d(TAG,"GCM MESSAGE RECEIVED");
    	if(!(u.isRegisteredOnGCM() && u.isRegisteredOnQpinion())){ // need to check proper User initialization
    		Log.i(TAG, "Received message but not registered");
    		return;
    	}
    	
        // check to see if it is a message
        if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
           TransactionManager tm = new TransactionManager(context);
           tm.handleGCM_Message(intent);
        }

    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = "GCM deleted : "+ total + " messages..!!";
        RegistrationUtils.displayMessage(context, message);
        // notifies user
        
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        RegistrationUtils.displayMessage(context, "GCM ERROR : "+errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        RegistrationUtils.displayMessage(context, "Recoverable Error : "+errorId);
        return super.onRecoverableError(context, errorId);
    }

   

}
