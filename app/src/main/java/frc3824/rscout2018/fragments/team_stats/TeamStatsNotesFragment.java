package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import activitystarter.Arg;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.NotesStorage;
import frc3824.rscout2018.database.data_models.SuperMatchData;
import frc3824.rscout2018.database.data_models.Team;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.databinding.FragmentTeamStatsNotesBinding;

/**
 * @class TeamStatsNotesFragment
 * @brief A fragment for displaying the notes taken during a team's matches about its performance
 */
public class TeamStatsNotesFragment extends Fragment
{
    int mTeamNumber;
    FragmentTeamStatsNotesBinding mBinding;
    NotesStorage mNotes = new NotesStorage();

    public void setTeamNumber(int teamNumber)
    {
        mTeamNumber = teamNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_stats_notes, null, false);
        mBinding.setNotes(mNotes);
        new UpdateTask().execute();
        return mBinding.getRoot();
    }

    private class UpdateTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects)
        {
            Team team = new Team(mTeamNumber);

            String matchNotesText = "";
            for(TeamMatchData tmd : team.getMatches().values())
            {
                if(tmd.getNotes() != null && !tmd.getNotes().isEmpty())
                {
                    matchNotesText += String.format("Match %d:\n\t%s\n", tmd.getMatchNumber(), tmd.getNotes());
                }
            }
            mNotes.setMatchNotes(matchNotesText);

            String superNotesText = "";
            for(SuperMatchData smd : team.getSuperMatches().values())
            {
                if(smd.getNotes() != null && !smd.getNotes().isEmpty())
                {
                    superNotesText += String.format("Match %d:\n\t%s\n", smd.getMatchNumber(), smd.getNotes());
                }
            }
            mNotes.setSuperNotes(superNotesText);

            return null;
        }
    }

}
