package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.model.OutPackage;

/**
 * Created by Prateek on 11-09-2015.
 */
public class OutPackageManager {

    private final Context mContext;
    private ArrayList<OutPackage> mPendingList = new ArrayList<OutPackage>();

    public OutPackageManager(Context context) {
        mContext = context;
        loadItemsFromDB();
    }

    public ArrayList<OutPackage> getPendingItems() {
        return mPendingList;
    }

    public static OutPackage createOutPackageFromCursor(Cursor c) {
        OutPackage out = new OutPackage();

        out.setTransportId(c.getString(c.getColumnIndex(DB.UID)));
        out.setItemOwnerPhone(c.getString(c.getColumnIndex(S.TRANSPORT_ITEM_OWNER_PHONE)));
        out.setItemAssociatePhone(c.getString(c.getColumnIndex(S.TRANSPORT_ITEM_ASSOCIATE_PHONE)));
        out.setMaxRetryAttempt(c.getInt(c.getColumnIndex(DB.MAX_RETRY_ATTEMPT)));
        out.setAttemptCounter(c.getInt(c.getColumnIndex(DB.ATTEMPT_COUNTER)));
        out.setUrl(c.getString(c.getColumnIndex(DB.URL)));
        out.setReqResponseType(c.getInt(c.getColumnIndex(DB.REQ_RESPONSE_TYPE)));
        out.setReqType(c.getInt(c.getColumnIndex(DB.REQ_TYPE)));
        out.setReqCode(c.getInt(c.getColumnIndex(S.TRANSPORT_REQ_CODE)));
        out.setReqSenderPhone(c.getString(c.getColumnIndex(S.TRANSPORT_REQ_SENDER_PHONE)));
        out.setReqReceiverPhone(c.getString(c.getColumnIndex(S.TRANSPORT_REQ_RECEIVER_PHONE)));
        out.setMoneyPayerPhone(c.getString(c.getColumnIndex(S.TRANSPORT_MONEY_PAYER_PHONE)));
        out.setMoneyReceiverPhone(c.getString(c.getColumnIndex(S.TRANSPORT_MONEY_RECEIVER_PHONE)));
        out.setOwnerItemType(c.getInt(c.getColumnIndex(S.TRANSPORT_OWNER_ITEM_TYPE)));
        out.setAssociateItemtype(c.getInt(c.getColumnIndex(S.TRANSPORT_ASSOCIATE_ITEM_TYPE)));
        out.setOwnerItemId(c.getString(c.getColumnIndex(S.TRANSPORT_OWNER_ITEM_ID)));
        out.setAssociateItemId(c.getString(c.getColumnIndex(S.TRANSPORT_ASSOCIATE_ITEM_ID)));
        out.setItemBodyJsonType(c.getInt(c.getColumnIndex(S.TRANSPORT_ITEM_BODY_JSON_TYPE)));
        out.setItemBodyJsonString(c.getString(c.getColumnIndex(S.TRANSPORT_ITEM_BODY_JSON_STRING)));
        out.setMessage(c.getString(c.getColumnIndex(S.TRANSPORT_MESSAGE)));
        return out;
    }



    protected void loadItemsFromDB() {
        mPendingList.clear();
        Cursor c = mContext.getContentResolver().query(DB.PACKAGE_FORSERVER_TABLE_URI,
                DB.PACKAGE_FORSERVER_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                OutPackage outPackage =  createOutPackageFromCursor(c);
                if(outPackage != null) {
                    Log.d("SPLIT", "LOADING outPackage[" + outPackage.getReqCode() + "] : " + outPackage.getTransportId());
                }
                mPendingList.add(outPackage);
                c.moveToNext();
            }
            c.close();
        }
    }

}
