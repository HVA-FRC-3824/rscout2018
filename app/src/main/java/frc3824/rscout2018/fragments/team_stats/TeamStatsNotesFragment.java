package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import activitystarter.Arg;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.Team;
import frc3824.rscout2018.database.data_models.TeamMatchData;

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
        TextView matchNotes = view.findViewById(R.id.match_notes);
        TextView superNotes = view.findViewById(R.id.super_notes);
        return view;
    }

    private class UpdateTask extends AsyncTask
    {
        String matchNotesText = "";
        //String superNotesText = "";
        @Override
        protected Object doInBackground(Object[] objects)
        {
            Team team = new Team(mTeamNumber);

            for(TeamMatchData tmd : team.getMatches().values())
            {
                if(tmd.getNotes() != null && !tmd.getNotes().isEmpty())
                {
                    matchNotesText += String.format("Match %d:\n\t%s\n", tmd.getMatchNumber(), tmd.getNotes());
                }
            }

            return null;
        }
    }

}
