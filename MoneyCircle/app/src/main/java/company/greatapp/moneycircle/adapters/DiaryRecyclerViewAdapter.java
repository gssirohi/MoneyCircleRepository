package company.greatapp.moneycircle.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.CardViewObject;

/**
 * Created by Prateek on 18-07-2015.
 */
public class DiaryRecyclerViewAdapter extends RecyclerView
        .Adapter<DiaryRecyclerViewAdapter
        .DataObjectHolder> {

    private static String LOG_TAG = "DiaryRecyclerViewAdapter";
    private ArrayList<CardViewObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView mTitle;
        TextView mMainInfo;
        TextView mSecondaryInfo;
        ImageView mTitleIcon;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitleId);
            mMainInfo = (TextView) itemView.findViewById(R.id.tvMainInfoId);
            mSecondaryInfo = (TextView) itemView.findViewById(R.id.tvSecondaryInfoId);
            mTitleIcon = (ImageView) itemView.findViewById(R.id.ivTitleIconId);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public DiaryRecyclerViewAdapter(ArrayList<CardViewObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row1, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        CardViewObject object = mDataset.get(position);
        holder.mTitle.setText(object.getTitle());
        holder.mMainInfo.setText(object.getMainInfo());
        holder.mSecondaryInfo.setText(object.getSecondaryInfo());
        holder.mTitleIcon.setImageResource(object.getIcon());
    }

    public void addItem(CardViewObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
