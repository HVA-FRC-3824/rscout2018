package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.Arg;
import frc3824.rscout2018.R;

/**
 * @class TeamStatsNotesFragment
 * @brief A fragment for displaying the notes taken during a team's matches about its performance
 */
public class TeamStatsNotesFragment extends Fragment
{
    int mTeamNumber;

    public void setTeamNumber(int teamNumber)
    {
        mTeamNumber = teamNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team_stats_notes, null);

        return view;
    }
}
