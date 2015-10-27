package yegor_gruk.example.com.rememberme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import yegor_gruk.example.com.rememberme.Activities.MainActivity;
import yegor_gruk.example.com.rememberme.BroadcastReceivers.AlarmReceiver;
import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.DataBase.ModelDAO;

public class AlarmHandler {

    private Context context;
    private Intent alarmIntent;
    private AlarmManager manager;

    public AlarmHandler(Context context) {

        this.context = context;

        alarmIntent = new Intent(context, AlarmReceiver.class);
        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }


    public void createAlarmQueue(long[] alarms) throws SQLException {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        DatabaseModel databaseModel = new DatabaseModel();

        ModelDAO dao = HelperFactory.getHelper().getModelDAO();

        for (long alarmTime : alarms) {

            Log.wtf("!!!!!", "alarm at " + format.format(alarmTime));

            databaseModel.setRepTime(alarmTime);
            databaseModel.setIsActive(true);

            dao.create(databaseModel);

            setAlarm(alarmTime);
        }

        //setAlarms(alarms);

    }


    /*
    public void setAlarms(long[] alarms) {

        for (long alarmTime : alarms)
            setAlarm(alarmTime);

    }
    */

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

    public void sendNotification() {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Намерение для запуска второй активности
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Строим уведомление
        Notification builder = new Notification.Builder(context)
                .setTicker("Пришла посылка!")
                .setContentTitle("Посылка")
                .setContentText(
                        "Это я, почтальон Печкин. Принес для вас посылку")
                .setSmallIcon(R.drawable.ic_group_work_black_24dp).setContentIntent(pIntent)
                .addAction(R.drawable.ic_group_work_black_24dp, "Открыть", pIntent)
                .build();

        // убираем уведомление, когда его выбрали
        builder.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, builder);
    }

     /*
    public void setRepeatingAlarm(long intervalLength, int times) {


         //Слабое место. int может быть переполнен

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        long temp = new Date().getTime();

        long[] array = new long[times];


        AlarmScheduleModel scheduleModel = new AlarmScheduleModel(context, (int) intervalLength, times);
        scheduleModel.saveAll();

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        long nextAlarmAt = System.currentTimeMillis() + intervalLength;
        calendar.setTimeInMillis(nextAlarmAt);

        String message = "First alarm at " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);

        Log.d("AH.setRepeatingAlarm()", message);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                intervalLength, pendingIntent);

    }


    public void cancelAlarm() {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(context, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }
    */

}
