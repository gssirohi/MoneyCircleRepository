package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 6/29/2015.
 */
public abstract class Model {
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


    public Model()
    {

    }

    public abstract void setTitle(String title);
    public abstract void setModelType(int modelType);
    public abstract void setDbId(int dbId);
    public abstract void setUID(String uid);
    public abstract void setJsonString(String jsonString);

    public abstract String getTitle();
    public abstract int getModelType();
    public abstract String getUID();
    public abstract int getDbId();
    public abstract String getJsonString();

    public abstract ContentValues getContentValues();
    protected abstract Uri getTableUri();

    public void printModelData(){
        Log.d("SPLIT", "====================MODEL===================");
        Log.d("SPLIT","DBID["+getDbId()+"] : UID["+getUID()+"]");
        Log.d("SPLIT","TITLE : "+getTitle());
        Log.d("SPLIT", "-----------------------------------------------");
    }

    public  boolean isDBInstance() {
        String uid = getUID();
        if (uid.contains("DB"))
            return true;
        else
            return false;
    }
    public void insertItemInDB(Context context) {
        String uid = getUID();
        uid = uid.replaceAll("NEW","DB");
        setUID(uid);
        Log.d("Split","MODEL: INSERTING :----->");
        printModelData();
        ContentValues values = getContentValues();
        context.getContentResolver().insert(getTableUri(), values);
    }


}
