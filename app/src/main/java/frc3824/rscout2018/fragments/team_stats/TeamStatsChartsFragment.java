package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.Arg;
import frc3824.rscout2018.R;

/**
 * A fragment for displaying chart showing the performance of a team
 */
public class TeamStatsChartsFragment extends Fragment
{
    @Arg
    int mTeamNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team_stats_charts, container);

        return view;
    }
}
