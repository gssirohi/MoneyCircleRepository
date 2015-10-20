package company.greatapp.moneycircle.adapters;

/**
 * Created by gyanendra.sirohi on 8/7/2015.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.NavDrawerItem;
import company.greatapp.moneycircle.tools.Tools;

public class NavDrawerListAdapter extends BaseAdapter {

    private static final String LOG_TAG = NavDrawerListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(LOG_TAG, "[NavDrawerListAdapter] getView position :" + position);

        ViewHolder holder = null;
        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.icon);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.txtCount = (TextView) convertView.findViewById(R.id.counter);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        holder.txtTitle.setText(navDrawerItems.get(position).getTitle());

        if (position == 3) { // Position 3 is for Notification
            int count = Tools.getUnSeenAndUnRespondedNotificationCount(context);
            if (count > 0) {
                navDrawerItems.get(position).setCounterVisibility(true);
                navDrawerItems.get(position).setCount(""+count);
                notifyDataSetChanged();         // if notifyDataSetChanged() is not called, then adapter is refrencing the previous data,
                                                // and it is not reflecting the update content.
            }
        }

        // displaying count
        // check whether it set visible or not
        if (navDrawerItems.get(position).getCounterVisibility()) {
            holder.txtCount.setVisibility(View.VISIBLE);
            holder.txtCount.setText(navDrawerItems.get(position).getCount());
        } else {
            // hide the counter view
            holder.txtCount.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtCount;
    }

}