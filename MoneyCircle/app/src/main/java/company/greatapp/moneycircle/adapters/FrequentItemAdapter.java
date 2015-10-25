package company.greatapp.moneycircle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import company.greatapp.moneycircle.manager.FrequentItemManager;
import company.greatapp.moneycircle.model.FrequentItem;
import company.greatapp.moneycircle.view.FrequentItemView;

/**
 * Created by Prateek on 25-10-2015.
 */
public class FrequentItemAdapter extends CursorAdapter {


    private final FrequentItemManager mFrequentItemManager;

    public FrequentItemAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mFrequentItemManager = new FrequentItemManager(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = new FrequentItemView(context, null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int c = cursor.getCount();

        int pos = cursor.getPosition();
        pos = pos+1;
        int p = (c -pos +1);
        cursor.moveToPosition(p - 1);

        FrequentItem frequentItem = (FrequentItem)mFrequentItemManager.createHeavyItemFromCursor(cursor);
        ((FrequentItemView)view).initView(frequentItem);

    }
}
