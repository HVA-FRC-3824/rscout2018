package frc3824.rscout2018.fragments.super_scout;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.SuperMatchData;
import frc3824.rscout2018.databinding.FragmentSuperNotesBinding;
import frc3824.rscout2018.utilities.Utilities;

/**
 * @author frc3824
 * Created: 8/16/16
 */
public class SuperNotesFragment extends Fragment
{
    SuperMatchData mSuperMatchData;
    FragmentSuperNotesBinding mBinding;
    private final static String TAG = "SuperNotesFragment";

    public SuperNotesFragment()
    {}

    public void setSuperMatchData(SuperMatchData superMatchData)
    {
        mSuperMatchData = superMatchData;
        if(mBinding != null)
        {
            mBinding.setSmd(mSuperMatchData);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_super_notes, container, false);
        if(mSuperMatchData != null)
        {
            mBinding.setSmd(mSuperMatchData);
        }
        View view = mBinding.getRoot();

        Utilities.setupUi(getActivity(), view);

        return view;
    }

}