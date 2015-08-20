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
    //--------------------------------//

    // In this model Title and Circle Name would be same.
    public Circle(String circleName) {
        mCircleName = circleName;
        mTitle = circleName;
        mUid = Tools.generateUniqueId();
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
    public String getJsonString() {
        return this.jsonString;
    }


}
