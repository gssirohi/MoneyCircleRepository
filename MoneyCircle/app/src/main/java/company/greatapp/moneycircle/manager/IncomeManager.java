package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import company.greatapp.moneycircle.NewHomeActivity;
import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 05-07-2015.
 */
public class IncomeManager extends BaseModelManager{
    Context context;
    ArrayList<Model> incomes = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();

    public ArrayList<String> getTitles() {
        return this.titles;
    }

    public IncomeManager(Context context){
        this.context = context;
        loadItemsFromDB();

    }


    @Override
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
        int date             = cursor.getInt(cursor.getColumnIndex(DB.DATE));
        int dateOfMonth             = cursor.getInt(cursor.getColumnIndex(DB.DAY_OF_MONTH));
        int weekOfMonth             = cursor.getInt(cursor.getColumnIndex(DB.WEEK_OF_MONTH));
        int month             = cursor.getInt(cursor.getColumnIndex(DB.MONTH));
        int year             = cursor.getInt(cursor.getColumnIndex(DB.YEAR));


        Income income =new Income(dbId, uid);
        income.setTitle(title);
        income.setCategory(new Category(category));
        income.setAmount(Float.parseFloat(amount));
        income.setDescription(description);
        income.setDateString(date_string);
        income.setJsonString(json_string);
        return income;
    }

    public static Model createLightItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        int dbId               =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String title            = cursor.getString(cursor.getColumnIndex(DB.TITLE));
        String category           = cursor.getString(cursor.getColumnIndex(DB.CATEGORY));
        String amount             = cursor.getString(cursor.getColumnIndex(DB.AMOUNT));
        String description     = cursor.getString(cursor.getColumnIndex(DB.DESCRIPTION));
        String json_string     = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String date_string       = cursor.getString(cursor.getColumnIndex(DB.DATE_STRING));
        int date             = cursor.getInt(cursor.getColumnIndex(DB.DATE));
        int dateOfMonth             = cursor.getInt(cursor.getColumnIndex(DB.DAY_OF_MONTH));
        int weekOfMonth             = cursor.getInt(cursor.getColumnIndex(DB.WEEK_OF_MONTH));
        int month             = cursor.getInt(cursor.getColumnIndex(DB.MONTH));
        int year             = cursor.getInt(cursor.getColumnIndex(DB.YEAR));


        Income income =new Income(dbId, uid);
        income.setTitle(title);
        income.setCategory(new Category(category));
        income.setAmount(Float.parseFloat(amount));
        income.setDescription(description);
        income.setDateString(date_string);
        income.setJsonString(json_string);
        return income;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {
        incomes.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.INCOME_TABLE_URI,
                DB.INCOME_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Model model = createHeavyItemFromCursor(c);
                incomes.add(model);
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
        return DB.INCOME_TABLE_URI;
    }

    @Override
    protected int getModelType() {
        return Model.MODEL_TYPE_INCOME;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return this.incomes;
    }

}
