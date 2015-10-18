package company.greatapp.moneycircle.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.dialogs.TitleSelectionDialog;


/**
 * Created by gyanendra on 5/10/15.
 */
public class TitleSelectionView extends LinearLayout {

    private ViewGroup viewGroup;
    private TextView titleText;
    private final Context mContext;
    private String[] titleList;
    private int selectedIndex = -1;
    private String selectedTitle;
    private TitleSelectionDialog titleDialog;
    private boolean isDialogConfirmButtonRequired;
    private OnTitleSelectedListener onTitleSelectedListener;
    private String headerTitle = "Title";

    private boolean isDialogCancelButtonRequired;

    public boolean isDialogCancelButtonRequired() {
        return isDialogCancelButtonRequired;
    }

    public void setIsDialogCancelButtonRequired(boolean isDialogCancelButtonRequired) {
        this.isDialogCancelButtonRequired = isDialogCancelButtonRequired;
    }

    public TitleSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        initView();
    }

    public TitleSelectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle);

        mContext = context;
        initView();
        if(titleText != null) {

        }
    }

    public TitleSelectionView(Context context, AttributeSet attrs, String[] titles) {
        super(context, attrs);

        titleList = titles;
        mContext = context;
        initView();

    }

    public void setStyle(int styleResId) {

        try {
            int[] attrs = {android.R.attr.textSize, android.R.attr.textColor};
            TypedArray ta = mContext.obtainStyledAttributes(styleResId, attrs);
            float size = ta.getDimensionPixelSize(0, 3);
            int color = ta.getColor(1, Color.BLUE);
            ta.recycle();

            titleText.setTextColor(color);
            titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        } catch (Exception e) {

        }
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.title_selection_view_layout, this, true);
        titleText = (TextView)viewGroup.findViewById(R.id.text_title);

        selectedIndex = -1;
        selectedTitle = headerTitle;

        titleText.setText(selectedTitle);


        initSelectionDialog();
    }

    private void initSelectionDialog() {

        if(titleList != null) {

            viewGroup.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleViewClicked();
                }
            });


            titleDialog = new TitleSelectionDialog(mContext, titleList,headerTitle);
            titleDialog.setTitleSelectListener(new TitleSelectionDialog.TitleSelectedListener() {
                @Override
                public void onTitleSelected(String title, int position) {
                    setSelectedTitle(title, position);
                }
            });
            titleDialog.setConfirmationRequired(isDialogConfirmButtonRequired);
            titleDialog.setCancelButtonRequired(isDialogCancelButtonRequired);

        }

    }

    private void setSelectedTitle(String title, int position) {
        if(!TextUtils.isEmpty(title) && position >= 0) {
            selectedIndex = position;
            selectedTitle = title;
            titleText.setText(selectedTitle);
        } else {
            selectedIndex = -1;
            selectedTitle = headerTitle;
            titleText.setText(selectedTitle);
        }

        if(onTitleSelectedListener != null) {
            onTitleSelectedListener.onTitleSelected(selectedIndex, selectedTitle);
        }

        if(titleDialog != null) {
            titleDialog.setSelection(selectedIndex);
        }
    }

    private void handleViewClicked() {
        if(titleDialog == null) {
            if(selectedIndex >= 0) {
                titleDialog = new TitleSelectionDialog(mContext, titleList, selectedIndex,headerTitle);
            } else {
                titleDialog = new TitleSelectionDialog(mContext, titleList,headerTitle);
            }
        }
        if(titleDialog != null) {
            titleDialog.setSelection(selectedIndex);
            titleDialog.show();
        }

    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public String getSelectedTitle() {
        return selectedTitle;
    }

    public void setTitleList(String[] titles) {
        titleList = titles;
        initSelectionDialog();
    }

    public void setDialogConfirmButtonEnable(boolean enable) {
        if(titleDialog != null) {
            isDialogConfirmButtonRequired = enable;
            titleDialog.setConfirmationRequired(enable);
        }
    }

    public void setSelection(int selectedTitlePos) {
        if(titleList != null && titleList.length > selectedTitlePos) {
            setSelectedTitle(titleList[selectedTitlePos], selectedTitlePos);
        }
    }

    public void setOnTitleSelectedListner(OnTitleSelectedListener onTitleSelectedListener) {
        this.onTitleSelectedListener = onTitleSelectedListener;

    }

    public int getTitleCount() {
        if(titleList != null) {
            return titleList.length;
        } else {
            return 0;
        }
    }

    public String getTitleAt(int i) {
        if(titleList != null && titleList.length >= (i+1)) {
            return titleList[i];
        }
        return null;
    }

    public interface OnTitleSelectedListener {
        public void onTitleSelected(int position, String title);
    }

    public void setDialogHeaderTitle(String header) {
        this.headerTitle = header;
        if(titleDialog != null) {
            titleDialog.setDialogHeaderTitle(header);
        }
    }
}
