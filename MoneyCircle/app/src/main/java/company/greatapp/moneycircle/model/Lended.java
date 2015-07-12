package company.greatapp.moneycircle.model;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Lended extends Model {
    int amount;
    Contact toContact;
    String contactName = "";//name as in contact list
    String phone = "";
    String date;
    Date dueDate;
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



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Contact getToContact() {
        return toContact;
    }

    public void setToContact(Contact toContact) {
        this.toContact = toContact;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
