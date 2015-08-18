package company.greatapp.moneycircle.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Contact;

/**
 * Created by Prateek on 08-08-2015.
 */
public class ManageCircleMemberListAdapter extends BaseAdapter {

    public static final String LOGTAG = "ManageCircleListAdapter";
    public static final int HOLDER_ID = 1;
    public static final int MEMBER_ID = 2;

    Context mContext;
    ArrayList<Contact> mMemberList;
    LayoutInflater mInflater;

    public ManageCircleMemberListAdapter(Context context, ArrayList<Contact> memberList) {
        Log.d(LOGTAG, "Constructor");
        mContext = context;
        mMemberList = memberList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.d(LOGTAG, "getCount");
        return mMemberList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d(LOGTAG, "getItem");
        return mMemberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d(LOGTAG, "getItem");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(LOGTAG, "getView");

        ViewHolder holder = null;
        if (convertView == null) {

            convertView = (View)mInflater.inflate(R.layout.row_circle_member,parent,false);
            holder = new ViewHolder();
            holder.memberImage = (ImageView)convertView.findViewById(R.id.ivMemberImageId);
            holder.memberName = (TextView)convertView.findViewById(R.id.tvMemberNameId);
            holder.deleteMember = (ImageView)convertView.findViewById(R.id.ivDeleteMemberIconId);
            convertView.setTag(R.string.holder_id,holder);      // The specified key should be an id declared in the resources of the application to ensure it is unique
                                                                //Keys identified as belonging to the Android framework or not associated with any package will cause an IllegalArgumentException to be thrown
        } else {

            holder = (ViewHolder)convertView.getTag(R.string.holder_id);
        }

        holder.memberName.setText(mMemberList.get(position).getTitle());
        holder.deleteMember.setTag(R.string.circle_member_id, position);
        /*holder.deleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"v :"+(Integer)v.getTag(R.string.circle_member_id),Toast.LENGTH_LONG).show();
            }
        });*/

        Log.d(LOGTAG, "getView end");
        return convertView;
    }

    public static class ViewHolder {
        public ImageView memberImage;
        public TextView memberName;
        public ImageView deleteMember;
    }
}
