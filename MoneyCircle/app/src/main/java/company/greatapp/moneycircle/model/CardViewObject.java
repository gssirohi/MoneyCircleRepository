package company.greatapp.moneycircle.model;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;

/**
 * Created by Prateek on 18-07-2015.
 */
public class CardViewObject {

    private String mTitle;
    private String mMainInfo;
    private String mSecondaryInfo;
    private int mIcon;
    private int mPrimaryColor;

    /*CardViewObject(String text1, String text2){
        mTitle = text1;
        mMainInfo = text2;
    }*/

    public CardViewObject(int type, String mainInfo, String sencondaryInfo) {
        if (type == Model.MODEL_TYPE_INCOME) {
            setTitle("Income");
            setIcon(R.drawable.ic_income);
        } else if(type == Model.MODEL_TYPE_EXPENSE){
            setTitle("Expense");
            setIcon(R.drawable.ic_expense);
        } else if (type == Model.MODEL_TYPE_BORROW) {
            setTitle("Borrowed");
            setIcon(R.drawable.ic_borrow);
        } else if (type == Model.MODEL_TYPE_LENT) {
            setTitle("Lended");
            setIcon(R.drawable.ic_lent);
        } else {
            setTitle("Split");
            setIcon(R.drawable.ic_split);
        }
        mMainInfo = mainInfo;
        mSecondaryInfo = sencondaryInfo;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getMainInfo() {
        return mMainInfo;
    }

    public void setMainInfo(String mainInfo) {
        this.mMainInfo = mainInfo;
    }

    public String getSecondaryInfo() {
        return mSecondaryInfo;
    }

    public void setSecondaryInfo(String sencondaryInfo) {
        this.mSecondaryInfo = sencondaryInfo;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setPrimaryColor(int primaryColor) {
        mPrimaryColor = primaryColor;
    }

    public int getPrimaryColor() {
        return mPrimaryColor;
    }
}
