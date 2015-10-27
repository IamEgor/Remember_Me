package yegor_gruk.example.com.rememberme.Utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import yegor_gruk.example.com.rememberme.R;

public class Utilities {

    private static final double GOLDEN_RATIO = 1.61803398875;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

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

    public static void makeToast2(View view, Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastRoot = inflater.inflate(R.layout.test_customtoast, null);
        TextView tv = (TextView) toastRoot.findViewById(R.id.toasttext);

        tv.setWidth(200);
        tv.setHeight(view.getHeight());

        Toast toast = new Toast(context);

        toast.setView(toastRoot);

        int location[] = new int[2];
        view.getLocationOnScreen(location);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, location[0] - 200, location[1] - view.getHeight() / 2);

        toast.show();

        toast.setDuration(Toast.LENGTH_SHORT);
        Log.d("^^^", "getRight()" + view.getRight());
        Log.d("^^^", "getWidth() " + view.getWidth());
        Log.d("^^^", "location[0] " + location[0]);
        Log.d("^^^", "toast.getXOffset() " + toast.getXOffset());
        Log.d("^^^", "toast.getYOffset() " + toast.getYOffset());
        Log.d("^^^", "___________");
    }

    private String getBeautifulTime(long minutes) {

        return minutes / 60 + ":" + minutes % 60;
    }
}
