package frc3824.rscout2018.fragments.match_scout;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.databinding.FragmentMatchTeleopBinding;
import frc3824.rscout2018.utilities.Utilities;
import frc3824.rscout2018.views.powered_up.SavableCubes;

/**
 * @class MatchTeleopFragment
 * @brief Fragment used to record information about how a team performed during the teleop period
 */
public class MatchTeleopFragment extends Fragment
{
    FragmentMatchTeleopBinding mBinding = null;
    TeamMatchData mTeamMatchData = null;
    SavableCubes mCubes;

    /**
     * Sets the data model for binding
     * @param teamMatchData The data model for how a team performed in a specific match
     */
    public void setTeamMatchData(TeamMatchData teamMatchData)
    {
        mTeamMatchData = teamMatchData;
        if(mBinding != null)
        {
            mBinding.setTmd(mTeamMatchData);
        }
    }

    public void start()
    {
        mCubes.start();
    }

    public void stop()
    {
        mCubes.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_teleop, container, false);
        if(mTeamMatchData != null)
        {
            mBinding.setTmd(mTeamMatchData);
        }
        View view = mBinding.getRoot();
        mCubes = view.findViewById(R.id.cubes);
        mCubes.setAuto(false);

        // Add touch listeners
        Utilities.setupUi(getActivity(), view);

        return view;
    }
}
