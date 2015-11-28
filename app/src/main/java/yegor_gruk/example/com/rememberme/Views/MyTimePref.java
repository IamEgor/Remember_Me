package yegor_gruk.example.com.rememberme.Views;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.text.ParseException;

import yegor_gruk.example.com.rememberme.Util.Utilities;

public class MyTimePref extends DialogPreference {

    private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    private static final String PREFERENCE_NS = "http://schemas.android.com/apk/lib/com.gruk.yegorka.testprefslistener.MyTimePref";

    private static final String ATTR_DEFAULT_VALUE = "defaultValue";
    private static final String ATTR_SUMMARY = "summary";
    private static final String ATTR_IS_24_HOUR = "is24HourView";

    //private final String mDefaultValue;

    private boolean is24HourView;
    private String summary;
    private String mDefaultValue;

    private TimePicker picker;

    public MyTimePref(Context context, AttributeSet attrs) {
        super(context, attrs);

        is24HourView = attrs.getAttributeBooleanValue(PREFERENCE_NS, ATTR_IS_24_HOUR, true);

        String timeStr = attrs.getAttributeValue(ANDROID_NS, ATTR_DEFAULT_VALUE);

        int attributeId = attrs.getAttributeResourceValue(ANDROID_NS, ATTR_SUMMARY, -1);

        if (attributeId == -1)
            throw new RuntimeException("invalid summary resource");

        //summary = context.getString(attributeId);

        Log.wtf("MyTimePref constructor", " timeStr -" + timeStr);

        if (timeStr != null)
            mDefaultValue = timeStr;
        else
            mDefaultValue = Utilities.getTimeStr();

        setDialogTitle(null);

    }

    @Override
    protected void onAttachedToActivity() {
        if (getPersistedString(null) == null)
            persistString(mDefaultValue);
        super.onAttachedToActivity();
    }

    @Override
    protected View onCreateDialogView() {

        picker = new TimePicker(getContext());
        picker.setIs24HourView(is24HourView);

        mDefaultValue = getPersistedString(mDefaultValue);

        try {

            int[] hoursAndMinutes = Utilities.getHoursAndMinutes(mDefaultValue);

            picker.setCurrentHour(hoursAndMinutes[0]);
            picker.setCurrentMinute(hoursAndMinutes[1]);

        } catch (ParseException e) {
            throw new RuntimeException(" protected View onCreateDialogView() {");
        }

        return picker;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (!positiveResult)
            return;

        if (shouldPersist()) {

            int hour = picker.getCurrentHour();
            int minute = picker.getCurrentMinute();

            mDefaultValue = Utilities.getTimeStr(hour, minute);

            persistString(mDefaultValue);

        }

        notifyChanged();
    }

    @Override
    public CharSequence getSummary() {

        String summary = super.getSummary().toString();
        String value = getPersistedString(mDefaultValue);

        return String.format(summary, value);
    }

}
