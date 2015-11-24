package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Circle extends Model  {
    //=====COMMON==============//
    private int mDbId;
    private String mUid = "";
    private String mTitle = "";
    private int mModelType;
    private String jsonString = "";
//--------------------------------//
//=====SPECIFIC==============//
    private String mCircleName = "";
    private String mContactsJson;
    private ArrayList<Contact> mMemberList = new ArrayList<Contact>();
    private int memberCount;
    //--------------------------------//

    // In this model Title and Circle Name would be same.
    public Circle(String circleName) {
        mCircleName = circleName;
        mTitle = circleName;
        mUid = Tools.generateUniqueId();
        setModelType(MODEL_TYPE_CIRCLE);
    }

    /**
     * This Constructor is used for creating circle object from cursor.
     * @param circleName
     * @param uid
     */
    public Circle(String circleName, String uid) {
        mCircleName = circleName;
        mTitle = circleName;
        mUid = uid;
        setModelType(MODEL_TYPE_CIRCLE);
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    @Override
    protected Uri getTableUri() {
        return DB.CIRCLE_TABLE_URI;
    }
    public String getCircleName() {
        return mCircleName;
    }

    public void setCircleName(String circleName) {
        this.mCircleName = circleName;
    }

    public String getContactsJson() {
        return mContactsJson;
    }

    public void setContactsJson(String contactsJson) {
        this.mContactsJson = contactsJson;
    }

    public ArrayList<Contact> getMemberList() {
        return mMemberList;
    }

    public void setMemberList(ArrayList<Contact> memberList) {
        this.mMemberList = memberList;
    }


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
        row.put(DB.UID,getUID());
        row.put(DB.CIRCLE_NAME,getCircleName());
        row.put(DB.CIRCLE_CONTACTS_JSON,getContactsJson());
        row.put(DB.JSON_STRING,getJsonString());
        return row;
    }

    @Override
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public void setAmount(float amount) {

    }

    @Override
    public void setDateString(String dateString) {

    }

    @Override
    public void setDueDateString(String dueDateString) {

    }

    @Override
    public void setLinkedContact(Contact contact) {

    }

    @Override
    public void setCategory(Category categoryUid) {

    }

    @Override
    public String getJsonString() {
        return this.jsonString;
    }

    @Override
    public float getAmount() {
        return 0;
    }

    @Override
    public String getDateString() {
        return null;
    }

    @Override
    public String getDueDateString() {
        return null;
    }

    @Override
    public Contact getLinkedContact() {
        return null;
    }

    @Override
    public Category getCategory() {
        return null;
    }


}
