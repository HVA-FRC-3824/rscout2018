package frc3824.rscout2018.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.services.CommunicationService;
import frc3824.rscout2018.utilities.Constants;

@MakeActivityStarter
public class HomeActivity extends Activity implements View.OnClickListener
{

    static String mEventKey;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Inflate the match scouting button
        Button button = findViewById(R.id.match_scouting_button);
        button.setOnClickListener(this);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_MATCH_SCOUT,
                                                       false));

        // Inflate the pit scouting button
        button.setOnClickListener(this);
        button = findViewById(R.id.pit_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_PIT_SCOUT, false));

        // Inflate the super scouting button
        button.setOnClickListener(this);
        button = findViewById(R.id.super_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_SUPER_SCOUT,
                                                       false));

        boolean enableStrategist = sharedPreferences.getBoolean(Constants.Settings.ENABLE_STRATEGIST,
                                                                false);
        // Inflate the match preview button
        button = findViewById(R.id.match_preview_button);
        button.setEnabled(enableStrategist);
        button.setOnClickListener(this);

        // Inflate the team stats button
        button = findViewById(R.id.team_stats_button);
        button.setEnabled(enableStrategist);
        button.setOnClickListener(this);

        // Inflate the event charts button
        button = findViewById(R.id.event_charts_button);
        button.setEnabled(enableStrategist);
        button.setOnClickListener(this);

        button = findViewById(R.id.pick_list_button);
        button.setEnabled(enableStrategist);
        button.setOnClickListener(this);

        button = findViewById(R.id.pull_matches);
        button.setEnabled(enableStrategist);
        button.setOnClickListener(this);

        // Inflate the settings button
        findViewById(R.id.settings_button).setOnClickListener(this);

        // If an event key is selected then setup database
        if (sharedPreferences.contains(Constants.Settings.EVENT_KEY))
        {
            String temp = sharedPreferences.getString(Constants.Settings.EVENT_KEY, "");

            if (temp != mEventKey)
            {
                mEventKey = temp;
                String ip = sharedPreferences.getString(Constants.Settings.SERVER_IP, "");
                int port = Integer.parseInt(sharedPreferences.getString(Constants.Settings.SERVER_PORT, ""));
                // todo: Something when ip is empty or port is -1

                Database.getInstance().setEventKey(mEventKey);
            }
        }
        else
        {
            mEventKey = "None";
        }

        // Display event key
        TextView tv = findViewById(R.id.event);
        tv.setText(String.format("Event: %s", mEventKey));

        // Display version
        tv = findViewById(R.id.version);
        tv.setText(String.format("Version: %s", Constants.VERSION));
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Reset everything (the preferences might have changed)
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Reset match scouting button
        Button button = findViewById(R.id.match_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_MATCH_SCOUT,
                                                       false));

        // Reset pit scouting button
        button = findViewById(R.id.pit_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_PIT_SCOUT, false));

        // Reset super scouting button
        button = findViewById(R.id.super_scouting_button);
        button.setEnabled(sharedPreferences.getBoolean(Constants.Settings.ENABLE_SUPER_SCOUT,
                                                       false));

        boolean enableStrategist = sharedPreferences.getBoolean(Constants.Settings.ENABLE_STRATEGIST,
                                                                false);

        // Reset match preview button
        button = findViewById(R.id.match_preview_button);
        button.setEnabled(enableStrategist);

        // Reset team stats button
        button = findViewById(R.id.team_stats_button);
        button.setEnabled(enableStrategist);

        // Reset event charts button
        button = findViewById(R.id.event_charts_button);
        button.setEnabled(enableStrategist);

        // Reset pick list button
        button = findViewById(R.id.pick_list_button);
        button.setEnabled(enableStrategist);

        // Setup database
        if (sharedPreferences.contains(Constants.Settings.EVENT_KEY))
        {
            String temp = sharedPreferences.getString(Constants.Settings.EVENT_KEY, "");

            if (temp != mEventKey)
            {
                mEventKey = temp;
                String ip = sharedPreferences.getString(Constants.Settings.SERVER_IP, "");
                int port = Integer.parseInt(sharedPreferences.getString(Constants.Settings.SERVER_PORT, ""));
                // todo: Something when ip is empty or port is -1

                Database.getInstance().setEventKey(mEventKey);

                TextView tv = findViewById(R.id.event);
                tv.setText(String.format("Event: %s", mEventKey));
            }
        }
        else
        {
            mEventKey = "None";
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            // Go to the match list for match scouting
            case R.id.match_scouting_button:
                MatchListActivityStarter.start(this,
                                               Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING);
                break;
            // Go to the team list for pit scouting
            case R.id.pit_scouting_button:
                TeamListActivityStarter.start(this,
                                              Constants.IntentExtras.NextPageOptions.PIT_SCOUTING);
                break;
            // Go to the match list for super scouting
            case R.id.super_scouting_button:
                MatchListActivityStarter.start(this,
                                               Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING);
                break;
            // Go to the match list for match preview
            case R.id.match_preview_button:
                MatchListActivityStarter.start(this,
                                               Constants.IntentExtras.NextPageOptions.MATCH_PREVIEW);
                break;
            // Go to the team list for team stats
            case R.id.team_stats_button:
                TeamListActivityStarter.start(this,
                                              Constants.IntentExtras.NextPageOptions.TEAM_STATS);
                break;
            // Go to the event charts
            case R.id.event_charts_button:
                EventChartsActivityStarter.start(this);
                break;

            // Go to the pick list
            case R.id.pick_list_button:
                PickListActivityStarter.start(this);
                break;

            // Go to the settings
            case R.id.settings_button:
                SettingsActivityStarter.start(this);
                break;

            case R.id.pull_matches:
                Intent intent = new Intent(HomeActivity.this, CommunicationService.class);
                intent.putExtra(Constants.IntentExtras.PULL_MATCHES, true);
                startService(intent);
                break;

            default:
                assert(false);
        }
    }
}
