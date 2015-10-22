package yegor_gruk.example.com.rememberme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import yegor_gruk.example.com.rememberme.Activities.MainActivity;
import yegor_gruk.example.com.rememberme.BroadcastReceivers.AlarmReceiver;
import yegor_gruk.example.com.rememberme.ModelsAndPrefs.AlarmScheduleModel;

/**
 * Created by Egor on 04.10.2015.
 */
public class AlarmHandler {

    private Context context;
    private PendingIntent pendingIntent;

    public AlarmHandler(Context context) {
        this.context = context;
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
    }


    public void setRepeatingAlarm(long intervalLength, int times) {

        /*
         * Слабое место. int может быть переполнен
         */

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
    public void sendNotification(){

        String bigText = "Это я, почтальон Печкин. Принес для вас посылку. "
                + "Только я вам ее не отдам. Потому что у вас документов нету. ";

        Intent resultIntent = new Intent(context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setTicker("Пришла посылка!")
                .setContentTitle("Уведомление с большим текстом")
                .setContentText(
                        "Это я, почтальон Печкин. Принес для вас посылку")
                .setSmallIcon(R.drawable.ic_group_work_black_24dp)
                .addAction(R.drawable.ic_invert_colors_black_48dp, "Запустить активность", resultPendingIntent)
                .setAutoCancel(true);

        Notification notification = new Notification.BigTextStyle(builder)
                .bigText(bigText).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);

    }
    */
}
