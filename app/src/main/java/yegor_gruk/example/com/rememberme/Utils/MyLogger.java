package yegor_gruk.example.com.rememberme.Utils;

import android.util.Log;

/**
 * Created by Egor on 25.10.2015.
 */
public class MyLogger {

    public static final String TAG = "%%" + MyLogger.class.toString() + "%%";

    public static void log(String tag, Object obj) {
        Log.e(tag, obj.toString());
    }

    public static void log(Object obj) {
        log(TAG, obj);
    }

    public static void log(Object[] objs) {

        for (Object obj : objs) {
            log(obj);
        }
    }

}
