package company.greatapp.moneycircle.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.tools.GreatJSON;

/**
 * Created by Gyanendrasingh on 8/21/2015.
 */
public class AccountRegister {

    private int registerType;

    private float totalOfCurrentDay;
    private float totalOfCurrentWeek;
    private float totalOfCurrentMonth;
    private float totalOfCurrentYear;

    private float totalOfLastDay;
    private float totalOfLastWeek;
    private float totalOflastMonth;
    private float totalOfLastYear;

    private float totalTillNow;

    private ArrayList<Model> upComingEventsOfToday;
    private ArrayList<Model> upComingEventsOfWeek;
    private ArrayList<Model> upComingEventsOfMonth;

    private ArrayList<Model> topItemsOfMonth;
    private ArrayList<Model> topItemsOfYear;
    private ArrayList<Model> topItemsOfTotal;

    public AccountRegister(int type) {
        setRegisterType(type);
    }

    private AccountRegister() {
        //This constructor now can't be used as it is private now.
    }

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    public float getTotalOfCurrentDay() {
        return totalOfCurrentDay;
    }

    public void setTotalOfCurrentDay(float totalOfCurrentDay) {
        this.totalOfCurrentDay = totalOfCurrentDay;
    }

    public float getTotalOfCurrentWeek() {
        return totalOfCurrentWeek;
    }

    public void setTotalOfCurrentWeek(float totalOfCurrentWeek) {
        this.totalOfCurrentWeek = totalOfCurrentWeek;
    }

    public float getTotalOfCurrentMonth() {
        return totalOfCurrentMonth;
    }

    public void setTotalOfCurrentMonth(float totalOfCurrentMonth) {
        this.totalOfCurrentMonth = totalOfCurrentMonth;
    }

    public float getTotalOfCurrentYear() {
        return totalOfCurrentYear;
    }

    public void setTotalOfCurrentYear(float totalOfCurrentYear) {
        this.totalOfCurrentYear = totalOfCurrentYear;
    }

    public float getTotalOfLastDay() {
        return totalOfLastDay;
    }

    public void setTotalOfLastDay(float totalOfLastDay) {
        this.totalOfLastDay = totalOfLastDay;
    }

    public float getTotalOfLastWeek() {
        return totalOfLastWeek;
    }

    public void setTotalOfLastWeek(float totalOfLastWeek) {
        this.totalOfLastWeek = totalOfLastWeek;
    }

    public float getTotalOfLastMonth() {
        return totalOflastMonth;
    }

    public void setTotalOflastMonth(float totalOflastMonth) {
        this.totalOflastMonth = totalOflastMonth;
    }

    public float getTotalOfLastYear() {
        return totalOfLastYear;
    }

    public void setTotalOfLastYear(float totalOfLastYear) {
        this.totalOfLastYear = totalOfLastYear;
    }

    public float getTotalTillNow() {
        return totalTillNow;
    }

    public void setTotalTillNow(float totalTillNow) {
        this.totalTillNow = totalTillNow;
    }

    public ArrayList<Model> getUpComingEventsOfToday() {
        return upComingEventsOfToday;
    }

    public void setUpComingEventsOfToday(ArrayList<Model> upComingEventsOfToday) {
        this.upComingEventsOfToday = upComingEventsOfToday;
    }

    public ArrayList<Model> getUpComingEventsOfWeek() {
        return upComingEventsOfWeek;
    }

    public void setUpComingEventsOfWeek(ArrayList<Model> upComingEventsOfWeek) {
        this.upComingEventsOfWeek = upComingEventsOfWeek;
    }

    public ArrayList<Model> getUpComingEventsOfMonth() {
        return upComingEventsOfMonth;
    }

    public void setUpComingEventsOfMonth(ArrayList<Model> upComingEventsOfMonth) {
        this.upComingEventsOfMonth = upComingEventsOfMonth;
    }

    public ArrayList<Model> getTopItemsOfMonth() {
        return topItemsOfMonth;
    }

    public void setTopItemsOfMonth(ArrayList<Model> topItemsOfMonth) {
        this.topItemsOfMonth = topItemsOfMonth;
    }

    public ArrayList<Model> getTopItemsOfYear() {
        return topItemsOfYear;
    }

    public void setTopItemsOfYear(ArrayList<Model> topItemsOfYear) {
        this.topItemsOfYear = topItemsOfYear;
    }

    public ArrayList<Model> getTopItemsOfTotal() {
        return topItemsOfTotal;
    }

    public void setTopItemsOfTotal(ArrayList<Model> topItemsOfTotal) {
        this.topItemsOfTotal = topItemsOfTotal;
    }

