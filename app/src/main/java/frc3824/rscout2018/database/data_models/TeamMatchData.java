package frc3824.rscout2018.database.data_models;

import android.databinding.Bindable;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import frc3824.rscout2018.BR;
import frc3824.rscout2018.database.Database;

/**
 * Data Model for a single team in a single match
 */
public class TeamMatchData extends DataModel
{
    //region Logistics
    //region Match Number
    int matchNumber;

    /**
     * Getter function for match number
     * @returns The match number
     */
    @Bindable
    public int getMatchNumber()
    {
        return matchNumber;
    }

    /**
     * Setter function for match number
     * @param matchNumber The match number
     */
    public void setMatchNumber(int matchNumber)
    {
        this.matchNumber = matchNumber;
        notifyChange();
    }
    //endregion
    //region Team Number
    int teamNumber;

    /**
     * Getter function for team number
     * @returns The team number
     */
    @Bindable
    public int getTeamNumber()
    {
        return teamNumber;
    }

    /**
     * Setter function for team number
     * @param teamNumber The team number
     */
    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
        notifyChange();
    }
    //endregion
    //region Scout Name
    String scoutName;

    /**
     * Getter function for scout name
     * @returns The name of the scout who recorded the information for {@link TeamMatchData#teamNumber} in {@link TeamMatchData#matchNumber}
     */
    @Bindable
    public String getScoutName()
    {
        return scoutName;
    }

    /**
     * Setter function for scout name
     * @param scoutName The name of the scout who recorded the information for {@link TeamMatchData#teamNumber} in {@link TeamMatchData#matchNumber}
     */
    public void setScoutName(String scoutName)
    {
        this.scoutName = scoutName;
        notifyChange();
    }
    //endregion
    //endregion

    //region Fouls
    //region Fouls
    int fouls;

    /**
     * Getter function for fouls
     * @returns The number of normal fouls caused by this team in this match
     */
    @Bindable
    public int getFouls()
    {
        return fouls;
    }

    /**
     * Setter function for fouls
     * @param fouls The number of normal fouls caused by this team in this match
     */
    public void setFouls(int fouls)
    {
        this.fouls = fouls;
        notifyChange();
    }
    //endregion
    //region Tech Fouls
    int techFouls;

    /**
     * Getter function for tech fouls
     * @returns The number of tech fouls caused by this team in this match
     */
    @Bindable
    public int getTechFouls()
    {
        return techFouls;
    }

    /**
     * Setter function for tech fouls
     * @param techFouls The number of tech fouls caused by this team in this match
     */
    public void setTechFouls(int techFouls)
    {
        this.techFouls = techFouls;
        notifyChange();
    }
    //endregion
    //region Yellow Card
    boolean yellowCard;

    /**
     * Getter function for yellow card
     *
     * @note The data binding system requires the function follow javabean naming convention
     *       and thus uses "is"
     *
     * @return Whether a yellow card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    @Bindable
    public boolean isYellowCard()
    {
        return yellowCard;
    }

    /**
     * Setter function for yellow card
     * @param yellowCard Whether a yellow card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    public void setYellowCard(boolean yellowCard)
    {
        this.yellowCard = yellowCard;
        notifyChange();
    }
    //endregion
    //region Red Card
    boolean redCard;

    /**
     * Getter function for red card
     *
     * @note The data binding system requires the function follow javabean naming convention
     *       and thus uses "is"
     *
     * @return Whether a red card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    @Bindable
    public boolean isRedCard()
    {
        return redCard;
    }

    /**
     * Setter function for red card
     * @param redCard Whether a red card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    public void setRedCard(boolean redCard)
    {
        this.redCard = redCard;
        notifyChange();
    }
    //endregion
    //endregion

    //region Misc
    //region DQ
    boolean dq;

    /**
     * Getter function for whether team {@link TeamMatchData#teamNumber} was disqualified in match {@link TeamMatchData#matchNumber}
     * @returns Whether team {@link TeamMatchData#teamNumber} was disqualified in match {@link TeamMatchData#matchNumber}
     */
    public boolean isDq()
    {
        return dq;
    }

    /**
     * Setter function for whether team {@link TeamMatchData#teamNumber} was disqualified in match {@link TeamMatchData#matchNumber}
     * @param dq Whether team {@link TeamMatchData#teamNumber} was disqualified in match {@link TeamMatchData#matchNumber}
     */
    public void setDq(boolean dq)
    {
        this.dq = dq;
        notifyChange();
    }
    //endregion
    //region No Show
    boolean noShow;

    /**
     * Getter function for whether team {@link TeamMatchData#teamNumber} did not show up to match {@link TeamMatchData#matchNumber}
     * @returns Whether team {@link TeamMatchData#teamNumber} did not show up to match {@link TeamMatchData#matchNumber}
     */
    public boolean isNoShow()
    {
        return noShow;
    }

    /**
     * Setter function for whether team {@link TeamMatchData#teamNumber} did not show up to match {@link TeamMatchData#matchNumber}
     * @param noShow Whether team {@link TeamMatchData#teamNumber} did not show up to match {@link TeamMatchData#matchNumber}
     */
    public void setNoShow(boolean noShow)
    {
        this.noShow = noShow;
        notifyChange();
    }
    //endregion
    //region Notes
    String notes;

    /**
     * Getter function for notes
     * @returns The notes taken on team {@link TeamMatchData#teamNumber} in match {@link TeamMatchData#matchNumber}
     */
    @Bindable
    public String getNotes()
    {
        return notes;
    }

    /**
     * Setter function for notes
     * @param notes The notes taken on team {@link TeamMatchData#teamNumber} in match {@link TeamMatchData#matchNumber}
     */
    public void setNotes(String notes)
    {
        this.notes = notes;
        notifyChange();
    }
    //endregion
    //endregion

    //region Game Specific
    //region Autonomous
    //region Test
    String test;

    @Bindable
    public String getTest()
    {
        return test;
    }

    public void setTest(String test)
    {
        this.test = test;
        notifyChange();
    }
    //endregion
    //endregion
    //region Teleop
    //endregion
    //region Endgame
    //endregion
    //endregion

    //region Constructors
    public TeamMatchData(int teamNumber, int matchNumber)
    {
        this.teamNumber = teamNumber;
        this.matchNumber = matchNumber;
        load();
    }
    //endregion

    //region Database
    public void save()
    {
        super.save(String.format("tmd_%d_%d", teamNumber, matchNumber));
    }

    public void load()
    {
        super.load(String.format("tmd_%d_%d", teamNumber, matchNumber), Arrays.asList("teamNumber", "matchNumber"));

    }
    //endregion
}
