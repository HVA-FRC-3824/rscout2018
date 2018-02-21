package frc3824.rscout2018.database.data_models;

import java.util.HashMap;
import java.util.Map;

import frc3824.rscout2018.database.Database;

/**
 * Created by frc3824.
 */
public class Team
{
    //region Logistics
    TeamLogistics mLogistics;

    public TeamLogistics getLogistics()
    {
        return mLogistics;
    }

    public void setLogistics(TeamLogistics logistics)
    {
        mLogistics = logistics;
    }
    //endregion
    //region Matches
    Map<Integer, TeamMatchData> mMatches;

    public Map<Integer, TeamMatchData> getMatches()
    {
        return mMatches;
    }

    public TeamMatchData getMatch(int matchNumber)
    {
        if(mMatches.containsKey(matchNumber))
        {
            return mMatches.get(matchNumber);
        }
        return null;
    }

    public void addMatch(TeamMatchData teamMatchData)
    {
        mMatches.put(teamMatchData.getMatchNumber(), teamMatchData);
    }
    //endregion
    //region Pit
    TeamPitData mPit;

    public TeamPitData getPit()
    {
        return mPit;
    }

    public void setPit(TeamPitData pit)
    {
        mPit = pit;
    }
    //endregion

    public Team(int teamNumber)
    {
        mLogistics = Database.getInstance().getTeamLogistics(teamNumber);
        // mPit = new TeamPitData(teamNumber);
        mMatches = new HashMap<>();

        if(mLogistics != null)
        {
            for (int matchNumber : mLogistics.getMatchNumbers())
            {
                TeamMatchData tmd = Database.getInstance()
                                            .getTeamMatchData(teamNumber, matchNumber);
                if (tmd != null)
                {
                    addMatch(tmd);
                }
            }
        }
    }
}
