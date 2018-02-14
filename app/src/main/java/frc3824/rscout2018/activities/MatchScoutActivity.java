package frc3824.rscout2018.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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
import frc3824.rscout2018.fragments.match_scout.MatchStartFragment;
import frc3824.rscout2018.fragments.match_scout.MatchTeleopFragment;
import frc3824.rscout2018.services.CommunicationService;
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
    private ViewPager mViewPager;
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
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mFPA);
        mViewPager.setOffscreenPageLimit(mFPA.getCount());

        SmartTabLayout tabLayout = findViewById(R.id.tab_layout);
        if (position < 3)
        {
            tabLayout.setBackgroundColor(Color.BLUE);
        }
        else
        {
            tabLayout.setBackgroundColor(Color.RED);
        }
        tabLayout.setViewPager(mViewPager);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(MatchScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    mTMD.save();

                                    Intent intent = new Intent(MatchScoutActivity.this, CommunicationService.class);
                                    intent.putExtra(Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING, mTMD.toString());
                                    startService(intent);

                                    MatchListActivityStarter.start(MatchScoutActivity.this, Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    MatchListActivityStarter.start(MatchScoutActivity.this, Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING);
                                }
                            })
                            .setNeutralButton("Cancel", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Nothing goes here
                                }
                            });
                    builder.create().show();
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
            if(!mPractice && mTMD.isDirty()) // Don't need to worry about saving for practice or if there is nothing new
            {
                mTMD.save();
                Intent intent = new Intent(MatchScoutActivity.this, CommunicationService.class);
                intent.putExtra(Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING, mTMD.toString());
                startService(intent);
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
        HashMap<Integer, Fragment> mFragments;


        public MatchScoutFragmentPagerAdapter(FragmentManager fm, TeamMatchData teamMatchData)
        {
            super(fm);
            mTeamMatchData = teamMatchData;
            mFragments = new HashMap<>();
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
            if(mFragments.containsKey(position))
            {
                return mFragments.get(position);
            }

            switch (position)
            {
                case 0:
                    MatchStartFragment msf = new MatchStartFragment();
                    msf.setData(mTeamMatchData);
                    mFragments.put(0, msf);
                    return msf;
                case 1:
                    MatchAutoFragment maf = new MatchAutoFragment();
                    maf.setTeamMatchData(mTeamMatchData);
                    mFragments.put(1, maf);
                    return maf;
                case 2:
                    MatchTeleopFragment mtf = new MatchTeleopFragment();
                    mtf.setTeamMatchData(mTeamMatchData);
                    mFragments.put(2, mtf);
                    return mtf;
                case 3:
                    MatchEndgameFragment mef = new MatchEndgameFragment();
                    mef.setTeamMatchData(mTeamMatchData);
                    mFragments.put(3, mef);
                    return mef;
                case 4:
                    MatchFoulsFragment mff = new MatchFoulsFragment();
                    mff.setTeamMatchData(mTeamMatchData);
                    mFragments.put(4, mff);
                    return mff;
                case 5:
                    MatchMiscFragment mmf = new MatchMiscFragment();
                    mmf.setTeamMatchData(mTeamMatchData);
                    mFragments.put(5, mmf);
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
