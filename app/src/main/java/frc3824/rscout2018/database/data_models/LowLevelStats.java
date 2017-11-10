package frc3824.rscout2018.database.data_models;

import android.databinding.Bindable;

/**
 * @class LowLevelStats
 * @brief The aggregated statistics about a particular element of a team's performance
 *
 * Contains average, standard deviation, minimum, maximum, and ranking
 */
public class LowLevelStats
{
    //region Average
    double average;

    /**
     * Getter function for the average
     * @returns The average
     */
    @Bindable
    public double getAverage()
    {
        return average;
    }

    /**
     * Setter function for the average
     * @param average The average
     */
    public void setAverage(double average)
    {
        this.average = average;
    }
    //endregion
    //region Standard Deviation
    double std;

    /**
     * Getter function for the standard deviation
     * @return The standard deviation
     */
    @Bindable
    public double getStd()
    {
        return std;
    }

    /**
     * Setter function for the standard deviation
     * @param std The standard deviation
     */
    public void setStd(double std)
    {
        this.std = std;
    }
    //endregion
    //region Minimum
    double minimum;

    /**
     * Getter function for the minimum
     * @returns The minimum
     */
    @Bindable
    public double getMinimum()
    {
        return minimum;
    }

    /**
     * Setter function for the minimum
     * @param minimum The minimum
     */
    public void setMinimum(double minimum)
    {
        this.minimum = minimum;
    }
    //endregion
    //region Maximum
    double maximum;

    /**
     * Getter function for the maximum
     * @return The maximum
     */
    @Bindable
    public double getMaximum()
    {
        return maximum;
    }

    /**
     * Setter function for the maximum
     * @param maximum The maximum
     */
    public void setMaximum(double maximum)
    {
        this.maximum = maximum;
    }
    //endregion
    //region Ranking
    int ranking;

    /**
     * Getter function for the ranking
     * @returns The ranking
     */
    @Bindable
    public int getRanking()
    {
        return ranking;
    }

    /**
     * Setter function for the ranking
     * @param ranking The ranking
     */
    public void setRanking(int ranking)
    {
        this.ranking = ranking;
    }
    //endregion

    //region Constructors
    public LowLevelStats()
    {
    }
    //endregion
}
