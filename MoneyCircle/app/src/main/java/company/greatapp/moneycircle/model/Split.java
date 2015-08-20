package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 7/15/2015.
 */
public class Split extends Model {
    //=====COMMON==============//
    private int dbId;
    private String uid = "";
    private String title = "";
    private int modelType;
    private String jsonString = "";
    //--------------------------------//

    //=========== SPECIFICS ==============//
    private String category;
    private String description = "";
    private float amount;
    private String dateString="";
    private Date date;
    private Date dueDate;
    private String dueDateString = "";

    private ArrayList<Contact> linkedParticipants = new ArrayList<Contact>();
    private ArrayList<Contact> linkedContacts = new ArrayList<Contact>();
    private Circle linkedCircle;
    private Expense linkedExpense;
    private ArrayList<Lent> linkedLents = new ArrayList<Lent>();
    private String linkedContactsJson = "";

    public Split(){
        setUID(Tools.generateUniqueId());
    }

    public ArrayList<Contact> getLinkedParticipants() {
        return linkedParticipants;
    }

    public void setLinkedParticipants(ArrayList<Contact> linkedParticipants) {
        this.linkedParticipants = linkedParticipants;
    }

    public Circle getLinkedCircle() {
        return linkedCircle;
    }

    public void setLinkedCircle(Circle linkedCircle) {
        this.linkedCircle = linkedCircle;
    }

    public String getLinkedCircleJson() {
        return linkedCircleJson;
    }

    public void setLinkedCircleJson(String linkedCircleJson) {
        this.linkedCircleJson = linkedCircleJson;
    }

    public String getLinkedParticipantsJson() {
        return linkedParticipantsJson;
    }

    public void setLinkedParticipantsJson(String linkedParticipantsJson) {
        this.linkedParticipantsJson = linkedParticipantsJson;
    }

    private String linkedCircleJson = "";
    private String linkedParticipantsJson = "";
    private String linkedExpenseJson = "";
    private String linkedLentsJson = "";

    private int totalParticipants;
    //---------------------------------//

    @Override
    protected Uri getTableUri() {
        return DB.SPLIT_TABLE_URI;
    }
    public int getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(int totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public ArrayList<Contact> getLinkedContacts() {
        return linkedContacts;
    }

    public void setLinkedContacts(ArrayList<Contact> linkedContacts) {
        this.linkedContacts = linkedContacts;
    }

    public Expense getLinkedExpense() {
        return linkedExpense;
    }

    public void setLinkedExpense(Expense linkedExpense) {
        this.linkedExpense = linkedExpense;
    }

    public ArrayList<Lent> getLinkedLents() {
        return linkedLents;
    }

    public void setLinkedLents(ArrayList<Lent> linkedLents) {
        this.linkedLents = linkedLents;
    }

    public String getLinkedContactsJson() {
        return linkedContactsJson;
    }

    public void setLinkedContactsJson(String linkedContactsJson) {
        this.linkedContactsJson = linkedContactsJson;
    }

    public String getLinkedExpenseJson() {
        return linkedExpenseJson;
    }

    public void setLinkedExpenseJson(String linkedExpenseJson) {
        this.linkedExpenseJson = linkedExpenseJson;
    }

    public String getLinkedLentsJson() {
        return linkedLentsJson;
    }

    public void setLinkedLentsJson(String linkedLentsJson) {
        this.linkedLentsJson = linkedLentsJson;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDateString() {
        return dueDateString;
    }

    public void setDueDateString(String dueDateString) {
        this.dueDateString = dueDateString;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
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
        row.put(DB.AMOUNT, getAmount());
        row.put(DB.SPLIT_TOTAL_PARTICIPANTS,getTotalParticipants());
        row.put(DB.DUE_DATE_STRING, getDueDateString());

        row.put(DB.SPLIT_LINKED_CONTACTS_JSON,getLinkedContactsJson());
        row.put(DB.SPLIT_LINKED_CIRCLE_JSON,getLinkedCircleJson());
        row.put(DB.SPLIT_LINKED_PARTICIPANTS_JSON,getLinkedParticipantsJson());

        row.put(DB.SPLIT_LINKED_EXPENSE_JSON,getLinkedExpenseJson());
        row.put(DB.SPLIT_LINKED_LENTS_JSON,getLinkedLentsJson());

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
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public String getJsonString() {
        return jsonString;
    }

    public String printModelData(){
        String msg = "";
        msg = "\n=======================SPLIT MODEL======================\n";
        msg = msg+"DBID["+getDbId()+"] : UID["+getUID()+"]\n";
        msg = msg+"TITLE : "+getTitle()+"\n";
        msg = msg+"AMOUNT : "+getAmount()+"\n";
        msg = msg+"TOTAL MEMBER : "+getTotalParticipants()+"\n";
        msg = msg+"CATEGORY : "+getCategory()+"\n";
        msg = msg+"DESC : "+getDescription()+"\n";
        msg = msg+"DATE_S : "+getDateString()+"\n";

        msg = msg+"PARTICIPANTS JSON : "+getLinkedParticipantsJson()+"\n";
        msg = msg+"===========PARTICIPANTS=================\n";
        for(Contact c: getLinkedParticipants()) {
            msg = msg+"   PRTICIPANT : "+(c!= null?c.printModelData():"null participant")+"\n";
        }
        msg = msg+"----------------------------------------\n";
        msg = msg+"\n";

        msg = msg+"CONTACTS JSON : "+getLinkedContactsJson()+"\n";
        msg = msg+"===========CONTACTS=================\n";
        for(Contact c: getLinkedContacts()) {
            msg = msg+"   CONTACT : "+(c!=null?c.printModelData():"null contact")+"\n";
        }
        msg = msg+"----------------------------------------\n";
        msg = msg+"\n";

        msg = msg+"CIRCLE JSON : "+getLinkedCircleJson()+"\n";
        msg = msg+"===========CIRCLE=================\n";
        msg = msg+"   CIRCLE : "+(getLinkedCircle()!=null?getLinkedCircle().printModelData():"null circle")+"\n";
        msg = msg+"----------------------------------------\n";
        msg = msg+"\n";

        msg = msg+"EXPENSE JSON : "+getLinkedExpenseJson()+"\n";
        msg = msg+"===========EXPENSE=================\n";
        msg = msg+"   EXPENSE : "+(getLinkedExpense()!=null?getLinkedExpense().printModelData():"null expense")+"\n";
        msg = msg+"----------------------------------------\n";
        msg = msg+"\n";

        msg = msg+"LENTS JSON : "+getLinkedLentsJson()+"\n";
        msg = msg+"===========LENTS=================\n";
        for(Lent l: getLinkedLents()) {
            msg = msg+"   LENT : "+(l!=null?l.printModelData():"null lent")+"\n";
        }
        msg = msg+"----------------------------------------\n";
        msg = msg+"\n";

        msg = msg+"-----------------------------------------------\n";
        Log.d("SPLIT", msg);
        return msg;
    }


}
