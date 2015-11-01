package yegor_gruk.example.com.rememberme.Prefs;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import yegor_gruk.example.com.rememberme.R;

public class MainActivityPrefs extends AppPrefs {

    public static final String NUMBER_OF_INTERVALS = "NUMBER_OF_INTERVALS";
    public static final String FIRST_POINT = "FIRST_POINT";
    public static final String LAST_POINT = "LAST_POINT";

    private SimpleDateFormat dateFormat;

    public MainActivityPrefs(Context context) {
        super(context);

        dateFormat = new SimpleDateFormat(context.getString(R.string.time_format));
    }

    public void saveIntervals(int interval) {
        Log.v("saveIntervals() ", "" + interval);
        put(NUMBER_OF_INTERVALS, interval);
    }

    public void saveFirst(String first) {
        Log.v("saveFirst() ", "" + first);
        put(FIRST_POINT, first);
    }

    public void saveLast(String last) {
        Log.v("saveLast() ", "" + last);
        put(LAST_POINT, last);
    }

    public int getIntervals() {
        Log.v("getIntervals() ", "" + getInt(NUMBER_OF_INTERVALS, 1));
        return getInt(NUMBER_OF_INTERVALS, 1);
    }

    public String getFirst() {
        Log.v("getFirst() ", "" + getString(FIRST_POINT, dateFormat.format(new Date())));
        return getString(FIRST_POINT, dateFormat.format(new Date()));
    }

    public String getLast() {
        Log.v("getLast() ", "" + getString(LAST_POINT, "23:59"));
        return getString(LAST_POINT, "23:59");
    }
}