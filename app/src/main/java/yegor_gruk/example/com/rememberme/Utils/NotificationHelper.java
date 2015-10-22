package yegor_gruk.example.com.rememberme.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import yegor_gruk.example.com.rememberme.Activities.MainActivity;
import yegor_gruk.example.com.rememberme.R;

public class NotificationHelper {

    private static final String KEY_OK = "KEY_OK";
    private static final String KEY_CANCEL = "KEY_CANCEL";
    private static final String ON_NOTIFICATION_CLICK = "ON_NOTIFICATION_CLICK";

    private static final int NOTIFICATION_ID = "NOTIFICATION_ID".hashCode();

    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void createNotification() {

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
                        //.setAutoCancel(false)
                .setOngoing(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, builder);

    }

    private PendingIntent getPendingIntent(String key) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(key, key);
        return PendingIntent.getActivity(context, key.hashCode(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
