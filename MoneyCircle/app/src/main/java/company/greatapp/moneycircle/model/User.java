package company.greatapp.moneycircle.model;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.S;

public class User {

    private String regId;
    private String name;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private int gender;
    private int avator;
    private String sid;
    private boolean gcm_is_registered;
    private boolean qpinion_is_registered;
    private String facebook_account_id;
    private String birthday;

    public static final int U_REGID = 10;
    public static final int U_NAME = 11;
    public static final int U_PHONE = 12;
    public static final int U_EMAIL = 13;
    public static final int U_GENDER = 14;
    public static final int U_AVATOR = 15;
    public static final int U_SID = 16;
    public static final int U_GCM_IS_REGISTERED = 17;
    public static final int U_APP_SERVER_IS_REGISTERED = 18;
    private static final String TAG = "USER";

    public static final int MALE = 1;
    public static final int FEMALE = 2;
    public static final String PREFS_REG_ID = "RegId";
    public static final String PREFS_USER_NAME = "UserName";
    public static final String PREFS_PHONE_NUMBER = "phoneNumber";
    public static final String PREFS_EMAIL_ID = "emailId";
    public static final String PREFS_IS_REGISTERED_ON_GCM = "isRegisteredOnGCM";
    public static final String PREFS_IS_REGISTERED_ON_QPINION = "isRegisteredOnMoneyCircle";
    public static final String PREFS_GENDER = "gender";
    public static final String PREFS_AVATOR = "avator";
    public static final String PREFS_SID = "sid";
    public static final String PREFS_FACEBOOK_ACCOUNT_ID = "facebook_account_id";
    public static final String PREFS_FIRST_NAME = "first_name";
    public static final String PREFS_LAST_NAME = "last_name";
    public static final String PREFS_BIRTHDAY = "birthday";

    private SharedPreferences userPrefs;
    private Context mContext;

    public User(Context context) {
        mContext = context;
        userPrefs = mContext.getSharedPreferences(C.USER_PREFERENCES, mContext.MODE_PRIVATE);
        regId = userPrefs.getString(PREFS_REG_ID, "NA");
        name = userPrefs.getString(PREFS_USER_NAME, "Unnammed");
        first_name = userPrefs.getString(PREFS_FIRST_NAME, "Unnammed");
        last_name = userPrefs.getString(PREFS_LAST_NAME, "Unnammed");
        phone = userPrefs.getString(PREFS_PHONE_NUMBER, "0101010101");
        email = userPrefs.getString(PREFS_EMAIL_ID, "abcd@qpinion.com");
        gender = userPrefs.getInt(PREFS_GENDER, MALE);
        avator = userPrefs.getInt(PREFS_AVATOR, 0);
        sid = userPrefs.getString(PREFS_SID, "000000");
        birthday = userPrefs.getString(PREFS_BIRTHDAY, "NA");
        facebook_account_id = userPrefs.getString(PREFS_FACEBOOK_ACCOUNT_ID, "NA");
        gcm_is_registered = userPrefs.getBoolean(PREFS_IS_REGISTERED_ON_GCM, false);
        qpinion_is_registered = userPrefs.getBoolean(PREFS_IS_REGISTERED_ON_QPINION, false);
    }

