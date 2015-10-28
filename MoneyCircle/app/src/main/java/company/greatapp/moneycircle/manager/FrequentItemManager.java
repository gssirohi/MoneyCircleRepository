package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.FrequentItem;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by Prateek on 25-10-2015.
 */
public class FrequentItemManager{

    private final Context mContext;
    private ArrayList<FrequentItem> frequentItems = new ArrayList<FrequentItem>();

    public FrequentItemManager(Context context) {
        mContext = context;
        loadItemsFromDB();
    }

    public Model createHeavyItemFromCursor(Cursor cursor) {

        if(cursor == null) return null;

        int dbId               =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String title            = cursor.getString(cursor.getColumnIndex(DB.TITLE));
        String category           = cursor.getString(cursor.getColumnIndex(DB.CATEGORY));
        String amount             = cursor.getString(cursor.getColumnIndex(DB.AMOUNT));
        String description     = cursor.getString(cursor.getColumnIndex(DB.DESCRIPTION));
        String json_string     = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String date_string       = cursor.getString(cursor.getColumnIndex(DB.DATE_STRING));


        FrequentItem frequentItem = new FrequentItem(dbId, uid);
        frequentItem.setTitle(title);
        frequentItem.setCategory((Category) Tools.getDbInstance(mContext, category, Model.MODEL_TYPE_CATEGORY));
        frequentItem.setAmount(Float.parseFloat(amount));
        frequentItem.setDescription(description);
        frequentItem.setDateString(date_string);
        frequentItem.setJsonString(json_string);
        return frequentItem;
    }

    public static Model createLightItemFromCursor(Cursor cursor) {

        if(cursor == null) return null;

        int dbId               =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String title            = cursor.getString(cursor.getColumnIndex(DB.TITLE));
        String category           = cursor.getString(cursor.getColumnIndex(DB.CATEGORY));
        String amount             = cursor.getString(cursor.getColumnIndex(DB.AMOUNT));
        String description     = cursor.getString(cursor.getColumnIndex(DB.DESCRIPTION));
        String date_string       = cursor.getString(cursor.getColumnIndex(DB.DATE_STRING));
        String json_string     = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));

        FrequentItem frequentItem = new FrequentItem(dbId, uid);
        frequentItem.setTitle(title);
        frequentItem.setCategory(new Category(category));
        frequentItem.setAmount(Float.parseFloat(amount));
        frequentItem.setDescription(description);
        frequentItem.setDateString(date_string);
        frequentItem.setJsonString(json_string);
        return frequentItem;
    }

    protected void loadItemsFromDB() {
        frequentItems.clear();
        Cursor c = mContext.getContentResolver().query(DB.FREQUENT_ITEM_TABLE_URI,
                DB.FREQUENT_ITEM_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                FrequentItem frequentItem = (FrequentItem)createHeavyItemFromCursor(c);
                if(frequentItem != null) {
                    Log.d("SPLIT", "LOADING EXPENSE[" + frequentItem.getTitle() + "] : " + frequentItem.getUID());
                }
                frequentItems.add(frequentItem);
                c.moveToNext();
            }
            c.close();
        }}

    public ArrayList<FrequentItem> getFrequentItemList() {
        return frequentItems;
    }
}
