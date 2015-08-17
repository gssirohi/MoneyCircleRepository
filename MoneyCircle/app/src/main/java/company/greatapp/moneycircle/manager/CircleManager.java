package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

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
    Context context;
    ArrayList<Model> circles = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();

    public CircleManager(Context context) {
        this.context = context;
        mContactManager = new ContactManager(context);
        loadItemsFromDB();
    }

    @Override
    public Model createItemFromCursor(Cursor cursor) {

        if (cursor == null) return null;

        int dbId = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid = cursor.getString(cursor.getColumnIndex(DB.UID));
        String circlename = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_NAME));
        String jsonString = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String contactJson = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_CONTACTS_JSON));

        Circle circle = new Circle();
        circle.setDbId(dbId);
        circle.setUID(uid);
        circle.setCircleName(circlename);
        circle.setContactsJson(contactJson);
        circle.setJsonString(jsonString);
        circle.setContacts(GreatJSON.getContactListFromJsonString(contactJson, mContactManager));

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
            c = context.getContentResolver().query(DB.CIRCLE_TABLE_URI,
                    DB.CIRCLE_TABLE_PROJECTION, null, null, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    Model model = createItemFromCursor(c);
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
        return context;
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
