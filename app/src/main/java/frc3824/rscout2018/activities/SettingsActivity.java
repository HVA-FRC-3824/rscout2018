package frc3824.rscout2018.activities;


import android.os.Bundle;
import android.preference.PreferenceActivity;

import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.fragments.settings.SettingsFragment;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
@MakeActivityStarter
public class SettingsActivity extends AppCompatPreferenceActivity
{
    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
