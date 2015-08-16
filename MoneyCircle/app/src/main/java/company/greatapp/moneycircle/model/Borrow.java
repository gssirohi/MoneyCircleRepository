package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;

import java.util.Date;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Borrow extends Model {


    //=====COMMON==============//
    private int mDbId;
    private String mUid = "";
    private String mTitle = "";
    private int mModelType;
    private String jsonString = "";
    //--------------------------------//

    //=========== SPECIFICS ==============//

    private String mCategory;
    private String mDescription = "";
    private float mAmount;
    private String mDateString ="";
    private Date date;
    private Date dueDate;
    private String dueDateString = "";
    private String linkedContactJson;
    private Contact linkedContact;

    public Borrow() {      // Empty Constructor
        setUID(Tools.generateUniqueId());
    }

    /**
     * This Constructor is only used to create expense object whose UID need to be load from DB.
     * @param dbId
     * @param uid
     */
    public Borrow(int dbId, String uid) {
        mDbId = dbId;
        mUid = uid;
    }

    @Override
    protected Uri getTableUri() {
        return DB.BORROW_TABLE_URI;
    }
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDateString() {
        return dueDateString;
    }

    public void setDueDateString(String dueDateString) {
        this.dueDateString = dueDateString;
    }

    public String getLinkedContactJson() {
        return linkedContactJson;
    }

    public void setLinkedContactJson(String linkedContactJson) {
        this.linkedContactJson = linkedContactJson;
    }

    public Contact getLinkedContact() {
        return linkedContact;
    }

    public void setLinkedContact(Contact linkedContact) {
        this.linkedContact = linkedContact;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float amount) {
        this.mAmount = amount;
    }

    public String getDateString() {
        return mDateString;
    }

    public void setDateString(String dateString) {
        this.mDateString = dateString;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    //-------------------------------------//

    @Override
    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public void setModelType(int modelType) {
        this.mModelType = modelType;
    }

    @Override
    public void setDbId(int dbId) {
        this.mDbId = dbId;
    }

    @Override
    public void setUID(String uid) {
        this.mUid = uid;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public int getModelType() {
        return mModelType;
    }

    @Override
    public String getUID() {
        return mUid;
    }

    @Override
    public int getDbId() {
        return mDbId;
    }

    @Override
    public ContentValues getContentValues() {

        ContentValues row = new ContentValues();
        row.put(DB.UID , getUID());
        row.put(DB.TITLE , getTitle());
        row.put(DB.CATEGORY , getCategory());
        row.put(DB.DESCRIPTION , getDescription());
        row.put(DB.AMOUNT, getAmount());

        row.put(DB.LINKED_CONTACT_JSON, getLinkedContactJson());

        row.put(DB.DUE_DATE_STRING, getDueDateString());

        row.put(DB.DATE_STRING ,getDateString());
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(getDate());
//
//        row.put(DB.DATE , cal.get(Calendar.DATE));
//        row.put(DB.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
//        row.put(DB.WEEK_OF_MONTH, cal.get(Calendar.WEEK_OF_MONTH));
//        row.put(DB.MONTH , cal.get(Calendar.MONTH));
//        row.put(DB.YEAR , cal.get(Calendar.YEAR));

        row.put(DB.JSON_STRING ,getJsonString() );
        return row;
    }

    @Override
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public String getJsonString() {
        return jsonString;
    }

}
