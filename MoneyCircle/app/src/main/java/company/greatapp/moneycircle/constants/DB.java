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

    public static final String DATE_STRING = "dateString";
    public static final String DATE = "date";
    public static final String DAY = "day";
    public static final String WEEK = "week";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    public static final String TITLE = "title";
    public static final String AMOUNT = "amount";

    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String JSON_STRING = "jsonString";

    public static final String LINKED_SPLIT_UID = "linkedSplitUID";
    public static final String LINKED_SPLIT_DB_ID = "linkedSplitDB_ID";
    public static final String IS_LINKED_WITH_SPLIT = "isSplitLinked";

    public static final String LINKED_CONTACT_UID = "linkedContactUid";
    public static final String LINKED_CONTACT_DB_ID = "linkedCOntactDbID";

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

    public static final Uri INCOME_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + INCOME_TABLE_NAME);

    public static final String[] INCOME_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            DATE_STRING,DATE,DAY,WEEK,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//

    //--------------EXPENSE TABLE--------------------------------------//


    public static final String EXPENSE_TABLE_NAME = "expenseTable";

    public static final Uri EXPENSE_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + EXPENSE_TABLE_NAME);

    public static final String[] EXPENSE_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            DATE_STRING,DATE,DAY,WEEK,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//
    //--------------BORROW TABLE--------------------------------------//


    public static final String BORROW_TABLE_NAME = "borrowTable";
    public static final String BORROW_DUE_DATE ="borrowDueDate";

    public static final Uri BORROW_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + BORROW_TABLE_NAME);

    public static final String[] BORROW_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            LINKED_CONTACT_UID,LINKED_CONTACT_DB_ID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            DATE_STRING,DATE,DAY,WEEK,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//
//--------------LENDED TABLE--------------------------------------//


    public static final String LENT_TABLE_NAME = "lendedTable";

    public static final Uri LENT_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + LENT_TABLE_NAME);

    public static final String[] LENT_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            LINKED_CONTACT_UID,LINKED_CONTACT_DB_ID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            DATE_STRING,DATE,DAY,WEEK,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//
//--------------CATEGORY TABLE--------------------------------------//


    public static final String CATEGORY_TABLE_NAME = "categoryTable";
    public static final String CATEGORY_TYPE = "categoryType";
    public static final String CATEGORY_NAME = "categoryName";


    public static final Uri CATEGORY_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + CATEGORY_TABLE_NAME);
    public static final String[] CATEGORY_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            CATEGORY_NAME,
            CATEGORY_TYPE};
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
