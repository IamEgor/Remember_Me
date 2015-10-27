package yegor_gruk.example.com.rememberme.Activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yegor_gruk.example.com.rememberme.AlarmHandler;
import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.Prefs.MainActivityPrefs;
import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.Utils.Utilities;
import yegor_gruk.example.com.rememberme.WaiterPackage.TimerWaiter;

import static yegor_gruk.example.com.rememberme.Utils.Utilities.getAnatomicSpinnerDrag;
import static yegor_gruk.example.com.rememberme.Utils.Utilities.getUsualSpinnerDrag;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

        textView1.setText(dateFormat.format(new Date().getTime()));
        textView2.setText("23:59");
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


    public void setAlarm() {
        try {

            AlarmHandler alarmHandler = new AlarmHandler(this);
            alarmHandler.createAlarmQueue(Utilities.getTimes(textView1, textView2, intervals));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            textView1.setClickable(false);
            textView2.setClickable(false);
            intervals.setClickable(false);
        }


    }

    public void cancelAlarm() {
        //AlarmHandler alarmHandler = new AlarmHandler(this);
        //alarmHandler.cancelAlarm();
        try {
            List<DatabaseModel> list = HelperFactory.getHelper().getModelDAO().getAllRecords();

            Log.v("$$$$$$$$", "size = " + list.size());
            for (DatabaseModel model : list) {
                Log.v("$$$$$$$$", model.toString());
            }
        } catch (SQLException e) {
            Toast.makeText(this, "cancelAlarm() " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
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
        //textView1.setText(prefs.getFirst());
        //textView2.setText(prefs.getLast());

    }

    @Override
    protected void onPause() {
        super.onPause();

        prefs.saveIntervals(Integer.parseInt(intervals.getText().toString()));
        //prefs.saveFirst(textView1.getText().toString());
        //prefs.saveLast(textView2.getText().toString());
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
            try {
                intent.putExtra("longArray", Utilities.getTimes(textView1, textView2, intervals));
                intent.putExtra("intervals", Integer.parseInt(intervals.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
