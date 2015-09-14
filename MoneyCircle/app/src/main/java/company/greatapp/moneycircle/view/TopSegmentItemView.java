package company.greatapp.moneycircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Circle;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 8/20/2015.
 */
public class TopSegmentItemView extends LinearLayout {

    private Model model = null;
    private ViewGroup viewGroup;
    private TextView tv_item_title;
    private ImageView iv_item_icon;
    private TextView tv_item_value;

    public TopSegmentItemView(Context context, AttributeSet attr) {
        super(context,attr);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            viewGroup = (ViewGroup)inflater.inflate(R.layout.top_segment_item_layout, this, true);
            tv_item_title = (TextView)viewGroup.findViewById(R.id.tv_top_segment_item_title);
            tv_item_value = (TextView)viewGroup.findViewById(R.id.tv_top_segment_item_value);
            iv_item_icon = (ImageView)viewGroup.findViewById(R.id.iv_top_segment_item_icon);

//            LayerDrawable bgDrawable = (LayerDrawable)viewGroup.getBackground();
//            final GradientDrawable shape = (GradientDrawable)bgDrawable.findDrawableByLayerId(R.id.shape_circle_outer_ring_item);
//            shape.setStroke(10,context.getResources().getColor(R.color.app_basic));
        }
    }

    public void setItemTitle(String title) {
        tv_item_title.setText(title);
    }

    public void setItemValue(String value) {
        tv_item_value.setText(value);
    }

    public void setModeOnlyTitle() {
        tv_item_value.setVisibility(View.GONE);
        iv_item_icon.setVisibility(View.GONE);
        tv_item_title.setVisibility(View.VISIBLE);
    }

    public void setModeTitleAndValue() {
        tv_item_value.setVisibility(View.VISIBLE);
        iv_item_icon.setVisibility(View.GONE);
        tv_item_title.setVisibility(View.VISIBLE);
    }

    public void setModeTitleAndIcon() {
        tv_item_value.setVisibility(View.GONE);
        iv_item_icon.setVisibility(View.VISIBLE);
        tv_item_title.setVisibility(View.VISIBLE);
    }

    public void setModeValueAndIcon() {
        tv_item_value.setVisibility(View.VISIBLE);
        iv_item_icon.setVisibility(View.VISIBLE);
        tv_item_title.setVisibility(View.GONE);
    }

    public void setModeTitleValueAndIcon() {
        tv_item_value.setVisibility(View.VISIBLE);
        iv_item_icon.setVisibility(View.VISIBLE);
        tv_item_title.setVisibility(View.VISIBLE);
    }

    public void setModel(Model model, int modelType) {
        switch(modelType) {
            case Model.MODEL_TYPE_CONTACT:
                Contact contact = (Contact)model;
                float value = contact.getLentAmountToThis() - contact.getBorrowedAmountfromThis();
                float value_negative = contact.getBorrowedAmountfromThis() - contact.getLentAmountToThis();
                String msg = "";
                if(value > 0) {
                    msg = "owes you "+ Tools.floatString(value);
                    tv_item_value.setTextColor(getContext().getResources().getColor(R.color.text_info));
                } else if(value < 0) {
                    tv_item_value.setTextColor(getContext().getResources().getColor(R.color.text_error));
                    msg = "you owe "+ Tools.floatString(value_negative);
                } else {
                    tv_item_value.setTextColor(getContext().getResources().getColor(R.color.text_basic_light));
                    msg = "settled";
                }
                tv_item_value.setText(msg);
                tv_item_title.setText(contact.getContactName());
                setModeTitleValueAndIcon();
                break;
            case Model.MODEL_TYPE_CATEGORY:
                Category category = (Category)model;
                tv_item_title.setText(category.getTitle());
                tv_item_value.setText(Tools.floatString(category.getSpentAmountOnThis()));
                setModeTitleValueAndIcon();
                break;
            case Model.MODEL_TYPE_CIRCLE:
                Circle circle = (Circle)model;
                tv_item_title.setText(circle.getTitle());
                tv_item_value.setText(""+circle.getMemberCount());
                setModeTitleAndValue();
                break;
        }
    }


}
