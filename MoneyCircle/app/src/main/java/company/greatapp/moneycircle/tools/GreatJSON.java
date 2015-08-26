package company.greatapp.moneycircle.tools;

import android.content.Context;
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
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Split;

/**
 * Created by gyanendra.sirohi on 7/14/2015.
 */
public class GreatJSON {

    public static JSONArray getJsonArrayForModelList(ArrayList<Model> models) {
        String jsonString = "";
        JSONArray array = new JSONArray();

        if(models == null) return null;
        for(Model m : models) {
            array.put(getJsonObjectForModel(m));
        }

        jsonString = array.toString();
        Log.d("split", "MODEL JSON ARRAY : " + jsonString);
        return array;
    }

    public static JSONObject getJsonObjectForModel(Model model) {
        if(model == null) {
            Log.d("SPLIT", "JSON Model is Null");
            return null;
        }

        model.printModelData();
        String jsonString = "";
        JSONObject obj = new JSONObject();
        try{
            obj.put("title", model.getTitle());
            obj.put("uid", model.getUID());
            obj.put("dbid", model.getDbId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = obj.toString();
        Log.d("split", "MODEL JSON OBJECT : " + jsonString);
        return obj;
    }
    public static ArrayList<Model> getModelListFromJsonString(String json, Context context, int modelType) {
        Log.i("SPLIT","getting Model List for JSON : "+json);
        ArrayList<Model> list = new ArrayList<Model>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i<length;i++) {
                JSONObject obj = (JSONObject)array.get(i);
                Model model = getModelFromJsonString(obj.toString(), context, modelType);
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("SPLIT","returning Model List of Size : "+list.size());
        return list;
    }

    public static Model getModelFromJsonString(String json, Context context, int modelType) {
        Model model = null;
        if(context == null) return null;
        if(TextUtils.isEmpty(json)){
            Log.d("SPLIT","JSON is NULL or EMPTY");
            return null;
        }
        Log.d("SPLIT","getting Model for below json-->");
        Log.d("SPLIT","JSON : "+json);
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            model = (Model)Tools.getDbInstance(context,uid, modelType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }

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
    public static ArrayList<Contact> getContactListFromJsonString(String json, Context context) {
        ArrayList<Contact> list = new ArrayList<Contact>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i<length;i++) {
                JSONObject obj = (JSONObject)array.get(i);
                Contact contact = getContactFromJsonString(obj.toString(), context);
                list.add(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Contact getContactFromJsonString(String json, Context context) {
        Contact contact = null;
        if(context == null) return null;
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
            contact = (Contact)Tools.getDbInstance(context,uid, Model.MODEL_TYPE_CONTACT);
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
        if(circle == null) return null;
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
    public static ArrayList<Circle> getCircleListFromJsonString(String json, Context context) {
        if(context == null) return null;
        ArrayList<Circle> list = new ArrayList<Circle>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i<length;i++) {
                JSONObject obj = (JSONObject)array.get(i);
                Circle circle = getCircleFromJsonString(obj.toString(), context);
                list.add(circle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Circle getCircleFromJsonString(String json, Context context) {
        Circle circle = null;
        if(context == null) return circle;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            circle = (Circle)Tools.getDbInstance(context, uid, Model.MODEL_TYPE_CIRCLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return circle;
    }

    public static JSONObject getJsonObjectForSplit(Split split) {
        if(split == null) return null;
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

    private static Split getSplitFromJsonString(String json, Context context) {
        if(context == null) return null;
        Split split = null;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            split = (Split)Tools.getDbInstance(context, uid, Model.MODEL_TYPE_SPLIT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return split;
    }

    public static JSONObject getJsonObjectForExpense(Expense expense) {
        if(expense == null) return null;
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

    public static Expense getExpenseFromJsonString(String json, Context context) {
        if(context == null) return null;
        Expense expense = null;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            expense = (Expense)Tools.getDbInstance(context, uid, Model.MODEL_TYPE_EXPENSE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expense;
    }
    public static JSONArray getJsonArrayForLentList(ArrayList<Lent> lents) {
        if(lents == null) return null;
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
        if(lent == null) return null;
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
    public static ArrayList<Lent> getLentListFromJsonString(String json, Context context) {
        if(context == null) return null;
        ArrayList<Lent> list = new ArrayList<Lent>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            for (int i = 0; i<length;i++) {
                JSONObject obj = (JSONObject)array.get(i);
                Lent lent = getLentFromJsonString(obj.toString(), context);
                list.add(lent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Lent getLentFromJsonString(String json, Context context) {
        if(context == null) return null;
        Lent lent = null;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            String uid = obj.getString("uid");
            String dbid = obj.getString("dbid");
            lent = (Lent)Tools.getDbInstance(context, uid, Model.MODEL_TYPE_LENT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lent;
    }
}
