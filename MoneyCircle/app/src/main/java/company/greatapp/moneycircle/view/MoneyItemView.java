package company.greatapp.moneycircle.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Split;

/**
 * Created by Gyanendrasingh on 18-07-2015.
 */

public class MoneyItemView extends LinearLayout {


    private final ViewGroup viewGroup;
    private final TextView tv_title;
    private final TextView tv_amount;
    private final TextView tv_date;
    private final int mType;
    private final FrameLayout f_member;
    private Income income;
    public MoneyItemView(Context context, AttributeSet attrs, int type) {

        super(context, attrs);
        mType = type;
        Log.d("Spilt", "called super");
        setOrientation(LinearLayout.VERTICAL);
        // setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.income_expense_item_layout, this, true);
        tv_title = (TextView)viewGroup.findViewById(R.id.tv_item_title);
        tv_amount = (TextView)viewGroup.findViewById(R.id.tv_item_amount);
        tv_date = (TextView)viewGroup.findViewById(R.id.tv_item_date);
        f_member = (FrameLayout)viewGroup.findViewById(R.id.f_member);

    }

    public void initView(Model model) {
        if(model == null) return;
        tv_title.setText(model.getTitle());

        switch(mType) {
            case Model.MODEL_TYPE_INCOME:

                tv_amount.setText(""+((Income)model).getAmount());
                tv_date.setText(((Income)model).getDateString());
                break;
            case Model.MODEL_TYPE_EXPENSE:
                tv_amount.setText(""+((Expense)model).getAmount());
                tv_date.setText(((Expense)model).getDateString());
                break;
            case Model.MODEL_TYPE_BORROW:
                tv_amount.setText(""+((Borrow)model).getAmount());
                tv_date.setText(((Borrow) model).getDateString());
                Contact memberB = ((Borrow)model).getLinkedContact();
                if(memberB != null)
                f_member.addView(new TagItemView(getContext(),f_member,memberB,false));
                break;
            case Model.MODEL_TYPE_LENT:
                tv_amount.setText(""+((Lent)model).getAmount());
                tv_date.setText(((Lent)model).getDateString());
                Contact memberL = ((Lent)model).getLinkedContact();
                if(memberL != null)
                f_member.addView(new TagItemView(getContext(),f_member,memberL,false));
                break;
            case Model.MODEL_TYPE_SPLIT:
                tv_amount.setText(""+((Split)model).getAmount());
                tv_date.setText(((Split)model).getDateString());
                break;
        }

    }

}
