package company.greatapp.moneycircle.tools;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gyanendrasingh on 18-07-2015.
 */
public class DateUtils {
    public static String getNextDate(String curDate) {
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            today.setTime(date);
            today.add(Calendar.DAY_OF_YEAR, 1);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getPreviousDate(String curDate) {
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            today.setTime(date);
            today.add(Calendar.DAY_OF_YEAR, -1);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getNextWeekFirstDate(String curDate) {
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            today.setFirstDayOfWeek(Calendar.SUNDAY);
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            today.setTime(date);
            today.set(Calendar.DAY_OF_WEEK, today.getActualMinimum(Calendar.DAY_OF_WEEK));
            today.add(Calendar.DATE, 7);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getNextWeekLastDate(String curDate) {
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            today.setFirstDayOfWeek(Calendar.SUNDAY);
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            today.setTime(date);
            today.set(Calendar.DAY_OF_WEEK, today.getActualMaximum(Calendar.DAY_OF_WEEK));
            today.add(Calendar.DATE, 7);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getPreviousWeekFirstDate(String curDate) {
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            today.setFirstDayOfWeek(Calendar.SUNDAY);
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            today.setTime(date);
            today.set(Calendar.DAY_OF_WEEK, today.getActualMinimum(Calendar.DAY_OF_WEEK));
            today.add(Calendar.DATE, -7);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getPreviousWeekLastDate(String curDate) {
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            today.setFirstDayOfWeek(Calendar.SUNDAY);
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            today.setTime(date);
            today.set(Calendar.DAY_OF_WEEK, today.getActualMaximum(Calendar.DAY_OF_WEEK));
            today.add(Calendar.DATE, -7);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }


    public static String getNextMonthFirstDate(String curDate) {
        String nextDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            nextDate = format.format(calendar.getTime());

        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getNextMonthLastDate(String curDate) {
        String nextDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            nextDate = format.format(calendar.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getPreviousMonthFirstDate(String curDate) {
        String nextDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            nextDate = format.format(calendar.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getPreviousMonthLastDate(String curDate) {
        String nextDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            nextDate = format.format(calendar.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getNextYearFirstDate(String curDate) {
        String nextDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
            nextDate = format.format(calendar.getTime());

        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getNextYearLastDate(String curDate) {
        String nextDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
            nextDate = format.format(calendar.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getPreviousYearFirstDate(String curDate) {
        String nextDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -1);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
            nextDate = format.format(calendar.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getPreviousYearLastDate(String curDate) {
        String nextDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date date = format.parse(curDate);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -1);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
            nextDate = format.format(calendar.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    public static String getDateString(int year, int monthOfYear, int dayOfMonth) {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,monthOfYear);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        DateFormat format = getDateFormat();
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        DateFormat format = getDateFormat();
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentWeekFirstDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getActualMinimum(Calendar.DAY_OF_WEEK));
        DateFormat format = getDateFormat();
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentWeekLastDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getActualMaximum(Calendar.DAY_OF_WEEK));
        DateFormat format = getDateFormat();
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentMonthFirstDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        DateFormat format = getDateFormat();
        dateString = format.format(c.getTime());
        return dateString;

    }

    public static String getCurrentMonthLastDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateFormat format = getDateFormat();
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentYearFirstDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
        DateFormat format = getDateFormat();
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentYearLastDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        DateFormat format = getDateFormat();
        dateString = format.format(c.getTime());
        return dateString;
    }
    public static boolean isCurrDate(String date){
        boolean isIt = false;
        if(date.equals(getCurrentDate()))
            isIt =  true;
        else
            isIt =  false;

        Log.d("SPLIT", "is Current Date [" + date + "] : " + isIt);
        return isIt;

    }

    public static boolean isCurrWeek(String date){
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(date);
            dateToBeChecked.setTime(dateObj);

            Calendar currFirstDate = Calendar.getInstance();
            Calendar currLastDate = Calendar.getInstance();
            String date1 = getCurrentWeekFirstDate();
            String date2 = getCurrentWeekLastDate();

            if(date.equals(date1) || date.equals(date2)) {
                isIt =  true;
            } else {
                currFirstDate.setTime(format.parse(date1));
                currLastDate.setTime(format.parse(date2));
                if(dateToBeChecked.after(currFirstDate)
                        && dateToBeChecked.before(currLastDate)) {
                    isIt =  true;
                } else {
                    isIt =  false;
                }
            }

        } catch (Exception e) {
            isIt =  false;
        }
        Log.d("SPLIT", "is Current Week [" + date + "] : " + isIt);
        return isIt;
    }

    public static boolean isCurrMonth(String date){
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(date);
            dateToBeChecked.setTime(dateObj);

            Calendar currFirstDate = Calendar.getInstance();
            Calendar currLastDate = Calendar.getInstance();
            String date1 = getCurrentMonthFirstDate();
            String date2 = getCurrentMonthLastDate();

            if(date.equals(date1) || date.equals(date2)) {
                isIt =  true;
            } else {
                currFirstDate.setTime(format.parse(date1));
                currLastDate.setTime(format.parse(date2));
                if(dateToBeChecked.after(currFirstDate)
                        && dateToBeChecked.before(currLastDate)) {
                    isIt =  true;
                } else {
                    isIt =  false;
                }
            }

        } catch (Exception e) {
            isIt =  false;
        }
        Log.d("SPLIT", "is Current Month [" + date + "] : " + isIt);
        return isIt;
    }

    public static boolean isCurrYear(String date){
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(date);
            dateToBeChecked.setTime(dateObj);

            Calendar currFirstDate = Calendar.getInstance();
            Calendar currLastDate = Calendar.getInstance();
            String date1 = getCurrentYearFirstDate();
            String date2 = getCurrentYearLastDate();

            if(date.equals(date1) || date.equals(date2)) {
                isIt =  true;
            } else {
                currFirstDate.setTime(format.parse(date1));
                currLastDate.setTime(format.parse(date2));
                if(dateToBeChecked.after(currFirstDate)
                        && dateToBeChecked.before(currLastDate)) {
                    isIt = true;
                } else {
                    isIt =  false;
                }
            }

        } catch (Exception e) {
            isIt =  false;
        }
        Log.d("SPLIT", "is Current Year [" + date + "] : " + isIt);
        return isIt;
    }

    public static boolean isLastDate(String date){
       boolean isIt;
        if(date.equals(getPreviousDate(getCurrentDate())))
            isIt =  true;
        else
            isIt =  false;
        Log.d("SPLIT", "is Last Date [" + date + "] : " + isIt);
        return isIt;
    }

    public static boolean isLastWeek(String date){
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(date);
            dateToBeChecked.setTime(dateObj);

            Calendar currFirstDate = Calendar.getInstance();
            Calendar currLastDate = Calendar.getInstance();
            String currentDate = getCurrentDate();
            String date1 = getPreviousWeekFirstDate(currentDate);
            String date2 = getPreviousWeekLastDate(currentDate);

            if(date.equals(date1) || date.equals(date2)) {
                isIt = true;
            } else {
                currFirstDate.setTime(format.parse(date1));
                currLastDate.setTime(format.parse(date2));
                if(dateToBeChecked.after(currFirstDate)
                        && dateToBeChecked.before(currLastDate)) {
                    isIt = true;
                } else {
                    isIt =  false;
                }
            }

        } catch (Exception e) {
            isIt =  false;
        }
        Log.d("SPLIT", "is Last week [" + date + "] : " + isIt);
        return isIt;
    }

    public static boolean isLastMonth(String date){
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(date);
            dateToBeChecked.setTime(dateObj);

            Calendar currFirstDate = Calendar.getInstance();
            Calendar currLastDate = Calendar.getInstance();
            String currentDate = getCurrentDate();
            String date1 = getPreviousMonthFirstDate(currentDate);
            String date2 = getPreviousMonthLastDate(currentDate);

            if(date.equals(date1) || date.equals(date2)) {
                isIt =  true;
            } else {
                currFirstDate.setTime(format.parse(date1));
                currLastDate.setTime(format.parse(date2));
                if(dateToBeChecked.after(currFirstDate)
                        && dateToBeChecked.before(currLastDate)) {
                    isIt =  true;
                } else {
                    isIt =  false;
                }
            }

        } catch (Exception e) {
            isIt =  false;
        }
        Log.d("SPLIT", "is Last Month [" + date + "] : " + isIt);
        return isIt;
    }
    public static boolean isLastYear(String date){
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(date);
            dateToBeChecked.setTime(dateObj);

            Calendar currFirstDate = Calendar.getInstance();
            Calendar currLastDate = Calendar.getInstance();
            String currentDate = getCurrentDate();
            String date1 = getPreviousYearFirstDate(currentDate);
            String date2 = getPreviousYearLastDate(currentDate);

            if(date.equals(date1) || date.equals(date2)) {
                isIt =  true;
            } else {
                currFirstDate.setTime(format.parse(date1));
                currLastDate.setTime(format.parse(date2));
                if(dateToBeChecked.after(currFirstDate)
                        && dateToBeChecked.before(currLastDate)) {
                    isIt =  true;
                } else {
                    isIt =  false;
                }
            }

        } catch (Exception e) {
            isIt =  false;
        }
        Log.d("SPLIT", "is Last Year [" + date + "] : " + isIt);
        return isIt;
    }
    public static boolean isPassed(String date) {
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(date);
            dateToBeChecked.setTime(dateObj);

            Calendar currDate = Calendar.getInstance();
            if (dateToBeChecked.after(currDate) || dateToBeChecked.equals(currDate)) {
                isIt = false;
            } else {
                isIt = true;
            }
        } catch (Exception e) {
            isIt =  true;//true is a fail case here hence default
        }
        Log.d("SPLIT", "is Passed  [" + date + "] : " + isIt);
        return isIt;
    }

    public static boolean isLiesInNextSevenDays(String dueDate) {
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(dueDate);
            dateToBeChecked.setTime(dateObj);

            Calendar currDate = Calendar.getInstance();
            Calendar currNextDate = Calendar.getInstance();
            currNextDate.add(Calendar.DATE,7);
            if (dateToBeChecked.after(currDate) || dateToBeChecked.equals(currDate)) {
                if(dateToBeChecked.before(currNextDate)) {
                    isIt = true;
                } else {
                    isIt = false;
                }
            } else {
                    isIt = false;
            }
        } catch (Exception e) {
            isIt =  false;//fasle is a fail case here hence default
        }
        Log.d("SPLIT", "is Lies in Next 7  [" + dueDate + "] : " + isIt);
        return isIt;
    }

    public static boolean isLiesInNextFifteenDays(String dueDate) {
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(dueDate);
            dateToBeChecked.setTime(dateObj);

            Calendar currDate = Calendar.getInstance();
            Calendar currNextDate = Calendar.getInstance();
            currNextDate.add(Calendar.DATE,15);
            if (dateToBeChecked.after(currDate) || dateToBeChecked.equals(currDate)) {
                if(dateToBeChecked.before(currNextDate)) {
                    isIt = true;
                } else {
                    isIt = false;
                }
            } else {
                isIt = false;
            }
        } catch (Exception e) {
            isIt =  false;//fasle is a fail case here hence default
        }
        Log.d("SPLIT", "is Lies in Next 15  [" + dueDate + "] : " + isIt);
        return isIt;
    }

    public static boolean isLiesInNextThirtyDays(String dueDate) {
        boolean isIt;
        try {
            Calendar dateToBeChecked = Calendar.getInstance();
            DateFormat format = getDateFormat();
            Date dateObj = format.parse(dueDate);
            dateToBeChecked.setTime(dateObj);

            Calendar currDate = Calendar.getInstance();
            Calendar currNextDate = Calendar.getInstance();
            currNextDate.add(Calendar.DATE,30);
            if (dateToBeChecked.after(currDate) || dateToBeChecked.equals(currDate)) {
                if(dateToBeChecked.before(currNextDate)) {
                    isIt = true;
                } else {
                    isIt = false;
                }
            } else {
                isIt = false;
            }
        } catch (Exception e) {
            isIt =  false;//fasle is a fail case here hence default
        }
        Log.d("SPLIT", "is Lies in Next 30  [" + dueDate + "] : " + isIt);
        return isIt;
    }

    public static DateFormat getDateFormat() {
        //return new SimpleDateFormat("yyyy/MM/dd");
        //return new SimpleDateFormat("yyyy/MM/dd");
        return new SimpleDateFormat("dd MMM yyyy");
    }
}
