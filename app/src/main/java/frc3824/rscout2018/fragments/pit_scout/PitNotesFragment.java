package frc3824.rscout2018.fragments.pit_scout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frc3824.rscout2018.R;
import frc3824.rscout2018.Fragments.Fragment;
import frc3824.rscout2018.Utilities.Utilities;



public class PitNotesFragment extends Fragment{
    private final static String TAG = "Pit Notes Fragment";

    public PitNotesFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pit_notes, container, attachToRoot: false);
        if(mValueMap != null)
        {
            restoreContentsFromMap(mValueMap, (ViewGroup)view);
        }

        Utilities.setupUi(getActivity(), view);

        return view;
    }

}
