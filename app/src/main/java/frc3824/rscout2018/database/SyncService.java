package frc3824.rscout2018.database;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import frc3824.rscout2018.database.data_models.SyncResponse;

/**
 * A service to poll the remote database
 */
public class SyncService extends IntentService
{
    private static final String TAG = "SyncService";
    static List<String> m_buffer = null;

    public SyncService()
    {
        super("syncdb");
        if(m_buffer == null)
        {
            m_buffer = new ArrayList<>();
        }
    }

    public static void add(String json)
    {
        m_buffer.add(json);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        if(m_buffer.size() > 0)
        {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String json = gson.toJson(m_buffer);

            params.put("data_list", json);
        }

        //todo: Pull url from settings

        client.post("127.0.0.1:38240/",
                    params,
                    new AsyncHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                        {
                            Log.d(TAG, "Successful transfer");
                            m_buffer.clear();

                            // Save the response from the server
                            SyncResponse response = new Gson().fromJson(new String(responseBody), SyncResponse.class);
                            response.save();
                        }

                        @Override
                        public void onFailure(int statusCode,
                                              Header[] headers,
                                              byte[] responseBody,
                                              Throwable error)
                        {
                            Log.d(TAG, String.format("Error on transfer: Status Code - %d", statusCode));
                        }
                    });
    }
}
