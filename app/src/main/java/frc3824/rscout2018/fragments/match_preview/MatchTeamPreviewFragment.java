package frc3824.rscout2018.fragments.match_preview;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.Team;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.database.data_models.powered_up.CubeEvent;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.utilities.Utilities;

/**
 * @class MatchTeamPreviewFragment
 * @brief The fragment which displays the highlights for a specific
 *        team during the preview of a match
 */
public class MatchTeamPreviewFragment extends Fragment
{
    @Arg
    boolean mRed;
    Team mTeam;



    public void setTeam(Team team)
    {
        mTeam = team;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        View view = inflater.inflate(R.layout.fragment_match_team_preview, container);

        ActivityStarter.fill(this);
        if(mRed)
        {
            view.setBackgroundColor(Color.RED);
        }
        else
        {
            view.setBackgroundColor(Color.BLUE);
        }

        // Add touch listeners
        Utilities.setupUi(getActivity(), view);

        return view;
    }

    private class UpdateTask extends AsyncTask
    {
        float auto_switch_attempts = 0;
        float auto_switch_successes = 0;

        float auto_scale_attempts = 0;
        float auto_scale_successes = 0;

        float teleop_switch_attempts = 0;
        float teleop_switch_successes = 0;

        float teleop_scale_attempts = 0;
        float teleop_scale_successes = 0;

        float short_cycle_sum = 0;
        float short_cycle_num = 0;

        float medium_cycle_sum = 0;
        float medium_cycle_num = 0;

        float long_cycle_sum = 0;
        float long_cycle_num = 0;

        float drop_avg = 0;
        float launch_fail_avg = 0;

        float average_fouls = 0;
        float average_tech_fouls = 0;

        boolean yellow_card = false;
        boolean red_card = false;

        @Override
        protected Object doInBackground(Object[] objects)
        {

            /**
             * Auto attempts/tries
             * Teleop attempts/tries
             *
             * cycle time back to pick up after
             * average # drops
             *
             * # average fouls
             * # average tech fouls
             * # yellow cards
             * # red cards
             */

            for(TeamMatchData teamMatchData : mTeam.getMatches().values())
            {
                for(CubeEvent cubeEvent: teamMatchData.getAutoCubeEvents())
                {
                    switch (cubeEvent.getEvent())
                    {
                        case Constants.MatchScouting.CubeEvents.DROPPED:
                            drop_avg ++;
                            break;
                        case Constants.MatchScouting.CubeEvents.LAUNCH_FAILURE:
                            launch_fail_avg ++;
                            if(cubeEvent.getLocationX() < Constants.TeamStats.Cubes.SWITCH_THRESHOlD ||
                                    cubeEvent.getLocationX() > 1.0 - Constants.TeamStats.Cubes.SWITCH_THRESHOlD)
                            {
                                auto_switch_attempts ++;
                            }
                            else
                            {
                                auto_scale_attempts ++;
                            }
                            break;
                        case Constants.MatchScouting.CubeEvents.PLACED:
                        case Constants.MatchScouting.CubeEvents.LAUNCH_SUCCESS:
                            if(cubeEvent.getLocationX() < Constants.TeamStats.Cubes.SWITCH_THRESHOlD ||
                                    cubeEvent.getLocationX() > 1.0 - Constants.TeamStats.Cubes.SWITCH_THRESHOlD)
                            {
                                auto_switch_successes ++;
                            }
                            else
                            {
                                auto_switch_successes ++;
                            }
                            break;
                    }
                }

                /*
                for(CubeEvent cubeEvent: )
                {
                    CubeEvent cubeEvent = teleopCubeEvents.get(i);
                    switch (cubeEvent.getEvent())
                    {
                        case Constants.MatchScouting.CubeEvents.DROPPED:
                            drop_avg ++;
                            break;
                        case Constants.MatchScouting.CubeEvents.LAUNCH_FAILURE:
                            launch_fail_avg ++;
                            if(cubeEvent.getLocationX() < Constants.TeamStats.Cubes.SWITCH_THRESHOlD ||
                                    cubeEvent.getLocationX() > 1.0 - Constants.TeamStats.Cubes.SWITCH_THRESHOlD)
                            {
                                teleop_switch_attempts ++;
                            }
                            else
                            {
                                teleop_scale_attempts ++;
                            }
                            break;
                        case Constants.MatchScouting.CubeEvents.PLACED:
                        case Constants.MatchScouting.CubeEvents.LAUNCH_SUCCESS:
                            if(cubeEvent.getLocationX() < Constants.TeamStats.Cubes.SWITCH_THRESHOlD ||
                                    cubeEvent.getLocationX() > 1.0 - Constants.TeamStats.Cubes.SWITCH_THRESHOlD)
                            {
                                teleop_switch_successes ++;
                            }
                            else
                            {
                                teleop_switch_successes ++;
                            }
                            break;
                    }

                }
                */
            }


            return null;
        }
    }
}
