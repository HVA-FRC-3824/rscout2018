package frc3824.rscout2018.database.data_models;

import android.databinding.Bindable;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import frc3824.rscout2018.BR;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.powered_up.CubeEvent;
import frc3824.rscout2018.database.data_models.powered_up.DropData;

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
    //region Crossed Auto Line
    boolean crossedAutoLine;

    /**
     * Returns whether the team crossed the auto line
     * @return
     */
    @Bindable
    public boolean getCrossedAutoLine()
    {
        return  crossedAutoLine;
    }

    /**
     * Sets whether the team crossed the auto line
     */
    public void setCrossedAutoLine(boolean crossedAutoLine)
    {
        this.crossedAutoLine = crossedAutoLine;
        notifyChange();
    }
    //endregion
    //region Start Location X
    float startLocationX;

    /**
     * Returns the start location x as a percentage of the width of the field
     */
    @Bindable
    public float getStartLocationX()
    {
        return startLocationX;
    }

    /**
     * Sets the start location x as a percentage of the width of the field
     */
    public void setStartLocationX(float startLocationX)
    {
        this.startLocationX = startLocationX;
        notifyChange();
    }
    //endregion
    //region Start Location Y
    float startLocationY;

    /**
     * Returns the start location y as a percentage of the depth of the field
     */
    @Bindable
    public float getStartLocationY()
    {
        return startLocationY;
    }

    /**
     * Sets the start location y as a percentage of the depth of the field
     */
    public void setStartLocationY(float startLocationY)
    {
        this.startLocationY = startLocationY;
        notifyChange();
    }
    //endregion
    //region Cube Events
    ArrayList<CubeEvent> autoCubeEvents;

    @Bindable
    public ArrayList<CubeEvent> getAutoCubeEvents()
    {
        return autoCubeEvents;
    }

    public void setAutoCubeEvents(ArrayList<CubeEvent> autoCubeEvents)
    {
        this.autoCubeEvents = autoCubeEvents;
        notifyChange();
    }
    //endregion
    //endregion
    //region Teleop
    //region Cube Events
    ArrayList<CubeEvent> teleopCubeEvents;

    @Bindable
    public ArrayList<CubeEvent> getTeleopCubeEvents()
    {
        return teleopCubeEvents;
    }

    public void setTeleopCubeEvents(ArrayList<CubeEvent> teleopCubeEvents)
    {
        this.teleopCubeEvents = teleopCubeEvents;
        notifyChange();
    }
    //endregion
    //endregion
    //region Endgame
    //region Climb Status
    String climbStatus;

    /**
     * Returns the status of the climb (whether or not
     * it was successful and if not then why)
     */
    @Bindable
    public String getClimbStatus()
    {
        return climbStatus;
    }

    /**
     * Set the status of the climb (whether or not
     * it was successful and if not then why)
     */
    public void setClimbStatus(String climbStatus)
    {
        this.climbStatus = climbStatus;
        notifyChange();
    }
    //endregion
    //region Climb Method
    String climbMethod;

    /**
     * Returns the method of the climb upon a
     * successful climb
     */
    @Bindable
    public String getClimbMethod()
    {
        return climbMethod;
    }

    /**
     * Set the method of the climb upon a
     * successful climb
     */
    public void setClimbMethod(String climbMethod)
    {
        this.climbMethod = climbMethod;
        notifyChange();
    }
    //endregion
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
