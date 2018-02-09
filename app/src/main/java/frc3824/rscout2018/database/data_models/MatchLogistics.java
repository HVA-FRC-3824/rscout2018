package frc3824.rscout2018.database.data_models;

import android.databinding.Bindable;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import frc3824.rscout2018.database.Database;

/**
 * Data model containing the logistics information for a specific match
 */
public class MatchLogistics extends DataModel
{
    //region Match Number
    int matchNumber;
    public long lastModified;

    public int getMatchNumber()
    {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber)
    {
        this.matchNumber = matchNumber;
        notifyChange();
    }
    //endregion

    //region Team Numbers
    ArrayList<Integer> teamNumbers;

    public int getTeamNumber(int position)
    {
        assert(position >= 0 && position < teamNumbers.size());
        return teamNumbers.get(position);
    }

    /**
     * Getter function for the numbers of the teams in a given match
     */
    @Bindable
    public ArrayList<Integer> getTeamNumbers()
    {
        return teamNumbers;
    }

    /**
     * Setter function for the numbers of the teams in a given match
     */
    public void setTeamNumbers(ArrayList<Integer> teamNumbers)
    {
        assert(teamNumbers.size() == 6);
        this.teamNumbers = teamNumbers;
        notifyChange();
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

    public int getBlue1()
    {
        // todo
        return -1;
    }

    public int getBlue2()
    {
        // todo
        return -1;
    }

    public int getBlue3()
    {
        // todo
        return -1;
    }

    public int getRed1()
    {
        // todo
        return -1;
    }

    public int getRed2()
    {
        // todo
        return -1;
    }

    public int getRed3()
    {
        // todo
        return -1;
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
        super.save(String.format("ml_%d", matchNumber));
    }

    public void load()
    {
        super.load(String.format("ml_%d", matchNumber), Arrays.asList("matchNumber"));
    }
    //endregion
}
