package frc3824.rscout2018.database.data_models;

import android.databinding.Bindable;

import java.util.Arrays;

public class SuperMatchData extends DataModel
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

    //region Power Ups
    //region Force
    //region Red
    int forceRed;

    /**
     *
     * Returns the number of cubes that were placed in the force column by the red alliance
     */
    @Bindable
    public int getForceRed()
    {
        return forceRed;
    }

    /**
     * Sets the number of cubes that were placed in the force column by the red alliance
     */
    public void setForceRed(int forceRed)
    {
        this.forceRed = forceRed;
        notifyChange();
    }
    //endregion
    //region Blue
    int forceBlue;

    /**
     *
     * Returns the number of cubes that were placed in the force column by the blue alliance
     */
    @Bindable
    public int getForceBlue()
    {
        return forceBlue;
    }

    /**
     * Sets the number of cubes that were placed in the force column by the blue alliance
     */
    public void setForceBlue(int forceBlue)
    {
        this.forceBlue = forceBlue;
        notifyChange();
    }
    //endregion
    //endregion
    //region Levitate
    //region Red
    int levitateRed;

    /**
     * Returns the number of cubes that were placed in the levitate column by the red alliance
     */
    @Bindable
    public int getLevitateRed()
    {
        return levitateRed;
    }

    /**
     * Sets the number of cubes that were placed in the levitate column by the red alliance
     */
    public void setLevitateRed(int levitateRed)
    {
        this.levitateRed = levitateRed;
        notifyChange();
    }
    //endregion
    //region Blue
    int levitateBlue;

    /**
     * Returns the number of cubes that were placed in the levitate column by the blue alliance
     */
    @Bindable
    public int getLevitateBlue()
    {
        return levitateBlue;
    }

    /**
     * Sets the number of cubes that were placed in the levitate column by the blue alliance
     */
    public void setLevitateBlue(int levitateBlue)
    {
        this.levitateBlue = levitateBlue;
        notifyChange();
    }
    //endregion
    //endregion
    //region Boost
    //region Red
    int boostRed;

    /**
     * Returns the number of cubes that were placed in the boost column by the red alliance
     */
    @Bindable
    public int getBoostRed()
    {
        return boostRed;
    }

    /**
     * Sets the number of cubes that were placed in the boost column by the red alliance
     */
    public void setBoostRed(int boostRed)
    {
        this.boostRed = boostRed;
        notifyChange();
    }
    //endregion
    //region Blue
    int boostBlue ;

    /**
     * Returns the number of cubes that were placed in the boost column by the blue alliance
     */
    @Bindable
    public int getBoostBlue ()
    {
        return boostBlue ;
    }

    /**
     * Sets the number of cubes that were placed in the boost column by the blue alliance
     */
    public void setBoostBlue (int boostBlue )
    {
        this.boostBlue = boostBlue ;
        notifyChange();
    }
    //endregion
    //endregion
    //endregion

    //region Misc
    //region Notes
    String notes;

    /**
     * Returns the notes taken by the super scout for this match
     */
    @Bindable
    public String getNotes()
    {
        return notes;
    }

    /**
     * Sets the notes taken by the super scout for this match
     */
    public void setNotes(String notes)
    {
        this.notes = notes;
    }
    //endregion
    //endregion

    public SuperMatchData(int matchNumber)
    {
        this.matchNumber = matchNumber;
        load();
    }

    //region Database
    public void save()
    {
        super.save(String.format("smd_%d", matchNumber));
    }

    public void load()
    {
        super.load(String.format("smd_%d", matchNumber), Arrays.asList("matchNumber"));

    }
    //endregion
}
