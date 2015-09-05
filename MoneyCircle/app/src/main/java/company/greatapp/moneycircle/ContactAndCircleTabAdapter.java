package company.greatapp.moneycircle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import company.greatapp.moneycircle.manager.ContactManager;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ContactAndCircleTabAdapter extends FragmentStatePagerAdapter {

    private static final int TOTAL_TABS = 3;
    private ContactManager mContactManager = null;
    private List<Fragment> mFragmentList = null;

    public ContactAndCircleTabAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {

        if (mFragmentList != null) {
            return mFragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_TABS;
    }

    /**
     * This Function is needed if we use PagerTabStrip or PagerTitleStrip
     * @param position
     * @return*//*

    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;

        switch (position) {
            case 0:
                title = "Registered Contact";
                break;
            case 1:
                title = "Contacts";
                break;
            case 2:
                title = "Circles";
                break;
            default:break;
        }

        return title;
    }*/
}
