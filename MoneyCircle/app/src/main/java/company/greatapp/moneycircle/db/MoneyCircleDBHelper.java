package company.greatapp.moneycircle.db;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.constants.S;


public class MoneyCircleDBHelper extends SQLiteOpenHelper {


    private AtomicInteger openCounter = new AtomicInteger();
    private SQLiteDatabase mDbHelper;

    public MoneyCircleDBHelper(Context context, String name, CursorFactory factory,int version)
		{
			
			super(context, DB.DB_NAME, null, DB.DB_VERSION);
			Log.d("DBhelper","DB constructed!");
			
		}

	@Override
    public synchronized void close() {
Log.d("DB", "closing db.. ");
        if(openCounter.decrementAndGet() == 0) {
            super.close();
            Log.d("DB", "DB CLOSED");
        }
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {

        Log.d("DB", "Opening DB...");
        if(openCounter.incrementAndGet() == 1) {
         mDbHelper =  super.getWritableDatabase();
            Log.d("DB", "DB Opened");
        }
        return mDbHelper;
    }
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("DBhelper", "Creating Tables...");
         //=========================================================================//
			db.execSQL("create table " + DB.CONTACT_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text,"
                            + DB.EMAIL + " text,"
                            + DB.CONTACT_IMAGE_URI + " text,"
                            + DB.REGISTERED + " int,"
                            + DB.SERVER_NAME + " text,"
                            + DB.SERVER_ID + " text,"
                            + DB.STATE + " int, "
                            + DB.GENDER + " int, "
                            + DB.CONTACT_BORROWED_AMOUNT_FROM_THIS + " text,"
                            + DB.CONTACT_LENT_AMOUNT_TO_THIS + " text,"
                            + DB.JSON_STRING + " text);"

            );
			Log.d("DBhelper", "query sent for" + DB.CONTACT_TABLE_NAME);
     //=========================================================================//
            db.execSQL("create table " + DB.CIRCLE_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.CIRCLE_NAME + " text, "
                            + DB.CIRCLE_CONTACTS_JSON + " text, "
                            + DB.JSON_STRING + " text );"
            );
            Log.d("DBhelper","query sent for"+DB.CIRCLE_TABLE_NAME);
    //=========================================================================//
            db.execSQL("create table " + DB.INCOME_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.TITLE + " text,"
                            + DB.CATEGORY + " text,"
                            + DB.DESCRIPTION + " text,"
                            + DB.AMOUNT + " text, "
                            + DB.DATE_STRING + " text, "
                            + DB.DATE + " int,"
                            + DB.DAY_OF_MONTH + " int,"
                            + DB.WEEK_OF_MONTH + " int,"
                            + DB.MONTH + " int,"
                            + DB.YEAR + " int,"
                            + DB.JSON_STRING + " text );"

            );

            Log.d("DBhelper", "query sent for" + DB.INCOME_TABLE_NAME);
    //=========================================================================//
            db.execSQL("create table " + DB.EXPENSE_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.TITLE + " text,"
                            + DB.CATEGORY + " text,"
                            + DB.DESCRIPTION + " text,"
                            + DB.AMOUNT + " text, "
                            + DB.IS_LINKED_WITH_SPLIT + " int,"
                            + DB.LINKED_SPLIT_JSON + " text, "
                            + DB.DATE_STRING + " text, "
                            + DB.DATE + " int,"
                            + DB.DAY_OF_MONTH + " int,"
                            + DB.WEEK_OF_MONTH + " int,"
                            + DB.MONTH + " int,"
                            + DB.YEAR + " int,"
                            + DB.JSON_STRING + " text );"

            );
            Log.d("DBhelper", "query sent for" + DB.EXPENSE_TABLE_NAME);
     //=========================================================================//
            db.execSQL("create table " + DB.BORROW_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.TITLE + " text,"
                            + DB.CATEGORY + " text,"
                            + DB.DESCRIPTION + " text,"
                            + DB.AMOUNT + " text, "
                            + DB.STATE + " int, "
                            + DB.LINKED_CONTACT_JSON + " text, "
                            + DB.DUE_DATE_STRING + " text, "
                            + DB.DATE_STRING + " text, "
                            + DB.ITEM_OWNER_PHONE + " text, "
                            + DB.BORROW_LENT_TYPE + " int,"
                            + DB.DATE + " int,"
                            + DB.DAY_OF_MONTH + " int,"
                            + DB.WEEK_OF_MONTH + " int,"
                            + DB.MONTH + " int,"
                            + DB.YEAR + " int,"
                            + DB.JSON_STRING + " text );"

            );
            Log.d("DBhelper", "query sent for" + DB.BORROW_TABLE_NAME);
    //=========================================================================//
            db.execSQL("create table " + DB.LENT_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.TITLE + " text,"
                            + DB.CATEGORY + " text,"
                            + DB.DESCRIPTION + " text,"
                            + DB.AMOUNT + " text, "
                            + DB.STATE + " int, "
                            + DB.DUE_DATE_STRING + " text, "
                            + DB.LINKED_CONTACT_JSON + " text, "
                            + DB.IS_LINKED_WITH_SPLIT + " int,"
                            + DB.LINKED_SPLIT_JSON + " text, "
                            + DB.DATE_STRING + " text, "
                            + DB.ITEM_OWNER_PHONE + " text, "
                            + DB.BORROW_LENT_TYPE + " int,"
                            + DB.DATE + " int,"
                            + DB.DAY_OF_MONTH + " int,"
                            + DB.WEEK_OF_MONTH + " int,"
                            + DB.MONTH + " int,"
                            + DB.YEAR + " int,"
                            + DB.JSON_STRING + " text );"
            );
            Log.d("DBhelper", "query sent for" + DB.LENT_TABLE_NAME);
    //=========================================================================//
            db.execSQL("create table " + DB.SPLIT_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.TITLE + " text,"
                            + DB.CATEGORY + " text,"
                            + DB.DESCRIPTION + " text,"
                            + DB.AMOUNT + " text, "
                            + DB.DUE_DATE_STRING + " text, "
                            + DB.SPLIT_TOTAL_PARTICIPANTS + " int,"
                            + DB.SPLIT_LINKED_CONTACTS_JSON + " text, "
                            + DB.SPLIT_LINKED_CIRCLE_JSON + " text, "
                            + DB.SPLIT_LINKED_PARTICIPANTS_JSON + " text, "
                            + DB.SPLIT_LINKED_EXPENSE_JSON + " text, "
                            + DB.SPLIT_LINKED_LENTS_JSON + " text,"
                            + DB.DATE_STRING + " text, "
                            + DB.DATE + " int,"
                            + DB.DAY_OF_MONTH + " int,"
                            + DB.WEEK_OF_MONTH + " int,"
                            + DB.MONTH + " int,"
                            + DB.YEAR + " int,"
                            + DB.JSON_STRING + " text );"
            );
            Log.d("DBhelper", "query sent for" + DB.SPLIT_TABLE_NAME);

    //=========================================================================//
            db.execSQL("create table " + DB.CATEGORY_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.CATEGORY_NAME + " text, "
                            + DB.CATEGORY_FOR_INCOME + " int, "
                            + DB.CATEGORY_FOR_EXPENSE + " int, "
                            + DB.CATEGORY_FOR_BORROW + " int, "
                            + DB.CATEGORY_FOR_LENT + " int, "
                            + DB.CATEGORY_FOR_SPLIT + " int, "
                            + DB.CATEGORY_SPENT_AMOUNT_ON_THIS + " text, "

                            + DB.CATEGORY_TYPE + " int  );"
            );
            Log.d("DBhelper", "query sent for" + DB.CATEGORY_TABLE_NAME);

    //=========================================================================//
            db.execSQL(     "create table "     + DB.PACKAGE_FORSERVER_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + S.TRANSPORT_ITEM_OWNER_PHONE + " text,"
                            + S.TRANSPORT_ITEM_ASSOCIATE_PHONE + " text,"
                            + DB.MAX_RETRY_ATTEMPT + " int,"
                            + DB.ATTEMPT_COUNTER + " int,"
                            + DB.URL + " text,"
                            + DB.REQ_RESPONSE_TYPE + " int,"
                            + DB.REQ_TYPE + " int,"
                            + S.TRANSPORT_REQ_CODE + " int,"
                            + S.TRANSPORT_REQ_SENDER_PHONE + " text,"
                            + S.TRANSPORT_REQ_RECEIVER_PHONE + " text,"
                            + S.TRANSPORT_MONEY_RECEIVER_PHONE + " text,"
                            + S.TRANSPORT_MONEY_PAYER_PHONE + " text,"
                            + S.TRANSPORT_OWNER_ITEM_TYPE + " int,"
                            + S.TRANSPORT_ASSOCIATE_ITEM_TYPE + " int,"
                            + S.TRANSPORT_OWNER_ITEM_ID + " text,"
                            + S.TRANSPORT_ASSOCIATE_ITEM_ID + " text,"
                            + S.TRANSPORT_ITEM_BODY_JSON_TYPE + " int,"
                            + S.TRANSPORT_ITEM_BODY_JSON_STRING + " text,"
                            + S.TRANSPORT_MESSAGE + " text);"
            );
            Log.d("DBhelper", "query sent for" + DB.PACKAGE_FORSERVER_TABLE_NAME);
    //=========================================================================//

            db.execSQL(     "create table "     + DB.COMMON_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text );"
            );
            Log.d("DBhelper","query sent for"+DB.COMMON_TABLE_NAME);
    //=========================================================================//

