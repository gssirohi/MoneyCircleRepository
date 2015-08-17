package company.greatapp.moneycircle.circles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import company.greatapp.moneycircle.R;

/**
 * Created by Prateek on 12-07-2015.
 */
public class CircleView extends LinearLayout {
    public CircleView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.circle_item_view, this, false);

            LinearLayout groupListLinearLayout = (LinearLayout) findViewById(R.id.llGroupListId);
        }
    }
}
