package company.greatapp.moneycircle.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import company.greatapp.moneycircle.AddNewEntryActivity;
import company.greatapp.moneycircle.ManageCircleActivity;
import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.split.SplitToolActivity;
import company.greatapp.moneycircle.view.CircleButton;

/**
 * Created by gyanendra.sirohi on 8/25/2015.
 */
public class AddNewEntryDialog extends Dialog {
    private final Context mContext;
    private Contact mContact;
    private CircleButton b_split;
    private CircleButton b_income;
    private CircleButton b_expense;
    private CircleButton b_lent;
    private CircleButton b_borrow;
    private CircleButton b_daily_shop;
    private CircleButton b_circle;
    private CircleButton b_category;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private LinearLayout ll_3;
    private LinearLayout ll_4;

    public AddNewEntryDialog(Context context) {
        super(context);
        mContext = context;
        mContact = null;
        init(mContact);
    }

    public AddNewEntryDialog(Context context,Contact contact) {
        super(context);
        mContext = context;
        mContact = contact;
        init(contact);
    }


    private void init(Contact contact) {
        //TODO: show add option screen
        //startActivity(new Intent(this,SplitToolActivity.class));
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.new_entry_options_dialog_layout, null, false);

        ll_1 = (LinearLayout)viewGroup.findViewById(R.id.ll_new_entry_options_1);
        ll_2 = (LinearLayout)viewGroup.findViewById(R.id.ll_new_entry_options_2);
        ll_3 = (LinearLayout)viewGroup.findViewById(R.id.ll_new_entry_options_3);
        ll_4 = (LinearLayout)viewGroup.findViewById(R.id.ll_new_entry_options_4);
        if(contact != null) {
            ll_2.setVisibility(View.GONE);
            ll_4.setVisibility(View.GONE);
        }
        if(C.NEW_ENTRY_DIALOG_TRANSPARENT) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        setContentView(viewGroup);
        setTitle("Add New Entry");
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(C.NEW_ENTRY_DIALOG_TRANSPARENT) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }


        b_split = (CircleButton)viewGroup.findViewById(R.id.b_split);
        b_income = (CircleButton)viewGroup.findViewById(R.id.b_income);
        b_expense = (CircleButton)viewGroup.findViewById(R.id.b_expense);
        b_borrow = (CircleButton)viewGroup.findViewById(R.id.b_borrow);
        b_lent = (CircleButton)viewGroup.findViewById(R.id.b_lent);
        b_daily_shop = (CircleButton)viewGroup.findViewById(R.id.b_daily_shop);

        b_circle = (CircleButton)viewGroup.findViewById(R.id.b_new_circle);
        b_category = (CircleButton)viewGroup.findViewById(R.id.b_new_category);

        b_daily_shop.setColor(mContext.getResources().getColor(R.color.app_light));
        b_daily_shop.setTextColor(mContext.getResources().getColor(R.color.app_light));
        b_daily_shop.setHolo(true);
        b_daily_shop.setOnClickListener(getListener(1));

        b_split.setColor(mContext.getResources().getColor(R.color.split));
        b_split.setTextColor(mContext.getResources().getColor(R.color.split));
        b_split.setHolo(true);
        b_split.setOnClickListener(getListener(2));

        b_income.setColor(mContext.getResources().getColor(R.color.income));
        b_income.setTextColor(mContext.getResources().getColor(R.color.income));
        b_income.setHolo(true);
        b_income.setOnClickListener(getListener(3));

        b_expense.setColor(mContext.getResources().getColor(R.color.expense));
        b_expense.setTextColor(mContext.getResources().getColor(R.color.expense));
        b_expense.setHolo(true);
        b_expense.setOnClickListener(getListener(4));

        b_lent.setColor(mContext.getResources().getColor(R.color.lent));
        b_lent.setTextColor(mContext.getResources().getColor(R.color.lent));
        b_lent.setHolo(true);
        b_lent.setOnClickListener(getListener(5));

        b_borrow.setColor(mContext.getResources().getColor(R.color.borrow));
        b_borrow.setTextColor(mContext.getResources().getColor(R.color.borrow));
        b_borrow.setHolo(true);
        b_borrow.setOnClickListener(getListener(6));

        b_category.setColor(mContext.getResources().getColor(R.color.category_light));
        b_category.setTextColor(mContext.getResources().getColor(R.color.category));
        b_category.setOnClickListener(getListener(7));

        b_circle.setColor(mContext.getResources().getColor(R.color.circle_light));
        b_circle.setTextColor(mContext.getResources().getColor(R.color.circle));
        b_circle.setOnClickListener(getListener(8));

        SlideToAbove();
    }


    public void showDialogWithAnimation() {
        show();
        SlideToAbove();
    }

    public void SlideToAbove() {
        Animation slide = null;
        slide = new TranslateAnimation(Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0.0f, Animation.RELATIVE_TO_PARENT,
                2.0f, Animation.ABSOLUTE, 0.0f);

        slide.setDuration(1000);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
//        b_daily_shop.startAnimation(slide);
//        b_split.startAnimation(slide);
//        b_income.startAnimation(slide);
//        b_expense.startAnimation(slide);
//        b_lent.startAnimation(slide);
//        b_borrow.startAnimation(slide);
//        b_category.startAnimation(slide);
//        b_circle.startAnimation(slide);
        ll_1.startAnimation(slide);
        if(mContact == null)
            ll_2.startAnimation(slide);
        ll_3.startAnimation(slide);
        if(mContact == null)
            ll_4.startAnimation(slide);
        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

              //  rl_footer.clearAnimation();
            }

        });

    }

    private View.OnClickListener getListener(final int type) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(type);
            }
        };
    }

    private void handleButtonClick(int type) {
        String msg = "";
        Intent i = null;

        switch(type) {
            case 1:
                msg = "daily expense";
                break;
            case 2:
                msg = "split";
                i = new Intent(mContext,SplitToolActivity.class);
                break;
            case 3:
                msg = "income";
                i = new Intent(mContext,AddNewEntryActivity.class);
                i.putExtra(C.ENTRY_TYPE, C.ENTRY_TYPE_INPUT);
                i.putExtra(C.MODEL_TYPE, Model.MODEL_TYPE_INCOME);
                break;
            case 4:
                msg = "expense";
                i = new Intent(mContext,AddNewEntryActivity.class);
                i.putExtra(C.ENTRY_TYPE, C.ENTRY_TYPE_INPUT);
                i.putExtra(C.MODEL_TYPE, Model.MODEL_TYPE_EXPENSE);
                break;
            case 5:
                msg = "lent";
                i = new Intent(mContext,AddNewEntryActivity.class);
                i.putExtra(C.ENTRY_TYPE, C.ENTRY_TYPE_INPUT);
                i.putExtra(C.MODEL_TYPE, Model.MODEL_TYPE_LENT);
                break;
            case 6:
                msg = "borrow";
                i = new Intent(mContext,AddNewEntryActivity.class);
                i.putExtra(C.ENTRY_TYPE, C.ENTRY_TYPE_INPUT);
                i.putExtra(C.MODEL_TYPE, Model.MODEL_TYPE_BORROW);
                break;
            case 7:
                msg = "category";
                break;
            case 8:
                msg = "circle";
                i = new Intent(mContext, ManageCircleActivity.class);
                break;
        }

        if(i != null) {

            if(mContact != null){
                i.putExtra(C.CONTACT_UID,mContact.getUID());

            }
            mContext.startActivity(i);
        }
        Toast.makeText(mContext, "Add new " + msg, Toast.LENGTH_SHORT).show();
        this.dismiss();
    }

}
