package frc3824.rscout2018.activities;

import android.app.Activity;
import android.os.Bundle;

import activitystarter.ActivityStarter;
import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;


@MakeActivityStarter
public class EventChartsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_charts);
        ActivityStarter.fill(this);
    }
}
