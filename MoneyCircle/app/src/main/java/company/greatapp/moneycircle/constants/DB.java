package company.greatapp.moneycircle.constants;

import android.net.Uri;

/**
 * Created by gyanendra.sirohi on 7/2/2015.
 */
public class DB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "MoneyCircleDataBase";
    public static final String DB_AUTHORITY = "company.greatapp.moneycircle.db";


//-----------------COMMON-------------------------------------------//
    public static final String DB_ID = "dbId";
    public static final String UID = "uid";
//-----------------------------------------------------------------//


//--------------CONTACT TABLE--------------------------------------//


    public static final String CONTACT_TABLE_NAME = "contactTable";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phone";
    public static final String IMAGE_URI = "imageuri";
    public static final String EMAIL = "email";
    public static final String REGISTERED = "registered";
    public static final String CIRCLE_COUNT = "circleCount";
    public static final String CIRCLE_JSON_STRING = "circleJson";
    public static final String SERVER_NAME = "serverName";
    public static final String SERVER_ID = "serverId";

    public static final Uri CONTACT_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + CONTACT_TABLE_NAME);
    public static final String[] CONTACT_TABLE_PROJECTION = new String[]{
            DB_ID,UID,NAME,PHONE_NUMBER,IMAGE_URI,EMAIL,REGISTERED,
            CIRCLE_COUNT,CIRCLE_JSON_STRING,SERVER_NAME,SERVER_ID};
//----------------------------------------------------------------//

//--------------INCOME TABLE--------------------------------------//


    public static final String INCOME_TABLE_NAME = "incomeTable";
    public static final String INCOME_TYPE ="incomeType";
    public static final String INCOME_CATEGORY ="incomeCategory";
    public static final String INCOME_JSON_STRING ="incomeJson";
    public static final String INCOME_AMOUNT ="incomeAmount";
    public static final String INCOME_DATE = "incomeDate";
    public static final String INCOME_DESCRIPTION = "incomeDescription";



    public static final Uri INCOME_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + INCOME_TABLE_NAME);
    public static final String[] INCOME_TABLE_PROJECTION = new String[]{
            DB_ID,UID,NAME,PHONE_NUMBER,INCOME_TYPE,INCOME_CATEGORY,
            INCOME_JSON_STRING,INCOME_AMOUNT,INCOME_DATE,INCOME_DESCRIPTION};
//----------------------------------------------------------------//

    //--------------EXPENSE TABLE--------------------------------------//


    public static final String EXPENSE_TABLE_NAME = "expenseTable";
    public static final String EXPENSE_DATE       ="expenseDate";
    public static final String  EXPENSE_AMOUNT    ="expenseAmount";
    public static final String EXPENSE_CATEGORY   ="expenseCategory";
    public static final String EXPENSE_DESCRIPTION ="expenseDescription";



    public static final Uri EXPENSE_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + EXPENSE_TABLE_NAME);
    public static final String[] EXPENSE_TABLE_PROJECTION = new String[]{
            DB_ID,UID,NAME,PHONE_NUMBER,EXPENSE_DATE,EXPENSE_AMOUNT,
            EXPENSE_CATEGORY,EXPENSE_DESCRIPTION};
//----------------------------------------------------------------//
    //--------------BORROW TABLE--------------------------------------//


    public static final String BORROW_TABLE_NAME = "borrowTable";
    public static final String BORROW_TYPE ="borrowType";
    public static final String BORROW_CATEGORY ="borrowCategory";
    public static final String BORROW_AMOUNT ="borrowAmount";
    public static final String BORROW_DATE ="borrowDate";
    public static final String BORROW_DUE_DATE ="borrowDueDate";
    public static final String BORROW_DESCRIPTION ="borrowDescription";




    public static final Uri BORROW_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + BORROW_TABLE_NAME);
    public static final String[] BORROW_TABLE_PROJECTION = new String[]{
            DB_ID,UID,NAME,PHONE_NUMBER,BORROW_TYPE,BORROW_CATEGORY,
            BORROW_AMOUNT,BORROW_DATE,BORROW_DUE_DATE,BORROW_DESCRIPTION};
//----------------------------------------------------------------//
//--------------LENDED TABLE--------------------------------------//


    public static final String LENDED_TABLE_NAME = "lendedTable";
    public static final String LENDED_TYPE = "lendedType";
    public static final String LENDED_CATEGORY ="lendedCategoty";
    public static final String LENDED_AMOUNT ="lendedAmount";
    public static final String LENDED_DATE ="lendedDate";
    public static final String LENDED_DESCRIPTION = "lendedDescription";


    public static final Uri LENDED_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + LENDED_TABLE_NAME);
    public static final String[] LENDED_TABLE_PROJECTION = new String[]{
            DB_ID,UID,NAME,PHONE_NUMBER,LENDED_TYPE,LENDED_CATEGORY,
            LENDED_AMOUNT,LENDED_DATE,LENDED_DESCRIPTION};
//----------------------------------------------------------------//
//--------------CATEGORY TABLE--------------------------------------//


    public static final String CATEGORY_TABLE_NAME = "categoryTable";
    public static final String CATEGORY_TYPE = "categoryType";
    public static final String CATEGORY_DATE = "categoryDate";
    public static final String CATEGORY_DESCRIPTION = "categoryDescription";


    public static final Uri CATEGORY_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + CATEGORY_TABLE_NAME);
    public static final String[] CATEGORY_TABLE_PROJECTION = new String[]{
            DB_ID,UID,CATEGORY_TYPE,
            CATEGORY_DATE,CATEGORY_DESCRIPTION};
//----------------------------------------------------------------//
    //--------------CIRCLE TABLE--------------------------------------//


    public static final String CIRCLE_TABLE_NAME = "circleTable";
    public static final String CIRCLE_NAME = "circleName";



    public static final Uri CIRCLE_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + CIRCLE_TABLE_NAME);
    public static final String[] CIRCLE_TABLE_PROJECTION = new String[]{
            DB_ID,UID,CIRCLE_NAME,CIRCLE_JSON_STRING};
//----------------------------------------------------------------//
    //--------------NOTIFICATION TABLE--------------------------------------//


    public static final String NOTIFICATION_TABLE_NAME = "notificationTable";
    public static final String NOTIFICATION_TYPE = "notificationType";
    public static final String NOTIFICATION_DESCRIPTION = "notificationDescription";
    public static final String NOTIFICATION_JSON_STRING = "notificationJsonString";




    public static final Uri NOTIFICATION_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + NOTIFICATION_TABLE_NAME);
    public static final String[] NOTIFICATION_TABLE_PROJECTION = new String[]{
            DB_ID,UID,NAME,PHONE_NUMBER,NOTIFICATION_TYPE,NOTIFICATION_DESCRIPTION,
                                    NOTIFICATION_JSON_STRING};
//----------------------------------------------------------------//
    //--------------COMMON TABLE--------------------------------------//


    public static final String COMMON_TABLE_NAME = "commonTable";




    public static final Uri COMMON_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + COMMON_TABLE_NAME);
    public static final String[] COMMON_TABLE_PROJECTION = new String[]{
            DB_ID,UID,NAME,PHONE_NUMBER,};
//----------------------------------------------------------------//
}
