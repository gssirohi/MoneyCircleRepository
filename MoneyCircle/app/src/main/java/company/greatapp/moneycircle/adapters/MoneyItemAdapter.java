package company.greatapp.moneycircle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import company.greatapp.moneycircle.manager.BaseModelManager;
import company.greatapp.moneycircle.manager.BorrowManager;
import company.greatapp.moneycircle.manager.ExpenseManager;
import company.greatapp.moneycircle.manager.IncomeManager;
import company.greatapp.moneycircle.manager.LentManager;
import company.greatapp.moneycircle.manager.SplitManager;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Split;
import company.greatapp.moneycircle.view.MoneyItemView;
import company.greatapp.moneycircle.view.TagItemView;

/**
 * Created by Gyanendrasingh on 18-07-2015.
 */
public class MoneyItemAdapter extends CursorAdapter {


    public static String TAG = "MONEY_ITEM_CURSOR_ADAPTER";
    private final int mType;
    private Income income;
    private BaseModelManager manager;
    private Context mContext;

    public MoneyItemAdapter(Context context, Cursor c, boolean autoRequery, int type) {
        super(context, c, autoRequery);
        mType = type;
        Log.d(TAG, "constructor");
        switch(type) {
            case Model.MODEL_TYPE_INCOME:
                manager = new IncomeManager(context);
                break;
            case Model.MODEL_TYPE_EXPENSE:
                manager = new ExpenseManager(context);
                break;
            case Model.MODEL_TYPE_BORROW:
                manager = new BorrowManager(context);
                break;
            case Model.MODEL_TYPE_LENT:
                manager = new LentManager(context);
                break;
            case Model.MODEL_TYPE_SPLIT:
                manager = new SplitManager(context);
                break;
        }
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

        Model model = manager.createItemFromCursor(cursor);
        if(model == null) {
            Log.d(TAG,"income = null");
        }
        ((MoneyItemView)view).initView(model);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup arg2) {
        Log.d(TAG,"newView");

        View view = new MoneyItemView(context, null,mType);
        return view;
    }


}