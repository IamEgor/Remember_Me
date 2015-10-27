package yegor_gruk.example.com.rememberme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yegor_gruk.example.com.rememberme.Adapters.NewAdapter;
import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.Models.AdapterModel;
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

        Toast.makeText(this, "" + getIntent().getIntExtra("intervals", -1), Toast.LENGTH_LONG).show();
        try {
            List<DatabaseModel> databaseModels = HelperFactory.getHelper().getModelDAO().getAllLastRecords(getIntent().getIntExtra("intervals", -1));
            //List<DatabaseModel> databaseModels = HelperFactory.getHelper().getModelDAO().getAllRecords();

            Log.wtf("^^^^^", " List<DatabaseModel>");

            for (DatabaseModel databaseModel : databaseModels) {
                Log.wtf("^^^^^", databaseModel.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (long item : getIntent().getLongArrayExtra("longArray")) {
            models.add(new AdapterModel(true, item));
        }

        NewAdapter adapter = new NewAdapter(this, models);
        listView.setAdapter(adapter);
    }
}
