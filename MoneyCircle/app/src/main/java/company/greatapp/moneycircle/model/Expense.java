package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import company.greatapp.moneycircle.constants.DB;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Expense extends Model  {
    int amount;
    Date date;
    String category;
    String description;
    Uri bilUri;
    String contactName = "";//name as in contact list
    String phone = "";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getBilUri() {
        return bilUri;
    }

    public void setBilUri(Uri bilUri) {
        this.bilUri = bilUri;
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
        row.put(DB.PHONE_NUMBER,getPhone());
        row.put(DB.EXPENSE_DATE, String.valueOf(getDate()));
        row.put(DB.EXPENSE_AMOUNT,getAmount());
        row.put(DB.EXPENSE_CATEGORY, String.valueOf(getCategory()));
        row.put(DB.EXPENSE_DESCRIPTION,getDescription());
        return row;

    }

    @Override
    public void printModelData() {

    }
}
