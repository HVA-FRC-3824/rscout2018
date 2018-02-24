package frc3824.rscout2018.database;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import frc3824.rscout2018.database.data_models.MatchLogistics;
import frc3824.rscout2018.database.data_models.SuperMatchData;
import frc3824.rscout2018.database.data_models.TeamLogistics;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.database.data_models.TeamPitData;


/**
 *
 */

public class Database
{
    private static final String TAG = "Database";
    private static Database mSingleton = null;
    private String mEventKey;
    FirebaseDatabase mDatabase;
    Map<String, DatabaseReference> mReferences;
    Map<String, Map<String, DataSnapshot> > mMap;
    Set<String> mEvents;

    private static final String ROOT = "root";
    private static final String EVENT = "events";
    private static final String MATCH_LOGISTICS = "match_logistics";
    private static final String TEAM_MATCH = "partial_matches";
    private static final String TEAM_PIT = "pit";
    private static final String TEAM_LOGISTICS = "team_logistics";
    private static final String SUPER_MATCH = "super_match";

    public static Database getInstance()
    {
        if(mSingleton == null)
        {
            mSingleton = new Database();
        }
        return mSingleton;
    }

    private Database()
    {
        mReferences = new HashMap<>();
        mEvents = new HashSet<>();
        mMap = new HashMap<>();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.setPersistenceEnabled(true);

        mReferences.put(ROOT, mDatabase.getReference());
        mReferences.get(ROOT).keepSynced(true);
        mReferences.get(ROOT).addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                mEvents.add(dataSnapshot.getKey());
                Log.v(TAG, String.format("root.onChildAdded: %s", dataSnapshot.getKey()));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                mEvents.add(dataSnapshot.getKey());
                Log.v(TAG, String.format("root.onChildChanged: %s", dataSnapshot.getKey()));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                mEvents.remove(dataSnapshot.getKey());
                Log.v(TAG, String.format("root.onChildRemoved: %s", dataSnapshot.getKey()));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {
                Log.v(TAG, String.format("root.onChildMoved: %s", dataSnapshot.getKey()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.v(TAG, String.format("root.onCancelled"));
            }
        });
    }

    public void goOnline()
    {
        mDatabase.goOnline();
    }

    public void goOffline()
    {
        mDatabase.goOffline();
    }

    public void setEventKey(String eventKey)
    {
        if(mEventKey == null || mEventKey.isEmpty() || eventKey != mEventKey)
        {
            mEventKey = eventKey;

            mReferences.put(EVENT, mReferences.get(ROOT).child(mEventKey));

            addRef(MATCH_LOGISTICS);
            addRef(TEAM_LOGISTICS);
            addRef(TEAM_MATCH);
            addRef(TEAM_PIT);
            addRef(SUPER_MATCH);
        }
    }

