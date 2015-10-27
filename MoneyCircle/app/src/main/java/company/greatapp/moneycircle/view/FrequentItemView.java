package company.greatapp.moneycircle.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.FrequentItem;

/**
 * Created by Prateek on 24-10-2015.
 */
public class FrequentItemView extends CardView {

    private static final String LOG_TAG = "Prateek";//FrequentItemView.class.getSimpleName();

    private final Context mContext;
    private ViewGroup mViewGroup;
    private CategoryItemView mCategoryItemView;
    private TextView mTv_title;
    private TextView mTv_Currency;
    private EditText mEt_amount;
    private ImageView mIb_selectItem;

    private int mIndex;
    private FrequentItemViewCallback mCallback;

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
//                if (((ImageView)v).)
                mCallback.onItemSelected(mIndex);
            }
        });
        setFrequentItemViewCallback((FrequentItemViewCallback) mContext);
    }

    public void initView(final FrequentItem frequentItem) {

        if (frequentItem == null) {
            return;
        }

        mIndex = (Integer)getTag();
        Log.d(LOG_TAG, "FrequentItemView initView mIndex : " + mIndex);
        mTv_title.setText(frequentItem.getTitle());
        final String amount = Float.toString(frequentItem.getAmount());
        mEt_amount.setText(amount);
        mEt_amount.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!amount.equals(mEt_amount.getText().toString())) {
                    Toast.makeText(mContext, "Amount change", Toast.LENGTH_LONG).show();
                    Log.d(LOG_TAG, "FrequentItemView Amount change");
                }
            }
        });
        /*mEt_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(mContext, "beforeTextChanged", Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG, "FrequentItemView beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(mContext, "onTextChanged", Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG, "FrequentItemView onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(mContext, "afterTextChanged", Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG, "FrequentItemView afterTextChanged");
            }
        });*/

        mCategoryItemView.initView(frequentItem.getCategory());

    }

    private void setFrequentItemViewCallback(FrequentItemViewCallback callback) {
        mCallback = callback;
    }

    public interface FrequentItemViewCallback {
        void onItemSelected(int index);
    }
}
