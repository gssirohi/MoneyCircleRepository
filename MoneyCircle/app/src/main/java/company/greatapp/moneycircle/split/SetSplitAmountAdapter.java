package company.greatapp.moneycircle.split;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import company.greatapp.moneycircle.model.Participant;

/**
 * Created by gyanendra.s.sirohi on 6/26/2015.
 */
class SetSplitAmountAdapter extends BaseAdapter {

    private final ArrayList<Participant> participants;
    private final int mode;
    Context context;
    private static LayoutInflater inflater = null;
    private TextView tv_amount;

    public SetSplitAmountAdapter(Context context, ArrayList<Participant> participants, int mode) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mode = mode;
        this.participants = participants;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return participants.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return participants.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
            return  new SetSplitAmountRowItemView(context,null,inflater,participants.get(position),mode);
    }
}