    private void addRef(final String id)
    {
        mReferences.put(id, mReferences.get(EVENT).child(id));
        mMap.put(id, new HashMap<String, DataSnapshot>());

        mReferences.get(id).addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                mMap.get(id).put(dataSnapshot.getKey(), dataSnapshot);
                Log.v(TAG, String.format("%s.onChildAdded: %s", id, dataSnapshot.getKey()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                mMap.get(id).put(dataSnapshot.getKey(), dataSnapshot);
                Log.v(TAG, String.format("%s.onChildChanged: %s", id, dataSnapshot.getKey()));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                mMap.get(id).remove(dataSnapshot.getKey());
                Log.v(TAG, String.format("%s.onChildRemoved: %s", id, dataSnapshot.getKey()));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {
                Log.v(TAG, String.format("%s.onChildMoved: %s", id, dataSnapshot.getKey()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.v(TAG, String.format("%s.onCancelled", id));
            }
        });
    }

    //region Schedule
    public MatchLogistics getMatchLogistics(int matchNumber)
    {
        DataSnapshot dataSnapshot = mMap.get(MATCH_LOGISTICS).get(String.valueOf(matchNumber));
        if(dataSnapshot == null)
        {
            return null;
        }

        return dataSnapshot.getValue(MatchLogistics.class);
    }

    public void updateMatchLogistics(MatchLogistics matchLogistics)
    {
        mReferences.get(MATCH_LOGISTICS).child(String.valueOf(matchLogistics.getMatchNumber())).setValue(matchLogistics);
    }

    public ArrayList<MatchLogistics> getSchedule()
    {
        ArrayList<MatchLogistics> schedule = new ArrayList<>();
        for(Map.Entry<String, DataSnapshot> entry : mMap.get(MATCH_LOGISTICS).entrySet())
        {
            DataSnapshot dataSnapshot = entry.getValue();
            schedule.add(dataSnapshot.getValue(MatchLogistics.class));
        }
        return schedule;
    }

    public int numberOfMatches()
    {
        return mMap.get(MATCH_LOGISTICS).size();
    }
    //endregion

    //region Team Match Data
    public TeamMatchData getTeamMatchData(int teamNumber, int matchNumber)
    {
        DataSnapshot dataSnapshot = mMap.get(TEAM_MATCH).get(String.format("%d_%d", teamNumber, matchNumber));
        if(dataSnapshot == null)
        {
            return null;
        }
        return dataSnapshot.getValue(TeamMatchData.class);
    }

    public void updateTeamMatchData(TeamMatchData teamMatchData)
    {
        mReferences.get(TEAM_MATCH).child(String.format("%d_%d", teamMatchData.getTeamNumber(), teamMatchData.getMatchNumber())).setValue(teamMatchData);
    }
    //endregion

    //region Team Logistics
    public TeamLogistics getTeamLogistics(int teamNumber)
    {
        DataSnapshot dataSnapshot = mMap.get(TEAM_LOGISTICS).get(String.valueOf(teamNumber));
        if(dataSnapshot == null)
        {
            return null;
        }
        return dataSnapshot.getValue(TeamLogistics.class);
    }

    public void updateTeamLogistics(TeamLogistics teamLogistics)
    {
        mReferences.get(TEAM_LOGISTICS).child(String.valueOf(teamLogistics.getTeamNumber())).setValue(teamLogistics);
    }

    public ArrayList<Integer> getTeamNumbers()
    {
        ArrayList<Integer> keys = new ArrayList<>();
        for(String key: mMap.get(TEAM_LOGISTICS).keySet())
        {
            keys.add(Integer.parseInt(key));
        }
        return keys;
    }
    //endregion

    //region Team Pit Data
    public TeamPitData getTeamPitData(int teamNumber)
    {
        DataSnapshot dataSnapshot = mMap.get(TEAM_PIT).get(String.valueOf(teamNumber));
        if(dataSnapshot == null)
        {
            return null;
        }
        return dataSnapshot.getValue(TeamPitData.class);
    }

    public ArrayList<TeamPitData> getAllTeamPitData()
    {
        Collection<DataSnapshot> data = mMap.get(TEAM_PIT).values();

        ArrayList<TeamPitData> rv = new ArrayList<>();
        for(DataSnapshot snapshot : data)
        {
            if(snapshot != null)
            {
                rv.add(snapshot.getValue(TeamPitData.class));
            }
        }
        return rv;
    }

    public void updateTeamPitData(TeamPitData teamPitData)
    {
        Gson gson = new Gson();
        String json = gson.toJson(teamPitData);
        mReferences.get(TEAM_PIT).child(String.valueOf(teamPitData.getTeamNumber())).setValue(teamPitData);
    }
    //endregion

    //region SuperMatchData
    public void updateSuperMatchData(SuperMatchData superMatchData)
    {
        mReferences.get(SUPER_MATCH).child(String.valueOf(superMatchData.getMatchNumber())).setValue(superMatchData);
    }

    public SuperMatchData getSuperMatchData(int matchNumber)
    {
        DataSnapshot dataSnapshot = mMap.get(SUPER_MATCH).get(String.valueOf(matchNumber));
        if(dataSnapshot == null)
        {
            return null;
        }
        return dataSnapshot.getValue(SuperMatchData.class);
    }
    //endregion
}
