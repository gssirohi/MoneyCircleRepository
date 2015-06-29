package company.greatapp.moneycircle.chooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.RegisteredContact;
import company.greatapp.moneycircle.split.SetSplitAmountActivity;
import company.greatapp.moneycircle.split.SetSplitAmountRowItemView;

/**
 * Created by gyanendra.s.sirohi on 6/26/2015.
 */
class ChooserAdapter extends BaseAdapter {

    private final int type;
    private ArrayList<Model> objects;
    Context context;
    private static LayoutInflater inflater = null;
    private TextView tv_amount;

    public ChooserAdapter(Context context, int type, ArrayList<Model> objects) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.objects = objects;
        this.type = type;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null)
            return new ChooserListItemView(context,null,inflater,type,objects.get(position));
        else
            return convertView;
    }
}

