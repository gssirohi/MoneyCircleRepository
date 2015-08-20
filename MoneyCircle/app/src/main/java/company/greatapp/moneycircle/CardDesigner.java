package company.greatapp.moneycircle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public static final int CARD_DAILY_REPORT = 6;

    public CardDesigner(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public ViewGroup getCardView(int type) {
        ViewGroup card = null;
        if(type == CARD_DAILY_REPORT) {
            card = (ViewGroup)inflater.inflate(R.layout.card_daily_report, mParent, false);
        } else {
            card = (ViewGroup)inflater.inflate(R.layout.card_money_item, mParent, false);
        }

        if(card == null) return null;

        ImageView iv_card_icon = (ImageView)card.findViewById(R.id.iv_card_icon);
        TextView tv_title = (TextView)card.findViewById(R.id.tv_card_title);

        Intent i = null;
        switch (type){
            case CARD_INCOME:
                tv_title.setText("INCOME");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_INCOME);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_income);
                break;
            case CARD_EXPENSE:
                tv_title.setText("EXPENSE");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_EXPENSE);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_expense);
                break;
            case CARD_BORROW:
                tv_title.setText("BORROW");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_BORROW);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_borrow);
                break;
            case CARD_LENT:
                tv_title.setText("LENT");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_LENT);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_lent);
                break;
            case CARD_SPLIT:
                tv_title.setText("SPLIT");
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_SPLIT);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_split);
                break;
            case CARD_DAILY_REPORT:
                tv_title.setText("TODAY'S REPORT");
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
