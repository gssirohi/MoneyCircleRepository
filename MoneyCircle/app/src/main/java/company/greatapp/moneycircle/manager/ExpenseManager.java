package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import company.greatapp.moneycircle.NewHomeActivity;
import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 09-07-2015.
 */
public class ExpenseManager extends BaseModelManager  {
    Context context;
    ArrayList<Model> expenses = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();

    public ArrayList<String> getTitles() {
        return this.titles;
    }


    public ExpenseManager(Context context){
        this.context = context;
        //if (context instanceof NewHomeActivity) {
            loadItemsFromDB();
        //}
    }


    @Override
    public Model createItemFromCursor(Cursor cursor) {

        if(cursor == null) return null;

        int dbId               =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String title            = cursor.getString(cursor.getColumnIndex(DB.TITLE));
        String category           = cursor.getString(cursor.getColumnIndex(DB.CATEGORY));
        String amount             = cursor.getString(cursor.getColumnIndex(DB.AMOUNT));
        String description     = cursor.getString(cursor.getColumnIndex(DB.DESCRIPTION));
        int isLinked             = cursor.getInt(cursor.getColumnIndex(DB.IS_LINKED_WITH_SPLIT));
        String splitJson     = cursor.getString(cursor.getColumnIndex(DB.LINKED_SPLIT_JSON));
        String json_string     = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String date_string       = cursor.getString(cursor.getColumnIndex(DB.DATE_STRING));
        int date             = cursor.getInt(cursor.getColumnIndex(DB.DATE));
        int dateOfMonth             = cursor.getInt(cursor.getColumnIndex(DB.DAY_OF_MONTH));
        int weekOfMonth             = cursor.getInt(cursor.getColumnIndex(DB.WEEK_OF_MONTH));
        int month             = cursor.getInt(cursor.getColumnIndex(DB.MONTH));
        int year             = cursor.getInt(cursor.getColumnIndex(DB.YEAR));


        Expense expense =new Expense(dbId, uid);
        expense.setTitle(title);
        expense.setCategory(category);
        expense.setAmount(Float.parseFloat(amount));
        expense.setDescription(description);
        expense.setIsLinkedWithSplit((isLinked == 1)?true:false);
        expense.setLinkedSplitJson(splitJson);
        expense.setDateString(date_string);
        expense.setJsonString(json_string);
        return expense;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {
        expenses.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.EXPENSE_TABLE_URI,
                DB.EXPENSE_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Model model = createItemFromCursor(c);
                if(model != null) {
                    Log.d("SPLIT", "LOADING EXPENSE["+model.getTitle()+"] : " + model.getUID());
                }
                expenses.add(model);
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
        return DB.EXPENSE_TABLE_URI;
    }

    @Override
    protected int getModelType() {
        return Model.MODEL_TYPE_EXPENSE;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return this.expenses;
    }

}
