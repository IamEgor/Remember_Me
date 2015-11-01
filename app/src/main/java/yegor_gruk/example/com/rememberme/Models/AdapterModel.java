package yegor_gruk.example.com.rememberme.Models;

import com.j256.ormlite.table.DatabaseTable;

import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;

/**
 * Created by Egor on 19.10.2015.
 */
@DatabaseTable
public class AdapterModel extends DatabaseModel {

    public static final String COMPLETED = "Завершен";
    public static final String MISSED = "Пропущен";
    public static final String READY = "Готов";
    public static final String SKIP = "Пропуск";

    private static final String ERROR = "Ошибка";


    /*
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private long timeMils;
    @DatabaseField
    private boolean isActive;
    */

    /*

    private String formatedTime;
    private String label;
    private int imageId;
    private HashMap<String, Integer> map = new HashMap<String, Integer>() {
        {
            put(COMPLETED, R.drawable.ic_alarm_on_black_48dp);
            put(MISSED, R.drawable.ic_alarm_off_black_48dp);
            put(READY, R.drawable.ic_alarm_black_48dp);
            put(SKIP, R.drawable.ic_alarm_sleep_black_48dp);
        }
    };


    public AdapterModel(boolean isActive, long timeMils) {
        this.isActive = isActive;
        this.timeMils = timeMils;

    }


    public void invertBool() {
        isActive = !isActive;
    }

    public String getFormatedTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        formatedTime = dateFormat.format(timeMils);
        Log.d("###", formatedTime);
        return formatedTime;
    }

    public String getLabel() {

        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();

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

        //if (label == null)
        label = getLabel();

        imageId = map.get(label);
        Log.d("###", "" + imageId);
        return imageId;
    }

    */

    /*
    private int[] pics = new int[]{
            R.drawable.ic_alarm_sleep_black_48dp,
            R.drawable.ic_alarm_black_48dp,
            R.drawable.ic_alarm_on_black_48dp,
            R.drawable.ic_alarm_off_black_48dp};
    */
}