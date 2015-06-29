package company.greatapp.moneycircle.chooser;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.RegisteredContact;
import company.greatapp.moneycircle.split.SetSplitAmountActivity;

/**
 * Created by Gyanendrasingh on 26-06-2015.
 */
public class ChooserListItemView extends LinearLayout {

    private final Model model;

    public ChooserListItemView(Context context, AttributeSet attrs, LayoutInflater infaltor,int type,
                               Model model) {
        super(context, attrs);
        this.model = model;
        ViewGroup viewGroup = (ViewGroup) infaltor.inflate(R.layout.chooser_list_item_layout, this, true);
        CheckBox checkbox = (CheckBox) viewGroup.findViewById(R.id.cb_chooser_item);
        checkbox.setText(((RegisteredContact)model).getName());

    }

}
