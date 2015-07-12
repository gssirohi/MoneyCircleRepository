package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 10-07-2015.
 */
public class CommonManager extends BaseModelManager  {

    Context context;
    ArrayList<Model> common = new ArrayList<Model>();

    public CommonManager(Context context) {
        this.context = context;
        loadItemsFromDB();
    }
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

        common.clear();

        Cursor c = context.getContentResolver().query(DB.COMMON_TABLE_URI,
                DB.COMMON_TABLE_PROJECTION, null, null, null);
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
