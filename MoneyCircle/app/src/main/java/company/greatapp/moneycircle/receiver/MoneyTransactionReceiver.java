package company.greatapp.moneycircle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import company.greatapp.moneycircle.asynctask.UpdateAccountRegistersTask;
import company.greatapp.moneycircle.services.AccountantService;
import company.greatapp.moneycircle.services.PendingPackageTransportService;

/**
 * Created by Gyanendrasingh on 8/21/2015.
 */
public class MoneyTransactionReceiver extends BroadcastReceiver{

    public static final String ACTION_MONEY_TRANSACTION = "MONEY_TRANSACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(context == null || intent == null) return;

        String action = intent.getAction();

        if(action.equals(MoneyTransactionReceiver.ACTION_MONEY_TRANSACTION)) {

//            UpdateAccountRegistersTask task = new UpdateAccountRegistersTask(context);
//            task.execute(intent);
            Log.d("TAG","Trnsaction Receiver intent received");

            String last_json = intent.getStringExtra(UpdateAccountRegistersTask.LAST_TRANSACTION_JSON);
            int transaction_model = intent.getIntExtra(UpdateAccountRegistersTask.TRANSACTION_TYPE,0);


            Intent serviceIntent = new Intent(context, AccountantService.class);
            serviceIntent.putExtra(UpdateAccountRegistersTask.LAST_TRANSACTION_JSON,last_json);
            serviceIntent.putExtra(UpdateAccountRegistersTask.TRANSACTION_TYPE,transaction_model);
            context.startService(serviceIntent);
        }
    }
}
