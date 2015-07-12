package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;


import company.greatapp.moneycircle.model.Category;

import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 10-07-2015.
 */
public class CategoryManager extends BaseModelManager  {

    Context context;
    ArrayList<Model> category = new ArrayList<Model>();

    public CategoryManager(Context context) {
        this.context = context;
        loadItemsFromDB();
    }
    @Override
    public Model createItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        int dbId               = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String name            = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String categoryType    = cursor.getString(cursor.getColumnIndex(DB.CATEGORY_TYPE));
        String categoryDescription = cursor.getString(cursor.getColumnIndex(DB.CATEGORY_DESCRIPTION));

        Category category = new Category();
        category.setDbId(dbId);
        category.setUID(uid);
        category.setTitle(name);
        category.setCategoryType(categoryType);
        category.setCategoryDesciption(categoryDescription);


        return category;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {

        category.clear();

        Cursor c = context.getContentResolver().query(DB.CATEGORY_TABLE_URI,
                DB.CATEGORY_TABLE_PROJECTION, null, null, null);
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

        Category category = (Category)model;
        String uid = category.getUID();
        uid = uid.replaceAll("NEW","DB");
        category.setUID(uid);
        ContentValues values = category.getContentValues();
        context.getContentResolver().insert(DB.CATEGORY_TABLE_URI, values);

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
