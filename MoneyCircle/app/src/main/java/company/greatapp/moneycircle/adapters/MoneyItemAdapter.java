package company.greatapp.moneycircle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import company.greatapp.moneycircle.manager.IncomeManager;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.view.IncomeItemView;

/**
 * Created by Gyanendrasingh on 18-07-2015.
 */
public class MoneyItemAdapter extends CursorAdapter {


    public static String TAG = "MONEY_ITEM_CURSOR_ADAPTER";
    private final IncomeManager im;
    private Income income;
    private Context mContext;

    public MoneyItemAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        Log.d(TAG, "constructor");
        im = new IncomeManager(context);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d("Split","bindView");
        int c = cursor.getCount();
        int pos = cursor.getPosition();
        pos = pos+1;
        int p = (c -pos +1);
        //cursor.move(p -pos);
        cursor.moveToPosition(p-1);

        income = (Income)im.createItemFromCursor(cursor);
        if(income == null) {
            Log.d(TAG,"income = null");
        }
        ((IncomeItemView)view).initView(income);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup arg2) {
        Log.d(TAG,"newView");

        View view = new IncomeItemView(context, null);
        return view;
    }


}