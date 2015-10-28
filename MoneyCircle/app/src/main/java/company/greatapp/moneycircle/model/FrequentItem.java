package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by Prateek on 24-10-2015.
 */
public class FrequentItem extends Model{

    private int dbId;
    private String UID = "";
    private String title = "";
    private Category category;
    private float amount;
    private String description = "";
    private String dateString ="";
    private String jsonString = "";
    private boolean isSelected = false;

    public FrequentItem() {
        setUID(Tools.generateUniqueId());
    }

    public FrequentItem(int dbId, String uid) {
        this.dbId = dbId;
        this.UID = uid;
    }

    public FrequentItem(Context context, Cursor cursor) {
        dbId  =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        UID = cursor.getString(cursor.getColumnIndex(DB.UID));
        title = cursor.getString(cursor.getColumnIndex(DB.TITLE));
        String categoryUid = cursor.getString(cursor.getColumnIndex(DB.CATEGORY));
        category = (Category) Tools.getDbInstance(context, categoryUid, Model.MODEL_TYPE_CATEGORY);
        amount = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.AMOUNT)));
        description = cursor.getString(cursor.getColumnIndex(DB.DESCRIPTION));
        jsonString = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        dateString = cursor.getString(cursor.getColumnIndex(DB.DATE_STRING));
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getModelType() {
        return -1;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setModelType(int modelType) {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateString() {
        return dateString;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String getDueDateString() {
        return null;
    }

    @Override
    public Contact getLinkedContact() {
        return null;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public void setDueDateString(String dueDateString) {

    }

    @Override
    public void setLinkedContact(Contact contact) {

    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public ContentValues getContentValues() {

        ContentValues row = new ContentValues();
        row.put(DB.UID , getUID());
        row.put(DB.TITLE , getTitle());
        row.put(DB.CATEGORY , getCategory().getUID());
        row.put(DB.DESCRIPTION, getDescription());
        row.put(DB.AMOUNT, getAmount());

        row.put(DB.DATE_STRING ,getDateString());

        row.put(DB.JSON_STRING ,getJsonString() );
        return row;
    }

    @Override
    protected Uri getTableUri() {
        return DB.FREQUENT_ITEM_TABLE_URI;
    }

}
