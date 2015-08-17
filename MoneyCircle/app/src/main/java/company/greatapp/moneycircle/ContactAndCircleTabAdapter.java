package company.greatapp.moneycircle;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import company.greatapp.moneycircle.circles.CirclesViewFragment;
import company.greatapp.moneycircle.contacts.ContactsViewFragment;
import company.greatapp.moneycircle.contacts.RegisteredContactsViewFragment;
import company.greatapp.moneycircle.manager.CircleManager;
import company.greatapp.moneycircle.manager.ContactManager;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ContactAndCircleTabAdapter extends FragmentStatePagerAdapter {

    private static final int TOTAL_TABS = 3;
    private ContactManager mContactManager = null;
    private CircleManager mCircleManager = null;

    public ContactAndCircleTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContactManager = new ContactManager(context);
        mCircleManager = new CircleManager(context);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new RegisteredContactsViewFragment(mContactManager);
            case 1:
                return new ContactsViewFragment(mContactManager);
            case 2:
                return new CirclesViewFragment(mCircleManager);
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_TABS;
    }
}
