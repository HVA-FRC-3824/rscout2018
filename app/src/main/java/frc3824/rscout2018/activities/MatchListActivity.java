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

import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.data_models.MatchLogistics;
import frc3824.rscout2018.utilities.Constants;
import io.realm.Realm;

/**
 * @class MatchListActivity
 *
 */
@MakeActivityStarter
public class MatchListActivity extends ListActivity
{
    @Arg
    String next_page;

    int mNumberOfMatches;
    int mMatchScoutPosition;
    Realm mDatabase;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_match_list);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mMatchScoutPosition = sharedPreferences.getInt(Constants.Settings.MATCH_SCOUT_POSITION, -1);

        mDatabase = Realm.getDefaultInstance();
        mNumberOfMatches = (int)mDatabase.where(MatchLogistics.class).count();

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(new MatchListAdapter());
    }

   private class MatchListAdapter implements ListAdapter, View.OnClickListener
    {
        LayoutInflater mLayoutInflator;
        Realm mRealm;
        Map<Integer, Integer> mTeamNumbers;

        MatchListAdapter()
        {
            mLayoutInflator = getLayoutInflater();
            mRealm = Realm.getDefaultInstance();
            if(next_page == Constants.IntentExtras.NextPage.MATCH_SCOUTING)
            {
                mTeamNumbers = new HashMap<>();
            }
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
            return mNumberOfMatches;
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
        public View getView(int position, View view, ViewGroup viewGroup)
        {
            if(view == null)
            {
                switch(next_page)
                {
                    case Constants.IntentExtras.NextPage.MATCH_SCOUTING:
                        view = mLayoutInflator.inflate(R.layout.list_item_textview, null);
                        break;
                    case Constants.IntentExtras.NextPage.MATCH_VIEW:
                    case Constants.IntentExtras.NextPage.SUPER_SCOUTING:
                        view = mLayoutInflator.inflate(R.layout.list_item_textview, null);
                        break;
                }
            }

            switch(next_page)
            {
                case Constants.IntentExtras.NextPage.MATCH_SCOUTING:
                    int teamNumber;
                    if(mTeamNumbers.containsKey(position + 1))
                    {
                        teamNumber = mTeamNumbers.get(position + 1);
                    }
                    else
                    {
                        MatchLogistics m = mRealm.where(MatchLogistics.class).equalTo(Constants.Database.PrimaryKeys.MATCH_LOGISTICS, position + 1).findFirst();
                        if(m == null)
                        {
                            // error
                        }
                        teamNumber = m.getTeamNumber(mMatchScoutPosition);
                        mTeamNumbers.put(position + 1, teamNumber);
                    }
                    ((TextView)view).setText(String.format("Match: %d Team: %d", position + 1, teamNumber));
                    break;
                case Constants.IntentExtras.NextPage.MATCH_VIEW:
                case Constants.IntentExtras.NextPage.SUPER_SCOUTING:
                    ((TextView)view).setText(String.format("Match: %d", position + 1));
                    break;
            }

            view.setId(position + 1);
            view.setOnClickListener(this);

            return view;
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
            return mNumberOfMatches  == 0;
        }

        @Override
        public void onClick(View view)
        {
            switch (next_page)
            {
                case Constants.IntentExtras.NextPage.MATCH_SCOUTING:
                    MatchScoutActivityStarter.start(view.getId());
                    break;
                case Constants.IntentExtras.NextPage.SUPER_SCOUTING:
                    // SuperScoutActivityStarter.start(view.getId());
                    break;
                case Constants.IntentExtras.NextPage.MATCH_VIEW:
                    // MatchViewActivityStarter.start(view.getId());
                    break;
                default:
                    assert(false);
            }
        }
    }
}
