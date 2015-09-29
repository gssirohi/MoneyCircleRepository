package company.greatapp.moneycircle.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.Accountant;

/**
 * Created by gyanendra on 30/9/15.
 */
public class AccountantService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AccountantService(String name) {
        super(name);
    }


    public AccountantService() {
        super("");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("TAG","Accountant service started..");
        Accountant accountant = new Accountant(getBaseContext(),true);
        accountant.updateAllRegistersInDb(intent);
        Log.d("TAG", "Accountant service finished");

    }
}
