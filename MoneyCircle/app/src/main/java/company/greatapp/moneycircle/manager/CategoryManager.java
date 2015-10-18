package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.DB;


import company.greatapp.moneycircle.model.Category;

import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.split.SplitToolActivity;

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
    private ArrayList<Model> mAllCategories = new ArrayList<Model>();

    /**
     * This Constructor is used to load default categories in DB only.
     * @param context
     */
    public CategoryManager(Context context) {
        mContext = context;
        loadItemsFromDB();
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

        loadItemsFromDB();
    }

    @Override
    public Model createHeavyItemFromCursor(Cursor cursor) {

        if (cursor == null) {
            return null;
        }

        int dbId = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uId = cursor.getString(cursor.getColumnIndex(DB.UID));
        String categoryName = cursor.getString(cursor.getColumnIndex(DB.CATEGORY_NAME));
        int categoryType = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_TYPE));
        boolean forIncome = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_INCOME)) == 1?true:false;
        boolean forExpense = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_EXPENSE)) == 1?true:false;
        boolean forBorrow = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_BORROW)) == 1?true:false;
        boolean forLent = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_LENT)) == 1?true:false;
        boolean forSplit = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_SPLIT)) == 1?true:false;

        float spent = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.CATEGORY_SPENT_AMOUNT_ON_THIS)));

        Category category = new Category(categoryName, dbId, uId, categoryType);
        category.setForIncome(forIncome);
        category.setForExpense(forExpense);
        category.setForBorrow(forBorrow);
        category.setForLent(forLent);
        category.setForSplit(forSplit);
        category.setSpentAmountOnThis(spent);
        return category;
    }

    public static Model createLightItemFromCursor(Cursor cursor) {

        if (cursor == null) {
            return null;
        }

        int dbId = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uId = cursor.getString(cursor.getColumnIndex(DB.UID));
        String categoryName = cursor.getString(cursor.getColumnIndex(DB.CATEGORY_NAME));
        int categoryType = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_TYPE));
        boolean forIncome = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_INCOME)) == 1?true:false;
        boolean forExpense = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_EXPENSE)) == 1?true:false;
        boolean forBorrow = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_BORROW)) == 1?true:false;
        boolean forLent = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_LENT)) == 1?true:false;
        boolean forSplit = cursor.getInt(cursor.getColumnIndex(DB.CATEGORY_FOR_SPLIT)) == 1?true:false;
        float spent = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.CATEGORY_SPENT_AMOUNT_ON_THIS)));

        Category category = new Category(categoryName, dbId, uId, categoryType);
        category.setForIncome(forIncome);
        category.setForExpense(forExpense);
        category.setForBorrow(forBorrow);
        category.setForLent(forLent);
        category.setForSplit(forSplit);
        category.setSpentAmountOnThis(spent);
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
        if(mAllCategories == null) {
            mAllCategories = new ArrayList<Model>();
        }
        mAllCategories.clear();
        Cursor cursor = mContext.getContentResolver().query(DB.CATEGORY_TABLE_URI,
                DB.CATEGORY_TABLE_PROJECTION, null, null,null);
        if (cursor != null && cursor.getCount() > 0) {
            Log.d(LOGTAG, "loadItemsFromDB cursorCount :"+cursor.getCount());
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Category category = (Category) createHeavyItemFromCursor(cursor);

                mAllCategories.add(category);

                if (category.isForIncome()) {
                    if (mIncomeCategoryList == null) {
                        mIncomeCategoryList = new ArrayList<>();
                    }
                    mIncomeCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mIncomeCategoryList :" + category.getTitle());
                }

                if (category.isForExpense()) {
                    if (mExpenseCategoryList == null) {
                        mExpenseCategoryList = new ArrayList<>();
                    }
                    mExpenseCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mExpenseCategoryList :" + category.getTitle());
                }

                if (category.isForBorrow()) {
                    if (mBorrowCategoryList == null) {
                        mBorrowCategoryList = new ArrayList<>();
                    }
                    mBorrowCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mBorrowCategoryList :" + category.getTitle());
                }

                if (category.isForLent()) {
                    if (mLentCategoryList == null) {
                        mLentCategoryList = new ArrayList<>();
                    }
                    mLentCategoryList.add(category);
                    Log.d(LOGTAG, "loadItemsFromDB mLentCategoryList :" + category.getTitle());
                }

                if (category.isForSplit()) {
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
        return mAllCategories;
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

        ArrayList<Model> defaultCategoryList = getDefaultCategoryList();

        int i = 0;
        for (Model category : defaultCategoryList) {
            Log.d(LOGTAG, "insertDefaultCategoriesInDB insertItemInDB :"+i);
            insertItemInDB(category);
            i++;
        }

    }

    private ArrayList<Model> getDefaultCategoryList() {

        ArrayList<Model> listI = new ArrayList<>();
        listI.add(new Category("Salary", R.drawable.icon_cat_salary));
        listI.add(new Category("Selling",R.drawable.icon_cat_selling));
        listI.add(new Category("Provident Fund",R.drawable.icon_cat_providentfund));
        listI.add(new Category("Gifts",R.drawable.icon_cat_gifts));
        listI.add(new Category("Loan",R.drawable.icon_cat_loan));
        listI.add(new Category("Other",R.drawable.icon_cat_other));

        for (Model m : listI) {
            ((Category)m).setForIncome(true);
            mAllCategories.add(m);
        }

        ArrayList<Model> listE = new ArrayList<>();
        listE.add(new Category("Grocery",R.drawable.icon_cat_grocery));
        listE.add(new Category("Clothes",R.drawable.icon_cat_clothes));
        listE.add(new Category("Food",R.drawable.icon_cat_fooddrinks));
        listE.add(new Category("Shopping",R.drawable.icon_cat_shopping));
        listE.add(new Category("Travel",R.drawable.icon_cat_travel));
        listE.add(new Category("Medical",R.drawable.icon_cat_healthmedical));
        listE.add(new Category("Servant",R.drawable.icon_cat_maiddriver));
        listE.add(new Category("Rent/Mortgage",R.drawable.icon_cat_rentmortgage));
        listE.add(new Category("Household",R.drawable.icon_cat_household));
        listE.add(new Category("Bills/Utilities",R.drawable.icon_cat_billsutilities));
        listE.add(new Category("Gifts",R.drawable.icon_cat_gifts));

        for (Model m : listE) {
            ((Category)m).setForExpense(true);
            mAllCategories.add(m);
        }

        //update these categories in AddNewEntry and Split Activity also
        ArrayList<Model> listB = new ArrayList<>();
        listB.add(new Category("Cash borrowed",R.drawable.icon_cat_cashincome));
        listB.add(new Category("Paid by friend",R.drawable.icon_cat_imps));
        listB.add(new Category("Bill due",R.drawable.icon_cat_billsutilities));

        for (Model m : listB) {
            ((Category)m).setForBorrow(true);
            mAllCategories.add(m);
        }

        //update these categories in AddNewEntry and Split Activity also
        ArrayList<Model> listL = new ArrayList<>();
        listL.add(new Category("Cash lent",R.drawable.icon_cat_cashforward));
        listL.add(new Category("Paid for friend",R.drawable.icon_cat_investment));

        for (Model m : listL) {
            ((Category)m).setForLent(true);
            mAllCategories.add(m);
        }

        ArrayList<Model> listS = new ArrayList<>();
        listS.add(new Category("split 1",R.drawable.icon_cat_miscellaneous));

        for (Model m : listS) {
            ((Category)m).setForSplit(true);
            mAllCategories.add(m);
        }
        //add a category for the case when no category is selected
        mAllCategories.add(new Category("Unknown", R.drawable.icon_cat_unknown));

        return  mAllCategories;
    }

    public ArrayList<Model> getItemListByModel(int modelType) {
        switch(modelType) {
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

    public static Category getCategoryByName(Context context , String s) {
        String [] projection = DB.CATEGORY_TABLE_PROJECTION;
        String selection=DB.CATEGORY_NAME + "=" + "?";
        String [] selArgs = new String[]{""+s};
        Uri tableUri = DB.CATEGORY_TABLE_URI;

        Cursor cursor = context.getContentResolver().query(tableUri,projection,selection,selArgs,null);
        Category cat =  (Category)createLightItemFromCursor(cursor);
        return cat;

    }
}
