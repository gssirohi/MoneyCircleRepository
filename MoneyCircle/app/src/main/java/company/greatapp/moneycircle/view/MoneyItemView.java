package company.greatapp.moneycircle.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.tools.Tools;

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
    private final FrameLayout f_split;
    private final LinearLayout ll_split_member;
    private final LinearLayout ll_due_date_info;
    private final TextView tv_due_date;
    private Income income;
    public MoneyItemView(Context context, AttributeSet attrs, int type) {

        super(context, attrs);
        mType = type;
        Log.d("Spilt", "called super");
        setOrientation(LinearLayout.VERTICAL);
        // setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.money_item_layout, this, true);
        tv_title = (TextView)viewGroup.findViewById(R.id.tv_item_title);
        tv_amount = (TextView)viewGroup.findViewById(R.id.tv_item_amount);
        tv_date = (TextView)viewGroup.findViewById(R.id.tv_item_date);
        tv_due_date = (TextView)viewGroup.findViewById(R.id.tv_due_date);
        f_member = (FrameLayout)viewGroup.findViewById(R.id.f_member);
        f_split = (FrameLayout)viewGroup.findViewById(R.id.f_split);
        ll_split_member = (LinearLayout)viewGroup.findViewById(R.id.ll_split_member);
        ll_due_date_info = (LinearLayout)viewGroup.findViewById(R.id.ll_due_date_info);

    }

    public void initView(Model model) {
        if(model == null) return;
        f_member.removeAllViews();
        f_split.removeAllViews();
        ll_split_member.removeAllViews();

        tv_title.setText(model.getTitle());

        switch(mType) {
            case Model.MODEL_TYPE_INCOME:

                tv_amount.setText(Tools.floatString(((Income)model).getAmount()));
                tv_date.setText(((Income)model).getDateString());
                ll_due_date_info.setVisibility(View.GONE);
                break;
            case Model.MODEL_TYPE_EXPENSE:
                tv_amount.setText(Tools.floatString(((Expense)model).getAmount()));
                tv_date.setText(((Expense)model).getDateString());
                if(((Expense)model).isLinkedWithSplit()){
                    f_split.addView(new TagItemView(getContext(), f_split, "SPLIT", false));
                }
                ll_due_date_info.setVisibility(View.GONE);
                break;
            case Model.MODEL_TYPE_BORROW:
                tv_amount.setText(Tools.floatString(((Borrow)model).getAmount()));
                tv_date.setText(((Borrow) model).getDateString());
                Contact memberB = ((Borrow)model).getLinkedContact();
                if(memberB != null) {
                    f_member.addView(new TagItemView(getContext(), f_member, memberB, false));
                } else {
                    String json = ((Borrow)model).getLinkedContactJson();
                    memberB = GreatJSON.getContactFromJsonString(json,getContext());
                    if(memberB != null) {
                        f_member.addView(new TagItemView(getContext(), f_member, memberB, false));
                    } else {
                        Log.d("SPLIT","MoneyItemView Contact from json["+json+"] is NULL");
                    }
                }
                ll_due_date_info.setVisibility(View.VISIBLE);
                tv_due_date.setText(""+((Borrow)model).getDueDateString());
                break;
            case Model.MODEL_TYPE_LENT:
                tv_amount.setText(Tools.floatString(((Lent)model).getAmount()));
                tv_date.setText(((Lent)model).getDateString());
                Contact memberL = ((Lent)model).getLinkedContact();
                if(memberL != null) {
                    f_member.addView(new TagItemView(getContext(), f_member, memberL, false));
                } else {
                    String json = ((Lent)model).getLinkedContactJson();
                    memberL = GreatJSON.getContactFromJsonString(json,getContext());
                    if(memberL != null) {
                        f_member.addView(new TagItemView(getContext(), f_member, memberL, false));
                    } else {
                        Log.d("SPLIT","MoneyItemView Contact from json["+json+"] is NULL");
                    }
                }
                if(((Lent)model).isLinkedWithSplit()){
                    f_split.addView(new TagItemView(getContext(), f_split, "SPLIT", false));
                }
                ll_due_date_info.setVisibility(View.VISIBLE);
                tv_due_date.setText(""+((Lent)model).getDueDateString());
                break;
            case Model.MODEL_TYPE_SPLIT:
                tv_amount.setText(Tools.floatString(((Split)model).getAmount()));
                tv_date.setText(((Split)model).getDateString());
//                TextView tv = new TextView(getContext());
//                f_member.addView(tv);
//                tv.setText(((Split)model).printModelData());
                f_member.setVisibility(View.GONE);
                f_split.setVisibility(View.GONE);
                ll_split_member.setVisibility(View.VISIBLE);
                addMemberTagViews((Split) model);
                ll_due_date_info.setVisibility(View.VISIBLE);
                tv_due_date.setText("" + ((Split) model).getDueDateString());
                break;
        }

    }

    private void addMemberTagViews(Split split) {
        if(split == null)return;

        for(Contact c : split.getLinkedParticipants()) {
            if(c != null){
                ll_split_member.addView(new TagItemView(getContext(), ll_split_member, c, false));
            }
        }
    }

}
