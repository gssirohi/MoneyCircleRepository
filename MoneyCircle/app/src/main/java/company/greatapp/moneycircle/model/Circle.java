package company.greatapp.moneycircle.model;

import android.content.ContentValues;

import org.json.JSONObject;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Circle extends Model  {
    //=====COMMON==============//
    private int dbId;
    private String uid = "";
    private String title = "";
    private int modelType;
    private String jsonString = "";
//--------------------------------//
//=====SPECIFIC==============//
private String circleName = "";
    private String contactsJson;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    //--------------------------------//

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getContactsJson() {
        return contactsJson;
    }

    public void setContactsJson(String contactsJson) {
        this.contactsJson = contactsJson;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }


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
