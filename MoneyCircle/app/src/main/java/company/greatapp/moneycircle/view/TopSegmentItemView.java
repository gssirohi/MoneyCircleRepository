package company.greatapp.moneycircle.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;

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


}
