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

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.RegisteredContact;
import company.greatapp.moneycircle.split.SetSplitAmount;

/**
 * Created by Gyanendrasingh on 26-06-2015.
 */
public class SetSplitAmountRowItemView extends LinearLayout {
    private final TextView tv_name;
    private final Button b_ok;
    private final Button b_edit;
    private final EditText et_amount;
    private final TextView tv_amount;
    private final SetSplitAmount.Participant participant;

    public SetSplitAmountRowItemView(Context context, AttributeSet attrs,LayoutInflater infaltor,
                                     SetSplitAmount.Participant participant) {
        super(context, attrs);
        this.participant = participant;
        ViewGroup viewGroup = (ViewGroup) infaltor.inflate(R.layout.set_split_amount_row_item, this, true);
        tv_name = (TextView) viewGroup.findViewById(R.id.tv_name);
        b_ok = (Button) viewGroup.findViewById(R.id.b_ok);
        b_edit = (Button) viewGroup.findViewById(R.id.b_edit);
        et_amount = (EditText) viewGroup.findViewById(R.id.et_amount);
        tv_amount = (TextView) viewGroup.findViewById(R.id.tv_amount);
        et_amount.setVisibility(View.GONE);
        b_ok.setVisibility(View.GONE);
        TextView text = (TextView) viewGroup.findViewById(R.id.tv_name);
        RegisteredContact member = participant.member;
        int amount  = participant.amount;
        text.setText(member.getName());
        tv_amount.setText(""+amount);
        et_amount.setText(""+amount);

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

    private void onClickEDIT() {
        et_amount.setText(""+participant.amount);
        et_amount.setVisibility(View.VISIBLE);
        tv_amount.setVisibility(View.GONE);
        b_edit.setVisibility(View.GONE);
        b_ok.setVisibility(View.VISIBLE);
    }

    private void onClickOK() {
        participant.amount = Integer.parseInt(et_amount.getText().toString());
        tv_amount.setText(""+participant.amount);
        et_amount.setVisibility(View.GONE);
        tv_amount.setVisibility(View.VISIBLE);
        b_ok.setVisibility(View.GONE);
        b_edit.setVisibility(View.VISIBLE);
        sendRefreshAction();
    }

    private void sendRefreshAction() {
            Log.d("sender", "Broadcasting message");
            Intent intent = new Intent(SetSplitAmount.ACTION_AMOUNT_REFRESH);
            // You can also include some extra data.
            intent.putExtra("message", "This is my message!");
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    public SetSplitAmount.Participant getParticipant(){
        return this.participant;
    }
}
