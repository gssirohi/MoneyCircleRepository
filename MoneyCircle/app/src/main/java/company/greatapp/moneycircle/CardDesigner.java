package company.greatapp.moneycircle;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import company.greatapp.moneycircle.manager.Accountant;
import company.greatapp.moneycircle.model.AccountRegister;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.view.CashFlowView;
import company.greatapp.moneycircle.view.CircleButton;
import company.greatapp.moneycircle.view.CircleItemView;
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


    private ViewGroup mCardIncome;
    private ViewGroup mCardExpense;
    private ViewGroup mCardBorrow;
    private ViewGroup mCardLent;
    private ViewGroup mCardSplit;
    private ViewGroup mCardDailyReport;
    private ViewGroup mCardUpcomingBorrow;
    private ViewGroup mCardUpcomingLent;
    private ViewGroup mCardBudget;
    private ViewGroup mCardTopSpend;
    private ViewGroup mCardTopBorrower;
    private ViewGroup mCardTopLender;
    private Accountant mAccountant;
    private AccountRegister incomeRegister;
    private AccountRegister expenseRegister;
    private AccountRegister borrowRegister;
    private AccountRegister lentRegister;
    private AccountRegister splitRegister;

    public CardDesigner(Context context, ViewGroup parent) {
        mContext = context;
        mParent = parent;
        mAccountant = new Accountant(context,true);
        incomeRegister = mAccountant.getRegister(Model.MODEL_TYPE_INCOME);
        expenseRegister = mAccountant.getRegister(Model.MODEL_TYPE_EXPENSE);
        borrowRegister = mAccountant.getRegister(Model.MODEL_TYPE_BORROW);
        lentRegister = mAccountant.getRegister(Model.MODEL_TYPE_LENT);
        splitRegister = mAccountant.getRegister(Model.MODEL_TYPE_SPLIT);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initAllCardViews(mAccountant);
    }

    public ViewGroup getCardView(int cardType) {

        switch(cardType) {
            case CARD_INCOME:
                if(mCardIncome == null) {
                    initCardView(mAccountant,CARD_INCOME);
                }
                return mCardIncome;
            case CARD_EXPENSE:
                if(mCardExpense == null) {
                    initCardView(mAccountant,CARD_EXPENSE);
                }
                return mCardExpense;
            case CARD_BORROW:
                if(mCardBorrow == null) {
                    initCardView(mAccountant,CARD_BORROW);
                }
                return mCardBorrow;
            case CARD_LENT:
                if(mCardLent == null) {
                    initCardView(mAccountant,CARD_LENT);
                }
                return mCardLent;
            case CARD_SPLIT:
                if(mCardSplit == null) {
                    initCardView(mAccountant,CARD_SPLIT);
                }
                return mCardSplit;
            case CARD_DAILY_REPORT:
                if(mCardDailyReport == null) {
                    initCardView(mAccountant,CARD_DAILY_REPORT);
                }
                return mCardDailyReport;
            case CARD_UPCOMING_BORROW:
                if(mCardUpcomingBorrow == null) {
                    initCardView(mAccountant,CARD_UPCOMING_BORROW);
                }
                return mCardUpcomingBorrow;
            case CARD_UPCOMING_LENT:
                if(mCardUpcomingLent == null) {
                    initCardView(mAccountant,CARD_UPCOMING_LENT);
                }
                return mCardUpcomingLent;
            case CARD_BUDGET:
                if(mCardBudget == null) {
                    initCardView(mAccountant,CARD_BUDGET);
                }
                return mCardBudget;
            case CARD_TOP_BORROWER:
                if(mCardTopBorrower == null) {
                    initCardView(mAccountant,CARD_TOP_BORROWER);
                }
                return mCardTopBorrower;
            case CARD_TOP_LENDERS:
                if(mCardTopLender == null) {
                    initCardView(mAccountant,CARD_TOP_LENDERS);
                }
                return mCardTopLender;
            case CARD_TOP_SPEND_AREAS:
                if(mCardTopSpend == null) {
                    initCardView(mAccountant,CARD_TOP_SPEND_AREAS);
                }
                return mCardTopSpend;
        }
        return null;
    }
    private void initAccountant(Accountant accountant) {
        mAccountant = accountant;
        incomeRegister = mAccountant.getRegister(Model.MODEL_TYPE_INCOME);
        expenseRegister = mAccountant.getRegister(Model.MODEL_TYPE_EXPENSE);
        borrowRegister = mAccountant.getRegister(Model.MODEL_TYPE_BORROW);
        lentRegister = mAccountant.getRegister(Model.MODEL_TYPE_LENT);
        splitRegister = mAccountant.getRegister(Model.MODEL_TYPE_SPLIT);
    }

    public void initAllCardViews(Accountant accountant) {
        if(accountant == null) return;
        initAccountant(accountant);
        initCardView(accountant,CARD_INCOME);
        initCardView(accountant,CARD_EXPENSE);
        initCardView(accountant,CARD_BORROW);
        initCardView(accountant,CARD_LENT);
        initCardView(accountant,CARD_SPLIT);
        initCardView(accountant,CARD_DAILY_REPORT);

        initCardView(accountant,CARD_UPCOMING_BORROW);
        initCardView(accountant,CARD_UPCOMING_LENT);
        initCardView(accountant,CARD_BUDGET);
        initCardView(accountant, CARD_TOP_BORROWER);
        initCardView(accountant, CARD_TOP_LENDERS);
        initCardView(accountant, CARD_TOP_SPEND_AREAS);

    }
    public void initCardView(Accountant accountant, int type) {
        if(accountant == null) return;
        Log.d("SPLIT","--------initializing CARD VIEW ["+type+"]-------->");
        ImageView iv_card_icon;
        TextView tv_title;
        LinearLayout ll_items;
        LinearLayout ll_last;

        TextView tv_previous_month_title;
        TextView tv_this_month_title;
        TextView tv_this_year_title;
        TextView tv_total_title;

        TextView tv_previous_month_value;
        TextView tv_this_month_value;
        TextView tv_this_year_value;
        TextView tv_total_value;

        View no_item;

        Intent i = null;
        switch (type){
            case CARD_INCOME:
                if(mCardIncome == null) {
                    mCardIncome = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardIncome == null) return ;
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


                tv_previous_month_value.setText("" + incomeRegister.getTotalOfLastMonth());
                tv_this_month_value.setText(""+incomeRegister.getTotalOfCurrentMonth());
                tv_this_year_value.setText(""+incomeRegister.getTotalOfCurrentYear());
                tv_total_value.setText(""+incomeRegister.getTotalTillNow());

                ll_last = (LinearLayout)mCardIncome.findViewById(R.id.ll_card_content_row_5);
                no_item = (View)mCardIncome.findViewById(R.id.no_item_view);
                if(incomeRegister.getLastTransaction() != null) {
                    ll_last.removeAllViews();
                    MoneyItemView v = new MoneyItemView(mContext,null,Model.MODEL_TYPE_INCOME);
                    v.initView(incomeRegister.getLastTransaction());
                    ll_last.addView(v);
                    ll_last.setVisibility(View.VISIBLE);
                    no_item.setVisibility(View.GONE);
                } else {
                    ll_last.setVisibility(View.GONE);
                    no_item.setVisibility(View.VISIBLE);
                }
                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_INCOME);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_income);
                mCardIncome.invalidate();
                break;
            case CARD_EXPENSE:
                if(mCardExpense == null) {
                    mCardExpense = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardExpense == null) return ;
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


                ll_last = (LinearLayout)mCardExpense.findViewById(R.id.ll_card_content_row_5);
                no_item = (View)mCardExpense.findViewById(R.id.no_item_view);
                if(expenseRegister.getLastTransaction() != null) {
                    ll_last.removeAllViews();
                    MoneyItemView v = new MoneyItemView(mContext,null,Model.MODEL_TYPE_EXPENSE);
                    v.initView(expenseRegister.getLastTransaction());
                    ll_last.addView(v);
                    ll_last.setVisibility(View.VISIBLE);
                    no_item.setVisibility(View.GONE);
                } else {
                    ll_last.setVisibility(View.GONE);
                    no_item.setVisibility(View.VISIBLE);
                }

                tv_title.setText("EXPENSE");
                tv_previous_month_title.setText("Previous month Expense");
                tv_this_month_title.setText("This month Expense");
                tv_this_year_title.setText("This year Expense");
                tv_total_title.setText("Total Expense");

                tv_previous_month_value.setText(""+expenseRegister.getTotalOfLastMonth());
                tv_this_month_value.setText(""+expenseRegister.getTotalOfCurrentMonth());
                tv_this_year_value.setText(""+expenseRegister.getTotalOfCurrentYear());
                tv_total_value.setText(""+expenseRegister.getTotalTillNow());

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_EXPENSE);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_expense);
                mCardExpense.invalidate();
                break;
            case CARD_BORROW:
                if(mCardBorrow == null) {
                    mCardBorrow = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardBorrow == null) return ;
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


                ll_last = (LinearLayout)mCardBorrow.findViewById(R.id.ll_card_content_row_5);
                no_item = (View)mCardBorrow.findViewById(R.id.no_item_view);
                if(borrowRegister.getLastTransaction() != null) {
                    ll_last.removeAllViews();
                    MoneyItemView v = new MoneyItemView(mContext,null,Model.MODEL_TYPE_BORROW);
                    v.initView(borrowRegister.getLastTransaction());
                    ll_last.addView(v);
                    ll_last.setVisibility(View.VISIBLE);
                    no_item.setVisibility(View.GONE);
                } else {
                    ll_last.setVisibility(View.GONE);
                    no_item.setVisibility(View.VISIBLE);
                }

                tv_title.setText("BORROW");
                tv_previous_month_title.setText("Previous month BORROW");
                tv_this_month_title.setText("This month BORROW");
                tv_this_year_title.setText("This year BORROW");
                tv_total_title.setText("Total BORROW");


                tv_previous_month_value.setText(""+borrowRegister.getTotalOfLastMonth());
                tv_this_month_value.setText(""+borrowRegister.getTotalOfCurrentMonth());
                tv_this_year_value.setText(""+borrowRegister.getTotalOfCurrentYear());
                tv_total_value.setText(""+borrowRegister.getTotalTillNow());

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_BORROW);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_borrow);
                break;
            case CARD_LENT:
                if(mCardLent == null) {
                    mCardLent = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardLent == null) return ;
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


                ll_last = (LinearLayout)mCardLent.findViewById(R.id.ll_card_content_row_5);
                no_item = (View)mCardLent.findViewById(R.id.no_item_view);
                if(lentRegister.getLastTransaction() != null) {
                    ll_last.removeAllViews();
                    MoneyItemView v = new MoneyItemView(mContext,null,Model.MODEL_TYPE_LENT);
                    v.initView(lentRegister.getLastTransaction());
                    ll_last.addView(v);
                    ll_last.setVisibility(View.VISIBLE);
                    no_item.setVisibility(View.GONE);
                } else {
                    ll_last.setVisibility(View.GONE);
                    no_item.setVisibility(View.VISIBLE);
                }

                tv_title.setText("LENT");
                tv_previous_month_title.setText("Previous month LENT");
                tv_this_month_title.setText("This month LENT");
                tv_this_year_title.setText("This year LENT");
                tv_total_title.setText("Total LENT");

                tv_previous_month_value.setText(""+lentRegister.getTotalOfLastMonth());
                tv_this_month_value.setText(""+lentRegister.getTotalOfCurrentMonth());
                tv_this_year_value.setText(""+lentRegister.getTotalOfCurrentYear());
                tv_total_value.setText(""+lentRegister.getTotalTillNow());

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_LENT);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_lent);
                break;
            case CARD_SPLIT:
                if(mCardSplit == null) {
                    mCardSplit = (ViewGroup) inflater.inflate(R.layout.card_money_item, mParent, false);
                }
                if(mCardSplit == null) return ;
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


                ll_last = (LinearLayout)mCardSplit.findViewById(R.id.ll_card_content_row_5);
                no_item = (View)mCardSplit.findViewById(R.id.no_item_view);
                if(splitRegister.getLastTransaction() != null) {
                    ll_last.removeAllViews();
                    MoneyItemView v = new MoneyItemView(mContext,null,Model.MODEL_TYPE_SPLIT);
                    v.initView(splitRegister.getLastTransaction());
                    ll_last.addView(v);
                    ll_last.setVisibility(View.VISIBLE);
                    no_item.setVisibility(View.GONE);
                } else {
                    ll_last.setVisibility(View.GONE);
                    no_item.setVisibility(View.VISIBLE);
                }

                tv_title.setText("SPLIT");
                tv_previous_month_title.setText("Previous month SPLIT");
                tv_this_month_title.setText("This month SPLIT");
                tv_this_year_title.setText("This year SPLIT");
                tv_total_title.setText("Total SPLIT");

                tv_previous_month_value.setText(""+splitRegister.getTotalOfLastMonth());
                tv_this_month_value.setText(""+splitRegister.getTotalOfCurrentMonth());
                tv_this_year_value.setText(""+splitRegister.getTotalOfCurrentYear());
                tv_total_value.setText(""+splitRegister.getTotalTillNow());

                i = new Intent(mContext,NewHomeActivity.class);
                i.putExtra(Model.MODEL_TYPE,Model.MODEL_TYPE_SPLIT);
                tv_title.setOnClickListener(getListener(i));
                iv_card_icon.setImageResource(R.drawable.ic_split);
                break;
            case CARD_DAILY_REPORT:
                if(mCardDailyReport == null) {
                    mCardDailyReport = (ViewGroup) inflater.inflate(R.layout.card_daily_report, mParent, false);
                }
                if(mCardDailyReport == null) return ;
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
                CashFlowView cash_flow = (CashFlowView)mCardDailyReport.findViewById(R.id.v_cash_flow);

                tv_title.setText("Today's Report");
                tv_i_title.setText("Today's Income");
                tv_e_title.setText("Today's Expense");
                tv_b_title.setText("Today's Borrow");
                tv_l_title.setText("Today's Lent");

                tv_i_value.setText(""+incomeRegister.getTotalOfCurrentDay());
                tv_e_value.setText(""+expenseRegister.getTotalOfCurrentDay());
                tv_b_value.setText(""+borrowRegister.getTotalOfCurrentDay());
                tv_l_value.setText(""+lentRegister.getTotalOfCurrentDay());
                float in = incomeRegister.getTotalOfCurrentDay() +  borrowRegister.getTotalOfCurrentDay();
                float out = expenseRegister.getTotalOfCurrentDay() + lentRegister.getTotalOfCurrentDay();
                float net = in - out;
                cash_flow.setCashNet(""+net);
                cash_flow.setCashIn(""+in);
                cash_flow.setCashOut(""+out);

                break;
            case CARD_UPCOMING_BORROW:
                if(mCardUpcomingBorrow == null) {
                    mCardUpcomingBorrow = (ViewGroup) inflater.inflate(R.layout.card_upcoming_events, mParent, false);
                }
                if(mCardUpcomingBorrow == null) return ;
                iv_card_icon = (ImageView)mCardUpcomingBorrow.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardUpcomingBorrow.findViewById(R.id.tv_card_title);
                ll_items = (LinearLayout)mCardUpcomingBorrow.findViewById(R.id.ll_card_events);
                no_item = (View)mCardUpcomingBorrow.findViewById(R.id.no_item_view);

                tv_title.setText("UPCOMING BILLS & BORROWS");
                ArrayList<Model> listB = borrowRegister.getUpComingEventsOfMonth();
                if(listB != null && listB.size()>0) {
                    addUpcomingData(ll_items, listB, Model.MODEL_TYPE_BORROW);
                    no_item.setVisibility(View.GONE);
                } else {
                    no_item.setVisibility(View.VISIBLE);
                    addUpcomingData(ll_items, listB, Model.MODEL_TYPE_BORROW);
                }

                break;

            case CARD_UPCOMING_LENT:
                if(mCardUpcomingLent == null) {
                    mCardUpcomingLent = (ViewGroup) inflater.inflate(R.layout.card_upcoming_events, mParent, false);
                }
                if(mCardUpcomingLent == null) return ;
                iv_card_icon = (ImageView)mCardUpcomingLent.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardUpcomingLent.findViewById(R.id.tv_card_title);
                ll_items = (LinearLayout)mCardUpcomingLent.findViewById(R.id.ll_card_events);
                no_item = (View)mCardUpcomingLent.findViewById(R.id.no_item_view);

                tv_title.setText("UPCOMINGS TO TAKE BACK");
                ArrayList<Model> listL = lentRegister.getUpComingEventsOfMonth();
                if(listL != null && listL.size()>0) {
                    addUpcomingData(ll_items, listL, Model.MODEL_TYPE_LENT);
                    no_item.setVisibility(View.GONE);
                } else {
                    no_item.setVisibility(View.VISIBLE);
                    addUpcomingData(ll_items, listL, Model.MODEL_TYPE_LENT);
                }
                break;
            case CARD_BUDGET:
                if(mCardBudget == null) {
                    mCardBudget = (ViewGroup) inflater.inflate(R.layout.card_budget, mParent, false);
                }
                if(mCardBudget == null) return ;
                iv_card_icon = (ImageView)mCardBudget.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardBudget.findViewById(R.id.tv_card_title);

                ProgressBar pb_budget = (ProgressBar)mCardBudget.findViewById(R.id.pb_card_budget);
                no_item = (View)mCardBudget.findViewById(R.id.no_item_view);
                ll_items = (LinearLayout)mCardBudget.findViewById(R.id.ll_card_content_frame);
                CircleItemView civ_total_budget = (CircleItemView)mCardBudget.findViewById(R.id.civ_total_budget);
                CircleItemView civ_available = (CircleItemView)mCardBudget.findViewById(R.id.civ_available);
                CircleItemView civ_total_spent = (CircleItemView)mCardBudget.findViewById(R.id.civ_total_spent);

                tv_title.setText("MONTHLY BUDGET");
                if(accountant.getBudget() > 0) {
                    civ_available.setItemName("AVAILABLE BUDGET TO SPEND");
                    civ_available.setItemValue("" + (accountant.getBudget() - expenseRegister.getTotalOfCurrentMonth()));

                    civ_total_spent.setItemName("THIS MONTH SPENT");
                    civ_total_spent.setItemValue("" + expenseRegister.getTotalOfCurrentMonth());

                    civ_total_budget.setItemName("THIS MONTH BUDGET");
                    civ_total_budget.setItemValue(""+accountant.getBudget());

                    pb_budget.setMax((int) accountant.getBudget());
                    pb_budget.setProgress((int) expenseRegister.getTotalOfCurrentMonth());
                    ll_items.setVisibility(View.VISIBLE);
                    no_item.setVisibility(View.GONE);
                } else {
                    ll_items.setVisibility(View.GONE);
                    no_item.setVisibility(View.VISIBLE);
                }
                break;
            case CARD_TOP_SPEND_AREAS:
                if(mCardTopSpend == null) {
                    mCardTopSpend = (ViewGroup) inflater.inflate(R.layout.card_top_items, mParent, false);
                }
                if(mCardTopSpend == null) return ;
                iv_card_icon = (ImageView)mCardTopSpend.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardTopSpend.findViewById(R.id.tv_card_title);
                ll_items = (LinearLayout)mCardTopSpend.findViewById(R.id.ll_card_top_items);
                no_item = (View)mCardTopSpend.findViewById(R.id.no_item_view);

                tv_title.setText("Top Spend Areas");
                ArrayList<Model> listSP = expenseRegister.getTopItems();
                if(listSP != null && listSP.size()>0) {
                    addTopItems(ll_items, listSP, CARD_TOP_SPEND_AREAS);
                    no_item.setVisibility(View.GONE);
                } else {
                    no_item.setVisibility(View.VISIBLE);
                    addTopItems(ll_items, listSP, CARD_TOP_SPEND_AREAS);
                }
                break;
            case CARD_TOP_BORROWER:
                if(mCardTopBorrower == null) {
                    mCardTopBorrower = (ViewGroup) inflater.inflate(R.layout.card_top_items, mParent, false);
                }
                if(mCardTopBorrower == null) return ;
                iv_card_icon = (ImageView)mCardTopBorrower.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardTopBorrower.findViewById(R.id.tv_card_title);
                ll_items = (LinearLayout)mCardTopBorrower.findViewById(R.id.ll_card_top_items);
                no_item = (View)mCardTopBorrower.findViewById(R.id.no_item_view);

                tv_title.setText("Top Borrowers");
                ArrayList<Model> listTB = lentRegister.getTopItems();
                if(listTB != null && listTB.size()>0) {
                    addTopItems(ll_items, listTB, CARD_TOP_BORROWER);
                    no_item.setVisibility(View.GONE);
                } else {
                    addTopItems(ll_items, listTB, CARD_TOP_BORROWER);
                    no_item.setVisibility(View.VISIBLE);
                }
                break;
            case CARD_TOP_LENDERS:
                if(mCardTopLender == null) {
                    mCardTopLender = (ViewGroup) inflater.inflate(R.layout.card_top_items, mParent, false);
                }
                if(mCardTopLender == null) return ;
                iv_card_icon = (ImageView)mCardTopLender.findViewById(R.id.iv_card_icon);
                tv_title = (TextView)mCardTopLender.findViewById(R.id.tv_card_title);
                ll_items = (LinearLayout)mCardTopLender.findViewById(R.id.ll_card_top_items);
                no_item = (View)mCardTopLender.findViewById(R.id.no_item_view);

                tv_title.setText("Top Lenders");
                ArrayList<Model> listTL = borrowRegister.getTopItems();
                if(listTL != null && listTL.size()>0) {
                    addTopItems(ll_items, listTL, CARD_TOP_LENDERS);
                    no_item.setVisibility(View.GONE);
                } else {
                    no_item.setVisibility(View.VISIBLE);
                    addTopItems(ll_items, listTL, CARD_TOP_LENDERS);
                }
                break;
        }
    }

    private void addTopItems(LinearLayout ll_items, ArrayList<Model> topList, int cardType) {

        if(topList == null)return;
        ll_items.removeAllViews();
        switch (cardType) {

            case CARD_TOP_BORROWER:// CONTACT who you lent most
                for(Model m : topList) {
                    if(m == null){
                        Log.d("SPLIT","adding TOP ITEM : BORROWER , FOUND NULL");
                        continue;
                    }
                    TopSegmentItemView v = new TopSegmentItemView(mContext,null);
                    float lendings = ((Contact)m).getLentAmountToThis();
                    v.setItemTitle(m.getTitle());
                    v.setItemValue("" + lendings);
                    ll_items.addView(v);
                }
                break;
            case CARD_TOP_LENDERS://CONTACT whom you borrowed most from
                for(Model m : topList) {
                    if(m == null){
                        Log.d("SPLIT","adding TOP ITEM : LENDERS , FOUND NULL");
                        continue;
                    }
                    TopSegmentItemView v = new TopSegmentItemView(mContext,null);
                    float borrowed = ((Contact)m).getBorrowedAmountfromThis();
                    v.setItemTitle(m.getTitle());
                    v.setItemValue(""+borrowed);
                    ll_items.addView(v);
                }

                break;

            case CARD_TOP_SPEND_AREAS:
                for(Model m : topList) {
                    if(m == null){
                        Log.d("SPLIT","adding TOP ITEM : SPEND AREAS , FOUND NULL");
                        continue;
                    }
                    TopSegmentItemView v = new TopSegmentItemView(mContext,null);
                    float spend = ((Category)m).getSpentAmountOnThis();
                    v.setItemTitle(m.getTitle());
                    v.setItemValue(""+spend);
                    ll_items.addView(v);
                }

                break;
            default:
                Log.d("SPLIT", "card not found");
        }

    }

    private void addUpcomingData(LinearLayout ll_events, ArrayList<Model> upcomings,int modelType) {

        if(upcomings == null) return;
        ll_events.removeAllViews();
        for(Model model : upcomings) {
            MoneyItemView v = new MoneyItemView(mContext,null,modelType);
            v.initView(model);
            ll_events.addView(v);
        }

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
