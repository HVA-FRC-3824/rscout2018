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

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.sdsmdg.tastytoast.TastyToast;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.data_models.MatchLogistics;
import frc3824.rscout2018.data_models.TeamMatchData;
import frc3824.rscout2018.fragments.match_scout.MatchAutoFragment;
import frc3824.rscout2018.fragments.match_scout.MatchEndgameFragment;
import frc3824.rscout2018.fragments.match_scout.MatchFoulsFragment;
import frc3824.rscout2018.fragments.match_scout.MatchMiscFragment;
import frc3824.rscout2018.fragments.match_scout.MatchTeleopFragment;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * @class MatchScoutActivity
 * @brief The page for scouting an individual team in a single match
 */
@MakeActivityStarter
public class MatchScoutActivity extends Activity implements RealmChangeListener<TeamMatchData>
{
    private final static String TAG = "MatchScoutActivity";

    private int mTeamNumber = -1;
    @Arg
    protected int mMatchNumber = -1;
    private boolean mPractice = false;

    private Realm mDatabase;

    private MatchScoutFragmentPagerAdapter mFPA;
    private TeamMatchData mTMD;
    private boolean mDirty = false;

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
                // Shouldn't be possible to get here
                // todo: Error
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

            mTMD = mDatabase.where(TeamMatchData.class)
                            .equalTo(Constants.Database.PrimaryKeys.TEAM_MATCH_DATA,
                                     String.format("%d_%d", mTeamNumber, mMatchNumber))
                            .findFirst();
            if (mTMD == null)
            {
                mDatabase.beginTransaction();
                mTMD = mDatabase.createObject(TeamMatchData.class, String.format("%d_%d", mTeamNumber, mMatchNumber));
                mTMD.setTeamNumber(mTeamNumber);
                mTMD.setMatchNumber(mMatchNumber);
                mDatabase.commitTransaction();
            }
            mTMD.addChangeListener(this);

        }
        else
        {
            mPractice = true;
            header.setTitle("Practice Match");
            mTMD = new TeamMatchData();
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
     * When the data model is changed dirty is set to true
     * @param t The data model
     */
    public void onChange(TeamMatchData t)
    {
        mDirty = true;
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
            if (mPractice)
            {
                MatchScoutActivityStarter.start(MatchScoutActivity.this, -1);
            }
            else
            {
                if (mDirty)
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
            if (mPractice)
            {
                MatchScoutActivityStarter.start(MatchScoutActivity.this, -1);
            }
            else
            {
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
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
            if (mPractice)
            {
                HomeActivityStarter.start(MatchScoutActivity.this);
            }
            else
            {
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
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
            if (mPractice)
            {
                MatchListActivityStarter.start(MatchScoutActivity.this, Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING);
            }
            else
            {
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
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
            if(!mPractice)
            {
                mDatabase.beginTransaction();
                mDatabase.copyToRealmOrUpdate(mTMD);
                mDatabase.commitTransaction();
                mDirty = false;
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

        String[] mTitles = { "Auto", "Teleop", "Endgame", "Fouls", "Misc"};
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
            switch (position)
            {
                case 0:
                    MatchAutoFragment maf = new MatchAutoFragment();
                    maf.setData(mTeamMatchData);
                    return maf;
                case 1:
                    MatchTeleopFragment mtf = new MatchTeleopFragment();
                    mtf.setData(mTeamMatchData);
                    return mtf;
                case 2:
                    MatchEndgameFragment mef = new MatchEndgameFragment();
                    mef.setData(mTeamMatchData);
                    return mef;
                case 3:
                    MatchFoulsFragment mff = new MatchFoulsFragment();
                    mff.setData(mTeamMatchData);
                    return mff;
                case 4:
                    MatchMiscFragment mmf = new MatchMiscFragment();
                    mmf.setData(mTeamMatchData);
                    return mmf;
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
}
