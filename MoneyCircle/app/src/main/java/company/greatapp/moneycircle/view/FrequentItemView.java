package company.greatapp.moneycircle.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.FrequentItem;

/**
 * Created by Prateek on 24-10-2015.
 */
public class FrequentItemView extends CardView {

    private final Context mContext;
    private ViewGroup mViewGroup;
    private CategoryItemView mCategoryItemView;
    private TextView mTv_title;
    private TextView mTv_Currency;
    private EditText mEt_amount;
    private ImageView mIb_selectItem;

    public FrequentItemView(Context context) {
        super(context);
        mContext = context;
        inflateView();
    }

    public FrequentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflateView();
    }

    public FrequentItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflateView();
    }

    public void inflateView() {

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewGroup = (ViewGroup)inflater.inflate(R.layout.frequent_item_row_layout, this, true);

        FrameLayout fl_categoryView = (FrameLayout)mViewGroup.findViewById(R.id.fl_categoryView);
        mCategoryItemView = new CategoryItemView(mContext, null);
        fl_categoryView.addView(mCategoryItemView);

        mTv_title = (TextView)mViewGroup.findViewById(R.id.tv_itemTitle);

        mTv_Currency = (TextView)mViewGroup.findViewById(R.id.tv_currencyType);

        mEt_amount = (EditText)mViewGroup.findViewById(R.id.et_amount);

        mIb_selectItem = (ImageView)mViewGroup.findViewById(R.id.ib_select_item);
        mIb_selectItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ImageView)v).)
            }
        });
    }

    public void initView(FrequentItem frequentItem) {

        if (frequentItem == null) {
            return;
        }

        mTv_title.setText(frequentItem.getTitle());
        mEt_amount.setText(Float.toString(frequentItem.getAmount()));
        mCategoryItemView.initView(frequentItem.getCategory());

    }
}
