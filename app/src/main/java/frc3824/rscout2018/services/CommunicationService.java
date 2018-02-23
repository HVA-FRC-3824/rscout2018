package frc3824.rscout2018.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import frc3824.rscout2018.buses.ToastBus;
import frc3824.rscout2018.buses.ToastRequest;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.DataModel;
import frc3824.rscout2018.database.data_models.MatchLogistics;
import frc3824.rscout2018.database.data_models.SuperMatchData;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.utilities.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author frc3824
 */

public class CommunicationService extends IntentService
{
    OkHttpClient mClient;
    String mUrl;
    private static final MediaType kTXT = MediaType.parse("application/txt; charset=utf-8");
    private static final MediaType kIMAGE = MediaType.parse("image/png; charset=utf-8");
    private static final MediaType kJSON = MediaType.parse("application/json; charset=utf-8");
    static Set<TeamMatchData> mTmdQueue = null;
    static Set<SuperMatchData> mSmdQueue;
    static Gson mGson;

    /**
     * Constructor
     */
    public CommunicationService()
    {
        super("Comms Service");
        mClient = new OkHttpClient();
        if(mTmdQueue == null)
        {
            mTmdQueue = new HashSet<>();
            mSmdQueue = new HashSet<>();
            mGson = new GsonBuilder()
                    .setExclusionStrategies(new DataModel.DataModelExclusionStrategy())
                    .create();
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        updateUrl();
    }

    private void updateUrl()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPreferences.getString(Constants.Settings.SERVER_IP, "127.0.0.1");
        String port = sharedPreferences.getString(Constants.Settings.SERVER_PORT, "38241");
        if(!url.isEmpty() && !port.isEmpty())
        {
            mUrl = String.format("http://%s:%s", url, port);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if (intent.hasExtra(Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING))
        {
            putMatchData(intent.getIntExtra(Constants.IntentExtras.MATCH_NUMBER, -1), intent.getIntExtra(Constants.IntentExtras.TEAM_NUMBER, -1));
        }
        else if (intent.hasExtra(Constants.IntentExtras.NextPageOptions.PIT_SCOUTING))
        {
            //handleSendingTeamPitData(intent.getStringExtra(Constants.IntentExtras.NextPageOptions.PIT_SCOUTING));
        }
        else if (intent.hasExtra(Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING))
        {
            putSuperData(intent.getIntExtra(Constants.IntentExtras.MATCH_NUMBER, -1));
        }
        else if(intent.hasExtra(Constants.IntentExtras.DOWNLOAD_SCHEDULE))
        {
            getSchedule();
        }
        else if(intent.hasExtra(Constants.IntentExtras.DOWNLOAD_FULL_UPDATE))
        {
            // getFullUpdate();
        }
        else if(intent.hasExtra(Constants.IntentExtras.DOWNLOAD_PIT_DATA))
        {
            // getPitData();
        }
        else if (intent.hasExtra(Constants.IntentExtras.IP_MODIFIED))
        {
            updateUrl();
        }
        else if( intent.hasExtra(Constants.IntentExtras.PING))
        {
            getPing();
        }
    }

    private void getPing()
    {
        Request request = new Request.Builder()
                .url(mUrl + "/ping")
                .get()
                .build();

        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.code() == 200)
            {
                ToastBus.getInstance().publish(new ToastRequest("Pong",
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.SUCCESS));
            }
            else
            {
                ToastBus.getInstance().publish(new ToastRequest(String.format("Error: Response code: %d", response.code()),
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.ERROR));
            }
        }
        catch (IOException e)
        {
            ToastBus.getInstance().publish(new ToastRequest("Ping failed",
                                                            TastyToast.LENGTH_LONG,
                                                            TastyToast.ERROR));
        }
    }

    private void putMatchData(int matchNumber, int teamNumber)
    {
        if(matchNumber == -1 || teamNumber == -1)
        {
            ToastBus.getInstance().publish(new ToastRequest("Incorrect intent parameters",
                                                            TastyToast.LENGTH_LONG,
                                                            TastyToast.ERROR));
            return;
        }

        TeamMatchData teamMatchData = new TeamMatchData(teamNumber, matchNumber);
        RequestBody body = RequestBody.create(kJSON, mGson.toJson(teamMatchData));

        Request request = new Request.Builder()
                .url(mUrl + "/updateTeamMatchData")
                .post(body)
                .build();

        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.code() == 200)
            {
                ToastBus.getInstance().publish(new ToastRequest("Team Match Data Saved",
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.SUCCESS));
                // If success then start unloading the queue
                unloadQueue();
            }
            else
            {
                ToastBus.getInstance().publish(new ToastRequest(String.format("Error: Response code: %d", response.code()),
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.ERROR));
                // If failure then add to the queue for when connection is back
                mTmdQueue.add(teamMatchData);
            }
        }
        catch (IOException e)
        {
            ToastBus.getInstance().publish(new ToastRequest("Save Failure",
                                                            TastyToast.LENGTH_LONG,
                                                            TastyToast.ERROR));
            // If failure then add to the queue for when connection is back
            mTmdQueue.add(teamMatchData);
        }
    }

    private void putSuperData(int matchNumber)
    {
        if(matchNumber == -1)
        {
            ToastBus.getInstance().publish(new ToastRequest("Incorrect intent parameters",
                                                            TastyToast.LENGTH_LONG,
                                                            TastyToast.ERROR));
            return;
        }

        SuperMatchData superMatchData = new SuperMatchData(matchNumber);
        RequestBody body = RequestBody.create(kJSON, mGson.toJson(superMatchData));

        Request request = new Request.Builder()
                .url(mUrl + "/updateSuperMatchData")
                .post(body)
                .build();

        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.code() == 200)
            {
                ToastBus.getInstance().publish(new ToastRequest("Super Match Data Saved",
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.SUCCESS));
                // If success then start unloading the queue
                unloadQueue();
            }
            else
            {
                ToastBus.getInstance().publish(new ToastRequest(String.format("Error: Response code: %d", response.code()),
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.ERROR));
                // If failure then add to the queue for when connection is back
                mSmdQueue.add(superMatchData);
            }
        }
        catch (IOException e)
        {
            ToastBus.getInstance().publish(new ToastRequest("Save Failure",
                                                            TastyToast.LENGTH_LONG,
                                                            TastyToast.ERROR));
            // If failure then add to the queue for when connection is back
            mSmdQueue.add(superMatchData);
        }
    }

    private void unloadQueue()
    {
        // If nothing in either queue then nothing to send
        if (mTmdQueue.isEmpty() && mSmdQueue.isEmpty())
        {
            return;
        }
        ToastBus.getInstance().publish(new ToastRequest("Unloading queued data",
                                                        TastyToast.LENGTH_SHORT,
                                                        TastyToast.SUCCESS));

        if (!mTmdQueue.isEmpty())
        {
            RequestBody body = RequestBody.create(kJSON, mGson.toJson(mTmdQueue));

            Request request = new Request.Builder()
                    .url(mUrl + "/updateTeamMatchDataList")
                    .post(body)
                    .build();

            try
            {
                Response response = mClient.newCall(request).execute();
                if (response.code() == 200)
                {
                    ToastBus.getInstance()
                            .publish(new ToastRequest("Team Match Data from queue Saved",
                                                      TastyToast.LENGTH_LONG,
                                                      TastyToast.SUCCESS));
                    mTmdQueue.clear();
                }
                else
                {
                    ToastBus.getInstance()
                            .publish(new ToastRequest(String.format("Error: Response code: %d",
                                                                    response.code()),
                                                      TastyToast.LENGTH_LONG,
                                                      TastyToast.ERROR));
                }
            }
            catch (IOException e)
            {
                ToastBus.getInstance().publish(new ToastRequest("Save Failure",
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.ERROR));
            }
        }


        if (!mSmdQueue.isEmpty())
        {
            RequestBody body = RequestBody.create(kJSON, mGson.toJson(mSmdQueue));
            Request request = new Request.Builder()
                    .url(mUrl + "/updateSuperMatchDataList")
                    .post(body)
                    .build();

            try
            {
                Response response = mClient.newCall(request).execute();
                if (response.code() == 200)
                {
                    ToastBus.getInstance()
                            .publish(new ToastRequest("Super Match Data from queue Saved",
                                                      TastyToast.LENGTH_LONG,
                                                      TastyToast.SUCCESS));
                    mSmdQueue.clear();
                }
                else
                {
                    ToastBus.getInstance()
                            .publish(new ToastRequest(String.format("Error: Response code: %d",
                                                                    response.code()),
                                                      TastyToast.LENGTH_LONG,
                                                      TastyToast.ERROR));
                }
            }
            catch (IOException e)
            {
                ToastBus.getInstance().publish(new ToastRequest("Save Failure",
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.ERROR));
            }
        }
    }

    private void getSchedule()
    {
        Request request = new Request.Builder()
                .url(mUrl + "/schedule")
                .get()
                .build();

        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.code() == 200)
            {
                ToastBus.getInstance().publish(new ToastRequest("Schedule Received",
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.SUCCESS));
                ResponseBody body = response.body();
                ArrayList<MatchLogistics> matches = mGson.fromJson(body.toString(), new TypeToken<List<MatchLogistics>>(){}.getType());
                for(MatchLogistics match : matches)
                {
                    Database.getInstance().updateMatchLogistics(match);
                }
            }
            else
            {
                ToastBus.getInstance().publish(new ToastRequest(String.format("Error: Response code: %d", response.code()),
                                                                TastyToast.LENGTH_LONG,
                                                                TastyToast.ERROR));
            }
        }
        catch (IOException e)
        {
            ToastBus.getInstance().publish(new ToastRequest("Schedule request failed",
                                                            TastyToast.LENGTH_LONG,
                                                            TastyToast.ERROR));
        }
    }

}
