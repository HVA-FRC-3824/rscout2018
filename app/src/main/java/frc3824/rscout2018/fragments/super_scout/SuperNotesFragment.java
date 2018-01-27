package frc3824.rscout2018.fragments.super_scout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frc3824.rscout2018.R;
import frc3824.rscout2018.utilities.Utilities;

/**
 * @author frc3824
 * Created: 8/16/16
 */
public class SuperNotesFragment extends Fragment {

    private final static String TAG = "SuperNotesFragment";

    public SuperNotesFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_super_notes, container, false);

        Utilities.setupUi(getActivity(), view);

        return view;
    }

}
