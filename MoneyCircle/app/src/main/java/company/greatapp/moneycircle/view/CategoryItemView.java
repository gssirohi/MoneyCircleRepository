package company.greatapp.moneycircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.Category;

/**
 * Created by gyanendra on 17/10/15.
 */
public class CategoryItemView extends LinearLayout {
    private Context context;
    private ViewGroup viewGroup;
    private TextView tv_name;
    private TextView tv_amount;
    private ImageView iv_icon;
    private Category mCategory;

    public CategoryItemView(Context context) {
        super(context);
        this.context = context;
        initInflator();
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initInflator();
    }

    public CategoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initInflator();
    }

    private void initInflator() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.category_list_item_layout, this, true);

        tv_name = (TextView)viewGroup.findViewById(R.id.tv_cat_item_name);
        tv_amount = (TextView)viewGroup.findViewById(R.id.tv_cat_item_amount);
        iv_icon = (ImageView)viewGroup.findViewById(R.id.iv_cat_item);
    }

    public void initView(Category category) {
        mCategory = category;
        tv_name.setText(category.getTitle());
        tv_amount.setText(category.getSpentAmountOnThis()+"");
        iv_icon.setImageResource(category.getCategoryIconResId());
    }
}
