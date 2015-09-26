package company.greatapp.moneycircle.circles;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import company.greatapp.moneycircle.ManageCircleActivity;
import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.manager.ContactManager;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class CirclesViewFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static CirclesViewFragment mCircleViewFragment = null;
    private final String LOGTAG = getClass().getSimpleName().toString();

    private ContactManager mContactManager;
    ListView mListView;

    private static final int CIRCLE_LOADER_ID = 3;

    CirclesAdapter mCircleAdapter = null;

    private CirclesViewFragment() {}     // Empty Constructor

    public CirclesViewFragment(ContactManager manager) {
        mContactManager = manager;
    }

    /*public static final CirclesViewFragment getInstance(ContactManager contactManager) {
        if (mCircleViewFragment == null) {
            mCircleViewFragment = new CirclesViewFragment(contactManager);
        }
        return mCircleViewFragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(LOGTAG, "[CirclesViewFragment] onCreateView");
        View view = inflater.inflate(R.layout.fragment_circle_viewer, container, false);
        Button createCircleButton = (Button) view.findViewById(R.id.btCreateNewCircleId);
        createCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCircleDialog();
            }
        });
        mListView = (ListView) view.findViewById(R.id.lvCircleViewId);

        mListView.setVisibility(View.VISIBLE);
        mCircleAdapter = new CirclesAdapter(getActivity(), null, true, mContactManager);
        mListView.setAdapter(mCircleAdapter);

        getLoaderManager().initLoader(CIRCLE_LOADER_ID, null, this);

        return view;
    }

    public void createNewCircleDialog() {

        Intent intent = new Intent(getActivity(), ManageCircleActivity.class);
        startActivity(intent);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == CIRCLE_LOADER_ID) {

            return new CursorLoader(getActivity(), DB.CIRCLE_TABLE_URI, DB.CIRCLE_TABLE_PROJECTION, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == CIRCLE_LOADER_ID) {
            if (data == null) {
                return;
            }

            mCircleAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

        if (loader.getId() == CIRCLE_LOADER_ID) {
            mCircleAdapter.swapCursor(null);
        }

    }
}
