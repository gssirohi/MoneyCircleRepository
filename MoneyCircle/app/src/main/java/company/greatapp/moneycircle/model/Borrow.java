package company.greatapp.moneycircle.model;

import android.content.ContentValues;

import java.util.Date;

import company.greatapp.moneycircle.constants.DB;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Borrow extends Model {

    Contact fromContact;
    String date;
    String dueDate;
    String description;
    String category;
    int amount;
    String phone = "";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public Contact getFromContact() {
        return fromContact;
    }

    public void setFromContact(Contact fromContact) {
        this.fromContact = fromContact;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String borrowDescription) {
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

        ContentValues row = new ContentValues();
        row.put(DB.UID,getUID());
        row.put(DB.NAME, String.valueOf(getFromContact()));
        row.put(DB.PHONE_NUMBER,getPhone());
        row.put(DB.BORROW_CATEGORY, String.valueOf(getCategory()));
        row.put(DB.BORROW_AMOUNT,getAmount());
        row.put(DB.BORROW_DATE, String.valueOf(getDate()));
        row.put(DB.BORROW_DUE_DATE, String.valueOf(getDate()));
        row.put(DB.BORROW_DESCRIPTION,getDescription());

        return row;
    }

    @Override
    public void printModelData() {

    }
}
