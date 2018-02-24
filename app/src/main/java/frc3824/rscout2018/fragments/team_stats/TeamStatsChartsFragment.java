package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.DataModelOnUpdate;
import frc3824.rscout2018.database.data_models.Team;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.views.powered_up.ClimbView;
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
    ClimbView mClimbView = null;
    TextView mFouls;
    TextView mTechFouls;
    View mView;

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
        mView= inflater.inflate(R.layout.fragment_team_stats_charts, null);

        mStartView = mView.findViewById(R.id.start);
        mCubesView = mView.findViewById(R.id.cubes);
        mClimbView = mView.findViewById(R.id.climb);
        mFouls = mView.findViewById(R.id.fouls);
        mTechFouls = mView.findViewById(R.id.tech_fouls);

        if(mTeamNumber != -1)
        {
            new UpdateTask().execute();
        }

        return mView;
    }

    private class UpdateTask extends AsyncTask
    {
        boolean red = false;
        boolean yellow = false;
        int fouls = 0;
        int tech_fouls = 0;
        @Override
        protected Object doInBackground(Object[] objects)
        {
            if(mStartView != null && mTeamNumber != -1)
            {
                Team team = new Team(mTeamNumber);
                mStartView.setTeam(team);
                mCubesView.setTeam(team);
                mClimbView.setTeam(team);

                for(TeamMatchData tmd : team.getMatches().values())
                {
                    if(tmd.isRedCard())
                    {
                        red = true;
                    }
                    else if(tmd.isYellowCard())
                    {
                        yellow = true;
                    }

                    fouls += tmd.getFouls();
                    tech_fouls += tmd.getTechFouls();
                }

                publishProgress();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... objects)
        {
            if (red)
            {
                mView.setBackgroundColor(Color.RED);
            }
            else if (yellow)
            {
                mView.setBackgroundColor(Color.YELLOW);
            }

            mFouls.setText(String.valueOf(fouls));
            mTechFouls.setText(String.valueOf(tech_fouls));
        }
    }
}
