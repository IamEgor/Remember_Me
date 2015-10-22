package yegor_gruk.example.com.rememberme.ModelsAndPrefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Egor on 14.10.2015.
 */
public abstract class AppPrefs {

    public static final String APP_PREFS = "app_prefs";

    private Context context;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    protected AppPrefs(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    protected void put(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    protected void put(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    protected void put(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    protected String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    protected int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    protected boolean getBool(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }


}
