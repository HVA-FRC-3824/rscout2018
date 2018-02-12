package frc3824.rscout2018.fragments.match_scout;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.database.data_models.TeamPitData;
import frc3824.rscout2018.databinding.FragmentMatchStartBinding;
import frc3824.rscout2018.utilities.Utilities;
import info.hoang8f.widget.FButton;

/**
 * @author frc3824
 */
public class MatchStartFragment extends Fragment implements View.OnClickListener, ImageListener
{
    private TeamMatchData mTeamMatchData = null;
    private FragmentMatchStartBinding mBinding = null;
    private View.OnClickListener mStartListener;
    private FButton mStartButton;
    private CarouselView mCarouselView;
    private ArrayList<String> mPictureFilepaths;


    public void setData(TeamMatchData teamMatchData)
    {
        mTeamMatchData = teamMatchData;
        if(mBinding != null)
        {
            mBinding.setTmd(mTeamMatchData);
        }
        TeamPitData tpd = new TeamPitData(mTeamMatchData.getTeamNumber());
        mPictureFilepaths = tpd.getPictureFilepaths();
    }

    public void setStartListener(View.OnClickListener listener)
    {
        mStartListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate layout and bind the realm object
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_start, container, false);
        if(mTeamMatchData != null)
        {
            mBinding.setTmd(mTeamMatchData);
        }
        View view = mBinding.getRoot();

        // Inflate the carousel
        mCarouselView = view.findViewById(R.id.carousel);
        mCarouselView.setImageListener(this);

        // Add touch listeners
        Utilities.setupUi(getActivity(), view);

        mStartButton = view.findViewById(R.id.start_button);
        mStartButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        mStartButton.setEnabled(false);
        mStartListener.onClick(mStartButton);
    }

    @Override
    public void setImageForPosition(int position, ImageView imageView)
    {
        if(position > 0 && position < mPictureFilepaths.size())
        {
            Glide.with(this).load(mPictureFilepaths.get(position)).into(imageView);
        }
        else
        {
            // todo(Andrew): error
        }
    }
}
