package yegor_gruk.example.com.rememberme.Models;

import android.util.Log;

import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.R;

@DatabaseTable
public class AdapterModel {

    public static final String COMPLETED = "Завершен";
    public static final String MISSED = "Пропущен";
    public static final String READY = "Готов";
    public static final String SKIP = "Пропуск";

    private static final String ERROR = "Ошибка";

    private String formattedTime;
    private String label;
    private int imageId;

    private DatabaseModel databaseModel;

    private HashMap<String, Integer> map = new HashMap<String, Integer>() {
        {
            put(COMPLETED, R.drawable.ic_alarm_on_black_48dp);
            put(MISSED, R.drawable.ic_alarm_off_black_48dp);
            put(READY, R.drawable.ic_alarm_black_48dp);
            put(SKIP, R.drawable.ic_alarm_sleep_black_48dp);
        }
    };

    public AdapterModel(DatabaseModel databaseModel) {
        this.databaseModel = databaseModel;
    }

    public void invertBool() throws SQLException {

        if (databaseModel.isActive())
            databaseModel.setIsActive(false);
        else
            databaseModel.setIsActive(true);

        HelperFactory.getHelper().getModelDAO().update(databaseModel);
        //isActive = !isActive;
    }

    public String getFormattedTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        formattedTime = dateFormat.format(databaseModel.getRepTime());
        Log.d("###", formattedTime);
        return formattedTime;
    }

    public String getLabel() {

        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();

        long timeMils = databaseModel.getRepTime();
        boolean isActive = databaseModel.isActive();

        if (timeMils < currentTime && isActive)
            label = COMPLETED;
        else if (timeMils < currentTime && !isActive)
            label = MISSED;
        else if (timeMils > currentTime && isActive)
            label = READY;
        else if (timeMils > currentTime && !isActive)
            label = SKIP;
        else
            return label = ERROR;

        return label;
    }

    public int getImageId() {

        label = getLabel();

        imageId = map.get(label);

        return imageId;
    }

    public long getId() {
        return databaseModel.getId();
    }

    public int getBackgroundColor() {

        if (databaseModel.getRepTime() > Calendar.getInstance().getTimeInMillis())
            return R.color.background_enabled;
        else
            return R.color.background_disabled;
    }

    public int getTextColor() {

        if (databaseModel.getRepTime() > Calendar.getInstance().getTimeInMillis())
            return R.color.text_enabled;
        else
            return R.color.text_disabled;
    }

    public long getTime() {
        return databaseModel.getRepTime();
    }

    /*
    private int[] pics = new int[]{
            R.drawable.ic_alarm_sleep_black_48dp,
            R.drawable.ic_alarm_black_48dp,
            R.drawable.ic_alarm_on_black_48dp,
            R.drawable.ic_alarm_off_black_48dp};
    */
}