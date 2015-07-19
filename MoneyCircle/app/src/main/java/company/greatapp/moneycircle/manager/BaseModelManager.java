package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by gyanendra.sirohi on 7/3/2015.
 */
public abstract class BaseModelManager {

    public abstract Model createItemFromCursor(Cursor cursor);
    public abstract Model createItemFromIntent(Intent intent);
    protected abstract void loadItemsFromDB();
    protected abstract Context getContext();
    protected abstract Uri getTableUri();
    protected abstract int getModelType();
    public abstract ArrayList<Model> getItemList();

    public Model getItemFromListByUID(String uid) {
        Log.d("split", "required uid: " + uid);
        for(Model m : getItemList()) {
            Log.d("split", "checking uid: " + m.getUID());
            if(uid.equals(m.getUID()))
                return m;
        }
        return null;
    }

    public void insertItemInDB(Model model) {
        Context context = getContext();
        String uid = model.getUID();
        uid = uid.replaceAll("NEW","DB");
        model.setUID(uid);
        Log.d("Split","INSERTING MODEL:----->");
        model.printModelData();
        ContentValues values = model.getContentValues();
        context.getContentResolver().insert(getTableUri(), values);
    }

    public void updateItemInDB(Model model) {
        Context context = getContext();
        String where = DB.DB_ID + "=" + model.getDbId();
        String[] selectionArgs = null;
        ContentValues values = model.getContentValues();
        context.getContentResolver().update(getTableUri(), values, where, selectionArgs);
    }

    public void deleteItemFromDB(Model model) {
        Context context = getContext();
        String where = DB.DB_ID + "=" + model.getDbId();
        String[] selectionArgs = null;
        context.getContentResolver().delete(getTableUri(), where, selectionArgs);
    }

    public void printManagerData(){
        ArrayList<Model> list = getItemList();

        Log.d("SPLIT","TYPE: "+getModelType());
        Log.d("SPLIT","ITEM COUNT: "+list.size());
        for(Model m : list) {
            m.printModelData();
        }
    }

}
