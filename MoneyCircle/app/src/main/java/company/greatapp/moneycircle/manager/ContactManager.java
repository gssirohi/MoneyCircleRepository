package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

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
        String imageUri        = cursor.getString(cursor.getColumnIndex(DB.CONTACT_IMAGE_URI));
        int registered         = cursor.getInt(cursor.getColumnIndex(DB.REGISTERED));
        String jsonString      = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));

        Contact contact = new Contact();
        contact.setDbId(dbId);
        contact.setUID(uid);
        contact.setTitle(name);
        contact.setContactName(name);
        contact.setServerId(serverId);
        contact.setServerName(serverName);
        contact.setPhone(phone);
        contact.setEmail(email);
        contact.setImageUri(Uri.parse(imageUri));
        contact.setRegistered(registered);
        contact.setJsonString(jsonString);
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
    protected Context getContext() {
        return context;
    }

    @Override
    protected Uri getTableUri() {
        return DB.CONTACT_TABLE_URI;
    }

    @Override
    protected int getModelType() {
        return Model.MODEL_TYPE_CONTACT;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return this.contacts;
    }

    public ArrayList<String> getItemNameList() {
        return this.titles;
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
