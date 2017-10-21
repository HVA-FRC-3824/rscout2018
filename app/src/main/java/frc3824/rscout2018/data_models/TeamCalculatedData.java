package frc3824.rscout2018.data_models;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import frc3824.rscout2018.BR;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @class TeamCalculatedData
 * @brief A data model containing the aggregated statistics about a team's performance
 */
public class TeamCalculatedData extends RealmObject implements Observable
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
    //region Team Number
    @PrimaryKey
    int teamNumber;

    /**
     * Getter function for the team's number
     * @returns The team's number
     */
    @Bindable
    public int getTeamNumber()
    {
        return teamNumber;
    }

    /**
     * Setter function for the team's number
     * @param teamNumber The team's number
     */
    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
        mPropertyChangeRegistry.notifyChange(this, BR.teamNumber);
    }
    //endregion
    //region Number of Matches Completed
    int numMatchesCompleted;

    /**
     * Getter function for the number of matches a team has completed
     * @returns The number of matches the team has completed
     */
    @Bindable
    public int getNumMatchesCompleted()
    {
        return numMatchesCompleted;
    }

    /**
     * Setter function for the number of matches a team has completed
     * @param numMatchesCompleted The number of matches the team has completed
     */
    public void setNumMatchesCompleted(int numMatchesCompleted)
    {
        this.numMatchesCompleted = numMatchesCompleted;
        mPropertyChangeRegistry.notifyChange(this, BR.numMatchesCompleted);
    }
    //endregion
    //endregion

    //region Game Specific
    //region Autonomous
    //endregion
    //region Teleop
    //endregion
    //region End Game
    //endregion
    //endregion

    //region Fouls
    //region Fouls
    LowLevelStats fouls;

    /**
     * Getter function for the statistics on this team's ability to cause fouls
     * @returns The statistics on this team's ability to cause fouls
     */
    @Bindable
    public LowLevelStats getFouls()
    {
        return fouls;
    }

    /**
     * Setter function for the statistics on this team's ability to cause fouls
     * @param fouls The statistics on this team's ability to cause fouls
     */
    public void setFouls(LowLevelStats fouls)
    {
        this.fouls = fouls;
        mPropertyChangeRegistry.notifyChange(this, BR.fouls);
    }
    //endregion
    //region Tech Fouls
    LowLevelStats techFouls;

    /**
     * Getter function for the statistics on this team's ability to cause tech fouls
     * @returns The statistics on this team's ability to cause tech fouls
     */
    @Bindable
    public LowLevelStats getTechFouls()
    {
        return techFouls;
    }

    /**
     * Setter function for the statistics on this team's ability to cause tech fouls
     * @param techFouls
     */
    public void setTechFouls(LowLevelStats techFouls)
    {
        this.techFouls = techFouls;
        mPropertyChangeRegistry.notifyChange(this, BR.techFouls);
    }
    //endregion
    //region Yellow Cards
    LowLevelStats yellowCards;

    /**
     * Getter function for the statistics on this team's ability to accrue yellow cards
     * @returns The statistics on this team's ability to accrue yellow cards
     */
    @Bindable
    public LowLevelStats getYellowCards()
    {
        return yellowCards;
    }

    /**
     * Setter function for the statistics on this team's ability to accrue yellow cards
     * @param yellowCards The statistics on this team's ability to accrue yellow cards
     */
    public void setYellowCards(LowLevelStats yellowCards)
    {
        this.yellowCards = yellowCards;
        mPropertyChangeRegistry.notifyChange(this, BR.yellowCards);
    }
    //endregion
    //region Red Cards
    LowLevelStats redCards;

    /**
     * Getter function for the statistics on this team's ability to accrue red cards
     * @return The statistics on this team's ability to accrue red cards
     */
    @Bindable
    public LowLevelStats getRedCards()
    {
        return redCards;
    }

    /**
     * Setter function for the statistics on this team's ability to accrue red cards
     * @param redCards The statistics on this team's ability to accrue red cards
     */
    public void setRedCards(LowLevelStats redCards)
    {
        this.redCards = redCards;
        mPropertyChangeRegistry.notifyChange(this, BR.redCards);
    }
    //endregion
    //endregion

    //region Misc
    //region DQ
    LowLevelStats dq;

    /**
     * Getter function for the statistics for a team's ability to get disqualified
     * @return The statistics for a team's ability to get disqualified
     */
    @Bindable
    public LowLevelStats getDq()
    {
        return dq;
    }

    /**
     * Setter function for the statistics for a team's ability to get disqualified
     * @param dq The statistics for a team's ability to get disqualified
     */
    public void setDq(LowLevelStats dq)
    {
        this.dq = dq;
        mPropertyChangeRegistry.notifyChange(this, BR.dq);
    }
    //endregion
    //region No Show
    LowLevelStats noShow;

    /**
     * Getter for the statistics on the team's lack of attendance
     * @returns The statistics on team's lack of attendance
     */
    @Bindable
    public LowLevelStats getNoShow()
    {
        return noShow;
    }

    /**
     * Setter for the statistics on the team's lack of attendance
     * @param noShow The statistics on team's lack of attendance
     */
    public void setNoShow(LowLevelStats noShow)
    {
        this.noShow = noShow;
        mPropertyChangeRegistry.notifyChange(this, BR.noShow);
    }
    //endregion
    //endregion

    //region Constructors
    public TeamCalculatedData()
    {
        mPropertyChangeRegistry = new PropertyChangeRegistry();
    }
    //endregion
}
