package company.greatapp.moneycircle.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.split.SetSplitAmountActivity;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 6/30/2015.
 */
public class TagItemView extends LinearLayout {

    private final ViewGroup parent;
    private Model model = null;
    private String title;
    private RemoveTagListener listener;
    private ViewGroup viewGroup;

    public TagItemView(Context context,ViewGroup parent, Model model, boolean isCancelable) {
        super(context);
        this.parent = parent;
        this.model = model;
        if(model != null) {
            int type = model.getModelType();
            this.title = model.getTitle();
        }
        init(context, isCancelable);
    }

    public TagItemView(Context context,ViewGroup parent, String item, boolean isCancelable) {
        super(context);
        this.parent = parent;
        this.title = item;
        if(item.equals("SPLIT")) {
            int color = Tools.getModelColor(getContext(),Model.MODEL_TYPE_SPLIT);
            setColor(color);
        }
        init(context, isCancelable);
    }

    private void setColor(int color) {
        if(viewGroup == null) return;
        GradientDrawable bgDrawable = (GradientDrawable)viewGroup.getBackground();
      //  int colors[] = {color,R.color.app_lightest};
        if(bgDrawable != null)bgDrawable.setColor(color);
    }
    private void init(Context context, boolean isCancelable) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            viewGroup = (ViewGroup)inflater.inflate(R.layout.tag_item_layout, this, true);
            Button b = (Button)viewGroup.findViewById(R.id.b_tag_item);
            TextView divider = (TextView)viewGroup.findViewById(R.id.divider);
            ImageButton ib_cancel = (ImageButton)viewGroup.findViewById(R.id.ib_tag_cancel);
            ib_cancel.setVisibility(isCancelable ? View.VISIBLE : View.GONE);
            divider.setVisibility(isCancelable ? View.VISIBLE : View.GONE);
            ib_cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleCancel(v);
                }
            });
            b.setText(this.title);
            b.setOnClickListener(getListener(context));

            if(model != null) {
                int color = Tools.getModelColor(getContext(),model.getModelType());
                setColor(color);
            }
        }
    }

    private OnClickListener getListener(final Context context) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getModel() != null)
                    Toast.makeText(context,""+getModel().getTitle()+" CLICKED",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context,""+getTitle()+" CLICKED",Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void setRemoveTagListener(RemoveTagListener listener){
        this.listener = listener;
    }
    private void handleCancel(View view) {
        if(parent == null) return;
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
