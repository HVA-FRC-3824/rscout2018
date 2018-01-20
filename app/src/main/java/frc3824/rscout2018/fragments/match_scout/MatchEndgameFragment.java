package frc3824.rscout2018.fragments.match_scout;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.Arrays;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.databinding.FragmentMatchEndgameBinding;
import frc3824.rscout2018.utilities.Utilities;
import frc3824.rscout2018.views.SavableRadioButtons;

import static frc3824.rscout2018.utilities.Constants.MatchScouting.EndGame.CLIMB_STATE_OPTIONS;

/**
 * @class MatchEndgameFragment
 * @brief Fragment used to record information about how a team performed during the end game
 */
public class MatchEndgameFragment extends Fragment implements RadioGroup.OnCheckedChangeListener
{
    FragmentMatchEndgameBinding mBinding = null;
    TeamMatchData mTeamMatchData = null;

    SavableRadioButtons mClimbingMethod;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_endgame, container, false);
        if(mTeamMatchData != null)
        {
            mBinding.setTmd(mTeamMatchData);
        }
        View view = mBinding.getRoot();

        SavableRadioButtons climbingState = view.findViewById(R.id.endgame_climb_state);
        climbingState.setOnCheckChange(this);

        mClimbingMethod = view.findViewById(R.id.endgame_climb_method);

        // Add touch listeners
        Utilities.setupUi(getActivity(), view);

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(i == Arrays.asList(CLIMB_STATE_OPTIONS).indexOf("Successful")) {
            mClimbingMethod.setVisibility(View.VISIBLE);
        } else {
            mClimbingMethod.setVisibility(View.GONE);
        }
    }
}
