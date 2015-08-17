package company.greatapp.moneycircle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Gyanendrasingh on 8/15/2015.
 */
public class CardDesigner {

    private final Context mContext;
    private final LayoutInflater inflater;
    private final ViewGroup mParent;
    public static final int CARD_INCOME = 1;
    public static final int CARD_EXPENSE = 2;
    public static final int CARD_BORROW = 3;
    public static final int CARD_LENT = 4;
    public static final int CARD_SPLIT = 5;

    public CardDesigner(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public ViewGroup getCardView(int type) {
        ViewGroup card = (ViewGroup)inflater.inflate(R.layout.card_view_row1, mParent, false);
        ImageView iv = (ImageView)card.findViewById(R.id.ivTitleIconId);
        TextView tv = (TextView)card.findViewById(R.id.tvTitleId);
        Intent i = null;
        switch (type){
            case CARD_INCOME:
                tv.setText("INCOME");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_INCOME);
                tv.setOnClickListener(getListener(i));
                iv.setImageResource(R.drawable.ic_income);
                break;
            case CARD_EXPENSE:
                tv.setText("EXPENSE");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_EXPENSE);
                tv.setOnClickListener(getListener(i));
                iv.setImageResource(R.drawable.ic_expense);
                break;
            case CARD_BORROW:
                tv.setText("BORROW");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_BORROW);
                tv.setOnClickListener(getListener(i));
                iv.setImageResource(R.drawable.ic_borrow);
                break;
            case CARD_LENT:
                tv.setText("LENT");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_LENT);
                tv.setOnClickListener(getListener(i));
                iv.setImageResource(R.drawable.ic_lent);
                break;
            case CARD_SPLIT:
                tv.setText("SPLIT");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_SPLIT);
                tv.setOnClickListener(getListener(i));
                iv.setImageResource(R.drawable.ic_split);
                break;
        }
        return card;
    }
    android.view.View.OnClickListener getListener(final Intent i){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(i);
            }
        };
    }

}
