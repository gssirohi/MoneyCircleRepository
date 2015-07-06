package company.greatapp.moneycircle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Prateek on 30-06-2015.
 */
public class TabFragment extends Fragment implements View.OnClickListener{

    private String mTabType = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sorted_categorized_view, container, false);

        ImageView previousIcon = (ImageView)view.findViewById(R.id.ivPreviousNavigationId);
        previousIcon.setOnClickListener(this);
        ImageView nextIcon = (ImageView)view.findViewById(R.id.ivNextNavigationId);
        nextIcon.setOnClickListener(this);

        getTypeOfTab();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPreviousNavigationId :
                Toast.makeText(getActivity(), "Previous "+mTabType, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivNextNavigationId :
                Toast.makeText(getActivity(), "Next "+mTabType, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void getTypeOfTab() {
        int tabType = getArguments().getInt("Tab Type");
        switch (tabType) {
            case 1:
                mTabType = "Daily";
                break;
            case 2:
                mTabType = "Week";
                break;
            case 3:
                mTabType = "Month";
                break;
            case 4:
                mTabType = "Year";
                break;
            default:
                break;
        }
    }
}
