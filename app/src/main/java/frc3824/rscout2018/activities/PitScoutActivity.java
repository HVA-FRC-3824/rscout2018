package frc3824.rscout2018.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.adapters.fpa.PitScoutFragmentPagerAdapter;
import frc3824.rscout2018.data_models.TeamPitData;
import frc3824.rscout2018.fragments.pit_scout.PitPictureFragment;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.Sort;

/**
 * @class PitScoutActivity
 * @brief The page for scouting an individual team
 */
@MakeActivityStarter
public class PitScoutActivity extends Activity implements RealmChangeListener<TeamPitData>
{
    private final static String TAG = "MatchScoutActivity";

    @Arg
    protected int mTeamNumber = -1;
    private boolean mPractice = false;

    private Realm mDatabase;

    private PitScoutFragmentPagerAdapter mFPA;
    private TeamPitData mTPD;
    private boolean mDirty = false;

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
            // Shouldn't be possible to get here
            // todo: Error
        }

        ScoutHeader header = findViewById(R.id.header);
        header.setInterface(new PitScoutHeader());

        mDatabase = Realm.getDefaultInstance();

        if (mTeamNumber > 0)
        {
            header.setTitle(String.format("Team Number: %d",
                                          mTeamNumber));

            if (mDatabase.where(TeamPitData.class)
                         .lessThan(Constants.Database.PrimaryKeys.TEAM_LOGISTICS, mTeamNumber)
                         .count() > 0)
            {
                header.removePrevious();
            }

            if (mDatabase.where(TeamPitData.class)
                         .greaterThan(Constants.Database.PrimaryKeys.TEAM_LOGISTICS, mTeamNumber)
                         .count() > 0)
            {
                header.removeNext();
            }

            mTPD = mDatabase.where(TeamPitData.class)
                            .equalTo(Constants.Database.PrimaryKeys.TEAM_MATCH_DATA,
                                     mTeamNumber)
                            .findFirst();
            if (mTPD == null)
            {
                mDatabase.beginTransaction();
                mTPD = mDatabase.createObject(TeamPitData.class, mTeamNumber);
                mDatabase.commitTransaction();
            }
            mTPD.addChangeListener(this);
        }
        else
        {
            mPractice = true;
            header.setTitle("Practice Match");
            mTPD = new TeamPitData();
            header.removeSave();
        }
    }

    @Override
    public void onChange(TeamPitData teamPitData)
    {
        mDirty = true;
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
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
                {
                    TeamPitData tpd = mDatabase.where(TeamPitData.class)
                                               .lessThan(Constants.Database.PrimaryKeys.TEAM_PIT_DATA,
                                                         mTeamNumber)
                                               .findAllSorted(Constants.Database.PrimaryKeys.TEAM_PIT_DATA,
                                                              Sort.DESCENDING)
                                               .first();
                    if (tpd != null) // If null then this function shouldn't be possible to call as the button should have been hidden
                    {
                        PitScoutActivityStarter.start(PitScoutActivity.this, tpd.getTeamNumber());
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
                if (mDirty)
                {
                    // todo: Save dialog
                }
                else
                {
                    TeamPitData tpd = mDatabase.where(TeamPitData.class)
                                               .greaterThan(Constants.Database.PrimaryKeys.TEAM_PIT_DATA,
                                                            mTeamNumber)
                                               .findAllSorted(Constants.Database.PrimaryKeys.TEAM_PIT_DATA,
                                                              Sort.ASCENDING)
                                               .first();
                    if (tpd != null) // If null then this function shouldn't be possible to call as the button should have been hidden
                    {
                        PitScoutActivityStarter.start(PitScoutActivity.this, tpd.getTeamNumber());
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
                if (mDirty)
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
                if (mDirty)
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
            if (!mPractice && mDirty)
            {
                mDatabase.beginTransaction();
                mDatabase.copyToRealmOrUpdate(mTPD);
                mDatabase.commitTransaction();
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
