package frc3824.rscout2018.database.data_models.powered_up;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class SwitchScaleData extends BaseObservable
{
    //region Location
    private String location;

    /**
     * Returns which scale/switch (near, center, far)
     */
    @Bindable
    public String getLocation()
    {
        return location;
    }

    /**
     * Sets which scale/switch (near, center, far)
     * @param location
     */
    public void setLocation(String location)
    {
        this.location = location;
        notifyChange();
    }
    //endregion

    //region Correct
    private boolean correct;

    public boolean isCorrect()
    {
        return correct;
    }

    public void setCorrect(boolean correct)
    {
        this.correct = correct;
        notifyChange();
    }
    //endregion
}
