package frc3824.rscout2018.activities;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.data_models.MatchLogistics;
import frc3824.rscout2018.utilities.Constants;
import io.realm.Realm;

/**
 * @class MatchListActivity
 * @brief Activity that displays all the matches to select from. Determines which activity to start
 *        based on the intent extra {@link MatchListActivity#mNextPage}passed to it.
 */
@MakeActivityStarter
public class MatchListActivity extends ListActivity implements View.OnClickListener
{
    @Arg
    protected String mNextPage;

    int mMatchScoutPosition;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_match_list);

        ActivityStarter.fill(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try
        {
            mMatchScoutPosition = Integer.parseInt(sharedPreferences.getString(Constants.Settings.MATCH_SCOUT_POSITION,
                                                                               ""));
        }
        catch (NumberFormatException e)
        {
            // todo error
        }

        findViewById(R.id.practice).setOnClickListener(this);

        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(new MatchListAdapter());
    }

    /**
     * Practice button clicked
     * {@inheritDoc}
     */
    @Override
    public void onClick(View view)
    {
        MatchScoutActivityStarter.start(this, -1);
    }

    /**
     * @class MatchListAdapter
     * @brief The {@link ListAdapter} for showing the list of matches
     */
    private class MatchListAdapter implements ListAdapter, View.OnClickListener
    {
        LayoutInflater mLayoutInflator;
        Realm mDatabase;
        int mNumberOfMatches;
        Map<Integer, Integer> mTeamNumbers;

        /**
         * Constructor
         */
        MatchListAdapter()
        {
            mLayoutInflator = getLayoutInflater();
            mDatabase = Realm.getDefaultInstance();
            if (mNextPage == Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING)
            {
                mTeamNumbers = new HashMap<>();
            }
            mNumberOfMatches = (int) mDatabase.where(MatchLogistics.class).count();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEnabled(int i)
        {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver)
        {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver)
        {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getCount()
        {
            return mNumberOfMatches;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getItem(int i)
        {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getItemId(int i)
        {
            return i;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public View getView(int position, View view, ViewGroup viewGroup)
        {
            // Inflate the view if it is null
            if (view == null)
            {
                switch (mNextPage)
                {
                    case Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING:
                        if (mMatchScoutPosition < 6)
                        {
                            view = mLayoutInflator.inflate(R.layout.list_item_fbutton, null);
                        }
                        else // mMatchScoutPosition == 6 (all)
                        {
                            // TODO: 9/20/17  Admin
                        }
                        break;
                    case Constants.IntentExtras.NextPageOptions.MATCH_VIEW:
                    case Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING:
                        view = mLayoutInflator.inflate(R.layout.list_item_fbutton, null);
                        break;
                }
            }

            switch (mNextPage)
            {
                case Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING:
                    if (mMatchScoutPosition < 6)
                    {
                        int teamNumber;
                        if (mTeamNumbers.containsKey(position + 1))
                        {
                            teamNumber = mTeamNumbers.get(position + 1);
                        }
                        else
                        {
                            MatchLogistics m = mDatabase.where(MatchLogistics.class)
                                                        .equalTo(Constants.Database.PrimaryKeys.MATCH_LOGISTICS,
                                                                 position + 1)
                                                        .findFirst();
                            if (m == null)
                            {
                                // error
                            }
                            teamNumber = m.getTeamNumber(mMatchScoutPosition);
                            mTeamNumbers.put(position + 1, teamNumber);
                        }
                        ((TextView) view).setText(String.format("Match: %d Team: %d",
                                                                position + 1,
                                                                teamNumber));
                    }
                    else
                    {
                        // TODO: 9/20/17  Admin
                    }
                    break;
                case Constants.IntentExtras.NextPageOptions.MATCH_VIEW:
                case Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING:
                    ((TextView) view).setText(String.format("Match: %d", position + 1));
                    break;
            }

            view.setId(position + 1);
            if (!view.hasOnClickListeners())
            {
                view.setOnClickListener(this);
            }

            return view;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getItemViewType(int i)
        {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getViewTypeCount()
        {
            return 1;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty()
        {
            return mNumberOfMatches == 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onClick(View view)
        {
            switch (mNextPage)
            {
                case Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING:
                    MatchScoutActivityStarter.start(MatchListActivity.this, view.getId());
                    break;
                case Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING:
                    // SuperScoutActivityStarter.start(view.getId());
                    break;
                case Constants.IntentExtras.NextPageOptions.MATCH_VIEW:
                    // MatchViewActivityStarter.start(view.getId());
                    break;
                default:
                    assert (false);
            }
        }
    }
}
