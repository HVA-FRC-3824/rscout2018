package frc3824.rscout2018.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import frc3824.rscout2018.database.data_models.SuperMatchData;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.database.data_models.TeamPitData;
import frc3824.rscout2018.database.data_models.Update;
import frc3824.rscout2018.utilities.Constants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    /**
     * Constructor
     */
    public CommunicationService()
    {
        super("Comms Service");
        mClient = new OkHttpClient();

        updateUrl();
    }

    private void updateUrl()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPreferences.getString(Constants.Settings.SERVER_IP, "127.0.0.1");
        String port = sharedPreferences.getString(Constants.Settings.SERVER_PORT, "38241");
        mUrl = String.format("http://%s:%s", url, port);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if (intent.hasExtra(Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING))
        {
            handleSendingTeamMatchData(intent.getStringExtra(Constants.IntentExtras.NextPageOptions.MATCH_SCOUTING));
        }
        else if (intent.hasExtra(Constants.IntentExtras.NextPageOptions.PIT_SCOUTING))
        {
            handleSendingTeamPitData(intent.getStringExtra(Constants.IntentExtras.NextPageOptions.PIT_SCOUTING));
        }
        else if (intent.hasExtra(Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING))
        {
            handleSendingSuperMatchData(intent.getStringExtra(Constants.IntentExtras.NextPageOptions.SUPER_SCOUTING));
        }
        else if(intent.hasExtra(Constants.IntentExtras.LOAD_DATA))
        {
            handleSendingUpdateRequest();
        }
        else if (intent.hasExtra(Constants.IntentExtras.IP_MODIFIED))
        {
            updateUrl();
        }
        else if( intent.hasExtra(Constants.IntentExtras.PING))
        {
            ping();
        }
    }

    private void handleSendingTeamMatchData(String json)
    {
        RequestBody body = RequestBody.create(kJSON, json);
        Request request = new Request.Builder()
                .url(mUrl)
                .post(body)
                .build();
        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful())
            {
                TastyToast.makeText(this,
                                    "Save successfully",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.SUCCESS).show();
            }
            else
            {
                TastyToast.makeText(this,
                                    String.format("Error: Response code: %d", response.code()),
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR).show();
            }
        }
        catch (IOException e)
        {
            TastyToast.makeText(this, "Save failed", TastyToast.LENGTH_LONG, TastyToast.ERROR)
                      .show();
        }
    }

    private void handleSendingTeamPitData(String json)
    {
        RequestBody body = RequestBody.create(kJSON, json);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart(Constants.IntentExtras.NextPageOptions.PIT_SCOUTING, json);
        Gson gson = new Gson();
        TeamPitData tpd = gson.fromJson(json, TeamPitData.class);
        for(String filepath : tpd.getPictureFilepaths())
        {
            int last_slash = filepath.lastIndexOf('/');
            String filename = filepath.substring(last_slash);
            String name = filename.substring(0, filename.lastIndexOf('.'));

            builder.addFormDataPart(name, filename, RequestBody.create(kIMAGE, new File(filepath)));
        }

        Request request = new Request.Builder()
                .url(mUrl)
                .post(builder.build())
                .build();
        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful())
            {
                TastyToast.makeText(this,
                                    "Save successfully",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.SUCCESS).show();
            }
            else
            {
                TastyToast.makeText(this,
                                    String.format("Error: Response code: %d", response.code()),
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR).show();
            }
        }
        catch (IOException e)
        {
            TastyToast.makeText(this, "Save failed", TastyToast.LENGTH_LONG, TastyToast.ERROR)
                      .show();
        }
    }


    private void handleSendingSuperMatchData(String json)
    {
        RequestBody body = RequestBody.create(kJSON, json);
        Request request = new Request.Builder()
                .url(mUrl)
                .post(body)
                .build();
        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful())
            {
                TastyToast.makeText(this,
                                    "Save successfully",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.SUCCESS).show();
            }
            else
            {
                TastyToast.makeText(this,
                                    String.format("Error: Response code: %d", response.code()),
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR).show();
            }
        }
        catch (IOException e)
        {
            TastyToast.makeText(this, "Save failed", TastyToast.LENGTH_LONG, TastyToast.ERROR)
                      .show();
        }
    }

    private void handleSendingUpdateRequest()
    {
        RequestBody body = RequestBody.create(kTXT, "update");
        Request request = new Request.Builder()
                .url(mUrl)
                .post(body)
                .build();

        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful())
            {
                TastyToast.makeText(this,
                                    "Update Received",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.SUCCESS).show();

                Gson gson = new Gson();
                Update update =gson.fromJson(response.body().toString(), Update.class);
                update.save();
            }
            else
            {
                TastyToast.makeText(this,
                                    String.format("Error: Response code: %d", response.code()),
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR).show();
            }
        }
        catch (IOException e)
        {
            TastyToast.makeText(this, "Update failed", TastyToast.LENGTH_LONG, TastyToast.ERROR)
                      .show();
        }
    }

    private void ping()
    {
        RequestBody body = RequestBody.create(kTXT, "ping");
        Request request = new Request.Builder()
                .url(mUrl)
                .post(body)
                .build();

        try
        {
            Response response = mClient.newCall(request).execute();
            if (response.code() == 200)
            {
                TastyToast.makeText(this,
                                    "Pong",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.SUCCESS).show();
            }
            else
            {
                TastyToast.makeText(this,
                                    String.format("Error: Response code: %d", response.code()),
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR).show();
            }
        }
        catch (IOException e)
        {
            TastyToast.makeText(this, "Ping failed", TastyToast.LENGTH_LONG, TastyToast.ERROR)
                      .show();
        }
    }

}
