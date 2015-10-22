package yegor_gruk.example.com.rememberme.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import yegor_gruk.example.com.rememberme.AlarmHandler;
import yegor_gruk.example.com.rememberme.ModelsAndPrefs.AlarmScheduleModel;

/**
 * Created by Egor on 04.10.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        Log.d("AR.onReceive() - ", "I'm running");

        AlarmScheduleModel scheduleModel = new AlarmScheduleModel(context);

        if (scheduleModel.decrementReps() <= 0)
            new AlarmHandler(context).cancelAlarm();
        //добавить public поле AlarmScheduleModel в AlarmHandler

    }
}
