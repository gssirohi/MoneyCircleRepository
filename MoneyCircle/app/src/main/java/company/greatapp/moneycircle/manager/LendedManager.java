package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Lended;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 09-07-2015.
 */
public class LendedManager extends BaseModelManager  {
    Context context;
    ArrayList<Model> lended = new ArrayList<Model>();

    public LendedManager(Context context) {
        this.context = context;
        loadItemsFromDB();
    }
    @Override
    public Model createItemFromCursor(Cursor cursor) {

        if(cursor == null) return null;

        int dbId               = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String name            = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String phone           = cursor.getString(cursor.getColumnIndex(DB.PHONE_NUMBER));
        String lendedType      = cursor.getString(cursor.getColumnIndex(DB.LENDED_TYPE));
        String lendedCategory  = cursor.getString(cursor.getColumnIndex(DB.LENDED_CATEGORY));
        int lendedAmount       = cursor.getInt(cursor.getColumnIndex(DB.LENDED_AMOUNT));
        String lendedDate      = cursor.getString(cursor.getColumnIndex(DB.LENDED_DATE));
        String lendedDescription=cursor.getString(cursor.getColumnIndex(DB.LENDED_DESCRIPTION));


        Lended lended = new Lended();
        lended.setDbId(dbId);
        lended.setUID(uid);
        lended.setContactName(name);
        lended.setPhone(phone);
        lended.setCategory(lendedCategory);
        lended.setAmount(lendedAmount);
        lended.setDate(lendedDate);
        lended.setDescription(lendedDescription);


        return lended;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {

        lended.clear();

        Cursor c = context.getContentResolver().query(DB.LENT_TABLE_URI,
                DB.LENT_TABLE_PROJECTION, null, null, null);
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

        Lended lended = (Lended)model;
        String uid = lended.getUID();
        uid = uid.replaceAll("NEW","DB");
        lended.setUID(uid);
        ContentValues values = lended.getContentValues();
        context.getContentResolver().insert(DB.LENT_TABLE_URI, values);

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
