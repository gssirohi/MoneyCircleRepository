package company.greatapp.moneycircle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import company.greatapp.moneycircle.model.Contact;


/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class RegisteredContactsViewFragment extends Fragment {

    ListView mListView;
    Contact[] mRegisteredContactList = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registered_contact_viewer, container, false);

        if (mRegisteredContactList == null) {
            mListView = (ListView)view.findViewById(R.id.lvRegisteredContactViewId);
            mListView.setVisibility(View.GONE);
        } else {
            Button createCircleButton = (Button)view.findViewById(R.id.btInviteContactId);
            createCircleButton.setVisibility(View.GONE);
        }
        return view;
    }
}
