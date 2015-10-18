package company.greatapp.moneycircle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.InPackage;
import company.greatapp.moneycircle.view.CategoryItemView;
import company.greatapp.moneycircle.view.NotificationItemView;

/**
 * Created by Gyanendrasingh on 9/6/2015.
 */
public class CategoryCursorAdapter extends CursorAdapter {


    public CategoryCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = new CategoryItemView(context, null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int c = cursor.getCount();

        int pos = cursor.getPosition();
        pos = pos+1;
        int p = (c -pos +1);
        //cursor.move(p -pos);
        cursor.moveToPosition(p - 1);


        Category category = (Category) CategoryManager.createLightItemFromCursor(cursor);
        ((CategoryItemView) view).initView(category);
    }
}
