package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.Arg;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamPitData;
import frc3824.rscout2018.databinding.FragmentTeamStatsPitDataBinding;

/**
 * A fragment that displays the data collected during pit scouting
 */
public class TeamStatsPitDataFragment extends Fragment
{
    FragmentTeamStatsPitDataBinding mBinding = null;
    TeamPitData mTeamPitData = null;

    void setTeamPitData(TeamPitData teamPitData)
    {
        mTeamPitData = teamPitData;
        if(mBinding != null)
        {
            mBinding.setTpd(mTeamPitData);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_stats_pit_data, container, false);
        if(mTeamPitData != null)
        {
            mBinding.setTpd(mTeamPitData);
        }
        View view = mBinding.getRoot();

        // todo: images

        return view;
    }
}
