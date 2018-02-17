package frc3824.rscout2018.activities;

import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.utilities.Constants;

/**
 * @class TeamListActivity
 * @brief Activity that displays all the teams to select from. Determines which activity to start
 *        based on the intent extra {@link TeamListActivity#nextPage} passed to it.
 */
@MakeActivityStarter
public class TeamListActivity extends ListActivity implements View.OnClickListener
{
    @Arg
    String nextPage;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_list);
        ActivityStarter.fill(this);

        findViewById(R.id.practice).setOnClickListener(this);

        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(new TeamListAdapter());
    }

    @Override
    public void onClick(View view)
    {
        PitScoutActivityStarter.start(this, -1);
    }

    /**
     * @class TeamListAdapter
     * @brief The {@link ListAdapter} for showing the list of teams
     */
    private class TeamListAdapter implements ListAdapter, View.OnClickListener
    {
        LayoutInflater mLayoutInflator;
        ArrayList<Integer> mTeamNumbers;

        /**
         * Constructor
         */
        TeamListAdapter()
        {
            mLayoutInflator = getLayoutInflater();
            mTeamNumbers = Database.getInstance().getTeamNumbers();
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
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {}

        /**
         * {@inheritDoc}
         */
        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {}

        /**
         * {@inheritDoc}
         */
        @Override
        public int getCount()
        {
            return mTeamNumbers.size();
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
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            if(view == null)
            {
                view = mLayoutInflator.inflate(R.layout.list_item_fbutton, null);
            }


            int teamNumber = mTeamNumbers.get(i);


            ((TextView)view).setText(String.format("Team: %d", teamNumber));

            view.setId(teamNumber);
            view.setOnClickListener(this);

            return null;
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
            return mTeamNumbers.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onClick(View view)
        {
            switch (nextPage)
            {
                case Constants.IntentExtras.NextPageOptions.PIT_SCOUTING:
                    // PitScoutActivityStarter.start(view.getId());
                    break;
                case Constants.IntentExtras.NextPageOptions.TEAM_STATS:
                    // TeamViewActivityStarter.start(view.getId());
                    break;
                default:
                    assert(false);
            }
        }
    }
}
