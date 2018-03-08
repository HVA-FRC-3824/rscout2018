package frc3824.rscout2018.database.data_models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.Locale;

import static java.lang.String.format;

/**
 * Created by frc3824.
 */
public class MatchPreviewTeam extends BaseObservable
{
    //region Team Number
    int teamNumber;

    @Bindable
    public String getTeamNumber()
    {
        return String.valueOf(teamNumber);
    }

    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
        notifyChange();
    }
    //endregion


    //region Auto Cubes
    //region Auto Switch Attempts
    int autoSwitchAttempts;

    @Bindable
    public String getAutoSwitchAttempts()
    {
        return String.valueOf(autoSwitchAttempts);
    }

    public void incrementAutoSwitchFailures()
    {
        autoSwitchAttempts ++;
        notifyChange();
    }
    //endregion
    //region Auto Switch Successes
    int autoSwitchSuccesses;

    @Bindable
    public String getAutoSwitchSuccesses()
    {
        return String.valueOf(autoSwitchSuccesses);
    }

    public void incrementAutoSwitchSuccesses()
    {
        autoSwitchSuccesses ++;
        autoSwitchAttempts ++;
        notifyChange();
    }
    //endregion
    //region Auto Scale Attempts
    int autoScaleAttempts = 0;

    @Bindable
    public String getAutoScaleAttempts()
    {
        return String.valueOf(autoScaleAttempts);
    }

    public void incrementAutoScaleFailures()
    {
        autoScaleAttempts ++;
        notifyChange();
    }
    //endregion
    //region Auto Scale Successes
    int autoScaleSuccesses = 0;

    @Bindable
    public String getAutoScaleSuccesses()
    {
        return String.valueOf(autoScaleSuccesses);
    }

    public void incrementAutoScaleSuccesses()
    {
        autoScaleSuccesses ++;
        autoScaleAttempts ++;
        notifyChange();
    }
    //endregion
    //endregion
    //region Teleop Cubes
    //region Teleop Switch Attempts
    int teleopSwitchAttempts;

    @Bindable
    public String getTeleopSwitchAttempts()
    {
        return String.valueOf(teleopSwitchAttempts);
    }


    public void incrementTeleopSwitchFailures()
    {
        teleopSwitchAttempts ++;
        notifyChange();
    }
    //endregion
    //region Teleop Switch Successes
    int teleopSwitchSuccesses;

    @Bindable
    public String getTeleopSwitchSuccesses()
    {
        return String.valueOf(teleopSwitchSuccesses);
    }

    public void incrementTeleopSwitchSuccesses()
    {
        teleopSwitchSuccesses ++;
        teleopSwitchAttempts ++;
        notifyChange();
    }
    //endregion
    //region Teleop Scale Attempts
    int teleopScaleAttempts;

    @Bindable
    public String getTeleopScaleAttempts()
    {
        return String.valueOf(teleopScaleAttempts);
    }

    public void incrementTeleopScaleFailures()
    {
        teleopScaleAttempts ++;
        notifyChange();
    }
    //endregion
    //region Teleop Scale Successes
    int teleopScaleSuccesses;

    @Bindable
    public String getTeleopScaleSuccesses()
    {
        return String.valueOf(teleopScaleSuccesses);
    }

    public void incrementTeleopScaleSuccesses()
    {
        teleopScaleSuccesses ++;
        teleopScaleAttempts ++;
        notifyChange();
    }
    //endregion
    //endregion

    //region Short Cycle
    long shortCycleSum = 0;
    int shortCycleNum = 0;

    @Bindable
    public String getAverageShortCycle()
    {
        if(shortCycleNum == 0)
        {
            return "N/A";
        }

        return format(Locale.US, "%.2f", (float)shortCycleSum / 1000.0f / (float)shortCycleNum);
    }

    public void addToShortCycleSum(long shortCycleSum)
    {
        this.shortCycleSum += shortCycleSum;
        this.shortCycleNum ++;
        notifyChange();
    }
    //endregion
    //region Medium Cycle
    long mediumCycleSum = 0;
    int mediumCycleNum = 0;

    @Bindable
    public String getAverageMediumCycle()
    {
        if(mediumCycleNum == 0)
        {
            return "N/A";
        }

        return format(Locale.US, "%.2f", (float)mediumCycleSum / 1000.0f / (float)mediumCycleNum);
    }

    public void addToMediumCycleSum(long mediumCycleSum)
    {
        this.mediumCycleSum += mediumCycleSum;
        this.mediumCycleNum ++;
        notifyChange();
    }
    //endregion
    //region Long Cycle
    long longCycleSum = 0;
    int longCycleNum = 0;

    @Bindable
    public String getAverageLongCycle()
    {
        if(longCycleNum == 0)
        {
            return "N/A";
        }

        return format(Locale.US, "%.2f", (float)longCycleSum / 1000.0f / (float)longCycleNum);
    }

    public void addToLongCycleSum(long longCycleSum)
    {
        this.longCycleSum += longCycleSum;
        this.longCycleNum ++;
        notifyChange();
    }
    //endregion

    //region Number of Matches
    int numMatches;

    @Bindable
    public String getNumMatches()
    {
        return String.valueOf(numMatches);
    }

    public void setNumMatches(int numMatches)
    {
        this.numMatches = numMatches;
        notifyChange();
    }
    //endregion

    //region Drops
    int numDrops= 0;

    @Bindable
    public String getDropAverage()
    {
        if(numMatches == 0)
        {
            return "N/A";
        }

        return format(Locale.US, "%.2f", (float)numDrops / (float)numMatches);
    }

    public void incrementNumDrops()
    {
        numDrops ++;
        notifyChange();
    }
    //endregion
    //region Launch Fails
    int numLaunchFails = 0;

    @Bindable
    public String getLaunchFailAverage()
    {
        if(numMatches == 0)
        {
            return "N/A";
        }

        return format(Locale.US, "%.2f", (float)numLaunchFails / (float)numMatches);
    }

    public void incrementNumLaunchFails()
    {
        numLaunchFails ++;
        notifyChange();
    }
    //endregion
    //region Fouls
    int numFouls = 0;

    @Bindable
    public String getFoulsAverage()
    {
        if(numMatches == 0)
        {
            return "N/A";
        }

        return format(Locale.US, "%.2f", (float)numFouls / (float)numMatches);
    }

    public void addToFouls(int fouls)
    {
        numFouls += fouls;
    }
    //endregion
    //region Tech Fouls
    int numTechFouls = 0;

    @Bindable
    public String getTechFoulsAverage()
    {
        if(numMatches == 0)
        {
            return "0.00";
        }

        return format(Locale.US, "%.2f", (float)numTechFouls / (float)numMatches);
    }

    public void addToTechFouls(int techFouls)
    {
        numTechFouls += techFouls;
    }
    //endregion
    //region Yellow Card
    boolean yellowCard = false;

    @Bindable public boolean isYellowCard()
    {
        return yellowCard;
    }

    public void setYellowCard(boolean yellowCard)
    {
        if(yellowCard)
        {
            this.yellowCard = true;
            notifyChange();
        }
    }
    //endregion
    //region Red Card
    boolean redCard = false;

    @Bindable public boolean isRedCard()
    {
        return redCard;
    }

    public void setRedCard(boolean redCard)
    {
        if(redCard)
        {
            this.redCard = true;
            notifyChange();
        }
    }
    //endregion
}
