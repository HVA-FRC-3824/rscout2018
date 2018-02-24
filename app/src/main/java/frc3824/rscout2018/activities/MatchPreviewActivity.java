package frc3824.rscout2018.activities;

import android.os.Bundle;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;

/**
 * @class MatchPreviewActivity
 * @brief Activity for previewing the teams in a given match
 */
@MakeActivityStarter
public class MatchPreviewActivity extends RScoutActivity
{
    @Arg
    protected int mTeamNumber;

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        ActivityStarter.fill(this);
    }
}
