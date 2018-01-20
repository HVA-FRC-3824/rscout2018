package frc3824.rscout2018.database.data_models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Data model for a team's placement in the pick list
 */
public class TeamPickListItem extends BaseObservable
{
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

    //region Picked
    boolean picked;

    /**
     * Getter function for if the team has been picked
     */
    @Bindable
    public boolean isPicked()
    {
        return picked;
    }

    /**
     * Setter function for if the team is picked
     */
    public void setPicked(boolean picked)
    {
        this.picked = picked;
        notifyChange();
    }
    //endregion

    //region Pick Value
    float mPickValue;

    @Bindable
    public float getPickValue()
    {
        return mPickValue;
    }

    public void setPickValue(float value)
    {
        mPickValue = value;
        notifyChange();
    }


}
