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
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;


/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class RegisteredContactsViewFragment extends Fragment {

    ListView mListView;
    private ContactManager mContactManager;
    ArrayList<Model> mRegisteredContactList = null;

    public RegisteredContactsViewFragment() {} // Empty Constructor

    public RegisteredContactsViewFragment(ContactManager contactManager) {
        mContactManager = contactManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Prateek", "[RegisteredContactsViewFragment] onCreateView");
        View view = inflater.inflate(R.layout.fragment_registered_contact_viewer, container, false);
            mListView = (ListView)view.findViewById(R.id.lvRegisteredContactViewId);
            Button createCircleButton = (Button)view.findViewById(R.id.btInviteContactId);

        if (mContactManager == null) {
            mContactManager = new ContactManager(getActivity().getBaseContext());
            mRegisteredContactList = mContactManager.getRegisteredContactList();
        }

        if (mRegisteredContactList == null) {
            mListView.setVisibility(View.GONE);
            createCircleButton.setVisibility(View.VISIBLE);
        } else {
            createCircleButton.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            ContactAdapter adapter = new ContactAdapter(getActivity(), mContactManager.getItemList());
            mListView.setAdapter(adapter);
        }

        return view;
    }
}
