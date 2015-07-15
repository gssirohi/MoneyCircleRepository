package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;


import company.greatapp.moneycircle.model.Category;

import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 10-07-2015.
 */
public class CategoryManager extends BaseModelManager  {

    @Override
    public Model createItemFromCursor(Cursor cursor) {
        return null;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {

    }

    @Override
    protected Context getContext() {
        return null;
    }

    @Override
    protected Uri getTableUri() {
        return null;
    }

    @Override
    protected int getModelType() {
        return 0;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return null;
    }
}
