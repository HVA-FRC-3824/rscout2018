package frc3824.rscout2018.fragments.MatchScout;


import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frc3824.rscout2018.R;
import frc3824.rscout2018.data_models.TeamMatchData;
import frc3824.rscout2018.databinding.FragmentMatchAutoBinding;
import frc3824.rscout2018.utilities.Utilities;

public class MatchAutoFragment extends Fragment
{
    FragmentMatchAutoBinding mBinding;
    TeamMatchData mTeamMatchData;

    public void setData(TeamMatchData teamMatchData)
    {
        mTeamMatchData = teamMatchData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_auto, container, false);
        mBinding.setTmd(mTeamMatchData);
        View view = mBinding.getRoot();

        // Add touch listeners
        Utilities.setupUi(getActivity(), view);

        return view;
    }
}
