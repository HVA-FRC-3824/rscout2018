package frc3824.rscout2018.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.fragments.match_preview.MatchAlliancePreviewFragment;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;

/**
 * @class MatchPreviewActivity
 * @brief Activity for previewing the teams in a given match
 */
@MakeActivityStarter
public class MatchPreviewActivity extends RScoutActivity
{
    @Arg
    protected int mMatchNumber;

    private MatchPreviewFragmentPagerActivity mFPA;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        ActivityStarter.fill(this);
        setContentView(R.layout.activity_scout);

        ScoutHeader header = findViewById(R.id.header);
        header.setTitle(String.format("Match Number: %d", mMatchNumber));
        header.setInterface(new MatchPreviewHeader());
        header.removeSave();

        // If on the first match then the previous button should be hidden
        if (mMatchNumber == 1)
        {
            header.removePrevious();
        }

        // If on the final match then next button should be hidden
        if (mMatchNumber == Database.getInstance().numberOfMatches())
        {
            header.removeNext();
        }

        // Setup the TABS and fragment pages
        mFPA = new MatchPreviewFragmentPagerActivity(getFragmentManager());

        // Setup view pager
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mFPA);
        mViewPager.setOffscreenPageLimit(mFPA.getCount());

        SmartTabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(Color.BLUE);
        tabLayout.setViewPager(mViewPager);
    }

    private class MatchPreviewHeader implements ScoutHeaderInterface
    {

        @Override
        public void previous()
        {
            MatchPreviewActivityStarter.start(MatchPreviewActivity.this, mMatchNumber - 1);

        }

        @Override
        public void next()
        {
            MatchPreviewActivityStarter.start(MatchPreviewActivity.this, mMatchNumber + 1);

        }

        @Override
        public void home()
        {
            HomeActivityStarter.start(MatchPreviewActivity.this);

        }

        @Override
        public void list()
        {
            MatchListActivityStarter.start(MatchPreviewActivity.this, Constants.IntentExtras.NextPageOptions.MATCH_PREVIEW);

        }

        @Override
        public void save()
        {
            //should never be called
        }
    }


    private class MatchPreviewFragmentPagerActivity extends FragmentPagerAdapter
    {
        public MatchPreviewFragmentPagerActivity(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment f;
            switch (position)
            {
                case 0:
                    f = new MatchAlliancePreviewFragment();
                    ((MatchAlliancePreviewFragment)f).setMatchNumber(mMatchNumber, false);
                    return f;
                case 1:
                    f = new MatchAlliancePreviewFragment();
                    ((MatchAlliancePreviewFragment)f).setMatchNumber(mMatchNumber, true);
                    return f;
                default:
                    assert(false);
            }


            return null;
        }

        @Override
        public int getCount()
        {
            return Constants.MatchPreview.TABS.length;
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
            assert(position >= 0 && position < Constants.MatchPreview.TABS.length);
            return Constants.MatchPreview.TABS[position];
        }
    }

}
