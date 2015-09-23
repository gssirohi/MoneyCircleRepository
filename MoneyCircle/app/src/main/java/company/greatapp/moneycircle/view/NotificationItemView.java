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
import company.greatapp.moneycircle.manager.Accountant;
import company.greatapp.moneycircle.manager.Transporter;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.InPackage;
import company.greatapp.moneycircle.tools.Tools;

import static android.widget.Toast.makeText;


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
    private InPackage mInPackage;

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

        Contact contact;
        if(mInPackage.getReqCode() == S.TRANSPORT_REQUEST_CODE_SETTLE) {
            contact = Tools.getContactFromPhoneNumber(getContext(),mInPackage.getReqSenderPhone());
            if(contact != null) {
                contact.setState(States.CONTACT_IDEAL);
                contact.updateItemInDb(getContext());
                Transporter transporter = new Transporter(getContext());
                String transportId = transporter.transportSettleUpApproval(mInPackage,false);
            }
        }
        mInPackage.setResponseState(InPackage.RESPONSE_STATE_DISAGREED);
        mInPackage.updateItemInDb(getContext());
    }

    private void handleAgreeClicked() {

        Contact contact;
        if(mInPackage.getReqCode() == S.TRANSPORT_REQUEST_CODE_SETTLE) {
            contact = Tools.getContactFromPhoneNumber(getContext(),mInPackage.getReqSenderPhone());
            if(contact != null) {
                Accountant.performSettleUpWithContact(getContext(),contact);
                Transporter transporter = new Transporter(getContext());
                String transportId = transporter.transportSettleUpApproval(mInPackage,true);
            }
        }
        mInPackage.setResponseState(InPackage.RESPONSE_STATE_AGREED);
        mInPackage.updateItemInDb(getContext());
    }


    public void initView(InPackage inPackage) {
        this.mInPackage = inPackage;
       int reqCode = inPackage.getReqCode();

        tv_response_state_msg.setText(getResponseContextMsg());
        String sender = inPackage.getReqSenderName();

        String msg;
        if(reqCode != S.TRANSPORT_REQUEST_CODE_NOTIFICATION) {
            msg = inPackage.createNotificationMessage();
        } else {
            msg = inPackage.getMessage();

        }

        String moneyItemTitle = inPackage.getItemTitle();
        String amount = ""+ inPackage.getAmount();
        String dateString = ""+ inPackage.getItemDateString();
        String dueDateString = ""+ inPackage.getItemDueDateString();

        ll_money_item_frame.setVisibility(View.GONE);
        ll_agree_frame.setVisibility(View.GONE);
        ll_remove_entry_frame.setVisibility(View.GONE);

        tv_contact_name.setText(sender);
        tv_notification_msg.setText(msg);
        tv_title.setText(moneyItemTitle);
        tv_amount.setText(""+amount);
        tv_date.setText(dateString);
        tv_due_date.setText("due on " + dueDateString);

        doNotAllowPendingAction();

        switch(reqCode){

            case S.TRANSPORT_REQUEST_CODE_LENT:
                break;
            case S.TRANSPORT_REQUEST_CODE_BORROW:
                break;
            case S.TRANSPORT_REQUEST_CODE_PAY:
                break;
            case S.TRANSPORT_REQUEST_CODE_RECEIVE:
                break;
            case S.TRANSPORT_REQUEST_CODE_SETTLE:
                if(inPackage.getResponseState() == InPackage.RESPONSE_STATE_NOT_RESPONDED) {
                    allowPendingAction(true, InPackage.PENDING_ACTION_TYPE_APPROVAL);
                } else {

                }
                break;
            case S.TRANSPORT_REQUEST_CODE_REMINDER:
                break;
            case S.TRANSPORT_REQUEST_CODE_NOTIFICATION:
                break;
            case S.TRANSPORT_REQUEST_CODE_DELETE_BORROW:
                break;
            case S.TRANSPORT_REQUEST_CODE_DELETE_LENT:
                break;
            case S.TRANSPORT_REQUEST_CODE_SETTLE_AGREE:
                break;
            case S.TRANSPORT_REQUEST_CODE_SETTLE_DISAGREE:
                break;
            default:
        }

    }

    public void doNotAllowPendingAction() {
        allowPendingAction(false,0);
    }
    public void allowPendingAction(boolean allow,int pendingActionType) {
        if(allow) {
            if(pendingActionType == InPackage.PENDING_ACTION_TYPE_APPROVAL) {
                ll_remove_entry_frame.setVisibility(View.GONE);
                ll_agree_frame.setVisibility(View.VISIBLE);
                b_clear.setVisibility(View.GONE);
            } else if(pendingActionType == InPackage.PENDING_ACTION_TYPE_REMOVE_ENTRY) {
                ll_remove_entry_frame.setVisibility(View.VISIBLE);
                ll_agree_frame.setVisibility(View.GONE);
                b_clear.setVisibility(View.GONE);
            }

        } else {
            ll_remove_entry_frame.setVisibility(View.GONE);
            ll_agree_frame.setVisibility(View.GONE);
            b_clear.setVisibility(View.VISIBLE);
        }
    }


    public String getResponseContextMsg() {
        String msg = "";

       return msg;
    }

}
