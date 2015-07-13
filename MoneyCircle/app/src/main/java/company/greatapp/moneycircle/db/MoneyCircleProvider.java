package company.greatapp.moneycircle.db;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

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
	static int INCOME_TABLE_INDEX = 2;
    static int EXPENSE_TABLE_INDEX = 3;
    static int BORROW_TABLE_INDEX = 4;
    static int LENDED_TABLE_INDEX = 5;
    static int CATEGORY_TABLE_INDEX = 6;
    static int CIRCLE_TABLE_INDEX = 7;
    static int NOTIFICATION_TABLE_INDEX = 8;
    static int COMMON_TABLE_INDEX = 9;

	static {
	    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	    uriMatcher.addURI(DB.DB_AUTHORITY, DB.CONTACT_TABLE_NAME, CONTACT_TABLE_INDEX);
		uriMatcher.addURI(DB.DB_AUTHORITY,DB.INCOME_TABLE_NAME, INCOME_TABLE_INDEX);
        uriMatcher.addURI(DB.DB_AUTHORITY,DB.EXPENSE_TABLE_NAME, EXPENSE_TABLE_INDEX);
        uriMatcher.addURI(DB.DB_AUTHORITY,DB.BORROW_TABLE_NAME, BORROW_TABLE_INDEX);
        uriMatcher.addURI(DB.DB_AUTHORITY,DB.LENT_TABLE_NAME, LENDED_TABLE_INDEX);
        uriMatcher.addURI(DB.DB_AUTHORITY,DB.CATEGORY_TABLE_NAME, CATEGORY_TABLE_INDEX);
        uriMatcher.addURI(DB.DB_AUTHORITY,DB.CIRCLE_TABLE_NAME, CIRCLE_TABLE_INDEX);
        uriMatcher.addURI(DB.DB_AUTHORITY,DB.NOTIFICATION_TABLE_NAME, NOTIFICATION_TABLE_INDEX);
        uriMatcher.addURI(DB.DB_AUTHORITY,DB.COMMON_TABLE_NAME, COMMON_TABLE_INDEX);

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
            case 2:

            {
                long rowId = qpinionDBinstance.insert(DB.INCOME_TABLE_NAME, null, newRow);
            //    Toast.makeText(getContext(),"Inserted in Income Table",Toast.LENGTH_SHORT).show();

                if(rowId > 0) {
                    Log.d("in cp","inserted in "+DB.INCOME_TABLE_NAME);
                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri, null);
                }
                break;
            }

            case 3:
            {
                //this method will accept a row(values) and and insert into the table
                long rowId = qpinionDBinstance.insert(DB.EXPENSE_TABLE_NAME, null, newRow);
                //Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                if (rowId > 0) {
                    Log.d("in cp","inserted in "+DB.EXPENSE_TABLE_NAME);
                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri, null);
                }
                break;
            }
            case 4:
            {
                //this method will accept a row(values) and and insert into the table
                long rowId = qpinionDBinstance.insert(DB.BORROW_TABLE_NAME, null, newRow);
                //Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                if (rowId > 0) {
                    Log.d("in cp","inserted in "+DB.BORROW_TABLE_NAME);
                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri, null);
                }
                break;
            }

            case 5:
            {
                //this method will accept a row(values) and and insert into the table
                long rowId = qpinionDBinstance.insert(DB.LENT_TABLE_NAME, null, newRow);
                //Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                if (rowId > 0) {
                    Log.d("in cp","inserted in "+DB.LENT_TABLE_NAME);
                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri, null);
                }
                break;
            }

            case 6:
            {
                //this method will accept a row(values) and and insert into the table
                long rowId = qpinionDBinstance.insert(DB.CATEGORY_TABLE_NAME, null, newRow);
                //Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                if (rowId > 0) {
                    Log.d("in cp","inserted in "+DB.CATEGORY_TABLE_NAME);
                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri, null);
                }
                break;
            }

            case 7:
            {
                //this method will accept a row(values) and and insert into the table
                long rowId = qpinionDBinstance.insert(DB.CIRCLE_TABLE_NAME, null, newRow);
                //Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                if (rowId > 0) {
                    Log.d("in cp","inserted in "+DB.CIRCLE_TABLE_NAME);
                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri, null);
                }
                break;
            }

            case 8:
            {
                //this method will accept a row(values) and and insert into the table
                long rowId = qpinionDBinstance.insert(DB.NOTIFICATION_TABLE_NAME, null, newRow);
                //Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                if (rowId > 0) {
                    Log.d("in cp","inserted in "+DB.NOTIFICATION_TABLE_NAME);
                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri, null);
                }
                break;
            }

            case 9:
            {
                //this method will accept a row(values) and and insert into the table
                long rowId = qpinionDBinstance.insert(DB.COMMON_TABLE_NAME, null, newRow);
                //Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                if (rowId > 0) {
                    Log.d("in cp","inserted in "+DB.COMMON_TABLE_NAME);
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

            case 2:

        {
            Log.d("in cp","Quering data from "+DB.INCOME_TABLE_NAME);
            c = qpinionDBinstance.query(DB.INCOME_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            Log.d("in CP", "cursor returned from " + DB.INCOME_TABLE_NAME);
            c.setNotificationUri(getContext().getContentResolver(),uri);
            return c;
        }

            case 3:

            {
                Log.d("in cp","Quering data from "+DB.EXPENSE_TABLE_NAME);
                c = qpinionDBinstance.query(DB.EXPENSE_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                Log.d("in CP", "cursor returned from " + DB.EXPENSE_TABLE_NAME);
                c.setNotificationUri(getContext().getContentResolver(),uri);
                return c;
            }

            case 4:

            {
                Log.d("in cp","Quering data from "+DB.BORROW_TABLE_NAME);
                c = qpinionDBinstance.query(DB.BORROW_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                Log.d("in CP", "cursor returned from " + DB.BORROW_TABLE_NAME);
                c.setNotificationUri(getContext().getContentResolver(),uri);
                return c;
            }

            case 5:

            {
                Log.d("in cp","Quering data from "+DB.LENT_TABLE_NAME);
                c = qpinionDBinstance.query(DB.LENT_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                Log.d("in CP", "cursor returned from " + DB.LENT_TABLE_NAME);
                c.setNotificationUri(getContext().getContentResolver(),uri);
                return c;
            }

            case 6:

            {
                Log.d("in cp","Quering data from "+DB.CATEGORY_TABLE_NAME);
                c = qpinionDBinstance.query(DB.CATEGORY_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                Log.d("in CP", "cursor returned from " + DB.CATEGORY_TABLE_NAME);
                c.setNotificationUri(getContext().getContentResolver(),uri);
                return c;
            }

            case 7:

            {
                Log.d("in cp","Quering data from "+DB.CIRCLE_TABLE_NAME);
                c = qpinionDBinstance.query(DB.CIRCLE_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                Log.d("in CP", "cursor returned from " + DB.CIRCLE_TABLE_NAME);
                c.setNotificationUri(getContext().getContentResolver(),uri);
                return c;
            }

            case 8:

            {
                Log.d("in cp","Quering data from "+DB.NOTIFICATION_TABLE_NAME);
                c = qpinionDBinstance.query(DB.NOTIFICATION_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                Log.d("in CP", "cursor returned from " + DB.NOTIFICATION_TABLE_NAME);
                c.setNotificationUri(getContext().getContentResolver(),uri);
                return c;
            }

            case 9:

            {
                Log.d("in cp","Quering data from "+DB.COMMON_TABLE_NAME);
                c = qpinionDBinstance.query(DB.COMMON_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                Log.d("in CP", "cursor returned from " + DB.COMMON_TABLE_NAME);
                c.setNotificationUri(getContext().getContentResolver(),uri);
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
        case 2:
           {
               long rowId = qpinionDBinstance.update(DB.INCOME_TABLE_NAME, values, selection, selectionArgs);
               Toast.makeText(getContext(),"Inserted", Toast.LENGTH_SHORT).show();

               if(rowId > 0) {

                   Uri objUri = ContentUris.withAppendedId(uri, rowId);
                   getContext().getContentResolver().notifyChange(objUri , null);
                   Log.d("in cp","updated :"+DB.INCOME_TABLE_NAME);
               }
               break;

           }

            case 3:
            {
                long rowId = qpinionDBinstance.update(DB.EXPENSE_TABLE_NAME, values, selection, selectionArgs);
                Toast.makeText(getContext(),"Inserted", Toast.LENGTH_SHORT).show();

                if(rowId > 0) {

                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri , null);
                    Log.d("in cp","updated :"+DB.EXPENSE_TABLE_NAME);
                }
                break;

            }

            case 4:
            {
                long rowId = qpinionDBinstance.update(DB.BORROW_TABLE_NAME, values, selection, selectionArgs);
                Toast.makeText(getContext(),"Inserted", Toast.LENGTH_SHORT).show();

                if(rowId > 0) {

                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri , null);
                    Log.d("in cp","updated :"+DB.BORROW_TABLE_NAME);
                }
                break;

            }

            case 5:
            {
                long rowId = qpinionDBinstance.update(DB.LENT_TABLE_NAME, values, selection, selectionArgs);
                Toast.makeText(getContext(),"Inserted", Toast.LENGTH_SHORT).show();

                if(rowId > 0) {

                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri , null);
                    Log.d("in cp","updated :"+DB.LENT_TABLE_NAME);
                }
                break;

            }

            case 6:
            {
                long rowId = qpinionDBinstance.update(DB.CATEGORY_TABLE_NAME, values, selection, selectionArgs);
                Toast.makeText(getContext(),"Inserted", Toast.LENGTH_SHORT).show();

                if(rowId > 0) {

                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri , null);
                    Log.d("in cp","updated :"+DB.CATEGORY_TABLE_NAME);
                }
                break;

            }

            case 7:
            {
                long rowId = qpinionDBinstance.update(DB.CIRCLE_TABLE_NAME, values, selection, selectionArgs);
                Toast.makeText(getContext(),"Inserted", Toast.LENGTH_SHORT).show();

                if(rowId > 0) {

                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri , null);
                    Log.d("in cp","updated :"+DB.CIRCLE_TABLE_NAME);
                }
                break;

            }

            case 8:
            {
                long rowId = qpinionDBinstance.update(DB.NOTIFICATION_TABLE_NAME, values, selection, selectionArgs);
                Toast.makeText(getContext(),"Inserted", Toast.LENGTH_SHORT).show();

                if(rowId > 0) {

                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri , null);
                    Log.d("in cp","updated :"+DB.NOTIFICATION_TABLE_NAME);
                }
                break;

            }

            case 9:
            {
                long rowId = qpinionDBinstance.update(DB.COMMON_TABLE_NAME, values, selection, selectionArgs);
                Toast.makeText(getContext(),"Inserted", Toast.LENGTH_SHORT).show();

                if(rowId > 0) {

                    Uri objUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(objUri , null);
                    Log.d("in cp","updated :"+DB.COMMON_TABLE_NAME);
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
                Log.d("in CP", "item deleted from db");
		    }
			return 0;
		
		}
        case 2:

        {
            Log.d("in cp","Deleting data from "+DB.INCOME_TABLE_NAME);
            long rowId = qpinionDBinstance.delete(DB.INCOME_TABLE_NAME, selection, selectionArgs);

            if(rowId > 0) {
                Uri objUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(objUri, null);
                Log.d("in CP", "item deleted from db");
            }
            return 0;
        }

        case 3:

        {
            Log.d("in cp","Deleting data from "+DB.EXPENSE_TABLE_NAME);
            long rowId = qpinionDBinstance.delete(DB.EXPENSE_TABLE_NAME, selection, selectionArgs);

            if(rowId > 0) {
                Uri objUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(objUri, null);
                Log.d("in CP", "item deleted from db");
            }
            return 0;
        }

        case 4:

        {
            Log.d("in cp","Deleting data from "+DB.BORROW_TABLE_NAME);
            long rowId = qpinionDBinstance.delete(DB.BORROW_TABLE_NAME, selection, selectionArgs);

            if(rowId > 0) {
                Uri objUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(objUri, null);
                Log.d("in CP", "item deleted from db");
            }
            return 0;
        }

        case 5:

        {
            Log.d("in cp","Deleting data from "+DB.LENT_TABLE_NAME);
            long rowId = qpinionDBinstance.delete(DB.LENT_TABLE_NAME, selection, selectionArgs);

            if(rowId > 0) {
                Uri objUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(objUri, null);
                Log.d("in CP", "item deleted from db");
            }
            return 0;
        }

        case 6:

        {
            Log.d("in cp","Deleting data from "+DB.CATEGORY_TABLE_NAME);
            long rowId = qpinionDBinstance.delete(DB.CATEGORY_TABLE_NAME, selection, selectionArgs);

            if(rowId > 0) {
                Uri objUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(objUri, null);
                Log.d("in CP", "item deleted from db");
            }
            return 0;
        }

        case 7:

        {
            Log.d("in cp","Deleting data from "+DB.CIRCLE_TABLE_NAME);
            long rowId = qpinionDBinstance.delete(DB.CIRCLE_TABLE_NAME, selection, selectionArgs);

            if(rowId > 0) {
                Uri objUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(objUri, null);
                Log.d("in CP", "item deleted from db");
            }
            return 0;
        }

        case 8:

        {
            Log.d("in cp","Deleting data from "+DB.NOTIFICATION_TABLE_NAME);
            long rowId = qpinionDBinstance.delete(DB.NOTIFICATION_TABLE_NAME, selection, selectionArgs);

            if(rowId > 0) {
                Uri objUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(objUri, null);
                Log.d("in CP", "item deleted from db");
            }
            return 0;
        }

        case 9:

        {
            Log.d("in cp","Deleting data from "+DB.COMMON_TABLE_NAME);
            long rowId = qpinionDBinstance.delete(DB.COMMON_TABLE_NAME, selection, selectionArgs);

            if(rowId > 0) {
                Uri objUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(objUri, null);
                Log.d("in CP", "item deleted from db");
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
