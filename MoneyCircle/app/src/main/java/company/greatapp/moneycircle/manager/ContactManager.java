package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/2/2015.
 */
public class ContactManager extends BaseModelManager {
    Context context;
    ArrayList<Model> contacts = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();

    public ContactManager(Context context) {
        this.context = context;
        loadItemsFromDB();
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
        contacts.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.CONTACT_TABLE_URI,
                DB.CONTACT_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Model model = createItemFromCursor(c);
                if(((Contact)model).getRegistered() == C.REGISTERED_ON_MONEY_CIRCLE){
                    contacts.add(0, model);
                    titles.add(model.getTitle());
                } else {
                    contacts.add(model);
                    titles.add(model.getTitle());
                }
                c.moveToNext();
            }
            c.close();
        }
    }

    @Override
    public ArrayList<Model> getItemList() {
        return this.contacts;
    }

    public ArrayList<String> getItemNameList() {
        return this.titles;
    }

    @Override
    public Model getItemFromListByUID(String uid) {
        Log.d("split","required uid: "+uid);
        for(Model m : contacts) {
            Log.d("split", "checking uid: " + m.getUID());
            if(uid.equals(m.getUID()))
                return m;
        }
        return null;
    }

    @Override
    public void insertItemInDB(Model model) {
        Contact contact = (Contact)model;
        String uid = contact.getUID();
        uid = uid.replaceAll("NEW","DB");
        contact.setUID(uid);
        ContentValues values = contact.getContentValues();
        context.getContentResolver().insert(DB.CONTACT_TABLE_URI, values);
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
                insertItemInDB(contact);
                if(phones.isLast()) {
                    break;
                }
            }
        }

    }

}
