package company.greatapp.moneycircle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ContactAndCircleTabAdapter extends FragmentStatePagerAdapter {

    private static final int TOTAL_TABS = 3;

    public ContactAndCircleTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new RegisteredContactsViewFragment();
            case 1:
                return new ContactsViewFragment();
            case 2:
                return new CirclesViewFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_TABS;
    }
}
