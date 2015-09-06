package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Notification;

/**
 * Created by Ashish on 10-07-2015.
 */
public class NotificationManager extends BaseModelManager  {

    Context context;
    ArrayList<Model> notification = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();


    @Override
    public Model createHeavyItemFromCursor(Cursor cursor) {

      return null;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }


    public static Model createLightItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        int dbId               =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        int isResponded         =cursor.getInt(cursor.getColumnIndex(DB.NOTIFICATION_IS_RESPONDED));
        String due_date            = cursor.getString(cursor.getColumnIndex(DB.DUE_DATE_STRING));
        String amount             = cursor.getString(cursor.getColumnIndex(DB.AMOUNT));
        String description     = cursor.getString(cursor.getColumnIndex(DB.MONEY_ITEM_DESCRIPTION));
        String json_string     = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String date_string       = cursor.getString(cursor.getColumnIndex(DB.DATE_STRING));
        String sender_number   =cursor.getString(cursor.getColumnIndex(DB.SENDER_NUMBER));
        String item_title      = cursor.getString(cursor.getColumnIndex(DB.ITEM_TITLE));
        String money_due_date  = cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_MONEY_DUE_DATE));
        String message         = cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_MESSAGE));
        String money_reciever  =cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_MONEY_RECIEVER));
        String money_payer     = cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_MONEY_PAYER));
        String item_uid        = cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_OWNER_ITEM_UID));
        String notification_type = cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_TYPE));
        String sender_name       = cursor.getString(cursor.getColumnIndex(DB.SENDER_NAME));
        String item_owner_name    =cursor.getString(cursor.getColumnIndex(DB.ITEM_OWNER_NAME));

        Notification notification =new Notification(dbId, uid);
        notification.setTitle(item_title);
      //  notification.isResponded((isResponded == 1) ? true : false);
        notification.setAmount(Float.parseFloat(amount));
        notification.setmDescription(description);
        notification.setDateString(date_string);
        notification.setJsonString(json_string);
        notification.setSenderName(sender_name);
        notification.setOwnerNumber(sender_number);
        notification.setMoneyDueDate(money_due_date);
        notification.setMessage(message);
        notification.setMoneyReciever(money_reciever);
        notification.setMoneyPayer(money_payer);
        notification.setOwnerItemUid(item_uid);
        notification.setType(notification_type);
        notification.setItemOwnerName(item_owner_name);
        return notification;
    }

    @Override
    protected void loadItemsFromDB() {

        notification.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.NOTIFICATION_TABLE_URI,
                DB.NOTIFICATION_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Model model = createLightItemFromCursor(c);
                notification.add(model);
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
    }
}
