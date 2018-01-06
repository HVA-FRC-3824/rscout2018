package frc3824.rscout2018.database.data_models;


import android.databinding.Bindable;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import frc3824.rscout2018.BR;
import frc3824.rscout2018.database.Database;

/**
 * @class TeamPitData
 * @brief Data model for holding information recorded when talking to a team in their pit
 */
public class TeamPitData extends DataModel
{
    //region Logistics
    //region Team Number
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
        notifyChange();
    }
    //endregion
    //region Scout Name
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
        notifyChange();
    }
    //endregion
    //endregion

    //region Picture
    ArrayList<String> pictureFilepaths;

    /**
     * Getter function for the list of picture file paths for this robot
     * @returns The list of picture file paths for this robot
     */
    @Bindable
    public ArrayList<String> getPictureFilepaths()
    {
        return pictureFilepaths;
    }

    /**
     * Setter function for the list of picture file paths for this robot
     * @param pictureFilepaths The list of picture file paths for this robot
     */
    public void setPictureFilepaths(ArrayList<String> pictureFilepaths)
    {
        this.pictureFilepaths = pictureFilepaths;
        notifyChange();
    }

    /**
     * Returns the number of pictures of this robot
     * @return
     */
    public int numberOfPictures()
    {
        return pictureFilepaths.size();
    }

    public void addPicture(String filepath)
    {
        pictureFilepaths.add(filepath);
        notifyChange();
    }

    String defaultPictureFilepath;

    /**
     * Getter function for the file path to the default picture
     * @return
     */
    @Bindable
    public String getDefaultPictureFilepath()
    {
        return defaultPictureFilepath;
    }

    /**
     * Setter function for the file path to the default picture
     * @param defaultPictureFilepath
     */
    public void setDefaultPictureFilepath(String defaultPictureFilepath)
    {
        this.defaultPictureFilepath = defaultPictureFilepath;
        notifyChange();
    }
    //endregion

    //region Dimensions
    //region Robot Width
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
        notifyChange();
    }
    //endregion
    //region Robot Length
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
        notifyChange();
    }
    //endregion
    //region Robot Height
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
        notifyChange();
    }
    //endregion
    //region Robot Weight
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
        notifyChange();
    }
    //endregion
    //endregion

    //region Misc
    //region Programming Language
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
        notifyChange();
    }
    //endregion
    //region Drive Train
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
        notifyChange();
    }
    //endregion
    //region Notes
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
        notifyChange();
    }
    //endregion
    //endregion

    //region Constructors
    public TeamPitData(int teamNumber)
    {
        this.teamNumber = teamNumber;
        load();
        mDirty = false;
    }
    //endregion

    //region Database
    public void save()
    {
        super.save(String.format("tpd_%d", teamNumber));
    }

    public void load()
    {
        super.load(String.format("tpd_%d", teamNumber), Arrays.asList("teamNumber"));
    }
    //endregion
}
