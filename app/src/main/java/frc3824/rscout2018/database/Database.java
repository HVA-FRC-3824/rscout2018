package frc3824.rscout2018.database;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.Reducer;
import com.couchbase.lite.View;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import frc3824.rscout2018.database.data_models.MatchLogistics;
import frc3824.rscout2018.utilities.Constants;

/**
 *
 */

public class Database
{
    private Manager m_manager;
    private com.couchbase.lite.Database m_database;

    private static Database m_singleton = null;
    private String m_event_key;

    public static Database getInstance()
    {
        if(m_singleton == null)
        {
            m_singleton = new Database();
        }
        return m_singleton;
    }

    public void setContext(Context context) throws IOException
    {
        m_manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
    }

    public void setEventKey(String event_key)
    {
        if(event_key != m_event_key)
        {
            try
            {
                // The name must consist only of lowercase ASCII letters, digits, and
                // the special characters _$()+-/. It must also be less than 240 bytes
                // and start with a lower case letter.
                m_database = m_manager.getDatabase("frc_" + event_key);
            }
            catch (CouchbaseLiteException e)
            {
                e.printStackTrace();
            }
            m_event_key = event_key;
        }
    }

    public Document getDocument(String name)
    {
        return m_database.getDocument(name);
    }

    public int numberOfMatches()
    {
        // Create Data View
        View matches = m_database.getView("matches");

        // Setup Map Reduce
        matches.setMapReduce(new Mapper()
                             {
                                 @Override
                                 public void map(Map<String, Object> document, Emitter emitter)
                                 {
                                     // The map creates a list of all documents that are of type
                                     // MatchLogistics
                                    if(document.get("type") == "MatchLogistics")
                                    {
                                        emitter.emit(document.get("matchNumber"), 1);
                                    }
                                 }
                             },
                             new Reducer()
                             {
                                 @Override
                                 public Object reduce(List<Object> keys,
                                                      List<Object> values,
                                                      boolean rereduce)
                                 {
                                     // The reduce returns the number of them
                                     return values.size();
                                 }
                             }, "2");

        // Make the query
        Query query = matches.createQuery();
        QueryEnumerator result;
        try
        {
            result = query.run();
        }
        catch (CouchbaseLiteException e)
        {
            e.printStackTrace();
            return 0;
        }
        QueryRow row = result.next();
        if(row == null)
        {
            return 0;
        }
        return (int) row.getValue();
    }

    public ArrayList<Integer> getTeamNumbers()
    {
        View teams = m_database.getView("teams");
        teams.setMapReduce(new Mapper()
        {
            @Override
            public void map(Map<String, Object> document, Emitter emitter)
            {
                if (document.get("type") == "TeamLogistics")
                {
                    emitter.emit(document.get("teamNumber"), 1);
                }
            }
        }, new Reducer()
        {
            @Override
            public Object reduce(List<Object> keys, List<Object> values, boolean rereduce)
            {
                return keys;
            }
        }, "2");
        Query query = teams.createQuery();
        QueryEnumerator result = null;
        try
        {
            result = query.run();
        }
        catch (CouchbaseLiteException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
        QueryRow row = result.next();
        return (ArrayList<Integer>) row.getValue();
    }
    /*
    //region Super Scouting Data
    public void setSuperMatchData(SuperMatchData superMatchData) {
        superMatchData.last_modified = System.currentTimeMillis();
        mReferences.get(constants.Database_Lists.indices.SUPER).child(String.valueOf(superMatchData.match_number)).setValue(superMatchData);
    }

    public SuperMatchData getSuperMatchData(int match_number) {
        DataSnapshot d = mMaps.get(constants.Database_Lists.indices.SUPER).get(String.valueOf(match_number));
        if(d == null){
            return null;
        }
        return d.getValue(SuperMatchData.class);
    }

    public ArrayList<SuperMatchData> getAllSuperMatchData() {
        ArrayList<SuperMatchData> supers = new ArrayList<>();
        for(Map.Entry<String, DataSnapshot> entry: mMaps.get(Constants.Database_Lists.indices.SUPER).entrySet()){
            supers.add(entry.getValue().getValue(SuperMatchData.class));
        }
        return supers;
    }
    //endregion
    */
}
