package company.greatapp.moneycircle.db;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import company.greatapp.moneycircle.constants.DB;

public class MoneyCircleProvider extends ContentProvider{
	
	private static final String TAG = "PROVIDER";
	private SQLiteDatabase qpinionDBinstance;
	public static MoneyCircleDBHelper HelperInstance;
	
	@Override
	public boolean onCreate() {
		Log.d("in  cp","Creating DataBase");
		
		//A DBframe will be created with name DB_NAME ="AdminBankDb";
		HelperInstance = new MoneyCircleDBHelper(getContext(), null, null, DB.DB_VERSION);
		//creating database
		qpinionDBinstance = HelperInstance.getWritableDatabase();
		
		return true;
	}

	static UriMatcher uriMatcher;
	static int CONTACT_TABLE_INDEX = 1;

	static {
	    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	    uriMatcher.addURI(DB.DB_AUTHORITY, DB.CONTACT_TABLE_NAME, CONTACT_TABLE_INDEX);

	}
	
	@Override
	public Uri insert(Uri uri, ContentValues newRow) {
		Log.d("in cp","inserting..");
		int table_id =  uriMatcher.match(uri);
		
		Log.e("table_ID-->>",""+table_id);
		
		switch(table_id)//data will be inserted in t1 or t3 of corresponding month
		{
		case 1:
			{
				//this method will accept a row(values) and and insert into the table
				long rowId = qpinionDBinstance.insert(DB.CONTACT_TABLE_NAME, null, newRow);
				//Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
			    if (rowId > 0) {
					Log.d("in cp","inserted in "+DB.CONTACT_TABLE_NAME);
			        Uri objUri = ContentUris.withAppendedId(uri, rowId);
			        getContext().getContentResolver().notifyChange(objUri, null);
			    }
				break;
			}

		default:
			Log.e("in cp","URI NOT MATCHED for Inserting!!!");
		
		}//switch
		return null;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder)
	{		
		Cursor c;
		
		int table_id =  uriMatcher.match(uri);
		
		switch(table_id)
		{
		case 1:
			{
				Log.d("in cp","Quering data from "+DB.CONTACT_TABLE_NAME);
				c =  qpinionDBinstance.query(DB.CONTACT_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
				Log.d("in CP","coursor returned from "+DB.CONTACT_TABLE_NAME);
				c.setNotificationUri(getContext().getContentResolver(), uri);
				return c;
			
			}

		default:
			Log.e("in cp","URI NOT MATCHED for Quering data!!!");
		
		}//switch
		
		return null;
	}
	@Override
	public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) 
	{
		int table_id =  uriMatcher.match(uri);
		Log.d("in CP","updating..");
		
		switch(table_id)
		{
		case 1:
			{
				//this method will accept a row(values) and and insert into the table
				long rowId = qpinionDBinstance.update(DB.CONTACT_TABLE_NAME, values, selection, selectionArgs);
				//Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
				
			    if (rowId > 0) {
			        Uri objUri = ContentUris.withAppendedId(uri, rowId);
			        getContext().getContentResolver().notifyChange(objUri, null);
			        Log.d("in cp","updated : "+DB.CONTACT_TABLE_NAME);
			    }
				break;
			}
		default:
			Log.e("in cp","URI NOT MATCHED for Updating data!!!");
		
		}//switch
		return 0;
	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{int table_id =  uriMatcher.match(uri);
	
	switch(table_id)
	{
	case 1:
		{
			Log.d("in cp","Deleting data from "+DB.CONTACT_TABLE_NAME);
			long rowId = qpinionDBinstance.delete(DB.CONTACT_TABLE_NAME,  selection, selectionArgs);

		    if (rowId > 0) {
		        Uri objUri = ContentUris.withAppendedId(uri, rowId);
		        getContext().getContentResolver().notifyChange(objUri, null);
				Log.d("in CP","item deleted from db");
		    }
			return 0;
		
		}
	default:
		Log.e("in cp","URI NOT MATCHED for Deleting data!!!");
	
	}//switch
	
	return 1;
	}
	@Override
	public String getType(Uri uri) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	

}
