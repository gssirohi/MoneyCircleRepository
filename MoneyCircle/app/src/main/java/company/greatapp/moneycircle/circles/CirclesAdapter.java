package company.greatapp.moneycircle.circles;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.manager.CircleManager;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Circle;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.view.TagItemView;

/**
 * Created by Prateek on 12-07-2015.
 */
public class CirclesAdapter extends CursorAdapter{

    private final String LOGTAG = getClass().getSimpleName().toString();
    Context mContext;
    LayoutInflater mInflater;
    CircleManager mCircleManager;

    public CirclesAdapter(Context context, Cursor c, boolean autoRequery, ContactManager contactManager) {
        super(context, c, autoRequery);
        Log.d(LOGTAG, "CirclesAdapter Constructor");
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCircleManager = new CircleManager(context, contactManager);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(LOGTAG, "newView");

        View view = mInflater.inflate(R.layout.circle_item_view, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.groupName = (TextView)view.findViewById(R.id.tvGroupNameId);
        holder.membersCount = (TextView)view.findViewById(R.id.tvGroupMemberCountId);
        holder.groupList = (LinearLayout)view.findViewById(R.id.llGroupListId);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d(LOGTAG, "bindView");
        if (view != null && cursor != null) {
            ViewHolder holder = (ViewHolder)view.getTag();
            Circle circle = (Circle)mCircleManager.createHeavyItemFromCursor(cursor);
            holder.groupName.setText(circle.getCircleName());
            holder.membersCount.setText(""+circle.getMemberList().size());
            addTagViews(holder.groupList, C.TAG_CONTACTS, circle.getMemberList());
        }
    }

    private void addTagViews(LinearLayout layout, int type, ArrayList<Contact> memberList) {

        if(layout == null) return;

        int count = layout.getChildCount();
        layout.removeAllViews();

        for (Contact contact : memberList) {
            layout.addView(new TagItemView(mContext, layout, contact, false));
        }

    }

    private static class ViewHolder {
        TextView groupName;
        TextView membersCount;
        LinearLayout groupList;
    }
}
