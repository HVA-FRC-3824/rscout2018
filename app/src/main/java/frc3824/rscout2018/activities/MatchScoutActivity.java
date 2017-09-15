package frc3824.rscout2018.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.adapters.fpa.MatchScoutFragmentPagerAdapter;
import frc3824.rscout2018.data_models.MatchLogistics;
import frc3824.rscout2018.data_models.TeamMatchData;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;
import io.realm.Realm;

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
    private int mMatchNumber;
    private boolean mPractice = false;

    private String mScoutName;

    private Realm mDatabase;
    private boolean mDirty = false;

    private MatchScoutFragmentPagerAdapter mSFPA;
    private TeamMatchData mTMD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_scout);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int position = sharedPreferences.getInt(Constants.Settings.MATCH_SCOUT_POSITION, -1);
        if (position == -1)
        {
            // Shouldn't be possible to get here
            // todo: Error
        }

        ScoutHeader header = findViewById(R.id.header);
        header.setInterface(new MatchScoutHeader());

        mDatabase = Realm.getDefaultInstance();

        // Normal match
        if (mMatchNumber > 0)
        {
            MatchLogistics m = mDatabase.where(MatchLogistics.class)
                                        .equalTo(Constants.Database.PrimaryKeys.MATCH_LOGISTICS,
                                                 mMatchNumber)
                                        .findFirst();
            if (m == null)
            {
                // error
            }
            mTeamNumber = m.getTeamNumber(position);
            header.setTitle(String.format("Match Number: %d Team Number: %d",
                                          mMatchNumber,
                                          mTeamNumber));

            if (mMatchNumber == 1)
            {
                header.removePrevious();
            }

            if (mDatabase.where(MatchLogistics.class)
                         .equalTo(Constants.Database.PrimaryKeys.MATCH_LOGISTICS, mMatchNumber + 1)
                         .findFirst() == null)
            {
                header.removeNext();
            }

        }
        else
        { // Practice
            mPractice = true;
            header.setTitle("Practice Match");
        }

        // Keep screen on while scouting
        findViewById(android.R.id.content).setKeepScreenOn(true);

        mTMD = mDatabase.where(TeamMatchData.class)
                        .equalTo(Constants.Database.PrimaryKeys.TEAM_MATCH_DATA,
                                 String.format("%d_%d", mTeamNumber, mMatchNumber))
                        .findFirst();
        if (mTMD == null)
        {
            mTMD = new TeamMatchData(mTeamNumber, mMatchNumber);
        }

        // Setup the TABS and fragment pages
        mSFPA = new MatchScoutFragmentPagerAdapter(getFragmentManager(),
                                                   mTMD);

        // Setup view pager
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mSFPA);
        viewPager.setOffscreenPageLimit(mSFPA.getCount());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        if (position < 3)
        {
            tabLayout.setBackgroundColor(Color.BLUE);
        }
        else
        {
            tabLayout.setBackgroundColor(Color.RED);
        }
        tabLayout.setTabTextColors(Color.WHITE, Color.GREEN);
        tabLayout.setSelectedTabIndicatorColor(Color.GREEN);
        tabLayout.setupWithViewPager(viewPager);
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
            final Intent intent = new Intent(MatchScoutActivity.this, MatchScoutActivity.class);
            if (mPractice)
            {
                MatchScoutActivityStarter.start(-1);
            }
            else
            {
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
                {
                    MatchScoutActivityStarter.start(mMatchNumber - 1);
                }
            }
        }

        /**
         * Next button press handler
         */
        public void next()
        {
            final Intent intent = new Intent(MatchScoutActivity.this, MatchScoutActivity.class);
            if (mPractice)
            {
                MatchScoutActivityStarter.start(-1);
            }
            else
            {
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
                {
                    MatchScoutActivityStarter.start(mMatchNumber + 1);
                }
            }
        }

        /**
         * Home button press handler
         */
        public void home()
        {
            if (mPractice)
            {
                HomeActivityStarter.start();
            }
            else
            {
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
                {
                    HomeActivityStarter.start();
                }
            }
        }

        /**
         * List button press handler
         */
        public void list()
        {
            if (mPractice)
            {
                MatchListActivityStarter.start(Constants.IntentExtras.NextPage.MATCH_SCOUTING);
            }
            else
            {
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
                {
                    MatchListActivityStarter.start(Constants.IntentExtras.NextPage.MATCH_SCOUTING);
                }
            }
        }

        /**
         * Save button press handler
         */
        public void save()
        {

        }
    }
}
