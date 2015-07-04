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
    public static final String DB_ID = "dbId";
    public static final String UID = "uid";
//-----------------------------------------------------------------//


//--------------CONTACT TABLE--------------------------------------//


    public static final String CONTACT_TABLE_NAME = "contactTable";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phone";
    public static final String IMAGE_URI = "imageuri";
    public static final String EMAIL = "email";
    public static final String REGISTERED = "registered";
    public static final String CIRCLE_COUNT = "circleCount";
    public static final String CIRCLE_JSON_STRING = "circleJson";
    public static final String SERVER_NAME = "serverName";
    public static final String SERVER_ID = "serverId";

    public static final Uri CONTACT_TABLE_URI = Uri.parse("content://"+DB_AUTHORITY+"/" + CONTACT_TABLE_NAME);
    public static final String[] CONTACT_TABLE_PROJECTION = new String[]{
            DB_ID,UID,NAME,PHONE_NUMBER,IMAGE_URI,EMAIL,REGISTERED,
            CIRCLE_COUNT,CIRCLE_JSON_STRING,SERVER_NAME,SERVER_ID};
//----------------------------------------------------------------//



}