    public ContentValues getContentValues() {
        ContentValues row = new ContentValues();

        row.put(DB.ACCOUNT_REGISTER_TYPE,getRegisterType());

        row.put(DB.ACCOUNT_TOTAL_CURRENT_DAY,""+getTotalOfCurrentDay());
        row.put(DB.ACCOUNT_TOTAL_CURRENT_WEEK,""+getTotalOfCurrentWeek());
        row.put(DB.ACCOUNT_TOTAL_CURRENT_MONTH,""+getTotalOfCurrentMonth());
        row.put(DB.ACCOUNT_TOTAL_CURRENT_YEAR,""+getTotalOfCurrentYear());

        row.put(DB.ACCOUNT_TOTAL,""+getTotalTillNow());

        row.put(DB.ACCOUNT_TOTAL_LAST_DAY,""+getTotalOfLastDay());
        row.put(DB.ACCOUNT_TOTAL_LAST_WEEK,""+getTotalOfLastWeek());
        row.put(DB.ACCOUNT_TOTAL_LAST_MONTH,""+getTotalOfLastMonth());
        row.put(DB.ACCOUNT_TOTAL_LAST_YEAR,""+getTotalOfLastYear());

        JSONArray array1 = GreatJSON.getJsonArrayForModelList(getUpComingEventsOfToday());
        JSONArray array2 = GreatJSON.getJsonArrayForModelList(getUpComingEventsOfWeek());
        JSONArray array3 = GreatJSON.getJsonArrayForModelList(getUpComingEventsOfMonth());
        JSONArray array4 = GreatJSON.getJsonArrayForModelList(getTopItemsOfMonth());
        JSONArray array5 = GreatJSON.getJsonArrayForModelList(getTopItemsOfYear());
        JSONArray array6 = GreatJSON.getJsonArrayForModelList(getTopItemsOfTotal());


        row.put(DB.ACCOUNT_UPCOMINGS_DAY, "" + ((array1 != null) ? array1.toString() : ""));
        row.put(DB.ACCOUNT_UPCOMINGS_WEEK, "" + ((array2 != null) ? array2.toString() : ""));
        row.put(DB.ACCOUNT_UPCOMINGS_MONTH,""+  ((array3 != null) ?array3.toString():""));

        row.put(DB.ACCOUNT_TOPITEMS_MONTH,""+ ((array4 != null)?array4.toString():""));
        row.put(DB.ACCOUNT_TOPITEMS_YEAR,""+ ((array5 != null)?array5.toString():""));
        row.put(DB.ACCOUNT_TOPITEMS_TOTAL,""+ ((array6 != null)?array6.toString():""));

        return row;
    }

    public Uri insertItemInDB(Context context) {
        if(context == null)
       Log.d("SPLIT","context is NULL");
        ContentResolver resolver = context.getContentResolver();
        if(resolver == null)
            Log.d("SPLIT","context is NULL");
        ContentValues values = getContentValues();
        return resolver.insert(DB.ACCOUNT_TABLE_URI, values);
    }

    public int updateItemInDb(Context context) {
        String where = DB.ACCOUNT_REGISTER_TYPE + "=" + getRegisterType();
        String[] selectionArgs = null;
        Log.d("Split", "ACCOUNT REGISTER DB: UPDATING :----->");
        ContentValues values = getContentValues();
        return context.getContentResolver().update(DB.ACCOUNT_TABLE_URI, values, where, selectionArgs);
    }

    public void printRegister() {
        Log.i("SPLIT","===============================REGISTER TYPE : "+getRegisterType()+"===================================");
        Log.i("SPLIT","Total of CURRENT DAY : "+getTotalOfCurrentDay());
        Log.i("SPLIT","Total of CURRENT WEEK : "+getTotalOfCurrentWeek());
        Log.i("SPLIT","Total of CURRENT MONTH : "+getTotalOfCurrentMonth());
        Log.i("SPLIT","Total of CURRENT YEAR : "+getTotalOfCurrentYear());

        Log.i("SPLIT","Total of LAST DAY : "+getTotalOfLastDay());
        Log.i("SPLIT","Total of LAST WEEK : "+getTotalOfLastWeek());
        Log.i("SPLIT","Total of LAST MONTH : "+getTotalOfLastMonth());
        Log.i("SPLIT","Total of LAST YEAR : "+getTotalOfLastYear());

        Log.i("SPLIT","Total Till Now : "+getTotalTillNow());

        Log.i("SPLIT","Upcoming TODAY  : "+getUpComingEventsOfToday());
        Log.i("SPLIT","Upcoming WEEK  : "+getUpComingEventsOfWeek());
        Log.i("SPLIT","Upcoming MONTH  : "+getUpComingEventsOfMonth());

        Log.i("SPLIT","Top Items MONTH : "+getTopItemsOfMonth());
        Log.i("SPLIT","Top Items YEAR : "+getTopItemsOfYear());
        Log.i("SPLIT","Top Items TOTAL : "+getTopItemsOfTotal());
    }
}