     //=========================================================================//

            db.execSQL(     "create table "     + DB.ACCOUNT_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"

                            + DB.ACCOUNT_REGISTER_TYPE + " int,"

                            + DB.ACCOUNT_TOTAL_CURRENT_DAY + " text,"
                            + DB.ACCOUNT_TOTAL_CURRENT_WEEK + " text,"
                            + DB.ACCOUNT_TOTAL_CURRENT_MONTH + " text,"
                            + DB.ACCOUNT_TOTAL_CURRENT_YEAR + " text,"

                            + DB.ACCOUNT_TOTAL_LAST_DAY + " text,"
                            + DB.ACCOUNT_TOTAL_LAST_WEEK + " text,"
                            + DB.ACCOUNT_TOTAL_LAST_MONTH + " text,"
                            + DB.ACCOUNT_TOTAL_LAST_YEAR + " text,"

                            + DB.ACCOUNT_TOTAL + " text,"

                            + DB.ACCOUNT_UPCOMINGS_DAY + " text,"
                            + DB.ACCOUNT_UPCOMINGS_WEEK + " text,"
                            + DB.ACCOUNT_UPCOMINGS_MONTH + " text,"

                            + DB.ACCOUNT_TOPITEMS + " text,"
                            + DB.ACCOUNT_LAST_TRANSACTION + " text );"
            );
            Log.d("DBhelper","query sent for"+DB.ACCOUNT_TABLE_NAME);
            //=========================================================================//

