package frc3824.rscout2018.database.data_models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import frc3824.rscout2018.database.Database;


public class TeamLogistics extends DataModel
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
        notifyChange();
    }
    //endregion

    //region Nickname
    String nickname;

    /**
     * Getter function for {@link TeamLogistics#nickname}
     */
    @Bindable
    public String getNickname()
    {
        return nickname;
    }

    /**
     * Setter function for {@link TeamLogistics@nickname}
     * @param
     */
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
        notifyChange();
    }
    //endregion

    //region Match Numbers
    ArrayList<Integer> matchNumbers;

    /**
     * Getter for the numbers of the matches a specific team is in
     */
    @Bindable
    public ArrayList<Integer> getMatchNumbers()
    {
        return matchNumbers;
    }

    /**
     * Setter for the numbers of the matches a specific team is in
     */
    public void setMatchNumbers(ArrayList<Integer> matchNumbers)
    {
        assert(matchNumbers.size() == 6);
        this.matchNumbers = matchNumbers;
        notifyChange();
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
        super.save(String.format("tl_%d", teamNumber));
    }

    public void load()
    {
        super.load(String.format("tl_%d", teamNumber), Arrays.asList("teamNumber"));
    }
    //endregion
}
