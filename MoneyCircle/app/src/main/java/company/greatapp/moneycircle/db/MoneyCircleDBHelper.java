package company.greatapp.moneycircle.db;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import company.greatapp.moneycircle.constants.DB;


public class MoneyCircleDBHelper extends SQLiteOpenHelper {

	public MoneyCircleDBHelper(Context context, String name, CursorFactory factory,int version)
		{
			
			super(context, DB.DB_NAME, null, DB.DB_VERSION);
			Log.d("DBhelper","DB constructed!");
			
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
                            + DB.LINKED_CONTACT_JSON + " text, "
                            + DB.DUE_DATE_STRING + " text, "
                            + DB.DATE_STRING + " text, "
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
                            + DB.DUE_DATE_STRING + " text, "
                            + DB.LINKED_CONTACT_JSON + " text, "
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
                            + DB.CATEGORY_TYPE + " int  );"
            );
            Log.d("DBhelper", "query sent for" + DB.CATEGORY_TABLE_NAME);
    //=========================================================================//
            db.execSQL(     "create table "     + DB.NOTIFICATION_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text, "
                            + DB.NOTIFICATION_JSON_STRING + " text, "
                            + DB.NOTIFICATION_TYPE + " text, "
                            + DB.NOTIFICATION_DESCRIPTION + " text );"
            );
            Log.d("DBhelper","query sent for"+DB.NOTIFICATION_TABLE_NAME);
    //=========================================================================//

            db.execSQL(     "create table "     + DB.COMMON_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text );"
            );
            Log.d("DBhelper","query sent for"+DB.COMMON_TABLE_NAME);
    //=========================================================================//
		}


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("DBhelper", "upgrading dadabase.... ");

		}
	}

