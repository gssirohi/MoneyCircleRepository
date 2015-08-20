package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Circle;

import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.GreatJSON;

/**
 * Created by Ashish on 10-07-2015.
 */
public class CircleManager extends BaseModelManager {
    private final ContactManager mContactManager;
    Context mContext;
    ArrayList<Model> circles = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();

    public CircleManager(Context context) {
        this.mContext = context;
        mContactManager = new ContactManager(context);
        loadItemsFromDB();
    }

    public CircleManager(Context context, ContactManager contactManager) {
        this.mContext = context;
        mContactManager = contactManager;
    }

    @Override
    public Model createHeavyItemFromCursor(Cursor cursor) {

        if (cursor == null) return null;

        int dbId = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid = cursor.getString(cursor.getColumnIndex(DB.UID));
        String circlename = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_NAME));
        String jsonString = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String contactJson = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_CONTACTS_JSON));

        Circle circle = new Circle(circlename, uid);
        circle.setDbId(dbId);
        circle.setContactsJson(contactJson);
        circle.setJsonString(jsonString);
        circle.setMemberList(GreatJSON.getContactListFromJsonString(contactJson, mContactManager));

        return circle;
    }

    public static Model createLightItemFromCursor(Cursor cursor) {

        if (cursor == null) return null;

        int dbId = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid = cursor.getString(cursor.getColumnIndex(DB.UID));
        String circlename = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_NAME));
        String jsonString = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String contactJson = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_CONTACTS_JSON));

        Circle circle = new Circle(circlename, uid);
        circle.setDbId(dbId);
        circle.setContactsJson(contactJson);
        circle.setJsonString(jsonString);
        //circle.setMemberList(GreatJSON.getContactListFromJsonString(contactJson, mContactManager));

        return circle;
    }


    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {

        circles.clear();
        titles.clear();

        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(DB.CIRCLE_TABLE_URI,
                    DB.CIRCLE_TABLE_PROJECTION, null, null, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    Model model = createHeavyItemFromCursor(c);
                    circles.add(model);
                    titles.add(model.getTitle());
                    c.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    @Override
    protected Context getContext() {
        return mContext;
    }

    @Override
    protected Uri getTableUri() {
        return DB.CIRCLE_TABLE_URI;
    }

    @Override
    protected int getModelType() {
        return Model.MODEL_TYPE_CIRCLE;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return circles;
    }

    public ArrayList<String> getItemNameList() {
        return titles;
    }

}
