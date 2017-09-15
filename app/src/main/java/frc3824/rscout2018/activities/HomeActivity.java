package frc3824.rscout2018.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import frc3824.rscout2018.R;
import frc3824.rscout2018.utilities.Constants;

public class HomeActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId())
        {
            case R.id.match_scouting_button:
                MatchListActivityStarter.start(Constants.IntentExtras.NextPage.MATCH_SCOUTING);
                break;
            case R.id.pit_scouting_button:
                TeamListActivityStarter.start(Constants.IntentExtras.NextPage.PIT_SCOUTING);
                break;
            case R.id.super_scouting_button:
                MatchListActivityStarter.start(Constants.IntentExtras.NextPage.SUPER_SCOUTING);
                break;
            case R.id.match_view_button:
                MatchListActivityStarter.start(Constants.IntentExtras.NextPage.MATCH_VIEW);
                break;
            case R.id.team_view_button:
                TeamListActivityStarter.start(Constants.IntentExtras.NextPage.TEAM_VIEW);
                break;
            case R.id.event_view_button:
                break;
            case R.id.settings_button:
                SettingsActivityStarter.start();
                break;
        }
    }
}
