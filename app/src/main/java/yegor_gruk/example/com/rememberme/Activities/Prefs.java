package yegor_gruk.example.com.rememberme.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import yegor_gruk.example.com.rememberme.R;

public class Prefs extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String REMOVE_EXTRA = "remove_extra";

    private static final String PREFERENCE_KEY = "MyTimePref";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        boolean clearPrefs = getIntent().getBooleanExtra(REMOVE_EXTRA, false);

        if (clearPrefs) {

            String startTime = getString(R.string.start_time_key);
            String stopTime = getString(R.string.stop_time_key);

            SharedPreferences.Editor editor = getPreferenceScreen().getSharedPreferences().edit();

            editor.remove(startTime);
            editor.remove(stopTime);

            editor.apply();
        }

    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREFERENCE_KEY)) {
            // Notify that value was really changed
            String value = sharedPreferences.getString(PREFERENCE_KEY, null);
            Toast.makeText(this, value + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        // Unregister from changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

}
