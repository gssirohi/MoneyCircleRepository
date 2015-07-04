package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/2/2015.
 */
public class ContactManager extends BaseModelManager {
    Context context;
    ArrayList<Contact> contacts;

    public ContactManager(Context context) {
        this.context = context;
    }

    @Override
    public Model createItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        int dbId               = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String name            = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String serverId        = cursor.getString(cursor.getColumnIndex(DB.SERVER_ID));
        String serverName      = cursor.getString(cursor.getColumnIndex(DB.SERVER_NAME));
        String phone           = cursor.getString(cursor.getColumnIndex(DB.PHONE_NUMBER));
        String email           = cursor.getString(cursor.getColumnIndex(DB.EMAIL));
        int circleCount        = cursor.getInt(cursor.getColumnIndex(DB.CIRCLE_COUNT));
        String imageUri        = cursor.getString(cursor.getColumnIndex(DB.IMAGE_URI));
        int registered         = cursor.getInt(cursor.getColumnIndex(DB.REGISTERED));
        String circleJsonString = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_JSON_STRING));

        Contact contact = new Contact();
        contact.setDbId(dbId);
        contact.setUID(uid);
        contact.setTitle(name);
        contact.setContactName(name);
        contact.setServerId(serverId);
        contact.setServerName(serverName);
        contact.setPhone(phone);
        contact.setEmail(email);
        contact.setCircleCount(circleCount);
        contact.setImageUri(Uri.parse(imageUri));
        contact.setRegistered(registered);
        contact.setCirclesJsonString(circleJsonString);
        return contact;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {

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

    public void retriveContactsFromDevice() {
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if(phones != null) {

            while (phones.moveToNext())
            {
                String Name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String Number=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Number = Tools.getFormatedNumber(Number);
                if(TextUtils.isEmpty(Number)) continue;
                Contact contact = new Contact(Name, Number);
                ContentValues values = contact.getContentValues();
                context.getContentResolver().insert(DB.CONTACT_TABLE_URI, values);
                if(phones.isLast()) {
                    break;
                }
            }
        }

    }

}
