package yegor_gruk.example.com.rememberme.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import yegor_gruk.example.com.rememberme.Activities.ListActivity;
import yegor_gruk.example.com.rememberme.Util.NotificationHelper;
import yegor_gruk.example.com.rememberme.Util.Utilities;

/**
 * Created by Egor on 04.10.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent inIntent) {

        Log.wtf("!!!!! inIntent.getAction() : ", inIntent.getAction());

        //MainActivityPrefs mainActivityPrefs = new MainActivityPrefs(context);

        Bundle bundle = inIntent.getExtras();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            Log.wtf("!!!!! values ", String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }

        NotificationHelper helper = new NotificationHelper(context);
        helper.createNotification();

        Intent intent = new Intent(ListActivity.BROADCAST_ACTION);
        intent.putExtra(ListActivity.BROADCAST_ACTION, Utilities.NOTIFY_DATA);
        context.sendBroadcast(intent);
    }
}
