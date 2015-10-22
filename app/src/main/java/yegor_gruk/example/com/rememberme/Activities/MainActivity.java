package yegor_gruk.example.com.rememberme.Activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import yegor_gruk.example.com.rememberme.AlarmHandler;
import yegor_gruk.example.com.rememberme.ModelsAndPrefs.AppPrefs;
import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.WaiterPackage.TimerWaiter;

import static yegor_gruk.example.com.rememberme.Utils.Utilities.getAnatomicSpinnerDrag;
import static yegor_gruk.example.com.rememberme.Utils.Utilities.getUsualSpinnerDrag;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button okButton, cancelButton;
    private TextView textView1, textView2, intervals;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3;

    private SimpleDateFormat dateFormat;

    private MainActivityPrefs prefs;

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelAlarm);
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        intervals = (TextView) findViewById(R.id.intervals);
        linearLayout1 = (LinearLayout) findViewById(R.id.layout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.layout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.layout3);


        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);

        dateFormat = new SimpleDateFormat(getString(R.string.time_format));

        prefs = new MainActivityPrefs(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.okButton:
                setAlarm();
                break;
            case R.id.cancelAlarm:
                cancelAlarm();
                break;
            case R.id.layout1:
                try {
                    showTimerDialog(R.id.text1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.layout2:
                try {
                    showTimerDialog(R.id.text2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.layout3:
                showAlertDialog();
                break;
        }

    }

    private long calculateInterval() throws ParseException {

        Date d1 = dateFormat.parse(textView1.getText().toString());
        Date d2 = dateFormat.parse(textView2.getText().toString());
        long divider = Long.parseLong(intervals.getText().toString());

        return (d2.getTime() - d1.getTime()) / divider;
    }

    public void setAlarm() {

        try {

            int times = Integer.parseInt(intervals.getText().toString());
            long intervalLength = calculateInterval();

            AlarmHandler alarmHandler = new AlarmHandler(this);
            alarmHandler.setRepeatingAlarm(intervalLength, times);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void cancelAlarm() {
        AlarmHandler alarmHandler = new AlarmHandler(this);
        alarmHandler.cancelAlarm();
    }

    public void showTimerDialog(int id) throws ParseException {

        final TextView textView = (TextView) findViewById(id);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(textView.getText().toString()));

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);


        new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        textView.setText(dateFormat.format(calendar.getTime()));

                    }
                }, hours, minutes, true).show();

    }

    public void showAlertDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog, null);

        builder.setView(v);
        builder.setMessage("Pick quantity");

        final SeekBar sbBetVal = (SeekBar) v.findViewById(R.id.seekBar2);
        final TextView tvBetVal = (TextView) v.findViewById(R.id.times);
        sbBetVal.setMax(100);

        String textValue = intervals.getText().toString();

        tvBetVal.setText(textValue);
        sbBetVal.setProgress(getUsualSpinnerDrag(Integer.parseInt(textValue)));

        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        sbBetVal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            TimerWaiter timerWaiter = new TimerWaiter(1000) {
                @Override
                public void doOnExit() {
                    dialog.dismiss();
                }
            };

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timerWaiter.runStartThread();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                timerWaiter.runStopThread();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                tvBetVal.setText(String.valueOf(getAnatomicSpinnerDrag(progress)));
                intervals.setText(String.valueOf(getAnatomicSpinnerDrag(progress)));
            }
        });

        dialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        intervals.setText(String.valueOf(prefs.getIntervals()));
        textView1.setText(prefs.getFirst());
        textView2.setText(prefs.getLast());

    }

    @Override
    protected void onPause() {
        super.onPause();

        prefs.saveIntervals(Integer.parseInt(intervals.getText().toString()));
        prefs.saveFirst(textView1.getText().toString());
        prefs.saveLast(textView2.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "action_settings", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_list) {
            //Toast.makeText(this, "action_list", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MainActivityPrefs extends AppPrefs {

        private static final String NUMBER_OF_INTERVALS = "NUMBER_OF_INTERVALS";
        private static final String FIRST_POINT = "FIRST_POINT";
        private static final String LAST_POINT = "LAST_POINT";

        private Context context;

        public MainActivityPrefs(Context context) {
            super(context);
            this.context = context;
        }

        public void saveIntervals(int interval) {
            Log.v("saveIntervals() ", "" + interval);
            put(NUMBER_OF_INTERVALS, interval);
        }

        public void saveFirst(String first) {
            Log.v("saveFirst() ", "" + first);
            put(FIRST_POINT, first);
        }

        public void saveLast(String last) {
            Log.v("saveLast() ", "" + last);
            put(LAST_POINT, last);
        }

        public int getIntervals() {
            Log.v("getIntervals() ", "" + getInt(NUMBER_OF_INTERVALS, 1));
            return getInt(NUMBER_OF_INTERVALS, 1);
        }

        public String getFirst() {
            Log.v("getFirst() ", "" + getString(FIRST_POINT, dateFormat.format(new Date())));
            return getString(FIRST_POINT, dateFormat.format(new Date()));
        }

        public String getLast() {
            Log.v("getLast() ", "" + getString(LAST_POINT, "23:59"));
            return getString(LAST_POINT, "23:59");
        }
    }

}
