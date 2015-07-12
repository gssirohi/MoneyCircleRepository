package company.greatapp.moneycircle.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Circle;

import company.greatapp.moneycircle.model.Model;

/**
 * Created by Ashish on 10-07-2015.
 */
public class CircleManager extends BaseModelManager  {
    Context context;
    ArrayList<Model> circle = new ArrayList<Model>();

    public CircleManager(Context context) {
        this.context = context;
        loadItemsFromDB();
    }
    @Override
    public Model createItemFromCursor(Cursor cursor) {

        if(cursor == null) return null;

        int dbId               = cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String circlename      = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_NAME));
        String circleJsonStrin = cursor.getString(cursor.getColumnIndex(DB.CIRCLE_JSON_STRING));

        Circle circle = new Circle();
        circle.setDbId(dbId);
        circle.setUID(uid);
        circle.setName(circlename);
        circle.setJsonMembers(circleJsonStrin);
        return circle;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {

        circle.clear();

        Cursor c = context.getContentResolver().query(DB.CIRCLE_TABLE_URI,
                DB.CIRCLE_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
//           TODO
            c.close();
        }

    }

    @Override
    public ArrayList<Model> getItemList() {
        return null;
    }

    @Override
    public Model getItemFromListByUID(String uid) {
        return null;
    }

    @Override
    public void insertItemInDB(Model model) {

        Circle circle = (Circle)model;
        String uid = circle.getUID();
        uid = uid.replaceAll("NEW","DB");
        circle.setUID(uid);
        ContentValues values = circle.getContentValues();
        context.getContentResolver().insert(DB.CIRCLE_TABLE_URI, values);

    }

    @Override
    public void updateItemInDB(Model model) {

    }

    @Override
    public void deleteItemFromDB(Model model) {

    }

    @Override
    public boolean isItemExistInDb(Model model) {
        return false;
    }

    @Override
    public void printManagerData() {

    }
}
