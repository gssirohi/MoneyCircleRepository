package company.greatapp.moneycircle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import company.greatapp.moneycircle.asynctask.UpdateAccountRegistersTask;

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
            UpdateAccountRegistersTask task = new UpdateAccountRegistersTask(context);
            task.execute(intent);
        }
    }
}
