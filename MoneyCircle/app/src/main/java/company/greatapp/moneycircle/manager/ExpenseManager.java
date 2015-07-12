package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 09-07-2015.
 */
public class ExpenseManager extends BaseModelManager  {

    Context context;
    ArrayList<Model> expense = new ArrayList<Model>();
    private int modelType;
    private int dbId;
    private String title ="";
    private String uid = "";
    private Date expenseDate;

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }





    public ArrayList<Model> getExpense() {
        return expense;
    }

    public void setExpense(ArrayList<Model> expense) {
        this.expense = expense;
    }


    public ExpenseManager(Context context){
        this.context = context;
        loadItemsFromDB();
    }





    @Override
    public Model createItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        int dbId               = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String name            = cursor.getString(cursor.getColumnIndex(DB.NAME));
        String expensedate     = cursor.getString(cursor.getColumnIndex(DB.EXPENSE_DATE));
        int expenseAmount       = cursor.getInt(cursor.getColumnIndex(DB.EXPENSE_AMOUNT));
        String expenseCategory  = cursor.getString(cursor.getColumnIndex(DB.EXPENSE_CATEGORY));
        String expenseDescription =cursor.getString(cursor.getColumnIndex(DB.EXPENSE_DESCRIPTION));

        Expense expense = new Expense();
        expense.setDbId(dbId);
        expense.setUID(uid);
        expense.setTitle(name);
        expense.setDate(expenseDate);
        expense.setAmount(expenseAmount);
        expense.setCategory(expenseCategory);
        expense.setDescription(expenseDescription);

        return expense;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {

        expense.clear();

        Cursor c = context.getContentResolver().query(DB.EXPENSE_TABLE_URI,
                DB.EXPENSE_TABLE_PROJECTION, null, null, null);
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

        Expense expense = (Expense)model;
        String uid = expense.getUID();
        uid = uid.replaceAll("NEW","DB");
        expense.setUID(uid);
        ContentValues values = expense.getContentValues();
        context.getContentResolver().insert(DB.EXPENSE_TABLE_URI, values);

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
