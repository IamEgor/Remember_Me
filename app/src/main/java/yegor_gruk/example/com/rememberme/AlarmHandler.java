package yegor_gruk.example.com.rememberme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import yegor_gruk.example.com.rememberme.BroadcastReceivers.AlarmReceiver;
import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.DataBase.ModelDAO;

public class AlarmHandler {

    ExecutorService service;
    private Context context;
    private Intent alarmIntent;
    private AlarmManager manager;
    private Runnable runnable;

    public AlarmHandler(final Context context) {

        this.context = context;

        alarmIntent = new Intent(context, AlarmReceiver.class);
        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        service = Executors.newFixedThreadPool(1);

    }

    public void createAlarmQueue(long[] alarms) {
        service.execute(new MyRunnable(alarms));
    }


    public void setAlarm(long idAndTime) {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("FUCK", "FUCK$" + idAndTime);
        // Loop counter `i` is used as a `requestCode`
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) idAndTime, intent, 0);

        Log.wtf("AlarmHandler ", " int " + ((int) idAndTime) + " long " + idAndTime);

        //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) idAndTime, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

        //setExactAndAllowWhileIdle()
        //setAlarmClock())));
        manager.set(AlarmManager.RTC_WAKEUP, idAndTime, pendingIntent);

    }

    public void cancelAlarm(long idAndTime) {

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) idAndTime, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

        manager.cancel(pendingIntent);
    }

    class MyRunnable implements Runnable {

        private long[] alarms;

        public MyRunnable(long[] alarms) {
            this.alarms = alarms;
        }

        @Override
        public void run() {
            SimpleDateFormat format = new SimpleDateFormat(context.getString(R.string.time_format));

            DatabaseModel databaseModel = new DatabaseModel();

            ModelDAO dao;

            try {
                dao = HelperFactory.getHelper().getModelDAO();

                for (long alarmTime : alarms) {

                    Log.wtf("!!!!!", "alarm at " + format.format(alarmTime));

                    databaseModel.setRepTime(alarmTime);
                    databaseModel.setIsActive(true);

                    dao.create(databaseModel);

                    setAlarm(alarmTime);
                }

            } catch (SQLException e) {

                Log.wtf("!!!!!", e.getMessage());

                throw new RuntimeException();
            }


        }


    }

}
