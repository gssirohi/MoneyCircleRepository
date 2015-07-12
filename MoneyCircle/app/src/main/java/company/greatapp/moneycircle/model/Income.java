package company.greatapp.moneycircle.model;

import android.content.ContentValues;

import org.json.JSONObject;

import java.util.Date;

import company.greatapp.moneycircle.constants.DB;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Income extends Model {

    String contactName = "";
    String phone = "";
    String IncomeJson = "";
    String IncomeType = "";
    int amount;
    String date="";
    String category ="";
    String description;
    private int modelType;
    private int dbId;
    private String title ="";
    private String uid = "";

    public String getIncomeJson() {
        return IncomeJson;
    }

    public void setIncomeJson(String incomeJson) {
        this.IncomeJson = incomeJson;
    }






    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }




    @Override
    public void setTitle(String title) {
        this.title = title;
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



    public String getIncomeType() {
        return IncomeType;
    }

    public void setIncomeType(String incomeType) {
        this.IncomeType = incomeType;
    }




     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
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





     @Override
     public void setModelType(int modelType) {
          this.modelType = modelType;
     }

     @Override
     public void setDbId(int dbId) {
          this.dbId = dbId ;

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
          return uid;
     }

     @Override
     public int getDbId() {
          return this.dbId;
     }

     @Override
     public ContentValues getContentValues() {

         ContentValues row = new ContentValues();
         row.put(DB.NAME, getContactName());
         row.put(DB.UID,getUID());
         row.put(DB.PHONE_NUMBER,getPhone());
         row.put(DB.INCOME_TYPE,getIncomeType());
         row.put(DB.INCOME_CATEGORY,getCategory());
         row.put(DB.INCOME_JSON_STRING,getIncomeJson());
         row.put(DB.INCOME_AMOUNT,getAmount());
         row.put(DB.INCOME_DATE, String.valueOf(getDate()));
         row.put(DB.INCOME_DESCRIPTION,getDescription());

         return row;
     }

     @Override
     public void printModelData() {

     }
}
