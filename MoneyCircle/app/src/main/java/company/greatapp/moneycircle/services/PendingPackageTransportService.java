package company.greatapp.moneycircle.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.manager.OutPackageManager;
import company.greatapp.moneycircle.manager.Transporter;
import company.greatapp.moneycircle.model.MoneyCirclePackageForServer;

/**
 * Created by gyanendra on 14/9/15.
 */
public class PendingPackageTransportService extends IntentService{


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PendingPackageTransportService(String name) {
        super(name);
    }


    public PendingPackageTransportService() {
        super("");

    }
    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("SPLIT","PendingPackage Service STARTED");

        OutPackageManager outPackageManager = new OutPackageManager(getBaseContext());
        ArrayList<MoneyCirclePackageForServer> pendingList = outPackageManager.getPendingItems();
        Transporter transporter = new Transporter(getBaseContext());
        Log.d("SPLIT","Total Pending package : " + pendingList.size());

        int counter = 0;
        for(MoneyCirclePackageForServer outPackage : pendingList){
            Log.d("SPLIT","Service is Transpoting pending package ["+ (++counter) +"]");
            transporter.transportPendingPackage(outPackage);
        }
    }
}
