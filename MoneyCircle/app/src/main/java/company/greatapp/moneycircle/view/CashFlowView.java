package company.greatapp.moneycircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 8/20/2015.
 */
public class CashFlowView extends LinearLayout {

    private Model model = null;
    private String title;
    private ViewGroup viewGroup;
    private CircleButton cb_cash_net;
    private CircleButton cb_cash_in;
    private CircleButton cb_cash_out;

    public CashFlowView(Context context, AttributeSet attr) {
        super(context,attr);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            viewGroup = (ViewGroup)inflater.inflate(R.layout.cash_flow, this, true);
            cb_cash_net = (CircleButton)viewGroup.findViewById(R.id.cb_cash_net);
            cb_cash_in = (CircleButton)viewGroup.findViewById(R.id.cb_cash_in);
            cb_cash_out = (CircleButton)viewGroup.findViewById(R.id.cb_cash_out);

            int color = getResources().getColor(R.color.app_basic);
            int dark = getResources().getColor(R.color.app_darker);
            cb_cash_net.setHolo(true);
            cb_cash_net.setColor(color);
            cb_cash_net.setTextColor(dark);
            cb_cash_in.setHolo(true);
            cb_cash_in.setColor(color);
            cb_cash_in.setTextColor(dark);
            cb_cash_out.setHolo(true);
            cb_cash_out.setColor(color);
            cb_cash_out.setTextColor(dark);


        }
    }

    public void setCashIn(String cash) {
        cb_cash_in.setText(cash);
    }

    public void setCashOut(String cash) {
        cb_cash_out.setText(cash);
    }

    public void setCashNet(String cash) {
        cb_cash_net.setText(cash);
    }
}
