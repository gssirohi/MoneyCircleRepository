package company.greatapp.moneycircle.circles;

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
import company.greatapp.moneycircle.manager.CircleManager;
import company.greatapp.moneycircle.model.Circle;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class CirclesViewFragment extends Fragment{

    private CircleManager mCircleManager;
    ListView mListView;
    ArrayList<Circle> circles = null;

    public CirclesViewFragment() {}     // Empty Constructor

    public CirclesViewFragment(CircleManager manager) {
        mCircleManager = manager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Prateek", "[CirclesViewFragment] onCreateView");
        View view = inflater.inflate(R.layout.fragment_circle_viewer, container, false);
            Button createCircleButton = (Button)view.findViewById(R.id.btCreateNewCircleId);
            mListView = (ListView)view.findViewById(R.id.lvCircleViewId);

        /*if (circles == null) {
            mListView.setVisibility(View.GONE);
            createCircleButton.setVisibility(View.VISIBLE);
        } else {*/
            createCircleButton.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            CirclesAdapter adapter = new CirclesAdapter(getActivity(), mCircleManager.getItemList());
            mListView.setAdapter(adapter);
        //}
        return view;
    }
}