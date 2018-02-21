package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.Arg;
import frc3824.rscout2018.R;

/**
 * A fragment used to display the data collect on this team during each of its matches
 */
public class TeamStatsMatchDataFragment extends Fragment
{
    int mTeamNumber;


    public void setTeamNumber(int teamNumber)
    {
        mTeamNumber = teamNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team_stats_match_data, null);

        return view;
    }
}
