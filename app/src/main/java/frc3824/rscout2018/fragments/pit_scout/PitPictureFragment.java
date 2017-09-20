package frc3824.rscout2018.fragments.pit_scout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frc3824.rscout2018.data_models.TeamPitData;

/**
 * @class PitPictureFragment
 * @brief Fragment for taking pictures of a team's robot
 */
public class PitPictureFragment extends Fragment
{
    TeamPitData mTeamPitData;

    public void setData(TeamPitData teamPitData)
    {
        mTeamPitData = teamPitData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pit_picture, container, false);

        return view;
    }
}
