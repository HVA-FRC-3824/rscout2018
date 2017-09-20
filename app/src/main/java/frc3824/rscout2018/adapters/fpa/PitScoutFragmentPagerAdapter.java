package frc3824.rscout2018.adapters.fpa;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import frc3824.rscout2018.data_models.TeamPitData;
import frc3824.rscout2018.fragments.pit_scout.PitPictureFragment;

/**
 * @class PitScoutFragmentPagerAdapter
 * @brief Creates and manages the fragments that are displayed for pit scouting
 */
public class PitScoutFragmentPagerAdapter extends FragmentPagerAdapter
{
    static final String TAG = "MatchScoutFragmentPagerAdapter";

    String[] mTitles = { "Picture" }; //{ "Picture", "Dimensions", "Misc"};
    TeamPitData mTeamPitData;


    public PitScoutFragmentPagerAdapter(FragmentManager fm, TeamPitData teamPitData)
    {
        super(fm);
        mTeamPitData = teamPitData;
    }

    /**
     * Gets the fragment at the specified position for display
     *
     * @param position Position of the fragment wanted
     * @returns fragment to be displayed
     */
    @Override
    public Fragment getItem(int position)
    {
        assert(position >= 0 && position < mTitles.length);
        Fragment f;
        switch (position)
        {
            case 0:
                PitPictureFragment ppf = new PitPictureFragment();
                ppf.setData(mTeamPitData);
                return ppf;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                assert(false);
        }
        return null;
    }

    /**
     * Returns the number of fragments
     * @returns The number of fragments
     */
    @Override
    public int getCount()
    {
        return mTitles.length;
    }

    /**
     * Returns the title of the fragment at the specified position
     *
     * @param position Position of the fragment
     * @return The title of the fragment
     */
    @Override
    public String getPageTitle(int position)
    {
        assert(position >= 0 && position < mTitles.length);
        return mTitles[position];
    }
}
