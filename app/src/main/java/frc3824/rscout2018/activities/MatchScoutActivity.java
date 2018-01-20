package frc3824.rscout2018.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.sdsmdg.tastytoast.TastyToast;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.MatchLogistics;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.fragments.match_scout.MatchAutoFragment;
import frc3824.rscout2018.fragments.match_scout.MatchEndgameFragment;
import frc3824.rscout2018.fragments.match_scout.MatchFoulsFragment;
import frc3824.rscout2018.fragments.match_scout.MatchMiscFragment;
import frc3824.rscout2018.fragments.match_scout.MatchTeleopFragment;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;

/**
 * @class MatchScoutActivity
 * @brief The page for scouting an individual team in a single match
 */
@MakeActivityStarter
public class MatchScoutActivity extends Activity
{
    private final static String TAG = "MatchScoutActivity";

    private int mTeamNumber = -1;
    @Arg
    protected int mMatchNumber = -1;
    private boolean mPractice = false;

    private MatchScoutFragmentPagerAdapter mFPA;
    private TeamMatchData mTMD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout);
        ActivityStarter.fill(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int position = Integer.parseInt(sharedPreferences.getString(Constants.Settings.MATCH_SCOUT_POSITION, "-1"));
        if (position == -1)
        {
            Log.e(TAG, "Error: impossible match scout position");
            return;
        }

        ScoutHeader header = findViewById(R.id.header);
        header.setInterface(new MatchScoutHeader());

        // Normal match
        if (mMatchNumber > 0)
        {
            MatchLogistics m = new MatchLogistics(mMatchNumber);
            mTeamNumber = m.getTeamNumber(position);
            header.setTitle(String.format("Match Number: %d Team Number: %d",
                                          mMatchNumber,
                                          mTeamNumber));

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

            mTMD = new TeamMatchData(mTeamNumber, mMatchNumber);
        }
        // Practice Match
        else
        {
            mPractice = true;
            header.setTitle("Practice Match");
            mTMD = new TeamMatchData(-1, -1);
            header.removeSave();
        }

        // Keep screen on while scouting
        findViewById(android.R.id.content).setKeepScreenOn(true);

        // Setup the TABS and fragment pages
        mFPA = new MatchScoutFragmentPagerAdapter(getFragmentManager(),
                                                  mTMD);

        // Setup view pager
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mFPA);
        viewPager.setOffscreenPageLimit(mFPA.getCount());

        SmartTabLayout tabLayout = findViewById(R.id.tab_layout);
        if (position < 3)
        {
            tabLayout.setBackgroundColor(Color.BLUE);
        }
        else
        {
            tabLayout.setBackgroundColor(Color.RED);
        }
        tabLayout.setViewPager(viewPager);
    }

    /**
     * Inner class that handles pressing the buttons on the header
     */
    private class MatchScoutHeader implements ScoutHeaderInterface
    {

        /**
         * Previous button press handler
         */
        public void previous()
        {
            if (mPractice) // Don't need to worry about saving for practice
            {
                MatchScoutActivityStarter.start(MatchScoutActivity.this, -1);
            }
            else
            {
                if (mTMD.isDirty())
                {
                    // todo: Save dialog
                }
                else
                {
                    MatchScoutActivityStarter.start(MatchScoutActivity.this, mMatchNumber - 1);
                }
            }
        }

        /**
         * Next button press handler
         */
        public void next()
        {
            if (mPractice) // Don't need to worry about saving for practice
            {
                MatchScoutActivityStarter.start(MatchScoutActivity.this, -1);
            }
            else
            {
                if (mTMD.isDirty())
                {
                    // todo: Save dialog
                }
                else // Don't need to worry about saving if nothing has changed
                {
                    MatchScoutActivityStarter.start(MatchScoutActivity.this, mMatchNumber + 1);
                }
            }
        }

        /**
         * Home button press handler
         */
        public void home()
        {
            if (mPractice) // Don't need to worry about saving for practice
            {
                HomeActivityStarter.start(MatchScoutActivity.this);
            }
            else
            {
                if (mTMD.isDirty())
                {
                    // todo: Save dialog
                }
                else // Don't need to worry about saving if nothing has changed
                {
                    HomeActivityStarter.start(MatchScoutActivity.this);
                }
            }
        }

        /**
         * List button press handler
         */
        public void list()
        {
            if (mPractice) // Don't need to worry about saving for practice
            {
                MatchListActivityStarter.start(MatchScoutActivity.this, Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING);
            }
            else
            {
                if (mTMD.isDirty())
                {
                    // todo: Save dialog
                }
                else // Don't need to worry about saving if nothing has changed
                {
                    MatchListActivityStarter.start(MatchScoutActivity.this, Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING);
                }
            }
        }

        /**
         * Save button press handler
         */
        public void save()
        {
            if(!mPractice) // Don't need to worry about saving for practice
            {

                TastyToast.makeText(MatchScoutActivity.this, "Saved", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
            }
        }
    }

    /**
     * @class MatchScoutFragmentPagerAdapter
     *
     * Creates multiple fragments based for the match scout activity
     */
    private class MatchScoutFragmentPagerAdapter extends FragmentPagerAdapter
    {
        static final String TAG = "MatchScoutFragmentPagerAdapter";

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
            assert(position >= 0 && position < Constants.MatchScouting.TABS.length);
            switch (position)
            {
                case 0:
                    MatchAutoFragment maf = new MatchAutoFragment();
                    maf.setTeamMatchData(mTeamMatchData);
                    return maf;
                case 1:
                    MatchTeleopFragment mtf = new MatchTeleopFragment();
                    mtf.setTeamMatchData(mTeamMatchData);
                    return mtf;
                case 2:
                    MatchEndgameFragment mef = new MatchEndgameFragment();
                    mef.setTeamMatchData(mTeamMatchData);
                    return mef;
                case 3:
                    MatchFoulsFragment mff = new MatchFoulsFragment();
                    mff.setTeamMatchData(mTeamMatchData);
                    return mff;
                case 4:
                    MatchMiscFragment mmf = new MatchMiscFragment();
                    mmf.setTeamMatchData(mTeamMatchData);
                    return mmf;
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
            return Constants.MatchScouting.TABS.length;
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
            assert(position >= 0 && position < Constants.MatchScouting.TABS.length);
            return Constants.MatchScouting.TABS[position];
        }
    }
}
