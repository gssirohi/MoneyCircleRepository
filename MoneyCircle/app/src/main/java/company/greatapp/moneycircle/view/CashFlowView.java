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
    private CircleItemView civ_cash_net;
    private CircleItemView civ_cash_in;
    private CircleItemView civ_cash_out;

    public CashFlowView(Context context, AttributeSet attr) {
        super(context,attr);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            viewGroup = (ViewGroup)inflater.inflate(R.layout.cash_flow, this, true);
            civ_cash_net = (CircleItemView)viewGroup.findViewById(R.id.civ_cash_net_flow);
            civ_cash_in = (CircleItemView)viewGroup.findViewById(R.id.civ_cash_in);
            civ_cash_out = (CircleItemView)viewGroup.findViewById(R.id.civ_cash_out);

            civ_cash_net.setItemName("NET CASH IN");
            civ_cash_in.setItemName("TOTAL CASH IN");
            civ_cash_out.setItemName("TOTAL CASH OUT");

            setCashIn("00.00");
            setCashOut("00.00");
            setCashNet("00.00");

        }
    }

    public void setCashIn(String cash) {
        civ_cash_in.setItemValue(cash);
    }

    public void setCashOut(String cash) {
        civ_cash_out.setItemValue(cash);
    }

    public void setCashNet(String cash) {
        civ_cash_net.setItemValue(cash);
    }

    public void setResultTitle(String title) {
        civ_cash_net.setItemName(title);
    }

    public void setFirstItemTitle(String title) {
        civ_cash_in.setItemName(title);
    }

    public void setSecondItemTitle(String title){
        civ_cash_out.setItemName(title);
    }

    public void setResultValue(String value) {
        civ_cash_net.setItemValue(value);
    }
    public void setFirstItemValue(String value){
        civ_cash_in.setItemValue(value);
    }
    public void setSecondItemValue(String value){
        civ_cash_out.setItemValue(value);
    }
}
