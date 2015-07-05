package company.greatapp.moneycircle.model;

import android.content.ContentValues;

import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 6/29/2015.
 */
public abstract class Model {
    public static final int MODEL_TYPE_REGISTERED_CONTACT   = 1;
    public static final int MODEL_TYPE_CIRCLE               = 2;
    public static final int MODEL_TYPE_INCOME               = 3;
    public static final int MODEL_TYPE_EXPENSE              = 4;
    public static final int MODEL_TYPE_BORROW               = 5;
    public static final int MODEL_TYPE_LEND                 = 6;
    public static final int MODEL_TYPE_NOTIFICATION         = 7;
    public static final int MODEL_TYPE_USER                 = 8;


    public Model()
    {

    }

    public abstract void setTitle(String title);
    public abstract void setModelType(int modelType);
    public abstract void setDbId(int dbId);
    public abstract void setUID(String uid);

    public abstract String getTitle();
    public abstract int getModelType();
    public abstract String getUID();
    public abstract int getDbId();
    public abstract ContentValues getContentValues();

    public abstract void printModelData();


}
