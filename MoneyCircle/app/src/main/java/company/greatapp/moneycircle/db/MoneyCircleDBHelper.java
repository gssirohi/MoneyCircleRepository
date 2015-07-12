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

			db.execSQL("create table " + DB.CONTACT_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.SERVER_ID + " text,"
                            + DB.SERVER_NAME + " text,"
                            + DB.PHONE_NUMBER + " text,"
                            + DB.EMAIL + " text,"
                            + DB.CIRCLE_COUNT + " int,"
                            + DB.IMAGE_URI + " text,"
                            + DB.REGISTERED + " int,"
                            + DB.CIRCLE_JSON_STRING + " text);"

            );
			Log.d("DBhelper", "query sent for" + DB.CONTACT_TABLE_NAME);

			db.execSQL("create table " + DB.INCOME_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text, "
                            + DB.INCOME_JSON_STRING + " text, "
                            + DB.INCOME_TYPE + " text, "
                            + DB.INCOME_CATEGORY + " text, "
                            + DB.INCOME_AMOUNT + " int, "
                            + DB.INCOME_DATE + " text, "
                            + DB.INCOME_DESCRIPTION + " text );"


            );

            Log.d("DBhelper", "query sent for" + DB.INCOME_TABLE_NAME);

            db.execSQL("create table " + DB.EXPENSE_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text, "
                            +DB.EXPENSE_DATE + " text, "
                            +DB.EXPENSE_AMOUNT + " int,"
                            +DB.EXPENSE_CATEGORY + " text, "
                            + DB.EXPENSE_DESCRIPTION + " text );"


            );
            Log.d("DBhelper", "query sent for" + DB.EXPENSE_TABLE_NAME);

            db.execSQL("create table " + DB.BORROW_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text, "
                            +DB.BORROW_TYPE + " text, "
                            +DB.BORROW_CATEGORY + " text,"
                            +DB.BORROW_AMOUNT + " int, "
                            +DB.BORROW_DATE + " text, "
                            +DB.BORROW_DUE_DATE + " text, "
                            + DB.BORROW_DESCRIPTION + " text );"


            );
            Log.d("DBhelper", "query sent for" + DB.BORROW_TABLE_NAME);



            db.execSQL("create table " + DB.LENDED_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text, "
                            + DB.LENDED_TYPE + " text, "
                            +DB.LENDED_CATEGORY + " text, "
                            + DB.LENDED_AMOUNT + " int, "
                            + DB.LENDED_DATE + " text, "
                            + DB.LENDED_DESCRIPTION + " text );"


            );
            Log.d("DBhelper", "query sent for" + DB.LENDED_TABLE_NAME);



            db.execSQL("create table " + DB.CATEGORY_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.CATEGORY_TYPE + " text, "
                             +DB.CATEGORY_DESCRIPTION + " text  );"


            );
            Log.d("DBhelper", "query sent for" + DB.CATEGORY_TABLE_NAME);



            db.execSQL("create table " + DB.CIRCLE_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                             +DB.CIRCLE_NAME + "text, "
                            +DB.CIRCLE_JSON_STRING + "text );"


            );
            Log.d("DBhelper","query sent for"+DB.CIRCLE_TABLE_NAME);



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

            db.execSQL(     "create table "     + DB.COMMON_TABLE_NAME + "("

                            + DB.DB_ID + " Integer primary key ,"
                            + DB.UID + " text,"
                            + DB.NAME + " text,"
                            + DB.PHONE_NUMBER + " text );"


            );
            Log.d("DBhelper","query sent for"+DB.COMMON_TABLE_NAME);





		}


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("DBhelper", "upgrading dadabase.... ");

		}
	}

