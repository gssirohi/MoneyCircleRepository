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
			Log.d("DBhelper","Creating Tables...");

			db.execSQL(      "create table "    +DB.CONTACT_TABLE_NAME+"("

												+DB.DB_ID	     	+" Integer primary key ,"
												+DB.UID      	    +" text,"
							                    +DB.NAME      	    +" text,"
                                                +DB.SERVER_ID  	    +" text,"
                                                +DB.SERVER_NAME	    +" text,"
												+DB.PHONE_NUMBER  	+" text,"
							                    +DB.EMAIL  	        +" text,"
												+DB.CIRCLE_COUNT 	+" int,"
												+DB.IMAGE_URI 	    +" text,"
												+DB.REGISTERED 	    +" int,"
												+DB.CIRCLE_JSON_STRING  	+" text);"
												  
						);
			Log.d("DBhelper","query sent for"+DB.CONTACT_TABLE_NAME);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("DBhelper", "upgrading dadabase.... ");

		}
	}

