package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;

import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.MoneyCirclePackageFromServer;

/**
 * Created by Ashish on 10-07-2015.
 */
public class NotificationManager extends BaseModelManager  {

    Context context;
    ArrayList<Model> notification = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();


    @Override
    public Model createHeavyItemFromCursor(Cursor cursor) {

      return null;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    public static Model createLightItemFromCursor(Cursor cursor) {
        return null;

    }

    @Override
    protected void loadItemsFromDB() {

        notification.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.NOTIFICATION_TABLE_URI,
                DB.NOTIFICATION_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Model model = createLightItemFromCursor(c);
                notification.add(model);
                titles.add(model.getTitle());
                c.moveToNext();
            }
            c.close();
        }

    }

    @Override
    protected Context getContext() {
        return null;
    }

    @Override
    protected Uri getTableUri() {
        return null;
    }

    @Override
    protected int getModelType() {
        return 0;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return null;
    }
}
