package company.greatapp.moneycircle.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.manager.ContactManager;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ContactsViewFragment extends Fragment {

    private static ContactsViewFragment mContactsViewFragment;
    private ContactManager mContactManager = null;

    private ContactsViewFragment() {} // Empty constructor

    public ContactsViewFragment(ContactManager contactManager) {
            mContactManager = contactManager;
    }

    /*public static final ContactsViewFragment getInstance(ContactManager contactManager) {
        if (mContactsViewFragment == null) {
            mContactsViewFragment = new ContactsViewFragment(contactManager);
        }
        return mContactsViewFragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Prateek", "[ContactsViewFragment] onCreateView");
        View view = inflater.inflate(R.layout.fragment_contact_viewer, container, false);
        ListView listView = (ListView)view.findViewById(R.id.lvContactViewId);

        if (mContactManager == null) {
            mContactManager = new ContactManager(getActivity().getBaseContext());
        }
        ContactAdapter adapter = new ContactAdapter(getActivity(), mContactManager.getItemList());
        listView.setAdapter(adapter);
        Log.d("Prateek", "[ContactsViewFragment] onCreateView End");

        return view;
    }
}
