package frc3824.rscout2018.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.utilities.Constants;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@MakeActivityStarter
public class HomeActivity extends Activity implements View.OnClickListener{

    String mEventKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Button button = findViewById(R.id.match_scouting_button);
        button.setOnClickListener(this);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_MATCH_SCOUT, false));
        button.setOnClickListener(this);
        button = findViewById(R.id.pit_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_PIT_SCOUT, false));
        button.setOnClickListener(this);
        button = findViewById(R.id.super_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_SUPER_SCOUT, false));
        button.setOnClickListener(this);

        boolean enableStrategist = sharedPreferences.getBoolean(Constants.Settings.ENABLE_STRATEGIST, false);
        button = findViewById(R.id.match_view_button);
        button.setEnabled(enableStrategist);
        button.setOnClickListener(this);
        button = findViewById(R.id.team_view_button);
        button.setEnabled(enableStrategist);
        button.setOnClickListener(this);
        button = findViewById(R.id.event_view_button);
        button.setEnabled(enableStrategist);
        button.setOnClickListener(this);

        findViewById(R.id.settings_button).setOnClickListener(this);

        if(sharedPreferences.contains(Constants.Settings.EVENT_KEY))
        {
            mEventKey = sharedPreferences.getString(Constants.Settings.EVENT_KEY, "");
            RealmConfiguration configuration = new RealmConfiguration.Builder().name(String.format("%s.realm", mEventKey)).build();
            Realm.setDefaultConfiguration(configuration);
        }
        else
        {
            mEventKey = "None";
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Reset everything (the preferences might have changed)
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Button button = findViewById(R.id.match_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_MATCH_SCOUT, false));
        button = findViewById(R.id.pit_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_PIT_SCOUT, false));
        button = findViewById(R.id.super_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_SUPER_SCOUT, false));

        boolean enableStrategist = sharedPreferences.getBoolean(Constants.Settings.ENABLE_STRATEGIST, false);
        button = findViewById(R.id.match_view_button);
        button.setEnabled(enableStrategist);
        button = findViewById(R.id.team_view_button);
        button.setEnabled(enableStrategist);
        button = findViewById(R.id.event_view_button);
        button.setEnabled(enableStrategist);

        if(sharedPreferences.contains(Constants.Settings.EVENT_KEY))
        {
            mEventKey = sharedPreferences.getString(Constants.Settings.EVENT_KEY, "");
            RealmConfiguration configuration = new RealmConfiguration.Builder().name(String.format("%s.realm", mEventKey)).build();
            Realm.setDefaultConfiguration(configuration);
        }
        else
        {
            mEventKey = "None";
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.match_scouting_button:
                MatchListActivityStarter.start(this, Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING);
                break;
            case R.id.pit_scouting_button:
                TeamListActivityStarter.start(this, Constants.IntentExtras.NextPageOptions.PIT_SCOUTING);
                break;
            case R.id.super_scouting_button:
                MatchListActivityStarter.start(this, Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING);
                break;
            case R.id.match_view_button:
                MatchListActivityStarter.start(this, Constants.IntentExtras.NextPageOptions.MATCH_VIEW);
                break;
            case R.id.team_view_button:
                TeamListActivityStarter.start(this, Constants.IntentExtras.NextPageOptions.TEAM_VIEW);
                break;
            case R.id.event_view_button:
                break;
            case R.id.settings_button:
                SettingsActivityStarter.start(this);
                break;
        }
    }
}
