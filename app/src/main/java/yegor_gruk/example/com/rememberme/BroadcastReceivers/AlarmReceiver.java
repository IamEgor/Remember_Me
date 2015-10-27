package yegor_gruk.example.com.rememberme.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import yegor_gruk.example.com.rememberme.Prefs.MainActivityPrefs;

/**
 * Created by Egor on 04.10.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.wtf("!!!!! intent.getAction() : ", intent.getAction());

        MainActivityPrefs mainActivityPrefs = new MainActivityPrefs(context);

        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            Log.wtf("!!!!! values ", String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }
        /*
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        Log.d("AR.onReceive() - ", "I'm running");

        AlarmScheduleModel scheduleModel = new AlarmScheduleModel(context);

        if (scheduleModel.decrementReps() <= 0) {
            Toast.makeText(context, "!!! See logs !!!", Toast.LENGTH_LONG).show();
            Log.d("!!!", "AlarmReceiver.onReceive \n  if (scheduleModel.decrementReps() <= 0) { ");
        }
        */
        //new AlarmHandler(context).cancelAlarm();
        //добавить public поле AlarmScheduleModel в AlarmHandler

    }
}
