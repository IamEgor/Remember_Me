package yegor_gruk.example.com.rememberme.Prefs;

import android.content.Context;

/**
 * Created by Egor on 06.12.2015.
 */
public class ListActivityPrefs extends AppPrefs {

    public static final String SWITCH_STATE = "SWITCH_STATE";

    public ListActivityPrefs(Context context) {
        super(context);
    }

    public boolean isSwitchEnabled() {
        return getBool(SWITCH_STATE, false);
    }

    public void setSwitchState(boolean enabled) {
        put(SWITCH_STATE, enabled);
    }

}
