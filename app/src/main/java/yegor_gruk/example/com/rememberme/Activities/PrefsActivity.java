package yegor_gruk.example.com.rememberme.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;

import yegor_gruk.example.com.rememberme.AlarmHandler;
import yegor_gruk.example.com.rememberme.Prefs.AppPrefs;
import yegor_gruk.example.com.rememberme.Prefs.ListActivityPrefs;
import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.Util.Utilities;

public class PrefsActivity extends AppCompatPreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String REMOVE_EXTRA = "remove_extra";

    private static final String PREFERENCE_KEY = "MyTimePref";
    private final View.OnClickListener mActionBarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent returnIntent = new Intent();
            if (v.getId() == R.id.action_done) {
                try {
                    AlarmHandler alarmHandler = new AlarmHandler(PrefsActivity.this);
                    alarmHandler.createAlarmQueueThenCallLoader(Utilities.getAlarmsTime(PrefsActivity.this));
                    new ListActivityPrefs(PrefsActivity.this).setSwitchState(true);
                    setResult(RESULT_OK, returnIntent);
                } catch (ParseException e) {
                    throw new RuntimeException();
                }
            } else if (v.getId() == R.id.action_cancel) {
                //TODO сброс значений полей
                setResult(RESULT_CANCELED, returnIntent);
            }
            finish();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(AppPrefs.APP_PREFS);
        addPreferencesFromResource(R.xml.prefs);

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        boolean clearPrefs = getIntent().getBooleanExtra(REMOVE_EXTRA, false);

        if (clearPrefs) {

            String startTime = getString(R.string.start_time_key);
            String stopTime = getString(R.string.stop_time_key);
            String reps = getString(R.string.reps_key);

            SharedPreferences.Editor editor = getPreferenceScreen().getSharedPreferences().edit();

            editor.remove(startTime);
            editor.remove(stopTime);
            editor.remove(reps);

            editor.apply();
        }

        LayoutInflater inflater = getLayoutInflater();
        // Inflate the custom view and add click handlers for the buttons
        View actionBarButtons = inflater.inflate(R.layout.edit_event_custom_actionbar,
                new LinearLayout(this), false);

        View cancelActionView = actionBarButtons.findViewById(R.id.action_cancel);
        cancelActionView.setOnClickListener(mActionBarListener);

        View doneActionView = actionBarButtons.findViewById(R.id.action_done);
        doneActionView.setOnClickListener(mActionBarListener);

        // Retrieve an instance of the Activity's ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Hide the icon, title and home/up button
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        // Set the custom view and allow the bar to show it
        actionBar.setCustomView(actionBarButtons);
        actionBar.setDisplayShowCustomEnabled(true);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREFERENCE_KEY)) {
            // Notify that value was really changed
            //TODO вызов предупреждений(время старта больше времени останова, слишком много вызовов за короткий промежуток и др)
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
