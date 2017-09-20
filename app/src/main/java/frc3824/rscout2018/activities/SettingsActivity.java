package frc3824.rscout2018.activities;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import activitystarter.MakeActivityStarter;
import frc3824.rscout2018.R;

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
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener mBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener()
    {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value)
        {
            String stringValue = value.toString();

            if (preference instanceof ListPreference)
            {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
            }
            else
            {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #mBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference, String defaultSummary) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(mBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        mBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                                                                 PreferenceManager
                                                                         .getDefaultSharedPreferences(preference.getContext())
                                                                         .getString(preference.getKey(), defaultSummary));
    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstance)
        {
            super.onCreate(savedInstance);
            addPreferencesFromResource(R.xml.preferences);

            bindPreferenceSummaryToValue(findPreference("match_scout_position"), "Which position the match scout with this tablet will be viewing (Blue 1, Blue 2, etc)");
            bindPreferenceSummaryToValue(findPreference("pit_scout_position"), "Which position the pit scout with this tablet will has");
        }
    }

}
