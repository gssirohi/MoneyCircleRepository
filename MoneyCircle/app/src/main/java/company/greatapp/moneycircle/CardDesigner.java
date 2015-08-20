package company.greatapp.moneycircle;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import company.greatapp.moneycircle.manager.BorrowManager;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.manager.LentManager;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.view.MoneyItemView;
import company.greatapp.moneycircle.view.TopSegmentItemView;

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

    public static final int CARD_UPCOMING_BORROW = 7;
    public static final int CARD_UPCOMING_LENT = 8;
    public static final int CARD_BUDGET = 9;
    public static final int CARD_TOP_SPEND_AREAS = 10;
    public static final int CARD_TOP_BORROWER = 11;
    public static final int CARD_TOP_LENDERS = 12;

    public CardDesigner(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void initCardView(Accountant accountant) {

    }
    public ViewGroup initCardView(Accountant accountant, int type) {

        ImageView iv_card_icon;
        TextView tv_title;
        LinearLayout ll_items;


        TextView tv_previous_month_title;
        TextView tv_this_month_title;
        TextView tv_this_year_title;
        TextView tv_total_title;

        TextView tv_previous_month_value;
        TextView tv_this_month_value;
        TextView tv_this_year_value;
        TextView tv_total_value;

        Intent i = null;
        switch (type){
            case CARD_INCOME:
                if(mCardIncome == null) {
                    mCardIncome = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardIncome == null) return null;
                iv_card_icon = (ImageView)mCardIncome.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardIncome.findViewById(R.id.tv_card_title);

                tv_previous_month_title = (TextView)mCardIncome.findViewById(R.id.tv_card_content_row_1_title);
                tv_this_month_title = (TextView)mCardIncome.findViewById(R.id.tv_card_content_row_2_title);
                tv_this_year_title = (TextView)mCardIncome.findViewById(R.id.tv_card_content_row_3_title);
                tv_total_title = (TextView)mCardIncome.findViewById(R.id.tv_card_content_row_4_title);

                tv_previous_month_value = (TextView)mCardIncome.findViewById(R.id.tv_card_content_row_1_value);
                tv_this_month_value = (TextView)mCardIncome.findViewById(R.id.tv_card_content_row_2_value);
                tv_this_year_value = (TextView)mCardIncome.findViewById(R.id.tv_card_content_row_3_value);
                tv_total_value = (TextView)mCardIncome.findViewById(R.id.tv_card_content_row_4_value);

                tv_title.setText("INCOME");
                tv_previous_month_title.setText("Previous month Income");
                tv_this_month_title.setText("This month Income");
                tv_this_year_title.setText("This year Income");
                tv_total_title.setText("Total Income");

                tv_previous_month_value.setText(""+accountant.getPreviousMonthData(Model.MODEL_TYPE_INCOME));
                tv_this_month_value.setText(""+accountant.getThisMonthData(Model.MODEL_TYPE_INCOME));
                tv_this_year_value.setText(""+accountant.getThisYearData(Model.MODEL_TYPE_INCOME));
                tv_total_value.setText(""+accountant.getTotalData(Model.MODEL_TYPE_INCOME));

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_INCOME);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_income);
                break;
            case CARD_EXPENSE:
                if(mCardExpense == null) {
                    mCardExpense = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardExpense == null) return null;
                iv_card_icon = (ImageView)mCardExpense.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardExpense.findViewById(R.id.tv_card_title);

                tv_previous_month_title = (TextView)mCardExpense.findViewById(R.id.tv_card_content_row_1_title);
                tv_this_month_title = (TextView)mCardExpense.findViewById(R.id.tv_card_content_row_2_title);
                tv_this_year_title = (TextView)mCardExpense.findViewById(R.id.tv_card_content_row_3_title);
                tv_total_title = (TextView)mCardExpense.findViewById(R.id.tv_card_content_row_4_title);

                tv_previous_month_value = (TextView)mCardExpense.findViewById(R.id.tv_card_content_row_1_value);
                tv_this_month_value = (TextView)mCardExpense.findViewById(R.id.tv_card_content_row_2_value);
                tv_this_year_value = (TextView)mCardExpense.findViewById(R.id.tv_card_content_row_3_value);
                tv_total_value = (TextView)mCardExpense.findViewById(R.id.tv_card_content_row_4_value);

                tv_title.setText("EXPENSE");
                tv_previous_month_title.setText("Previous month Expense");
                tv_this_month_title.setText("This month Expense");
                tv_this_year_title.setText("This year Expense");
                tv_total_title.setText("Total Expense");

                tv_previous_month_value.setText(""+accountant.getPreviousMonthData(Model.MODEL_TYPE_EXPENSE));
                tv_this_month_value.setText(""+accountant.getThisMonthData(Model.MODEL_TYPE_EXPENSE));
                tv_this_year_value.setText(""+accountant.getThisYearData(Model.MODEL_TYPE_EXPENSE));
                tv_total_value.setText(""+accountant.getTotalData(Model.MODEL_TYPE_EXPENSE));

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_EXPENSE);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_expense);
                break;
            case CARD_BORROW:
                if(mCardBorrow == null) {
                    mCardBorrow = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardBorrow == null) return null;
                iv_card_icon = (ImageView)mCardBorrow.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardBorrow.findViewById(R.id.tv_card_title);

                tv_previous_month_title = (TextView)mCardBorrow.findViewById(R.id.tv_card_content_row_1_title);
                tv_this_month_title = (TextView)mCardBorrow.findViewById(R.id.tv_card_content_row_2_title);
                tv_this_year_title = (TextView)mCardBorrow.findViewById(R.id.tv_card_content_row_3_title);
                tv_total_title = (TextView)mCardBorrow.findViewById(R.id.tv_card_content_row_4_title);

                tv_previous_month_value = (TextView)mCardBorrow.findViewById(R.id.tv_card_content_row_1_value);
                tv_this_month_value = (TextView)mCardBorrow.findViewById(R.id.tv_card_content_row_2_value);
                tv_this_year_value = (TextView)mCardBorrow.findViewById(R.id.tv_card_content_row_3_value);
                tv_total_value = (TextView)mCardBorrow.findViewById(R.id.tv_card_content_row_4_value);

                tv_title.setText("BORROW");
                tv_previous_month_title.setText("Previous month BORROW");
                tv_this_month_title.setText("This month BORROW");
                tv_this_year_title.setText("This year BORROW");
                tv_total_title.setText("Total BORROW");

                tv_previous_month_value.setText(""+accountant.getPreviousMonthData(Model.MODEL_TYPE_BORROW));
                tv_this_month_value.setText(""+accountant.getThisMonthData(Model.MODEL_TYPE_BORROW));
                tv_this_year_value.setText(""+accountant.getThisYearData(Model.MODEL_TYPE_BORROW));
                tv_total_value.setText(""+accountant.getTotalData(Model.MODEL_TYPE_BORROW));

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_BORROW);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_borrow);
                break;
            case CARD_LENT:
                if(mCardLent == null) {
                    mCardLent = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardLent == null) return null;
                iv_card_icon = (ImageView)mCardLent.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardLent.findViewById(R.id.tv_card_title);

                tv_previous_month_title = (TextView)mCardLent.findViewById(R.id.tv_card_content_row_1_title);
                tv_this_month_title = (TextView)mCardLent.findViewById(R.id.tv_card_content_row_2_title);
                tv_this_year_title = (TextView)mCardLent.findViewById(R.id.tv_card_content_row_3_title);
                tv_total_title = (TextView)mCardLent.findViewById(R.id.tv_card_content_row_4_title);

                tv_previous_month_value = (TextView)mCardLent.findViewById(R.id.tv_card_content_row_1_value);
                tv_this_month_value = (TextView)mCardLent.findViewById(R.id.tv_card_content_row_2_value);
                tv_this_year_value = (TextView)mCardLent.findViewById(R.id.tv_card_content_row_3_value);
                tv_total_value = (TextView)mCardLent.findViewById(R.id.tv_card_content_row_4_value);

                tv_title.setText("LENT");
                tv_previous_month_title.setText("Previous month LENT");
                tv_this_month_title.setText("This month LENT");
                tv_this_year_title.setText("This year LENT");
                tv_total_title.setText("Total LENT");

                tv_previous_month_value.setText(""+accountant.getPreviousMonthData(Model.MODEL_TYPE_LENT));
                tv_this_month_value.setText(""+accountant.getThisMonthData(Model.MODEL_TYPE_LENT));
                tv_this_year_value.setText(""+accountant.getThisYearData(Model.MODEL_TYPE_LENT));
                tv_total_value.setText(""+accountant.getTotalData(Model.MODEL_TYPE_LENT));

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_LENT);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_lent);
                break;
            case CARD_SPLIT:
                if(mCardSplit == null) {
                    mCardSplit = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardSplit == null) return null;
                iv_card_icon = (ImageView)mCardSplit.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardSplit.findViewById(R.id.tv_card_title);

                tv_previous_month_title = (TextView)mCardSplit.findViewById(R.id.tv_card_content_row_1_title);
                tv_this_month_title = (TextView)mCardSplit.findViewById(R.id.tv_card_content_row_2_title);
                tv_this_year_title = (TextView)mCardSplit.findViewById(R.id.tv_card_content_row_3_title);
                tv_total_title = (TextView)mCardSplit.findViewById(R.id.tv_card_content_row_4_title);

                tv_previous_month_value = (TextView)mCardSplit.findViewById(R.id.tv_card_content_row_1_value);
                tv_this_month_value = (TextView)mCardSplit.findViewById(R.id.tv_card_content_row_2_value);
                tv_this_year_value = (TextView)mCardSplit.findViewById(R.id.tv_card_content_row_3_value);
                tv_total_value = (TextView)mCardSplit.findViewById(R.id.tv_card_content_row_4_value);

                tv_title.setText("SPLIT");
                tv_previous_month_title.setText("Previous month SPLIT");
                tv_this_month_title.setText("This month SPLIT");
                tv_this_year_title.setText("This year SPLIT");
                tv_total_title.setText("Total SPLIT");

                tv_previous_month_value.setText(""+accountant.getPreviousMonthData(Model.MODEL_TYPE_SPLIT));
                tv_this_month_value.setText(""+accountant.getThisMonthData(Model.MODEL_TYPE_SPLIT));
                tv_this_year_value.setText(""+accountant.getThisYearData(Model.MODEL_TYPE_SPLIT));
                tv_total_value.setText(""+accountant.getTotalData(Model.MODEL_TYPE_SPLIT));

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_SPLIT);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_split);
                break;
            case CARD_DAILY_REPORT:
                if(mCardDailyReport == null) {
                    mCardDailyReport = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardDailyReport == null) return null;
                iv_card_icon = (ImageView)mCardDailyReport.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardDailyReport.findViewById(R.id.tv_card_title);

                TextView tv_i_title = (TextView)mCardDailyReport.findViewById(R.id.tv_card_content_row_1_title);
                TextView tv_e_title = (TextView)mCardDailyReport.findViewById(R.id.tv_card_content_row_2_title);
                TextView tv_b_title = (TextView)mCardDailyReport.findViewById(R.id.tv_card_content_row_3_title);
                TextView tv_l_title = (TextView)mCardDailyReport.findViewById(R.id.tv_card_content_row_4_title);

                TextView tv_i_value = (TextView)mCardDailyReport.findViewById(R.id.tv_card_content_row_1_value);
                TextView tv_e_value = (TextView)mCardDailyReport.findViewById(R.id.tv_card_content_row_2_value);
                TextView tv_b_value = (TextView)mCardDailyReport.findViewById(R.id.tv_card_content_row_3_value);
                TextView tv_l_value = (TextView)mCardDailyReport.findViewById(R.id.tv_card_content_row_4_value);

                tv_title.setText("Today's Report");
                tv_i_title.setText("Today's Income");
                tv_e_title.setText("Today's Expense");
                tv_b_title.setText("Today's Borrow");
                tv_l_title.setText("Today's Lent");

                tv_i_value.setText(""+accountant.getTodayData(Model.MODEL_TYPE_INCOME));
                tv_e_value.setText(""+accountant.getTodayData(Model.MODEL_TYPE_EXPENSE));
                tv_b_value.setText(""+accountant.getTodayData(Model.MODEL_TYPE_BORROW));
                tv_l_value.setText(""+accountant.getTodayData(Model.MODEL_TYPE_LENT));

                break;
            case CARD_UPCOMING_BORROW:
                card = (ViewGroup)inflater.inflate(R.layout.card_upcoming_events, mParent, false);
                if(card == null) return null;
                iv_card_icon = (ImageView)card.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)card.findViewById(R.id.tv_card_title);
                ll_items = (LinearLayout)card.findViewById(R.id.ll_card_events);

                tv_title.setText("UPCOMING BILLS & BORROWS");
                addUpcomingBorrows(ll_items);

                break;
            case CARD_UPCOMING_LENT:
                card = (ViewGroup)inflater.inflate(R.layout.card_upcoming_events, mParent, false);
                if(card == null) return null;
                iv_card_icon = (ImageView)card.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)card.findViewById(R.id.tv_card_title);
                ll_items = (LinearLayout)card.findViewById(R.id.ll_card_events);

                tv_title.setText("UPCOMING LENTS RETURN");
                addUpcomingLents(ll_items);
                break;
            case CARD_BUDGET:
                card = (ViewGroup)inflater.inflate(R.layout.card_budget, mParent, false);
                if(card == null) return null;
                iv_card_icon = (ImageView)card.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)card.findViewById(R.id.tv_card_title);
                TextView tv_available_value = (TextView)card.findViewById(R.id.tv_card_budget_available_value);
                TextView tv_expense_value = (TextView)card.findViewById(R.id.tv_card_budget_expense_value);
                TextView tv_total_budget_value = (TextView)card.findViewById(R.id.tv_card_budget_total_value);
                ProgressBar pb_budget = (ProgressBar)card.findViewById(R.id.pb_card_budget);

                tv_title.setText("MONTHLY BUDGET");

                tv_available_value.setText("11000");
                tv_expense_value.setText("9000");
                tv_total_budget_value.setText("20000");
                pb_budget.setMax(20000);
                pb_budget.setProgress(9000);
                break;
            case CARD_TOP_SPEND_AREAS:
                card = (ViewGroup)inflater.inflate(R.layout.card_top_items, mParent, false);
                if(card == null) return null;
                iv_card_icon = (ImageView)card.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)card.findViewById(R.id.tv_card_title);
                ll_items = (LinearLayout)card.findViewById(R.id.ll_card_top_items);

                tv_title.setText("Top Spend Areas");
                addTopItems(ll_items, Model.MODEL_TYPE_CATEGORY);
                break;
            case CARD_TOP_BORROWER:
                break;
            case CARD_TOP_LENDERS:
                break;
        }
        return card;
    }

    private void addTopItems(LinearLayout ll_items, int modelType) {

        switch (modelType) {

            case Model.MODEL_TYPE_BORROW:

                break;
            case Model.MODEL_TYPE_LENT:

                break;

            case Model.MODEL_TYPE_CATEGORY:
                CategoryManager cm = new CategoryManager(mContext,Model.MODEL_TYPE_EXPENSE);
                boolean once = true;
                for(Model m : cm.getItemList()) {
                    TopSegmentItemView v = new TopSegmentItemView(mContext,null);
                    v.setItemTitle(m.getTitle());
                    v.setItemValue("3000");
                    ll_items.addView(v);
                }
                break;
            default:
                Log.d("SPLIT", "modelType not found");
        }

    }

    private void addUpcomingBorrows(LinearLayout ll_events) {
        //TODO: add only upcoming in week or month
        //just for example I am adding 3 views from borrow

        BorrowManager bm = new BorrowManager(mContext);
        MoneyItemView v1 = new MoneyItemView(mContext,null,Model.MODEL_TYPE_BORROW);
        MoneyItemView v2 = new MoneyItemView(mContext,null,Model.MODEL_TYPE_BORROW);
        MoneyItemView v3 = new MoneyItemView(mContext,null,Model.MODEL_TYPE_BORROW);
        if(bm.getItemList().size() > 2) {
            v1.initView(bm.getItemList().get(0));
            v2.initView(bm.getItemList().get(1));
            v3.initView(bm.getItemList().get(2));
        }
        ll_events.addView(v1);
        ll_events.addView(v2);
        ll_events.addView(v3);

    }

    private void addUpcomingLents(LinearLayout ll_events) {
        //TODO: add only upcoming in week or month
        //just for example I am adding 3 views from borrow

        LentManager bm = new LentManager(mContext);
        MoneyItemView v1 = new MoneyItemView(mContext,null,Model.MODEL_TYPE_LENT);
        MoneyItemView v2 = new MoneyItemView(mContext,null,Model.MODEL_TYPE_LENT);
        MoneyItemView v3 = new MoneyItemView(mContext,null,Model.MODEL_TYPE_LENT);
        if(bm.getItemList().size() > 2) {
            v1.initView(bm.getItemList().get(0));
            v2.initView(bm.getItemList().get(1));
            v3.initView(bm.getItemList().get(2));
        }
        ll_events.addView(v1);
        ll_events.addView(v2);
        ll_events.addView(v3);

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
