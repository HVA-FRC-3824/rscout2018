package frc3824.rscout2018.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.TeamPitData;
import frc3824.rscout2018.fragments.pit_scout.PitPictureFragment;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;

/**
 * @class PitScoutActivity
 * @brief The page for scouting an individual team
 */
@MakeActivityStarter
public class PitScoutActivity extends Activity
{
    private final static String TAG = "MatchScoutActivity";

    @Arg
    protected int mTeamNumber = -1;
    private boolean mPractice = false;

    private PitScoutFragmentPagerAdapter mFPA;
    private TeamPitData mTPD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout);
        ActivityStarter.fill(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int position = Integer.parseInt(sharedPreferences.getString(Constants.Settings.PIT_SCOUT_POSITION,
                                                                    "-1"));
        if (position == -1)
        {
            Log.e(TAG, "Error: impossible pit scout position");
            return;
        }

        // Setup header
        ScoutHeader header = findViewById(R.id.header);
        header.setInterface(new PitScoutHeader());

        // Real pit scouting
        if (mTeamNumber > 0)
        {
            header.setTitle(String.format("Team Number: %d",
                                          mTeamNumber));

            // todo: handle admin
            // If first team then remove the previous button
            ArrayList<Integer> teamNumbers = Database.getInstance().getTeamNumbers();
            int index = teamNumbers.indexOf(mTeamNumber);
            if (index - (teamNumbers.size() / 6 * position) <= 0)
            {
                header.removePrevious();
            }

            // todo: handle admin
            // If last team then remove the next button
            index = teamNumbers.indexOf(mTeamNumber);
            if (index >= (teamNumbers.size() / 6 * (position + 1)) - 1)
            {
                header.removeNext();
            }

            mTPD = new TeamPitData(mTeamNumber);
        }
        // Practice
        else
        {
            mPractice = true;
            header.setTitle("Practice Match");
            mTPD = new TeamPitData(-1);
            header.removeSave();
        }

        // Keep screen on while scouting
        findViewById(android.R.id.content).setKeepScreenOn(true);

        // Setup the TABS and fragment pages
        mFPA = new PitScoutFragmentPagerAdapter(getFragmentManager(),
                                                  mTPD);

        // Setup view pager
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mFPA);
        viewPager.setOffscreenPageLimit(mFPA.getCount());

        SmartTabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setViewPager(viewPager);
    }

    private class PitScoutHeader implements ScoutHeaderInterface
    {

        @Override
        public void previous()
        {
            if (mPractice)
            {
                PitScoutActivityStarter.start(PitScoutActivity.this, -1);
            }
            else
            {
                if (mTPD.isDirty())
                {
                    // todo: Save dialog
                }
                else
                {
                    ArrayList<Integer> teamNumbers = Database.getInstance().getTeamNumbers();
                    int index = teamNumbers.indexOf(mTeamNumber);
                    if (index > 0) // If null then this function shouldn't be possible to call as the button should have been hidden
                    {
                        PitScoutActivityStarter.start(PitScoutActivity.this, teamNumbers.get(index - 1));
                    }
                }
            }
        }

        @Override
        public void next()
        {
            if (mPractice)
            {
                PitScoutActivityStarter.start(PitScoutActivity.this, -1);
            }
            else
            {
                if (mTPD.isDirty())
                {
                    // todo: Save dialog
                }
                else
                {
                    ArrayList<Integer> teamNumbers = Database.getInstance().getTeamNumbers();
                    int index = teamNumbers.indexOf(mTeamNumber);
                    if (index < teamNumbers.size() - 1) // If null then this function shouldn't be possible to call as the button should have been hidden
                    {
                        PitScoutActivityStarter.start(PitScoutActivity.this, teamNumbers.get(index + 1));
                    }
                }
            }
        }

        @Override
        public void home()
        {
            if (mPractice)
            {
                HomeActivityStarter.start(PitScoutActivity.this);
            }
            else
            {
                if (mTPD.isDirty())
                {
                    // todo: Save dialog
                }
                else
                {
                    HomeActivityStarter.start(PitScoutActivity.this);
                }
            }
        }

        @Override
        public void list()
        {
            if (mPractice)
            {
                TeamListActivityStarter.start(PitScoutActivity.this, Constants.IntentExtras.NextPageOptions.PIT_SCOUTING);
            }
            else
            {
                if (mTPD.isDirty())
                {
                    // todo: Save dialog
                }
                else
                {
                    TeamListActivityStarter.start(PitScoutActivity.this, Constants.IntentExtras.NextPageOptions.PIT_SCOUTING);
                }
            }
        }

        @Override
        public void save()
        {
            if (!mPractice && mTPD.isDirty())
            {
                mTPD.save();
            }
        }
    }

    /**
     * @class PitScoutFragmentPagerAdapter
     * @brief Creates and manages the fragments that are displayed for pit scouting
     */
    private class PitScoutFragmentPagerAdapter extends FragmentPagerAdapter
    {
        static final String TAG = "MatchScoutFragmentPagerAdapter";

        String[] mTitles = {"Picture"}; //{ "Picture", "Dimensions", "Misc"};
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
         */
        @Override
        public Fragment getItem(int position)
        {
            assert (position >= 0 && position < mTitles.length);
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
                    assert (false);
            }
            return null;
        }

        /**
         * Returns the number of fragments
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
         *
         * @return The title of the fragment
         */
        @Override
        public String getPageTitle(int position)
        {
            assert (position >= 0 && position < mTitles.length);
            return mTitles[position];
        }
    }

}
