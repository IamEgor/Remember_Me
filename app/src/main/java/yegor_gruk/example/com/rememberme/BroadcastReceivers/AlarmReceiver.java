package yegor_gruk.example.com.rememberme.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import yegor_gruk.example.com.rememberme.Util.NotificationHelper;

/**
 * Created by Egor on 04.10.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.wtf("!!!!! intent.getAction() : ", intent.getAction());

        //MainActivityPrefs mainActivityPrefs = new MainActivityPrefs(context);

        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            Log.wtf("!!!!! values ", String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }

        NotificationHelper helper = new NotificationHelper(context);
        helper.createNotification();

    }
}
