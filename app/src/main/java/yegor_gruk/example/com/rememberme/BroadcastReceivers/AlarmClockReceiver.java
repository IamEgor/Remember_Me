package yegor_gruk.example.com.rememberme.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import yegor_gruk.example.com.rememberme.AlarmHandler;

/**
 * Created by Egor on 05.10.2015.
 */
public class AlarmClockReceiver extends BroadcastReceiver {

    public static final String ALARM_ALERT_ACTION = "com.android.deskclock.ALARM_ALERT";
    public static final String ALARM_SNOOZE_ACTION = "com.android.deskclock.ALARM_SNOOZE";
    public static final String ALARM_DISMISS_ACTION = "com.android.deskclock.ALARM_DISMISS";
    public static final String ALARM_DONE_ACTION = "com.android.deskclock.ALARM_DONE";

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        String action = intent.getAction();
        if (action.equals(ALARM_ALERT_ACTION) || action.equals(ALARM_DISMISS_ACTION) || action.equals(ALARM_SNOOZE_ACTION) || action.equals(ALARM_DONE_ACTION))
        {
            // for play/pause mediaplayer
            Toast.makeText(context, "AlarmClockReceiver catched", Toast.LENGTH_LONG).show();
            Log.d("AlarmClockReceiver", "AlarmClockReceiver catched");
        }
        */
        Toast.makeText(context, "AlarmClockReceiver catched", Toast.LENGTH_LONG).show();
        Log.d("AlarmClockReceiver", "AlarmClockReceiver catched");

        AlarmHandler handler = new AlarmHandler(context);
        handler.sendNotification();

    }
}

