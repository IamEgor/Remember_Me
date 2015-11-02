package yegor_gruk.example.com.rememberme.Activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yegor_gruk.example.com.rememberme.Adapters.NewAdapter;
import yegor_gruk.example.com.rememberme.Models.AdapterModel;
import yegor_gruk.example.com.rememberme.Prefs.MainActivityPrefs;
import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.TEST_TEST.MyNewLoader;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<AdapterModel>> {

    public static final String TAG = ListActivity.class.getName();

    private static final int URL_LOADER = 0;

    NewAdapter adapter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_list);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);


        ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new NewAdapter(this, new ArrayList<AdapterModel>());

        listView.setAdapter(adapter);

        getLoaderManager().initLoader(URL_LOADER, getIntent().getExtras(), this).forceLoad();
    }

    @Override
    public Loader<List<AdapterModel>> onCreateLoader(int loaderID, Bundle bundle) {

        Log.d(TAG, "||onCreateLoader()|| - " + bundle.getInt(MainActivityPrefs.NUMBER_OF_INTERVALS));

        switch (loaderID) {

            case URL_LOADER:
                return new MyNewLoader(this, bundle.getInt(MainActivityPrefs.NUMBER_OF_INTERVALS));

            default:
                return null;

        }

    }

    @Override
    public void onLoadFinished(Loader<List<AdapterModel>> loader, List<AdapterModel> databaseModels) {
        adapter.setModels(databaseModels);
    }

    @Override
    public void onLoaderReset(Loader<List<AdapterModel>> loader) {
        adapter.setModels(new ArrayList<AdapterModel>());
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
            Toast.makeText(this, "action_list", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
