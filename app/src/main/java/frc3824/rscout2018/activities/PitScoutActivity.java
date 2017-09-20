package frc3824.rscout2018.activities;

import android.app.Activity;
import android.os.Bundle;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.adapters.fpa.PitScoutFragmentPagerAdapter;
import frc3824.rscout2018.data_models.TeamPitData;
import frc3824.rscout2018.views.ScoutHeader;
import frc3824.rscout2018.views.ScoutHeaderInterface;
import io.realm.Realm;

/**
 * @class PitScoutActivity
 * @brief The page for scouting an individual team
 */
@MakeActivityStarter
public class PitScoutActivity extends Activity
{
    private final static String TAG = "MatchScoutActivity";

    @Arg
    private int mTeamNumber = -1;
    private boolean mPractice = false;

    private Realm mDatabase;

    private PitScoutFragmentPagerAdapter mSFPA;
    private TeamPitData mTPD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout);
        ActivityStarter.fill(this);

        ScoutHeader header = findViewById(R.id.header);
        header.setInterface(new PitScoutHeader());

        mDatabase = Realm.getDefaultInstance();
    }

    private class PitScoutHeader implements ScoutHeaderInterface
    {

        @Override
        public void previous()
        {
            
        }

        @Override
        public void next()
        {

        }

        @Override
        public void home()
        {

        }

        @Override
        public void list()
        {

        }

        @Override
        public void save()
        {

        }
    }
}
