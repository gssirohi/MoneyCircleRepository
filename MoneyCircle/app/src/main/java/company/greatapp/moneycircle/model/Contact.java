package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;

import org.json.JSONObject;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by Gyanendrasingh on 26-06-2015.
 */
public class Contact extends Model {

    //=====COMMON==============//
    private int dbId;
    private String uid = "";
    private String title = "";
    private int modelType;
    private String jsonString = "";
//--------------------------------//


    //=========SPECIFIC============//
    String contactName = "";//name as in contact list
    String phone = "";//phone number in contact list
    String email = "";
    Bitmap imageBitmap;
    Uri imageUri ;
    int registered;
    String serverName = "";// Name choosen by user while registering
    String serverId = "";//id provided by 3rd party server




    public Contact(String name, String number) {
        setContactName(name);
        setPhone(number);
        setUID(Tools.generateUniqueId());
    }


    public Contact(String name) {
        setContactName(name);
        setPhone("na");
        setUID(Tools.generateUniqueId());
    }

    public Contact() {
        setContactName("unnamed");
        setPhone("na");
        setUID(Tools.generateUniqueId());
    }

    @Override
    protected Uri getTableUri() {
        return DB.CONTACT_TABLE_URI;
    }
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
        setTitle(contactName);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
    @Override
    public String getJsonString() {
        return jsonString;
    }
    @Override
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
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

        row.put(DB.UID , getUID());
        row.put(DB.NAME , getContactName());
        row.put(DB.PHONE_NUMBER , getPhone());
        row.put(DB.EMAIL , getEmail());
        row.put(DB.CONTACT_IMAGE_URI , ""+getImageUri());
        row.put(DB.REGISTERED ,getRegistered());
        row.put(DB.SERVER_NAME , getServerName());
        row.put(DB.SERVER_ID, getServerId());
        row.put(DB.JSON_STRING ,getJsonString() );
        return row;
    }


}
