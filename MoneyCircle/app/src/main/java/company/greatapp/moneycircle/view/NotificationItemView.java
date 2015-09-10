package company.greatapp.moneycircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.model.MoneyCirclePackageFromServer;

/**
 * Created by Gyanendrasingh on 9/6/2015.
 */
public class NotificationItemView extends LinearLayout {

    private final ViewGroup viewGroup;
    private final TextView tv_title;
    private final TextView tv_amount;
    private final TextView tv_date;
    private final TextView tv_due_date;
    private final ImageView iv_contact_image;
    private final TextView tv_contact_name;
    private final TextView tv_notification_msg;
    private final LinearLayout ll_money_item_frame;
    private final LinearLayout ll_agree_frame;
    private final Button b_agree;
    private final Button b_disagree;
    private final LinearLayout ll_remove_entry_frame;
    private final Button b_remove_entry;
    private final Button b_add_expense_entry;
    private final Button b_edit_resend;
    private final Button b_clear;
    private boolean isResponded =true;

    public NotificationItemView(Context context, AttributeSet attrs) {

        super(context, attrs);

        Log.d("Spilt", "called super");
        setOrientation(LinearLayout.VERTICAL);
        // setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.notification_item_layout, this, true);

        iv_contact_image = (ImageView)viewGroup.findViewById(R.id.iv_notification_contact_image);
        tv_contact_name = (TextView)viewGroup.findViewById(R.id.tv_notification_contact_name);
        tv_notification_msg = (TextView)viewGroup.findViewById(R.id.tv_notification_message_text);

        ll_money_item_frame = (LinearLayout)viewGroup.findViewById(R.id.ll_notification_money_item_frame);
        tv_title = (TextView)viewGroup.findViewById(R.id.tv_item_title);
        tv_amount = (TextView)viewGroup.findViewById(R.id.tv_item_amount);
        tv_date = (TextView)viewGroup.findViewById(R.id.tv_item_date);
        tv_due_date = (TextView)viewGroup.findViewById(R.id.tv_due_date);

        ll_agree_frame = (LinearLayout)viewGroup.findViewById(R.id.ll_notification_agree_disagree_frame);
        b_agree = (Button)viewGroup.findViewById(R.id.b_notification_agree);
        b_disagree = (Button)viewGroup.findViewById(R.id.b_notification_disagree);


        ll_remove_entry_frame = (LinearLayout)viewGroup.findViewById(R.id.ll_notification_remove_entry_frame);
        b_remove_entry = (Button)viewGroup.findViewById(R.id.b_notification_remove_entry);
        b_add_expense_entry = (Button)viewGroup.findViewById(R.id.b_notification_add_entry_expense);
        b_edit_resend = (Button)viewGroup.findViewById(R.id.b_notification_edit_resend);

        b_clear = (Button)viewGroup.findViewById(R.id.b_notification_clear);


    }

    public void makeResponded(boolean isResponded) {
        if(isResponded) {
            ll_agree_frame.setVisibility(GONE);
            ll_remove_entry_frame.setVisibility(GONE);
            b_clear.setVisibility(VISIBLE);
        } else {
            ll_agree_frame.setVisibility(VISIBLE);
            ll_remove_entry_frame.setVisibility(VISIBLE);
            b_clear.setVisibility(GONE);
        }
    }

    public void initView(MoneyCirclePackageFromServer moneyCirclePackageFromServer) {
       int type = moneyCirclePackageFromServer.getReqCode();


        String sender = moneyCirclePackageFromServer.getReqSenderName();

        String msg = moneyCirclePackageFromServer.getMessage();

        String moneyItemTitle = moneyCirclePackageFromServer.getItemTitle();
        String amount = ""+ moneyCirclePackageFromServer.getAmount();
        String dateString = ""+ moneyCirclePackageFromServer.getItemDateString();
        String dueDateString = ""+ moneyCirclePackageFromServer.getItemDueDateString();

        tv_contact_name.setText(sender);
        tv_notification_msg.setText(msg);
        tv_title.setText(moneyItemTitle);
        tv_amount.setText(""+amount);
        tv_date.setText(dateString);
        tv_due_date.setText("due on " + dueDateString);
        boolean isResponded = moneyCirclePackageFromServer.isResponded();


        switch(type) {

            case S.NOTIFICATION_LENT_REQUEST:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_BORROW_REQUEST:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_PAY_REQUEST:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_RECEIVE_REQUEST:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_SETTLE_REQUEST:

                ll_money_item_frame.setVisibility(GONE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_REMINDER_REQUEST:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_MODIFY_lENT_REQUEST:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_DELETE_LENT_REQUEST:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_AGREE_LENT:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.NOTIFICATION_DISAGREE_LENT:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponded) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;


        }
        makeResponded(isResponded);

    }
}
