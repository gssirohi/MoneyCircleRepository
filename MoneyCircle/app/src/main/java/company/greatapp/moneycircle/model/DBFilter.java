package company.greatapp.moneycircle.model;

import android.net.Uri;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import company.greatapp.moneycircle.constants.DB;

/**
 * Created by Gyanendrasingh on 18-07-2015.
 */
public class DBFilter {
    int modelType;
    int periodType;
    int category;
    Period period;
    //String selection = DB.CATEGORY + "=" + category + " AND " + DB.DATE +  " BETWEEN ? AND ?";
    String selection =  DB.DATE_STRING +  " BETWEEN ? AND ?";
   // String selection = null;
    String[] args;


    public DBFilter(int modelType, int periodType, int category){
        this.modelType = modelType;
        this.periodType = periodType;
        this.category = category;
        init();
    }

    public void  init() {
        this.period = new Period(periodType);
        args = new String[]{period.startDate /*+ " 00:00:00"*/, period.endDate /*+ " 23:59:59" */};
    }


    public Uri getDbUri(){
        switch (modelType){
            case Model.MODEL_TYPE_INCOME:
                return DB.INCOME_TABLE_URI;
            case Model.MODEL_TYPE_EXPENSE:
                return DB.EXPENSE_TABLE_URI;
            case Model.MODEL_TYPE_BORROW:
                return DB.BORROW_TABLE_URI;
            case Model.MODEL_TYPE_LENT:
                return DB.LENT_TABLE_URI;
            case Model.MODEL_TYPE_SPLIT:
                return DB.SPLIT_TABLE_URI;
        }
        return null;
    }

    public String[] getProjection(){
        switch (modelType){
            case Model.MODEL_TYPE_INCOME:
                return DB.INCOME_TABLE_PROJECTION;
            case Model.MODEL_TYPE_EXPENSE:
                return DB.EXPENSE_TABLE_PROJECTION;
            case Model.MODEL_TYPE_BORROW:
                return DB.BORROW_TABLE_PROJECTION;
            case Model.MODEL_TYPE_LENT:
                return DB.LENT_TABLE_PROJECTION;
            case Model.MODEL_TYPE_SPLIT:
                return DB.SPLIT_TABLE_PROJECTION;
        }
        return null;
    }

    public void nextPeriod(){
        period.next();
    }

    public void previousPeriod(){
        period.previous();
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public int getPeriodType() {
        return periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
        init();// reset period with new mode and curr date
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String[] getArgs() {
        args = new String[]{period.getStartDate() /*+ " 00:00:00"*/, period.getEndDate() /*+ " 23:59:59" */};
        return args;
        //return null;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void print(){
        Log.d("Split","FILTER: periodType:"+periodType);
        Log.d("Split","FILTER: period start:"+period.getStartDate());
        Log.d("Split","FILTER: period End:"+period.getEndDate());
        Log.d("Split","FILTER: SELECTION:"+getSelection());
        Log.d("Split","FILTER: ARGS"+getArgs()[0]+"  "+getArgs()[1]);
    }
}

