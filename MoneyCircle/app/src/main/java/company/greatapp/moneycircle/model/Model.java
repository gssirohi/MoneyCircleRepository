package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import company.greatapp.moneycircle.asynctask.UpdateAccountRegistersTask;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.receiver.MoneyTransactionReceiver;
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 6/29/2015.
 */
public abstract class Model implements Comparable<Model>{
    public static final String MODEL_TYPE              = "modelType";
    public static final int MODEL_TYPE_CONTACT              = 1;
    public static final int MODEL_TYPE_CIRCLE               = 2;
    public static final int MODEL_TYPE_INCOME               = 3;
    public static final int MODEL_TYPE_EXPENSE              = 4;
    public static final int MODEL_TYPE_BORROW               = 5;
    public static final int MODEL_TYPE_LENT                 = 6;
    public static final int MODEL_TYPE_SPLIT                = 7;
    public static final int MODEL_TYPE_NOTIFICATION         = 8;
    public static final int MODEL_TYPE_USER                 = 9;
    public static final int MODEL_TYPE_CATEGORY             = 10;


    public Model()
    {

    }

    public abstract void setTitle(String title);
    public abstract void setModelType(int modelType);
    public abstract void setDbId(int dbId);
    public abstract void setUID(String uid);
    public abstract void setJsonString(String jsonString);
    public abstract void setAmount(float amount);

    public abstract void setDateString(String dateString);
    public abstract void setDueDateString(String dueDateString);
    public abstract void setLinkedContact(Contact contact);
    public abstract void setCategory(String categoryUid);


    public abstract String getTitle();
    public abstract int getModelType();
    public abstract String getUID();
    public abstract int getDbId();
    public abstract String getJsonString();
    public abstract float getAmount();

    public abstract String getDateString();
    public abstract String getDueDateString();
    public abstract Contact getLinkedContact();
    public abstract String getCategory();

    public abstract ContentValues getContentValues();
    protected abstract Uri getTableUri();

    public String printModelData(){
        String msg = "PRINT MODEL : ";
//        msg = "\n====================MODEL===================\n";
//        msg = msg+"DBID["+getDBId()+"] : UID["+getUID()+"]\n";
        msg = msg+"TITLE : "+getTitle()+"\n";
//        msg = msg+"-----------------------------------------------\n";
        Log.d("SPLIT",msg);
        return msg;
    }

    public  boolean isDBInstance() {
        String uid = getUID();
        if (uid.contains("DB"))
            return true;
        else
            return false;
    }
    public Uri insertItemInDB(Context context) {
        String uid = getUID();
        uid = uid.replaceAll("NEW","DB");
        setUID(uid);
        Log.d("Split","MODEL: INSERTING :----->");
        printModelData();
        ContentValues values = getContentValues();
        Uri uri = context.getContentResolver().insert(getTableUri(), values);
        Tools.sendTransactionBroadCast(context, this, getModelType());
        return uri;
    }

    public int updateItemInDb(Context context) {
        String where = DB.DB_ID + "=" + getDbId();
        String[] selectionArgs = null;
        Log.d("Split","MODEL: UPDATING :----->");
        printModelData();
        ContentValues values = getContentValues();
        int result =  context.getContentResolver().update(getTableUri(), values, where, selectionArgs);
        Tools.sendTransactionBroadCast(context, this, getModelType());
        return result;
    }

    public int deleteItemInDb(Context context) {

            String where = DB.DB_ID + "=" + getDbId();
            String[] selectionArgs = null;
            Log.d("Split","MODEL: DELETING :----->");
            printModelData();
            ContentValues values = getContentValues();
            int result =  context.getContentResolver().delete(getTableUri(), where, selectionArgs);
            Tools.sendTransactionBroadCast(context, this, getModelType());
        return result;
    }

    @Override
    public int compareTo(Model anotherModel) {
        return anotherModel.getUID().compareTo(this.getUID());
    }
}
