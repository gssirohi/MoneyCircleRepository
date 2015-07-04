package company.greatapp.moneycircle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import company.greatapp.moneycircle.manager.ContactManager;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class ContactsViewFragment extends Fragment {

    String[] names = {"Iron Man", "Hulk", "Thor", "Captain America", "Clause"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_viewer, container, false);
        ListView listView = (ListView)view.findViewById(R.id.lvContactViewId);
        ContactManager cm = new ContactManager(getActivity().getBaseContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, cm.getItemNameList());
        listView.setAdapter(adapter);

        return view;
    }
}
