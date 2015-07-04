package company.greatapp.moneycircle.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.split.SetSplitAmountActivity;

/**
 * Created by gyanendra.sirohi on 6/30/2015.
 */
public class TagItemView extends LinearLayout {

    public static final int TYPE_REGISTERED_CONTACT = 1;

    public TagItemView(Context context, int type, String title) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.tag_item_layout, this, true);
            Button b = (Button)viewGroup.findViewById(R.id.b_tag_item);
            b.setText(title);
            if(type == TYPE_REGISTERED_CONTACT) {
                b.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
            }
        }
    }
}
