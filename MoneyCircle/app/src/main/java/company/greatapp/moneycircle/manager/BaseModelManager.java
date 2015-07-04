package company.greatapp.moneycircle.manager;

import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import company.greatapp.moneycircle.model.Model;

/**
 * Created by gyanendra.sirohi on 7/3/2015.
 */
public abstract class BaseModelManager {

    public abstract Model createItemFromCursor(Cursor cursor);
    public abstract Model createItemFromIntent(Intent intent);
    protected abstract void loadItemsFromDB();
    public abstract ArrayList<Model> getItemList();
    public abstract Model getItemFromListByUID(String uid);
    public abstract void insertItemInDB(Model model);
    public abstract void updateItemInDB(Model model);
    public abstract void deleteItemFromDB(Model model);
    public abstract boolean isItemExistInDb(Model model);
    public abstract void printManagerData();

}
