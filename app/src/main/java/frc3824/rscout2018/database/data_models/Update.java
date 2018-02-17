package frc3824.rscout2018.database.data_models;

import java.util.ArrayList;

/**
 * @author frc3824
 */

public class Update
{
    public ArrayList<MatchLogistics> matchLogistics;
    public ArrayList<TeamLogistics> teamLogistics;
    public ArrayList<TeamMatchData> teamMatchData;
    public ArrayList<TeamPitData> teamPitData;
    public ArrayList<SuperMatchData> superMatchData;
    public ArrayList<TeamCalculatedData> teamCalculatedData;

    public void save()
    {
        for(MatchLogistics match : matchLogistics)
        {
            match.save();
        }

        for(TeamLogistics team : teamLogistics)
        {
            team.save();
        }

        for(TeamMatchData tmd : teamMatchData)
        {
            tmd.save();
        }

        for(TeamPitData tpd : teamPitData)
        {
            tpd.save();
        }

        for(SuperMatchData smd : superMatchData)
        {
            smd.save();
        }

        for(TeamCalculatedData tcd : teamCalculatedData)
        {
            tcd.save();
        }
    }

}
