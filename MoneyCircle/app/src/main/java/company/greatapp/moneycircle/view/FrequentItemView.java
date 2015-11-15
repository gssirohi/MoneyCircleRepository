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

    private static final String LOG_TAG = FrequentItemView.class.getSimpleName();

    private final Context mContext;
    private ViewGroup mViewGroup;
    private CategoryItemView mCategoryItemView;
    private TextView mTv_title;
    private TextView mTv_Currency;
    private EditText mEt_amount;
    private ImageView mIv_selectItem;
    private ImageView mIv_deleteItem;

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

        mIv_selectItem = (ImageView)mViewGroup.findViewById(R.id.iv_select_item);
        mIv_selectItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemSelected(mIndex);
            }
        });
        mIv_deleteItem = (ImageView)mViewGroup.findViewById(R.id.iv_delete_item);
        mIv_deleteItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemDeleted(mIndex);
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
        mEt_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(LOG_TAG, "FrequentItemView beforeTextChanged mIndex["+mIndex+"] s["+s+"] start["+start+"] after["+after+"] count["+count+"]");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(LOG_TAG, "FrequentItemView onTextChanged mIndex["+mIndex+"] s["+s+"] start["+start+"] before["+before+"] count["+count+"]");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(LOG_TAG, "FrequentItemView afterTextChanged mIndex["+mIndex+"] editable["+s+"]");
                float amountInEditText = 0f;
                if (s.toString().length() > 0) {
                    amountInEditText = Float.parseFloat(s.toString());
                }
                Log.d(LOG_TAG, "FrequentItemView afterTextChanged mIndex["+mIndex+"] amountInEditText["+amountInEditText+"]");
                if (frequentItem.getAmount() != amountInEditText) {
                    mCallback.onAmountChange(mIndex, amountInEditText);
                    Log.d(LOG_TAG, "FrequentItemView Amount change");
                }

                if (amountInEditText == 0 && frequentItem.isSelected()) {
                    Toast.makeText(mContext, "Please add amount to select item", Toast.LENGTH_SHORT).show();
                    mCallback.onItemSelected(mIndex);
                }
            }
        });

        if (frequentItem.isSelected()) {
            mIv_selectItem.setImageResource(R.drawable.ic_ok_selected);
        } else {
            mIv_selectItem.setImageResource(R.drawable.ic_ok_unselected);
        }

        mCategoryItemView.initView(frequentItem.getCategory());

    }

    private void setFrequentItemViewCallback(FrequentItemViewCallback callback) {
        mCallback = callback;
    }

    public interface FrequentItemViewCallback {
        void onItemSelected(int index);
        void onItemDeleted(int index);
        void onAmountChange(int index, float amount);
    }
}
