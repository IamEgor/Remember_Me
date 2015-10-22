package yegor_gruk.example.com.rememberme.ModelsAndPrefs;

import android.content.Context;

/**
 * Created by Egor on 04.10.2015.
 */

public class AlarmScheduleModel {

    public static final String INTERVAL = "interval";
    public static final String REPS = "reps";

    private int interval;
    private int repsLeft;

    private Context context;
    private Prefs scheduleModelPrefs;

    public AlarmScheduleModel(Context context, int interval, int repsLeft) {

        this.context = context;

        this.interval = interval;
        this.repsLeft = repsLeft;

        scheduleModelPrefs = new Prefs(context);
    }

    public AlarmScheduleModel(Context context) {
        this.context = context;
    }

    /*
    public long getInterval() {
        return scheduleModelPrefs.getsPrefs(INTERVAL);
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getRepsLeft() {
        return scheduleModelPrefs.getsPrefs(REPS);
    }

    public void setRepsLeft(int repsLeft) {
        this.repsLeft = repsLeft;
    }

    */

    public int getInterval() {
        return scheduleModelPrefs.getInterval();
    }

    public int getRepsLeft() {
        return scheduleModelPrefs.getRepsLeft();
    }

    public void saveAll() {
        scheduleModelPrefs.saveAll();
    }

    public int decrementReps() {
        return scheduleModelPrefs.decrementReps();
    }


    private class Prefs extends AppPrefs {

        public static final String INTERVAL = "INTERVAL";
        public static final String REPS = "REPS";

        private Context context;

        public Prefs(Context context) {
            super(context);
            this.context = context;
        }

        public int getInterval() {
            return getInt(INTERVAL, 1);
        }

        public int getRepsLeft() {
            return getInt(REPS, 1);
        }

        public void saveAll() {
            put(INTERVAL, interval);
            put(REPS, repsLeft);
        }

        public int decrementReps() {
            int value = getRepsLeft();
            put(REPS, --value);
            return value;
        }

    }
}
