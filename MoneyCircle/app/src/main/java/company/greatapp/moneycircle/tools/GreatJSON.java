package company.greatapp.moneycircle.tools;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import company.greatapp.moneycircle.manager.CircleManager;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.manager.ExpenseManager;
import company.greatapp.moneycircle.manager.LentManager;
import company.greatapp.moneycircle.manager.SplitManager;
import company.greatapp.moneycircle.model.Circle;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Split;

/**
 * Created by gyanendra.sirohi on 7/14/2015.
 */
public class GreatJSON {

    public static JSONArray getJsonArrayForContactList(ArrayList<Contact> contacts) {
        String jsonString = "";
        JSONArray array = new JSONArray();

        for(Contact c : contacts) {
            array.put(getJsonObjectForContact(c));
        }

        jsonString = array.toString();
        Log.d("split", "CONTACT JSON ARRAY : " + jsonString);
        return array;
    }

    public static JSONObject getJsonObjectForContact(Contact contact) {
        if(contact == null) {
            Log.d("SPLIT", "Contact is Null");
            return null;
        }

        Log.d("SPLIT","getting json obj for below contact-->");
        contact.printModelData();
        String jsonString = "";
        JSONObject obj = new JSONObject();
        try{
            obj.put("title", contact.getTitle());
            obj.put("uid", contact.getUID());
            obj.put("dbid", contact.getDbId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = obj.toString();
        Log.d("split", "CONTACT JSON OBJECT : " + jsonString);
        return obj;
    }
    public static ArrayList<Contact> getContactListFromJsonString(String json, ContactManager cm) {
        ArrayList<Contact> list = new ArrayList<Contact>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i<length;i++) {
                JSONObject obj = (JSONObject)array.get(i);
                Contact contact = getContactFromJsonString(obj.toString(), cm);
                list.add(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Contact getContactFromJsonString(String json, ContactManager cm) {
        Contact contact = null;
        if(cm == null) return null;
        if(TextUtils.isEmpty(json)){
            Log.d("SPLIT","JSON is NULL or EMPTY");
            return null;
        }
        Log.d("SPLIT","getting Contact for below json-->");
        Log.d("SPLIT","JSON : "+json);
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            contact = (Contact)cm.getItemFromListByUID(uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contact;
    }
    public static JSONArray getJsonArrayForCircleList(ArrayList<Circle> circles) {
        String jsonString = "";
        JSONArray array = new JSONArray();

        for(Circle c : circles) {
            array.put(getJsonObjectForCircle(c));
        }

        jsonString = array.toString();
        Log.d("split", "CIRCLE JSON ARRAY : " + jsonString);
        return array;
    }

    public static JSONObject getJsonObjectForCircle(Circle circle) {
        String jsonString = "";
        JSONObject obj = new JSONObject();
        try{
            obj.put("title", circle.getTitle());
            obj.put("uid", circle.getUID());
            obj.put("dbid", circle.getDbId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = obj.toString();
        Log.d("split", "CIRCLE JSON OBJECT : " + jsonString);
        return obj;
    }
    public static ArrayList<Circle> getCircleListFromJsonString(String json, CircleManager cm) {
        ArrayList<Circle> list = new ArrayList<Circle>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i<length;i++) {
                JSONObject obj = (JSONObject)array.get(i);
                Circle circle = getCircleFromJsonString(obj.toString(), cm);
                list.add(circle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Circle getCircleFromJsonString(String json, CircleManager cm) {
        Circle circle = null;
        if(cm == null) return circle;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            circle = (Circle)cm.getItemFromListByUID(uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return circle;
    }

    public static JSONObject getJsonObjectForSplit(Split split) {
        String jsonString = "";
        JSONObject obj = new JSONObject();
        try{
            obj.put("title", split.getTitle());
            obj.put("uid", split.getUID());
            obj.put("dbid", split.getDbId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = obj.toString();
        Log.d("split", "Split JSON OBJECT : " + jsonString);
        return obj;
    }

    private static Split getSplitFromJsonString(String json, SplitManager cm) {
        Split split = null;
        if(cm == null) return split;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            split = (Split)cm.getItemFromListByUID(uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return split;
    }

    public static JSONObject getJsonObjectForExpense(Expense expense) {
        String jsonString = "";
        JSONObject obj = new JSONObject();
        try{
            obj.put("title", expense.getTitle());
            obj.put("uid", expense.getUID());
            obj.put("dbid", expense.getDbId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = obj.toString();
        Log.d("expense", "Expense JSON OBJECT : " + jsonString);
        return obj;
    }

    private static Expense getExpenseFromJsonString(String json, ExpenseManager cm) {
        Expense expense = null;
        if(cm == null) return expense;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            expense = (Expense)cm.getItemFromListByUID(uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expense;
    }
    public static JSONArray getJsonArrayForLentList(ArrayList<Lent> lents) {
        String jsonString = "";
        JSONArray array = new JSONArray();

        for(Lent c : lents) {
            array.put(getJsonObjectForLent(c));
        }

        jsonString = array.toString();
        Log.d("split", "LENT JSON ARRAY : " + jsonString);
        return array;
    }

    public static JSONObject getJsonObjectForLent(Lent lent) {
        String jsonString = "";
        JSONObject obj = new JSONObject();
        try{
            obj.put("title", lent.getTitle());
            obj.put("uid", lent.getUID());
            obj.put("dbid", lent.getDbId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = obj.toString();
        Log.d("split", "LENT JSON OBJECT : " + jsonString);
        return obj;
    }
    public static ArrayList<Lent> getLentListFromJsonString(String json, LentManager cm) {
        ArrayList<Lent> list = new ArrayList<Lent>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i<length;i++) {
                JSONObject obj = (JSONObject)array.get(i);
                Lent lent = getLentFromJsonString(obj.toString(), cm);
                list.add(lent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Lent getLentFromJsonString(String json, LentManager cm) {
        Lent lent = null;
        if(cm == null) return lent;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            lent = (Lent)cm.getItemFromListByUID(uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lent;
    }
}