            //=========================================================================//

            db.execSQL(     "create table "     + DB.PACKAGE_FROM_SERVER_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.REQUEST_CODE + " int,"
                            + DB.REQUEST_SENDER_PHONE + " text,"
                            + DB.REQUEST_SENDER_NAME + " text,"
                            + DB.REQUEST_RECIEVER_PHONE + " text,"
                            + DB.ITEM_OWNER_PHONE       + " text,"
                            + DB.ITEM_ASSOCIATE_PHONE   + " text,"
                            + DB.MONEY_RECIEVER_PHONE   + " text,"
                            + DB.MONEY_PAYER_PHONE      + " text,"
                            + DB.OWNER_ITEM_TYPE        +  " text,"
                            + DB.ASSOCIATE_ITEM_TYPE    + " text,"
                            + DB.OWNER_ITEM_ID         + " text,"
                            + DB.ASSOCIATE_ITEM_ID     + " text,"
                            + DB.ITEM_BODY_JSON_TYPE    + " text,"
                            + DB.ITEM_TITLE             + " text,"
                            + DB.AMOUNT                 + " text,"
                            + DB.DATE_STRING            + " text,"
                            + DB.ITEM_DATE_STRING       + " text,"
                            + DB.MESSAGE                + " text,"
                            + DB.ITEM_DUE_DATE_STRING   + " text,"
                            + DB.IS_RESPONDABLE         + " int,"
                            + DB.RESPONSE_STATE         + " int,"
                            + DB.ITEM_STATE             + " int,"
                            + DB.ITEM_DESCRIPTION       + " text,"
                            + DB.ITEM_BODY_JSON_STRING + " text );"
            );
            Log.d("DBhelper","query sent for"+DB.PACKAGE_FROM_SERVER_TABLE_NAME);
            //=========================================================================//
            db.execSQL("create table " + DB.FREQUENT_ITEM_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.TITLE + " text,"
                            + DB.CATEGORY + " text,"
                            + DB.DESCRIPTION + " text,"
                            + DB.AMOUNT + " text, "
                            + DB.DATE_STRING + " text, "
                            + DB.JSON_STRING + " text );"

            );
            Log.d("DBhelper", "query sent for" + DB.FREQUENT_ITEM_TABLE_NAME);

        }


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("DBhelper", "upgrading dadabase.... ");

		}
	}

