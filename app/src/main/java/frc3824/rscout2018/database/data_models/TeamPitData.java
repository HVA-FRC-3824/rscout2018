package frc3824.rscout2018.database.data_models;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frc3824.rscout2018.BR;
import frc3824.rscout2018.database.Database;

/**
 * @class TeamPitData
 * @brief Data model for holding information recorded when talking to a team in their pit
 */
public class TeamPitData extends BaseObservable
{
    boolean mDirty; // Keeps track if anything has changed

    /**
     *
     * @returns Whether the model have been changed
     */
    public boolean isDirty()
    {
        return mDirty;
    }

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
        mDirty = true;
        notifyChange(BR.teamNumber);
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
        mDirty = true;
        notifyChange(BR.scoutName);
    }
    //endregion
    //endregion

    //region Picture
    ArrayList<String> pictureFilepaths;

    /**
     * Getter function for the list of picture file paths for this robot
     * @returns The list of picture file paths for this robot
     */
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
        mDirty = true;
        notifyPropertyChanged(BR.pictureFilepaths);
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
        mDirty = true;
        notifyPropertyChanged(BR.pictureFilepaths);
    }

    String defaultPictureFilepath;

    /**
     * Getter function for the file path to the default picture
     * @return
     */
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
        mDirty = true;
        notifyPropertyChanged(BR.defaultPictureFilepath);
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
        mDirty = true;
        notifyChange(BR.robotWidth);
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
        mDirty = true;
        notifyChange(BR.robotLength);
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
        mDirty = true;
        notifyChange(BR.robotHeight);
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
        mDirty = true;
        notifyChange(BR.robotWeight);
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
        mDirty = true;
        notifyChange(BR.programmingLanguage);
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
        mDirty = true;
        notifyChange(BR.driveTrain);
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
        mDirty = true;
        notifyChange(BR.notes);
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
        Document document = Database.getInstance().getDocument(String.format("tpd_%d", teamNumber));
        Map<String, Object> properties = new HashMap<>();
        for(Field field: getClass().getDeclaredFields())
        {
            try
            {
                properties.put(field.getName(), field.get(this));
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            document.putProperties(properties);
        }
        catch (CouchbaseLiteException e)
        {
            e.printStackTrace();
        }
    }

    public void load()
    {
        Document document = Database.getInstance().getDocument(String.format("tpd_%d", teamNumber));
        Map<String, Object> properties = document.getProperties();
        for(Field field: getClass().getDeclaredFields())
        {
            // Ignore as this was set in the constructor
            if (field.getName() == "teamNumber" || field.getName() == "mDirty")
            {
                continue;
            }
            if(properties.containsKey(field.getName()))
            {
                Object property = properties.get(field.getName());
                try
                {
                    field.set(this, property);
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    //endregion
}
