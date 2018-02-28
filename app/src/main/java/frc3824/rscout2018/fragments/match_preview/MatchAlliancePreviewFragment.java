package frc3824.rscout2018.fragments.match_preview;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.ActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.MatchLogistics;
import frc3824.rscout2018.utilities.Utilities;

/**
 * @class MatchAlliancePreviewFragment
 * @brief A fragment for the match preview that shows how how an alliance should stack up
 */
public class MatchAlliancePreviewFragment extends Fragment
{
    int mMatchNumber;
    boolean mRed;

    public void setMatchNumber(int matchNumber, boolean red)
    {
        mMatchNumber = matchNumber;
        mRed = red;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        View view = inflater.inflate(R.layout.fragment_match_alliance_preview, container, false);

        if(mRed)
        {
            view.setBackgroundColor(Color.RED);
        }
        else
        {
            view.setBackgroundColor(Color.BLUE);
        }

        FragmentManager fm = getChildFragmentManager();

        MatchTeamPreviewFragment team1 = (MatchTeamPreviewFragment)fm.findFragmentById(R.id.team1);
        MatchTeamPreviewFragment team2 = (MatchTeamPreviewFragment)fm.findFragmentById(R.id.team2);
        MatchTeamPreviewFragment team3 = (MatchTeamPreviewFragment)fm.findFragmentById(R.id.team3);

        MatchLogistics match = Database.getInstance().getMatchLogistics(mMatchNumber);
        team1.setTeamNumber(match.getTeamNumber(0 + (mRed ? 3 : 0)), mRed);
        team2.setTeamNumber(match.getTeamNumber(1 + (mRed ? 3 : 0)), mRed);
        team3.setTeamNumber(match.getTeamNumber(2 + (mRed ? 3 : 0)), mRed);

        // Add touch listeners
        Utilities.setupUi(getActivity(), view);

        return view;
    }
}
