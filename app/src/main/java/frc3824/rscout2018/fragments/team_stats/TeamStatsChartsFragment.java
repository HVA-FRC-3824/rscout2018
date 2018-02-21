package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.DataModelOnUpdate;
import frc3824.rscout2018.database.data_models.Team;
import frc3824.rscout2018.views.powered_up.StartLocationViewInner;

/**
 * A fragment for displaying chart showing the performance of a team
 */
public class TeamStatsChartsFragment extends Fragment implements DataModelOnUpdate
{
    int mTeamNumber;

    Team mTeam = null;

    StartLocationViewInner mStartLocationViewInner = null;

    public void setTeamNumber(int teamNumber)
    {
        mTeamNumber = teamNumber;
        mTeam = new Team(mTeamNumber);
        if(mStartLocationViewInner != null)
        {
            update();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team_stats_charts, null);

        mStartLocationViewInner = view.findViewById(R.id.start_location_heatmap);

        if(mTeam != null)
        {
            update();
        }

        return view;
    }

    public void update()
    {
        mStartLocationViewInner.setData(new ArrayList<>(mTeam.getMatches().values()));
    }

}
