package company.greatapp.moneycircle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import company.greatapp.moneycircle.NotificationHandler;
import company.greatapp.moneycircle.model.TransactionalMessage;
import company.greatapp.moneycircle.tools.DateUtils;
import company.greatapp.moneycircle.tools.GreatJSON;

/**
 * Created by Prateek on 20-07-2015.
 */
public class TransactionalSmsListener extends BroadcastReceiver {

    private String LOG_TAG = "TransactionalSmsListener";

    private Context mContext;

    private String[] merchantList = new String[]{"hdfc","icici","citi","sbi","kotak","obc","union","induslnd"};

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive");
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();

            mContext = context;

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
                    isTransactionalMessages(messages[0].getOriginatingAddress(), strMessage);
                }

            }
        }
    }

    /*
    Transactional Messages doesnot contain numbers
    It will contain Six character alphabetical name registered for the organization
     */
    public void isTransactionalMessages(String address, String message) {

        Log.d(LOG_TAG, "isTransactionalMessages address :" + address);

        Pattern pattern = Pattern.compile(".*\\d+.*");      // Pattern to check if the address contain numbers

        Matcher matcher = pattern.matcher(address);

        if (matcher.matches()) {
            Toast.makeText(mContext, "Non Transactional Message", Toast.LENGTH_LONG).show();
        } else {

            boolean isBankMessage = false;
            for (int i = 0; i < merchantList.length; i++) {
                if (address.toLowerCase().contains(merchantList[i])) {
                    isBankMessage = true;
                    parseMessage(i, message);
                }
            }

            if(!isBankMessage) {
                Toast.makeText(mContext, "Not Bank Messgae", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void parseMessage(int index, String message) {

        Log.d(LOG_TAG, "Message :" + message);

        float amount = 0;
        String where = null;
        String merchant = null;
        String transactionMode = null;
        StringBuilder notificationMessage = null;
        TransactionalMessage messageItem = null;
        if (message.toLowerCase().contains("was spent") || message.toLowerCase().contains("using credit card") || message.toLowerCase().contains("has been debited") || message.toLowerCase().contains("txn on")) {

            amount = getParsedAmount(message);

            if (amount > 0) {

                messageItem = new TransactionalMessage(TransactionalMessage.MESSAGE_TYPE_EXPENSE);
                notificationMessage = new StringBuilder();

                notificationMessage.append("Rs " + amount);
                messageItem.setAmount(amount);

                merchant = merchantList[index].toUpperCase();
                messageItem.setMerchant(merchant);

                where = getOutlet(message);

                if (where != null) {
                    Log.d(LOG_TAG, "where :" + where);
                    notificationMessage.append(" spent at " + where + " through ");
                    messageItem.setOutlet(where);
                } else {
                    notificationMessage.append("debited from ");
//                    bundle.putString(DB.ITEM_TITLE, "Online Transaction by" + merchant);
                }

                notificationMessage.append(merchant);

                transactionMode = getTransactionMode(message);
                if (transactionMode != null) {
                    Log.d(LOG_TAG, "transactionMode :" + transactionMode);
                    notificationMessage.append(" "+ transactionMode);
                    messageItem.setTransactionMode(transactionMode);
                }

                Toast.makeText(mContext, notificationMessage.toString(), Toast.LENGTH_LONG).show();

                messageItem.setDescription(notificationMessage.toString());
                messageItem.setDescription(message);

                messageItem.setDateString(DateUtils.getCurrentDate());

                String itemBodyJsonString = null;
                JSONObject obj = GreatJSON.getJSONObjectForTransactionalMessage(messageItem);
                if(obj != null) {
                    itemBodyJsonString = obj.toString();
                } else {
                    Log.d(LOG_TAG, "Error is adding itemBodyJsonString");
                    itemBodyJsonString = "";
                }
                messageItem.setJsonString(itemBodyJsonString);

                NotificationHandler notificationHandler = new NotificationHandler(mContext);
                notificationHandler.handleTransactionMessage(messageItem);
            } else {
                Toast.makeText(mContext, merchantList[index] + "Amount Non-Transactional Message", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(mContext, merchantList[index] + " Non-Transactional Message", Toast.LENGTH_LONG).show();
        }

    }

    public float getParsedAmount(String message) {

        String amountExpression = "[\\s\\.]\\d+(\\.\\d{2})?";
        Pattern amountPattern = Pattern.compile(amountExpression, Pattern.CASE_INSENSITIVE);

        Matcher matcher = amountPattern.matcher(message);

        if (matcher.find()) {
            String amount = matcher.group();
            if (amount.charAt(0)=='.') {
                amount = amount.substring(1,amount.length());
            }
            Log.d(LOG_TAG, "amount :" + amount);
            return Float.parseFloat(amount);
        }

        return 0;
    }

    public String getOutlet(String message) {

        int indexOfAt = message.indexOf(" at ");
        String where = null;
        String patternString = "[A-Z\\s]+";
        if (indexOfAt != -1) {
            int indexOfFullStop = message.indexOf(".", indexOfAt);
//            int indexOfOn = message.toLowerCase().indexOf(" on ", indexOfAt);

            String subString = null;
            if (indexOfFullStop != -1) {
                subString = message.substring(indexOfAt + 4, indexOfFullStop);
            } else {
                subString = message.substring(indexOfAt + 4);
            }

            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(subString);

            if (matcher.find()) {
                where = matcher.group();
            }

            /*if (indexOfFullStop == -1 && indexOfOn > -1) {
                where = message.substring(indexOfAt+4, indexOfOn);
            } else if(indexOfOn == -1 && indexOfFullStop > -1) {
                where = message.substring(indexOfAt+4, indexOfFullStop);
            } else if (indexOfFullStop < indexOfOn) {
                where = message.substring(indexOfAt+4, indexOfFullStop);
            } else if (indexOfOn < indexOfFullStop) {
                where = message.substring(indexOfAt+4, indexOfOn);
            } else {
                int indexOfNextSpcae = message.indexOf(" ", indexOfAt+4);
                if (indexOfNextSpcae > -1) {
                    where = message.substring(indexOfAt+4, indexOfNextSpcae);
                }
            }*/
        }
        return where;
    }

    public String getTransactionMode(String message) {

        String transactionMode = null;
        if (message.toLowerCase().contains("credit card")) {
            transactionMode = "Credit Card";
        } else if (message.toLowerCase().contains("netbanking")) {
            transactionMode = "NetBanking";
        }

        return transactionMode;
    }
}
