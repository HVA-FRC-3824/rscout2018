package frc3824.rscout2018.adapters.fpa;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import frc3824.rscout2018.data_models.TeamMatchData;
import frc3824.rscout2018.fragments.MatchScout.MatchAutoFragment;

/**
 * @class MatchScoutFragmentPagerAdapter
 *
 * Creates multiple fragments based on the LAYOUTS passed in
 */
public class MatchScoutFragmentPagerAdapter extends FragmentPagerAdapter
{
    static final String TAG = "MatchScoutFragmentPagerAdapter";

    String[] mTitles = { "Auto" }; //{ "Auto", "Teleop", "Endgame", "Fouls", "Misc"};
    TeamMatchData mTeamMatchData;


    public MatchScoutFragmentPagerAdapter(FragmentManager fm, TeamMatchData teamMatchData)
    {
        super(fm);
        mTeamMatchData = teamMatchData;
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
                MatchAutoFragment maf = new MatchAutoFragment();
                maf.setData(mTeamMatchData);
                return maf;
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
