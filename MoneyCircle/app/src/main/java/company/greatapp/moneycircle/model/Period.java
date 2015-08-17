package company.greatapp.moneycircle.model;

import company.greatapp.moneycircle.tools.DateUtils;

/**
 * Created by Gyanendrasingh on 18-07-2015.
 */
public class Period{
    public static final int PERIOD_DATE = 1;
    public static final int PERIOD_WEEK = 2;
    public static final int PERIOD_MONTH = 3;
    public static final int PERIOD_YEAR = 4;
    public static final int PERIOD_ALL = 5;
    int periodType;
    String startDate;
    String endDate;

    public int getPeriodType() {
        return periodType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Period(int periodType,String startDate, String endDate) {
        this.periodType = periodType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Period(int periodType) {
        this.periodType = periodType;
        current();
    }
    public void next(){
        String date = startDate;
        switch(periodType){
            case PERIOD_DATE:
                startDate = DateUtils.getNextDate(date);
                endDate = startDate;
                break;
            case PERIOD_WEEK:
                startDate = DateUtils.getNextWeekFirstDate(date);
                endDate = DateUtils.getNextWeekLastDate(date);
                break;
            case PERIOD_MONTH:
                startDate = DateUtils.getNextMonthFirstDate(date);
                endDate = DateUtils.getNextMonthLastDate(date);
                break;
            case PERIOD_YEAR:
                startDate = DateUtils.getNextYearFirstDate(date);
                endDate = DateUtils.getNextYearLastDate(date);
                break;

        }
        //return new Period(periodType,newStartDate,newEndDate);
    }
    public void previous(){
        String date = startDate;
        switch(periodType){
            case PERIOD_DATE:
                startDate = DateUtils.getPreviousDate(date);
                endDate = startDate;
                break;
            case PERIOD_WEEK:
                startDate = DateUtils.getPreviousWeekFirstDate(date);
                endDate = DateUtils.getPreviousWeekLastDate(date);
                break;
            case PERIOD_MONTH:
                startDate = DateUtils.getPreviousMonthFirstDate(date);
                endDate = DateUtils.getPreviousMonthLastDate(date);
                break;
            case PERIOD_YEAR:
                startDate = DateUtils.getPreviousYearFirstDate(date);
                endDate = DateUtils.getPreviousYearLastDate(date);
                break;

        }
       // return new Period(periodType,newStartDate,newEndDate);
    }

    public void current(){
        switch(periodType){
            case PERIOD_DATE:
                startDate = DateUtils.getCurrentDate();
                endDate = startDate;
                break;
            case PERIOD_WEEK:
                startDate = DateUtils.getCurrentWeekFirstDate();
                endDate = DateUtils.getCurrentWeekLastDate();
                break;
            case PERIOD_MONTH:
                startDate = DateUtils.getCurrentMonthFirstDate();
                endDate = DateUtils.getCurrentMonthLastDate();
                break;
            case PERIOD_YEAR:
                startDate = DateUtils.getCurrentYearFirstDate();
                endDate = DateUtils.getCurrentYearLastDate();
                break;

        }
        // return new Period(periodType,newStartDate,newEndDate);
    }
}
