package company.greatapp.moneycircle.tools;

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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
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
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentWeekFirstDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getActualMinimum(Calendar.DAY_OF_WEEK));
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentWeekLastDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getActualMaximum(Calendar.DAY_OF_WEEK));
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentMonthFirstDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        dateString = format.format(c.getTime());
        return dateString;

    }

    public static String getCurrentMonthLastDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentYearFirstDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        dateString = format.format(c.getTime());
        return dateString;
    }

    public static String getCurrentYearLastDate() {
        String dateString = "";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        dateString = format.format(c.getTime());
        return dateString;
    }

}
