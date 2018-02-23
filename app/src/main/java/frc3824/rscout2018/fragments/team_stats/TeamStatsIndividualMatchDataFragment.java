package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.views.powered_up.IndividualStart;

/**
 * Created by frc3824
 */
public class TeamStatsIndividualMatchDataFragment extends Fragment
{
    int mTeamNumber;
    int mMatchNumber;
    TeamMatchData mTeamMatchData;

    IndividualStart mIndividualStart;


    public void setTeamMatchNumber(int teamNumber, int matchNumber)
    {
        mTeamNumber = teamNumber;
        mMatchNumber = matchNumber;
        mTeamMatchData = Database.getInstance().getTeamMatchData(teamNumber, matchNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team_stats_individual_match_data, null);

        mIndividualStart = view.findViewById(R.id.start);

        update();

        return view;
    }

    public void update()
    {
        mIndividualStart.setTeamMatchData(mTeamMatchData);
    }
}
