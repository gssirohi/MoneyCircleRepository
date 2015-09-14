package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;

import company.greatapp.moneycircle.constants.C;
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
    boolean registered;
    String serverName = "";// Name choosen by user while registering
    String serverId = "";//id provided by 3rd party server

    float borrowedAmountfromThis;
    float lentAmountToThis;



    public Contact(String name, String number) {
        setContactName(name);
        setPhone(number);
        if(name.equals(C.USER_TITLE) && number.equals(C.USER_DUMMY_NUMBER))
            setUID(C.USER_UNIQUE_ID);
        else
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

    public float getBorrowedAmountfromThis() {
        return borrowedAmountfromThis;
    }

    public void setBorrowedAmountfromThis(float borrowedAmountfromThis) {
        this.borrowedAmountfromThis = borrowedAmountfromThis;
    }

    public float getLentAmountToThis() {
        return lentAmountToThis;
    }

    public void setLentAmountToThis(float lentAmountToThis) {
        this.lentAmountToThis = lentAmountToThis;
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

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
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
    public String getCategory() {
        return null;
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
    public void setCategory(String categoryUid) {

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
        row.put(DB.REGISTERED ,(isRegistered())?1:0);
        row.put(DB.CONTACT_BORROWED_AMOUNT_FROM_THIS,""+getBorrowedAmountfromThis());
        row.put(DB.CONTACT_LENT_AMOUNT_TO_THIS,""+getLentAmountToThis());
        row.put(DB.SERVER_NAME , getServerName());
        row.put(DB.SERVER_ID, getServerId());
        row.put(DB.JSON_STRING ,getJsonString() );
        return row;
    }


}
