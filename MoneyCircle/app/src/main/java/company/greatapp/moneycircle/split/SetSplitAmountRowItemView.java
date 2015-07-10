package company.greatapp.moneycircle.split;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Participant;

/**
 * Created by Gyanendrasingh on 26-06-2015.
 */
public class SetSplitAmountRowItemView extends LinearLayout {
    private final TextView tv_name;
    private final Button b_ok;
    private final Button b_edit;
    private final EditText et_editableValue;
    private final Participant participant;
    private final LinearLayout ll_display;

    public SetSplitAmountRowItemView(Context context, AttributeSet attrs,LayoutInflater infaltor,
                                     Participant participant) {
        super(context, attrs);
        this.participant = participant;
        ViewGroup viewGroup = (ViewGroup) infaltor.inflate(R.layout.set_split_amount_row_item, this, true);
        tv_name = (TextView) viewGroup.findViewById(R.id.tv_name);
        b_ok = (Button) viewGroup.findViewById(R.id.b_ok);
        b_edit = (Button) viewGroup.findViewById(R.id.b_edit);
        et_editableValue = (EditText) viewGroup.findViewById(R.id.et_amount);
        ll_display = (LinearLayout)findViewById(R.id.ll_display);

        et_editableValue.setVisibility(View.GONE);
        b_ok.setVisibility(View.GONE);

        setDisplay(participant);

        b_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOK();
            }
        });
        b_edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEDIT();
            }
        });
    }

    private void setDisplay(Participant participant) {
        String memberName = participant.memberName;
        float amount  = participant.amount;
        float percent = participant.percent;
        float shares = participant.share;
        TextView tv_amount = (TextView)ll_display.findViewById(R.id.tv_amount);
        TextView tv_percent = (TextView)ll_display.findViewById(R.id.tv_percent);
        TextView tv_share = (TextView)ll_display.findViewById(R.id.tv_shares);

        tv_name.setText(memberName);

        tv_amount.setText("" + new DecimalFormat("##.##").format(amount));
        tv_percent.setText("" + new DecimalFormat("##.##").format(percent));
        tv_share.setText("" + new DecimalFormat("##.##").format(shares));

        et_editableValue.setText("00");

    }

    private void onClickEDIT() {
        et_editableValue.setText("" + participant.amount);
        et_editableValue.setVisibility(View.VISIBLE);
        ll_display.setVisibility(View.GONE);
        b_edit.setVisibility(View.GONE);
        b_ok.setVisibility(View.VISIBLE);
    }

    private void onClickOK() {
        String value = et_editableValue.getText().toString();
        participant.editableValue = Float.parseFloat(value);
        et_editableValue.setVisibility(View.GONE);
        ll_display.setVisibility(View.VISIBLE);
        b_ok.setVisibility(View.GONE);
        b_edit.setVisibility(View.VISIBLE);
        sendRefreshAction();
    }

    private void sendRefreshAction() {
            Log.d("sender", "Broadcasting message");
            Intent intent = new Intent(SetSplitAmountActivity.ACTION_AMOUNT_REFRESH);
            // You can also include some extra data.
            intent.putExtra("message", "This is my message!");
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    public Participant getParticipant(){
        return this.participant;
    }
}
