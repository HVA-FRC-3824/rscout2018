package frc3824.rscout2018.fragments.pit_scout;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamPitData;
import frc3824.rscout2018.databinding.FragmentPitMiscBinding;
import frc3824.rscout2018.utilities.Utilities;

/**
 * @class PitMiscFragment
 * @brief A fragment used to record miscellaneous information about a team's robot
 */
public class PitMiscFragment extends Fragment
{
    FragmentPitMiscBinding mBinding = null;
    TeamPitData mTeamPitData = null;

    /**
     * Sets the data model for binding
     * @param teamPitData The information record about a team from pit scouting
     */
    public void setData(TeamPitData teamPitData)
    {
        mTeamPitData = teamPitData;
        if(mBinding != null)
        {
            mBinding.setTpd(mTeamPitData);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_auto, container, false);
        if(mTeamPitData != null)
        {
            mBinding.setTpd(mTeamPitData);
        }
        View view = mBinding.getRoot();

        // Add touch listeners
        Utilities.setupUi(getActivity(), view);

        return view;
    }
}
