package frc3824.rscout2018;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.io.IOException;

import frc3824.rscout2018.database.Database;

/**
 * Base application class that runs on start up
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
}
