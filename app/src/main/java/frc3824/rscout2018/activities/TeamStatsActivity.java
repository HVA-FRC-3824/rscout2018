package frc3824.rscout2018.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.fragments.team_stats.TeamStatsChartsFragment;
import frc3824.rscout2018.fragments.team_stats.TeamStatsMatchDataFragment;
import frc3824.rscout2018.fragments.team_stats.TeamStatsNotesFragment;
import frc3824.rscout2018.fragments.team_stats.TeamStatsPitDataFragment;
import frc3824.rscout2018.fragments.team_stats.TeamStatsScheduleFragment;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;

/**
 *
 */
@MakeActivityStarter
public class TeamStatsActivity extends Activity
{
    @Arg
    protected int mTeamNumber;
    TeamStatsFragmentPagerAdapter mFPA;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout);

        ScoutHeader header = findViewById(R.id.header);
        header.setInterface(new TeamStatsActivity.TeamStatsHeader());
        header.removeSave();

        // If first team then remove the previous button
        ArrayList<Integer> teamNumbers = Database.getInstance().getTeamNumbers();
        int index = teamNumbers.indexOf(mTeamNumber);
        if (index <= 0)
        {
            header.removePrevious();
        }

        // If last team then remove the next button
        index = teamNumbers.indexOf(mTeamNumber);
        if (index >= teamNumbers.size() - 1)
        {
            header.removeNext();
        }

        // Setup the TABS and fragment pages
        mFPA = new TeamStatsActivity.TeamStatsFragmentPagerAdapter(getFragmentManager());

        // Setup view pager
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mFPA);
        viewPager.setOffscreenPageLimit(mFPA.getCount());

        SmartTabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setViewPager(viewPager);
    }

    private class TeamStatsHeader implements ScoutHeaderInterface
    {

        @Override
        public void previous()
        {
            ArrayList<Integer> teamNumbers = Database.getInstance().getTeamNumbers();
            int index = teamNumbers.indexOf(mTeamNumber);
            if (index > 0) // If null then this function shouldn't be possible to call as the button should have been hidden
            {
                TeamStatsActivityStarter.start(TeamStatsActivity.this, teamNumbers.get(index - 1));
            }
        }

        @Override
        public void next()
        {
            ArrayList<Integer> teamNumbers = Database.getInstance().getTeamNumbers();
            int index = teamNumbers.indexOf(mTeamNumber);
            if (index < teamNumbers.size() - 1) // If null then this function shouldn't be possible to call as the button should have been hidden
            {
                TeamStatsActivityStarter.start(TeamStatsActivity.this, teamNumbers.get(index + 1));
            }
        }

        @Override
        public void home()
        {
            HomeActivityStarter.start(TeamStatsActivity.this);
        }

        @Override
        public void list()
        {
            TeamListActivityStarter.start(TeamStatsActivity.this, Constants.IntentExtras.NextPageOptions.TEAM_STATS);
        }

        @Override
        public void save()
        {
            // Never called
        }
    }

    private class TeamStatsFragmentPagerAdapter extends FragmentPagerAdapter
    {
        public TeamStatsFragmentPagerAdapter(FragmentManager fm)
        {
            super(fm);
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
            assert(position >= 0 && position < Constants.TeamStats.TABS.length);
            switch (position)
            {
                case 0:
                    TeamStatsChartsFragment tscf = new TeamStatsChartsFragment();
                    return tscf;
                case 1:
                    TeamStatsMatchDataFragment tsmdf = new TeamStatsMatchDataFragment();
                    return tsmdf;
                case 2:
                    TeamStatsPitDataFragment tspdf = new TeamStatsPitDataFragment();
                    return tspdf;
                case 3:
                    TeamStatsNotesFragment tsnf = new TeamStatsNotesFragment();
                    return tsnf;
                case 4:
                    TeamStatsScheduleFragment tssf = new TeamStatsScheduleFragment();
                    return tssf;
                default:
                    assert(false);
            }
            return null;
        }

        /**
         * @returns The number of fragments
         */
        @Override
        public int getCount()
        {
            return Constants.TeamStats.TABS.length;
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
            assert(position >= 0 && position < Constants.TeamStats.TABS.length);
            return Constants.TeamStats.TABS[position];
        }
    }
}
