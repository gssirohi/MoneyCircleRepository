package company.greatapp.moneycircle.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.split.SetSplitAmountActivity;

/**
 * Created by gyanendra.sirohi on 6/30/2015.
 */
public class TagItemView extends LinearLayout {

    private final ViewGroup parent;
    private Model model = null;
    private final String title;
    private RemoveTagListener listener;

    public TagItemView(Context context,ViewGroup parent, Model model, boolean isCancelable) {
        super(context);
        this.parent = parent;
        this.model = model;
        int type = model.getModelType();
        this.title = model.getTitle();
        init(context, isCancelable);
    }

    public TagItemView(Context context,ViewGroup parent, String item, boolean isCancelable) {
        super(context);
        this.parent = parent;
        this.title = item;
        init(context, isCancelable);
    }

    private void init(Context context, boolean isCancelable) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.tag_item_layout, this, true);
            Button b = (Button)viewGroup.findViewById(R.id.b_tag_item);
            ImageButton ib_cancel = (ImageButton)viewGroup.findViewById(R.id.ib_tag_cancel);
            ib_cancel.setVisibility(isCancelable ? View.VISIBLE : View.GONE);
            ib_cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleCancel(v);
                }
            });
            b.setText(this.title);
        }
    }

    public void setRemoveTagListener(RemoveTagListener listener){
        this.listener = listener;
    }
    private void handleCancel(View view) {
        parent.removeView(this);
        if(listener != null)
        listener.OnTagRemoved(this);
    }

    public Model getModel(){
        return this.model;
    }
    public String getTitle(){
        return this.title;
    }

    public interface RemoveTagListener{
        public abstract void OnTagRemoved(TagItemView view);
    }
}
