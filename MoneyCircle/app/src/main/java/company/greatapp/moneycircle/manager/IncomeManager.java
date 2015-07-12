package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 05-07-2015.
 */
public class IncomeManager extends BaseModelManager{
    Context context;
    ArrayList<Model> income = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();

    public ArrayList<String> getTitles() {
        return this.titles;
    }



    public ArrayList<Model> getIncome() {
        return income;
    }

    public void setIncome(ArrayList<Model> income) {
        this.income = income;
    }



    public IncomeManager(Context context){
        this.context = context;
        loadItemsFromDB();
    }


    @Override
    public Model createItemFromCursor(Cursor cursor) {

        if(cursor == null) return null;
        int dbId               =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String name            = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String phone           = cursor.getString(cursor.getColumnIndex(DB.PHONE_NUMBER));
        String incomeType      = cursor.getString(cursor.getColumnIndex(DB.INCOME_TYPE));
        String incomeCategory  = cursor.getString(cursor.getColumnIndex(DB.INCOME_CATEGORY));
        int amount             = cursor.getInt(cursor.getColumnIndex(DB.INCOME_AMOUNT));
        String incomeJsonString = cursor.getString(cursor.getColumnIndex(DB.INCOME_JSON_STRING));
        String incomeDate       = cursor.getString(cursor.getColumnIndex(DB.INCOME_DATE));
        String incomeDescription= cursor.getString(cursor.getColumnIndex(DB.INCOME_DESCRIPTION));

        Income income =new Income();
        income.setDbId(dbId);
        income.setUID(uid);
        income.setTitle(name);
        income.setContactName(name);
        income.setPhone(phone);
        income.setIncomeType(incomeType);
        income.setCategory(incomeCategory);
        income.setAmount(amount);
        income.setIncomeJson(incomeJsonString);
        income.setDate(incomeDate);
        income.setDescription(incomeDescription);

        return income;




    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {
        income.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.INCOME_TABLE_URI,
                DB.INCOME_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
//           TODO
            c.close();
        }

    }

    @Override
    public ArrayList<Model> getItemList() {
        return this.income;
    }

    @Override
    public Model getItemFromListByUID(String uid) {
        return null;
    }

    @Override
    public void insertItemInDB(Model model) {
        Income income = (Income)model;
        String uid = income.getUID();
        uid = uid.replaceAll("NEW","DB");
        income.setUID(uid);
        ContentValues values = income.getContentValues();
        context.getContentResolver().insert(DB.INCOME_TABLE_URI, values);

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
