package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Notification;

/**
 * Created by Ashish on 10-07-2015.
 */
public class NotificationManager extends BaseModelManager  {

    Context context;
    ArrayList<Model> notification = new ArrayList<Model>();

    public NotificationManager(Context context) {
        this.context = context;
        loadItemsFromDB();
    }
    @Override
    public Model createItemFromCursor(Cursor cursor) {

        if(cursor == null) return null;

        int dbId               = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String sendername      = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String phone           = cursor.getString(cursor.getColumnIndex(DB.PHONE_NUMBER));
        String notificationType= cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_TYPE));
        String notificationDescription= cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_DESCRIPTION));
        String notificationJsonString = cursor.getString(cursor.getColumnIndex(DB.NOTIFICATION_JSON_STRING));


        Notification notification = new Notification();
        notification.setDbId(dbId);
        notification.setUID(uid);
        notification.setContactName(sendername);
        notification.setPhone(phone);
        notification.setDescription(notificationDescription);


        return notification;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {

        notification.clear();

        Cursor c = context.getContentResolver().query(DB.NOTIFICATION_TABLE_URI,
                DB.NOTIFICATION_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
//           TODO
            c.close();
        }

    }

    @Override
    public ArrayList<Model> getItemList() {
        return null;
    }

    @Override
    public Model getItemFromListByUID(String uid) {
        return null;
    }

    @Override
    public void insertItemInDB(Model model) {

        Notification notification = (Notification)model;
        String uid = notification.getUID();
        uid = uid.replaceAll("NEW","DB");
        notification.setUID(uid);
        ContentValues values = notification.getContentValues();
        context.getContentResolver().insert(DB.NOTIFICATION_TABLE_URI, values);

    }

    @Override
    public void updateItemInDB(Model model) {

    }

    @Override
    public void deleteItemFromDB(Model model) {

    }

    @Override
    public boolean isItemExistInDb(Model model) {
        return false;
    }

    @Override
    public void printManagerData() {

    }
}
