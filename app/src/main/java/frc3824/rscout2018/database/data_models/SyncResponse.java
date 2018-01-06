package frc3824.rscout2018.database.data_models;

import java.util.List;

/**
 * Data Model for the sync response from the server
 */
public class SyncResponse
{
    public List<TeamMatchData> tmds;
    public List<TeamPitData> tpds;
    public List<TeamCalculatedData> tcds;
    public List<TeamLogistics> tls;
    public List<MatchLogistics> mls;

    SyncResponse(){}

    public void save()
    {
        for(TeamMatchData tmd: tmds)
        {
            tmd.save();
        }

        for(TeamPitData tpd: tpds)
        {
            tpd.save();
        }

        for(TeamCalculatedData tcd: tcds)
        {
            tcd.save();
        }

        for(TeamLogistics tl: tls)
        {
            tl.save();
        }

        for(MatchLogistics ml: mls)
        {
            ml.save();
        }
    }
}
