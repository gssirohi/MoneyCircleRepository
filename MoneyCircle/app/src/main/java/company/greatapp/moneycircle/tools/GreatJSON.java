package company.greatapp.moneycircle.tools;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.S;
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
import company.greatapp.moneycircle.model.MoneyCirclePackageFromServer;
import company.greatapp.moneycircle.model.Notification;
import company.greatapp.moneycircle.model.Split;
import company.greatapp.moneycircle.model.User;

/**
 * Created by gyanendra.sirohi on 7/14/2015.
 */
public class GreatJSON {

    ///=============================== FOR SERVER ================================================//
    public static JSONArray getPhoneNumberArrayForContactList(ArrayList<Contact> contacts) {
        String jsonString = "";
        JSONArray array = new JSONArray();

        for(Contact c : contacts) {
            array.put(getJsonObjectForPhoneNumber(c));
        }

        jsonString = array.toString();
        Log.d("split", "PHONE NUMBER JSON ARRAY : " + jsonString);
        return array;
    }

    public static JSONObject getJsonObjectForPhoneNumber(Contact contact) {
        if(contact == null) {
            Log.d("SPLIT", "Contact is Null");
            return null;
        }

        Log.d("SPLIT","getting json obj for below phoneNumber-->");
        contact.printModelData();
        String jsonString = "";
        JSONObject obj = new JSONObject();
        try{
            obj.put(S.PHONE_NUMBER, contact.getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = obj.toString();
        Log.d("split", "PHONE JSON OBJECT : " + jsonString);
        return obj;
    }
    //============================================================================================//
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

    public static Notification getNotificationFromJSONString(Context context, String json) {
        Notification notification = null;
        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            int notificationType = Integer.parseInt(obj.getString("notificationType"));

            String senderphoneNo = obj.getString("senderPhoneNo");

            Contact senderContact = Tools.getContactFromPhoneNumber(context, senderphoneNo);
            String senderName = senderContact.getContactName();
            Uri senderImageUri = senderContact.getImageUri();

            String itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");;
            String itemOwnerName = null;
            String dateString = obj.getString("dateString");
            String moneyItemTitle = obj.getString("moneyItemTitle");
            String moneyReceiverPhoneNo = obj.getString("moneyReceiverphoneNo");;
            String moneyPayerPhoneNo = obj.getString("moneyPayerphoneNo");
            String itemOwnerUID = null;

            String dueDateString = obj.getString("dueDateString");;

            String moneyReceiverName = null;
            String moneyPayerName = null;

            String amount = obj.getString("moneyItemAmount");;

            String message = null;

            User user = new User(context);

            switch (notificationType) {
                case S.NOTIFICATION_INFORMATION:
                    break;
                case S.NOTIFICATION_LENT_REQUEST:
                    moneyReceiverPhoneNo = obj.getString("moneyReceiverphoneNo");
                    moneyPayerPhoneNo = obj.getString("moneyPayerphoneNo");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    dueDateString = obj.getString("dueDateString");
                    amount = obj.getString("moneyItemAmount");
                    title = obj.getString("title");
                    break;
                case S.NOTIFICATION_BORROW_REQUEST:
                    moneyReceiverPhoneNo = obj.getString("moneyReceiverphoneNo");
                    moneyPayerPhoneNo = obj.getString("moneyPayerphoneNo");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    title = obj.getString("title");
                    break;
                case S.NOTIFICATION_PAY_REQUEST:
                    moneyReceiverPhoneNo = obj.getString("moneyReceiverphoneNo");
                    moneyPayerPhoneNo = obj.getString("moneyPayerphoneNo");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    title = obj.getString("title");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    break;
                case S.NOTIFICATION_MODIFY_lENT_REQUEST:
                    moneyReceiverPhoneNo = obj.getString("moneyReceiverphoneNo");
                    moneyPayerPhoneNo = obj.getString("moneyPayerphoneNo");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    title = obj.getString("title");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    break;
                case S.NOTIFICATION_RECEIVE_REQUEST:
                    moneyReceiverPhoneNo = obj.getString("moneyReceiverphoneNo");
                    moneyPayerPhoneNo = obj.getString("moneyPayerphoneNo");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    title = obj.getString("title");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    break;
                case S.NOTIFICATION_SETTLE_REQUEST:
                    break;
                case S.NOTIFICATION_REMINDER_REQUEST:
                    title = obj.getString("title");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    break;
                case S.NOTIFICATION_AGREE_LENT:
                    title = obj.getString("title");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    break;
                case S.NOTIFICATION_DISAGREE_LENT:
                    title = obj.getString("title");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    break;
                case S.NOTIFICATION_DELETE_LENT_REQUEST:
                    title = obj.getString("title");
                    itemOwnerPhoneNo = obj.getString("itemOwnerPhoneNo");
                    moneyItemTitle = obj.getString("moneyItemTitle");
                    break;

                default:
                    break;
            }
            message = getMessage(notificationType, senderName, amount);
            if (message == null) {
                message = "";
            }

            if (moneyReceiverPhoneNo != null && moneyPayerPhoneNo != null) {
                if (senderphoneNo.equals(moneyReceiverPhoneNo)) {
                    moneyReceiverName = senderName;
                    moneyPayerName = user.getName();
                } else {
                    moneyPayerName = senderName;
                    moneyReceiverName = user.getName();
                }
            }


            String ownerItemUID = obj.getString("ownerItemUID");

            notification = new Notification();
            notification.setTitle(title);
            notification.setAmount(Float.parseFloat(amount));
            notification.setDateString(dateString);
            notification.setDueDateString(dueDateString);
            notification.setJsonString(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notification;
    }

    public static MoneyCirclePackageFromServer getServerPackageFromJson(Context context, String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        MoneyCirclePackageFromServer serverPackage = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            int reqCode = Integer.parseInt(jsonObject.getString("reqCode"));
            serverPackage.setReqCode(reqCode);

            String senderphoneNo = jsonObject.getString("reqSenderPhone");
            serverPackage.setReqSenderPhone(senderphoneNo);
            String receiverPhoneNo = jsonObject.getString("reqReceiverPhone");
            serverPackage.setReqReceiverPhone(receiverPhoneNo);

            Contact senderContact = Tools.getContactFromPhoneNumber(context, senderphoneNo);
            String senderName = senderContact.getContactName();
            serverPackage.setReqSenderName(senderName);
            Uri senderImageUri = senderContact.getImageUri();
            serverPackage.setReqSenderImageUri(senderImageUri);

            String itemOwnerPhoneNo = jsonObject.getString("itemOwnerPhone");
            String itemOwnerName = null;
            if (itemOwnerPhoneNo.equals(receiverPhoneNo)) {
                itemOwnerName = "YOU";
            } else {
                itemOwnerName = senderName;
            }
            serverPackage.setItemOwnerName(itemOwnerName);
            String itemAssociatePhoneNo = jsonObject.getString("itemAssociatePhone");
            serverPackage.setItemAssociatePhone(itemAssociatePhoneNo);

            String moneyReceiverPhoneNo = jsonObject.getString("moneyReceiverPhone");
            serverPackage.setMoneyReceiverPhone(moneyReceiverPhoneNo);
            String moneyPayerPhoneNo = jsonObject.getString("moneyPayerPhone");
            serverPackage.setMoneyPayerPhone(moneyPayerPhoneNo);

            int ownerItemType = Integer.parseInt(jsonObject.getString("ownerItemType"));
            serverPackage.setOwnerItemType(ownerItemType);
            int associateItemtype = Integer.parseInt(jsonObject.getString("associateItemtype"));
            serverPackage.setAssociateItemtype(associateItemtype);

            String ownerItemId = jsonObject.getString("ownerItemId");
            serverPackage.setOwnerItemId(ownerItemId);
            String associateItemId = jsonObject.getString("associateItemId");
            serverPackage.setAssociateItemId(associateItemId);

            int itemBodyJsonType = Integer.parseInt(jsonObject.getString("itemBodyJsonType"));
            serverPackage.setItemBodyJsonType(itemBodyJsonType);
            String itemBodyJsonString = jsonObject.getString("itemBodyJsonString");
            serverPackage.setItemBodyJsonString(itemBodyJsonString);

            String message = jsonObject.getString("message");
            serverPackage.setMessage(message);

            String dateString = jsonObject.getString("dateString");
            serverPackage.setDateString(dateString);

            updateServerPackageWithItemInfo(serverPackage);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return serverPackage;
    }

    private static MoneyCirclePackageFromServer updateServerPackageWithItemInfo(MoneyCirclePackageFromServer serverPackage) {

        String itemJsonString = serverPackage.getItemBodyJsonString();
        if (TextUtils.isEmpty(itemJsonString)) {
            return serverPackage;
        }

        try {
            JSONObject itemJsonObj = new JSONObject(itemJsonString);

            String itemTitle = itemJsonObj.getString("title");
            serverPackage.setItemTitle(itemTitle);

            String amount = itemJsonObj.getString("moneyItemAmount");
            serverPackage.setAmount(amount);

            String itemDateString = itemJsonObj.getString("dateString");
            serverPackage.setItemDateString(itemDateString);

            String itemDueDateString = itemJsonObj.getString("dueDateString");
            serverPackage.setItemDueDateString(itemDueDateString);

            String itemDescription = itemJsonObj.getString("description");
            serverPackage.setItemDescription(itemDescription);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return serverPackage;
    }


    private static String getMessage(int notificationType, String name, String amount) {
        if (amount == null && (S.NOTIFICATION_INFORMATION != notificationType)) {
            return null;
        }
        String message = null;
        switch (notificationType) {
            case S.NOTIFICATION_LENT_REQUEST:
                message = "YOU OWE "+ amount +" to "+ name + " for this transaction";
                break;
            case S.NOTIFICATION_BORROW_REQUEST:
                message = name + " OWES YOU" + amount + " for this transaction";
                break;
            case S.NOTIFICATION_PAY_REQUEST:
                message = name + " PAYED " + amount + " to YOU for this transaction";
                break;
            case S.NOTIFICATION_SETTLE_REQUEST:
                message = name + " SETTLED UP with YOU";
                break;
            case S.NOTIFICATION_REMINDER_REQUEST:
                message = "REMINDER to pay " + amount + " to " + name;
                break;
            case S.NOTIFICATION_AGREE_LENT:
                message = name + " agree to pay "+ amount + " for this transaction";
                break;
            case S.NOTIFICATION_DISAGREE_LENT:
                message = name + " disagreed to pay "+ amount + " for this transaction";
                break;
            case S.NOTIFICATION_RECEIVE_REQUEST:
                message = name + " RECEIVED "+ amount + " for this transaction";
                break;
            case S.NOTIFICATION_DELETE_LENT_REQUEST:
                message = name + " deleted  this transaction";
                break;
            case S.NOTIFICATION_MODIFY_lENT_REQUEST:
                message = name + " modified this transaction";
                break;
            case S.NOTIFICATION_INFORMATION:
                message = "Information Message of Money Circle";
                break;
            default:
                break;
        }
        return message;
    }
}
