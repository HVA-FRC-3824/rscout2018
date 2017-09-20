package frc3824.rscout2018.data_models;


import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import frc3824.rscout2018.BR;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @class TeamPitData
 * @brief Data model for holding information recorded when talking to a team in their pit
 */
public class TeamPitData extends RealmObject implements Observable
{
    //region Observable
    @Ignore
    private PropertyChangeRegistry mPropertyChangeRegistry;

    @Override
    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback)
    {
        mPropertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback)
    {
        mPropertyChangeRegistry.remove(callback);
    }
    //endregion

    //region Logistics
    @PrimaryKey
    int teamNumber;

    /**
     * Getter function for teamNumber
     * @returns The team number
     */
    @Bindable
    public int getTeamNumber()
    {
        return teamNumber;
    }

    /**
     * Setter function for teamNumber
     * @param teamNumber The team number
     */
    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
        mPropertyChangeRegistry.notifyChange(this, BR.teamNumber);
    }

    String scoutName;

    /**
     * Getter function for scoutName
     * @returns The scout name
     */
    @Bindable
    public String getScoutName()
    {
        return scoutName;
    }

    /**
     * Setter function for scoutName
     * @param scoutName The scout who recorded the information about team {@link TeamPitData#teamNumber}
     */
    public void setScoutName(String scoutName)
    {
        this.scoutName = scoutName;
        mPropertyChangeRegistry.notifyChange(this, BR.scoutName);
    }
    //endregion

    //region Picture
    RealmList<RealmString> pictureFilepaths;

    //endregion

    //region Dimensions
    double robotWidth;

    /**
     * Getter function for robotWidth
     * @returns The width of the robot for team {@link TeamPitData#teamNumber}
     */
    @Bindable
    public double getRobotWidth()
    {
        return robotWidth;
    }

    /**
     * Setter function for robotWidth
     * @param robotWidth The width of the robot for team {@link TeamPitData#teamNumber}
     */
    public void setRobotWidth(double robotWidth)
    {
        this.robotWidth = robotWidth;
        mPropertyChangeRegistry.notifyChange(this, BR.robotWidth);
    }

    double robotLength;

    /**
     * Getter function for robotLength
     * @returns The length of the robot for team {@link TeamPitData#teamNumber}
     */
    @Bindable
    public double getRobotLength()
    {
        return robotLength;
    }

    /**
     * Setter function for robotLength
     * @param robotLength The length of the robot for team {@link TeamPitData#teamNumber}
     */
    public void setRobotLength(double robotLength)
    {
        this.robotLength = robotLength;
        mPropertyChangeRegistry.notifyChange(this, BR.robotLength);
    }

    double robotHeight;

    /**
     * Getter function for robotHeight
     * @returns The height of the robot for team {@link TeamPitData#teamNumber}
     */
    @Bindable
    public double getRobotHeight()
    {
        return robotHeight;
    }

    /**
     * Setter function for robotHeight
     * @param robotHeight The height of the robot for team {@link TeamPitData#teamNumber}
     */
    public void setRobotHeight(double robotHeight)
    {
        this.robotHeight = robotHeight;
        mPropertyChangeRegistry.notifyChange(this, BR.robotHeight);
    }

    double robotWeight;

    /**
     * Getter function for robotWeight
     * @returns The weight of the robot for team {@link TeamPitData#teamNumber}
     */
    @Bindable
    public double getRobotWeight()
    {
        return robotWeight;
    }

    /**
     * Setter function for robotWeight
     * @param robotWeight The weight of the robot for team {@link TeamPitData#teamNumber}
     */
    public void setRobotWeight(double robotWeight)
    {
        this.robotWeight = robotWeight;
        mPropertyChangeRegistry.notifyChange(this, BR.robotWeight);
    }
    //endregion

    //region Misc
    String programmingLanguage;

    /**
     * Getter function for the programming language for team (@link TeamPitData#teamNumber}
     * @returns The programming language
     */
    @Bindable
    public String getProgrammingLanguage()
    {
        return programmingLanguage;
    }

    /**
     * Setter function for the programming language for team (@link TeamPitData#teamNumber}
     * @param programmingLanguage The programming language
     */
    public void setProgrammingLanguage(String programmingLanguage)
    {
        this.programmingLanguage = programmingLanguage;
        mPropertyChangeRegistry.notifyChange(this, BR.programmingLanguage);
    }

    String driveTrain;

    /**
     * Getter function for the drive train
     * @returns The name of the drive train
     */
    @Bindable
    public String getDriveTrain()
    {
        return driveTrain;
    }

    /**
     * Setter function for the drive train
     * @param driveTrain The name of the drive train
     */
    public void setDriveTrain(String driveTrain)
    {
        this.driveTrain = driveTrain;
        mPropertyChangeRegistry.notifyChange(this, BR.driveTrain);
    }

    String notes;

    /**
     * Getter function for notes
     * @returns The notes
     */
    @Bindable
    public String getNotes()
    {
        return notes;
    }

    /**
     * Setter function for notes
     * @param notes The notes
     */
    public void setNotes(String notes)
    {
        this.notes = notes;
        mPropertyChangeRegistry.notifyChange(this, BR.notes);
    }
    //endregion
}
