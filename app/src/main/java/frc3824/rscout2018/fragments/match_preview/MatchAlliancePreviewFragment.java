package frc3824.rscout2018.fragments.match_preview;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.ActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.data_models.TeamCalculatedData;
import frc3824.rscout2018.utilities.Utilities;

/**
 * @class MatchAlliancePreviewFragment
 * @brief A fragment for the match preview that shows how how an alliance should stack up
 */
public class MatchAlliancePreviewFragment extends Fragment
{
    boolean mRed;
    TeamCalculatedData[] mTeams;
    //AllianceCalculatedData mAlliance = null;

    public void setData(TeamCalculatedData team1, TeamCalculatedData team2, TeamCalculatedData team3)
    {
        mTeams = new TeamCalculatedData[3];
        mTeams[0] = team1;
        mTeams[1] = team2;
        mTeams[2] = team3;
        //mAlliance = new AllianceCalculatedData(mTeams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        View view = inflater.inflate(R.layout.fragment_match_alliance_preview, container, false);
        //mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_alliance_preview, container, false);
        //mBinding.setAcd(mAlliance);
        //View view = mBinding.getRoot();

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
