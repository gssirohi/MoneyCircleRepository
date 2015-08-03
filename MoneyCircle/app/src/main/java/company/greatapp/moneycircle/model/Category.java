package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Category extends Model  {

    private String mCategoryName;
    private int mCategoryType;
    private int mCategoryId;        // Need to set this categoryId as unique, we will save this value in Income,expense, table and all
    private int mModelType;
    private int mDbId;
    private String mUId;
    private String mJsonString;

    public Category(String categoryName, int categoryType) {
        setTitle(categoryName);
        setUID(Tools.generateUniqueId());
        mCategoryType = categoryType;
        mModelType = Model.MODEL_TYPE_CATEGORY;
        // mCategoryId =      // TODO Need to set this value by deciding the unique number logic
    }

    public Category(String categoryName,int dbId, String uid, int categoryType/*,int categoryId*/) {
        mCategoryName = categoryName;
        mDbId = dbId;
        mUId = uid;
        mCategoryType = categoryType;
        mModelType = Model.MODEL_TYPE_CATEGORY;
        //mCategoryId = categoryId;
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

    private void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(DB.UID, getUID());
        values.put(DB.CATEGORY_NAME, getTitle());
        values.put(DB.CATEGORY_TYPE, getCategoryType());
        return values;
    }
}
