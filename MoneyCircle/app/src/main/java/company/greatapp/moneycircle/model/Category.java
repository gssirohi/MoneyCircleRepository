package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Category extends Model  {

    public static final int SINGLE_MODEL = 1;
    public static final int MULTIPLE_MODEL = 2;

    private String mCategoryName;
    private int mCategoryType;
    private int mModelType;
    private int mDbId;
    private String mUId;
    private String mJsonString;


    private boolean forIncome;
    private boolean forExpense;
    private boolean forBorrow;
    private boolean forLent;
    private boolean forSplit;


    public boolean isForSplit() {
        return forSplit;
    }

    public void setForSplit(boolean forSplit) {
        this.forSplit = forSplit;
        //this should be the part of Lent as well as expense
        if(forSplit) {
            this.forExpense = true;
            this.forLent = true;
        }
    }

    public boolean isForLent() {
        return forLent;
    }

    public void setForLent(boolean forLent) {
        this.forLent = forLent;
    }

    public boolean isForBorrow() {
        return forBorrow;
    }

    public void setForBorrow(boolean forBorrow) {
        this.forBorrow = forBorrow;
    }

    public boolean isForExpense() {
        return forExpense;
    }

    public void setForExpense(boolean forExpense) {
        this.forExpense = forExpense;
    }

    public boolean isForIncome() {
        return forIncome;
    }

    public void setForIncome(boolean forIncome) {
        this.forIncome = forIncome;
    }

    public Category(String categoryName, int categoryType) {
        mCategoryName = categoryName;
        setUID(Tools.generateUniqueId());
        mCategoryType = categoryType;
        mModelType = Model.MODEL_TYPE_CATEGORY;
    }

    public Category(String categoryName, String uid) {
        mCategoryName = categoryName;
        mUId = uid;
        mModelType = Model.MODEL_TYPE_CATEGORY;
    }

    public Category(String categoryName,int dbId, String uid, int categoryType) {
        mCategoryName = categoryName;
        mDbId = dbId;
        mUId = uid;
        mCategoryType = categoryType;
        mModelType = Model.MODEL_TYPE_CATEGORY;
    }

    @Override
    protected Uri getTableUri() {
        return DB.CATEGORY_TABLE_URI;
    }

    @Override
    public void setTitle(String title) {
        mCategoryName = title;
    }

    @Override
    public void setModelType(int modelType) {
        mModelType = modelType;
    }

    @Override
    public void setDbId(int dbId) {
        mDbId = dbId;
    }

    @Override
    public void setUID(String uid) {
        mUId = uid;
    }

    @Override
    public void setJsonString(String jsonString) {
        mJsonString = jsonString;
    }

    @Override
    public String getTitle() {
        return mCategoryName;
    }

    @Override
    public int getModelType() {
        return mModelType;
    }

    public void setCategoryType(int categoryType) {
        mCategoryType = categoryType;
    }

    public int getCategoryType() {
        return mCategoryType;
    }

    @Override
    public String getUID() {
        return mUId;
    }

    @Override
    public int getDbId() {
        return mDbId;
    }

    @Override
    public String getJsonString() {
        return mJsonString;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(DB.UID, getUID());
        values.put(DB.CATEGORY_NAME, getTitle());
        values.put(DB.CATEGORY_TYPE, getCategoryType());

        values.put(DB.CATEGORY_FOR_INCOME, isForIncome()?1:0);
        values.put(DB.CATEGORY_FOR_EXPENSE, isForExpense()?1:0);
        values.put(DB.CATEGORY_FOR_BORROW, isForBorrow()?1:0);
        values.put(DB.CATEGORY_FOR_LENT, isForLent()?1:0);
        values.put(DB.CATEGORY_FOR_SPLIT, isForSplit()?1:0);

        return values;
    }

    @Override
    public String toString() {
        return mCategoryName;
    }
}
