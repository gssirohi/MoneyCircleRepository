package company.greatapp.moneycircle.model;

import android.content.ContentValues;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Notification extends Model {

    String contactName = "";//name as in contact list
    String phone = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String notificationDescription) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String description;
    String category;


    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setModelType(int modelType) {

    }

    @Override
    public void setDbId(int dbId) {

    }

    @Override
    public void setUID(String uid) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getModelType() {
        return 0;
    }

    @Override
    public String getUID() {
        return null;
    }

    @Override
    public int getDbId() {
        return 0;
    }

    @Override
    public ContentValues getContentValues() {
        return null;
    }

    @Override
    public void printModelData() {

    }
}
