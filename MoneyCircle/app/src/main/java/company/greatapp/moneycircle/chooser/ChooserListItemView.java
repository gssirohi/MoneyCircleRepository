package company.greatapp.moneycircle.chooser;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Contact;

/**
 * Created by Gyanendrasingh on 26-06-2015.
 */
public class ChooserListItemView extends LinearLayout {

    private final Model model;

    public ChooserListItemView(Context context, AttributeSet attrs, LayoutInflater infaltor,int type,
                               Model model) {
        super(context, attrs);
        this.model = model;
        ViewGroup viewGroup = (ViewGroup) infaltor.inflate(R.layout.single_chooser_list_item_layout, this, true);
        TextView tv = (TextView) viewGroup.findViewById(R.id.tv_chooser_item);
        tv.setText(((Contact)model).getContactName());

    }

}
