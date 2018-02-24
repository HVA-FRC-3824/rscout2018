package frc3824.rscout2018.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.sdsmdg.tastytoast.TastyToast;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.MatchLogistics;
import frc3824.rscout2018.database.data_models.SuperMatchData;
import frc3824.rscout2018.fragments.super_scout.SuperNotesFragment;
import frc3824.rscout2018.fragments.super_scout.SuperPowerUpFragment;
import frc3824.rscout2018.services.CommunicationService;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;


public class SuperScoutActivity extends RScoutActivity
{
    @Arg
    protected int mMatchNumber = -1;
    private boolean mPractice = false;
    SuperScoutFragmentPagerAdapter mFPA;

    private SuperMatchData mSMD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout);
        ActivityStarter.fill(this);


        ScoutHeader header = findViewById(R.id.header);
        header.setInterface(new SuperScoutActivity.SuperScoutHeader());

        // Normal match
        if (mMatchNumber > 0)
        {
            MatchLogistics m = new MatchLogistics(mMatchNumber);
            header.setTitle(String.format("Match Number: %d",
                                          mMatchNumber));

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

            mSMD = new SuperMatchData(mMatchNumber);
        }
        // Practice Match
        else
        {
            mPractice = true;
            header.setTitle("Practice Match");
            mSMD = new SuperMatchData( -1);
            header.removeSave();
        }

        // Keep screen on while scouting
        findViewById(android.R.id.content).setKeepScreenOn(true);

        // Setup the TABS and fragment pages
        mFPA = new SuperScoutActivity.SuperScoutFragmentPagerAdapter(getFragmentManager(),
                                                                     mSMD);

        // Setup view pager
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mFPA);
        viewPager.setOffscreenPageLimit(mFPA.getCount());

        SmartTabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(Color.BLUE);
        tabLayout.setViewPager(viewPager);
    }

    /**
     * Inner class that handles pressing the buttons on the header
     */
    private class SuperScoutHeader implements ScoutHeaderInterface
    {

        /**
         * Previous button press handler
         */
        public void previous()
        {
            if (mPractice) // Don't need to worry about saving for practice
            {
                MatchScoutActivityStarter.start(SuperScoutActivity.this, -1);
            }
            else
            {
                if (mSMD.isDirty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuperScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Save to local database
                                    Database.getInstance().updateSuperMatchData(mSMD);

                                    // Send to the background service to be sent to server
                                    Intent intent = new Intent(SuperScoutActivity.this, CommunicationService.class);
                                    intent.putExtra(Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING, true);
                                    intent.putExtra(Constants.IntentExtras.MATCH_NUMBER, mMatchNumber);
                                    startService(intent);

                                    SuperScoutActivityStarter.start(SuperScoutActivity.this, mMatchNumber - 1);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    SuperScoutActivityStarter.start(SuperScoutActivity.this, mMatchNumber - 1);
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
                    SuperScoutActivityStarter.start(SuperScoutActivity.this, mMatchNumber - 1);
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
                MatchScoutActivityStarter.start(SuperScoutActivity.this, -1);
            }
            else
            {
                if (mSMD.isDirty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuperScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Save to local database
                                    Database.getInstance().updateSuperMatchData(mSMD);

                                    // Send to the background service to be sent to server
                                    Intent intent = new Intent(SuperScoutActivity.this, CommunicationService.class);
                                    intent.putExtra(Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING, true);
                                    intent.putExtra(Constants.IntentExtras.MATCH_NUMBER, mMatchNumber);
                                    startService(intent);

                                    SuperScoutActivityStarter.start(SuperScoutActivity.this, mMatchNumber + 1);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    SuperScoutActivityStarter.start(SuperScoutActivity.this, mMatchNumber + 1);
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
                    SuperScoutActivityStarter.start(SuperScoutActivity.this, mMatchNumber + 1);
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
                HomeActivityStarter.start(SuperScoutActivity.this);
            }
            else
            {
                if (mSMD.isDirty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuperScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            // Set action for clicking yes
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Save to local database
                                    Database.getInstance().updateSuperMatchData(mSMD);

                                    // Send to the background service to be sent to server
                                    Intent intent = new Intent(SuperScoutActivity.this, CommunicationService.class);
                                    intent.putExtra(Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING, true);
                                    intent.putExtra(Constants.IntentExtras.MATCH_NUMBER, mMatchNumber);
                                    startService(intent);

                                    HomeActivityStarter.start(SuperScoutActivity.this);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    HomeActivityStarter.start(SuperScoutActivity.this);
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
                    HomeActivityStarter.start(SuperScoutActivity.this);
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
                MatchListActivityStarter.start(SuperScoutActivity.this, Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING);
            }
            else
            {
                if (mSMD.isDirty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuperScoutActivity.this)
                            .setTitle("Unsaved Changes")
                            .setMessage("You have unsaved changes. Would you like to save them?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // Save to local database
                                    Database.getInstance().updateSuperMatchData(mSMD);

                                    // Send to the background service to be sent to server
                                    Intent intent = new Intent(SuperScoutActivity.this, CommunicationService.class);
                                    intent.putExtra(Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING, true);
                                    intent.putExtra(Constants.IntentExtras.MATCH_NUMBER, mMatchNumber);
                                    startService(intent);

                                    MatchListActivityStarter.start(SuperScoutActivity.this, Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    MatchListActivityStarter.start(SuperScoutActivity.this, Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING);
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
                    MatchListActivityStarter.start(SuperScoutActivity.this, Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING);
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
                // Save to local database
                Database.getInstance().updateSuperMatchData(mSMD);

                // Send to the background service to be sent to server
                Intent intent = new Intent(SuperScoutActivity.this, CommunicationService.class);
                intent.putExtra(Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING, true);
                intent.putExtra(Constants.IntentExtras.MATCH_NUMBER, mMatchNumber);
                startService(intent);
            }
        }
    }

    /**
     * @class MatchScoutFragmentPagerAdapter
     *
     * Creates multiple fragments based for the match scout activity
     */
    private class SuperScoutFragmentPagerAdapter extends FragmentPagerAdapter
    {
        static final String TAG = "MatchScoutFragmentPagerAdapter";

        SuperMatchData mSuperMatchData;


        public SuperScoutFragmentPagerAdapter(FragmentManager fm, SuperMatchData superMatchData)
        {
            super(fm);
            mSuperMatchData = superMatchData;
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
                    SuperPowerUpFragment spuf = new SuperPowerUpFragment();
                    spuf.setSuperMatchData(mSuperMatchData);
                    return spuf;
                case 1:
                    SuperNotesFragment snf = new SuperNotesFragment();
                    snf.setSuperMatchData(mSuperMatchData);
                    return snf;
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
            return Constants.SuperScouting.TABS.length;
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
            assert(position >= 0 && position < Constants.SuperScouting.TABS.length);
            return Constants.SuperScouting.TABS[position];
        }
    }
}
