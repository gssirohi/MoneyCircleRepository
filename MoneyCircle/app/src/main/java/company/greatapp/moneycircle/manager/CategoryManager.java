package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;


import company.greatapp.moneycircle.model.Category;

import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 10-07-2015.
 */
public class CategoryManager extends BaseModelManager  {

    private static final String LOGTAG = "CategroyManager";

    private Context mContext;
    private int mModelType;

    private ArrayList<Model> mIncomeCategoryList;
    private ArrayList<Model> mExpenseCategoryList;
    private ArrayList<Model> mBorrowCategoryList;
    private ArrayList<Model> mLentCategoryList;
    private ArrayList<Model> mSplitCategoryList;

    /**
     * This Constructor is used to load default categories in DB only.
     * @param context
     */
    public CategoryManager(Context context) {
        mContext = context;
    }

    /**
     * This Constructor is used to load the categories from DB based on the modelType.
     * Hence, in this constructor only loadItemsFromDB is called.
     * @param context  This is context of the activity
     * @param modelType if "0" means all category has to be loaded
     */
    public CategoryManager(Context context, int modelType) {
        mContext = context;
        mModelType = modelType;

        loadItemsFromDB(); // TODO currently all categories are loaded need to change that only particular required category will be loaded
    }

    @Override
    public Model createItemFromCursor(Cursor cursor) {

        if (cursor == null) {
            return null;
        }

        int dbId = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uId = cursor.getString(cursor.getColumnIndex(DB.UID));
        String categoryName = cursor.getString(cursor.getColumnIndex(DB.CATEGORY_NAME));
        int categoryType = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_TYPE));

        Category category = new Category(categoryName, dbId, uId, categoryType);
        return category;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    // TODO Unnecessary logs need to be removed from this function
    @Override
    public void loadItemsFromDB() {
        Log.d(LOGTAG, "loadItemsFromDB");

        Cursor cursor = mContext.getContentResolver().query(DB.CATEGORY_TABLE_URI,
                DB.CATEGORY_TABLE_PROJECTION, null, null,null);
        if (cursor != null && cursor.getCount() > 0) {
            Log.d(LOGTAG, "loadItemsFromDB cursorCount :"+cursor.getCount());
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Category category = (Category)createItemFromCursor(cursor);
                if (category.getCategoryType() == Model.MODEL_TYPE_INCOME) {
                    if (mIncomeCategoryList == null) {
                        mIncomeCategoryList = new ArrayList<>();
                    }
                    mIncomeCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mIncomeCategoryList :" + category.getTitle());
                } else if (category.getCategoryType() == Model.MODEL_TYPE_EXPENSE) {
                    if (mExpenseCategoryList == null) {
                        mExpenseCategoryList = new ArrayList<>();
                    }
                    mExpenseCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mExpenseCategoryList :" + category.getTitle());
                } else if (category.getCategoryType() == Model.MODEL_TYPE_BORROW) {
                    if (mBorrowCategoryList == null) {
                        mBorrowCategoryList = new ArrayList<>();
                    }
                    mBorrowCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mBorrowCategoryList :" + category.getTitle());
                } else if (category.getCategoryType() == Model.MODEL_TYPE_LENT) {
                    if (mLentCategoryList == null) {
                        mLentCategoryList = new ArrayList<>();
                    }
                    mLentCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mLentCategoryList :" + category.getTitle());
                } else if (category.getCategoryType() == Model.MODEL_TYPE_SPLIT) {
                    if (mSplitCategoryList == null) {
                        mSplitCategoryList = new ArrayList<>();
                    }
                    mSplitCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mSplitCategoryList :" + category.getTitle());
                }
                cursor.moveToNext(); // This statement is mandatory to traverse to next object of cursor, otherwise it will go in infinite loop
            }
            cursor.close();
        }
        Log.d(LOGTAG, "loadItemsFromDB End");
    }

    @Override
    protected Context getContext() {
        return mContext;
    }

    @Override
    protected Uri getTableUri() {
        return DB.CATEGORY_TABLE_URI;
    }

    @Override
    protected int getModelType() {
        return Model.MODEL_TYPE_CATEGORY;
    }

    @Override
    public ArrayList<Model> getItemList() {
        Log.d(LOGTAG, "getItemList");

        switch (mModelType) {
            case Model.MODEL_TYPE_INCOME:
                return mIncomeCategoryList;
            case Model.MODEL_TYPE_EXPENSE:
                return mExpenseCategoryList;
            case Model.MODEL_TYPE_BORROW:
                return mBorrowCategoryList;
            case Model.MODEL_TYPE_LENT:
                return mLentCategoryList;
            case Model.MODEL_TYPE_SPLIT:
                return mSplitCategoryList;
        }
        return null;
    }

    public ArrayList<Model> getIncomeCategoryList() {
        return mIncomeCategoryList;
    }

    public ArrayList<Model> getExpenseCategoryList() {
        return mExpenseCategoryList;
    }

    public ArrayList<Model> getBorrowCategoryList() {
        return mBorrowCategoryList;
    }

    public ArrayList<Model> getLentCategoryList() {
        return mLentCategoryList;
    }

    public ArrayList<Model> getSplitCategoryList() {
        return mSplitCategoryList;
    }

    public void insertDefaultCategoriesInDB() {

        Log.d(LOGTAG, "insertDefaultCategoriesInDB");

        ArrayList<Model> defaultCategoryList = new ArrayList<>();
        defaultCategoryList.addAll(getDefaultIncomeCategoryList());     // Income Category List
        defaultCategoryList.addAll(getDefaultExpenseCategoryList());    // Expense Category List
        defaultCategoryList.addAll(getDefaultBorrowCategoryList());     // Borrow Category List
        defaultCategoryList.addAll(getDefaultLentCategoryList());       // Lent Category List
        defaultCategoryList.addAll(getDefaultSplitCategoryList());      // Split Category List

        int i = 0;
        for (Model category : defaultCategoryList) {
            Log.d(LOGTAG, "insertDefaultCategoriesInDB insertItemInDB :"+i);
            insertItemInDB(category);
            i++;
        }

    }

    private ArrayList<Model> getDefaultIncomeCategoryList() {
        ArrayList<Model> list = new ArrayList<>();
        list.add(new Category("Salary", Model.MODEL_TYPE_INCOME));
        list.add(new Category("Shares", Model.MODEL_TYPE_INCOME));
        list.add(new Category("Rent", Model.MODEL_TYPE_INCOME));
        list.add(new Category("Bussiness Profit", Model.MODEL_TYPE_INCOME));
        list.add(new Category("Freelancing", Model.MODEL_TYPE_INCOME));
        list.add(new Category("Donation", Model.MODEL_TYPE_INCOME));
        list.add(new Category("Gambling", Model.MODEL_TYPE_INCOME));
        list.add(new Category("Equity", Model.MODEL_TYPE_INCOME));
        list.add(new Category("Mutual Fund", Model.MODEL_TYPE_INCOME));

        return list;
    }

    private ArrayList<Model> getDefaultExpenseCategoryList() {
        ArrayList<Model> list = new ArrayList<>();
        list.add(new Category("Entertainment", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Bills", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Clothing", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Food", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Juice", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Drinks", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Kitchen", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("House Holds", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Internet", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Daily Traveling", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Trip", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("Policy", Model.MODEL_TYPE_EXPENSE));
        list.add(new Category("House Hold Helps", Model.MODEL_TYPE_EXPENSE));

        return list;
    }

    private ArrayList<Model> getDefaultBorrowCategoryList() {
        ArrayList<Model> list = new ArrayList<>();
        list.add(new Category("Bank Loan", Model.MODEL_TYPE_BORROW));
        list.add(new Category("Credit Card", Model.MODEL_TYPE_BORROW));
        list.add(new Category("From Friends", Model.MODEL_TYPE_BORROW));
        list.add(new Category("Shop", Model.MODEL_TYPE_BORROW));

        return list;
    }

    private ArrayList<Model> getDefaultLentCategoryList() {
        ArrayList<Model> list = new ArrayList<>();
        list.add(new Category("To Friend", Model.MODEL_TYPE_LENT));
        list.add(new Category("To Family", Model.MODEL_TYPE_LENT));
        list.add(new Category("To Colege", Model.MODEL_TYPE_LENT));
        list.add(new Category("On Lease", Model.MODEL_TYPE_LENT));

        return list;
    }

    private ArrayList<Model> getDefaultSplitCategoryList() {
        ArrayList<Model> list = new ArrayList<>();
        list.add(new Category("Lunch", Model.MODEL_TYPE_SPLIT));
        list.add(new Category("Pizza", Model.MODEL_TYPE_SPLIT));
        list.add(new Category("Trip", Model.MODEL_TYPE_SPLIT));
        list.add(new Category("Rent", Model.MODEL_TYPE_SPLIT));
        list.add(new Category("Party", Model.MODEL_TYPE_SPLIT));
        list.add(new Category("Movie", Model.MODEL_TYPE_SPLIT));
        list.add(new Category("Kitchen", Model.MODEL_TYPE_SPLIT));
        list.add(new Category("Flat", Model.MODEL_TYPE_SPLIT));

        return list;
    }
}
