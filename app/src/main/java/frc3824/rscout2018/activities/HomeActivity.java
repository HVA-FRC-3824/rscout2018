package frc3824.rscout2018.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;
import frc3824.rscout2018.buses.ToastBus;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.TeamLogistics;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.services.CommunicationService;
import frc3824.rscout2018.utilities.Constants;

@MakeActivityStarter
public class HomeActivity extends RScoutActivity implements View.OnClickListener
{
    static String mEventKey;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init(true);

        ToastBus.getInstance().subscribe(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        init(false);
    }

    private void init(boolean first)
    {
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
        if(first)
        {
            button.setOnClickListener(this);
        }

        // Inflate the team stats button
        button = findViewById(R.id.team_stats_button);
        button.setEnabled(enableStrategist);
        if(first)
        {
            button.setOnClickListener(this);
        }
        // Inflate the event charts button
        button = findViewById(R.id.event_charts_button);
        button.setEnabled(enableStrategist);
        if(first)
        {
            button.setOnClickListener(this);
        }

        // Inflate the pick list button
        button = findViewById(R.id.pick_list_button);
        button.setEnabled(enableStrategist);
        if(first)
        {
            button.setOnClickListener(this);
        }

        boolean enableAdmin = sharedPreferences.getBoolean(Constants.Settings.ENABLE_ADMIN, false);

        // Inflate the ping button
        button = findViewById(R.id.ping_button);
        button.setEnabled(enableAdmin);
        if(first)
        {
            button.setOnClickListener(this);
        }

        // Inflate the pull matches button
        button = findViewById(R.id.pull_matches_button);
        button.setEnabled(enableAdmin);
        if(first)
        {
            button.setOnClickListener(this);
        }

        // Inflate the update button
        button = findViewById(R.id.update);
        button.setEnabled(enableAdmin);
        if(first)
        {
            button.setOnClickListener(this);
        }

        // Inflate the generate test button
        button = findViewById(R.id.generate_test_data);
        button.setEnabled(enableAdmin);
        if(first)
        {
            button.setOnClickListener(this);
        }

        if(first)
        {
            // Inflate the settings button
            findViewById(R.id.settings_button).setOnClickListener(this);
        }

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
        tv.setText(String.format("Version: %s", getApplicationVersionName()));
    }

    @Override
    public void onClick(View view)
    {
        Intent intent;
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

            case R.id.ping_button:
                intent = new Intent(HomeActivity.this, CommunicationService.class);
                intent.putExtra(Constants.IntentExtras.PING, true);
                startService(intent);
                break;

            case R.id.pull_matches_button:
                intent = new Intent(HomeActivity.this, CommunicationService.class);
                intent.putExtra(Constants.IntentExtras.DOWNLOAD_SCHEDULE, true);
                startService(intent);
                break;

            case R.id.generate_test_data:
                TeamLogistics teamLogistics = new TeamLogistics(1);
                teamLogistics.setNickname("The first team");
                teamLogistics.setMatchNumbers(new ArrayList<>(Arrays.asList(1)));
                Database.getInstance().updateTeamLogistics(teamLogistics);

                TeamMatchData teamMatchData = new TeamMatchData(1, 1);
                teamMatchData.setStartedWithCube(true);
                teamMatchData.setStartLocationX(0.05f);
                teamMatchData.setStartLocationY(0.5f);

                new GenerateTestDataTask().execute();
                break;

            default:
                assert(false);
        }
    }

    //Programmatically get the current version Name
    private String getApplicationVersionName()
    {
        try
        {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        }
        catch(Exception ignored)
        {}
        return "";
    }

    public class GenerateTestDataTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {

            TeamLogistics teamLogistics = new TeamLogistics(1);
            teamLogistics.setNickname("The first team");
            teamLogistics.setMatchNumbers(new ArrayList<>(Arrays.asList(1)));
            Database.getInstance().updateTeamLogistics(teamLogistics);

            TeamMatchData teamMatchData = new TeamMatchData(1, 1);
            teamMatchData.setStartedWithCube(true);
            teamMatchData.setStartLocationX(0.05f);
            teamMatchData.setStartLocationY(0.5f);
            Database.getInstance().updateTeamMatchData(teamMatchData);

            return null;
        }
    }
}
