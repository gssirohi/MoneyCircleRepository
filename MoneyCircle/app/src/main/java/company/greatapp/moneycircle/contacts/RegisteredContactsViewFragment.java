package company.greatapp.moneycircle.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Model;


/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class RegisteredContactsViewFragment extends Fragment {

    ListView mListView;
    private ContactManager mContactManager;
    ArrayList<Model> mRegisteredContactList = null;

    private static RegisteredContactsViewFragment mRegisteredContactsViewFragment = null;

    private RegisteredContactsViewFragment() {} // Empty Constructor

    private RegisteredContactsViewFragment(ContactManager contactManager) {
        mContactManager = contactManager;
    }

    public static final RegisteredContactsViewFragment getInstance(ContactManager contactManager) {
        if (mRegisteredContactsViewFragment == null) {
            mRegisteredContactsViewFragment = new RegisteredContactsViewFragment(contactManager);
        }
        return mRegisteredContactsViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Prateek", "[RegisteredContactsViewFragment] onCreateView");
        View view = inflater.inflate(R.layout.fragment_registered_contact_viewer, container, false);
            mListView = (ListView)view.findViewById(R.id.lvRegisteredContactViewId);
            Button bt_inviteContact = (Button)view.findViewById(R.id.btInviteContactId);

        if (mContactManager != null) {
            mContactManager = new ContactManager(getActivity().getBaseContext());
            mRegisteredContactList = mContactManager.getRegisteredContactList();
        }

        if (mRegisteredContactList == null) {
            mListView.setVisibility(View.GONE);
            bt_inviteContact.setVisibility(View.VISIBLE);
        } else {
            bt_inviteContact.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            ContactAdapter adapter = new ContactAdapter(getActivity(), mRegisteredContactList);
            mListView.setAdapter(adapter);
        }

        return view;
    }
}
