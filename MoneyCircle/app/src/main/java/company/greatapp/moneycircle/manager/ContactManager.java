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
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/2/2015.
 */
public class ContactManager extends BaseModelManager {
    Context context;
    ArrayList<Model> mContactList = new ArrayList<Model>();
    ArrayList<Model> mRegisteredContactList = new ArrayList<Model>();
    ArrayList<Model> mUnRegisteredContactList = new ArrayList<Model>();

    ArrayList<String> titles = new ArrayList<String>();

    public ContactManager(Context context) {
        this.context = context;
        loadItemsFromDB();
    }

    @Override
    public Model createHeavyItemFromCursor(Cursor cursor) {
        if (cursor == null) return null;

        int dbId = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid = cursor.getString(cursor.getColumnIndex(DB.UID));
        String name = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String serverId = cursor.getString(cursor.getColumnIndex(DB.SERVER_ID));
        String serverName = cursor.getString(cursor.getColumnIndex(DB.SERVER_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(DB.PHONE_NUMBER));
        String email = cursor.getString(cursor.getColumnIndex(DB.EMAIL));
        String imageUri = cursor.getString(cursor.getColumnIndex(DB.CONTACT_IMAGE_URI));
        int registered = cursor.getInt(cursor.getColumnIndex(DB.REGISTERED));
        int gender = cursor.getInt(cursor.getColumnIndex(DB.GENDER));
        int state = cursor.getInt(cursor.getColumnIndex(DB.STATE));
        float borrowedAmount = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.CONTACT_BORROWED_AMOUNT_FROM_THIS)));
        float lentAmount = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.CONTACT_LENT_AMOUNT_TO_THIS)));
        String jsonString = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));


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
        contact.setRegistered((registered == 1) ? true : false);
        contact.setBorrowedAmountfromThis(borrowedAmount);
        contact.setLentAmountToThis(lentAmount);
        contact.setJsonString(jsonString);
        contact.setGender(gender);
        contact.setState(state);
        return contact;
    }

    public static Model createLightItemFromCursor(Cursor cursor) {
        if (cursor == null) return null;

        int dbId = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid = cursor.getString(cursor.getColumnIndex(DB.UID));
        String name = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String serverId = cursor.getString(cursor.getColumnIndex(DB.SERVER_ID));
        String serverName = cursor.getString(cursor.getColumnIndex(DB.SERVER_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(DB.PHONE_NUMBER));
        String email = cursor.getString(cursor.getColumnIndex(DB.EMAIL));
        String imageUri = cursor.getString(cursor.getColumnIndex(DB.CONTACT_IMAGE_URI));
        int registered = cursor.getInt(cursor.getColumnIndex(DB.REGISTERED));
        int gender = cursor.getInt(cursor.getColumnIndex(DB.GENDER));
        int state = cursor.getInt(cursor.getColumnIndex(DB.STATE));
        float borrowedAmount = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.CONTACT_BORROWED_AMOUNT_FROM_THIS)));
        float lentAmount = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.CONTACT_LENT_AMOUNT_TO_THIS)));
        String jsonString = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));

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
        contact.setRegistered((registered == 1)? true : false);
        contact.setBorrowedAmountfromThis(borrowedAmount);
        contact.setLentAmountToThis(lentAmount);
        contact.setJsonString(jsonString);
        contact.setGender(gender);
        contact.setState(state);
        return contact;
    }


    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {
        mContactList.clear();
        mUnRegisteredContactList.clear();
        mRegisteredContactList.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.CONTACT_TABLE_URI,
                DB.CONTACT_TABLE_PROJECTION, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Model model = createHeavyItemFromCursor(c);
                if (((Contact) model).isRegistered()) {
                    mRegisteredContactList.add(model);
                } else {
                    mUnRegisteredContactList.add(model);
                }
                mContactList.add(model);
                titles.add(model.getTitle());
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
        return this.mContactList;
    }

    public ArrayList<String> getItemNameList() {
        return this.titles;
    }

    public ArrayList<Model> getRegisteredContactList() {
        return this.mRegisteredContactList;
    }

    public ArrayList<Model> getUnRegisteredContactList() {
        return this.mUnRegisteredContactList;
    }

    public ArrayList<String> getPhoneNumberList() {
        ArrayList<Model> list  = getItemList();
        ArrayList<String> phones = new ArrayList<String>();
        Contact contact;
        for(Model model: list) {
            contact = (Contact)model;
            phones.add(contact.getPhone());
        }
        return phones;
    }

    public boolean updateContactsFromDevice() {
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        boolean isUpdated = false;
        User user = new User(context);
        ArrayList<String> addedPhones = getPhoneNumberList();
        if (phones != null) {

            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                number = Tools.getFormatedNumber(number);
                if (TextUtils.isEmpty(number) || user.getPhoneNumber().equals(number)) {
                    continue;
                } else if(addedPhones.contains(number)) {
                    Contact contact = getContactByPhoneNumber(number);
                    if(!contact.getContactName().equals(name)) {
                        contact.setContactName(name);
                        contact.updateItemInDb(context);
                        isUpdated = true;
                    }
                    continue;
                }

                Contact contact = new Contact(name, number);
                isUpdated = true;
                insertItemInDB(contact);
                addedPhones.add(number);
                if (phones.isLast()) {
                    break;
                }
            }
        }
//        Contact contact = new Contact(C.USER_TITLE, C.USER_DUMMY_NUMBER);
//        insertItemInDB(contact);

        if(isUpdated) {
            loadItemsFromDB();
        }
        return isUpdated;
    }


    public void retriveContactsFromDevice() {
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        User user = new User(context);
        ArrayList<String> addedPhones = new ArrayList<String>();
        if (phones != null) {

            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                number = Tools.getFormatedNumber(number);
                if (TextUtils.isEmpty(number) || addedPhones.contains(number) || user.getPhoneNumber().equals(number)) continue;
                Contact contact = new Contact(name, number);
                insertItemInDB(contact);
                addedPhones.add(number);
                if (phones.isLast()) {
                    break;
                }
            }
        }
//        Contact contact = new Contact(C.USER_TITLE, C.USER_DUMMY_NUMBER);
//        insertItemInDB(contact);

    }

    public Contact getUser() {
        return (Contact) getHeavyItemFromListByUID(C.USER_UNIQUE_ID);
    }

    public Contact getContactByPhoneNumber(String registeredPhoneNumber) {
        if(mContactList != null){
            for(Model model : mContactList) {
                String phone = ((Contact)model).getPhone();
                if(phone.equals(registeredPhoneNumber)){
                    return (Contact)model;
                }
            }
        }
        return null;
    }
}
