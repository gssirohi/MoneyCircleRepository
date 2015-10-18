package company.greatapp.moneycircle.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import company.greatapp.moneycircle.R;


/**
 * Created by root on 26/9/15.
 */
public class TitleSelectionAdapter extends BaseAdapter {

    private Context context;
    private String[] titleList;
    private LayoutInflater layoutInflater;
    private Dialog dialog;
    private int selectedIndex = -1;

    public TitleSelectionAdapter(Context context, Dialog dialog, String[] titleList) {
        this.titleList = titleList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dialog = dialog;
    }


    @Override
    public int getCount() {
        return titleList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = (View) layoutInflater.inflate(R.layout.title_dialog_item_layout, null);
            holder.radioButtonTitle = (RadioButton) view.findViewById(R.id.radioButton);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        try {
            if(selectedIndex == position) {
                holder.radioButtonTitle.setText(titleList[position]);
                holder.radioButtonTitle.setChecked(true);
            } else {
                holder.radioButtonTitle.setText(titleList[position]);
                holder.radioButtonTitle.setChecked(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void selectItemAtIndex(int index) {
        selectedIndex = index;
        super.notifyDataSetChanged();
    }

    public class Holder {
        RadioButton radioButtonTitle;
    }
}
