package frc3824.rscout2018.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import frc3824.rscout2018.fragments.pit_scout.PitDimensionsFragment;
import frc3824.rscout2018.fragments.pit_scout.PitMiscFragment;
import frc3824.rscout2018.fragments.pit_scout.PitPictureFragment;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;

/**
 * @class PitScoutActivity
 * @brief The page for scouting an individual team
 */
@MakeActivityStarter
public class PitScoutActivity extends RScoutActivity
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

            mTPD = Database.getInstance().getTeamPitData(mTeamNumber);
            if(mTPD == null)
            {
                mTPD = new TeamPitData(mTeamNumber);
            }
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
        tabLayout.setBackgroundColor(Color.BLUE);
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
                final ArrayList<Integer> teamNumbers = Database.getInstance().getTeamNumbers();
                final int index = teamNumbers.indexOf(mTeamNumber);
                if (mTPD.isDirty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PitScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Save to local database
                                    Database.getInstance().updateTeamPitData(mTPD);

                                    PitScoutActivityStarter.start(PitScoutActivity.this, teamNumbers.get(index - 1));
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    PitScoutActivityStarter.start(PitScoutActivity.this, teamNumbers.get(index - 1));
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
                else
                {
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
                final ArrayList<Integer> teamNumbers = Database.getInstance().getTeamNumbers();
                final int index = teamNumbers.indexOf(mTeamNumber);

                if (mTPD.isDirty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PitScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Save to local database
                                    Database.getInstance().updateTeamPitData(mTPD);

                                    PitScoutActivityStarter.start(PitScoutActivity.this, teamNumbers.get(index + 1));
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    PitScoutActivityStarter.start(PitScoutActivity.this, teamNumbers.get(index + 1));
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
                else
                {
                    PitScoutActivityStarter.start(PitScoutActivity.this, teamNumbers.get(index + 1));
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(PitScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Save to local database
                                    Database.getInstance().updateTeamPitData(mTPD);

                                    HomeActivityStarter.start(PitScoutActivity.this);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    HomeActivityStarter.start(PitScoutActivity.this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(PitScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Save to local database
                                    Database.getInstance().updateTeamPitData(mTPD);

                                    TeamListActivityStarter.start(PitScoutActivity.this, Constants.IntentExtras.NextPageOptions.PIT_SCOUTING);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    TeamListActivityStarter.start(PitScoutActivity.this, Constants.IntentExtras.NextPageOptions.PIT_SCOUTING);
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
                Database.getInstance().updateTeamPitData(mTPD);
                int breakpoint=-1;
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
            assert (position >= 0 && position < Constants.PitScouting.TABS.length);
            switch (position)
            {
                case 0:
                    PitPictureFragment ppf = new PitPictureFragment();
                    ppf.setData(mTeamPitData);
                    return ppf;
                case 1:
                    PitDimensionsFragment pdf = new PitDimensionsFragment();
                    pdf.setData(mTeamPitData);
                    return pdf;
                case 2:
                    PitMiscFragment pmf = new PitMiscFragment();
                    pmf.setData(mTeamPitData);
                    return pmf;
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
            return Constants.PitScouting.TABS.length;
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
            assert (position >= 0 && position < Constants.PitScouting.TABS.length);
            return Constants.PitScouting.TABS[position];
        }
    }

}
