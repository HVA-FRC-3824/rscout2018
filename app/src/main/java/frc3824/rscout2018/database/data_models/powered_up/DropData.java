package frc3824.rscout2018.database.data_models.powered_up;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class DropData extends BaseObservable
{
    //region Location
    private String location;

    /**
     * Returns where the cube was dropped
     * Options:
     * Near Switch
     * Far Switch
     * Scale
     * Exchange Station
     * Ground
     */
    @Bindable
    public String getLocation()
    {
        return location;
    }

    /**
     * Sets where the cube was dropped
     *
     * Same options as above
     */
    public void setLocation(String location)
    {
        this.location = location;
        notifyChange();
    }
    //endregion

    //region Correct
    private boolean correctSide;

    /**
     * Returns whether the cube was placed on the
     * correct side if placed on the switch or scale
     */
    public boolean isCorrectSide()
    {
        return correctSide;
    }

    /**
     * Sets whether the cube was place on the correct
     * side if place on the switch of scale
     */
    public void setCorrectSide(boolean correct)
    {
        this.correctSide = correct;
        notifyChange();
    }
    //endregion
}
