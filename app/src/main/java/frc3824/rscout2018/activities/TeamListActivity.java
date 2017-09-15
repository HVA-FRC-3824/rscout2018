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

import java.util.HashMap;
import java.util.Map;

import activitystarter.Arg;
import frc3824.rscout2018.R;
import frc3824.rscout2018.data_models.MatchLogistics;
import frc3824.rscout2018.data_models.TeamLogistics;
import frc3824.rscout2018.utilities.Constants;
import io.realm.Realm;

/**
 *
 */
public class TeamListActivity extends ListActivity
{
    @Arg
    String next_page;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_match_list);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(new TeamListAdapter());
    }

    private class TeamListAdapter implements ListAdapter, View.OnClickListener
    {
        LayoutInflater mLayoutInflator;
        Realm mDatabase;
        Map<Integer, Integer> mTeamNumbers;
        Integer mNumberOfTeams = null;

        TeamListAdapter()
        {
            mLayoutInflator = getLayoutInflater();
            mDatabase = Realm.getDefaultInstance();
            mTeamNumbers = new HashMap<>();
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        @Override
        public boolean isEnabled(int i)
        {
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver)
        {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver)
        {

        }

        @Override
        public int getCount()
        {
            if(mNumberOfTeams == null)
            {
                mNumberOfTeams = (int)mDatabase.where(TeamLogistics.class).count();
            }
            return mNumberOfTeams;
        }

        @Override
        public Object getItem(int i)
        {
            return null;
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            if(view == null)
            {
                view = mLayoutInflator.inflate(R.layout.list_item_textview, null);
            }


            int teamNumber;
            if(!mTeamNumbers.containsKey(i))
            {
                teamNumber = mDatabase.where(TeamLogistics.class).findAll().get(i).getTeamNumber();
                mTeamNumbers.put(i, teamNumber);
            }
            else
            {
                teamNumber = mTeamNumbers.get(i);
            }

            ((TextView)view).setText(String.format("Team: %d", teamNumber));

            view.setId(teamNumber);
            view.setOnClickListener(this);

            return null;
        }

        @Override
        public int getItemViewType(int i)
        {
            return 0;
        }

        @Override
        public int getViewTypeCount()
        {
            return 0;
        }

        @Override
        public boolean isEmpty()
        {
            if(mNumberOfTeams == null)
            {
                mNumberOfTeams = (int)mDatabase.where(TeamLogistics.class).count();
            }
            return mNumberOfTeams == 0;
        }

        @Override
        public void onClick(View view)
        {
            switch (next_page)
            {
                case Constants.IntentExtras.NextPage.PIT_SCOUTING:
                    // PitScoutActivityStarter.start(view.getId());
                    break;
                case Constants.IntentExtras.NextPage.TEAM_VIEW:
                    // TeamViewActivityStarter.start(view.getId());
                    break;
                default:
                    assert(false);
            }
        }
    }
}
