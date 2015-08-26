package company.greatapp.moneycircle.chooser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;

/**
 * Created by gyanendra.s.sirohi on 6/26/2015.
 */
class ChooserAdapter extends BaseAdapter {

    private final String LOGTAG = getClass().getSimpleName().toString();
//    private SparseBooleanArray mSelectedItemsIds;
    private ArrayList<Model> objects;
    private ArrayList<Model> searchedObjects;
    Context context;
    private static LayoutInflater inflater = null;
    private int mMode;
    private ArrayList<String> mSelectedItems;

    public ChooserAdapter(Context context, ArrayList<Model> objects, int mode) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.objects = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMode = mode;
        searchedObjects = new ArrayList<Model>(objects.size());
        searchedObjects.addAll(objects);
        mSelectedItems = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
//        return objects.size();
        return searchedObjects.size();
    }

    @Override
    public Model getItem(int position) {
        // TODO Auto-generated method stub
//        return objects.get(position);
        return searchedObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void toggleSelection(int position) {
        //selectView(position, !mSelectedItemsIds.get(position));
        selectItem(position);
    }

    public void removeSelection() {
        notifyDataSetChanged();
    }

    public void selectItem(int position) {
            String uid = searchedObjects.get(position).getUID();
//            Log.d(LOGTAG,"selectItem uid :"+uid);
            Log.d(LOGTAG, "selectItem title :" + searchedObjects.get(position).getTitle());
        if (mMode == ListView.CHOICE_MODE_MULTIPLE) {
            if (mSelectedItems.contains(uid)){
                Log.d(LOGTAG,"selectItem contains uid :"+uid);
                mSelectedItems.remove(uid);
            } else {
                Log.d(LOGTAG,"selectItem add uid :"+uid);
                mSelectedItems.add(uid);
            }
        } else {
            mSelectedItems.clear();
            mSelectedItems.add(uid);
        }

    }

    public ArrayList<String> getSelectedItems() {
        return mSelectedItems;
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }


    private static class ViewHolder {
        TextView tv;
        CheckedTextView checkedTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //---------------------------------------------------------
        /*convertView = inflater.inflate(R.layout.multiple_chooser_list_item_layout, null);
        CheckedTextView view = (CheckedTextView) convertView.findViewById(R.id.ctv_chooser_item);
        view.setText(objects.get(position).getTitle());
        if (mMode != ListView.CHOICE_MODE_MULTIPLE) {
            view.setCheckMarkDrawable(null);
        }*/
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (mMode == ListView.CHOICE_MODE_MULTIPLE) {
                convertView = inflater.inflate(R.layout.multiple_chooser_list_item_layout, null);
                holder.checkedTextView = (CheckedTextView)convertView.findViewById(R.id.ctv_chooser_item);
            } else {
                convertView = inflater.inflate(R.layout.single_chooser_list_item_layout, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_chooser_item);
            }
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (holder.checkedTextView != null) {
            Model model = searchedObjects.get(position);
            holder.checkedTextView.setText(model.getTitle());
            Log.d(LOGTAG, "getView title :" + model.getTitle());
            boolean value = mSelectedItems.contains(model.getUID());
       /*     if (mSelectedItems.contains(model.getUID())) {
                Log.d(LOGTAG, "getView checked uid :" + model.getUID());
//                holder.checkedTextView.setChecked(true);
//                ((ListView)parent).setItemChecked(position,true);
            } else {
                Log.d(LOGTAG, "getView not checked uid :" + model.getUID());
//                holder.checkedTextView.setChecked(false);
            }*/
            ((ListView)parent).setItemChecked(position,value);
        } else {
            holder.tv.setText(searchedObjects.get(position).getTitle());;
        }

        // Capture position and set to the TextViews
        /*CheckedTextView view = (CheckedTextView) convertView.findViewById(android.R.id.text1);
        view.setText(objects.get(position).getTitle());*/
        /*final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.single_chooser_list_item_layout, null);
            // Locate the TextViews in listview_item.xml
            holder.tv = (TextView) convertView.findViewById(R.id.tv_chooser_item);
            holder.checkedTextView = (CheckedTextView)convertView.findViewById(R.id.ctv_chooser_item);
            if (mMode == ListView.CHOICE_MODE_MULTIPLE) {
                holder.tv.setVisibility(View.GONE);
                holder.checkedTextView.setVisibility(View.VISIBLE);
            } else {
                holder.tv.setVisibility(View.VISIBLE);
                holder.checkedTextView.setVisibility(View.GONE);
            }
            //holder.tv.setFocusable(false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String title = objects.get(position).getTitle();
        if (holder.checkedTextView.getVisibility() == View.VISIBLE) {
            holder.checkedTextView.setText(title);
            *//*holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ChooserAdapter", "onClick");
                }
            });*//*
        } else {
            holder.tv.setText(title);
        }*/
        return convertView;
        //------------------------------------------------------------
    }


    public void filter(String charText) {
        charText = charText.toLowerCase();
        searchedObjects.clear();
        if (charText.length() == 0) {
            searchedObjects.addAll(objects);
        } else {
            for (Model model : objects) {
                if (model.getTitle().toLowerCase().contains(charText)) {
                    searchedObjects.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }


//    public void selectView(int position, boolean value) {
//        if (value)
//            mSelectedItemsIds.put(position, value);
//        else
//            mSelectedItemsIds.delete(position);
//        notifyDataSetChanged();
//    }
//
//    public int getSelectedCount() {
//        return mSelectedItemsIds.size();
//    }

//    public SparseBooleanArray getSelectedIds() {
//        return mSelectedItemsIds;
//    }
}

