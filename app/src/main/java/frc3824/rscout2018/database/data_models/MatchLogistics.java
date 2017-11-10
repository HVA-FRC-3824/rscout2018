package frc3824.rscout2018.database.data_models;

import android.databinding.BaseObservable;

import com.android.databinding.library.baseAdapters.BR;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frc3824.rscout2018.database.Database;

/**
 * Data model containing the logistics information for a specific match
 */
public class MatchLogistics extends BaseObservable
{


    //region Match Number
    int matchNumber;

    public int getMatchNumber()
    {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber)
    {
        this.matchNumber = matchNumber;
        notifyPropertyChanged(BR.matchNumber);
    }
    //endregion

    //region Team Numbers
    ArrayList<Integer> teamNumbers;

    public int getTeamNumber(int position)
    {
        assert(position >= 0 && position < teamNumbers.size());
        return teamNumbers.get(position);
    }

    public ArrayList<Integer> getTeamNumbers()
    {
        return teamNumbers;
    }

    public void setTeamNumbers(ArrayList<Integer> teamNumbers)
    {
        assert(teamNumbers.size() == 6);
        this.teamNumbers = teamNumbers;
        notifyPropertyChanged(BR.teamNumbers);
    }
    //endregion

    public boolean isRed(int teamNumber)
    {
        // todo
        return false;
    }

    public boolean isBlue(int teamNumber)
    {
        // todo
        return false;
    }

    //region Constructors
    public MatchLogistics(int matchNumber)
    {
        this.matchNumber = matchNumber;
        load();
    }
    //endregion

    //region Database
    public void save()
    {
        Document document = Database.getInstance().getDocument(String.format("ml_%d", matchNumber));
        Map<String, Object> properties = new HashMap<>();
        for(Field field: getClass().getDeclaredFields())
        {
            try
            {
                properties.put(field.getName(), field.get(this));
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            document.putProperties(properties);
        }
        catch (CouchbaseLiteException e)
        {
            e.printStackTrace();
        }
    }

    public void load()
    {
        Document document = Database.getInstance().getDocument(String.format("ml_%d", matchNumber));
        Map<String, Object> properties = document.getProperties();
        for(Field field: getClass().getDeclaredFields())
        {
            // Ignore as this was set in the constructor
            if (field.getName() == "matchNumber")
            {
                continue;
            }
            if(properties.containsKey(field.getName()))
            {
                Object property = properties.get(field.getName());
                try
                {
                    field.set(this, property);
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    //endregion
}
