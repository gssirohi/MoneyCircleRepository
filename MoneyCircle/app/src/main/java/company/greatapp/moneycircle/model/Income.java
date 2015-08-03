package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;

import java.util.Calendar;
import java.util.Date;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Income extends Model {
    //=====COMMON==============//
    private int dbId;
    private String uid = "";
    private String title = "";
    private int modelType;
    private String jsonString = "";
    //--------------------------------//

    //=========== SPECIFICS ==============//
    private int mCategory;
    private String mDescription = "";
    private float mAmount;
    private String mDateString ="";
    private Date date;

    public Income() {}      // Empty Constructor

    // TODO Need to update this constructor implementation properly
    public Income(String title, int amount, int category, String date) {

        setTitle(title);
        setAmount(amount);
        setCategory(category);
        setDateString(date);
        setUID(Tools.generateUniqueId());
        setModelType(Category.MODEL_TYPE_INCOME);
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        this.mCategory = category;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float amount) {
        this.mAmount = amount;
    }

    public String getDateString() {
        return mDateString;
    }

    public void setDateString(String dateString) {
        this.mDateString = dateString;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    //-------------------------------------//

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
        row.put(DB.TITLE , getTitle());
        row.put(DB.CATEGORY , getCategory());
        row.put(DB.DESCRIPTION , getDescription());
        row.put(DB.AMOUNT, ""+getAmount());

        row.put(DB.DATE_STRING ,getDateString());
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(getDate());
//
//        row.put(DB.DATE , cal.get(Calendar.DATE));
//        row.put(DB.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
//        row.put(DB.WEEK_OF_MONTH, cal.get(Calendar.WEEK_OF_MONTH));
//        row.put(DB.MONTH , cal.get(Calendar.MONTH));
//        row.put(DB.YEAR , cal.get(Calendar.YEAR));

        row.put(DB.JSON_STRING ,getJsonString() );
        return row;
    }

    @Override
    protected Uri getTableUri() {
        return DB.INCOME_TABLE_URI;
    }

    @Override
    public void setJsonString(String jsonString) {
       this.jsonString = jsonString;
    }

    @Override
    public String getJsonString() {
        return jsonString;
    }

}
