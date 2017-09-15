package frc3824.rscout2018.data_models;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Data Model for a single team in a single match
 */
public class TeamMatchData extends RealmObject implements Observable
{
    // Logistics
    @PrimaryKey
    String id; //!< Solely for the database
    int matchNumber;
    @Index
    int teamNumber;
    String scoutName;

    // Autonomous
    String test;

    // Teleop

    // Endgame

    // Fouls
    int fouls;
    int techFouls;
    boolean yellowCard;
    boolean redCard;

    // Misc
    String notes;

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

    @Bindable
    public int getMatchNumber()
    {
        return this.matchNumber;
    }

    public void setMatchNumber(int matchNumber)
    {
        this.matchNumber = matchNumber;
        mPropertyChangeRegistry.notifyChange(this, BR.matchNumber);
        this.id = String.format("%d_%d", this.teamNumber, this.matchNumber);
    }

    @Bindable
    public int getTeamNumber()
    {
        return this.teamNumber;
    }

    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
        mPropertyChangeRegistry.notifyChange(this, BR.teamNumber);
        this.id = String.format("%d_%d", this.teamNumber, matchNumber);
    }

    @Bindable
    public String getScoutName()
    {
        return this.scoutName;
    }

    public void setScoutName(String scoutName)
    {
        this.scoutName = scoutName;
        mPropertyChangeRegistry.notifyChange(this, BR.scoutName);
    }

    @Bindable
    public int getFouls()
    {
        return this.fouls;
    }

    public void setFouls(int fouls)
    {
        this.fouls = fouls;
        mPropertyChangeRegistry.notifyChange(this, BR.fouls);
    }

    @Bindable
    public int getTechFouls()
    {
        return this.techFouls;
    }

    public void setTechFouls(int techFouls)
    {
        this.techFouls = techFouls;
        mPropertyChangeRegistry.notifyChange(this, BR.techFouls);
    }

    @Bindable
    public boolean hasYellow_card()
    {
        return this.yellowCard;
    }

    public void setYellowCard(boolean yellowCard)
    {
        this.yellowCard = yellowCard;
        mPropertyChangeRegistry.notifyChange(this, BR.yellowCard);
    }

    @Bindable
    public boolean hasRedCard()
    {
        return redCard;
    }

    public void setRedCard(boolean redCard)
    {
        this.redCard = redCard;
        mPropertyChangeRegistry.notifyChange(this, BR.redCard);
    }

    @Bindable
    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
        mPropertyChangeRegistry.notifyChange(this, BR.notes);
    }

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
}
