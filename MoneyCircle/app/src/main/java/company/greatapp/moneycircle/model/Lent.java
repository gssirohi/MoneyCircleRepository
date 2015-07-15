package company.greatapp.moneycircle.model;

import android.content.ContentValues;

import java.util.Calendar;
import java.util.Date;

import company.greatapp.moneycircle.constants.DB;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Lent extends Model {

    //=====COMMON==============//
    private int dbId;
    private String uid = "";
    private String title = "";
    private int modelType;
    private String jsonString = "";
    //--------------------------------//

    //=========== SPECIFICS ==============//
    private int category;
    private String description = "";
    private int amount;
    private String dateString="";
    private Date date;
    private Date dueDate;
    private String dueDateString = "";
    private String linkedContactJson;
    private Contact linkedContact;

    private boolean isLinkedWithSplit;
    private Split linkedSplit;
    private String linkedSplitJson;
    //---------------------------------//

    public boolean isLinkedWithSplit() {
        return isLinkedWithSplit;
    }

    public void setIsLinkedWithSplit(boolean isLinkedWithSplit) {
        this.isLinkedWithSplit = isLinkedWithSplit;
    }

    public Split getLinkedSplit() {
        return linkedSplit;
    }

    public void setLinkedSplit(Split linkedSplit) {
        this.linkedSplit = linkedSplit;
    }

    public String getLinkedSplitJson() {
        return linkedSplitJson;
    }

    public void setLinkedSplitJson(String linkedSplitJson) {
        this.linkedSplitJson = linkedSplitJson;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
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
        this.title = title;
    }

    @Override
    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    @Override
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    @Override
    public void setUID(String uid) {
        this.uid = uid;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getModelType() {
        return modelType;
    }

    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public int getDbId() {
        return dbId;
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

        row.put(DB.IS_LINKED_WITH_SPLIT, isLinkedWithSplit()?1:0);
        row.put(DB.LINKED_SPLIT_JSON, getLinkedSplitJson());

        row.put(DB.DUE_DATE_STRING, getDueDateString());

        row.put(DB.DATE_STRING ,getDateString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate());

        row.put(DB.DATE , cal.get(Calendar.DATE));
        row.put(DB.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        row.put(DB.WEEK_OF_MONTH, cal.get(Calendar.WEEK_OF_MONTH));
        row.put(DB.MONTH , cal.get(Calendar.MONTH));
        row.put(DB.YEAR , cal.get(Calendar.YEAR));

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
