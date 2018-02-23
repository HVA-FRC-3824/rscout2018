package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.DataModelOnUpdate;
import frc3824.rscout2018.database.data_models.Team;
import frc3824.rscout2018.views.powered_up.CubesView;
import frc3824.rscout2018.views.powered_up.StartLocationView;
import frc3824.rscout2018.views.powered_up.StartView;

/**
 * A fragment for displaying chart showing the performance of a team
 */
public class TeamStatsChartsFragment extends Fragment
{
    int mTeamNumber = -1;

    StartView mStartView = null;
    CubesView mCubesView = null;

    public void setTeamNumber(int teamNumber)
    {
        mTeamNumber = teamNumber;
        if(mStartView != null)
        {
            new UpdateTask().execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team_stats_charts, null);

        mStartView = view.findViewById(R.id.start);
        mCubesView = view.findViewById(R.id.cubes);

        if(mTeamNumber != -1)
        {
            new UpdateTask().execute();
        }

        return view;
    }

    private class UpdateTask extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] objects)
        {
            if(mStartView != null && mTeamNumber != -1)
            {
                Team team = new Team(mTeamNumber);
                mStartView.setTeam(team);
                mCubesView.setTeam(team);
            }

            return null;
        }
    }
}
