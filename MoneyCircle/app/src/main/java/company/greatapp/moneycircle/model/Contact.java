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
    String contactName = "";//name as in contact list
    String phone = "";//phone number in contact list
    String email = "";
    Bitmap imageBitmap;
    Uri imageUri ;
    String serverName = "";// Name choosen by user while registering
    String serverId = "";//id provided by 3rd party server
    JSONObject jsonCircles;
    int registered;
    int circleCount;
    String circlesJsonString = "";
    ArrayList<Circle> circles;
    private String uid = "";
    private String title = "";
    private int modelType;
    private int dbId;

    public Contact(String name, String number) {
        this.contactName = name;
        this.phone = number;
        setUID(Tools.generateUniqueId());
    }


    public Contact(String contactName) {
        this.contactName = contactName;
        this.phone = "na";
        setUID(Tools.generateUniqueId());
    }

    public Contact() {
        this.contactName = "unammed";
        this.phone = "na";
        setUID(Tools.generateUniqueId());
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
        return this.title;
    }

    @Override
    public int getModelType() {
        return this.modelType;
    }


    @Override
    public String getUID() {
        return this.uid;
    }

    @Override
    public int getDbId() {
        return this.dbId;
    }

    public int getCircleCount() {
        return this.circleCount;
    }

    public void setCircleCount(int circleCount) {
        this.circleCount = circleCount;
    }

    public String getCirclesJsonString() {
        return circlesJsonString;
    }

    public void setCirclesJsonString(String circlesJsonString) {
        this.circlesJsonString = circlesJsonString;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }



    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public void setCircles(ArrayList<Circle> circles) {
        this.circles = circles;
    }

    public JSONObject getJsonCircles() {
        return jsonCircles;
    }

    public void setJsonCircles(JSONObject jsonCircles) {
        this.jsonCircles = jsonCircles;
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

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
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

    @Override
    public ContentValues getContentValues() {
        ContentValues row = new ContentValues();
        row.put(DB.NAME , getContactName());
        row.put(DB.UID , getUID());
        row.put(DB.SERVER_NAME , getServerName());
        row.put(DB.SERVER_ID, getServerId());
        row.put(DB.PHONE_NUMBER , getPhone());
        row.put(DB.IMAGE_URI , ""+getImageUri());
        row.put(DB.EMAIL , getEmail());
        row.put(DB.REGISTERED ,getRegistered());
        row.put(DB.CIRCLE_COUNT , getCircleCount());
        row.put(DB.CIRCLE_JSON_STRING , getCirclesJsonString());
        return row;
    }

    @Override
    public void printModelData() {

    }
}
