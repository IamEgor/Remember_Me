package yegor_gruk.example.com.rememberme.Util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utilities {

    private static final double GOLDEN_RATIO = 1.61803398875;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public static int getAnatomicSpinnerDrag(int usualProgress) {

        return (int) (Math.pow(usualProgress, GOLDEN_RATIO) / 86.8) + 1;
    }

    public static int getUsualSpinnerDrag(int anatomicProgress) {

        return (int) Math.pow((anatomicProgress - 1) * 86.89, 1 / GOLDEN_RATIO);
    }

    public static long calculateInterval(TextView firstPoint, TextView secondPoint, TextView times) throws ParseException {

        long divider = Long.parseLong(times.getText().toString());

        Date d1 = dateFormat.parse(firstPoint.getText().toString());
        Date d2 = dateFormat.parse(secondPoint.getText().toString());

        return (d2.getTime() - d1.getTime()) / divider;
    }

    public static long[] getTimes(TextView firstPoint, TextView secondPoint, TextView times_) throws ParseException {

        int times = Integer.parseInt(times_.getText().toString());

        long startTime = dateFormat.parse(firstPoint.getText().toString()).getTime();
        long stopTime = dateFormat.parse(secondPoint.getText().toString()).getTime();

        long interval = (stopTime - startTime) / times;

        long temp = System.currentTimeMillis();

        long[] array = new long[times];

        for (int i = 0; i < times; i++) {
            array[i] = temp = temp + interval;
        }

        return array;
    }

    //----------------------------------------------------------------------------------------------

    public static void makeToast(View view, Context context, String message) {

        int location[] = new int[2];
        view.getLocationOnScreen(location);
        Toast toast = Toast.makeText(context,
                message, Toast.LENGTH_SHORT);

        Log.d("^^^", "getRight()" + view.getRight());
        Log.d("^^^", "getWidth() " + view.getWidth());
        Log.d("^^^", "location[0] " + location[0]);
        Log.d("^^^", "toast.getXOffset() " + toast.getXOffset());
        Log.d("^^^", "toast.getYOffset() " + toast.getYOffset());
        Log.d("^^^", "___________");

        toast.setGravity(Gravity.TOP | Gravity.LEFT, (int) (location[0] - 2.3 * view.getWidth()), location[1] - view.getHeight() / 2);

        toast.show();
    }

    public static String getTimeStr() {

        Calendar calendar = Calendar.getInstance();

        return dateFormat.format(calendar.getTime());
    }

    public static String getTimeStr(int hours, int minutes) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

        return dateFormat.format(calendar.getTime());
    }

    public static int[] getHoursAndMinutes(String s) throws ParseException {

        int[] time = new int[2];

        Calendar calendar = stringToCalendar(s);

        time[0] = calendar.get(Calendar.HOUR_OF_DAY);
        time[1] = calendar.get(Calendar.MINUTE);

        return time;
    }

    public static Date stringToDate(String string) throws ParseException {
        return dateFormat.parse(string);
    }

    public static Calendar stringToCalendar(String string) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate(string));

        return calendar;
    }


}
