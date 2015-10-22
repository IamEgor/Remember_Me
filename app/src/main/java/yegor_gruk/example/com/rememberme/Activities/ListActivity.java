package yegor_gruk.example.com.rememberme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import yegor_gruk.example.com.rememberme.AdapterModel;
import yegor_gruk.example.com.rememberme.Adapters.NewAdapter;
import yegor_gruk.example.com.rememberme.R;

/**
 * Created by Egor on 19.10.2015.
 */
public class ListActivity extends Activity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_list);

        listView = (ListView) findViewById(R.id.listView);

        Calendar cal = Calendar.getInstance();

        ArrayList<AdapterModel> models = new ArrayList<>();
        models.add(new AdapterModel(true, cal.getTimeInMillis()));
        models.add(new AdapterModel(false, cal.getTimeInMillis()));
        models.add(new AdapterModel(true, cal.getTimeInMillis()));
        models.add(new AdapterModel(true, cal.getTimeInMillis()));
        models.add(new AdapterModel(false, cal.getTimeInMillis()));
        models.add(new AdapterModel(true, cal.getTimeInMillis()));
        models.add(new AdapterModel(false, cal.getTimeInMillis()));
        models.add(new AdapterModel(true, cal.getTimeInMillis()));


        NewAdapter adapter = new NewAdapter(this, models);
        listView.setAdapter(adapter);
    }
}
