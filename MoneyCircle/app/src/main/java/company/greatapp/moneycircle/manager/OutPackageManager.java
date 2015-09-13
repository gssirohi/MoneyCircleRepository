package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.model.MoneyCirclePackageForServer;

/**
 * Created by Prateek on 11-09-2015.
 */
public class OutPackageManager {

    Context context;
    ArrayList<MoneyCirclePackageForServer> mPendingOutPackageList = new ArrayList<MoneyCirclePackageForServer>();

    public MoneyCirclePackageForServer createHeavyItemFromCursor(Cursor cursor) {
        return null;
    }

    public MoneyCirclePackageForServer createItemFromIntent(Intent intent) {
        return null;
    }

    public static MoneyCirclePackageForServer createLightItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        /*contentValues.put(S.TRANSPORT_ITEM_OWNER_PHONE, getItemOwnerPhone());
        contentValues.put(S., getItemAssociatePhone());
        contentValues.put(DB.MAX_RETRY_ATTEMPT, getMaxRetryAttempt());
        contentValues.put(DB.ATTEMPT_COUNTER, getAttemptCounter());
        contentValues.put(DB.URL, getUrl());
        contentValues.put(DB.REQ_RESPONSE_TYPE, getReqResponseType());
        contentValues.put(DB.REQ_TYPE, getReqType());
        contentValues.put(S.TRANSPORT_REQ_CODE, getReqCode());
        contentValues.put(S.TRANSPORT_REQ_SENDER_PHONE, getReqSenderPhone());
        contentValues.put(S.TRANSPORT_REQ_RECEIVER_PHONE, getReqReceiverPhone());
        contentValues.put(S.TRANSPORT_MONEY_RECEIVER_PHONE, getMoneyReceiverPhone());
        contentValues.put(S.TRANSPORT_MONEY_PAYER_PHONE, getMoneyPayerPhone());
        contentValues.put(S.TRANSPORT_OWNER_ITEM_TYPE, getOwnerItemType());
        contentValues.put(S.TRANSPORT_ASSOCIATE_ITEM_TYPE, getAssociateItemtype());
        contentValues.put(S.TRANSPORT_OWNER_ITEM_ID, getOwnerItemId());
        contentValues.put(S.TRANSPORT_ASSOCIATE_ITEM_ID, getAssociateItemId());
        contentValues.put(S.TRANSPORT_ITEM_BODY_JSON_TYPE, getItemBodyJsonType());
        contentValues.put(S.TRANSPORT_ITEM_BODY_JSON_STRING, getItemBodyJsonString());
        contentValues.put(S.TRANSPORT_MESSAGE, getMessage());*/
        int reqDbId               = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String transportId        = cursor.getString(cursor.getColumnIndex(DB.UID));
        String itemOwnerPhone        = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_ITEM_OWNER_PHONE));
        String itemAssociatePhone        = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_ITEM_ASSOCIATE_PHONE));
        int maxAttempt         =cursor.getInt(cursor.getColumnIndex(DB.MAX_RETRY_ATTEMPT));
        int attemptCounter         =cursor.getInt(cursor.getColumnIndex(DB.ATTEMPT_COUNTER));
        String url            = cursor.getString(cursor.getColumnIndex(DB.URL));
        int reqResponseType               = cursor.getInt(cursor.getColumnIndex(DB.REQ_RESPONSE_TYPE));
        int reqType               = cursor.getInt(cursor.getColumnIndex(DB.REQ_TYPE));
        int reqCode               = cursor.getInt(cursor.getColumnIndex(S.TRANSPORT_REQ_CODE));
        String reqSenderPhone             = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_REQ_SENDER_PHONE));
        String reqReceiverPhone     = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_REQ_RECEIVER_PHONE));
        String moneyReceiverPhone     = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_MONEY_RECEIVER_PHONE));
        String moneyPayerPhone       = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_MONEY_PAYER_PHONE));
        int ownerItemType   = cursor.getInt(cursor.getColumnIndex(S.TRANSPORT_OWNER_ITEM_TYPE));
        int associateItemType   = cursor.getInt(cursor.getColumnIndex(S.TRANSPORT_ASSOCIATE_ITEM_TYPE));
        String ownerItemId  = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_OWNER_ITEM_ID));
        String associateItemId         = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_ASSOCIATE_ITEM_ID));
        int itemBodyJsonType   = cursor.getInt(cursor.getColumnIndex(S.TRANSPORT_ITEM_BODY_JSON_TYPE));
        String itemBodyJsonString  =cursor.getString(cursor.getColumnIndex(S.TRANSPORT_ITEM_BODY_JSON_STRING));
        String message     = cursor.getString(cursor.getColumnIndex(S.TRANSPORT_MESSAGE));

        /*MoneyCirclePackageForServer outPackage = new MoneyCirclePackageForServer();
        outPackage.setdBId(reqDbId);
        outPackage.setTransportId(transportId);
        outPackage.setdBId(reqDbId);
        outPackage.setdBId(reqDbId);
        outPackage.setdBId(reqDbId);
        outPackage.setdBId(reqDbId);
        outPackage.setdBId(reqDbId);
        outPackage.setdBId(reqDbId);*/
        //return notification;
        return null;
    }

   /* @Override
    protected void loadItemsFromDB() {

        mPendingOutPackageList.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.NOTIFICATION_TABLE_URI,
                DB.NOTIFICATION_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Model model = createLightItemFromCursor(c);
                mPendingOutPackageList.add(model);
                titles.add(model.getTitle());
                c.moveToNext();
            }
            c.close();
        }

    }

    @Override
    protected Context getContext() {
        return null;
    }

    @Override
    protected Uri getTableUri() {
        return null;
    }

    @Override
    protected int getModelType() {
        return 0;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return null;
    }*/
}
