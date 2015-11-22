package yegor_gruk.example.com.rememberme.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import yegor_gruk.example.com.rememberme.Activities.MainActivity;
import yegor_gruk.example.com.rememberme.R;

public class NotificationHelper {

    private static final String KEY_SET_UP = "KEY_SET_UP";
    private static final String KEY_OK = "KEY_OK";
    private static final String KEY_CANCEL = "KEY_CANCEL";
    //private static final String ON_NOTIFICATION_CLICK = "ON_NOTIFICATION_CLICK";

    private static final int NOTIFICATION_ID = "NOTIFICATION_ID".hashCode();

    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void createNotification() {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent onNotificationClick =
                PendingIntent.getActivity(context, (int) System.currentTimeMillis(),
                        new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);

        Notification builder = new Notification.Builder(context)
                .setContentTitle("Приступить к занятию")
                        //.setContentText("Subject")
                .setSmallIcon(R.drawable.ic_group_work_black_24dp)
                .setContentIntent(onNotificationClick)
                .addAction(R.drawable.ic_group_work_black_24dp, "Начать", getPendingIntent(KEY_OK))
                .addAction(R.drawable.ic_group_work_black_24dp, "Пропустить", getPendingIntent(KEY_CANCEL))
                        //.setAutoCancel(true)
                        //.setOngoing(true)
                .build();

        builder.flags |= Notification.FLAG_AUTO_CANCEL;
        builder.defaults |= Notification.DEFAULT_SOUND;

        wakeUpFor(10);

        notificationManager.notify(NOTIFICATION_ID, builder);

    }

    private PendingIntent getPendingIntent(String key) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(key, key);
        return PendingIntent.getActivity(context, key.hashCode(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }


    public void sendNotificationForSetUp(String title, String text) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Намерение для запуска второй активности

        // Строим уведомление
        Notification builder = new Notification.Builder(context)
                //.setTicker(context.getResources().getString(R.string.notification_ticker))
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_group_work_black_24dp)
                        //.setContentIntent(onNotificationClick) <= getPendingIntent(KEY_SET_UP)
                .addAction(R.drawable.ic_group_work_black_24dp, "Начать", getPendingIntent(KEY_SET_UP))
                .build();

        // убираем уведомление, когда его выбрали
        builder.flags |= Notification.FLAG_AUTO_CANCEL;
        builder.defaults |= Notification.DEFAULT_SOUND;

        wakeUpFor(10);

        notificationManager.notify(0, builder);
    }


    public void wakeUpFor(int seconds) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        boolean isScreenOn = pm.isScreenOn();//pm.isScreenOn();

        Log.e("screen on.............", "" + isScreenOn);

        if (!isScreenOn) {
            //FLAG_KEEP_SCREEN_ON
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");

            wl.acquire(seconds * 1000);
            PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");

            wl_cpu.acquire(seconds * 1000);
        }
    }
}
