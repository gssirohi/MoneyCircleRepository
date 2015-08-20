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
    public static final String DB_ID = "_id";
    public static final String UID = "uid";

    public static final String DUE_DATE_STRING ="dueDate";

    public static final String DATE_STRING = "dateString";
    public static final String DATE = "date";
    public static final String DAY_OF_MONTH = "day";
    public static final String WEEK_OF_MONTH = "week";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    public static final String TITLE = "title";
    public static final String AMOUNT = "amount";

    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String JSON_STRING = "jsonString";

    public static final String LINKED_SPLIT_JSON = "linkedSplitjson";
    public static final String IS_LINKED_WITH_SPLIT = "isSplitLinked";

    public static final String LINKED_CONTACT_JSON = "linkedContactJson";


//-----------------------------------------------------------------//


//--------------CONTACT TABLE--------------------------------------//


    public static final String CONTACT_TABLE_NAME = "contactTable";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phone";
    public static final String CONTACT_IMAGE_URI = "imageuri";
    public static final String EMAIL = "email";
    public static final String REGISTERED = "registered";
    public static final String SERVER_NAME = "serverName";
    public static final String SERVER_ID = "serverId";

    public static final Uri CONTACT_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + CONTACT_TABLE_NAME);
    public static final String[] CONTACT_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            NAME,PHONE_NUMBER,CONTACT_IMAGE_URI,EMAIL,
            REGISTERED,SERVER_NAME,SERVER_ID,
            JSON_STRING};
//----------------------------------------------------------------//
    //--------------CIRCLE TABLE--------------------------------------//


    public static final String CIRCLE_TABLE_NAME = "circleTable";
    public static final String CIRCLE_NAME = "circleName";
    public static final String CIRCLE_CONTACTS_JSON = "circleContactJson";

    public static final Uri CIRCLE_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + CIRCLE_TABLE_NAME);
    public static final String[] CIRCLE_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            CIRCLE_NAME,
            CIRCLE_CONTACTS_JSON,
            JSON_STRING};
//----------------------------------------------------------------//

//--------------INCOME TABLE--------------------------------------//


    public static final String INCOME_TABLE_NAME = "incomeTable";

    public static final Uri INCOME_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + INCOME_TABLE_NAME);

    public static final String[] INCOME_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            DATE_STRING,DATE, DAY_OF_MONTH, WEEK_OF_MONTH,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//

    //--------------EXPENSE TABLE--------------------------------------//


    public static final String EXPENSE_TABLE_NAME = "expenseTable";

    public static final Uri EXPENSE_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + EXPENSE_TABLE_NAME);

    public static final String[] EXPENSE_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            IS_LINKED_WITH_SPLIT,LINKED_SPLIT_JSON,
            DATE_STRING,DATE, DAY_OF_MONTH, WEEK_OF_MONTH,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//
    //--------------BORROW TABLE--------------------------------------//


    public static final String BORROW_TABLE_NAME = "borrowTable";

    public static final Uri BORROW_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + BORROW_TABLE_NAME);

    public static final String[] BORROW_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            LINKED_CONTACT_JSON,
            DUE_DATE_STRING,
            DATE_STRING,DATE, DAY_OF_MONTH, WEEK_OF_MONTH,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//
//--------------LENDED TABLE--------------------------------------//


    public static final String LENT_TABLE_NAME = "lendedTable";

    public static final Uri LENT_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + LENT_TABLE_NAME);

    public static final String[] LENT_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            LINKED_CONTACT_JSON,
            DUE_DATE_STRING,
            IS_LINKED_WITH_SPLIT,LINKED_SPLIT_JSON,
            DATE_STRING,DATE, DAY_OF_MONTH, WEEK_OF_MONTH,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//


    public static final String SPLIT_TABLE_NAME = "splitTable";

    public static final String SPLIT_LINKED_CONTACTS_JSON = "splitConts";
    public static final String SPLIT_LINKED_CIRCLE_JSON = "splitCircle";
    public static final String SPLIT_LINKED_PARTICIPANTS_JSON = "splitPartic";
    public static final String SPLIT_LINKED_EXPENSE_JSON = "splitExp";
    public static final String SPLIT_LINKED_LENTS_JSON = "splitLents";
    public static final String SPLIT_TOTAL_PARTICIPANTS = "splitTotalPart";

    public static final Uri SPLIT_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + SPLIT_TABLE_NAME);

    public static final String[] SPLIT_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            TITLE,CATEGORY,DESCRIPTION,AMOUNT,
            DUE_DATE_STRING,
            SPLIT_TOTAL_PARTICIPANTS,
            SPLIT_LINKED_CONTACTS_JSON,SPLIT_LINKED_CIRCLE_JSON,SPLIT_LINKED_PARTICIPANTS_JSON,
            SPLIT_LINKED_EXPENSE_JSON,
            SPLIT_LINKED_LENTS_JSON,
            DATE_STRING,DATE, DAY_OF_MONTH, WEEK_OF_MONTH,MONTH,YEAR,
            JSON_STRING};
//----------------------------------------------------------------//
//--------------CATEGORY TABLE--------------------------------------//


    public static final String CATEGORY_TABLE_NAME = "categoryTable";
    public static final String CATEGORY_TYPE = "categoryType";
    public static final String CATEGORY_NAME = "categoryName";

    public static final String CATEGORY_FOR_INCOME = "catIncome";
    public static final String CATEGORY_FOR_EXPENSE = "catExpemse";
    public static final String CATEGORY_FOR_BORROW = "catBorrow";
    public static final String CATEGORY_FOR_LENT = "catLent";
    public static final String CATEGORY_FOR_SPLIT = "catSplit";


    public static final Uri CATEGORY_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + CATEGORY_TABLE_NAME);
    public static final String[] CATEGORY_TABLE_PROJECTION = new String[]{
            DB_ID,UID,
            CATEGORY_NAME,
            CATEGORY_FOR_INCOME,CATEGORY_FOR_EXPENSE,CATEGORY_FOR_BORROW,CATEGORY_FOR_LENT,CATEGORY_FOR_SPLIT,
            CATEGORY_TYPE};
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
