package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Borrow;

import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 09-07-2015.
 */
public class BorrowManager extends BaseModelManager  {

    Context context;
    ArrayList<Model> borrow = new ArrayList<Model>();




    @Override
    public Model createItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        int dbId               = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String name            = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String borrowType      = cursor.getString(cursor.getColumnIndex(DB.BORROW_TYPE));
        String borrowCategory  = cursor.getString(cursor.getColumnIndex(DB.BORROW_CATEGORY));
        int borrowAmount       = cursor.getInt(cursor.getColumnIndex(DB.BORROW_AMOUNT));
        String borrowDate      = cursor.getString(cursor.getColumnIndex(DB.BORROW_DATE));
        String borrowDueDate   = cursor.getString(cursor.getColumnIndex(DB.BORROW_DUE_DATE));
        String borrowDescription=cursor.getString(cursor.getColumnIndex(DB.BORROW_DESCRIPTION));


        Borrow borrow = new Borrow();
        borrow.setDbId(dbId);
        borrow.setUID(uid);
        borrow.setTitle(name);
        borrow.setCategory(borrowCategory);
        borrow.setAmount(borrowAmount);
        borrow.setDate(borrowDate);
        borrow.setDueDate(borrowDueDate);
        borrow.setDescription(borrowDescription);


        return borrow;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    public BorrowManager(Context context) {
        this.context = context;
        loadItemsFromDB();
    }

    @Override
    protected void loadItemsFromDB() {

        borrow.clear();

        Cursor c = context.getContentResolver().query(DB.BORROW_TABLE_URI,
                DB.BORROW_TABLE_PROJECTION, null, null, null);
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

        Borrow borrow = (Borrow)model;
        String uid = borrow.getUID();
        uid = uid.replaceAll("NEW","DB");
        borrow.setUID(uid);
        ContentValues values = borrow.getContentValues();
        context.getContentResolver().insert(DB.BORROW_TABLE_URI, values);

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