    public User(Context _c, String _name, String _phone, String _email, int _gender, int _avator) {
        mContext = _c;
        userPrefs = mContext.getSharedPreferences(C.USER_PREFERENCES, mContext.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = userPrefs.edit();
        if(_name != null)prefEditor.putString(PREFS_USER_NAME, _name);this.name = _name;
        if(_phone != null)prefEditor.putString(PREFS_PHONE_NUMBER, _phone);this.phone = _phone;
        if(_email != null)prefEditor.putString(PREFS_EMAIL_ID, _email);this.email = _email;
        prefEditor.putInt(PREFS_GENDER,(_gender == MALE)? MALE:FEMALE);this.gender = _gender;
        prefEditor.putInt(PREFS_AVATOR,_avator);this.setAvator(_avator);
        prefEditor.commit();
    }

    public void updateInfo(int flag, String msg) {
        userPrefs = mContext.getSharedPreferences(C.USER_PREFERENCES, mContext.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = userPrefs.edit();
        switch(flag) {
            case U_REGID:
                prefEditor.putString(PREFS_REG_ID, msg);this.regId = msg;
                break;
            case U_NAME:
                prefEditor.putString(PREFS_USER_NAME, msg);this.name = msg;
                break;
            case U_PHONE:
                prefEditor.putString(PREFS_PHONE_NUMBER, msg);this.phone = msg;
                break;
            case U_EMAIL:
                prefEditor.putString(PREFS_EMAIL_ID, msg);this.email = msg;
                break;
            case U_GENDER:
                prefEditor.putInt(PREFS_GENDER, Integer.parseInt(msg));this.gender = Integer.parseInt(msg);
                break;
            case U_AVATOR:
                prefEditor.putInt(PREFS_AVATOR, Integer.parseInt(msg));this.setAvator(Integer.parseInt(msg));
                break;
            case U_SID:
                prefEditor.putString(PREFS_SID, msg);this.sid = msg;
                break;
            case U_GCM_IS_REGISTERED:
                prefEditor.putBoolean(PREFS_IS_REGISTERED_ON_GCM, Boolean.parseBoolean(msg));
                this.gcm_is_registered = Boolean.parseBoolean(msg);
                break;
            case U_APP_SERVER_IS_REGISTERED:
                prefEditor.putBoolean(PREFS_IS_REGISTERED_ON_QPINION, Boolean.parseBoolean(msg));
                this.qpinion_is_registered = Boolean.parseBoolean(msg);
            default:
                Log.e(TAG,"illegal modifier FLAG");
        }
        prefEditor.commit();
        Log.i(TAG, "User Preferences Updated!!");
        printUser();
    }

    public void printUser() {
        Log.d(TAG,"user name : "+getName());
        Log.d(TAG,"user first name : "+getFirstName());
        Log.d(TAG,"user last name : "+getLastName());
        Log.d(TAG,"user phone : "+getPhoneNumber());
        Log.d(TAG,"user email : "+getEmail());
        Log.d(TAG,"user gender: "+getGender());
        Log.d(TAG, "user GCM: " + isRegisteredOnGCM());
        Log.d(TAG, "user Qpinion: " + isRegisteredOnMoneyCircle());
        Log.d(TAG, "user regId: " + getRegId());
        Log.d(TAG, "user facebookId: " + getFacebookAccountId());
        Log.d(TAG, "user birthday: " + getBirthday());
    }

    public String getRegId() {
        return regId;
    }


    public String getName() {
        return name;
    }



    public String getPhoneNumber() {
        return phone;
    }


    public String getEmail() {
        return email;
    }



    public int getGender() {
        return gender;
    }


    public String getSid() {
        return sid;
    }



    public boolean isRegisteredOnGCM() {
        return gcm_is_registered;
    }



    public boolean isRegisteredOnMoneyCircle() {
        return qpinion_is_registered;
    }

    public int getAvator() {
        return avator;
    }

    public void setAvator(int avator) {
        this.avator = avator;
    }


    public JSONObject getJsonObject() {
        JSONObject json = new JSONObject();
        try{
            json.put(S.USER_NAME, getName());
            json.put(S.USER_FIRST_NAME, getFirstName());
            json.put(S.USER_LAST_NAME, getLastName());
            json.put(S.PHONE_NUMBER , getPhoneNumber());
            json.put(S.EMAIL_ID, getEmail());
            json.put(S.REG_ID, getRegId());
            json.put(S.GENDER,""+ getGender());//sending it as a string
            json.put(S.FACEBOOK_ACCOUNT_ID, getFacebookAccountId());
            json.put(S.BIRTHDAY, getBirthday());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getFacebookAccountId() {
        return facebook_account_id;
    }

    public void setFacebookAccountId(String facebook_account_id) {
        this.facebook_account_id = facebook_account_id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
