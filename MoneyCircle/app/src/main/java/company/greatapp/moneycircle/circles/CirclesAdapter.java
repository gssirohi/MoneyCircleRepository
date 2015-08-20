package company.greatapp.moneycircle.circles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.model.Circle;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.view.TagItemView;

/**
 * Created by Prateek on 12-07-2015.
 */
public class CirclesAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Model> mCircles = new ArrayList<Model>();
    LayoutInflater mInflater;

    public CirclesAdapter(Context context, ArrayList<Model> circles) {
        mContext = context;
        mCircles = circles;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mCircles.size();
    }

    @Override
    public Object getItem(int position) {
        return mCircles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.circle_item_view, parent, false);
            holder = new ViewHolder();
            holder.groupName = (TextView)convertView.findViewById(R.id.tvGroupNameId);
            holder.membersCount = (TextView)convertView.findViewById(R.id.tvGroupMemberCountId);
            holder.groupList = (LinearLayout)convertView.findViewById(R.id.llGroupListId);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Circle circle = (Circle)mCircles.get(position);
        holder.groupName.setText(circle.getCircleName());
        holder.membersCount.setText(""+circle.getMemberList().size());
        addTagViews(holder.groupList, C.TAG_CONTACTS, circle.getMemberList());

        return convertView;
    }

    private void addTagViews(LinearLayout layout, int type, ArrayList<Contact> memberList) {
//    private void addTagViews(LinearLayout layout, int type) {
        /*TODO returned Result will be uids finally not the String
        so we need to get names/titles of the item from manager classes using these uids
        */

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
