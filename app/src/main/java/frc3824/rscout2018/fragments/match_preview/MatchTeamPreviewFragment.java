package frc3824.rscout2018.fragments.match_preview;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import frc3824.rscout2018.R;
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
    //TeamCalculatedData mTeam;
    //FragmentMatchTeamPreview mBinding;

    /**
     * Sets the team data for the match preview
     * @param team The team data
     */
    /*
    public void setData(TeamCalculatedData team)
    {
        mTeam = team;
    }
    */

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        View view = inflater.inflate(R.layout.fragment_match_team_preview, container);
        // mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_alliance_preview, container, false);
        // mBinding.setTcd(mTeam);
        // View view = mBinding.getRoot();

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
}
