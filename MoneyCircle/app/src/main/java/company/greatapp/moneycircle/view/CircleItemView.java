package company.greatapp.moneycircle.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 6/30/2015.
 */
public class CircleItemView extends RelativeLayout {

    private ViewGroup viewGroup;
    private TextView tv_item_name;
    private TextView tv_item_value;

    public CircleItemView(Context context, AttributeSet attr) {
        super(context,attr);
        init(context);
    }

    public CircleItemView(Context context, AttributeSet attr, String itemName, String itemValue) {
        super(context,attr);
        init(context);
        setItemName(itemName);
        setItemValue(itemValue);
    }

    public void setItemName(String itemName) {
        tv_item_name.setText("" + itemName);
    }

    public void setItemValue(String itemValue) {
        tv_item_value.setText("" + itemValue);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            viewGroup = (ViewGroup) inflater.inflate(R.layout.circle_item_value_layout, this, true);
            tv_item_name = (TextView)viewGroup.findViewById(R.id.tv_circle_item_name);
            tv_item_value = (TextView)viewGroup.findViewById(R.id.tv_circle_item_value);
        }

    }

}
