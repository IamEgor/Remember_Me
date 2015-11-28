package yegor_gruk.example.com.rememberme.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.WaiterPackage.TimerWaiter;


public class MySeekBarPref extends DialogPreference implements DiscreteSeekBar.OnProgressChangeListener {

    private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";

    // Attribute names
    private static final String ATTR_DEFAULT_VALUE = "defaultValue";
    // Default values for defaults
    private static final int DEFAULT_CURRENT_VALUE = 5;
    private static final int DEFAULT_MIN_VALUE = 1;
    private static final int DEFAULT_MAX_VALUE = 21;
    private static final int DEFAULT_DELAY_MILLIS = 500;
    // Default persist
    private static final int DEFAULT_NOT_PERSISTED = -1;
    // Real defaults
    private final int mDefaultValue;
    private final int mMaxValue;
    private final int mMinValue;
    //TextView textView;
    DiscreteSeekBar discreteSeekBar;
    TimerWaiter timerWaiter;
    private int mCurrentValue;

    public MySeekBarPref(Context context, AttributeSet attrs) {
        super(context, attrs);

        mMinValue = DEFAULT_MIN_VALUE;
        mMaxValue = DEFAULT_MAX_VALUE;
        mDefaultValue = attrs.getAttributeIntValue(ANDROID_NS, ATTR_DEFAULT_VALUE, DEFAULT_CURRENT_VALUE);

        setDialogLayoutResource(R.layout.seekbar_layout);

        setDialogTitle(null);
        //------------------------------------------------------------------------------------------
        timerWaiter = new TimerWaiter(DEFAULT_DELAY_MILLIS) {
            @Override
            public void doOnExit() {
                onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
                getDialog().dismiss();
            }
        };
        setPositiveButtonText(null);
        setNegativeButtonText(null);
        //------------------------------------------------------------------------------------------
    }

    @Override
    protected void onAttachedToActivity() {
        if (getPersistedInt(DEFAULT_NOT_PERSISTED) == DEFAULT_NOT_PERSISTED)
            persistInt(mDefaultValue);
        Log.d("#####", "MySeekBarPref onAttachedToActivity || " + " getPersistedInt() " + getPersistedInt(DEFAULT_NOT_PERSISTED));
        super.onAttachedToActivity();
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mCurrentValue = getPersistedInt(mDefaultValue);

        // Setup DiscreteSeekBar
        discreteSeekBar = (DiscreteSeekBar) view.findViewById(R.id.discreteSeekBar);
        discreteSeekBar.setMin(mMinValue);
        discreteSeekBar.setMax(mMaxValue);
        discreteSeekBar.setProgress(mCurrentValue);

        discreteSeekBar.setOnProgressChangeListener(this);

        // Setup text label for current value
        //textView = (TextView) view.findViewById(R.id.myTextValue);
        //textView.setText(Integer.toString(mCurrentValue));

    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        // Return if change was cancelled
        if (!positiveResult) {
            return;
        }

        // Persist current value if needed
        if (shouldPersist()) {
            persistInt(mCurrentValue);
        }

        // Notify activity about changes (to update preference summary line)
        notifyChanged();
    }

    @Override
    public CharSequence getSummary() {
        // Format summary string with current value
        int value = getPersistedInt(mDefaultValue);

        return getContext().getResources().getQuantityString(R.plurals.plurals_11, value, value);

    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        mCurrentValue = value;
    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
        timerWaiter.runStartThread();
    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
        timerWaiter.runStopThread();
    }
}
