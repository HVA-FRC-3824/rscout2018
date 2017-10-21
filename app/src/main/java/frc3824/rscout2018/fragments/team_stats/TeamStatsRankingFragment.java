package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.Arg;
import frc3824.rscout2018.R;

/**
 * A fragment that shows how a team's performance rankings in several categories
 */
public class TeamStatsRankingFragment extends Fragment
{
    @Arg
    int mTeamNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team_stats_ranking, container);

        return view;
    }
}
