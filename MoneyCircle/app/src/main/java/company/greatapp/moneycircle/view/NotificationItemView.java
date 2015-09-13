package company.greatapp.moneycircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.constants.States;
import company.greatapp.moneycircle.manager.Transporter;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.MoneyCirclePackageFromServer;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.Tools;

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
    private final TextView tv_response_state_msg;
    private boolean isResponded =true;
    private MoneyCirclePackageFromServer mMoneyCirclePackageFromServer;

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

        tv_response_state_msg = (TextView)viewGroup.findViewById(R.id.tv_notification_response_state_msg);

        ll_agree_frame = (LinearLayout)viewGroup.findViewById(R.id.ll_notification_agree_disagree_frame);
        b_agree = (Button)viewGroup.findViewById(R.id.b_notification_agree);
        b_disagree = (Button)viewGroup.findViewById(R.id.b_notification_disagree);


        ll_remove_entry_frame = (LinearLayout)viewGroup.findViewById(R.id.ll_notification_remove_entry_frame);
        b_remove_entry = (Button)viewGroup.findViewById(R.id.b_notification_remove_entry);
        b_add_expense_entry = (Button)viewGroup.findViewById(R.id.b_notification_add_entry_expense);
        b_edit_resend = (Button)viewGroup.findViewById(R.id.b_notification_edit_resend);

        b_clear = (Button)viewGroup.findViewById(R.id.b_notification_clear);

        b_agree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAgreeClicked();
            }
        });

        b_disagree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDisagreeClicked();
            }
        });
        b_remove_entry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRemoveEntryClicked();
            }
        });
        b_add_expense_entry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddAsItemClicked();
            }
        });
        b_edit_resend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEditAndResendClicked();
            }
        });
        b_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClearClicked();
            }
        });


    }

    private void handleClearClicked() {

    }

    private void handleEditAndResendClicked() {

    }

    private void handleAddAsItemClicked() {

    }

    private void handleRemoveEntryClicked() {

    }

    private void handleDisagreeClicked() {
        if(mMoneyCirclePackageFromServer == null) return;
        switch(mMoneyCirclePackageFromServer.getReqCode()) {
            case S.TRANSPORT_REQUEST_CODE_LENT:

                mMoneyCirclePackageFromServer.setIsRespondable(true);
                mMoneyCirclePackageFromServer.setResponseState(MoneyCirclePackageFromServer.RESPONSE_STATE_DISAGREED);
                mMoneyCirclePackageFromServer.updateItemInDb(getContext());
                Transporter transporter = new Transporter(getContext());
                String transportId = transporter.transportPackageApproval(mMoneyCirclePackageFromServer,false);
                break;
        }
    }

    private void handleAgreeClicked() {
        if(mMoneyCirclePackageFromServer == null) return;
        Transporter transporter = new Transporter(getContext());
        String transportId = "";
        User user = new User(getContext());
        String lentUid = "";
        String borrowUid = "";
        Borrow borrow;
        Lent lent;

        switch(mMoneyCirclePackageFromServer.getReqCode()) {
            case S.TRANSPORT_REQUEST_CODE_LENT:
                //Get new borrow item with state PAYMENT_PENDING
                borrow = new Borrow(mMoneyCirclePackageFromServer);

                borrow.insertItemInDB(getContext());

                mMoneyCirclePackageFromServer.setResponseState(MoneyCirclePackageFromServer.RESPONSE_STATE_AGREED);

                mMoneyCirclePackageFromServer.setAssociateItemId(borrow.getUID().replaceAll("NEW", "DB"));
                mMoneyCirclePackageFromServer.updateItemInDb(getContext());

                transportId = transporter.transportPackageApproval(mMoneyCirclePackageFromServer,true);
                Tools.sendMoneyTransactionBroadCast(getContext(), borrow, Model.MODEL_TYPE_BORROW);
                break;
            case S.TRANSPORT_REQUEST_CODE_BORROW:
                //Get new lent item with state WAITING FOR PAYMENT
                lent = new Lent(mMoneyCirclePackageFromServer);
                lent.insertItemInDB(getContext());

                mMoneyCirclePackageFromServer.setResponseState(MoneyCirclePackageFromServer.RESPONSE_STATE_AGREED);

                mMoneyCirclePackageFromServer.setAssociateItemId(lent.getUID().replaceAll("NEW", "DB"));
                mMoneyCirclePackageFromServer.updateItemInDb(getContext());

                transportId = transporter.transportPackageApproval(mMoneyCirclePackageFromServer,true);
                Tools.sendMoneyTransactionBroadCast(getContext(), lent, Model.MODEL_TYPE_LENT);
                break;

            case S.TRANSPORT_REQUEST_CODE_PAY:
                if(user.getPhoneNumber().equals(mMoneyCirclePackageFromServer.getItemAssociatePhone())){
                    lentUid = mMoneyCirclePackageFromServer.getAssociateItemId();
                } else {
                    lentUid = mMoneyCirclePackageFromServer.getOwnerItemId();
                }
                mMoneyCirclePackageFromServer.setIsRespondable(true);
                mMoneyCirclePackageFromServer.setResponseState(MoneyCirclePackageFromServer.RESPONSE_STATE_AGREED);
                mMoneyCirclePackageFromServer.updateItemInDb(getContext());

                lent = (Lent)Tools.getDbInstance(getContext(),lentUid,Model.MODEL_TYPE_LENT);
                lent.setState(States.LENT_PAYMENT_CLEARED);
                lent.updateItemInDb(getContext());

                transportId = transporter.transportPackageApproval(mMoneyCirclePackageFromServer,true);
                Tools.sendMoneyTransactionBroadCast(getContext(), lent, Model.MODEL_TYPE_LENT);
                break;

        }
    }

    public String getResponseContextMsg() {
        String msg = "";

            if(mMoneyCirclePackageFromServer.getResponseState() == MoneyCirclePackageFromServer.RESPONSE_STATE_AGREED) {
                switch (mMoneyCirclePackageFromServer.getReqCode()) {
                    case S.TRANSPORT_REQUEST_CODE_LENT:
                        msg = "This entry has been added in Borrow items";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_BORROW:
                        msg = "This entry has been added in Lent items";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_SETTLE:
                        msg = "settle up is done for this request";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_PAY:
                        msg = "Received Payment is updated in Lent item";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_RECEIVE:
                        msg = "Paid Payment is updated in Borrow item";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_REMINDER:
                        msg = "";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_MODIFY_LENT:
                        msg = "This Borrow is Modified in item List";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_MODIFY_BORROW:
                        msg = "This Lent is Modified in item List";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_DELETE_LENT:
                        msg = "This Borrow is Deleted in item List";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_DELETE_BORROW:
                        msg = "This Lent is Deleted in item List";
                        break;


                }
            } else if(mMoneyCirclePackageFromServer.getResponseState() == MoneyCirclePackageFromServer.RESPONSE_STATE_DISAGREED){//Disagreed
                switch (mMoneyCirclePackageFromServer.getReqCode()) {
                    case S.TRANSPORT_REQUEST_CODE_LENT:
                        msg = "You disagreed this borrow";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_BORROW:
                        msg = "You disagreed this lent";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_SETTLE:
                        msg = "You disagreed this settle up request";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_PAY:
                        msg = "You disagreed this payment received";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_RECEIVE:
                        msg = "You disagreed this Paid Amount";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_REMINDER:
                        msg = "";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_MODIFY_LENT:
                        msg = "This Borrow Modify request is declined";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_MODIFY_BORROW:
                        msg = "This Lent Modify request is declined";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_DELETE_LENT:
                        msg = "This Borrow delete request is declined";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_DELETE_BORROW:
                        msg = "This Lent delete request is declined";
                        break;
                }
            } else if(mMoneyCirclePackageFromServer.getResponseState() ==
                    MoneyCirclePackageFromServer.RESPONSE_STATE_NOT_RESPONDED){

                switch (mMoneyCirclePackageFromServer.getReqCode()) {
                    case S.TRANSPORT_REQUEST_CODE_LENT:
                        msg = "Press Agree for adding this transaction in Borrow list";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_BORROW:
                        msg = "Press Agree for adding this transaction in Lent list";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_SETTLE:
                        msg = "Press Agree for settling up with this contact";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_PAY:
                        msg = "Press Agree for updating received payment in lent item";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_RECEIVE:
                        msg = "Press Agree for updating paid amount in Borrow item";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_REMINDER:
                        msg = "";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_MODIFY_LENT:
                        msg = "Press Agree for modify this Borrow";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_MODIFY_BORROW:
                        msg = "Press Agree for modify this Lent";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_DELETE_LENT:
                        msg = "Press Agree to delete this Borrow";
                        break;
                    case S.TRANSPORT_REQUEST_CODE_DELETE_BORROW:
                        msg = "Press Agree to delete this Lent";
                        break;
                }
            }
        return msg;
    }

    public void initView(MoneyCirclePackageFromServer moneyCirclePackageFromServer) {
        this.mMoneyCirclePackageFromServer = moneyCirclePackageFromServer;
       int reqCode = moneyCirclePackageFromServer.getReqCode();

        tv_response_state_msg.setText(getResponseContextMsg());
        String sender = moneyCirclePackageFromServer.getReqSenderName();

        String msg;
        if(reqCode != S.TRANSPORT_REQUEST_CODE_NOTIFICATION) {
            msg = moneyCirclePackageFromServer.createNotificationMessage();
        } else {
            msg = moneyCirclePackageFromServer.getMessage();
        }

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
        boolean isResponseViewNotRequired = !(moneyCirclePackageFromServer.isRespondable()
                &&
        (moneyCirclePackageFromServer.getResponseState() == MoneyCirclePackageFromServer.RESPONSE_STATE_NOT_RESPONDED));


        switch(reqCode) {

            case S.TRANSPORT_REQUEST_CODE_LENT:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_BORROW:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_PAY:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_RECEIVE:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_SETTLE:

                ll_money_item_frame.setVisibility(GONE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_REMINDER:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_MODIFY_LENT:
            case S.TRANSPORT_REQUEST_CODE_MODIFY_BORROW:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_DELETE_BORROW:
            case S.TRANSPORT_REQUEST_CODE_DELETE_LENT:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_AGREE_LENT:
            case S.TRANSPORT_REQUEST_CODE_AGREE_BORROW:
            case S.TRANSPORT_REQUEST_CODE_AGREE_PAY:
            case S.TRANSPORT_REQUEST_CODE_AGREE_MODIFY:
            case S.TRANSPORT_REQUEST_CODE_AGREE_SETTLE:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;
            case S.TRANSPORT_REQUEST_CODE_DISAGREE_LENT:
            case S.TRANSPORT_REQUEST_CODE_DISAGREE_BORROW:
            case S.TRANSPORT_REQUEST_CODE_DISAGREE_PAY:
            case S.TRANSPORT_REQUEST_CODE_DISAGREE_MODIFY:
            case S.TRANSPORT_REQUEST_CODE_DISAGREE_SETTLE:

                ll_money_item_frame.setVisibility(VISIBLE);
                if(isResponseViewNotRequired) {
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);
                } else {
                    ll_agree_frame.setVisibility(VISIBLE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(GONE);
                }
                break;

            case S.TRANSPORT_REQUEST_CODE_NOTIFICATION:

                ll_money_item_frame.setVisibility(VISIBLE);
                    ll_agree_frame.setVisibility(GONE);
                    ll_remove_entry_frame.setVisibility(GONE);
                    b_clear.setVisibility(VISIBLE);

                break;


        }

    }
}
