package frc3824.rscout2018;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.io.IOException;

import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.SyncService;

/**
 * Base application class that runs on start up
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        // Setup database
        try
        {
            Database.getInstance().setContext(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Setup sync service
        Intent intent = new Intent(this, SyncService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                                         SystemClock.elapsedRealtime() + 1000,
                                         AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                                         pendingIntent);
    }
}
