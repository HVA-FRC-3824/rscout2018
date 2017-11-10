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


public class TeamLogistics extends BaseObservable
{
    //region Team Number
    int teamNumber;

    public int getTeamNumber()
    {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
        notifyPropertyChanged(BR.teamNumber);
    }
    //endregion

    //region Nickname
    String nickname;

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }
    //endregion

    //region Match Numbers
    ArrayList<Integer> matchNumbers;

    public ArrayList<Integer> getMatchNumbers()
    {
        return matchNumbers;
    }

    public void setMatchNumbers(ArrayList<Integer> matchNumbers)
    {
        assert(matchNumbers.size() == 6);
        this.matchNumbers = matchNumbers;
        notifyPropertyChanged(BR.matchNumbers);
    }
    //endregion

    //region Constructors
    public TeamLogistics(int teamNumber)
    {
        this.teamNumber = teamNumber;
        load();
    }
    //endregion

    //region Database
    public void save()
    {
        Document document = Database.getInstance().getDocument(String.format("tl_%d", teamNumber));
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
        Document document = Database.getInstance().getDocument(String.format("tl_%d", teamNumber));
        Map<String, Object> properties = document.getProperties();
        for(Field field: getClass().getDeclaredFields())
        {
            // Ignore as these were set in the constructor
            if (field.getName() == "teamNumber")
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
