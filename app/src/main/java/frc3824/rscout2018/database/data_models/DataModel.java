package frc3824.rscout2018.database.data_models;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.SyncService;

/**
 * The base Data Model class
 */
public class DataModel extends BaseObservable implements ExclusionStrategy, Document.ChangeListener
{
    protected boolean mDirty = false;
    protected Document mDocument = null;
    protected DataModelOnUpdate mOnUpdate = null;

    /**
     * @returns Whether the model is different from the one in the database
     */
    public boolean isDirty()
    {
        return mDirty;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f)
    {
        if(f.getName() == "mDirty")
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz)
    {
        return false;
    }

    @Override
    public void changed(Document.ChangeEvent event)
    {
        if(mOnUpdate == null)
        {
            mOnUpdate.update();
        }
    }

    /**
     * Handles when a property has been changed
     */
    class DataModelPropertyChangedCallback extends OnPropertyChangedCallback
    {

        @Override
        public void onPropertyChanged(Observable observable, int i)
        {
            mDirty = true;
        }
    }

    /**
     * Constructor
     *
     * @note protected so it cannot not be created directly and must be a parent class
     */
    protected DataModel()
    {
        mDirty = false;
        addOnPropertyChangedCallback(new DataModelPropertyChangedCallback());
    }

    /**
     * @returns A map of the variables in the child class
     */
    protected Map<String, Object> getProperties()
    {
        Map<String, Object> properties = new HashMap<>();
        for (Field field : getClass().getDeclaredFields())
        {
            try
            {
                properties.put(field.getName(), field.get(this));
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        properties.remove("mDirty");
        properties.remove("mDocument");
        properties.remove("mOnUpdate");
        properties.put("className", getClass().getName());

        return properties;
    }

    protected void save(String key)
    {
        Map<String, Object> properties = getProperties();
        try
        {
            mDocument.putProperties(properties);
        }
        catch (CouchbaseLiteException e)
        {
            e.printStackTrace();
        }
        mDirty = false;
    }

    /**
     * Sets the variables of the child class with the values in properties
     * @param properties
     */
    protected void setProperties(Map<String, Object> properties)
    {
        setProperties(properties, new ArrayList<String>());
    }

    /**
     * Sets the variables of the child class with the values in properties
     * @param properties
     * @param ignore A list of variables to not set (Usually these are set somewhere else or are the
     *               identifying variables)
     */
    protected void setProperties(Map<String, Object> properties, List<String> ignore)
    {
        for(Field field: getClass().getDeclaredFields())
        {
            if(ignore.contains(field.getName()) || field.getName() == "className")
            {
                continue;
            }

            if(properties.containsKey(field.getName()))
            {
                Object property = properties.get(field.getName());
                try
                {
                    field.set(this, property);
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void load(String key, List<String> ignore)
    {
        mDocument = Database.getInstance().getDocument(key);
        mDocument.addChangeListener(this);
        Map<String, Object> properties = mDocument.getProperties();
        setProperties(properties, ignore);
        mDirty = false;
    }

    public void setOnUpdate(DataModelOnUpdate onUpdate)
    {
        mOnUpdate = onUpdate;
    }

    /**
     * Sends a copy of this data model to the server
     */
    public void sync()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        GsonBuilder builder = new GsonBuilder();
        builder.addSerializationExclusionStrategy(this);
        Gson gson = builder.create();
        final String json = gson.toJson(this);
        Log.d(getClass().getName(), json);

        params.put("data", json);
        client.post("127.0.0.1:38240/", params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                Log.d(DataModel.this.getClass().getName(), "Successful transfer");
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  byte[] responseBody,
                                  Throwable error)
            {
                SyncService.add(json);
                if(statusCode == 404)
                {

                }
                else if(statusCode == 500)
                {

                }
                else
                {

                }
            }
        });

    }
}