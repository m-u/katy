package ca.cloudsense.automation.katy.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.cloudsense.automation.katy.MainActivity;
import ca.cloudsense.automation.katy.R;

/**
 * Created by muccello on 2014-10-01. This is the fragment that displays when
 * a selection from the NavigationDrawer is chosen.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        int res;
        switch (sectionNumber) {
            case 4:
                //View rootView = inflater.inflate(R.layout.fragment_my, container, false);
                res = R.layout.fragment_my2;
                break;
            default:
                res = R.layout.fragment_my;
                break;
        }
        return inflater.inflate(res/*R.layout.fragment_my*/, container, false);
        //return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
