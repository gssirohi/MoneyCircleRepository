package company.greatapp.moneycircle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Prateek on 20-07-2015.
 */
public class TransactionalSmsListener extends BroadcastReceiver {

    private String LOG_TAG = "TransactionalSmsListener";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");    // PDU is a protocol data unit,
                SmsMessage[] messages = new SmsMessage[pdusObj.length];

                String strMessage = "";
                for (int i = 0; i < pdusObj.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                    strMessage += " : ";
                    strMessage += messages[i].getMessageBody().toString();
                    strMessage += "\n";
                }
                if (messages.length > -1) {
                    //Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
                }
                isTransactionalMessages(context, messages[0].getOriginatingAddress(), strMessage);
            }
        }
    }

    /*
    Transactional Messages doesnot contain numbers
    It will contain Six character alphabetical name registered for the organization
     */
    public void isTransactionalMessages(Context context, String address, String message) {

        Log.d(LOG_TAG, "isTransactionalMessages address :" + address);

        Pattern pattern = Pattern.compile(".*\\d+.*");      // Pattern to check if the address contain numbers

        Matcher matcher = pattern.matcher(address);

        if (!matcher.matches()) {
            Toast.makeText(context, "Transactional Message", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Non Transactional Message", Toast.LENGTH_LONG).show();
        }

    }
}
