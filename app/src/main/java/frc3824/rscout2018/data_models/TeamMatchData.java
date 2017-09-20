package frc3824.rscout2018.data_models;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import frc3824.rscout2018.BR;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Data Model for a single team in a single match
 */
public class TeamMatchData extends RealmObject implements Observable
{
    //region Observable
    @Ignore
    private PropertyChangeRegistry mPropertyChangeRegistry;

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback)
    {
        mPropertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback)
    {
        mPropertyChangeRegistry.remove(callback);
    }
    //endregion

    //region Logistics
    @PrimaryKey
    String id; //!< Solely for the database

    //region Match Number
    int matchNumber;

    /**
     * Getter function for match number
     * @returns The match number
     */
    @Bindable
    public int getMatchNumber()
    {
        return this.matchNumber;
    }

    /**
     * Setter function for match number
     * @param matchNumber The match number
     */
    public void setMatchNumber(int matchNumber)
    {
        this.matchNumber = matchNumber;
        mPropertyChangeRegistry.notifyChange(this, BR.matchNumber);
        this.id = String.format("%d_%d", this.teamNumber, this.matchNumber);
    }
    //endregion
    //region Team Number
    @Index
    int teamNumber;

    /**
     * Getter function for team number
     * @returns The team number
     */
    @Bindable
    public int getTeamNumber()
    {
        return this.teamNumber;
    }

    /**
     * Setter function for team number
     * @param teamNumber The team number
     */
    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
        mPropertyChangeRegistry.notifyChange(this, BR.teamNumber);
        this.id = String.format("%d_%d", this.teamNumber, matchNumber);
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
        return this.scoutName;
    }

    /**
     * Setter function for scout name
     * @param scoutName The name of the scout who recorded the information for {@link TeamMatchData#teamNumber} in {@link TeamMatchData#matchNumber}
     */
    public void setScoutName(String scoutName)
    {
        this.scoutName = scoutName;
        mPropertyChangeRegistry.notifyChange(this, BR.scoutName);
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
        return this.fouls;
    }

    /**
     * Setter function for fouls
     * @param fouls The number of normal fouls caused by this team in this match
     */
    public void setFouls(int fouls)
    {
        this.fouls = fouls;
        mPropertyChangeRegistry.notifyChange(this, BR.fouls);
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
        return this.techFouls;
    }

    /**
     * Setter function for tech fouls
     * @param techFouls The number of tech fouls caused by this team in this match
     */
    public void setTechFouls(int techFouls)
    {
        this.techFouls = techFouls;
        mPropertyChangeRegistry.notifyChange(this, BR.techFouls);
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
        return this.yellowCard;
    }

    /**
     * Setter function for yellow card
     * @param yellowCard Whether a yellow card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    public void setYellowCard(boolean yellowCard)
    {
        this.yellowCard = yellowCard;
        mPropertyChangeRegistry.notifyChange(this, BR.yellowCard);
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
        mPropertyChangeRegistry.notifyChange(this, BR.redCard);
    }
    //endregion
    //endregion

    //region Misc
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
        mPropertyChangeRegistry.notifyChange(this, BR.notes);
    }
    //endregion
    //endregion

    //region Game Specific
    //region Autonomous
    //region Test
    String test;

    public String getTest()
    {
        return test;
    }

    public void setTest(String test)
    {
        this.test = test;
    }
    //endregion
    //endregion
    //region Teleop
    //endregion
    //region Endgame
    //endregion
    //endregion

    //region Constructors
    public TeamMatchData()
    {
        mPropertyChangeRegistry = new PropertyChangeRegistry();
    }

    public TeamMatchData(int teamNumber, int matchNumber)
    {
        this.teamNumber = teamNumber;
        this.matchNumber = matchNumber;
        id = String.format("%d_%d", this.teamNumber, this.matchNumber);
    }
    //endregion
}
