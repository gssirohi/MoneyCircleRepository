package company.greatapp.moneycircle.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Objects;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.Accountant;
import company.greatapp.moneycircle.split.SetSplitAmountActivity;

/**
 * Created by Gyanendrasingh on 8/21/2015.
 */
public class UpdateAccountRegistersTask extends AsyncTask<Void,Void,String> {

    private final Context mContext;

    public UpdateAccountRegistersTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        Accountant accountant = new Accountant(mContext,false);
        accountant.updateAllRegistersInDb();
        return null;
    }

    @Override
    protected void onPostExecute(String object) {
        Intent intent = new Intent(C.ACTION_ACCOUNTANT_DB_UPDATED);
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        mContext.sendBroadcast(intent);

    }
}
