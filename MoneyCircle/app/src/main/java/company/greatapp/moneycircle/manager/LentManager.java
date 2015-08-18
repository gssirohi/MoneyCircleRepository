package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.NewHomeActivity;
import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.GreatJSON;

/**
 * Created by Ashish on 09-07-2015.
 */
public class LentManager extends BaseModelManager  {
    private final ContactManager mContactManager;
    Context context;
    ArrayList<Model> lents = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();

    public ArrayList<String> getTitles() {
        return this.titles;
    }

    public LentManager(Context context){
        this.context = context;
        mContactManager = new ContactManager(context);
       // if (context instanceof NewHomeActivity) {
            loadItemsFromDB();
       // }
    }


    @Override
    public Model createItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        int dbId               =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String title           = cursor.getString(cursor.getColumnIndex(DB.TITLE));
        String category           = cursor.getString(cursor.getColumnIndex(DB.CATEGORY));
        String amount             = cursor.getString(cursor.getColumnIndex(DB.AMOUNT));
        String description     = cursor.getString(cursor.getColumnIndex(DB.DESCRIPTION));
        String dueDateString     = cursor.getString(cursor.getColumnIndex(DB.DUE_DATE_STRING));
        String linkedContactJson     = cursor.getString(cursor.getColumnIndex(DB.LINKED_CONTACT_JSON));
        int isLinked             = cursor.getInt(cursor.getColumnIndex(DB.IS_LINKED_WITH_SPLIT));
        String splitJson     = cursor.getString(cursor.getColumnIndex(DB.LINKED_SPLIT_JSON));
        String json_string     = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String date_string     = cursor.getString(cursor.getColumnIndex(DB.DATE_STRING));
        int date               = cursor.getInt(cursor.getColumnIndex(DB.DATE));
        int dateOfMonth             = cursor.getInt(cursor.getColumnIndex(DB.DAY_OF_MONTH));
        int weekOfMonth             = cursor.getInt(cursor.getColumnIndex(DB.WEEK_OF_MONTH));
        int month             = cursor.getInt(cursor.getColumnIndex(DB.MONTH));
        int year             = cursor.getInt(cursor.getColumnIndex(DB.YEAR));


        Lent lent =new Lent(dbId, uid);
        lent.setTitle(title);
        lent.setCategory(category);
        lent.setAmount(Float.parseFloat(amount));
        lent.setDescription(description);
        lent.setDueDateString(dueDateString);
        if(!TextUtils.isEmpty(linkedContactJson)) {
            Contact member = GreatJSON.getContactFromJsonString(linkedContactJson, mContactManager);
            lent.setLinkedContact(member);
        }
        lent.setIsLinkedWithSplit((isLinked == 1)?true:false);
        lent.setLinkedSplitJson(splitJson);
        lent.setDateString(date_string);
        lent.setJsonString(json_string);
        return lent;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {
        lents.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.LENT_TABLE_URI,
                DB.LENT_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Model model = createItemFromCursor(c);
                if(model != null) {
                    Log.d("SPLIT", "LOADING LENT[" + model.getTitle() + "] : " + model.getUID());
                }
                lents.add(model);
                titles.add(model.getTitle());
                c.moveToNext();
            }
            c.close();
        }}

    @Override
    protected Context getContext() {
        return context;
    }

    @Override
    protected Uri getTableUri() {
        return DB.LENT_TABLE_URI;
    }

    @Override
    protected int getModelType() {
        return Model.MODEL_TYPE_LENT;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return this.lents;
    }

}
