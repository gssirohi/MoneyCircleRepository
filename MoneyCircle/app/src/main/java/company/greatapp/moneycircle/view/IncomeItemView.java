package company.greatapp.moneycircle.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Income;

/**
 * Created by Gyanendrasingh on 18-07-2015.
 */

public class IncomeItemView extends LinearLayout {


    private final ViewGroup viewGroup;
    private final TextView tv_title;
    private final TextView tv_amount;
    private final TextView tv_date;
private Income income;
    public IncomeItemView(Context context, AttributeSet attrs ) {

        super(context, attrs);
        Log.d("Spilt", "called super");
        setOrientation(LinearLayout.VERTICAL);
        // setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.income_expense_item_layout, this, true);
        tv_title = (TextView)viewGroup.findViewById(R.id.tv_item_title);
        tv_amount = (TextView)viewGroup.findViewById(R.id.tv_item_amount);
        tv_date = (TextView)viewGroup.findViewById(R.id.tv_item_date);

    }

    public void initView(Income income) {
        if(income == null) return;
        tv_title.setText(income.getTitle());
        tv_amount.setText(""+income.getAmount());
        tv_date.setText(income.getDateString());
    }

}
