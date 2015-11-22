package yegor_gruk.example.com.rememberme.Views.PreferenceViews;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
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

    private final String mDefaultValue;

    private boolean is24HourView;
    private String summary;
    private String timeStr;

    private TimePicker picker;

    public MyTimePref(Context context, AttributeSet attrs) {
        super(context, attrs);

        is24HourView = attrs.getAttributeBooleanValue(PREFERENCE_NS, ATTR_IS_24_HOUR, true);
        mDefaultValue = attrs.getAttributeValue(ANDROID_NS, ATTR_DEFAULT_VALUE);

        int attributeId = attrs.getAttributeResourceValue(ANDROID_NS, ATTR_SUMMARY, -1);

        if (attributeId == -1)
            throw new RuntimeException("invalid summary resource");

        summary = context.getString(attributeId);

        if (mDefaultValue != null)
            timeStr = mDefaultValue;
        else
            timeStr = Utilities.getTimeStr();

        setDialogTitle(null);

    }

    @Override
    protected View onCreateDialogView() {

        picker = new TimePicker(getContext());
        picker.setIs24HourView(is24HourView);

        timeStr = getPersistedString(timeStr);

        try {

            int[] hoursAndMinutes = Utilities.getHoursAndMinutes(timeStr);

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

            timeStr = Utilities.getTimeStr(hour, minute);

            persistString(timeStr);

        }

        notifyChanged();
    }

    @Override
    public CharSequence getSummary() {

        String summary = super.getSummary().toString();
        String value = getPersistedString(timeStr);

        return String.format(summary, value);
    }

}
