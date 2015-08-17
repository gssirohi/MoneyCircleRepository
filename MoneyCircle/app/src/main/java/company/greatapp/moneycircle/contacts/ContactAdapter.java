package company.greatapp.moneycircle.contacts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by Prateek on 11-07-2015.
 */
public class ContactAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Model> mContactList;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, ArrayList<Model> contactList) {
        mContext = context;
        mContactList = contactList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Model getItem(int position) {
        return mContactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("Prateek", "[ContactAdapter] getView");
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.contact_item_view, parent, false);
            holder = new ViewHolder();
            holder.profilePic = (ImageView)convertView.findViewById(R.id.ivProfilePicContactItemId);
            holder.contactName = (TextView)convertView.findViewById(R.id.tvContactNameId);
            holder.inviteIcon = (ImageView)convertView.findViewById(R.id.ivInviteIconId);
            holder.chatIcon = (ImageView)convertView.findViewById(R.id.ivInviteIconId);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Contact contact = (Contact)mContactList.get(position);
        holder.contactName.setText(contact.getTitle());
        if (contact.getRegistered() == C.REGISTERED_ON_MONEY_CIRCLE) {
            holder.inviteIcon.setVisibility(View.GONE);
            holder.chatIcon.setVisibility(View.VISIBLE);
        } else {
            holder.chatIcon.setVisibility(View.GONE);
            holder.inviteIcon.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView profilePic;
        TextView contactName;
        ImageView inviteIcon;
        ImageView chatIcon;
    }
}
