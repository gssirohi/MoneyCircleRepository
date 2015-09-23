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

import company.greatapp.moneycircle.view.ContactItemView;
import company.greatapp.moneycircle.R;
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

            convertView = new ContactItemView(mContext,null,(Contact)mContactList.get(position));
            holder = new ViewHolder();
            holder.profilePic = (ImageView)convertView.findViewById(R.id.iv_contact);
            holder.contactName = (TextView)convertView.findViewById(R.id.tv_contact_name);

            convertView.setTag(holder);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView profilePic;
        TextView contactName;
    }
}
