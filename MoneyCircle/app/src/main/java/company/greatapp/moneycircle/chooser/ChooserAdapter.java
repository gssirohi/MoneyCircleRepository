package company.greatapp.moneycircle.chooser;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Contact;

/**
 * Created by gyanendra.s.sirohi on 6/26/2015.
 */
class ChooserAdapter extends BaseAdapter {

    private final int type;
    private SparseBooleanArray mSelectedItemsIds;
    private ArrayList<Model> objects;
    Context context;
    private static LayoutInflater inflater = null;
    private TextView tv_amount;

    public ChooserAdapter(Context context, int type, ArrayList<Model> objects) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.objects = objects;
        this.type = type;
        mSelectedItemsIds = new SparseBooleanArray();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objects.size();
    }

    @Override
    public Model getItem(int position) {
        // TODO Auto-generated method stub
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    private class ViewHolder {
        TextView tv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //---------------------------------------------------------
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.chooser_list_item_layout, null);
            // Locate the TextViews in listview_item.xml
            holder.tv = (TextView) convertView.findViewById(R.id.tv_chooser_item);
            holder.tv.setFocusable(false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Capture position and set to the TextViews
        holder.tv.setText(/*((Contact)*/ objects.get(position).getTitle()); //).getContactName());
        return convertView;
        //------------------------------------------------------------
    }
}

