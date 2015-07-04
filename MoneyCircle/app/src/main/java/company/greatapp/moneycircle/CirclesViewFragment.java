package company.greatapp.moneycircle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by prateek02.arora on 30-06-2015.
 */
public class CirclesViewFragment extends Fragment{

    ListView mListView;
    String[] circles = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_circle_viewer, container, false);

        if (circles == null) {
            mListView = (ListView)view.findViewById(R.id.lvCircleViewId);
            mListView.setVisibility(View.GONE);
        } else {
            Button createCircleButton = (Button)view.findViewById(R.id.btCreateNewCircleId);
            createCircleButton.setVisibility(View.GONE);
        }
        return view;
    }
}
